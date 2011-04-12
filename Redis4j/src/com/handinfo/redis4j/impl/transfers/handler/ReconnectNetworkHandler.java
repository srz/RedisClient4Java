package com.handinfo.redis4j.impl.transfers.handler;

import java.net.ConnectException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.util.Timeout;
import org.jboss.netty.util.Timer;
import org.jboss.netty.util.TimerTask;

import com.handinfo.redis4j.api.ISession;
import com.handinfo.redis4j.impl.util.Log;

public class ReconnectNetworkHandler extends SimpleChannelUpstreamHandler
{
	private final Logger logger = (new Log(ReconnectNetworkHandler.class.getName())).getLogger();
	private final Timer timer;
	private long startTime = -1;
	private ISession session;
	

	public ReconnectNetworkHandler(ISession session, Timer timer)
	{
		this.session = session;
		this.timer = timer;
	}

	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e)
	{
		logger.warning("Disconnected from: " + session.getRemoteAddress() + " RUN TIME: " + ((System.currentTimeMillis() - startTime) / 1000) + "s");

	}

	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e)
	{
		this.session.cleanCommandQueue();
		if (!session.isStartClose())
		{
			logger.warning("After " + session.getReconnectDelay() + "s to reconnect...");
			timer.newTimeout(new TimerTask()
			{
				public void run(Timeout timeout) throws Exception
				{
					logger.info("Reconnecting to: " + session.getRemoteAddress());

					try
					{
						session.reConnect();
					}
					catch (Exception ex)
					{
						//ex.printStackTrace();
					}
				}
			}, session.getReconnectDelay(), TimeUnit.SECONDS);
		}
	}

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
	{
		if (startTime < 0)
		{
			startTime = System.currentTimeMillis();
		}

		logger.info("Connected to: " + ctx.getChannel().getRemoteAddress());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
	{
		Throwable cause = e.getCause();
		if (cause instanceof ConnectException)
		{
			startTime = -1;
			logger.warning("Failed to connect: " + session.getRemoteAddress() + " Error message : " + cause.getMessage());
			ctx.getChannel().close();
		} else
		{
			ctx.sendDownstream(e);
		}
	}
}
