package com.handinfo.redis4j.impl.classification;

import com.handinfo.redis4j.api.IConnector;
import com.handinfo.redis4j.api.RedisCommand;
import com.handinfo.redis4j.api.classification.ISets;
import com.handinfo.redis4j.impl.CommandExecutor;

public class Sets extends CommandExecutor implements ISets
{

	public Sets(IConnector connector)
	{
		super(connector);
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISets#sadd(java.lang.String, java.lang.String)
	 */
	public Boolean sadd(String key, String member)
	{
		return integerReply(RedisCommand.SADD, key, member)==1 ? true : false;
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISets#sinter(java.lang.String)
	 */
	public String[] sinter(String...keys)
	{
		return (String[]) multiBulkReply(RedisCommand.SINTER, false, keys);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISets#smove(java.lang.String, java.lang.String, java.lang.String)
	 */
	public Boolean smove(String source, String destination, String member)
	{
		return integerReply(RedisCommand.SMOVE, source, destination, member)==1 ? true : false;
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISets#sunion(java.lang.String)
	 */
	public String[] sunion(String...keys)
	{
		return (String[]) multiBulkReply(RedisCommand.SUNION, false, keys);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISets#scard(java.lang.String)
	 */
	public int scard(String key)
	{
		return integerReply(RedisCommand.SCARD, key);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISets#sinterstore(java.lang.String, java.lang.String)
	 */
	public int sinterstore(String destination, String...keys)
	{
		return integerReply(RedisCommand.SINTERSTORE, destination, keys);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISets#spop(java.lang.String)
	 */
	public String spop(String key)
	{
		return (String) bulkReply(RedisCommand.SPOP, false, key);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISets#sunionstore(java.lang.String, java.lang.String)
	 */
	public int sunionstore(String destination, String...keys)
	{
		return integerReply(RedisCommand.SUNIONSTORE, destination, keys);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISets#sdiff(java.lang.String)
	 */
	public String[] sdiff(String...keys)
	{
		return (String[]) multiBulkReply(RedisCommand.SDIFF, false, keys);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISets#sismember(java.lang.String, java.lang.String)
	 */
	public Boolean sismember(String key, String member)
	{
		return integerReply(RedisCommand.SISMEMBER, key, member)==1 ? true : false;
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISets#srandmember(java.lang.String)
	 */
	public String srandmember(String key)
	{
		return (String) bulkReply(RedisCommand.SRANDMEMBER, false, key);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISets#sdiffstore(java.lang.String, java.lang.String)
	 */
	public int sdiffstore(String destination, String...keys)
	{
		return integerReply(RedisCommand.SDIFFSTORE, destination, keys);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISets#smembers(java.lang.String)
	 */
	public String[] smembers(String key)
	{
		return (String[]) multiBulkReply(RedisCommand.SMEMBERS, false, key);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISets#srem(java.lang.String, java.lang.String)
	 */
	public Boolean srem(String key, String member)
	{
		return integerReply(RedisCommand.SREM, key, member)==1 ? true : false;
	}
}
