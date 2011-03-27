package com.handinfo.redis4j.api;

public enum RedisCommand
{
	DEL("DEL", OperateType.SYNC, Classification.KEYS,  ""),
	KEYS("KEYS", OperateType.SYNC, Classification.KEYS,  ""),
	RENAME("RENAME", OperateType.SYNC, Classification.KEYS,  ""),
	TYPE("TYPE", OperateType.SYNC, Classification.KEYS,  ""),
	EXISTS("EXISTS", OperateType.SYNC, Classification.KEYS,  ""),
	MOVE("MOVE", OperateType.SYNC, Classification.KEYS,  ""),
	RENAMENX("RENAMENX", OperateType.SYNC, Classification.KEYS,  ""),
	EXPIRE("EXPIRE", OperateType.SYNC, Classification.KEYS,  ""),
	PERSIST("PERSIST", OperateType.SYNC, Classification.KEYS,  ""),
	SORT("SORT", OperateType.SYNC, Classification.KEYS,  ""),
	EXPIREAT("EXPIREAT", OperateType.SYNC, Classification.KEYS,  ""),
	RANDOMKEY("RANDOMKEY", OperateType.SYNC, Classification.KEYS,  ""),
	TTL("TTL", OperateType.SYNC, Classification.KEYS,  ""),

	APPEND("APPEND", OperateType.SYNC, Classification.STRINGS,  ""),
	GETRANGE("GETRANGE", OperateType.SYNC, Classification.STRINGS,  ""),
	MSET("MSET", OperateType.SYNC, Classification.STRINGS,  ""),
	SETNX("SETNX", OperateType.SYNC, Classification.STRINGS,  ""),
	DECR("DECR", OperateType.SYNC, Classification.STRINGS,  ""),
	GETSET("GETSET", OperateType.SYNC, Classification.STRINGS,  ""),
	MSETNX("MSETNX", OperateType.SYNC, Classification.STRINGS,  ""),
	SETRANGE("SETRANGE", OperateType.SYNC, Classification.STRINGS,  ""),
	DECRBY("DECRBY", OperateType.SYNC, Classification.STRINGS,  ""),
	INCR("INCR", OperateType.SYNC, Classification.STRINGS,  ""),
	SET("SET", OperateType.SYNC, Classification.STRINGS,  ""),
	STRLEN("STRLEN", OperateType.SYNC, Classification.STRINGS,  ""),
	GET("GET", OperateType.SYNC, Classification.STRINGS,  ""),
	INCRBY("INCRBY", OperateType.SYNC, Classification.STRINGS,  ""),
	SETBIT("SETBIT", OperateType.SYNC, Classification.STRINGS,  ""),
	GETBIT("GETBIT", OperateType.SYNC, Classification.STRINGS,  ""),
	MGET("MGET", OperateType.SYNC, Classification.STRINGS,  ""),
	SETEX("SETEX", OperateType.SYNC, Classification.STRINGS,  ""),
		
	HDEL("HDEL", OperateType.SYNC, Classification.HASHES,  ""),
	HGETALL("HGETALL", OperateType.SYNC, Classification.HASHES,  ""),
	HLEN("HLEN", OperateType.SYNC, Classification.HASHES,  ""),
	HSET("HSET", OperateType.SYNC, Classification.HASHES,  ""),
	HEXISTS("HEXISTS", OperateType.SYNC, Classification.HASHES,  ""),
	HINCRBY("HINCRBY", OperateType.SYNC, Classification.HASHES,  ""),
	HMGET("HMGET", OperateType.SYNC, Classification.HASHES,  ""),
	HSETNX("HSETNX", OperateType.SYNC, Classification.HASHES,  ""),
	HGET("HGET", OperateType.SYNC, Classification.HASHES,  ""),
	HKEYS("HKEYS", OperateType.SYNC, Classification.HASHES,  ""),
	HMSET("HMSET", OperateType.SYNC, Classification.HASHES,  ""),
	HVALS("HVALS", OperateType.SYNC, Classification.HASHES,  ""),
	
