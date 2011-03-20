package com.handinfo.redis4j.impl;

import com.handinfo.redis4j.api.IStrings;
import com.handinfo.redis4j.api.RedisCommandType;
import com.handinfo.redis4j.api.RedisResultInfo;
import com.handinfo.redis4j.impl.transfers.Connector;

public class Strings extends BaseCommand implements IStrings
{

	public Strings(Connector connector)
	{
		super(connector);
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IStrings#append(java.lang.String, java.lang.String)
	 */
	public int append(String key, String value) throws Exception
	{
		return integerReply(RedisCommandType.APPEND, key);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IStrings#getrange(java.lang.String, int, int)
	 */
	public String getrange(String key, int start, int end) throws Exception
	{
		return (String) bulkReply(RedisCommandType.GETRANGE, false, start, end);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IStrings#mset(java.lang.String, java.lang.String)
	 */
	public Boolean mset(String key, String value) throws Exception
	{
		return singleLineReplyForBoolean(RedisCommandType.MSET, RedisResultInfo.OK, value);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IStrings#setnx(java.lang.String, java.lang.String)
	 */
	public Boolean setnx(String key, String value) throws Exception
	{
		return integerReply(RedisCommandType.SETNX, key)==1 ? true : false;
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IStrings#decr(java.lang.String)
	 */
	public int decr(String key) throws Exception
	{
		return integerReply(RedisCommandType.DECR, key);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IStrings#getset(java.lang.String, java.lang.String)
	 */
	public String getset(String key, String value) throws Exception
	{
		return (String) bulkReply(RedisCommandType.GETSET, false, value);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IStrings#msetnx(java.lang.String, java.lang.String)
	 */
	public Boolean msetnx(String key, String value) throws Exception
	{
		return integerReply(RedisCommandType.MSETNX, key)==1 ? true : false;
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IStrings#setrange(java.lang.String, int, java.lang.String)
	 */
	public int setrange(String key, int offset, String value) throws Exception
	{
		return integerReply(RedisCommandType.SETRANGE, key, offset, value);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IStrings#decrby(java.lang.String, int)
	 */
	public int decrby(String key, int decrement) throws Exception
	{
		return integerReply(RedisCommandType.DECRBY, key, decrement);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IStrings#incr(java.lang.String)
	 */
	public int incr(String key) throws Exception
	{
		return integerReply(RedisCommandType.INCR, key);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IStrings#set(java.lang.String, java.lang.String)
	 */
	public boolean set(String key, String value) throws Exception
	{
		return singleLineReplyForBoolean(RedisCommandType.SET, RedisResultInfo.OK, key, value);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IStrings#strlen(java.lang.String)
	 */
	public int strlen(String key) throws Exception
	{
		return integerReply(RedisCommandType.STRLEN, key);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IStrings#get(java.lang.String)
	 */
	public String get(String key) throws Exception
	{
		return (String) bulkReply(RedisCommandType.GET, false, key);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IStrings#incrby(java.lang.String, int)
	 */
	public int incrby(String key, int increment) throws Exception
	{
		return integerReply(RedisCommandType.INCRBY, key, increment);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IStrings#setbit(java.lang.String, int, int)
	 */
	public int setbit(String key, int offset, int value) throws Exception
	{
		return integerReply(RedisCommandType.SETBIT, key, offset, value);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IStrings#getbit(java.lang.String, int)
	 */
	public int getbit(String key, int offset) throws Exception
	{
		return integerReply(RedisCommandType.GETBIT, key, offset);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IStrings#mget(java.lang.String)
	 */
	public String[] mget(String...keys) throws Exception
	{
		return (String[]) multiBulkReply(RedisCommandType.MGET, false, keys);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IStrings#setex(java.lang.String, int, java.lang.String)
	 */
	public boolean setex(String key, int seconds, String value) throws Exception
	{
		return singleLineReplyForBoolean(RedisCommandType.SETEX, RedisResultInfo.OK, key, seconds, value);
	}
}
