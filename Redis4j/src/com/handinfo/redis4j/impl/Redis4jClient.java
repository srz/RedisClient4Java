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
	
	//TODO 暂时先写个简单版本的,后面在追加重载版本
	public Object[] sort(String key, String...args)
	{
		return multiBulkReply(RedisCommandType.SORT, false, key, args);
	}
	
	public boolean expireat(String key,  long timestamp)
	{
		return integerReply(RedisCommandType.EXPIREAT, key, timestamp)==1 ? true : false;
	}
	
	public String randomkey()
	{
		return (String) bulkReply(RedisCommandType.RANDOMKEY, false);
	}
	
	public int ttl(String key)
	{
		return integerReply(RedisCommandType.TTL, key);
	}
	
	public int append(String key, String value)
	{
		return integerReply(RedisCommandType.APPEND, key);
	}
	
	public String getrange(String key, int start, int end)
	{
		return (String) bulkReply(RedisCommandType.GETRANGE, false, start, end);
	}
	
	public Boolean mset(String key, String value)
	{
		return singleLineReplyForBoolean(RedisCommandType.MSET, RedisResultInfo.OK, value);
	}
	
	public Boolean setnx(String key, String value)
	{
		return integerReply(RedisCommandType.SETNX, key)==1 ? true : false;
	}
	
	public int decr(String key)
	{
		return integerReply(RedisCommandType.DECR, key);
	}
	
	public String getset(String key, String value)
	{
		return (String) bulkReply(RedisCommandType.GETSET, false, value);
	}
	
	public Boolean msetnx(String key, String value)
	{
		return integerReply(RedisCommandType.MSETNX, key)==1 ? true : false;
	}
	
	public int setrange(String key, int offset, String value)
	{
		return integerReply(RedisCommandType.SETRANGE, key, offset, value);
	}
	
	public int decrby(String key, int decrement)
	{
		return integerReply(RedisCommandType.DECRBY, key, decrement);
	}
	
	public int incr(String key)
	{
		return integerReply(RedisCommandType.INCR, key);
	}
	
	@Override
	public boolean set(String key, String value)
	{
		return singleLineReplyForBoolean(RedisCommandType.SET, RedisResultInfo.OK, key, value);
	}
	
	public int strlen(String key)
	{
		return integerReply(RedisCommandType.STRLEN, key);
	}
	
	@Override
	public String get(String key)
	{
		return (String) bulkReply(RedisCommandType.GET, false, key);
	}
	
	public int incrby(String key, int increment)
	{
		return integerReply(RedisCommandType.INCRBY, key, increment);
	}
	
	public int setbit(String key, int offset, int value)
	{
		return integerReply(RedisCommandType.SETBIT, key, offset, value);
	}
	
	public int getbit(String key, int offset)
	{
		return integerReply(RedisCommandType.GETBIT, key, offset);
	}
	
	public String[] mget(String...keys)
	{
		return (String[]) multiBulkReply(RedisCommandType.MGET, false, keys);
	}
	
	public boolean setex(String key, int seconds, String value)
	{
		return singleLineReplyForBoolean(RedisCommandType.SETEX, RedisResultInfo.OK, key, seconds, value);
	}
	
	public Boolean hdel(String key, String field)
	{
		return integerReply(RedisCommandType.HDEL, key, field)==1 ? true : false;
	}
	
	public String[] hgetall(String key)
	{
		return (String[]) multiBulkReply(RedisCommandType.HGETALL, false, key);
	}
	
	public int hlen(String key)
	{
		return integerReply(RedisCommandType.HLEN, key);
	}
	
	public Boolean hset(String key, String field, String value)
	{
		return integerReply(RedisCommandType.HSET, key, field, value)==1 ? true : false;
	}
	
	public Boolean hexists(String key, String field)
	{
		return integerReply(RedisCommandType.HEXISTS, key, field)==1 ? true : false;
	}
	
	public int hincrby(String key, String field, int increment)
	{
		return integerReply(RedisCommandType.HINCRBY, key, field, increment);
	}
	
	public String[] hmget(String key, String field)
	{
		return (String[]) multiBulkReply(RedisCommandType.HMGET, false, key, field);
	}
	
	public Boolean hsetnx(String key, String field, String value)
	{
		return integerReply(RedisCommandType.HSETNX, key, field, value)==1 ? true : false;
	}
	
	public String hget(String key, String field)
	{
		return (String) bulkReply(RedisCommandType.HGET, false, key, field);
	}
	
	public String[] hkeys(String key)
	{
		return (String[]) multiBulkReply(RedisCommandType.HKEYS, false, key);
	}
	
	public boolean hmset(String key, String field, String value)
	{
		return singleLineReplyForBoolean(RedisCommandType.HMSET, RedisResultInfo.OK, key, field, value);
	}
	
	public String[] hvals(String key)
	{
		return (String[]) multiBulkReply(RedisCommandType.HVALS, false, key);
	}
	
	public String[] blpop(String key, int timeout)
	{
		return (String[]) multiBulkReply(RedisCommandType.BLPOP, false, key, timeout);
	}
	
	public int llen(String key)
	{
		return integerReply(RedisCommandType.LLEN, key);
	}
	
	public int lrem(String key, int count, String value)
	{
		return integerReply(RedisCommandType.LREM, key, count, value);
	}
	
	public int rpush(String key, String value)
	{
		return integerReply(RedisCommandType.RPUSH, key, value);
	}
	
	public String[] brpop(String key, int timeout)
	{
		return (String[]) multiBulkReply(RedisCommandType.BRPOP, false, key, timeout);
	}
	
	public String lpop(String key)
	{
		return singleLineReplyForString(RedisCommandType.LPOP, key);
	}
	
	public boolean lset(String key, int index, String value)
	{
		return singleLineReplyForBoolean(RedisCommandType.LSET, RedisResultInfo.OK, key, index, value);
	}
	
	public int rpushx(String key, String value)
	{
		return integerReply(RedisCommandType.RPUSHX, key, value);
	}
	
	public String brpoplpush(String source, String destination, int timeout)
	{
		return (String) bulkReply(RedisCommandType.BRPOPLPUSH, false, source, destination, timeout);
	}
	
	public int lpush(String key, String value)
	{
		return integerReply(RedisCommandType.LPUSH, key, value);
	}
	
	public boolean ltrim(String key, int start, int stop)
	{
		return singleLineReplyForBoolean(RedisCommandType.LTRIM, RedisResultInfo.OK, key, start, stop);
	}
	
	public String lindex(String key, int index)
	{
		return singleLineReplyForString(RedisCommandType.LINDEX, key, index);
	}
	
	public int lpushx(String key, String value)
	{
		return integerReply(RedisCommandType.LPUSHX, key, value);
	}
	
	public String rpop(String key)
	{
		return (String) bulkReply(RedisCommandType.RPOP, false, key);
	}
	
	public int linsert(String key, String BEFORE_AFTER, String pivot, String value)
	{
		return integerReply(RedisCommandType.LINSERT, key, BEFORE_AFTER, pivot, value);
	}
	
	public String[] lrange(String key, int start, int stop)
	{
		return (String[]) multiBulkReply(RedisCommandType.LRANGE, false, key, start, stop);
	}
	
	public String rpoplpush(String source, String destination)
	{
		return (String) bulkReply(RedisCommandType.RPOPLPUSH, false, source, destination);
	}
	
	public Boolean sadd(String key, String member)
	{
		return integerReply(RedisCommandType.SADD, key, member)==1 ? true : false;
	}
	
	public String[] sinter(String...keys)
	{
		return (String[]) multiBulkReply(RedisCommandType.SINTER, false, keys);
	}
	
	public Boolean smove(String source, String destination, String member)
	{
		return integerReply(RedisCommandType.SMOVE, source, destination, member)==1 ? true : false;
	}
	
	public String[] sunion(String...keys)
	{
		return (String[]) multiBulkReply(RedisCommandType.SUNION, false, keys);
	}
	
	public int scard(String key)
	{
		return integerReply(RedisCommandType.SCARD, key);
	}
	
	public int sinterstore(String destination, String...keys)
	{
		return integerReply(RedisCommandType.SINTERSTORE, destination, keys);
	}
	
	public String spop(String key)
	{
		return (String) bulkReply(RedisCommandType.SPOP, false, key);
	}
	
	public int sunionstore(String destination, String...keys)
	{
		return integerReply(RedisCommandType.SUNIONSTORE, destination, keys);
	}
	
	public String[] sdiff(String...keys)
	{
		return (String[]) multiBulkReply(RedisCommandType.SDIFF, false, keys);
	}
	
	public Boolean sismember(String key, String member)
	{
		return integerReply(RedisCommandType.SISMEMBER, key, member)==1 ? true : false;
	}
	
	public String srandmember(String key)
	{
		return (String) bulkReply(RedisCommandType.SRANDMEMBER, false, key);
	}
	
	public int sdiffstore(String destination, String...keys)
	{
		return integerReply(RedisCommandType.SDIFFSTORE, destination, keys);
	}
	
	public String[] smembers(String key)
	{
		return (String[]) multiBulkReply(RedisCommandType.SMEMBERS, false, key);
	}
	
	public Boolean srem(String key, String member)
	{
		return integerReply(RedisCommandType.SREM, key, member)==1 ? true : false;
	}
	
	public Boolean zadd(String key, int score, String member)
	{
		return integerReply(RedisCommandType.ZADD, key, score, member)==1 ? true : false;
	}
	
	public int zinterstore(String...args)
	{
		return integerReply(RedisCommandType.ZINTERSTORE, args);
	}
	
	public Boolean zrem(String key, String member)
	{
		return integerReply(RedisCommandType.ZREM, key, member)==1 ? true : false;
	}
	
	public String[] zrevrangebyscore(String...args)
	{
		return (String[]) multiBulkReply(RedisCommandType.ZREVRANGEBYSCORE, false, args);
	}
	
	public int zcard(String key)
	{
		return integerReply(RedisCommandType.ZCARD, key);
	}
	
	public String[] zrange(String key, int start, int stop)
	{
		return (String[]) multiBulkReply(RedisCommandType.ZRANGE, false, key, start, stop);
	}
	
	public int zremrangebyrank(String key, int start, int stop)
	{
		return integerReply(RedisCommandType.ZREMRANGEBYRANK, key, start, stop);
	}
	
	public int zrevrank(String key, String member)
	{
		return integerReply(RedisCommandType.ZREVRANK, key, member);
	}
	
	public int zcount(String key, int min, int max)
	{
		return integerReply(RedisCommandType.ZCOUNT, key, min, max);
	}
	
	public String[] zrangebyscore(String...args)
	{
		return (String[]) multiBulkReply(RedisCommandType.ZRANGEBYSCORE, false, args);
	}
	
	public int zremrangebyscore(String key, int min, int max)
	{
		return integerReply(RedisCommandType.ZREMRANGEBYSCORE, key, min, max);
	}
	
	public String zscore(String key, String member)
	{
		return (String) bulkReply(RedisCommandType.ZSCORE, false, key, member);
	}
	
	public String zincrby(String key, int increment, String member)
	{
		return (String) bulkReply(RedisCommandType.ZINCRBY, false, key, increment, member);
	}
	
	public int zrank(String key, String member)
	{
		return integerReply(RedisCommandType.ZRANK, key, member);
	}
	
	public String[] zrevrange(String key, int start, int stop)
	{
		return (String[]) multiBulkReply(RedisCommandType.ZREVRANGE, false, key, start, stop);
	}
	
	public int zunionstore(String...args)
	{
		return integerReply(RedisCommandType.ZUNIONSTORE, args);
	}
	
	public boolean discard()
	{
		return singleLineReplyForBoolean(RedisCommandType.DISCARD, RedisResultInfo.OK);
	}
	
	public String[] exec()
	{
		return (String[]) multiBulkReply(RedisCommandType.EXEC, false);
	}
	
	public boolean multi()
	{
		return singleLineReplyForBoolean(RedisCommandType.MULTI, RedisResultInfo.OK);
	}
	
	public boolean unwatch()
	{
		return singleLineReplyForBoolean(RedisCommandType.UNWATCH, RedisResultInfo.OK);
	}
	
	public boolean watch()
	{
		return singleLineReplyForBoolean(RedisCommandType.WATCH, RedisResultInfo.OK);
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
	
	public boolean bgrewriteaof()
	{
		return singleLineReplyForBoolean(RedisCommandType.BGREWRITEAOF, RedisResultInfo.OK);
	}
	
	public boolean bgsave()
	{
		return singleLineReplyForBoolean(RedisCommandType.BGSAVE, RedisResultInfo.OK);
	}
	
	public String[] config_get(String parameter)
	{
		return (String[]) multiBulkReply(RedisCommandType.CONFIG, false, RedisCommandType.CONFIG_GET, parameter);
	}
	
	public boolean config_resetstat()
	{
		return singleLineReplyForBoolean(RedisCommandType.CONFIG, RedisResultInfo.OK, RedisCommandType.CONFIG_RESETSTAT);
	}
	
	public boolean config_set(String parameter, String value)
	{
		return singleLineReplyForBoolean(RedisCommandType.CONFIG, RedisResultInfo.OK, RedisCommandType.CONFIG_SET, parameter, value);
	}
	
	public int dbsize()
	{
		return integerReply(RedisCommandType.DBSIZE);
	}
	
	public String[] debug_object(String key)
	{
		return (String[]) multiBulkReply(RedisCommandType.DEBUG, false, RedisCommandType.DEBUG_OBJECT, key);
	}
	
	public String[] debug_segfault()
	{
		return (String[]) multiBulkReply(RedisCommandType.DEBUG, false, RedisCommandType.DEBUG_SEGFAULT);
	}
	
	public boolean flushall()
	{
		return singleLineReplyForBoolean(RedisCommandType.FLUSHALL, RedisResultInfo.OK);
	}
	
	public boolean flushdb()
	{
		return singleLineReplyForBoolean(RedisCommandType.FLUSHDB, RedisResultInfo.OK);
	}
	
	public String[] info()
	{
		return (String[]) multiBulkReply(RedisCommandType.INFO, false);
	}
	
	public int lastsave()
	{
		return integerReply(RedisCommandType.LASTSAVE);
	}
	
	public String monitor()
	{
		return (String) bulkReply(RedisCommandType.MONITOR, false);
	}
	
	public boolean save()
	{
		return singleLineReplyForBoolean(RedisCommandType.SAVE, RedisResultInfo.OK);
	}
	
	public boolean shutdown()
	{
		return !singleLineReplyForBoolean(RedisCommandType.SHUTDOWN, RedisResultInfo.SHUTDOWNERROR);
	}
	
	public boolean slaveof()
	{
		return singleLineReplyForBoolean(RedisCommandType.SLAVEOF, RedisResultInfo.OK);
	}
	
	public String[] sync()
	{
		return (String[]) multiBulkReply(RedisCommandType.SYNC, false);
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