	BLPOP("BLPOP", OperateType.SYNC, Classification.LISTS,  ""),
	LLEN("LLEN", OperateType.SYNC, Classification.LISTS,  ""),
	LREM("LREM", OperateType.SYNC, Classification.LISTS,  ""),
	RPUSH("RPUSH", OperateType.SYNC, Classification.LISTS,  ""),
	BRPOP("BRPOP", OperateType.SYNC, Classification.LISTS,  ""),
	LPOP("LPOP", OperateType.SYNC, Classification.LISTS,  ""),
	LSET("LSET", OperateType.SYNC, Classification.LISTS,  ""),
	RPUSHX("RPUSHX", OperateType.SYNC, Classification.LISTS,  ""),
	BRPOPLPUSH("BRPOPLPUSH", OperateType.SYNC, Classification.LISTS,  ""),
	LPUSH("LPUSH", OperateType.SYNC, Classification.LISTS,  ""),
	LTRIM("LTRIM", OperateType.SYNC, Classification.LISTS,  ""),
	LINDEX("LINDEX", OperateType.SYNC, Classification.LISTS,  ""),
	LPUSHX("LPUSHX", OperateType.SYNC, Classification.LISTS,  ""),
	RPOP("RPOP", OperateType.SYNC, Classification.LISTS,  ""),
	LINSERT("LINSERT", OperateType.SYNC, Classification.LISTS,  ""),
	LRANGE("LRANGE", OperateType.SYNC, Classification.LISTS,  ""),
	RPOPLPUSH("RPOPLPUSH", OperateType.SYNC, Classification.LISTS,  ""),
	
	SADD("SADD", OperateType.SYNC, Classification.SETS,  ""),
	SINTER("SINTER", OperateType.SYNC, Classification.SETS,  ""),
	SMOVE("SMOVE", OperateType.SYNC, Classification.SETS,  ""),
	SUNION("SUNION", OperateType.SYNC, Classification.SETS,  ""),
	SCARD("SCARD", OperateType.SYNC, Classification.SETS,  ""),
	SINTERSTORE("SINTERSTORE", OperateType.SYNC, Classification.SETS,  ""),
	SPOP("SPOP", OperateType.SYNC, Classification.SETS,  ""),
	SUNIONSTORE("SUNIONSTORE", OperateType.SYNC, Classification.SETS,  ""),
	SDIFF("SDIFF", OperateType.SYNC, Classification.SETS,  ""),
	SISMEMBER("SISMEMBER", OperateType.SYNC, Classification.SETS,  ""),
	SRANDMEMBER("SRANDMEMBER", OperateType.SYNC, Classification.SETS,  ""),
	SDIFFSTORE("SDIFFSTORE", OperateType.SYNC, Classification.SETS,  ""),
	SMEMBERS("SMEMBERS", OperateType.SYNC, Classification.SETS,  ""),	
	SREM("SREM", OperateType.SYNC, Classification.SETS,  ""),

	ZADD("ZADD", OperateType.SYNC, Classification.SORTEDSETS,  ""),
	ZINTERSTORE("ZINTERSTORE", OperateType.SYNC, Classification.SORTEDSETS,  ""),
	ZREM("ZREM", OperateType.SYNC, Classification.SORTEDSETS,  ""),
	ZREVRANGEBYSCORE("ZREVRANGEBYSCORE", OperateType.SYNC, Classification.SORTEDSETS,  ""),
	ZCARD("ZCARD", OperateType.SYNC, Classification.SORTEDSETS,  ""),
	ZRANGE("ZRANGE", OperateType.SYNC, Classification.SORTEDSETS,  ""),
	ZREMRANGEBYRANK("ZREMRANGEBYRANK", OperateType.SYNC, Classification.SORTEDSETS,  ""),
	ZREVRANK("ZREVRANK", OperateType.SYNC, Classification.SORTEDSETS,  ""),
	ZCOUNT("ZCOUNT", OperateType.SYNC, Classification.SORTEDSETS,  ""),
	ZRANGEBYSCORE("ZRANGEBYSCORE", OperateType.SYNC, Classification.SORTEDSETS,  ""),
	ZREMRANGEBYSCORE("ZREMRANGEBYSCORE", OperateType.SYNC, Classification.SORTEDSETS,  ""),
	ZSCORE("ZSCORE", OperateType.SYNC, Classification.SORTEDSETS,  ""),
	ZINCRBY("ZINCRBY", OperateType.SYNC, Classification.SORTEDSETS,  ""),
	ZRANK("ZRANK", OperateType.SYNC, Classification.SORTEDSETS,  ""),
	ZREVRANGE("ZREVRANGE", OperateType.SYNC, Classification.SORTEDSETS,  ""),
	ZUNIONSTORE("ZUNIONSTORE", OperateType.SYNC, Classification.SORTEDSETS,  ""),
	
