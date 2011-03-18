package com.handinfo.redis4j.impl.protocol;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.WriteCompletionEvent;

import com.handinfo.redis4j.impl.transfers.Connector;

public class RedislHandler extends SimpleChannelUpstreamHandler
{

	private static final Logger logger = Logger.getLogger(RedislHandler.class.getName());

	private volatile Channel channel;
	private final BlockingQueue<Object[]> answer = new LinkedBlockingQueue<Object[]>();

	/**
	 * @param connector
	 */
	public RedislHandler()
	{
		super();
	}

	public Object[] syncSendRequest(ChannelBuffer command)
	{
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		buffer.writeBytes(command);

		channel.write(buffer).awaitUninterruptibly();

		Object[] result = null;
		boolean interrupted = false;
		try
		{
			result = answer.take();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		return result;
	}
	
	public BlockingQueue<Object[]> asyncSendRequest(ChannelBuffer command)
	{
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		buffer.writeBytes(command);

		channel.write(buffer).awaitUninterruptibly();
		
		return answer;
	}

	@Override
	public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception
	{
		if (e instanceof ChannelStateEvent)
		{
			// logger.info(e.toString());
		}
		super.handleUpstream(ctx, e);
	}

	@Override
	public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception
	{
		channel = e.getChannel();
		super.channelOpen(ctx, e);
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
	{
		answer.offer((Object[]) e.getMessage());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
	{
		logger.log(Level.WARNING, "Unexpected exception from downstream.", e.getCause());
		//e.getChannel().close();
	}

	/* (non-Javadoc)
	 * @see org.jboss.netty.channel.SimpleChannelUpstreamHandler#channelClosed(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ChannelStateEvent)
	 */
	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception
	{
		
	}

	/* (non-Javadoc)
	 * @see org.jboss.netty.channel.SimpleChannelUpstreamHandler#channelDisconnected(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ChannelStateEvent)
	 */
	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception
	{
		answer.offer(new Object[]{null});
	}
}