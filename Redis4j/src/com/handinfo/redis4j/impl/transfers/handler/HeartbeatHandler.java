package com.handinfo.redis4j.impl.transfers.handler;

import java.util.logging.Logger;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.handler.timeout.IdleState;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler;
import org.jboss.netty.handler.timeout.IdleStateEvent;

import com.handinfo.redis4j.api.ISession;
import com.handinfo.redis4j.api.RedisCommand;
import com.handinfo.redis4j.impl.util.Log;

public class HeartbeatHandler extends IdleStateAwareChannelHandler
{
	private final Logger logger = (new Log(HeartbeatHandler.class.getName())).getLogger();
	private ISession session;

	public HeartbeatHandler(ISession session)
	{
		super();
		this.session = session;
	}

	@Override
	public void channelIdle(ChannelHandlerContext ctx, IdleStateEvent e) throws Exception
	{
		if (e.getState() == IdleState.WRITER_IDLE)
		{
			double idelTime = (double) (System.currentTimeMillis() - e.getLastActivityTimeMillis()) / 1000;
			logger.info("Idel " + idelTime + "s,send PING...");
			try
			{
				this.session.executeCommand(RedisCommand.PING);
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
}
