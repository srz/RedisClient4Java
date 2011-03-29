/**
 * 
 */
package com.handinfo.redis4j.impl.database;

import com.handinfo.redis4j.api.RedisCommand;
import com.handinfo.redis4j.api.database.IDatabaseBatch;
import com.handinfo.redis4j.api.database.IDatabaseTransaction;
import com.handinfo.redis4j.api.database.IRedisDatabaseClient;
import com.handinfo.redis4j.impl.RedisClient;

/**
 * Database版本客户端
 */
public final class RedisDatabaseClient extends RedisClient implements IRedisDatabaseClient
{
	public RedisDatabaseClient(String host, int port, int indexDB, int heartbeatTime, int reconnectDelay)
	{
		super(host, port, indexDB, heartbeatTime, reconnectDelay);
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.database.IRedisDatabaseClient#getBatch()
	 */
	@Override
	public IDatabaseBatch getBatch()
	{
		return new DatabaseBatch(super.connector);
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.database.IRedisDatabaseClient#getBatch(boolean)
	 */
	@Override
	public IDatabaseTransaction getTransaction()
	{
		return new DatabaseTransaction(super.connector);
	}


	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#append(java.lang.String, java.lang.String)
	 */
	@Override
	public int append(String key, String value)
	{
		return super.sendRequest(Integer.class, RedisCommand.APPEND, key, value);
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#bgrewriteaof()
	 */
	@Override
	public String bgrewriteaof()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#bgsave()
	 */
	@Override
	public String bgsave()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#blpop(java.lang.String, int)
	 */
	@Override
	public String[] blpop(String key, int timeout)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#brpop(java.lang.String, int)
	 */
	@Override
	public String[] brpop(String key, int timeout)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#brpoplpush(java.lang.String, java.lang.String, int)
	 */
	@Override
	public String brpoplpush(String source, String destination, int timeout)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#config_get(java.lang.String)
	 */
	@Override
	public String[] config_get(String parameter)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#config_resetstat()
	 */
	@Override
	public boolean config_resetstat()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#config_set(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean config_set(String parameter, String value)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#dbsize()
	 */
	@Override
	public int dbsize()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#debug_object(java.lang.String)
	 */
	@Override
	public String[] debug_object(String key)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#debug_segfault()
	 */
	@Override
	public String[] debug_segfault()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#decr(java.lang.String)
	 */
	@Override
	public int decr(String key)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#decrby(java.lang.String, int)
	 */
	@Override
	public int decrby(String key, int decrement)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#del(java.lang.String[])
	 */
	@Override
	public int del(String... keys)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#echo(java.lang.String)
	 */
	@Override
	public String echo(String message)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#exists(java.lang.String)
	 */
	@Override
	public boolean exists(String key)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#expire(java.lang.String, int)
	 */
	@Override
	public boolean expire(String key, int seconds)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#expireat(java.lang.String, long)
	 */
	@Override
	public boolean expireat(String key, long timestamp)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#flushall()
	 */
	@Override
	public boolean flushall()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#flushdb()
	 */
	@Override
	public boolean flushdb()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#get(java.lang.String)
	 */
	@Override
	public String get(String key)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#getbit(java.lang.String, int)
	 */
	@Override
	public int getbit(String key, int offset)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#getrange(java.lang.String, int, int)
	 */
	@Override
	public String getrange(String key, int start, int end)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#getset(java.lang.String, java.lang.String)
	 */
	@Override
	public String getset(String key, String value)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#hdel(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean hdel(String key, String field)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#hexists(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean hexists(String key, String field)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#hget(java.lang.String, java.lang.String)
	 */
	@Override
	public String hget(String key, String field)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#hgetall(java.lang.String)
	 */
	@Override
	public String[] hgetall(String key)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#hincrby(java.lang.String, java.lang.String, int)
	 */
	@Override
	public int hincrby(String key, String field, int increment)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#hkeys(java.lang.String)
	 */
	@Override
	public String[] hkeys(String key)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#hlen(java.lang.String)
	 */
	@Override
	public int hlen(String key)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#hmget(java.lang.String, java.lang.String)
	 */
	@Override
	public String[] hmget(String key, String field)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#hmset(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean hmset(String key, String field, String value)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#hset(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean hset(String key, String field, String value)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#hsetnx(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean hsetnx(String key, String field, String value)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#hvals(java.lang.String)
	 */
	@Override
	public String[] hvals(String key)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#incr(java.lang.String)
	 */
	@Override
	public int incr(String key)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#incrby(java.lang.String, int)
	 */
	@Override
	public int incrby(String key, int increment)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#info()
	 */
	@Override
	public String info()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#keys(java.lang.String)
	 */
	@Override
	public String[] keys(String key)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#lastsave()
	 */
	@Override
	public int lastsave()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#lindex(java.lang.String, int)
	 */
	@Override
	public String lindex(String key, int index)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#linsert(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public int linsert(String key, String BEFORE_AFTER, String pivot, String value)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#llen(java.lang.String)
	 */
	@Override
	public int llen(String key)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#lpop(java.lang.String)
	 */
	@Override
	public String lpop(String key)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#lpush(java.lang.String, java.lang.String)
	 */
	@Override
	public int lpush(String key, String value)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#lpushx(java.lang.String, java.lang.String)
	 */
	@Override
	public int lpushx(String key, String value)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#lrange(java.lang.String, int, int)
	 */
	@Override
	public String[] lrange(String key, int start, int stop)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#lrem(java.lang.String, int, java.lang.String)
	 */
	@Override
	public int lrem(String key, int count, String value)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#lset(java.lang.String, int, java.lang.String)
	 */
	@Override
	public boolean lset(String key, int index, String value)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#ltrim(java.lang.String, int, int)
	 */
	@Override
	public boolean ltrim(String key, int start, int stop)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#mget(java.lang.String[])
	 */
	@Override
	public String[] mget(String... keys)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#move(java.lang.String, int)
	 */
	@Override
	public boolean move(String key, int indexDB)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#mset(java.lang.String, java.lang.String)
	 */
	@Override
	public Boolean mset(String key, String value)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#msetnx(java.lang.String, java.lang.String)
	 */
	@Override
	public Boolean msetnx(String key, String value)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#persist(java.lang.String)
	 */
	@Override
	public boolean persist(String key)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#ping()
	 */
	@Override
	public boolean ping()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#randomkey()
	 */
	@Override
	public String randomkey()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#rename(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean rename(String key, String newKey)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#renamenx(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean renamenx(String key, String newKey)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#rpop(java.lang.String)
	 */
	@Override
	public String rpop(String key)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#rpoplpush(java.lang.String, java.lang.String)
	 */
	@Override
	public String rpoplpush(String source, String destination)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#rpush(java.lang.String, java.lang.String)
	 */
	@Override
	public int rpush(String key, String value)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#rpushx(java.lang.String, java.lang.String)
	 */
	@Override
	public int rpushx(String key, String value)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#sadd(java.lang.String, java.lang.String)
	 */
	@Override
	public Boolean sadd(String key, String member)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#save()
	 */
	@Override
	public boolean save()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#scard(java.lang.String)
	 */
	@Override
	public int scard(String key)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#sdiff(java.lang.String[])
	 */
	@Override
	public String[] sdiff(String... keys)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#sdiffstore(java.lang.String, java.lang.String[])
	 */
	@Override
	public int sdiffstore(String destination, String... keys)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#set(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean set(String key, String value)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#setbit(java.lang.String, int, int)
	 */
	@Override
	public int setbit(String key, int offset, int value)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#setex(java.lang.String, int, java.lang.String)
	 */
	@Override
	public boolean setex(String key, int seconds, String value)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#setnx(java.lang.String, java.lang.String)
	 */
	@Override
	public Boolean setnx(String key, String value)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#setrange(java.lang.String, int, java.lang.String)
	 */
	@Override
	public int setrange(String key, int offset, String value)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#shutdown()
	 */
	@Override
	public boolean shutdown()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#sinter(java.lang.String[])
	 */
	@Override
	public String[] sinter(String... keys)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#sinterstore(java.lang.String, java.lang.String[])
	 */
	@Override
	public int sinterstore(String destination, String... keys)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#sismember(java.lang.String, java.lang.String)
	 */
	@Override
	public Boolean sismember(String key, String member)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#slaveof()
	 */
	@Override
	public boolean slaveof()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#smembers(java.lang.String)
	 */
	@Override
	public String[] smembers(String key)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#smove(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Boolean smove(String source, String destination, String member)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#sort(java.lang.String, java.lang.String[])
	 */
	@Override
	public Object[] sort(String key, String... args)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#spop(java.lang.String)
	 */
	@Override
	public String spop(String key)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#srandmember(java.lang.String)
	 */
	@Override
	public String srandmember(String key)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#srem(java.lang.String, java.lang.String)
	 */
	@Override
	public Boolean srem(String key, String member)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#strlen(java.lang.String)
	 */
	@Override
	public int strlen(String key)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#sunion(java.lang.String[])
	 */
	@Override
	public String[] sunion(String... keys)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#sunionstore(java.lang.String, java.lang.String[])
	 */
	@Override
	public int sunionstore(String destination, String... keys)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#sync()
	 */
	@Override
	public String[] sync()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#ttl(java.lang.String)
	 */
	@Override
	public int ttl(String key)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#type(java.lang.String)
	 */
	@Override
	public String type(String key)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#zadd(java.lang.String, int, java.lang.String)
	 */
	@Override
	public Boolean zadd(String key, int score, String member)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#zcard(java.lang.String)
	 */
	@Override
	public int zcard(String key)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#zcount(java.lang.String, int, int)
	 */
	@Override
	public int zcount(String key, int min, int max)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#zincrby(java.lang.String, int, java.lang.String)
	 */
	@Override
	public String zincrby(String key, int increment, String member)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#zinterstore(java.lang.String[])
	 */
	@Override
	public int zinterstore(String... args)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#zrange(java.lang.String, int, int)
	 */
	@Override
	public String[] zrange(String key, int start, int stop)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#zrangebyscore(java.lang.String[])
	 */
	@Override
	public String[] zrangebyscore(String... args)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#zrank(java.lang.String, java.lang.String)
	 */
	@Override
	public int zrank(String key, String member)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#zrem(java.lang.String, java.lang.String)
	 */
	@Override
	public Boolean zrem(String key, String member)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#zremrangebyrank(java.lang.String, int, int)
	 */
	@Override
	public int zremrangebyrank(String key, int start, int stop)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#zremrangebyscore(java.lang.String, int, int)
	 */
	@Override
	public int zremrangebyscore(String key, int min, int max)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#zrevrange(java.lang.String, int, int)
	 */
	@Override
	public String[] zrevrange(String key, int start, int stop)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#zrevrangebyscore(java.lang.String[])
	 */
	@Override
	public String[] zrevrangebyscore(String... args)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#zrevrank(java.lang.String, java.lang.String)
	 */
	@Override
	public int zrevrank(String key, String member)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#zscore(java.lang.String, java.lang.String)
	 */
	@Override
	public String zscore(String key, String member)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IDatabase#zunionstore(java.lang.String[])
	 */
	@Override
	public int zunionstore(String... args)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IConnection#quit()
	 */
	@Override
	public boolean quit()
	{
		super.connector.disConnect();
		return true;
	}



	

}
