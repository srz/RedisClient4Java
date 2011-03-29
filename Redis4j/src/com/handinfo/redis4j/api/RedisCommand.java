package com.handinfo.redis4j.api;

public enum RedisCommand
{
	DEL("DEL", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.KEYS,  "Delete a key"),
	KEYS("KEYS", RedisResponseType.MultiBulkReplies, OperateType.SYNC, Classification.KEYS,  "Find all keys matching the given pattern"),
	RENAME("RENAME", RedisResponseType.SingleLineReply, OperateType.SYNC, Classification.KEYS,  "Rename a key"),
	TYPE("TYPE", RedisResponseType.SingleLineReply, OperateType.SYNC, Classification.KEYS,  "Determine the type stored at key"),
	EXISTS("EXISTS", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.KEYS,  "Determine if a key exists"),
	MOVE("MOVE", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.KEYS,  "Move a key to another database"),
	RENAMENX("RENAMENX", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.KEYS,  "Rename a key, only if the new key does not exist"),
	EXPIRE("EXPIRE", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.KEYS,  "Set a key's time to live in seconds"),
	PERSIST("PERSIST", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.KEYS,  "Remove the expiration from a key"),
	SORT("SORT", RedisResponseType.MultiBulkReplies, OperateType.SYNC, Classification.KEYS,  "Sort the elements in a list, set or sorted set"),
	EXPIREAT("EXPIREAT", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.KEYS,  "Set the expiration for a key as a UNIX timestamp"),
	RANDOMKEY("RANDOMKEY", RedisResponseType.BulkReplies, OperateType.SYNC, Classification.KEYS,  "Return a random key from the keyspace"),
	TTL("TTL", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.KEYS,  "Get the time to live for a key"),

	APPEND("APPEND", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.STRINGS,  "Append a value to a key"),
	GETRANGE("GETRANGE", RedisResponseType.BulkReplies, OperateType.SYNC, Classification.STRINGS,  "Get a substring of the string stored at a key"),
	MSET("MSET", RedisResponseType.SingleLineReply, OperateType.SYNC, Classification.STRINGS,  "Set multiple keys to multiple values"),
	SETNX("SETNX", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.STRINGS,  "Set the value of a key, only if the key does not exist"),
	DECR("DECR", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.STRINGS,  "Decrement the integer value of a key by one"),
	GETSET("GETSET", RedisResponseType.BulkReplies, OperateType.SYNC, Classification.STRINGS,  "Set the string value of a key and return its old value"),
	MSETNX("MSETNX", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.STRINGS,  "Set multiple keys to multiple values, only if none of the keys exist"),
	SETRANGE("SETRANGE", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.STRINGS,  "Overwrite part of a string at key starting at the specified offset"),
	DECRBY("DECRBY", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.STRINGS,  "Decrement the integer value of a key by the given number"),
	INCR("INCR", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.STRINGS,  "Increment the integer value of a key by one"),
	SET("SET", RedisResponseType.SingleLineReply, OperateType.SYNC, Classification.STRINGS,  "Set the string value of a key"),
	STRLEN("STRLEN", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.STRINGS,  "Get the length of the value stored in a key"),
	GET("GET", RedisResponseType.BulkReplies, OperateType.SYNC, Classification.STRINGS,  "Get the value of a key"),
	INCRBY("INCRBY", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.STRINGS,  "Increment the integer value of a key by the given number"),
	SETBIT("SETBIT", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.STRINGS,  "Sets or clears the bit at offset in the string value stored at key"),
	GETBIT("GETBIT", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.STRINGS,  "Returns the bit value at offset in the string value stored at key"),
	MGET("MGET", RedisResponseType.MultiBulkReplies, OperateType.SYNC, Classification.STRINGS,  "Get the values of all the given keys"),
	SETEX("SETEX", RedisResponseType.SingleLineReply, OperateType.SYNC, Classification.STRINGS,  "Set the value and expiration of a key"),
		
