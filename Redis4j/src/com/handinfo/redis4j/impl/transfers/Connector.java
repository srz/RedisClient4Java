package com.handinfo.redis4j.impl.transfers;

import java.net.InetSocketAddress;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import com.handinfo.redis4j.api.RedisCommandType;
import com.handinfo.redis4j.impl.protocol.RedislHandler;
import com.handinfo.redis4j.impl.protocol.encode.RedisCommandEncoder;

public class Connector
{
	private final ReentrantLock lock = new ReentrantLock();

	private ClientBootstrap bootstrap;
	private ChannelGroup channelGroup;

	private int poolMaxSize;
	private BlockingQueue<Channel> pool;
	
	private InetSocketAddress hostAddress;
	private int defaultIndexDB;

	public Connector(String host, int port, int poolMaxSize, int indexDB)
	{
		if (!checkIpAndPort(host, port))
		{
			throw new RedisConnectionPoolException("create connection pool failed, please check host or port");
		}

		this.hostAddress = new InetSocketAddress(host.trim(), port);
		this.defaultIndexDB = indexDB;
		
		// 配置启动参数
		this.bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));

		// 设置连接使用的管道参数
		this.bootstrap.setPipelineFactory(new PipelineFactory());

		// 设置ChannelGroup
		this.channelGroup = new DefaultChannelGroup();

		this.poolMaxSize = poolMaxSize;
		this.pool = new LinkedBlockingQueue<Channel>(poolMaxSize);

		Channel firstChannel = null;
		try
		{
			firstChannel = getChannelFromPool();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		if (firstChannel == null)
		{
			throw new RedisConnectionPoolException("create connection pool failed, please check server state");
		}
		else
		{
			try
			{
				pool.put(firstChannel);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	private Channel getChannelFromPool() throws InterruptedException
	{
		Channel channel = null;
		
		lock.lock();
		try
		{
			//从池子中移除已经断开的连接
			for(Channel item : pool)
			{
				if(!channelGroup.contains(item))
				{
					pool.remove(item);
				}
			}
			// 池子中查找是否有可用连接
			if (pool.size() > 0)
			{
				// 有可用连接 - 返回任意可用连接
				channel = pool.take();
			} else
			{
				// 无可用连接 - 判断已经成功创建的连接是否达到了池子尺寸
				if (channelGroup.size() >= this.poolMaxSize)
				{
					// 达到了最大连接数 - 池子满,阻塞住
					channel = pool.take();
				} else
				{
					// 没达到最大连接数

					// 创建新的Channel,并添加到ChannelGroup

					// 创建一个连接
					ChannelFuture future = bootstrap.connect(hostAddress);

					// 等待连接创建结果
					channel = future.awaitUninterruptibly().getChannel();
					if (future.isSuccess())
					{
						// 加入Group,以便于关闭全部
						channelGroup.add(channel);

						// 切换默认DB
						selectDB(channel, defaultIndexDB);
					}
				}
			}
		}
		finally
		{
			lock.unlock();
		}

		return channel;
	}

	private void selectDB(Channel conn, int indexDB)
	{
		Object[] command = new Object[2];
		command[0] = RedisCommandType.SELECT;
		command[1] = indexDB;
		conn.getPipeline().get(RedislHandler.class).syncSendRequest(RedisCommandEncoder.getBinaryCommand(command));
	}

	public Object[] executeCommand(String commandType, Object... args)
	{
		Object[] command = new Object[args.length + 1];
		command[0] = commandType;
		for (int i = 1; i < command.length; i++)
		{
			command[i] = args[i - 1];
		}

		Object[] result = null;
		Channel channel = null;

		try
		{
			channel = getChannelFromPool();

			if(channel != null && channel.isConnected())
			{
				// 读数据
				result = channel.getPipeline().get(RedislHandler.class).syncSendRequest(RedisCommandEncoder.getBinaryCommand(command));
				
				if (result[0] == null)
				{
					System.out.println("与服务器断开");
					//throw new Exception("channelDisconnected");
				}
				else
				{
					pool.put(channel);
				}
			}
			else
			{
				System.out.println("无法连接服务器");
			}
		}
		catch (InterruptedException e)
		{
			//System.out.println("InterruptedException [" + e.getMessage() + "] in Connector row 125");
			e.printStackTrace();
		}

		return result;
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

		try
		{
			channel = getChannelFromPool();

			if(channel != null && channel.isConnected())
			{
				// 读数据
				result = channel.getPipeline().get(RedislHandler.class).asyncSendRequest(RedisCommandEncoder.getBinaryCommand(command));
			}

		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		return result;
	}

	public void disConnect()
	{
		channelGroup.close().awaitUninterruptibly();
		bootstrap.releaseExternalResources();
		pool.clear();
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
}
