package com.handinfo.redis4j.impl;

import com.handinfo.redis4j.api.ICommandExecutor;
import com.handinfo.redis4j.api.IConnector;
import com.handinfo.redis4j.api.RedisCommand;
import com.handinfo.redis4j.api.RedisResponseMessage;
import com.handinfo.redis4j.api.RedisResponseType;
import com.handinfo.redis4j.api.exception.CleanLockedThreadException;
import com.handinfo.redis4j.api.exception.ErrorCommandException;
import com.handinfo.redis4j.impl.util.ObjectWrapper;

public abstract class CommandExecutor implements ICommandExecutor
{
	private IConnector connector;
	
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
	public boolean singleLineReplyForBoolean(RedisCommand command, RedisResponseMessage RedisResultInfo, Object... args) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException
	{
		Object[] result = connector.executeCommand(command, args);

		if (result != null && result.length > 1)
		{
			RedisResponseType resultType = (RedisResponseType) result[0];
			if (resultType == RedisResponseType.SingleLineReply)
			{
				if (((String) result[1]).equalsIgnoreCase(RedisResultInfo.getValue()))
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
	public String singleLineReplyForString(RedisCommand command, Object... args) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException
	{
		Object[] result = connector.executeCommand(command, args);

		if (result != null && result.length > 1)
		{
			RedisResponseType resultType = (RedisResponseType) result[0];
			if (resultType == RedisResponseType.SingleLineReply)
			{
				return (String) result[1];
			}
		}

		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ICommandExecutor#integerReply(java.lang.String, java.lang.Object)
	 */
	public int integerReply(RedisCommand command, Object... args) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException
	{
		Object[] result = connector.executeCommand(command, args);

		if (result != null && result.length > 1)
		{
			RedisResponseType resultType = (RedisResponseType) result[0];
			if (resultType == RedisResponseType.IntegerReply)
			{
				return Integer.valueOf((String) result[1]);
			}
		}

		return -1;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ICommandExecutor#bulkReply(java.lang.String, boolean, java.lang.Object)
	 */
	public Object bulkReply(RedisCommand command, boolean isUseObjectDecoder, Object... args) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException
	{
		Object[] result = connector.executeCommand(command, args);

		if (result != null && result.length > 1)
		{
			RedisResponseType resultType = (RedisResponseType) result[0];
			if (resultType == RedisResponseType.BulkReplies)
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
	public Object[] multiBulkReply(RedisCommand command, boolean isUseObjectDecoder, Object... args) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException
	{
		Object[] result = connector.executeCommand(command, args);
		
		if (result != null && result.length > 1)
		{
			RedisResponseType resultType = (RedisResponseType) result[0];
			if (resultType == RedisResponseType.MultiBulkReplies)
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
}
