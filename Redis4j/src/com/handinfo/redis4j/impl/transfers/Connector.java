package com.handinfo.redis4j.impl.transfers;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timer;

import com.handinfo.redis4j.api.IConnector;
import com.handinfo.redis4j.api.IRedis4jAsync;
import com.handinfo.redis4j.api.RedisCommand;
import com.handinfo.redis4j.api.RedisResponse;
import com.handinfo.redis4j.api.RedisResponseType;
import com.handinfo.redis4j.api.exception.CleanLockedThreadException;
import com.handinfo.redis4j.api.exception.ErrorCommandException;
import com.handinfo.redis4j.impl.util.CommandWrapper;

public class Connector implements IConnector
{
	private static final Logger logger = Logger.getLogger(Connector.class.getName());

	private ClientBootstrap bootstrap;
	private ChannelGroup channelGroup;
	private InetSocketAddress hostAddress;
	private int defaultIndexDB;
	private Channel channel;
	private int heartbeatTime;
	private int reconnectDelay;
	private final Timer timer = new HashedWheelTimer();
	private AtomicBoolean isStartQuit = new AtomicBoolean(false);
	private final BlockingQueue<CommandWrapper> commandQueue = new LinkedBlockingQueue<CommandWrapper>();;
	private AtomicBoolean isAllowWrite = new AtomicBoolean(true);
	private final ReentrantLock lock = new ReentrantLock();
	private boolean isUseHeartbeat;


