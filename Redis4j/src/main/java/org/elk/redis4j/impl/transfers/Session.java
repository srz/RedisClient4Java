package org.elk.redis4j.impl.transfers;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

import org.elk.redis4j.api.ISession;
import org.elk.redis4j.api.RedisCommand;
import org.elk.redis4j.api.RedisResponse;
import org.elk.redis4j.api.RedisResponseMessage;
import org.elk.redis4j.api.RedisResponseType;
import org.elk.redis4j.api.Sharding;
import org.elk.redis4j.api.async.IRedisAsyncClient;
import org.elk.redis4j.api.exception.CleanLockedThreadException;
import org.elk.redis4j.api.exception.ErrorCommandException;
import org.elk.redis4j.api.exception.RedisClientException;
import org.elk.redis4j.impl.util.CommandWrapper;
import org.elk.redis4j.impl.util.LogUtil;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelPipeline;


public class Session implements ISession
{
	private final Logger logger = LogUtil.getLogger(Session.class.getName());
	private Channel channel;
	private AtomicBoolean isStartQuit = new AtomicBoolean(false);
	private final BlockingQueue<CommandWrapper> commandQueue = new LinkedBlockingQueue<CommandWrapper>();
	private AtomicBoolean isAllowWrite = new AtomicBoolean(true);
	private final ReentrantLock lock = new ReentrantLock();

	private final Condition cleanCondition = lock.newCondition();
	private final AtomicInteger totalOfWaitCondition = new AtomicInteger(0);

	private Sharding sharding;
	private ChannelPipeline pipeline;
	private SessionManager manager;
	private String name;

	/**
	 * @return the totalOfWaitCondition
	 */
	public AtomicInteger getTotalOfWaitCondition()
	{
		return totalOfWaitCondition;
	}

	/**
	 * @return the cleanCondition
	 */
	public Condition getCleanCondition()
	{
		return cleanCondition;
	}

	private final Condition condition = lock.newCondition();

	public Condition getCondition()
	{
		return condition;
	}

	public Session(SessionManager manager, Sharding sharding)
	{
		this.sharding = sharding;
		this.manager = manager;
		this.name = this.sharding.getName();
	}

	/**
	 * @return the isStartClean
	 */
	@Override
	public AtomicBoolean isAllowWrite()
	{
		return this.isAllowWrite;
	}

	/**
	 * @return the commandQueue
	 */
	@Override
	public BlockingQueue<CommandWrapper> getCommandQueue()
	{
		return commandQueue;
	}

	@Override
	public boolean isConnected()
	{
		if (channel != null)
			return channel.isConnected();
		else
			return false;
	}

	@Override
	public ReentrantLock getChannelSyncLock()
	{
		return this.lock;
	}

	@Override
	public int getHeartbeatTime()
	{
		return this.sharding.getHeartbeatTime();
	}

	@Override
	public int getReconnectDelay()
	{
		return this.sharding.getReconnectDelay();
	}

	@Override
	public InetSocketAddress getRemoteAddress()
	{
		return this.sharding.getServerAddress();
	}

	@Override
	public boolean isStartClose()
	{
		return this.isStartQuit.get();
	}

	@Override
	public boolean isUseHeartbeat()
	{
		return this.sharding.isUseHeartbeat();
	}

	@Override
	public void reConnect()
	{
		this.manager.updateChannel(this);
	}

	@Override
	public void setChannel(Channel channel)
	{
		this.channel = channel;
		RedisResponse response = executeCommand(RedisCommand.AUTH, this.sharding.getPassword());
		if (!RedisResponseMessage.OK.getValue().equalsIgnoreCase(response.getTextValue()))
		{
			logger.severe("Auth failed! server back[" + response.getTextValue() + "] Client has been auto quit!");
			this.manager.disconnectAllSession();

			// 帐号验证失败
			throw new RedisClientException("Auth failed! server back[" + response.getTextValue() + "] Client has been auto quit!");
		}
		executeCommand(RedisCommand.SELECT, this.sharding.getDefaultIndexDB());
	}

	@Override
	public void setPipiline(ChannelPipeline pipeline)
	{
		this.pipeline = pipeline;
	}

	@Override
	public ChannelPipeline getPipiline()
	{
		return this.pipeline;
	}