	HDEL("HDEL", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.HASHES,  "Delete a hash field"),
	HGETALL("HGETALL", RedisResponseType.MultiBulkReplies, OperateType.SYNC, Classification.HASHES,  "Get all the fields and values in a hash"),
	HLEN("HLEN", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.HASHES,  "Get the number of fields in a hash"),
	HSET("HSET", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.HASHES,  "Set the string value of a hash field"),
	HEXISTS("HEXISTS", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.HASHES,  "Determine if a hash field exists"),
	HINCRBY("HINCRBY", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.HASHES,  "Increment the integer value of a hash field by the given number"),
	HMGET("HMGET", RedisResponseType.MultiBulkReplies, OperateType.SYNC, Classification.HASHES,  "Get the values of all the given hash field"),
	HSETNX("HSETNX", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.HASHES,  "Set the value of a hash field, only if the field does not exist"),
	HGET("HGET", RedisResponseType.BulkReplies, OperateType.SYNC, Classification.HASHES,  "Get the value of a hash field"),
	HKEYS("HKEYS", RedisResponseType.MultiBulkReplies, OperateType.SYNC, Classification.HASHES,  "Get all the fields in a hash"),
	HMSET("HMSET", RedisResponseType.SingleLineReply, OperateType.SYNC, Classification.HASHES,  "Set multiple hash fields to multiple values"),
	HVALS("HVALS", RedisResponseType.MultiBulkReplies, OperateType.SYNC, Classification.HASHES,  "Get all the values in a hash"),
	
	BLPOP("BLPOP", RedisResponseType.MultiBulkReplies, OperateType.SYNC, Classification.LISTS,  ""),
	LLEN("LLEN", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.LISTS,  ""),
	LREM("LREM", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.LISTS,  ""),
	RPUSH("RPUSH", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.LISTS,  ""),
	BRPOP("BRPOP", RedisResponseType.MultiBulkReplies, OperateType.SYNC, Classification.LISTS,  ""),
	LPOP("LPOP", RedisResponseType.BulkReplies, OperateType.SYNC, Classification.LISTS,  ""),
	LSET("LSET", RedisResponseType.SingleLineReply, OperateType.SYNC, Classification.LISTS,  ""),
	RPUSHX("RPUSHX", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.LISTS,  ""),
	//TODO BRPOPLPUSH Return value Bulk reply: the element being popped from source and pushed to destination. If timeout is reached, a Null multi-bulk reply is returned.
	BRPOPLPUSH("BRPOPLPUSH", RedisResponseType.BulkReplies, OperateType.SYNC, Classification.LISTS,  ""),
	LPUSH("LPUSH", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.LISTS,  ""),
	LTRIM("LTRIM", RedisResponseType.SingleLineReply, OperateType.SYNC, Classification.LISTS,  ""),
	LINDEX("LINDEX", RedisResponseType.BulkReplies, OperateType.SYNC, Classification.LISTS,  ""),
	LPUSHX("LPUSHX", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.LISTS,  ""),
	RPOP("RPOP", RedisResponseType.BulkReplies, OperateType.SYNC, Classification.LISTS,  ""),
	LINSERT("LINSERT", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.LISTS,  ""),
	LRANGE("LRANGE", RedisResponseType.MultiBulkReplies, OperateType.SYNC, Classification.LISTS,  ""),
	RPOPLPUSH("RPOPLPUSH", RedisResponseType.BulkReplies, OperateType.SYNC, Classification.LISTS,  ""),
	
	SADD("SADD", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.SETS,  ""),
	SINTER("SINTER", RedisResponseType.MultiBulkReplies, OperateType.SYNC, Classification.SETS,  ""),
	SMOVE("SMOVE", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.SETS,  ""),
	SUNION("SUNION", RedisResponseType.MultiBulkReplies, OperateType.SYNC, Classification.SETS,  ""),
	SCARD("SCARD", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.SETS,  ""),
	SINTERSTORE("SINTERSTORE", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.SETS,  ""),
	SPOP("SPOP", RedisResponseType.BulkReplies, OperateType.SYNC, Classification.SETS,  ""),
	SUNIONSTORE("SUNIONSTORE", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.SETS,  ""),
	SDIFF("SDIFF", RedisResponseType.MultiBulkReplies, OperateType.SYNC, Classification.SETS,  ""),
	SISMEMBER("SISMEMBER", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.SETS,  ""),
	SRANDMEMBER("SRANDMEMBER", RedisResponseType.BulkReplies, OperateType.SYNC, Classification.SETS,  ""),
	SDIFFSTORE("SDIFFSTORE", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.SETS,  ""),
	SMEMBERS("SMEMBERS", RedisResponseType.MultiBulkReplies, OperateType.SYNC, Classification.SETS,  ""),	
	SREM("SREM", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.SETS,  ""),

