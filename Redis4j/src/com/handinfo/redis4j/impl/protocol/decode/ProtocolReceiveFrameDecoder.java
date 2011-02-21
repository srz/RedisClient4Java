package com.handinfo.redis4j.impl.protocol.decode;

import java.nio.charset.Charset;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBufferIndexFinder;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

public class ProtocolReceiveFrameDecoder extends FrameDecoder
{

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception
	{
		System.out.println("ProtocolReceiveFrameDecoder.decode");
		// 确保收到第一个字节.
		if (buffer.readableBytes() < 1)
		{
			return null;
		}

		buffer.markReaderIndex();

		// Read the first byte.
		char firstByte = (char) buffer.getByte(0);
		switch (firstByte)
		{
		case '+':
		{
			// With a single line reply the first byte of the reply
			// will be "+"
			int firstIndexCR = buffer.bytesBefore(ChannelBufferIndexFinder.CR);
			int firstIndexLF = buffer.bytesBefore(ChannelBufferIndexFinder.LF);

			if(firstIndexLF > 0 && firstIndexLF == firstIndexCR+1)
			{
				//第一个\r\n之前的数据(包括+号和\r\n)
				return buffer.readBytes(firstIndexLF+1);
			}
			else
			{
				return null;
			}
			
		}
			//break;
		case '-':
		{
			// With an error message the first byte of the reply
			// will be "-"
			return null;
		}

			//break;
		case ':':
		{
			// With an integer number the first byte of the reply
			// will be ":"
			return null;
		}
			//break;
		case '$':
		{
			// With bulk reply the first byte of the reply will be
			// "$"
			
			int firstIndexCR = buffer.bytesBefore(ChannelBufferIndexFinder.CR);
			int firstIndexLF = buffer.bytesBefore(ChannelBufferIndexFinder.LF);
			
			if(firstIndexLF > 0 && firstIndexLF == firstIndexCR+1)
			{
				int contentLength = Integer.valueOf(buffer.toString(1, firstIndexCR-1, Charset.forName("UTF-8")));
				if(contentLength == -1)
				{
					//返回的头内容为 -1
					return buffer.readBytes(firstIndexLF+1);
				}
				else
				{
					//返回正确的数据
					int frameLength = firstIndexLF+1+contentLength+2;
					if(buffer.readableBytes() < frameLength)
					{
						//数据不完整时
						return null;
					}
					else
					{
						return buffer.readBytes(frameLength);
					}
				}
			}
			else
			{
				return null;
			}
		}
		case '*':
		{
			// With multi-bulk reply the first byte of the reply
			// will be "*"
			return null;
		}
			//break;
		default:
			return null;
			//break;
		}
	}
}
