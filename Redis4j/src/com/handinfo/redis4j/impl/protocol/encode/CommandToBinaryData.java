package com.handinfo.redis4j.impl.protocol.encode;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import com.handinfo.redis4j.api.Command;

public class CommandToBinaryData extends OneToOneEncoder
{
	private static final Logger logger = Logger.getLogger(CommandToBinaryData.class.getName());
	
	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception
	{
		Command cmd = (Command)msg;
		return cmd.getValue();
	}

}
