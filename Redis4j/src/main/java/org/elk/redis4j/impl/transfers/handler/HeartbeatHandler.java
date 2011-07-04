package org.elk.redis4j.impl.transfers.handler;

import java.util.logging.Logger;

import org.elk.redis4j.api.ISession;
import org.elk.redis4j.api.RedisCommand;
import org.elk.redis4j.impl.util.LogUtil;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.handler.timeout.IdleState;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler;
import org.jboss.netty.handler.timeout.IdleStateEvent;


public class HeartbeatHandler extends IdleStateAwareChannelHandler
{
	private final Logger logger = LogUtil.getLogger(HeartbeatHandler.class.getName());
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