	PSUBSCRIBE("PSUBSCRIBE", OperateType.SYNC, Classification.PUBSUB,  ""),
	PUNSUBSCRIBE("PUNSUBSCRIBE", OperateType.SYNC, Classification.PUBSUB,  ""),
	UNSUBSCRIBE("UNSUBSCRIBE", OperateType.SYNC, Classification.PUBSUB,  ""),
	PUBLISH("PUBLISH", OperateType.SYNC, Classification.PUBSUB,  ""),
	SUBSCRIBE("SUBSCRIBE", OperateType.SYNC, Classification.PUBSUB,  ""),
	
	DISCARD("DISCARD", OperateType.SYNC, Classification.TRANSACTIONS,  ""),
	MULTI("MULTI", OperateType.SYNC, Classification.TRANSACTIONS,  ""),
	WATCH("WATCH", OperateType.SYNC, Classification.TRANSACTIONS,  ""),
	EXEC("EXEC", OperateType.SYNC, Classification.TRANSACTIONS,  ""),
	UNWATCH("UNWATCH", OperateType.SYNC, Classification.TRANSACTIONS,  ""),

	AUTH("AUTH", OperateType.SYNC, Classification.CONNECTION,  ""),
	PING("PING", OperateType.SYNC, Classification.CONNECTION,  ""),
	SELECT("SELECT", OperateType.SYNC, Classification.CONNECTION,  ""),
	ECHO("ECHO", OperateType.SYNC, Classification.CONNECTION,  ""),
	QUIT("QUIT", OperateType.SYNC, Classification.CONNECTION,  ""),

	BGREWRITEAOF("BGREWRITEAOF", OperateType.SYNC, Classification.SERVER,  ""),
	BGSAVE("BGSAVE", OperateType.SYNC, Classification.SERVER,  ""),
	CONFIG_GET("CONFIG GET", OperateType.SYNC, Classification.SERVER,  ""),
	CONFIG_RESETSTAT("CONFIG RESETSTAT", OperateType.SYNC, Classification.SERVER,  ""),
	CONFIG_SET("CONFIG SET", OperateType.SYNC, Classification.SERVER,  ""),
	DBSIZE("DBSIZE", OperateType.SYNC, Classification.SERVER,  ""),
	DEBUG_OBJECT("DEBUG OBJECT", OperateType.SYNC, Classification.SERVER,  ""),
	DEBUG_SEGFAULT("DEBUG SEGFAULT", OperateType.SYNC, Classification.SERVER,  ""),
	FLUSHALL("FLUSHALL", OperateType.SYNC, Classification.SERVER,  ""),
	FLUSHDB("FLUSHDB", OperateType.SYNC, Classification.SERVER,  ""),
	INFO("INFO", OperateType.SYNC, Classification.SERVER,  ""),
	LASTSAVE("LASTSAVE", OperateType.SYNC, Classification.SERVER,  ""),
	MONITOR("MONITOR", OperateType.SYNC, Classification.SERVER,  ""),
	SAVE("SAVE", OperateType.SYNC, Classification.SERVER,  ""),
	SHUTDOWN("SHUTDOWN", OperateType.SYNC, Classification.SERVER,  ""),
	SLAVEOF("SLAVEOF", OperateType.SYNC, Classification.SERVER,  ""),
	SYNC("SYNC", OperateType.SYNC, Classification.SERVER,  "");
	
	private String value;
	private OperateType operateType;
	private Classification classification;
	private String describe;
	
	private RedisCommand(String value, OperateType operateType, Classification classification, String describe)
	{
		this.value = value;
		this.operateType = operateType;
		this.classification = classification;
		this.describe = describe;
	}
	
	public String[] getValue()
	{
		return value.split(" ");
	}
	
	public OperateType getOperateType()
	{
		return operateType;
	}

	public Classification getClassification()
	{
		return classification;
	}

	public String getDescribe()
	{
		return describe;
	}

	public enum OperateType
	{
		SYNC, ASYNC;
	}
	
	public enum Classification
	{
		CONNECTION, HASHES, KEYS, LISTS, SERVER, SETS, SORTEDSETS, STRINGS, TRANSACTIONS, PUBSUB;
	}
}
