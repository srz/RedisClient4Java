package com.handinfo.redis4j.impl.transfers.handler;

import java.net.ConnectException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.util.Timeout;
import org.jboss.netty.util.Timer;
import org.jboss.netty.util.TimerTask;

import com.handinfo.redis4j.api.IConnector;

public class ReconnectNetworkHandler extends SimpleChannelUpstreamHandler
{
	private static final Logger logger = Logger.getLogger(ReconnectNetworkHandler.class.getName());
	private final Timer timer;
	private long startTime = -1;
	private IConnector connector;

	public ReconnectNetworkHandler(IConnector connector, Timer timer)
	{
		this.connector = connector;
		this.timer = timer;
	}

	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e)
	{
		printMsg(Level.WARNING, "Disconnected from: " + connector.getRemoteAddress() + " RUN TIME: " + ((System.currentTimeMillis() - startTime) / 1000) + "s");
		
	}

	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e)
	{
		this.connector.cleanCommandQueue();
		if (!connector.getIsStartClose())
		{
			printMsg(Level.WARNING, "After " + connector.getReconnectDelay() + "s to reconnect...");
			timer.newTimeout(new TimerTask()
			{
				public void run(Timeout timeout) throws Exception
				{
					printMsg(Level.INFO, "Reconnecting to: " + connector.getRemoteAddress());

					try
					{
						connector.connect();
					}
					catch (Exception ex)
					{
					}
				}
			}, connector.getReconnectDelay(), TimeUnit.SECONDS);
		}
	}

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
	{
		if (startTime < 0)
		{
			startTime = System.currentTimeMillis();
		}

		printMsg(Level.INFO, "Connected to: " + connector.getRemoteAddress());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
	{
		Throwable cause = e.getCause();
		if (cause instanceof ConnectException)
		{
			startTime = -1;
			printMsg(Level.WARNING, "Failed to connect: " + connector.getRemoteAddress() + " Error message : " + cause.getMessage());
			ctx.getChannel().close();
		} else
		{
			ctx.sendDownstream(e);
		}
	}

	private void printMsg(Level level, String msg)
	{
		logger.log(level, "Thread name:" + Thread.currentThread().getName() + " - ID:" + Thread.currentThread().getId() + " - " + msg);
	}
}
