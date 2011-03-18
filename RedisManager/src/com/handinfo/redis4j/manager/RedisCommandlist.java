package com.handinfo.redis4j.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class RedisCommandlist
{
	public static Map<String, List<String>> getAllCommand()
	{
		Map<String, List<String>> allCommand = new TreeMap<String, List<String>>();

		List<String> keys = new ArrayList<String>();
		keys.add("DEL");
		keys.add("EXISTS");
		keys.add("EXPIRE");
		keys.add("EXPIREAT");
		keys.add("KEYS");
		keys.add("MOVE");
		keys.add("PERSIST");
		keys.add("RANDOMKEY");
		keys.add("RENAME");
		keys.add("RENAMENX");
		keys.add("SORT");
		keys.add("TTL");
		keys.add("TYPE");
		allCommand.put("Keys", keys);

		List<String> strings = new ArrayList<String>();
		strings.add("APPEND");
		strings.add("DECR");
		strings.add("DECRBY");
		strings.add("GET");
		strings.add("GETBIT");
		strings.add("GETRANGE");
		strings.add("GETSET");
		strings.add("INCR");
		strings.add("INCRBY");
		strings.add("MGET");
		strings.add("MSET");
		strings.add("MSETNX");
		strings.add("SET");
		strings.add("SETBIT");
		strings.add("SETEX");
		strings.add("SETNX");
		strings.add("SETRANGE");
		strings.add("STRLEN");
		allCommand.put("Strings", strings);

		List<String> hashes = new ArrayList<String>();
		hashes.add("HDEL");
		hashes.add("HEXISTS");
		hashes.add("HGET");
		hashes.add("HGETALL");
		hashes.add("HINCRBY");
		hashes.add("HKEYS");
		hashes.add("HLEN");
		hashes.add("HMGET");
		hashes.add("HMSET");
		hashes.add("HSET");
		hashes.add("HSETNX");
		hashes.add("HVALS");
		allCommand.put("Hashes", hashes);

		List<String> lists = new ArrayList<String>();
		lists.add("BLPOP");
		lists.add("BRPOP");
		lists.add("BRPOPLPUSH");
		lists.add("LINDEX");
		lists.add("LINSERT");
		lists.add("LLEN");
		lists.add("LPOP");
		lists.add("LPUSH");
		lists.add("LPUSHX");
		lists.add("LRANGE");
		lists.add("LREM");
		lists.add("LSET");
		lists.add("LTRIM");
		lists.add("RPOP");
		lists.add("RPOPLPUSH");
		lists.add("RPUSH");
		lists.add("RPUSHX");
		allCommand.put("Lists", lists);

		List<String> sets = new ArrayList<String>();
		sets.add("SADD");
		sets.add("SCARD");
		sets.add("SDIFF");
		sets.add("SDIFFSTORE");
		sets.add("SINTER");
		sets.add("SINTERSTORE");
		sets.add("SISMEMBER");
		sets.add("SMEMBERS");
		sets.add("SMOVE");
		sets.add("SPOP");
		sets.add("SRANDMEMBER");
		sets.add("SREM");
		sets.add("SUNION");
		sets.add("SUNIONSTORE");
		allCommand.put("Sets", sets);

		List<String> sortedSets = new ArrayList<String>();
		sortedSets.add("ZADD");
		sortedSets.add("ZCARD");
		sortedSets.add("ZCOUNT");
		sortedSets.add("ZINCRBY");
		sortedSets.add("ZINTERSTORE");
		sortedSets.add("ZRANGE");
		sortedSets.add("ZRANGEBYSCORE");
		sortedSets.add("ZRANK");
		sortedSets.add("ZREM");
		sortedSets.add("ZREMRANGEBYRANK");
		sortedSets.add("ZREMRANGEBYSCORE");
		sortedSets.add("ZREVRANGE");
		sortedSets.add("ZREVRANGEBYSCORE");
		sortedSets.add("ZREVRANK");
		sortedSets.add("ZSCORE");
		sortedSets.add("ZUNIONSTORE");
		allCommand.put("SortedSets", sortedSets);

		List<String> pubSub = new ArrayList<String>();
		pubSub.add("PSUBSCRIBE");
		pubSub.add("PUBLISH");
		pubSub.add("PUNSUBSCRIBE");
		pubSub.add("SUBSCRIBE");
		pubSub.add("UNSUBSCRIBE");
		allCommand.put("Pub/Sub", pubSub);

		List<String> transactions = new ArrayList<String>();
		transactions.add("DISCARD");
		transactions.add("EXEC");
		transactions.add("MULTI");
		transactions.add("UNWATCH");
		transactions.add("WATCH");
		allCommand.put("Transactions", transactions);

		List<String> connection = new ArrayList<String>();
		connection.add("AUTH");
		connection.add("ECHO");
		connection.add("PING");
		connection.add("QUIT");
		connection.add("SELECT");
		allCommand.put("Connection", connection);

		List<String> server = new ArrayList<String>();
		server.add("BGREWRITEAOF");
		server.add("BGSAVE");
		server.add("CONFIG GET");
		server.add("CONFIG RESETSTAT");
		server.add("CONFIG SET");
		server.add("DBSIZE");
		server.add("DEBUG OBJECT");
		server.add("DEBUG SEGFAULT");
		server.add("FLUSHALL");
		server.add("FLUSHDB");
		server.add("INFO");
		server.add("LASTSAVE");
		server.add("MONITOR");
		server.add("SAVE");
		server.add("SHUTDOWN");
		server.add("SLAVEOF");
		server.add("SYNC");
		allCommand.put("Server", server);

		return allCommand;
	}
}
