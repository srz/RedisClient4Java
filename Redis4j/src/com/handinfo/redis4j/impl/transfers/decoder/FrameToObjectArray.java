package com.handinfo.redis4j.impl.transfers.decoder;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneDecoder;

import com.handinfo.redis4j.api.RedisResponse;
import com.handinfo.redis4j.api.RedisResponseType;

public class FrameToObjectArray extends OneToOneDecoder
{
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

		// 第一个\r\n中的\n的索引位置
		int firstIndexLF = findFirstCRLFIndex(binaryData, binaryData.readerIndex());

		// 头中的内容
		String header = binaryData.toString(1, firstIndexLF - 2, Charset.forName("UTF-8"));

		RedisResponse response = null;

		switch (RedisResponseType.fromChar(firstByte))
		{
		case SingleLineReply:
			{
				/**
				 * With a single line reply the first byte of the reply will be "+"
				 */
				response = new RedisResponse(RedisResponseType.SingleLineReply);
				response.setTextValue(header);
				return response;
			}
		case ErrorReply:
			{
				/**
				 * With an error message the first byte of the reply will be "-"
				 */
				response = new RedisResponse(RedisResponseType.ErrorReply);
				response.setTextValue(header);
				return response;
			}
		case IntegerReply:
			{
				/**
				 * With an integer number the first byte of the reply will be ":"
				 */
				response = new RedisResponse(RedisResponseType.IntegerReply);
				response.setTextValue(header);
				return response;
			}
		case BulkReplies:
			{
				/**
				 * With bulk reply the first byte of the reply will be "$"
				 */
				// 头中描述的内容长度
				int lengthFiledOfHead = Integer.valueOf(header);

				response = new RedisResponse(RedisResponseType.BulkReplies);
				if (lengthFiledOfHead == -1)
				{
					response.setBulkValue(null);
				} else
				{
					// 返回后面的byte array
					byte[] byteValue = new byte[lengthFiledOfHead];
					binaryData.getBytes(firstIndexLF + 1, byteValue, 0, lengthFiledOfHead);
					response.setBulkValue(byteValue);
				}
				return response;// result;
			}
		case MultiBulkReplies:
			{
				/**
				 * With multi-bulk reply the first byte of the reply will be "*"
				 */

				// 头中描述的元素个数
				int lengthFiledOfHead = Integer.valueOf(header);

				response = new RedisResponse(RedisResponseType.MultiBulkReplies);
				switch (lengthFiledOfHead)
				{
				case -1:
					{
						response.setMultiBulkValue(null);
						break;
					}
				case 0:
					{
						response.setMultiBulkValue(new ArrayList<RedisResponse>());
						break;
					}
				default:
					{
						List<RedisResponse> responseList = new ArrayList<RedisResponse>();
						int objectTotal = buildMultiBulkResponse(binaryData, binaryData.readerIndex(), responseList);
						switch (objectTotal)
						{
						case -1:
							response.setMultiBulkValue(null);
							break;
						case 0:
							response.setMultiBulkValue(responseList);
							break;
						default:
							response.setMultiBulkValue(responseList);
							break;
						}
						break;
					}
				}

				return response;
			}
		default:
			return null;
		}
	}

	private int buildMultiBulkResponse(ChannelBuffer buffer, int currentIndex, List<RedisResponse> responseList)
	{
		int firstIndexLF = findFirstCRLFIndex(buffer, currentIndex);
		if (firstIndexLF == -1)
		{
			return -1;
		}

		int objectTotal = Integer.valueOf(buffer.toString(currentIndex + 1, firstIndexLF - 2 - currentIndex, Charset.forName("UTF-8")));
		if (objectTotal <= 0)
			return objectTotal;

		int currentObject = 0;
		int byteIndex = firstIndexLF;
		int objectFirstIndex = 0;
		while (true)
		{
			// 游标+1
			byteIndex++;

			if (byteIndex >= buffer.writerIndex())
			{
				return -1;
			}

			objectFirstIndex = byteIndex;

			// 获取新游标位置的字符，如果是5种数据约定的开头字符，则记录
			switch (RedisResponseType.fromChar((char) buffer.getByte(byteIndex)))
			{
			case SingleLineReply:
				{
					byteIndex = findFirstCRLFIndex(buffer, byteIndex);
					if (byteIndex == -1)
					{
						return -1;
					}
					currentObject++;
					RedisResponse response = new RedisResponse(RedisResponseType.SingleLineReply);
					response.setTextValue(buffer.toString(objectFirstIndex + 1, byteIndex - objectFirstIndex - 2, Charset.forName("UTF-8")));
					responseList.add(response);
					break;
				}
			case ErrorReply:
				{
					byteIndex = findFirstCRLFIndex(buffer, byteIndex);
					if (byteIndex == -1)
					{
						return -1;
					}
					currentObject++;
					RedisResponse response = new RedisResponse(RedisResponseType.ErrorReply);
					response.setTextValue(buffer.toString(objectFirstIndex + 1, byteIndex - objectFirstIndex - 2, Charset.forName("UTF-8")));
					responseList.add(response);
					break;
				}
			case IntegerReply:
				{
					byteIndex = findFirstCRLFIndex(buffer, byteIndex);
					if (byteIndex == -1)
					{
						return -1;
					}
					currentObject++;
					RedisResponse response = new RedisResponse(RedisResponseType.IntegerReply);
					response.setTextValue(buffer.toString(objectFirstIndex + 1, byteIndex - objectFirstIndex - 2, Charset.forName("UTF-8")));
					responseList.add(response);
					break;
				}
			case BulkReplies:
				{
					byteIndex = addBulkResponse(buffer, byteIndex, responseList);
					if (byteIndex == -1)
					{
						return -1;
					}
					currentObject++;
					break;
				}
			case MultiBulkReplies:
				{
					RedisResponse response = new RedisResponse(RedisResponseType.MultiBulkReplies);
					response.setMultiBulkValue(new ArrayList<RedisResponse>());
					// 递归计算
					byteIndex = buildMultiBulkResponse(buffer, byteIndex, response.getMultiBulkValue());
					responseList.add(response);
					if (byteIndex == -1)
					{
						return -1;
					}
					currentObject++;
					break;
				}
			default:
				break;
			}

			// 获取完全部对象时退出
			if (currentObject == objectTotal)
				break;
		}

		return byteIndex;
	}

	private int addBulkResponse(ChannelBuffer buffer, int currentIndex, List<RedisResponse> responseList)
	{
		int firstIndexLF = findFirstCRLFIndex(buffer, currentIndex);
		if (firstIndexLF == -1)
		{
			return -1;
		}

		int contentLength = Integer.valueOf(buffer.toString(currentIndex + 1, firstIndexLF - 2 - currentIndex, Charset.forName("UTF-8")));

		if (contentLength == -1)
		{
			RedisResponse response = new RedisResponse(RedisResponseType.BulkReplies);
			response.setBulkValue(null);
			responseList.add(response);
			// 返回的头内容为 -1，说明无后续数据
			return firstIndexLF;
		} else
		{
			if (firstIndexLF + contentLength + 2 >= buffer.writerIndex())
			{
				return -1;
			} else
			{
				RedisResponse response = new RedisResponse(RedisResponseType.BulkReplies);

				byte[] byteValue = new byte[contentLength];
				buffer.getBytes(firstIndexLF + 1, byteValue, 0, contentLength);

				response.setBulkValue(byteValue);
				responseList.add(response);
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
