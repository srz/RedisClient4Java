package com.handinfo.redis4j.impl;

import java.util.logging.Logger;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import com.handinfo.redis4j.impl.protocol.Ping;

public class ProtocolSendHandler extends OneToOneEncoder
{

	private static final Logger logger = Logger.getLogger(ProtocolSendHandler.class.getName());

	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception
	{
		if (!(msg instanceof ChannelBuffer))
		{
			return msg;
		}

		logger.info("开始编码==>将请求的命令转换为redis协议的格式");
		
		ChannelBuffer buff = ChannelBuffers.dynamicBuffer();
		buff.writeBytes(Ping.getProtocol());
		return buff;
	}

}