	@Override
	public RedisResponse executeCommand(RedisCommand command, Object... args)
	{
		if (channel != null && channel.isConnected())
		{			
			Object[] redisCommand = new Object[args.length + command.getValue().length];
			System.arraycopy(command.getValue(), 0, redisCommand, 0, command.getValue().length);
			System.arraycopy(args, 0, redisCommand, command.getValue().length, args.length);
			
			final CommandWrapper cmdWrapper = new CommandWrapper(command, CommandWrapper.Type.SYNC, redisCommand);
			
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
				RedisResponse response = cmdWrapper.getSyncResult().get(0);
				if (response != null)
				{
					return response;
				} else
				{
					throw new ErrorCommandException("Error back value");
				}
			}
		} else
			throw new IllegalStateException("connection has been disconnected.");
	}

	@Override
	public List<RedisResponse> executeBatch(List<Object[]> commandList)
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
				List<RedisResponse> result = cmdWrapper.getSyncResult();

				if (result != null)
				{
					return result;
				} else
				{
					throw new ErrorCommandException("Error back value");
				}
			}
		} else
			throw new IllegalStateException("connection has been disconnected.");
	}

	@Override
	public void executeAsyncCommand(IRedisAsyncClient.Result notify, RedisCommand command, Object... args) throws InterruptedException
	{
		if (channel != null && channel.isConnected())
		{
			Object[] redisCommand = new Object[args.length + command.getValue().length];
			System.arraycopy(command.getValue(), 0, redisCommand, 0, command.getValue().length);
			System.arraycopy(args, 0, redisCommand, command.getValue().length, args.length);

			final CommandWrapper cmd = new CommandWrapper(command, CommandWrapper.Type.ASYNC, redisCommand);

			boolean isFirstWrite = true;
			while (true)
			{
				if (isStartQuit.get())
					break;
				if (isFirstWrite)
				{
					isFirstWrite = false;
					channel.write(cmd);
				}
				if (channel == null || !channel.isConnected())
				{
					notify.doInCurrentThread("Connection has been disconnected, please wait to auto reconnect or quit the client...");
					Thread.sleep(this.sharding.getReconnectDelay() * 1000);
					isFirstWrite = true;
				} else
				{
					RedisResponse response = cmd.getAsyncResult();

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
						if (response != null)
						{
							if (response.getType() != RedisResponseType.ErrorReply)
							{
								String result = null;
								if (response.getType() == RedisResponseType.BulkReplies)
								{
									result = String.valueOf(response.getBulkValue());
								} else if (response.getType() == RedisResponseType.MultiBulkReplies)
								{
									result = "";
								} else
								{
									result = response.getTextValue();
								}
								notify.doInCurrentThread(result);
							} else
								throw new ErrorCommandException(response.getTextValue());
						} else
						{
							throw new ErrorCommandException("Error back value");
						}
					}
				}
			}
		} else
			throw new IllegalStateException("connection has been disconnected.");
	}

	@Override
	public void cleanCommandQueue()
	{
		if (this.isAllowWrite().getAndSet(false))
		{
			Thread clean = new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					Session.this.getChannelSyncLock().lock();
					logger.info(Session.this.sharding.getServerAddress() + " signalAll....");
					Session.this.getCondition().signalAll();
					Session.this.getChannelSyncLock().unlock();

					Session.this.getChannelSyncLock().lock();
					try
					{
						if (Session.this.getTotalOfWaitCondition().get() != 0)
						{
							Session.this.cleanCondition.await();
						}
						logger.info(Session.this.sharding.getServerAddress() + " signalAll....  finished");

						logger.info(Session.this.sharding.getServerAddress() + " Start clean " + commandQueue.size() + " command...");

						for (CommandWrapper cmd : commandQueue)
						{
							if (cmd.getType() == CommandWrapper.Type.SYNC)
								cmd.resume();
						}
						commandQueue.clear();

						logger.info(Session.this.sharding.getServerAddress() + " Clean finished==" + commandQueue.size());
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					finally
					{
						Session.this.isAllowWrite().set(true);
						Session.this.getChannelSyncLock().unlock();
					}
				}
			});
			clean.setName("CleanCommandQueue-" + this.sharding.getServerAddress());
			clean.start();
		}
	}

	@Override
	public void close()
	{
		this.isStartQuit.set(true);
	}

	@Override
	public String getName()
	{
		return this.name;
	}
}
