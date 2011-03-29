package com.handinfo.redis4j.impl.database;

import com.handinfo.redis4j.api.IConnector;
import com.handinfo.redis4j.api.RedisCommand;
import com.handinfo.redis4j.api.database.IDatabaseTransaction;

public class DatabaseTransaction extends BatchCommandlist implements IDatabaseTransaction
{
	public DatabaseTransaction(IConnector connector)
	{
		super(connector);
		super.addCommand(RedisCommand.MULTI);
	}

	@Override
	public void commit()
	{
		super.addCommand(RedisCommand.EXEC);
		super.connector.executeBatch(super.commandList);
	}

	@Override
	public boolean discard()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean unwatch()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean watch()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int append(String key, String value)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String bgrewriteaof()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String bgsave()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] blpop(String key, int timeout)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] brpop(String key, int timeout)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String brpoplpush(String source, String destination, int timeout)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] config_get(String parameter)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean config_resetstat()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean config_set(String parameter, String value)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int dbsize()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String[] debug_object(String key)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] debug_segfault()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int decr(String key)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int decrby(String key, int decrement)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int del(String... keys)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String echo(String message)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists(String key)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean expire(String key, int seconds)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean expireat(String key, long timestamp)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean flushall()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean flushdb()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String get(String key)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getbit(String key, int offset)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getrange(String key, int start, int end)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getset(String key, String value)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hdel(String key, String field)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hexists(String key, String field)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String hget(String key, String field)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] hgetall(String key)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int hincrby(String key, String field, int increment)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String[] hkeys(String key)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int hlen(String key)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String[] hmget(String key, String field)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hmset(String key, String field, String value)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hset(String key, String field, String value)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hsetnx(String key, String field, String value)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String[] hvals(String key)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int incr(String key)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int incrby(String key, int increment)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String info()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] keys(String key)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int lastsave()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String lindex(String key, int index)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int linsert(String key, String BEFORE_AFTER, String pivot, String value)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int llen(String key)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String lpop(String key)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int lpush(String key, String value)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int lpushx(String key, String value)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String[] lrange(String key, int start, int stop)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int lrem(String key, int count, String value)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean lset(String key, int index, String value)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ltrim(String key, int start, int stop)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String[] mget(String... keys)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean move(String key, int indexDB)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Boolean mset(String key, String value)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean msetnx(String key, String value)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean persist(String key)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ping()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String randomkey()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean rename(String key, String newKey)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean renamenx(String key, String newKey)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String rpop(String key)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String rpoplpush(String source, String destination)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int rpush(String key, String value)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int rpushx(String key, String value)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Boolean sadd(String key, String member)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean save()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int scard(String key)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String[] sdiff(String... keys)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int sdiffstore(String destination, String... keys)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean set(String key, String value)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int setbit(String key, int offset, int value)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean setex(String key, int seconds, String value)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Boolean setnx(String key, String value)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int setrange(String key, int offset, String value)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean shutdown()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String[] sinter(String... keys)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int sinterstore(String destination, String... keys)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Boolean sismember(String key, String member)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean slaveof()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String[] smembers(String key)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean smove(String source, String destination, String member)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] sort(String key, String... args)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String spop(String key)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String srandmember(String key)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean srem(String key, String member)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int strlen(String key)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String[] sunion(String... keys)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int sunionstore(String destination, String... keys)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String[] sync()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int ttl(String key)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String type(String key)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean zadd(String key, int score, String member)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int zcard(String key)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int zcount(String key, int min, int max)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String zincrby(String key, int increment, String member)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int zinterstore(String... args)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String[] zrange(String key, int start, int stop)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] zrangebyscore(String... args)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int zrank(String key, String member)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Boolean zrem(String key, String member)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int zremrangebyrank(String key, int start, int stop)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int zremrangebyscore(String key, int min, int max)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String[] zrevrange(String key, int start, int stop)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] zrevrangebyscore(String... args)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int zrevrank(String key, String member)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String zscore(String key, String member)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int zunionstore(String... args)
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
