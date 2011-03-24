package com.handinfo.redis4j.impl.transfers.decoder;

import java.nio.charset.Charset;
import java.util.logging.Logger;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBufferIndexFinder;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneDecoder;

import com.handinfo.redis4j.api.RedisResultType;

public class FrameToObjectArray extends OneToOneDecoder
{
	private static final Logger logger = Logger.getLogger(FrameToObjectArray.class.getName());

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

		// logger.info("received message,first byte is \"" + firstByte + "\"");

		Object[] result = null;
		switch (firstByte)
		{
		case RedisResultType.SingleLineReply:
		{
			// With a single line reply the first byte of the reply
			// will be "+"
			result = new Object[2];
			result[0] = firstByte;
			// 返回结果为+开头时,后面跟的一定是单行文本
			result[1] = header;
			return result;
		}
		case RedisResultType.ErrorReply:
		{
			// With an error message the first byte of the reply
			// will be "-"
			result = new Object[2];
			result[0] = firstByte;
			// 返回结果为-开头时,后面跟的一定是单行文本
			result[1] = header;
			return result;
		}
		case RedisResultType.IntegerReply:
		{
			// With an integer number the first byte of the reply
			// will be ":"
			result = new Object[2];
			result[0] = firstByte;
			result[1] = header;// binaryData.toString(1,
			// binaryData.readableBytes() - 3,
			// Charset.forName("UTF-8"));
			return result;
		}
		case RedisResultType.BulkReplies:
		{
			// With bulk reply the first byte of the reply will be
			// "$"

			// 头中描述的内容长度
			int lengthFiledOfHead = Integer.valueOf(header);

			result = new Object[2];
			result[0] = firstByte;
			if (lengthFiledOfHead == -1)
			{
				result[1] = null;
			} else
			{
				// 返回结果为$开头时,返回后面的bytes
				result[1] = binaryData.copy(firstIndexLF + 1, lengthFiledOfHead).array();
			}
			return result;
		}
		case RedisResultType.MultiBulkReplies:
		{
			// With multi-bulk reply the first byte of the reply
			// will be "*"

			// 头中描述的元素个数
			int lengthFiledOfHead = Integer.valueOf(header);

			if (lengthFiledOfHead == -1)
			{
				result = new Object[1];
				result[0] = firstByte;
			} else if (lengthFiledOfHead == 0)
			{
				result = new Object[2];
				result[0] = firstByte;
				result[1] = null;
			} else
			{
				result = new Object[lengthFiledOfHead + 1];
				result[0] = firstByte;

				int indexOfDelimiter = 0;
				int indexOfCR = 0;
				int indexOfLF = 0;
				int resultIndex = 1;

				// 遍历顺序取出元素
				for (int i = firstIndexLF + 1; i < binaryData.readableBytes();)
				{
					if (binaryData.getByte(i) == '$')
					{
						indexOfDelimiter = i;
					}

					if (binaryData.getByte(i) == '\r')
					{
						indexOfCR = i;
					}
					if (binaryData.getByte(i) == '\n')
					{
						indexOfLF = i;
					}
					if (indexOfCR + 1 == indexOfLF)
					{
						int dataLength = Integer.valueOf(binaryData.toString(indexOfDelimiter + 1, indexOfCR - indexOfDelimiter - 1, Charset.forName("UTF-8")));
						if (dataLength == 0)
						{
							result[resultIndex++] = null;
							i++;
						} else
						{
							result[resultIndex++] = binaryData.copy(indexOfLF + 1, dataLength).array();
						}

						i = i + dataLength + 3;
						indexOfCR = 0;
						indexOfLF = 0;
					} else
					{
						i++;
					}
				}
			}

			return result;
		}
		default:
			return null;
		}
	}
}
