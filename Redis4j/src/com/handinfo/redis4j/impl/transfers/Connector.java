package com.handinfo.redis4j.impl.transfers;

import java.net.InetSocketAddress;
import java.util.concurrent.BlockingQueue;
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
import com.handinfo.redis4j.api.RedisCommandType;
import com.handinfo.redis4j.api.RedisResultType;
import com.handinfo.redis4j.api.exception.CleanLockedThreadException;
import com.handinfo.redis4j.api.exception.ErrorCommandException;
import com.handinfo.redis4j.api.exception.RedisClientException;
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

	/**
	 * TODO 连接池考虑后面再增加,经过试用xmemcached客户度连接池,效果还不如没有时好
	 **/
	public Connector(String host, int port, int poolMaxSize, int indexDB, int heartbeatTime, int reconnectDelay) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException
	{
		if (!checkIpAndPort(host, port))
		{
			throw new IllegalArgumentException("create connector failed, please check host or port");
		}

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

	private void selectDB(int indexDB) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException
	{
		executeCommand(RedisCommandType.SELECT, indexDB);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.handinfo.redis4j.impl.transfers.IConnector#executeCommand(java.lang
	 * .String, java.lang.Object)
	 */
	public Object[] executeCommand(String commandType, Object... args) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException
	{
		// 等待返回结果
		if (channel != null && channel.isConnected())
		{
			Object[] redisCommand = new Object[args.length + 1];
			redisCommand[0] = commandType;
			System.arraycopy(args, 0, redisCommand, 1, args.length);

			final CommandWrapper cmd = new CommandWrapper(redisCommand);

			channel.write(cmd);

			if (cmd.getException() != null)
			{
				if (cmd.getException() instanceof CleanLockedThreadException)
				{
					throw (CleanLockedThreadException) cmd.getException();
				}
				else
				{
					throw cmd.getException();
				}
			} else
			{
				Object[] result = cmd.getResult();
				if (result != null && result.length > 1)
				{
					Character resultType = (Character) result[0];
					if (resultType != RedisResultType.ErrorReply)
					{
						return cmd.getResult();
					}
				}
				throw new ErrorCommandException((String) result[1]);
			}
		} else
			throw new IllegalStateException("connection has been disconnected.");
	}

	public BlockingQueue<Object[]> asyncExecuteCommand(String commandType, Object... args)
	{
		Object[] command = new Object[args.length + 1];
		command[0] = commandType;
		for (int i = 1; i < command.length; i++)
		{
			command[i] = args[i - 1];
		}

		BlockingQueue<Object[]> result = null;
		Channel channel = null;

		// try
		// {
		// //channel = getChannelFromPool();
		//
		// if (channel != null && channel.isConnected())
		// {
		// // 读数据
		// result =
		// channel.getPipeline().get(RedislHandler.class).asyncSendRequest(RedisCommandEncoder.getBinaryCommand(command));
		// }
		//
		// } catch (InterruptedException e)
		// {
		// e.printStackTrace();
		// }

		return result;
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
