package com.handinfo.redis4j.impl;

import java.util.concurrent.BlockingQueue;

import com.handinfo.redis4j.api.ICommandExecutor;
import com.handinfo.redis4j.api.IConnector;
import com.handinfo.redis4j.api.RedisResultType;
import com.handinfo.redis4j.api.exception.CleanLockedThreadException;
import com.handinfo.redis4j.api.exception.ErrorCommandException;
import com.handinfo.redis4j.impl.util.ObjectWrapper;

public abstract class CommandExecutor implements ICommandExecutor
{
	private IConnector connector;
	private BlockingQueue<Object[]> answer;
	
	/**
	 * @param connector
	 */
	public CommandExecutor(IConnector connector)
	{
		this.connector = connector;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ICommandExecutor#singleLineReplyForBoolean(java.lang.String, java.lang.String, java.lang.Object)
	 */
	public boolean singleLineReplyForBoolean(String redisCommandType, String RedisResultInfo, Object... args) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException
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
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ICommandExecutor#singleLineReplyForString(java.lang.String, java.lang.Object)
	 */
	public String singleLineReplyForString(String redisCommandType, Object... args) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException
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

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ICommandExecutor#integerReply(java.lang.String, java.lang.Object)
	 */
	public int integerReply(String redisCommandType, Object... args) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException
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

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ICommandExecutor#bulkReply(java.lang.String, boolean, java.lang.Object)
	 */
	public Object bulkReply(String redisCommandType, boolean isUseObjectDecoder, Object... args) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException
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
					{
						ObjectWrapper<?> obj = new ObjectWrapper((byte[]) result[1]);
						return obj.getOriginal();
					}
					else
						return new String((byte[]) result[1]);
				}
			}
		}

		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ICommandExecutor#multiBulkReply(java.lang.String, boolean, java.lang.Object)
	 */
	public Object[] multiBulkReply(String redisCommandType, boolean isUseObjectDecoder, Object... args) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException
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
							returnValue[i - 1] = new ObjectWrapper((byte[]) result[i]).getOriginal();
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
	public void asyncBulkReply(String redisCommandType, boolean isUseObjectDecoder, Object... args) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException
	{
		//answer = connector.asyncExecuteCommand(redisCommandType, args);
	}
	
	/**
	 * 返回类型为单行数据的命令统一执行此函数
	 * 发送给redis的数据,如果被DataWrapper包装过,则参数isUseObjectDecoder应该为true,否则为false
	 * 
	 * @return 从redis取得的对象,
	 *         因为解码时统一按照DataWrapper类型来解码
	 */
	public String asyncGetBulkReplyResult() throws IllegalStateException, CleanLockedThreadException, ErrorCommandException
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
