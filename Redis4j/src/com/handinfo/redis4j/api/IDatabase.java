/**
 * 
 */
package com.handinfo.redis4j.api;

/**
 * 当把Redis作为数据库时,实现以下功能
 * 与作为缓存的区别主要在于这里的所有操作均为String，不会对任何key对象做序列化,而缓存版本可以
 */
public interface IDatabase
{
	/**
	 * 提交任意信息,服务器接收后直接返回,不做任何处理,主要用于调试
	 */
	public String echo(String message);

	/**
	 * 向服务器发送ping消息,以防止连接时间过长被服务器断开,在通讯层已经调用了此功能,应用层通常不用调用
	 */
	public boolean ping();
	
	//hashes
	/**
	 * 删除Hashe对象中的指定字段
	 */
	public boolean hdel(String key, String field);

	/**
	 * 获取某Hashe对象中的全部字段
	 */
	public String[] hgetall(String key);

	/**
	 * 获取指定Hashe对象的长度
	 */
	public int hlen(String key);

	/**
	 * 
	 */
	public boolean hset(String key, String field, String value);

	/**
	 * 
	 */
	public boolean hexists(String key, String field);

	/**
	 * 
	 */
	public int hincrby(String key, String field, int increment);

	/**
	 * 
	 */
	public String[] hmget(String key, String field);

	/**
	 * 
	 */
	public boolean hsetnx(String key, String field, String value);

	/**
	 * 
	 */
	public String hget(String key, String field);

	/**
	 * 
	 */
	public String[] hkeys(String key);

	/**
	 * 
	 */
	public boolean hmset(String key, String field, String value);

	/**
	 * 
	 */
	public String[] hvals(String key);
	
	//keys
	/**
	 * 
	 */
	public int del(String... keys);

	/**
	 * 
	 */
	public String[] keys(String key);

	/**
	 * 
	 */
	public boolean rename(String key, String newKey);

	/**
	 * 
	 */
	public String type(String key);

	/**
	 * 
	 */
	public boolean exists(String key);

	/**
	 * 
	 */
	public boolean move(String key, int indexDB);

	/**
	 * 
	 */
	public boolean renamenx(String key, String newKey);

	/**
	 * 
	 */
	public boolean expire(String key, int seconds);

	/**
	 * 
	 */
	public boolean persist(String key);

	//TODO 暂时先写个简单版本的,后面在追加重载版本
	/**
	 * 
	 */
	public Object[] sort(String key, String... args);

	/**
	 * 
	 */
	public boolean expireat(String key, long timestamp);

	/**
	 * 
	 */
	public String randomkey();

	/**
	 * 
	 */
	public int ttl(String key);
	
	//Lists
	/**
	 * 
	 */
	public String[] blpop(String key, int timeout);

	/**
	 * 
	 */
	public int llen(String key);

	/**
	 * 
	 */
	public int lrem(String key, int count, String value);

	/**
	 * 
	 */
	public int rpush(String key, String value);

	/**
	 * 
	 */
	public String[] brpop(String key, int timeout);

	/**
	 * 
	 */
	public String lpop(String key);

	/**
	 * 
	 */
	public boolean lset(String key, int index, String value);

	/**
	 * 
	 */
	public int rpushx(String key, String value);

	/**
	 * 
	 */
	public String brpoplpush(String source, String destination, int timeout);

	/**
	 * 
	 */
	public int lpush(String key, String value);

	/**
	 * 
	 */
	public boolean ltrim(String key, int start, int stop);

	/**
	 * 
	 */
	public String lindex(String key, int index);

	/**
	 * 
	 */
	public int lpushx(String key, String value);

	/**
	 * 
	 */
	public String rpop(String key);

	/**
	 * 
	 */
	public int linsert(String key, String BEFORE_AFTER, String pivot, String value);

	/**
	 * 
	 */
	public String[] lrange(String key, int start, int stop);

	/**
	 * 
	 */
	public String rpoplpush(String source, String destination);
	
	//server
	/**
	 * 
	 */
	public String bgrewriteaof();

	/**
	 * 
	 */
	public String bgsave();

	/**
	 * 
	 */
	public String[] config_get(String parameter);

	/**
	 * 
	 */
	public boolean config_resetstat();

