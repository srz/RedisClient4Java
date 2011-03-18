package com.handinfo.redis4j.impl.protocol;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.handler.timeout.IdleState;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler;
import org.jboss.netty.handler.timeout.IdleStateEvent;

public class Idelhandler extends IdleStateAwareChannelHandler
{
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler#channelIdle
	 * (org.jboss.netty.channel.ChannelHandlerContext,
	 * org.jboss.netty.handler.timeout.IdleStateEvent)
	 */
	@Override
	public void channelIdle(ChannelHandlerContext ctx, IdleStateEvent e) throws Exception
	{
		if (e.getState() == IdleState.WRITER_IDLE)
		{
			System.out.println("idel=" + e.getLastActivityTimeMillis());
			// e.getChannel().write();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jboss.netty.channel.SimpleChannelHandler#exceptionCaught(org.jboss
	 * .netty.channel.ChannelHandlerContext,
	 * org.jboss.netty.channel.ExceptionEvent)
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception
	{
		// TODO Auto-generated method stub
		super.exceptionCaught(ctx, e);
	}

}
