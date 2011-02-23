package com.handinfo.redis4j.impl;

import com.handinfo.redis4j.api.DataWrapper;
import com.handinfo.redis4j.api.IRedis4j;
import com.handinfo.redis4j.api.RedisCommandType;
import com.handinfo.redis4j.api.RedisResultInfo;
import com.handinfo.redis4j.api.RedisResultType;
import com.handinfo.redis4j.impl.protocol.decode.ObjectDecoder;
import com.handinfo.redis4j.impl.transfers.Connector;

public class Redis4jClient implements IRedis4j
{
	private String host;
	private int port;
	private Connector connector;

	public Redis4jClient(String host, int port)
	{
		this.host = host;
		this.port = port;
	}

	@Override
	public boolean auth(String password)
	{
		return singleLineReply(RedisCommandType.AUTH, RedisResultInfo.OK, password);
	}

	@Override
	public String echo(String message)
	{
		Object result = bulkReply(RedisCommandType.ECHO, false, message);
		if (result != null)
		{
			return (String) result;
		}

		return null;
	}

	@Override
	public boolean ping()
	{
		return singleLineReply(RedisCommandType.PING, RedisResultInfo.PONG);
	}

	//
	@Override
	public boolean quit()
	{
		boolean serverInfo = singleLineReply(RedisCommandType.QUIT, RedisResultInfo.OK);
		connector.disconnect();
		return serverInfo;
	}

	@Override
	public boolean select(int dbIndex)
	{
		return singleLineReply(RedisCommandType.SELECT, RedisResultInfo.OK, dbIndex);
	}

	/*
	 * 创建到Redis服务器的连接，需要调用quit()函数关闭此连接
	 */
	@Override
	public boolean connect()
	{
		connector = new Connector();

		return connector.connect(host, port);
	}

	@Override
	public Object get(String key)
	{
		return bulkReply(RedisCommandType.GET, true, key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> boolean set(String key, T value)
	{
		DataWrapper data = new DataWrapper(value);

		return singleLineReply(RedisCommandType.SET, RedisResultInfo.OK, key, data);
	}

	public int del(String... keys)
	{
		return integerReply(RedisCommandType.DEL, keys);
	}

	public String[] keys(String key)
	{
		return (String[]) multiBulkReply(RedisCommandType.KEYS, false, key);
	}

	public boolean rename(String key, String newKey)
	{
		return singleLineReply(RedisCommandType.RENAME, RedisResultInfo.OK, key, newKey);
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
	private boolean singleLineReply(String redisCommandType, String RedisResultInfo, Object... args)
	{
		Object[] result = connector.executeCommand(redisCommandType, args);
		if (result.length > 1)
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

	/**
	 * 返回类型为状态码的命令统一执行此函数
	 * 
	 * @param redisCommandType
	 *            命令类型
	 * @param RedisResultInfo
	 *            返回结果的期望值,如不符合则认为操作失败
	 * @return 操作结果是否成功
	 */
	private int integerReply(String redisCommandType, Object... args)
	{
		Object[] result = connector.executeCommand(redisCommandType, args);
		if (result.length > 1)
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
	 * 
	 * @param redisCommandType
	 *            命令类型
	 * @param args
	 *            参数
	 * @return 从redis取得的对象,凡是发送给redis的数据，被其处理后返回的都应该使用DataWrapper包装,
	 *         因为解码时统一按照DataWrapper类型来解码
	 */
	private Object bulkReply(String redisCommandType, boolean isUseObjectDecoder, Object... args)
	{
		Object[] result = connector.executeCommand(redisCommandType, args);
		if (result.length > 1)
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

	private Object[] multiBulkReply(String redisCommandType, boolean isUseObjectDecoder, Object... args)
	{
		Object[] result = connector.executeCommand(redisCommandType, args);
		if (result.length > 1)
		{
			Character resultType = (Character) result[0];
			if (resultType == RedisResultType.MultiBulkReplies)
			{
				if (result[1] != null)
				{
					if (isUseObjectDecoder)
					{
						Object[] returnValue = new Object[result.length-1];
						for(int i=1; i<result.length; i++)
						{
							returnValue[i-1] = ObjectDecoder.getObject((byte[]) result[1]);
						}
						return returnValue;
					}
					else
					{
						String[] returnValue = new String[result.length-1];
						for(int i=1; i<result.length; i++)
						{
							returnValue[i-1] = new String((byte[]) result[i]);
						}
						return returnValue;
					}
				}
			}
		}

		return null;

	}
}
