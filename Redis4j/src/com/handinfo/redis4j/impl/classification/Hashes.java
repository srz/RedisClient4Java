package com.handinfo.redis4j.impl.classification;

import com.handinfo.redis4j.api.IConnector;
import com.handinfo.redis4j.api.RedisCommand;
import com.handinfo.redis4j.api.RedisResponseMessage;
import com.handinfo.redis4j.api.classification.IHashes;
import com.handinfo.redis4j.impl.CommandExecutor;

public class Hashes extends CommandExecutor implements IHashes
{

	public Hashes(IConnector connector)
	{
		super(connector);
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IHashes#hdel(java.lang.String, java.lang.String)
	 */
	public Boolean hdel(String key, String field)
	{
		return integerReply(RedisCommand.HDEL, key, field)==1 ? true : false;
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IHashes#hgetall(java.lang.String)
	 */
	public String[] hgetall(String key)
	{
		return (String[]) multiBulkReply(RedisCommand.HGETALL, false, key);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IHashes#hlen(java.lang.String)
	 */
	public int hlen(String key)
	{
		return integerReply(RedisCommand.HLEN, key);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IHashes#hset(java.lang.String, java.lang.String, java.lang.String)
	 */
	public Boolean hset(String key, String field, String value)
	{
		return integerReply(RedisCommand.HSET, key, field, value)==1 ? true : false;
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IHashes#hexists(java.lang.String, java.lang.String)
	 */
	public Boolean hexists(String key, String field)
	{
		return integerReply(RedisCommand.HEXISTS, key, field)==1 ? true : false;
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IHashes#hincrby(java.lang.String, java.lang.String, int)
	 */
	public int hincrby(String key, String field, int increment)
	{
		return integerReply(RedisCommand.HINCRBY, key, field, increment);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IHashes#hmget(java.lang.String, java.lang.String)
	 */
	public String[] hmget(String key, String field)
	{
		return (String[]) multiBulkReply(RedisCommand.HMGET, false, key, field);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IHashes#hsetnx(java.lang.String, java.lang.String, java.lang.String)
	 */
	public Boolean hsetnx(String key, String field, String value)
	{
		return integerReply(RedisCommand.HSETNX, key, field, value)==1 ? true : false;
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IHashes#hget(java.lang.String, java.lang.String)
	 */
	public String hget(String key, String field)
	{
		return (String) bulkReply(RedisCommand.HGET, false, key, field);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IHashes#hkeys(java.lang.String)
	 */
	public String[] hkeys(String key)
	{
		return (String[]) multiBulkReply(RedisCommand.HKEYS, false, key);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IHashes#hmset(java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean hmset(String key, String field, String value)
	{
		return singleLineReplyForBoolean(RedisCommand.HMSET, RedisResponseMessage.OK, key, field, value);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IHashes#hvals(java.lang.String)
	 */
	public String[] hvals(String key)
	{
		return (String[]) multiBulkReply(RedisCommand.HVALS, false, key);
	}
}