	public Connector(String host, int port, int indexDB, int heartbeatTime, int reconnectDelay, boolean isUseHeartbeat) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException
	{
		if (!checkIpAndPort(host, port))
		{
			throw new IllegalArgumentException("create connector failed, please check host or port");
		}

		this.isUseHeartbeat = isUseHeartbeat;
		this.reconnectDelay = reconnectDelay;
		this.heartbeatTime = heartbeatTime;
		this.hostAddress = new InetSocketAddress(host.trim(), port);
		this.defaultIndexDB = indexDB;
		this.channel = null;

		// 配置启动参数
		this.bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));

		// 设置连接使用的管道参数
		this.bootstrap.setPipelineFactory(new PipelineFactory(this, timer));

		// 设置ChannelGroup
		this.channelGroup = new DefaultChannelGroup();
	}

	/**
	 * @return the isUseHeartbeat
	 */
	public boolean isUseHeartbeat()
	{
		return isUseHeartbeat;
	}

	/**
	 * @return the lock
	 */
	public ReentrantLock getLock()
	{
		return lock;
	}

	/**
	 * @return the isStartClean
	 */
	public AtomicBoolean getIsAllowWrite()
	{
		return isAllowWrite;
	}

	/**
	 * @return the commandQueue
	 */
	public BlockingQueue<CommandWrapper> getCommandQueue()
	{
		return commandQueue;
	}

	/**
	 * @return the reconnectDelay
	 */
	public int getReconnectDelay()
	{
		return reconnectDelay;
	}

	public int getHeartbeatTime()
	{
		return this.heartbeatTime;
	}

	public InetSocketAddress getRemoteAddress()
	{
		return hostAddress;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.impl.transfers.IConnector#connect()
	 */
	public boolean connect() throws IllegalStateException, CleanLockedThreadException, ErrorCommandException
	{
		if (this.channel == null || !this.channel.isConnected())
		{
			ChannelFuture future = bootstrap.connect(hostAddress);

			// 等待连接创建结果
			channel = future.awaitUninterruptibly().getChannel();
			if (future.isSuccess())
			{
				// 加入Group,以便于关闭全部
				channelGroup.add(channel);

				// 切换默认DB
				selectDB(defaultIndexDB);

				return true;
			} else
			{
				return false;
			}
		} else
		{
			// 连接已存在
			return true;
		}
	}

	public boolean getIsConnected()
	{
		if (channel != null)
			return channel.isConnected();
		else
			return false;
	}

	private void selectDB(int indexDB) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException
	{
		executeCommand(RedisCommand.SELECT, indexDB);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.handinfo.redis4j.impl.transfers.IConnector#executeCommand(java.lang
	 * .String, java.lang.Object)
	 */
	public RedisResponse executeCommand(RedisCommand command, Object... args) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException
	{
		if (channel != null && channel.isConnected())
		{
			Object[] redisCommand = new Object[args.length + command.getValue().length];
			System.arraycopy(command.getValue(), 0, redisCommand, 0, command.getValue().length);
			System.arraycopy(args, 0, redisCommand, command.getValue().length, args.length);

			final CommandWrapper cmdWrapper = new CommandWrapper(CommandWrapper.Type.SYNC, redisCommand);

			channel.write(cmdWrapper);

			if (cmdWrapper.getException() != null)
			{
				if (cmdWrapper.getException() instanceof CleanLockedThreadException)
				{
					throw (CleanLockedThreadException) cmdWrapper.getException();
				} else
				{
					throw cmdWrapper.getException();
				}
			} else
			{
				RedisResponse response = cmdWrapper.getResult()[0];
				if (response != null)
				{
					if (response.getType() != RedisResponseType.ErrorReply)
					{
						return response;
					}
					else
					{
						throw new ErrorCommandException(response.getTextValue());
					}
				}
				else
				{
					throw new ErrorCommandException("Error back value");
				}
			}
		} else
			throw new IllegalStateException("connection has been disconnected.");
	}
	
	public RedisResponse[] executeBatch(ArrayList<String[]> commandList) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException
	{
		if (channel != null && channel.isConnected())
		{
			final CommandWrapper cmdWrapper = new CommandWrapper(commandList);

			channel.write(cmdWrapper);

			if (cmdWrapper.getException() != null)
			{
				if (cmdWrapper.getException() instanceof CleanLockedThreadException)
				{
					throw (CleanLockedThreadException) cmdWrapper.getException();
				} else
				{
					throw cmdWrapper.getException();
				}
			} else
			{
				RedisResponse[] result = cmdWrapper.getResult();

				if (result != null && result.length > 0)
				{
					return result;
				}
				else
				{
					throw new ErrorCommandException("Error back value");
				}
			}
		} else
			throw new IllegalStateException("connection has been disconnected.");
	}

	public void executeAsyncCommand(IRedis4jAsync.Notify notify, RedisCommand command, Object... args) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException, InterruptedException, BrokenBarrierException
	{
		if (channel != null && channel.isConnected())
		{
			Object[] redisCommand = new Object[args.length + command.getValue().length];
			System.arraycopy(command.getValue(), 0, redisCommand, 0, command.getValue().length);
			System.arraycopy(args, 0, redisCommand, command.getValue().length, args.length);

			final CommandWrapper cmd = new CommandWrapper(CommandWrapper.Type.ASYNC, redisCommand);

			boolean isFirstWrite = true;
			while (true)
			{
				if(isStartQuit.get())
					break;
				if(isFirstWrite)
				{
					isFirstWrite = false;
					channel.write(cmd);
				}
				if (channel == null || !channel.isConnected())
				{
					notify.onNotify("Connection has been disconnected, please wait to auto reconnect or quit the client...");
					Thread.sleep(this.reconnectDelay*1000);
					isFirstWrite = true;
				} else
				{
					if (cmd.getException() != null)
					{
						if (cmd.getException() instanceof CleanLockedThreadException)
						{
							throw (CleanLockedThreadException) cmd.getException();
						} else
						{
							throw cmd.getException();
						}
					} else
					{
						RedisResponse response = cmd.getResult()[0];
						if (response != null)
						{
							if (response.getType() != RedisResponseType.ErrorReply)
							{
								String result = null;
								if (response.getType() == RedisResponseType.BulkReplies)
								{
									result = String.valueOf(response.getBulkValue());
								}else if (response.getType() == RedisResponseType.MultiBulkReplies)
								{
									result = "";
								}
								else
								{
									result = response.getTextValue();
								}
								notify.onNotify(result);
								cmd.pause();
							}
							else
								throw new ErrorCommandException(response.getTextValue());
						}
						else
						{
							throw new ErrorCommandException("Error back value");
						}
						
					}
				}
			}
		} else
			throw new IllegalStateException("connection has been disconnected.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.impl.transfers.IConnector#disConnect()
	 */
	public void disConnect()
	{
		if (!isStartQuit.getAndSet(true))
		{
			timer.stop();
			channel.disconnect().awaitUninterruptibly();
			channel.close().awaitUninterruptibly();
			bootstrap.releaseExternalResources();
		}
	}

	public void cleanCommandQueue()
	{
		if (this.getIsAllowWrite().getAndSet(false))
		{
			Thread clean = new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					Connector.this.getLock().lock();
					try
					{
						printMsg(Level.INFO, "Start clean " + commandQueue.size() + " command...");

						for (CommandWrapper cmd : commandQueue)
						{
							cmd.resume();
						}
						commandQueue.clear();

						printMsg(Level.INFO, "Clean finished==" + commandQueue.size());
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					catch (BrokenBarrierException e)
					{
						e.printStackTrace();
					}
					finally
					{
						Connector.this.getIsAllowWrite().set(true);
						Connector.this.getLock().unlock();
					}
				}
			});
			clean.setName("CleanCommandQueue");
			clean.start();
		}
	}

	public boolean getIsStartClose()
	{
		return isStartQuit.get();
	}

	private boolean checkIpAndPort(String ip, int port)
	{
		boolean result = false;
		boolean isIP = false;
		boolean isPort = false;

		ip = ip.trim();
		if (ip.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}"))
		{
			String s[] = ip.split("\\.");
			if (Integer.parseInt(s[0]) < 255)
				if (Integer.parseInt(s[1]) < 255)
					if (Integer.parseInt(s[2]) < 255)
						if (Integer.parseInt(s[3]) < 255)
							isIP = true;
		}

		if (port >= 0 && port <= 65535)
		{
			isPort = true;
		}

		if (isIP && isPort)
		{
			result = true;
		}

		return result;
	}

	private void printMsg(Level level, String msg)
	{
		logger.log(level, "Thread name:" + Thread.currentThread().getName() + " - ID:" + Thread.currentThread().getId() + " - " + msg);
	}
}
