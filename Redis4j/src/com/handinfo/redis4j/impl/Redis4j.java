package com.handinfo.redis4j.impl;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import com.handinfo.redis4j.api.IRedis4j;
import com.handinfo.redis4j.impl.protocol.Ping;

public class Redis4j implements IRedis4j
{
	private String host;
	private int port;
	private ClientBootstrap bootstrap;
	private Channel channel;
	
	public Redis4j(String host, int port)
	{
		this.host = host;
		this.port = port;
	}

	@Override
	public boolean auth(String password)
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String echo(String message)
	{
		// TODO Auto-generated method stub
		return "xx";
	}

	@Override
	public boolean ping()
	{
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		boolean b = channel.write(buffer).awaitUninterruptibly().isSuccess();
		System.out.println(b);

		//channel.getPipeline().get(RedislHandler.class);
		return true;
	}

	//
	@Override
	public boolean quit()
	{
		// Close the connection. Make sure the close operation ends
		// because
		// all I/O operations are asynchronous in Netty.
		channel.close().awaitUninterruptibly();

		// Shut down all thread pools to exit.
		bootstrap.releaseExternalResources();
		return true;
	}

	@Override
	public boolean select(int dbIndex)
	{
		// TODO Auto-generated method stub
		return true;
	}

	/*
	 * 创建到Redis服务器的连接，需要调用quit()函数关闭此连接
	 * */
	@Override
	public boolean connect()
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

}
