package com.handinfo.redis4j.impl;

import com.handinfo.redis4j.api.ILists;
import com.handinfo.redis4j.api.RedisCommandType;
import com.handinfo.redis4j.api.RedisResultInfo;
import com.handinfo.redis4j.impl.transfers.Connector;

public class Lists extends BaseCommand implements ILists
{

	public Lists(Connector connector)
	{
		super(connector);
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ILists#blpop(java.lang.String, int)
	 */
	public String[] blpop(String key, int timeout)
	{
		return (String[]) multiBulkReply(RedisCommandType.BLPOP, false, key, timeout);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ILists#llen(java.lang.String)
	 */
	public int llen(String key)
	{
		return integerReply(RedisCommandType.LLEN, key);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ILists#lrem(java.lang.String, int, java.lang.String)
	 */
	public int lrem(String key, int count, String value)
	{
		return integerReply(RedisCommandType.LREM, key, count, value);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ILists#rpush(java.lang.String, java.lang.String)
	 */
	public int rpush(String key, String value)
	{
		return integerReply(RedisCommandType.RPUSH, key, value);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ILists#brpop(java.lang.String, int)
	 */
	public String[] brpop(String key, int timeout)
	{
		return (String[]) multiBulkReply(RedisCommandType.BRPOP, false, key, timeout);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ILists#lpop(java.lang.String)
	 */
	public String lpop(String key)
	{
		return singleLineReplyForString(RedisCommandType.LPOP, key);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ILists#lset(java.lang.String, int, java.lang.String)
	 */
	public boolean lset(String key, int index, String value)
	{
		return singleLineReplyForBoolean(RedisCommandType.LSET, RedisResultInfo.OK, key, index, value);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ILists#rpushx(java.lang.String, java.lang.String)
	 */
	public int rpushx(String key, String value)
	{
		return integerReply(RedisCommandType.RPUSHX, key, value);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ILists#brpoplpush(java.lang.String, java.lang.String, int)
	 */
	public String brpoplpush(String source, String destination, int timeout)
	{
		return (String) bulkReply(RedisCommandType.BRPOPLPUSH, false, source, destination, timeout);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ILists#lpush(java.lang.String, java.lang.String)
	 */
	public int lpush(String key, String value)
	{
		return integerReply(RedisCommandType.LPUSH, key, value);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ILists#ltrim(java.lang.String, int, int)
	 */
	public boolean ltrim(String key, int start, int stop)
	{
		return singleLineReplyForBoolean(RedisCommandType.LTRIM, RedisResultInfo.OK, key, start, stop);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ILists#lindex(java.lang.String, int)
	 */
	public String lindex(String key, int index)
	{
		return singleLineReplyForString(RedisCommandType.LINDEX, key, index);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ILists#lpushx(java.lang.String, java.lang.String)
	 */
	public int lpushx(String key, String value)
	{
		return integerReply(RedisCommandType.LPUSHX, key, value);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ILists#rpop(java.lang.String)
	 */
	public String rpop(String key)
	{
		return (String) bulkReply(RedisCommandType.RPOP, false, key);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ILists#linsert(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public int linsert(String key, String BEFORE_AFTER, String pivot, String value)
	{
		return integerReply(RedisCommandType.LINSERT, key, BEFORE_AFTER, pivot, value);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ILists#lrange(java.lang.String, int, int)
	 */
	public String[] lrange(String key, int start, int stop)
	{
		return (String[]) multiBulkReply(RedisCommandType.LRANGE, false, key, start, stop);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ILists#rpoplpush(java.lang.String, java.lang.String)
	 */
	public String rpoplpush(String source, String destination)
	{
		return (String) bulkReply(RedisCommandType.RPOPLPUSH, false, source, destination);
	}
}
