package com.handinfo.redis4j.impl;

import com.handinfo.redis4j.api.IHashes;
import com.handinfo.redis4j.api.RedisCommandType;
import com.handinfo.redis4j.api.RedisResultInfo;
import com.handinfo.redis4j.impl.transfers.Connector;

public class Hashes extends BaseCommand implements IHashes
{

	public Hashes(Connector connector)
	{
		super(connector);
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IHashes#hdel(java.lang.String, java.lang.String)
	 */
	public Boolean hdel(String key, String field) throws Exception
	{
		return integerReply(RedisCommandType.HDEL, key, field)==1 ? true : false;
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IHashes#hgetall(java.lang.String)
	 */
	public String[] hgetall(String key) throws Exception
	{
		return (String[]) multiBulkReply(RedisCommandType.HGETALL, false, key);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IHashes#hlen(java.lang.String)
	 */
	public int hlen(String key) throws Exception
	{
		return integerReply(RedisCommandType.HLEN, key);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IHashes#hset(java.lang.String, java.lang.String, java.lang.String)
	 */
	public Boolean hset(String key, String field, String value) throws Exception
	{
		return integerReply(RedisCommandType.HSET, key, field, value)==1 ? true : false;
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IHashes#hexists(java.lang.String, java.lang.String)
	 */
	public Boolean hexists(String key, String field) throws Exception
	{
		return integerReply(RedisCommandType.HEXISTS, key, field)==1 ? true : false;
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IHashes#hincrby(java.lang.String, java.lang.String, int)
	 */
	public int hincrby(String key, String field, int increment) throws Exception
	{
		return integerReply(RedisCommandType.HINCRBY, key, field, increment);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IHashes#hmget(java.lang.String, java.lang.String)
	 */
	public String[] hmget(String key, String field) throws Exception
	{
		return (String[]) multiBulkReply(RedisCommandType.HMGET, false, key, field);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IHashes#hsetnx(java.lang.String, java.lang.String, java.lang.String)
	 */
	public Boolean hsetnx(String key, String field, String value) throws Exception
	{
		return integerReply(RedisCommandType.HSETNX, key, field, value)==1 ? true : false;
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IHashes#hget(java.lang.String, java.lang.String)
	 */
	public String hget(String key, String field) throws Exception
	{
		return (String) bulkReply(RedisCommandType.HGET, false, key, field);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IHashes#hkeys(java.lang.String)
	 */
	public String[] hkeys(String key) throws Exception
	{
		return (String[]) multiBulkReply(RedisCommandType.HKEYS, false, key);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IHashes#hmset(java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean hmset(String key, String field, String value) throws Exception
	{
		return singleLineReplyForBoolean(RedisCommandType.HMSET, RedisResultInfo.OK, key, field, value);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IHashes#hvals(java.lang.String)
	 */
	public String[] hvals(String key) throws Exception
	{
		return (String[]) multiBulkReply(RedisCommandType.HVALS, false, key);
	}
}