	ZADD("ZADD", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.SORTEDSETS,  ""),
	ZINTERSTORE("ZINTERSTORE", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.SORTEDSETS,  ""),
	ZREM("ZREM", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.SORTEDSETS,  ""),
	ZREVRANGEBYSCORE("ZREVRANGEBYSCORE", RedisResponseType.MultiBulkReplies, OperateType.SYNC, Classification.SORTEDSETS,  ""),
	ZCARD("ZCARD", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.SORTEDSETS,  ""),
	ZRANGE("ZRANGE", RedisResponseType.MultiBulkReplies, OperateType.SYNC, Classification.SORTEDSETS,  ""),
	ZREMRANGEBYRANK("ZREMRANGEBYRANK", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.SORTEDSETS,  ""),
	//TODO ZREVRANK Return value
	//If member exists in the sorted set, Integer reply: the rank of member.
	//If member does not exist in the sorted set or key does not exist, Bulk reply: nil.
	ZREVRANK("ZREVRANK", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.SORTEDSETS,  ""),
	ZCOUNT("ZCOUNT", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.SORTEDSETS,  ""),
	ZRANGEBYSCORE("ZRANGEBYSCORE", RedisResponseType.MultiBulkReplies, OperateType.SYNC, Classification.SORTEDSETS,  ""),
	ZREMRANGEBYSCORE("ZREMRANGEBYSCORE", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.SORTEDSETS,  ""),
	ZSCORE("ZSCORE", RedisResponseType.BulkReplies, OperateType.SYNC, Classification.SORTEDSETS,  ""),
	ZINCRBY("ZINCRBY", RedisResponseType.BulkReplies, OperateType.SYNC, Classification.SORTEDSETS,  ""),
	//TODO ZRANK Return value
	//If member exists in the sorted set, Integer reply: the rank of member.
	//If member does not exist in the sorted set or key does not exist, Bulk reply: nil.
	ZRANK("ZRANK", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.SORTEDSETS,  ""),
	ZREVRANGE("ZREVRANGE", RedisResponseType.MultiBulkReplies, OperateType.SYNC, Classification.SORTEDSETS,  ""),
	ZUNIONSTORE("ZUNIONSTORE", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.SORTEDSETS,  ""),
	
	//TODO pub/sub待定
	PSUBSCRIBE("PSUBSCRIBE", RedisResponseType.IntegerReply, OperateType.ASYNC, Classification.PUBSUB,  ""),
	PUNSUBSCRIBE("PUNSUBSCRIBE", RedisResponseType.IntegerReply, OperateType.ASYNC, Classification.PUBSUB,  ""),
	UNSUBSCRIBE("UNSUBSCRIBE", RedisResponseType.IntegerReply, OperateType.ASYNC, Classification.PUBSUB,  ""),
	PUBLISH("PUBLISH", RedisResponseType.IntegerReply, OperateType.ASYNC, Classification.PUBSUB,  ""),
	SUBSCRIBE("SUBSCRIBE", RedisResponseType.IntegerReply, OperateType.ASYNC, Classification.PUBSUB,  ""),
	
	//TODO 到这里了
	DISCARD("DISCARD", RedisResponseType.SingleLineReply, OperateType.SYNC, Classification.TRANSACTIONS,  ""),
	MULTI("MULTI", RedisResponseType.SingleLineReply, OperateType.SYNC, Classification.TRANSACTIONS,  ""),
	WATCH("WATCH", RedisResponseType.SingleLineReply, OperateType.SYNC, Classification.TRANSACTIONS,  ""),
	EXEC("EXEC", RedisResponseType.MultiBulkReplies, OperateType.SYNC, Classification.TRANSACTIONS,  ""),
	UNWATCH("UNWATCH", RedisResponseType.SingleLineReply, OperateType.SYNC, Classification.TRANSACTIONS,  ""),

