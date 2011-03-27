package com.handinfo.redis4j.impl.classification;

import com.handinfo.redis4j.api.IConnector;
import com.handinfo.redis4j.api.RedisCommand;
import com.handinfo.redis4j.api.RedisResponseMessage;
import com.handinfo.redis4j.api.classification.ILists;
import com.handinfo.redis4j.impl.CommandExecutor;

public class Lists extends CommandExecutor implements ILists
{

	public Lists(IConnector connector)
	{
		super(connector);
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ILists#blpop(java.lang.String, int)
	 */
	public String[] blpop(String key, int timeout)
	{
		return (String[]) multiBulkReply(RedisCommand.BLPOP, false, key, timeout);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ILists#llen(java.lang.String)
	 */
	public int llen(String key)
	{
		return integerReply(RedisCommand.LLEN, key);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ILists#lrem(java.lang.String, int, java.lang.String)
	 */
	public int lrem(String key, int count, String value)
	{
		return integerReply(RedisCommand.LREM, key, count, value);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ILists#rpush(java.lang.String, java.lang.String)
	 */
	public int rpush(String key, String value)
	{
		return integerReply(RedisCommand.RPUSH, key, value);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ILists#brpop(java.lang.String, int)
	 */
	public String[] brpop(String key, int timeout)
	{
		return (String[]) multiBulkReply(RedisCommand.BRPOP, false, key, timeout);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ILists#lpop(java.lang.String)
	 */
	public String lpop(String key)
	{
		return singleLineReplyForString(RedisCommand.LPOP, key);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ILists#lset(java.lang.String, int, java.lang.String)
	 */
	public boolean lset(String key, int index, String value)
	{
		return singleLineReplyForBoolean(RedisCommand.LSET, RedisResponseMessage.OK, key, index, value);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ILists#rpushx(java.lang.String, java.lang.String)
	 */
	public int rpushx(String key, String value)
	{
		return integerReply(RedisCommand.RPUSHX, key, value);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ILists#brpoplpush(java.lang.String, java.lang.String, int)
	 */
	public String brpoplpush(String source, String destination, int timeout)
	{
		return (String) bulkReply(RedisCommand.BRPOPLPUSH, false, source, destination, timeout);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ILists#lpush(java.lang.String, java.lang.String)
	 */
	public int lpush(String key, String value)
	{
		return integerReply(RedisCommand.LPUSH, key, value);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ILists#ltrim(java.lang.String, int, int)
	 */
	public boolean ltrim(String key, int start, int stop)
	{
		return singleLineReplyForBoolean(RedisCommand.LTRIM, RedisResponseMessage.OK, key, start, stop);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ILists#lindex(java.lang.String, int)
	 */
	public String lindex(String key, int index)
	{
		return singleLineReplyForString(RedisCommand.LINDEX, key, index);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ILists#lpushx(java.lang.String, java.lang.String)
	 */
	public int lpushx(String key, String value)
	{
		return integerReply(RedisCommand.LPUSHX, key, value);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ILists#rpop(java.lang.String)
	 */
	public String rpop(String key)
	{
		return (String) bulkReply(RedisCommand.RPOP, false, key);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ILists#linsert(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public int linsert(String key, String BEFORE_AFTER, String pivot, String value)
	{
		return integerReply(RedisCommand.LINSERT, key, BEFORE_AFTER, pivot, value);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ILists#lrange(java.lang.String, int, int)
	 */
	public String[] lrange(String key, int start, int stop)
	{
		return (String[]) multiBulkReply(RedisCommand.LRANGE, false, key, start, stop);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ILists#rpoplpush(java.lang.String, java.lang.String)
	 */
	public String rpoplpush(String source, String destination)
	{
		return (String) bulkReply(RedisCommand.RPOPLPUSH, false, source, destination);
	}
}
