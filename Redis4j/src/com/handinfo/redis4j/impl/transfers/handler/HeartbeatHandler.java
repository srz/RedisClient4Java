package com.handinfo.redis4j.impl.transfers.handler;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.handler.timeout.IdleState;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler;
import org.jboss.netty.handler.timeout.IdleStateEvent;

import com.handinfo.redis4j.api.IConnector;
import com.handinfo.redis4j.api.RedisCommand;

public class HeartbeatHandler extends IdleStateAwareChannelHandler
{
	private static final Logger logger = Logger.getLogger(HeartbeatHandler.class.getName());
	private IConnector connector;

	public HeartbeatHandler(IConnector connector)
	{
		super();
		this.connector = connector;
	}

	@Override
	public void channelIdle(ChannelHandlerContext ctx, IdleStateEvent e) throws Exception
	{
		if (e.getState() == IdleState.WRITER_IDLE)
		{
			double idelTime = (double) (System.currentTimeMillis() - e.getLastActivityTimeMillis()) / 1000;
			printMsg(Level.INFO, "Idel " + idelTime + "s,send PING...");
			try
			{
				this.connector.executeCommand(RedisCommand.PING);
			}
			catch (Exception ex)
			{
			}
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception
	{
		super.exceptionCaught(ctx, e);
	}

	private void printMsg(Level level, String msg)
	{
		logger.log(level, "Thread name:" + Thread.currentThread().getName() + " - ID:" + Thread.currentThread().getId() + " - " + msg);
	}
}
