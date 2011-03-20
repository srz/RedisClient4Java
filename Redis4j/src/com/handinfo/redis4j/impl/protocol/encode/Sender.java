package com.handinfo.redis4j.impl.protocol.encode;

import java.util.logging.Logger;

import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelDownstreamHandler;

public class Sender extends SimpleChannelDownstreamHandler
{
	private static final Logger logger = Logger.getLogger(Sender.class.getName());
	
	@Override
	public void writeRequested(ChannelHandlerContext ctx, MessageEvent e) throws Exception
	{
		super.writeRequested(ctx, e);
		//logger.log(Level.INFO, "Sender.writeRequested="+ Thread.currentThread().getId());
	}

	@Override
	public void handleDownstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception
	{
		super.handleDownstream(ctx, e);
		//logger.log(Level.INFO, "Sender.handleDownstream="+ Thread.currentThread().getId());
	}
}
