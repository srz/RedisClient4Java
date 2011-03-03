package com.handinfo.redis4j.api;

public class RedisCommandType
{
	//keys
	public static final String DEL = "DEL";
	public static final String KEYS = "KEYS";
	public static final String RENAME = "RENAME";
	public static final String TYPE = "TYPE";
	public static final String EXISTS = "EXISTS";
	public static final String MOVE = "MOVE";
	public static final String RENAMENX = "RENAMENX";
	public static final String EXPIRE = "EXPIRE";
	public static final String PERSIST = "PERSIST";
	public static final String SORT = "SORT";
	public static final String EXPIREAT = "EXPIREAT";
	public static final String RANDOMKEY = "RANDOMKEY";
	public static final String TTL = "TTL";
	
	//Strings
	public static final String APPEND = "APPEND";
	public static final String GETRANGE = "GETRANGE";
	public static final String MSET = "MSET";
	public static final String SETNX = "SETNX";
	public static final String DECR = "DECR";
	public static final String GETSET = "GETSET";
	public static final String MSETNX = "MSETNX";
	public static final String SETRANGE = "SETRANGE";
	public static final String DECRBY = "DECRBY";
	public static final String INCR = "INCR";
	public static final String SET = "SET";
	public static final String STRLEN = "STRLEN";
	public static final String GET = "GET";
	public static final String INCRBY = "INCRBY";
	public static final String SETBIT = "SETBIT";
	public static final String GETBIT = "GETBIT";
	public static final String MGET = "MGET";
	public static final String SETEX = "SETEX";
	
	//Hashes
	public static final String HDEL = "HDEL";
	public static final String HGETALL = "HGETALL";
	public static final String HLEN = "HLEN";
	public static final String HSET = "HSET";
	public static final String HEXISTS = "HEXISTS";
	public static final String HINCRBY = "HINCRBY";
	public static final String HMGET = "HMGET";
	public static final String HSETNX = "HSETNX";
	public static final String HGET = "HGET";
	public static final String HKEYS = "HKEYS";
	public static final String HMSET = "HMSET";
	public static final String HVALS = "HVALS";
	
	//Lists
	public static final String BLPOP = "BLPOP";
	public static final String LLEN = "LLEN";
	public static final String LREM = "LREM";
	public static final String RPUSH = "RPUSH";
	public static final String BRPOP = "BRPOP";
	public static final String LPOP = "LPOP";
	public static final String LSET = "LSET";
	public static final String RPUSHX = "RPUSHX";
	public static final String BRPOPLPUSH = "BRPOPLPUSH";
	public static final String LPUSH = "LPUSH";
	public static final String LTRIM = "LTRIM";
	public static final String LINDEX = "LINDEX";
	public static final String LPUSHX = "LPUSHX";
	public static final String RPOP = "RPOP";
	public static final String LINSERT = "LINSERT";
	public static final String LRANGE = "LRANGE";
	public static final String RPOPLPUSH = "RPOPLPUSH";
	
	//Sets
	public static final String SADD = "SADD";
	public static final String SINTER = "SINTER";
	public static final String SMOVE = "SMOVE";
	public static final String SUNION = "SUNION";
	public static final String SCARD = "SCARD";
	public static final String SINTERSTORE = "SINTERSTORE";
	public static final String SPOP = "SPOP";
	public static final String SUNIONSTORE = "SUNIONSTORE";
	public static final String SDIFF = "SDIFF";
	public static final String SISMEMBER = "SISMEMBER";
	public static final String SRANDMEMBER = "SRANDMEMBER";
	public static final String SDIFFSTORE = "SDIFFSTORE";
	public static final String SMEMBERS = "SMEMBERS";
	public static final String SREM = "SREM";
	
	//Sorted Sets
	public static final String ZADD = "ZADD";
	public static final String ZINTERSTORE = "ZINTERSTORE";
	public static final String ZREM = "ZREM";
	public static final String ZREVRANGEBYSCORE = "ZREVRANGEBYSCORE";
	public static final String ZCARD = "ZCARD";
	public static final String ZRANGE = "ZRANGE";
	public static final String ZREMRANGEBYRANK = "ZREMRANGEBYRANK";
	public static final String ZREVRANK = "ZREVRANK";
	public static final String ZCOUNT = "ZCOUNT";
	public static final String ZRANGEBYSCORE = "ZRANGEBYSCORE";
	public static final String ZREMRANGEBYSCORE = "ZREMRANGEBYSCORE";
	public static final String ZSCORE = "ZSCORE";
	public static final String ZINCRBY = "ZINCRBY";
	public static final String ZRANK = "ZRANK";
	public static final String ZREVRANGE = "ZREVRANGE";
	public static final String ZUNIONSTORE = "ZUNIONSTORE";
	
	//Sorted Sets
	public static final String PSUBSCRIBE = "PSUBSCRIBE";
	public static final String PUNSUBSCRIBE = "PUNSUBSCRIBE";
	public static final String UNSUBSCRIBE = "UNSUBSCRIBE";
	public static final String PUBLISH = "PUBLISH";
	public static final String SUBSCRIBE = "SUBSCRIBE";
	
	//Transactions
	public static final String DISCARD = "DISCARD";
	public static final String MULTI = "MULTI";
	public static final String WATCH = "WATCH";
	public static final String EXEC = "EXEC";
	public static final String UNWATCH = "UNWATCH";
	
	//Connection
	public static final String AUTH = "AUTH";
	public static final String PING = "PING";
	public static final String SELECT = "SELECT";
	public static final String ECHO = "ECHO";
	public static final String QUIT = "QUIT";
	
	//Server
	public static final String BGREWRITEAOF = "BGREWRITEAOF";
	public static final String DBSIZE = "DBSIZE";
	public static final String INFO = "INFO";
	public static final String SLAVEOF = "SLAVEOF";
	public static final String BGSAVE = "BGSAVE";
	public static final String DEBUG = "DEBUG";
	public static final String DEBUG_OBJECT = "OBJECT";
	public static final String LASTSAVE = "LASTSAVE";
	public static final String SYNC = "SYNC";
	public static final String CONFIG = "CONFIG";
	public static final String CONFIG_GET = "GET";
	public static final String DEBUG_SEGFAULT = "SEGFAULT";
	public static final String MONITOR = "MONITOR";
	public static final String CONFIG_SET  = "SET";
	public static final String FLUSHALL = "FLUSHALL";
	public static final String SAVE = "SAVE";
	public static final String CONFIG_RESETSTAT = "RESETSTAT";
	public static final String FLUSHDB = "FLUSHDB";
	public static final String SHUTDOWN = "SHUTDOWN";
}
