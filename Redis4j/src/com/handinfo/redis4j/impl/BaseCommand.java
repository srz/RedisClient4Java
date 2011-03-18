package com.handinfo.redis4j.impl;

import java.util.concurrent.BlockingQueue;

import com.handinfo.redis4j.api.RedisResultType;
import com.handinfo.redis4j.impl.protocol.decode.ObjectDecoder;
import com.handinfo.redis4j.impl.transfers.Connector;

public class BaseCommand
{
	private Connector connector;
	private BlockingQueue<Object[]> answer;
	
	/**
	 * @param connector
	 */
	protected BaseCommand(Connector connector)
	{
		this.connector = connector;
	}

	/**
	 * 返回类型为状态码的命令统一执行此函数
	 * 
	 * @param redisCommandType
	 *            命令类型
	 * @param RedisResultInfo
	 *            返回结果的期望值,如不符合则认为操作失败
	 * @return 操作结果是否成功
	 */
	protected boolean singleLineReplyForBoolean(String redisCommandType, String RedisResultInfo, Object... args)
	{
		Object[] result = connector.executeCommand(redisCommandType, args);

		if (result != null && result.length > 1)
		{
			Character resultType = (Character) result[0];
			if (resultType == RedisResultType.SingleLineReply)
			{
				if (((String) result[1]).equalsIgnoreCase(RedisResultInfo))
				{
					return true;
				}
			}
		}

		return false;
	}
	
	protected String singleLineReplyForString(String redisCommandType, Object... args)
	{
		Object[] result = connector.executeCommand(redisCommandType, args);

		if (result != null && result.length > 1)
		{
			Character resultType = (Character) result[0];
			if (resultType == RedisResultType.SingleLineReply)
			{
				return (String) result[1];
			}
		}

		return null;
	}

	/**
	 * 返回类型为状态码的命令统一执行此函数
	 * 
	 * @param redisCommandType
	 *            命令类型
	 * @param RedisResultInfo
	 *            返回结果的期望值,如不符合则认为操作失败
	 * @return 操作结果是否成功
	 */
	protected int integerReply(String redisCommandType, Object... args)
	{
		Object[] result = connector.executeCommand(redisCommandType, args);

		if (result != null && result.length > 1)
		{
			Character resultType = (Character) result[0];
			if (resultType == RedisResultType.IntegerReply)
			{
				return Integer.valueOf((String) result[1]);
			}
		}

		return -1;
	}

	/**
	 * 返回类型为单行数据的命令统一执行此函数
	 * 发送给redis的数据,如果被DataWrapper包装过,则参数isUseObjectDecoder应该为true,否则为false
	 * 
	 * @param redisCommandType
	 *            命令类型
	 * @param isUseObjectDecoder 是否使用对象序列化功能
	 * @param args
	 *            其它参数
	 * @return 从redis取得的对象,
	 *         因为解码时统一按照DataWrapper类型来解码
	 */
	protected Object bulkReply(String redisCommandType, boolean isUseObjectDecoder, Object... args)
	{
		Object[] result = connector.executeCommand(redisCommandType, args);

		if (result != null && result.length > 1)
		{
			Character resultType = (Character) result[0];
			if (resultType == RedisResultType.BulkReplies)
			{
				if (result[1] != null)
				{
					if (isUseObjectDecoder)
						return ObjectDecoder.getObject((byte[]) result[1]);
					else
						return new String((byte[]) result[1]);
				}
			}
		}

		return null;
	}

	protected Object[] multiBulkReply(String redisCommandType, boolean isUseObjectDecoder, Object... args)
	{
		Object[] result = connector.executeCommand(redisCommandType, args);
		
		if (result != null && result.length > 1)
		{
			Character resultType = (Character) result[0];
			if (resultType == RedisResultType.MultiBulkReplies)
			{
				if (result[1] != null)
				{
					int returnValueLength = result.length - 1;

					if (isUseObjectDecoder)
					{
						Object[] returnValue = new Object[returnValueLength];

						for (int i = 1; i < result.length; i++)
						{
							returnValue[i - 1] = ObjectDecoder.getObject((byte[]) result[i]);
						}
						return returnValue;
					} else
					{
						String[] returnValue = new String[returnValueLength];

						for (int i = 1; i < result.length; i++)
						{
							returnValue[i - 1] = new String((byte[]) result[i]);
						}
						return returnValue;
					}
				}
			}
		}

		return null;
	}
	
	/**
	 * 返回类型为单行数据的命令统一执行此函数
	 * 发送给redis的数据,如果被DataWrapper包装过,则参数isUseObjectDecoder应该为true,否则为false
	 * 
	 * @param redisCommandType
	 *            命令类型
	 * @param isUseObjectDecoder 是否使用对象序列化功能
	 * @param args
	 *            其它参数
	 * @return 从redis取得的对象,
	 *         因为解码时统一按照DataWrapper类型来解码
	 */
	protected void asyncBulkReply(String redisCommandType, boolean isUseObjectDecoder, Object... args)
	{
		answer = connector.asyncExecuteCommand(redisCommandType, args);
	}
	
	/**
	 * 返回类型为单行数据的命令统一执行此函数
	 * 发送给redis的数据,如果被DataWrapper包装过,则参数isUseObjectDecoder应该为true,否则为false
	 * 
	 * @return 从redis取得的对象,
	 *         因为解码时统一按照DataWrapper类型来解码
	 */
	protected String asyncGetBulkReplyResult()
	{
		Object[] result = null;
		boolean interrupted = false;
		try
		{
			result = answer.take();
		}
		catch (InterruptedException e)
		{
			interrupted = true;
		}

		if (interrupted)
		{
			Thread.currentThread().interrupt();
		}

		if (result != null && result.length > 1)
		{
			Character resultType = (Character) result[0];
			if (resultType == RedisResultType.SingleLineReply)
			{
				if (result[1] != null)
				{
					return (String) result[1];
				}
			}
		}

		return null;
	}
}