	AUTH("AUTH", RedisResponseType.SingleLineReply, OperateType.SYNC, Classification.CONNECTION,  ""),
	PING("PING", RedisResponseType.SingleLineReply, OperateType.SYNC, Classification.CONNECTION,  ""),
	SELECT("SELECT", RedisResponseType.SingleLineReply, OperateType.SYNC, Classification.CONNECTION,  ""),
	ECHO("ECHO", RedisResponseType.BulkReplies, OperateType.SYNC, Classification.CONNECTION,  ""),
	QUIT("QUIT", RedisResponseType.SingleLineReply, OperateType.SYNC, Classification.CONNECTION,  ""),

	BGREWRITEAOF("BGREWRITEAOF", RedisResponseType.SingleLineReply, OperateType.SYNC, Classification.SERVER,  ""),
	BGSAVE("BGSAVE", RedisResponseType.SingleLineReply, OperateType.SYNC, Classification.SERVER,  ""),
	//TODO 返回类型需要测试,网站没有相关信息
	CONFIG_GET("CONFIG GET", RedisResponseType.BulkReplies, OperateType.SYNC, Classification.SERVER,  ""),
	CONFIG_RESETSTAT("CONFIG RESETSTAT", RedisResponseType.SingleLineReply, OperateType.SYNC, Classification.SERVER,  ""),
	//TODO 返回类型需要测试,网站没有相关信息
	CONFIG_SET("CONFIG SET", RedisResponseType.SingleLineReply, OperateType.SYNC, Classification.SERVER,  ""),
	DBSIZE("DBSIZE", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.SERVER,  ""),
	//TODO 返回类型需要测试,网站没有相关信息
	DEBUG_OBJECT("DEBUG OBJECT", RedisResponseType.BulkReplies, OperateType.SYNC, Classification.SERVER,  ""),
	//TODO 返回类型需要测试,网站没有相关信息
	DEBUG_SEGFAULT("DEBUG SEGFAULT", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.SERVER,  ""),
	FLUSHALL("FLUSHALL", RedisResponseType.SingleLineReply, OperateType.SYNC, Classification.SERVER,  ""),
	FLUSHDB("FLUSHDB", RedisResponseType.SingleLineReply, OperateType.SYNC, Classification.SERVER,  ""),
	INFO("INFO", RedisResponseType.BulkReplies, OperateType.SYNC, Classification.SERVER,  ""),
	LASTSAVE("LASTSAVE", RedisResponseType.IntegerReply, OperateType.SYNC, Classification.SERVER,  ""),
	//TODO 返回类型需要测试,网站没有相关信息
	MONITOR("MONITOR", RedisResponseType.BulkReplies, OperateType.ASYNC, Classification.SERVER,  ""),
	//TODO 返回类型需要测试,网站没有相关信息
	SAVE("SAVE", RedisResponseType.SingleLineReply, OperateType.SYNC, Classification.SERVER,  ""),
	SHUTDOWN("SHUTDOWN", RedisResponseType.SingleLineReply, OperateType.SYNC, Classification.SERVER,  ""),
	SLAVEOF("SLAVEOF", RedisResponseType.SingleLineReply, OperateType.SYNC, Classification.SERVER,  ""),
	//TODO 返回类型需要测试,网站没有相关信息
	SYNC("SYNC", RedisResponseType.SingleLineReply, OperateType.SYNC, Classification.SERVER,  "");
	
	private String value;
	private RedisResponseType responseType;
	private OperateType operateType;
	private Classification classification;
	private String describe;
	
	private RedisCommand(String value, RedisResponseType responseType, OperateType operateType, Classification classification, String describe)
	{
		this.value = value;
		this.operateType = operateType;
		this.responseType = responseType;
		this.classification = classification;
		this.describe = describe;
	}
	
	public String[] getValue()
	{
		return value.split(" ");
	}
	
	public RedisResponseType getResponseType()
	{
		return responseType;
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
