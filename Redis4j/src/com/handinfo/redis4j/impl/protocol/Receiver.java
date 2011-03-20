package com.handinfo.redis4j.impl.protocol;

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

import com.handinfo.redis4j.api.Command;
import com.handinfo.redis4j.impl.transfers.Connector;

public class Receiver extends SimpleChannelUpstreamHandler
{

	private static final Logger logger = Logger.getLogger(Receiver.class.getName());
	private Connector connector;

	/**
	 * @param connector
	 */
	public Receiver(Connector connector)
	{
		super();
		this.connector = connector;
	}
	
	public BlockingQueue<Object[]> asyncSendRequest(ChannelBuffer command)
	{
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		buffer.writeBytes(command);

		//channel.write(buffer).awaitUninterruptibly();
		
		return null;
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
	{
		Command cmd = null;
		try
		{
			cmd = this.connector.getWriteFinishCommandQueue().take();
		} catch (InterruptedException ex)
		{
			ex.printStackTrace();
			Thread.currentThread().interrupt();
		}
		if(cmd != null)
		{
			cmd.setResult((Object[]) e.getMessage());
			cmd.resume();
		}
		else
		{
			logger.log(Level.WARNING, "if you found this,please tell me,this is a bug!");
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
	{
		logger.log(Level.WARNING, "Unexpected exception from downstream.", e.getCause());
		
		Command cmd = null;
		try
		{
			cmd = this.connector.getWriteFinishCommandQueue().take();
		} catch (InterruptedException ex)
		{
			ex.printStackTrace();
			Thread.currentThread().interrupt();
		}
		if(cmd != null)
		{
			cmd.setException((Exception) e.getCause());
			cmd.resume();
		}
		else
		{
			logger.log(Level.WARNING, "if you found this,please tell me,this is a bug!");
		}
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
		Iterator<Command> task= this.connector.getWriteFinishCommandQueue().iterator();
		while(task.hasNext())
		{
			Command cmd = task.next();
			task.next().setException(new Exception("connection has been disconnected"));
			cmd.resume();
		}
		this.connector.getWriteFinishCommandQueue().clear();
	}
}