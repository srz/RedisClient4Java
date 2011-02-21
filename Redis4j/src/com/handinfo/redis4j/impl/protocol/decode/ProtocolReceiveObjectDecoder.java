package com.handinfo.redis4j.impl.protocol.decode;

import java.nio.charset.Charset;
import java.util.logging.Logger;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBufferIndexFinder;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneDecoder;

public class ProtocolReceiveObjectDecoder extends OneToOneDecoder
{
	private static final Logger logger = Logger.getLogger(ProtocolReceiveObjectDecoder.class.getName());

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception
	{
		if (!(msg instanceof ChannelBuffer))
		{
			return msg;
		}

		ChannelBuffer binaryData = (ChannelBuffer) msg;

		// 第一个字节内容
		char firstByte = (char) binaryData.getByte(0);

		// 第一个 \r的索引位置
		int firstIndexCR = binaryData.bytesBefore(ChannelBufferIndexFinder.CR);

		// 第一个\n的索引位置
		int firstIndexLF = binaryData.bytesBefore(ChannelBufferIndexFinder.LF);

		// 头中的内容
		String header = binaryData.toString(1, firstIndexCR - 1, Charset.forName("UTF-8"));

		// 头中描述的内容长度(如果是数字)
		int contentLength = -2;

		try
		{
			contentLength = Integer.parseInt(header);
		} catch (Exception e)
		{
			logger.info("返回的结果头中内容不是数字");
		}

		// ChannelBuffer result = ChannelBuffers.dynamicBuffer();

		logger.info("转换消息==>接收frame数据并转化为java pojo");

		switch (firstByte)
		{
		case '+':
		{
			// With a single line reply the first byte of the reply
			// will be "+"
			return binaryData.toString(1, binaryData.readableBytes() - 3, Charset.forName("UTF-8"));
		}
			// break;
		case '-':
		{
			// With an error message the first byte of the reply
			// will be "-"
			return msg;
		}

			// break;
		case ':':
		{
			// With an integer number the first byte of the reply
			// will be ":"
			return msg;
		}
			// break;
		case '$':
		{
			// With bulk reply the first byte of the reply will be
			// "$"

			if (contentLength == -1)
			{
				return "";
			} else
			{
				//TODO 未考虑返回结果为二进制的情况
				return binaryData.toString(firstIndexLF + 1, binaryData.readableBytes() - firstIndexLF - 3, Charset.forName("UTF-8"));
			}
		}
			// break;
		case '*':
		{
			// With multi-bulk reply the first byte of the reply
			// will be "*"
			return msg;
		}
			// break;
		default:
			return msg;
			// break;
		}

		// return result;
	}

}
