package com.handinfo.redis4j.impl.transfers;

import java.net.InetSocketAddress;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import com.handinfo.redis4j.api.RedisCommandType;
import com.handinfo.redis4j.impl.protocol.RedislHandler;
import com.handinfo.redis4j.impl.protocol.encode.RedisCommandEncoder;

public class Connector
{
	private ClientBootstrap bootstrap;
	
	private AtomicBoolean isClose;
	private int poolSize;
	private BlockingQueue<Channel> pool;
	private int numberOfCreateSucess = 0;
	
	
	
	public Connector(String host, int port, int poolSize, int indexDB)
	{
		connect(host, port, poolSize, indexDB);
	}

	@SuppressWarnings("finally")
	private boolean connect(String host, int port, int poolSize, int indexDB)
	{
		// 配置启动参数
		this.bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));

		// 设置连接使用的管道参数
		this.bootstrap.setPipelineFactory(new PipelineFactory());

		this.isClose = new AtomicBoolean(false);
		this.poolSize = poolSize;
		this.pool = new LinkedBlockingQueue<Channel>(poolSize);
		
		for (int i = 0; i < poolSize; i++)
		{
			// 创建一个连接
			ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port));

			// 等待连接创建结果
			Channel channel = future.awaitUninterruptibly().getChannel();
			if (future.isSuccess())
			{
				//切换默认DB
				selectDB(channel, indexDB);
				
				//加入池
				try
				{
					numberOfCreateSucess++;
					pool.put(channel);
				} catch (InterruptedException e)
				{
					numberOfCreateSucess--;
					e.printStackTrace();
					break;
				}
			}
			else
			{
				future.getCause().printStackTrace();
				break;
			}
		}
		
		if (numberOfCreateSucess == poolSize)
		{
			System.out.printf("create %d connector in pool\n", poolSize);
			return true;
		}
		else
		{
			try
			{
				throw new RedisConnectionPoolException();
			}
			catch (RedisConnectionPoolException e)
			{
				e.printStackTrace();
			}
			finally
			{
				disconnect();
				return false;
			}
		}
	}
	
	private void selectDB(Channel conn, int indexDB)
	{
		Object[] command = new Object[2];
		command[0] = RedisCommandType.SELECT;
		command[1] = indexDB;
		conn.getPipeline().get(RedislHandler.class).sendRequest(RedisCommandEncoder.getBinaryCommand(command));
	}
	
	public Object[] executeCommand(String commandType, Object...args)
	{
		Object[] command = new Object[args.length + 1];
		command[0] = commandType;
		for(int i=1; i<command.length; i++)
		{
			command[i] = args[i-1];
		}
		
		Object [] result = null;
		Channel conn = null;
		
		try
		{
			conn = pool.take();
			result = conn.getPipeline().get(RedislHandler.class).sendRequest(RedisCommandEncoder.getBinaryCommand(command));
			pool.put(conn);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		return result;
	}
	
	public synchronized void disconnect()
	{
		if (!isClose.getAndSet(true))
		{
			if(numberOfCreateSucess == poolSize && pool.size() != poolSize)
			{
				System.out.printf("there are %d connection leak!\n", poolSize - pool.size());
			}
			for(Channel channel: pool)
			{
				channel.close().awaitUninterruptibly();
			}
			System.out.printf("there is %d connection closed", pool.size());
			//TODO pool中的连接可能被分配出去还没回来,需要再考虑下
			pool.clear();
		}
		
		bootstrap.releaseExternalResources();
	}
}
