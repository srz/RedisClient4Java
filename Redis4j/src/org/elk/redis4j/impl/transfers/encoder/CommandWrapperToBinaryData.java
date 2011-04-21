package org.elk.redis4j.impl.transfers.encoder;

import org.elk.redis4j.impl.util.CommandWrapper;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;


public class CommandWrapperToBinaryData extends OneToOneEncoder
{
	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception
	{
		CommandWrapper cmd = (CommandWrapper)msg;
		return cmd.getValue();
	}

}
