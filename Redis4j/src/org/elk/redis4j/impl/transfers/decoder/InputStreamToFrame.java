package org.elk.redis4j.impl.transfers.decoder;

import java.nio.charset.Charset;

import org.elk.redis4j.api.RedisResponseType;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBufferIndexFinder;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;


public class InputStreamToFrame extends FrameDecoder
{

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception
	{
		// 确保收到至少一个字节
		if (buffer.readableBytes() == 0)
			return null;

		// 取出第一个readerIndex位置的字节
		char firstByte = (char) buffer.getByte(buffer.readerIndex());
		
		// 确保第一个readerIndex位置的字节是协议中约定的
		RedisResponseType responseType = RedisResponseType.fromChar(firstByte);
		if(responseType == null)
		{
			// 如不是协议中约定的字符,放弃第一个字符并重新处理
			buffer.readByte();
			return null;
		}

		// 确保收到第一个\r\n.
		int firstIndexCR = buffer.bytesBefore(buffer.readerIndex(), buffer.readableBytes(), ChannelBufferIndexFinder.CR);
		int firstIndexLF = buffer.bytesBefore(buffer.readerIndex(), buffer.readableBytes(), ChannelBufferIndexFinder.LF);

		if (!(firstIndexLF > 0 && firstIndexLF == firstIndexCR + 1))
		{
			return null;
		}

		switch (responseType)
		{
		case SingleLineReply:
			{
				/**
				 * With a single line reply the first byte of the reply will be "+"
				 */
				return buffer.readBytes(firstIndexLF + 1);
			}
		case ErrorReply:
			{
				/**
				 * With an error message the first byte of the reply will be "-"
				 */
				return buffer.readBytes(firstIndexLF + 1);
			}
		case IntegerReply:
			{
				/**
				 * With an integer number the first byte of the reply will be ":"
				 */
				return buffer.readBytes(firstIndexLF + 1);
			}
		case BulkReplies:
			{
				/**
				 * With bulk reply the first byte of the reply will be "$"
				 */
				int contentLength = Integer.valueOf(buffer.toString(buffer.readerIndex() + 1, firstIndexCR - 1, Charset.forName("UTF-8")));
				if (contentLength == -1)
				{
					// 返回的头内容为 -1，说明无后续数据
					return buffer.readBytes(firstIndexLF + 1);
				} else
				{
					// 返回包含数据的包
					int frameLength = firstIndexLF + 1 + contentLength + 2;
					if (buffer.readableBytes() < frameLength)
					{
						// 数据不完整时
						return null;
					} else
					{
						return buffer.readBytes(frameLength);
					}
				}
			}
		case MultiBulkReplies:
			{
				/**
				 * With multi-bulk reply the first byte of the reply will be "*"
				 */
				int lastByteIndex = getMultiBulkLineLastIndex(buffer, buffer.readerIndex());
				if (lastByteIndex == -1)
					return null;
				else
				{
					return buffer.readBytes(lastByteIndex - buffer.readerIndex() + 1);
				}
			}
		default:
			return null;
		}
	}

	private int getMultiBulkLineLastIndex(ChannelBuffer buffer, int currentIndex)
	{
		int firstIndexLF = findFirstCRLFIndex(buffer, currentIndex);
		if (firstIndexLF == -1)
		{
			return -1;
		}

		int objectTotal = Integer.valueOf(buffer.toString(currentIndex + 1, firstIndexLF - 2 - currentIndex, Charset.forName("UTF-8")));

		if (objectTotal <= 0)
			return firstIndexLF;

		int currentObject = 0;
		int byteIndex = firstIndexLF;
		while (true)
		{
			// 游标+1
			byteIndex++;

			if (byteIndex >= buffer.writerIndex())
			{
				return -1;
			}

			// 获取新游标位置的字符，如果是5种数据约定的开头字符，则记录
			switch (RedisResponseType.fromChar((char) buffer.getByte(byteIndex)))
			{
			case SingleLineReply:
				{
					byteIndex = findFirstCRLFIndex(buffer, byteIndex);
					currentObject++;
					break;
				}
			case ErrorReply:
				{
					byteIndex = findFirstCRLFIndex(buffer, byteIndex);
					currentObject++;
					break;
				}
			case IntegerReply:
				{
					byteIndex = findFirstCRLFIndex(buffer, byteIndex);
					currentObject++;
					break;
				}
			case BulkReplies:
				{
					byteIndex = getBulkLineLastIndex(buffer, byteIndex);
					currentObject++;
					break;
				}
			case MultiBulkReplies:
				{
					// 递归计算
					byteIndex = getMultiBulkLineLastIndex(buffer, byteIndex);
					currentObject++;
					break;
				}
			default:
				break;
			}

			if (byteIndex == -1)
			{
				return -1;
			}

			// 获取完全部对象时退出
			if (currentObject == objectTotal)
				break;
		}

		return byteIndex;
	}

	private int getBulkLineLastIndex(ChannelBuffer buffer, int currentIndex)
	{
		int firstIndexLF = findFirstCRLFIndex(buffer, currentIndex);
		if (firstIndexLF == -1)
		{
			return -1;
		}

		int contentLength = Integer.valueOf(buffer.toString(currentIndex + 1, firstIndexLF - 2 - currentIndex, Charset.forName("UTF-8")));

		if (contentLength == -1)
		{
			// 返回的头内容为 -1，说明无后续数据
			return firstIndexLF;
		} else
		{
			if (firstIndexLF + contentLength + 2 >= buffer.writerIndex())
			{
				return -1;
			} else
			{
				return firstIndexLF + contentLength + 2;
			}
		}
	}

	private int findFirstCRLFIndex(ChannelBuffer buffer, int currentIndex)
	{
		int firstIndexCR = currentIndex;
		int firstIndexLF = currentIndex;
		while (true)
		{
			if (currentIndex >= buffer.writerIndex())
			{
				return -1;
			}
			if (buffer.getByte(currentIndex) == '\r')
			{
				firstIndexCR = currentIndex;
			}
			if (buffer.getByte(currentIndex) == '\n')
			{
				firstIndexLF = currentIndex;
			}
			currentIndex++;

			// \r\n挨着时结束
			if (firstIndexCR + 1 == firstIndexLF)
				break;
		}
		return firstIndexLF;
	}
}
