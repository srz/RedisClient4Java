package com.handinfo.redis4j.impl.transfers;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import com.handinfo.redis4j.api.RedisCommandType;
import com.handinfo.redis4j.api.RedisResultInfo;
import com.handinfo.redis4j.impl.protocol.RedislHandler;
import com.handinfo.redis4j.impl.protocol.encode.RedisCommandEncoder;

public class Connector
{
	private ClientBootstrap bootstrap;
	private Channel channel;
	
	public boolean connect(String host, int port)
	{
		// 配置启动参数
		bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));

		// 设置连接使用的管道参数
		bootstrap.setPipelineFactory(new PipelineFactory());

		// 创建一个连接
		ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port));

		// 等待连接创建结果
		channel = future.awaitUninterruptibly().getChannel();
		if (!future.isSuccess())
		{
			future.getCause().printStackTrace();
			bootstrap.releaseExternalResources();
			return false;
		}
		return true;
	}
	
	public void selectDB(int indexDB)
	{
		executeCommand(RedisCommandType.SELECT, RedisResultInfo.OK, indexDB);
	}
	
	public Object[] executeCommand(String commandType, Object...args)
	{
		//System.out.println( String.valueOf(this.hashCode())+"--"+ Thread.currentThread().getName()+"--"+  commandType+"--");
		
		Object[] command = new Object[args.length + 1];
		command[0] = commandType;
		for(int i=1; i<command.length; i++)
		{
			command[i] = args[i-1];
		}

		return channel.getPipeline().get(RedislHandler.class).sendRequest(RedisCommandEncoder.getBinaryCommand(command));
	}
	
	public boolean disconnect()
	{
		channel.close().awaitUninterruptibly();
		bootstrap.releaseExternalResources();
		return true;
	}
}
