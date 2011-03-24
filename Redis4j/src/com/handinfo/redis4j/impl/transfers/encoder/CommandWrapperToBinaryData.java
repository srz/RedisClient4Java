package com.handinfo.redis4j.impl.transfers.encoder;

import java.util.logging.Logger;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import com.handinfo.redis4j.impl.util.CommandWrapper;

public class CommandWrapperToBinaryData extends OneToOneEncoder
{
	private static final Logger logger = Logger.getLogger(CommandWrapperToBinaryData.class.getName());
	
	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception
	{
		CommandWrapper cmd = (CommandWrapper)msg;
		return cmd.getValue();
	}

}