	/**
	 * 
	 */
	public boolean config_set(String parameter, String value);

	/**
	 * 
	 */
	public int dbsize();

	/**
	 * 
	 */
	public String[] debug_object(String key);

	/**
	 * 
	 */
	public String[] debug_segfault();

	/**
	 * 
	 */
	public boolean flushall();

	/**
	 * 
	 */
	public boolean flushdb();

	/**
	 * 
	 */
	public String info();

	/**
	 * 
	 */
	public int lastsave();

	/**
	 * 
	 */
	public boolean save();

	/**
	 * 
	 */
	public boolean shutdown();

	/**
	 * 
	 */
	public boolean slaveof();

	/**
	 * 
	 */
	public String[] sync();
	
	//Sets
	/**
	 * 
	 */
	public Boolean sadd(String key, String member);

	/**
	 * 
	 */
	public String[] sinter(String... keys);

	/**
	 * 
	 */
	public Boolean smove(String source, String destination, String member);

	/**
	 * 
	 */
	public String[] sunion(String... keys);

	/**
	 * 
	 */
	public int scard(String key);

	/**
	 * 
	 */
	public int sinterstore(String destination, String... keys);

	/**
	 * 
	 */
	public String spop(String key);

	/**
	 * 
	 */
	public int sunionstore(String destination, String... keys);

	/**
	 * 
	 */
	public String[] sdiff(String... keys);

	/**
	 * 
	 */
	public Boolean sismember(String key, String member);

	/**
	 * 
	 */
	public String srandmember(String key);

	/**
	 * 
	 */
	public int sdiffstore(String destination, String... keys);

	/**
	 * 
	 */
	public String[] smembers(String key);

	/**
	 * 
	 */
	public Boolean srem(String key, String member);
	
	//SortedSets
	/**
	 * 
	 */
	public Boolean zadd(String key, int score, String member);

	/**
	 * 
	 */
	public int zinterstore(String... args);

	/**
	 * 
	 */
	public Boolean zrem(String key, String member);

	/**
	 * 
	 */
	public String[] zrevrangebyscore(String... args);

	/**
	 * 
	 */
	public int zcard(String key);

	/**
	 * 
	 */
	public String[] zrange(String key, int start, int stop);

	/**
	 * 
	 */
	public int zremrangebyrank(String key, int start, int stop);

	/**
	 * 
	 */
	public int zrevrank(String key, String member);

	/**
	 * 
	 */
	public int zcount(String key, int min, int max);

	/**
	 * 
	 */
	public String[] zrangebyscore(String... args);

	/**
	 * 
	 */
	public int zremrangebyscore(String key, int min, int max);

	/**
	 * 
	 */
	public String zscore(String key, String member);

	/**
	 * 
	 */
	public String zincrby(String key, int increment, String member);

	/**
	 * 
	 */
	public int zrank(String key, String member);

	/**
	 * 
	 */
	public String[] zrevrange(String key, int start, int stop);

	/**
	 * 
	 */
	public int zunionstore(String... args);
	
	//Strings
	/**
	 * 
	 */
	public int append(String key, String value);

	/**
	 * 
	 */
	public String getrange(String key, int start, int end);

	/**
	 * 
	 */
	public Boolean mset(String key, String value);

	/**
	 * 
	 */
	public Boolean setnx(String key, String value);

	/**
	 * 
	 */
	public int decr(String key);

	/**
	 * 
	 */
	public String getset(String key, String value);

	/**
	 * 
	 */
	public Boolean msetnx(String key, String value);

	/**
	 * 
	 */
	public int setrange(String key, int offset, String value);

	/**
	 * 
	 */
	public int decrby(String key, int decrement);

	/**
	 * 
	 */
	public int incr(String key);

	/**
	 * 
	 */
	public boolean set(String key, String value);

	/**
	 * 
	 */
	public int strlen(String key);

	/**
	 * 
	 */
	public String get(String key);

	/**
	 * 
	 */
	public int incrby(String key, int increment);

	/**
	 * 
	 */
	public int setbit(String key, int offset, int value);

	/**
	 * 
	 */
	public int getbit(String key, int offset);

	/**
	 * 
	 */
	public String[] mget(String... keys);

	/**
	 * 
	 */
	public boolean setex(String key, int seconds, String value);
}
