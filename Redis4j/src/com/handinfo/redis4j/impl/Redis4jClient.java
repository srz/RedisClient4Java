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
	private Connector connector;

	public Redis4jClient(String host, int port, int poolSize, int indexDB)
	{
		connector = new Connector(host, port, poolSize, indexDB);
	}

	@Override
	public boolean auth(String password)
	{
		return singleLineReplyForBoolean(RedisCommandType.AUTH, RedisResultInfo.OK, password);
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
		return singleLineReplyForBoolean(RedisCommandType.PING, RedisResultInfo.PONG);
	}

	//
	@Override
	public boolean quit()
	{
		//boolean serverInfo = singleLineReplyForBoolean(RedisCommandType.QUIT, RedisResultInfo.OK);
		//connector.disconnect();
		connector.disconnect();
		return true;
	}

	/**
	 * 由于使用了连接池,如果公开此函数,并发情况下无法保证连接池中的所有连接都会修改默认操作的数据库
	 * 后续在考虑是否添加此函数
	 * @param dbIndex
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean select(int dbIndex)
	{
		return singleLineReplyForBoolean(RedisCommandType.SELECT, RedisResultInfo.OK, dbIndex);
	}

//	/*
//	 * 创建到Redis服务器的连接，需要调用quit()函数关闭此连接
//	 */
//	@Override
//	public boolean connect()
//	{
//		connector = new Connector();
//
//		return connector.connect(host, port);
//	}

	@Override
	public String get(String key)
	{
		return (String) bulkReply(RedisCommandType.GET, false, key);
	}

	@Override
	public boolean set(String key, String value)
	{
		return singleLineReplyForBoolean(RedisCommandType.SET, RedisResultInfo.OK, key, value);
	}
	
	
	/**
	 * 获取无法转化为字符串的对象,该功能不受到redis官方支持
	 * 请与{@link #setObject(String, Object)}函数配套使用
	 * @see com.handinfo.redis4j.api.IRedis4j#getObject(java.lang.String)
	 */
	@Override
	public Object getObject(String key)
	{
		return bulkReply(RedisCommandType.GET, true, key);
	}
	
	/**
	 * 存储无法转化为字符串的对象,该功能不受到redis官方支持
	 * 由于使用了对象序列化功能,所以使用此方法存储的对象无法使用sort进行排序,也不兼容其它种类的redis客户端
	 * 请谨慎使用
	 * 请与{@link #getObject(String)}函数配套使用
	 * @see com.handinfo.redis4j.api.IRedis4j#setObject(java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> boolean setObject(String key, T value)
	{
		DataWrapper data = new DataWrapper(value);

		return singleLineReplyForBoolean(RedisCommandType.SET, RedisResultInfo.OK, key, data);
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
		return singleLineReplyForBoolean(RedisCommandType.RENAME, RedisResultInfo.OK, key, newKey);
	}
	
	public String type(String key)
	{
		return singleLineReplyForString(RedisCommandType.TYPE,  key);
	}
	
	public boolean exists(String key)
	{
		return integerReply(RedisCommandType.EXISTS, key)==1 ? true : false;
	}
	
	public boolean move(String key, int indexDB)
	{
		return integerReply(RedisCommandType.MOVE, key, indexDB)==1 ? true : false;
	}
	
	public boolean renamenx(String key, String newKey)
	{
		return integerReply(RedisCommandType.RENAMENX, key, newKey)==1 ? true : false;
	}
	
	public boolean expire(String key, int seconds)
	{
		return integerReply(RedisCommandType.EXPIRE, key, seconds)==1 ? true : false;
	}
	
	public boolean persist(String key)
	{
		return integerReply(RedisCommandType.PERSIST, key)==1 ? true : false;
	}
	
	//TODO redis如何知道内部存储的是数字?
	public Object[] sort(String key)
	{
		return multiBulkReply(RedisCommandType.KEYS, false, key);
	}
	
	public boolean expireat(String key,  long timestamp)
	{
		return integerReply(RedisCommandType.EXPIREAT, key, timestamp)==1 ? true : false;
	}
	
	public Object randomkey()
	{
		return bulkReply(RedisCommandType.RANDOMKEY, true);
	}
	
	public int ttl(String key)
	{
		return integerReply(RedisCommandType.TTL, key);
	}
	
	
	
	
	
	
	public boolean flushdb()
	{
		return singleLineReplyForBoolean(RedisCommandType.FLUSHDB, RedisResultInfo.OK);
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
	private boolean singleLineReplyForBoolean(String redisCommandType, String RedisResultInfo, Object... args)
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
	
	private String singleLineReplyForString(String redisCommandType, Object... args)
	{
		Object[] result = connector.executeCommand(redisCommandType, args);

		if (result.length > 1)
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
}
