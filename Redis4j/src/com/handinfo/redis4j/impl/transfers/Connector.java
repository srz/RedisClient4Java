package com.handinfo.redis4j.impl.transfers;

import java.net.InetSocketAddress;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import com.handinfo.redis4j.api.Command;
import com.handinfo.redis4j.api.RedisCommandType;

public class Connector
{
	private static final Logger logger = Logger.getLogger(Connector.class.getName());

	private ClientBootstrap bootstrap;
	private ChannelGroup channelGroup;
	private InetSocketAddress hostAddress;
	private int defaultIndexDB;
	private Channel channel;
	private final BlockingQueue<Command> commandOfWriteFinish = new LinkedBlockingQueue<Command>();
	
	/**
	 * TODO 连接池考虑后面再增加,经过试用xmemcached客户度连接池,效果还不如没有时好
	 **/
	public Connector(String host, int port, int poolMaxSize, int indexDB) throws Exception
	{
		if (!checkIpAndPort(host, port))
		{
			throw new RedisConnectionPoolException("create connection pool failed, please check host or port");
		}

		this.hostAddress = new InetSocketAddress(host.trim(), port);
		this.defaultIndexDB = indexDB;
		this.channel = null;
		
		// 配置启动参数
		this.bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));

		// 设置连接使用的管道参数
		this.bootstrap.setPipelineFactory(new PipelineFactory(this));

		// 设置ChannelGroup
		this.channelGroup = new DefaultChannelGroup();
	}
	
	public boolean connect() throws Exception
	{
		if(this.channel == null || !this.channel.isConnected())
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
		}
		else
			return false;
	}

	public BlockingQueue<Command> getWriteFinishCommandQueue() throws InterruptedException
	{
		return commandOfWriteFinish;
	}

	private void selectDB(int indexDB) throws Exception
	{
		executeCommand(RedisCommandType.SELECT, indexDB);
	}

	public Object[] executeCommand(String commandType, Object... args) throws Exception
	{
		Object[] redisCommand = new Object[args.length + 1];
		redisCommand[0] = commandType;
		System.arraycopy(args, 0, redisCommand, 1, args.length);

		final Command cmd = new Command(redisCommand);

		synchronized (channel)
		{
			if (channel.isConnected())
			{
				ChannelFuture future = channel.write(cmd);

				future.addListener(new ChannelFutureListener()
				{
					@Override
					public void operationComplete(ChannelFuture future) throws Exception
					{
						if (channel.isConnected())
							commandOfWriteFinish.put(cmd);
					}
				});
			}
		}
		// 等待返回结果
		if (channel.isConnected())
			cmd.pause();
		else
			throw new Exception("connection has been disconnected");
		
		Object[] result = null;
		if(cmd.getException() != null)
		{
			throw cmd.getException();
		}
		else
		{
			result = cmd.getResult();
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

	public void disConnect()
	{
		channel.close().awaitUninterruptibly();
		bootstrap.releaseExternalResources();
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
