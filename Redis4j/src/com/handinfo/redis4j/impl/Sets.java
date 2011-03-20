package com.handinfo.redis4j.impl;

import com.handinfo.redis4j.api.ISets;
import com.handinfo.redis4j.api.RedisCommandType;
import com.handinfo.redis4j.api.RedisResultInfo;
import com.handinfo.redis4j.impl.transfers.Connector;

public class Sets extends BaseCommand implements ISets
{

	public Sets(Connector connector)
	{
		super(connector);
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISets#sadd(java.lang.String, java.lang.String)
	 */
	public Boolean sadd(String key, String member) throws Exception
	{
		return integerReply(RedisCommandType.SADD, key, member)==1 ? true : false;
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISets#sinter(java.lang.String)
	 */
	public String[] sinter(String...keys) throws Exception
	{
		return (String[]) multiBulkReply(RedisCommandType.SINTER, false, keys);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISets#smove(java.lang.String, java.lang.String, java.lang.String)
	 */
	public Boolean smove(String source, String destination, String member) throws Exception
	{
		return integerReply(RedisCommandType.SMOVE, source, destination, member)==1 ? true : false;
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISets#sunion(java.lang.String)
	 */
	public String[] sunion(String...keys) throws Exception
	{
		return (String[]) multiBulkReply(RedisCommandType.SUNION, false, keys);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISets#scard(java.lang.String)
	 */
	public int scard(String key) throws Exception
	{
		return integerReply(RedisCommandType.SCARD, key);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISets#sinterstore(java.lang.String, java.lang.String)
	 */
	public int sinterstore(String destination, String...keys) throws Exception
	{
		return integerReply(RedisCommandType.SINTERSTORE, destination, keys);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISets#spop(java.lang.String)
	 */
	public String spop(String key) throws Exception
	{
		return (String) bulkReply(RedisCommandType.SPOP, false, key);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISets#sunionstore(java.lang.String, java.lang.String)
	 */
	public int sunionstore(String destination, String...keys) throws Exception
	{
		return integerReply(RedisCommandType.SUNIONSTORE, destination, keys);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISets#sdiff(java.lang.String)
	 */
	public String[] sdiff(String...keys) throws Exception
	{
		return (String[]) multiBulkReply(RedisCommandType.SDIFF, false, keys);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISets#sismember(java.lang.String, java.lang.String)
	 */
	public Boolean sismember(String key, String member) throws Exception
	{
		return integerReply(RedisCommandType.SISMEMBER, key, member)==1 ? true : false;
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISets#srandmember(java.lang.String)
	 */
	public String srandmember(String key) throws Exception
	{
		return (String) bulkReply(RedisCommandType.SRANDMEMBER, false, key);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISets#sdiffstore(java.lang.String, java.lang.String)
	 */
	public int sdiffstore(String destination, String...keys) throws Exception
	{
		return integerReply(RedisCommandType.SDIFFSTORE, destination, keys);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISets#smembers(java.lang.String)
	 */
	public String[] smembers(String key) throws Exception
	{
		return (String[]) multiBulkReply(RedisCommandType.SMEMBERS, false, key);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISets#srem(java.lang.String, java.lang.String)
	 */
	public Boolean srem(String key, String member) throws Exception
	{
		return integerReply(RedisCommandType.SREM, key, member)==1 ? true : false;
	}
}
