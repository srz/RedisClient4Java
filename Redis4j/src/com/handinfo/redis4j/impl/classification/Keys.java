package com.handinfo.redis4j.impl.classification;

import com.handinfo.redis4j.api.IConnector;
import com.handinfo.redis4j.api.RedisCommand;
import com.handinfo.redis4j.api.RedisResponseMessage;
import com.handinfo.redis4j.api.classification.IKeys;
import com.handinfo.redis4j.impl.CommandExecutor;

public class Keys extends CommandExecutor implements IKeys
{

	public Keys(IConnector connector)
	{
		super(connector);
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IKeys#del(java.lang.String)
	 */
	public int del(String... keys)
	{
		return integerReply(RedisCommand.DEL, keys);
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IKeys#keys(java.lang.String)
	 */
	public String[] keys(String key)
	{
		return (String[]) multiBulkReply(RedisCommand.KEYS, false, key);
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IKeys#rename(java.lang.String, java.lang.String)
	 */
	public boolean rename(String key, String newKey)
	{
		return singleLineReplyForBoolean(RedisCommand.RENAME, RedisResponseMessage.OK, key, newKey);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IKeys#type(java.lang.String)
	 */
	public String type(String key)
	{
		return singleLineReplyForString(RedisCommand.TYPE,  key);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IKeys#exists(java.lang.String)
	 */
	public boolean exists(String key)
	{
		return integerReply(RedisCommand.EXISTS, key)==1 ? true : false;
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IKeys#move(java.lang.String, int)
	 */
	public boolean move(String key, int indexDB)
	{
		return integerReply(RedisCommand.MOVE, key, indexDB)==1 ? true : false;
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IKeys#renamenx(java.lang.String, java.lang.String)
	 */
	public boolean renamenx(String key, String newKey)
	{
		return integerReply(RedisCommand.RENAMENX, key, newKey)==1 ? true : false;
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IKeys#expire(java.lang.String, int)
	 */
	public boolean expire(String key, int seconds)
	{
		return integerReply(RedisCommand.EXPIRE, key, seconds)==1 ? true : false;
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IKeys#persist(java.lang.String)
	 */
	public boolean persist(String key)
	{
		return integerReply(RedisCommand.PERSIST, key)==1 ? true : false;
	}
	
	//TODO 暂时先写个简单版本的,后面在追加重载版本
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IKeys#sort(java.lang.String, java.lang.String)
	 */
	public Object[] sort(String key, String...args)
	{
		return multiBulkReply(RedisCommand.SORT, false, key, args);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IKeys#expireat(java.lang.String, long)
	 */
	public boolean expireat(String key,  long timestamp)
	{
		return integerReply(RedisCommand.EXPIREAT, key, timestamp)==1 ? true : false;
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IKeys#randomkey()
	 */
	public String randomkey()
	{
		return (String) bulkReply(RedisCommand.RANDOMKEY, false);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IKeys#ttl(java.lang.String)
	 */
	public int ttl(String key)
	{
		return integerReply(RedisCommand.TTL, key);
	}
}
