package com.handinfo.redis4j.impl.classification;

import com.handinfo.redis4j.api.IConnector;
import com.handinfo.redis4j.api.RedisCommandType;
import com.handinfo.redis4j.api.classification.ISortedSets;
import com.handinfo.redis4j.impl.CommandExecutor;

public class SortedSets extends CommandExecutor implements ISortedSets
{

	public SortedSets(IConnector connector)
	{
		super(connector);
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISortedSets#zadd(java.lang.String, int, java.lang.String)
	 */
	public Boolean zadd(String key, int score, String member)
	{
		return integerReply(RedisCommandType.ZADD, key, score, member)==1 ? true : false;
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISortedSets#zinterstore(java.lang.String)
	 */
	public int zinterstore(String...args)
	{
		return integerReply(RedisCommandType.ZINTERSTORE, args);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISortedSets#zrem(java.lang.String, java.lang.String)
	 */
	public Boolean zrem(String key, String member)
	{
		return integerReply(RedisCommandType.ZREM, key, member)==1 ? true : false;
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISortedSets#zrevrangebyscore(java.lang.String)
	 */
	public String[] zrevrangebyscore(String...args)
	{
		return (String[]) multiBulkReply(RedisCommandType.ZREVRANGEBYSCORE, false, args);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISortedSets#zcard(java.lang.String)
	 */
	public int zcard(String key)
	{
		return integerReply(RedisCommandType.ZCARD, key);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISortedSets#zrange(java.lang.String, int, int)
	 */
	public String[] zrange(String key, int start, int stop)
	{
		return (String[]) multiBulkReply(RedisCommandType.ZRANGE, false, key, start, stop);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISortedSets#zremrangebyrank(java.lang.String, int, int)
	 */
	public int zremrangebyrank(String key, int start, int stop)
	{
		return integerReply(RedisCommandType.ZREMRANGEBYRANK, key, start, stop);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISortedSets#zrevrank(java.lang.String, java.lang.String)
	 */
	public int zrevrank(String key, String member)
	{
		return integerReply(RedisCommandType.ZREVRANK, key, member);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISortedSets#zcount(java.lang.String, int, int)
	 */
	public int zcount(String key, int min, int max)
	{
		return integerReply(RedisCommandType.ZCOUNT, key, min, max);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISortedSets#zrangebyscore(java.lang.String)
	 */
	public String[] zrangebyscore(String...args)
	{
		return (String[]) multiBulkReply(RedisCommandType.ZRANGEBYSCORE, false, args);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISortedSets#zremrangebyscore(java.lang.String, int, int)
	 */
	public int zremrangebyscore(String key, int min, int max)
	{
		return integerReply(RedisCommandType.ZREMRANGEBYSCORE, key, min, max);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISortedSets#zscore(java.lang.String, java.lang.String)
	 */
	public String zscore(String key, String member)
	{
		return (String) bulkReply(RedisCommandType.ZSCORE, false, key, member);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISortedSets#zincrby(java.lang.String, int, java.lang.String)
	 */
	public String zincrby(String key, int increment, String member)
	{
		return (String) bulkReply(RedisCommandType.ZINCRBY, false, key, increment, member);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISortedSets#zrank(java.lang.String, java.lang.String)
	 */
	public int zrank(String key, String member)
	{
		return integerReply(RedisCommandType.ZRANK, key, member);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISortedSets#zrevrange(java.lang.String, int, int)
	 */
	public String[] zrevrange(String key, int start, int stop)
	{
		return (String[]) multiBulkReply(RedisCommandType.ZREVRANGE, false, key, start, stop);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.ISortedSets#zunionstore(java.lang.String)
	 */
	public int zunionstore(String...args)
	{
		return integerReply(RedisCommandType.ZUNIONSTORE, args);
	}
}
