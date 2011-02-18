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

		logger.info("转换消息==>符合 redis 协议的格式");

		ChannelBuffer buff = ChannelBuffers.dynamicBuffer();
		buff.writeBytes(Ping.getProtocol());
		// *2\r\n$4\r\necho\r\n$6\r\ntessrz\r\n
//		buff.writeBytes(new byte[]
//		{ '*', '2', '\\', 'r', '\\', 'n', '$', '4', '\\', 'r', '\\', 'n', 'e', 'c', 'h', 'o', '\\', 'r', '\\', 'n', '$', '2', '\\', 'r', '\\', 'n', 's', 'r', '\\', 'r', '\\', 'n' });

		System.out.println("---------+++++---------");
		
		for (int i = 0; i < buff.capacity(); i++)
		{
			byte b = buff.getByte(i);
			System.out.print((char) b);

		}
		
		System.out.println("------------------");
		
		System.out.println(buff.readableBytes());

		return buff;
	}

}
