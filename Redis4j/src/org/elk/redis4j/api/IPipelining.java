/**
 * 
 */
package org.elk.redis4j.api;

import java.util.Map;

/**
 * 当把Redis作为数据库时,实现以下功能
 * 与作为缓存的区别主要在于这里的所有操作均为String，不会对任何key对象做序列化,而缓存版本可以
 * @see http://redis.io/commands
 */
public interface IPipelining
{
	/**
	 * 提交任意信息,服务器接收后直接返回,不做任何处理,主要用于调试
	 * [ECHO]Echo the given string
	 * @param message
	 * @return
	 */
	public void echo(String message);

	/**
	 * 向服务器发送ping消息,以防止连接时间过长被服务器断开,在通讯层已经调用了此功能,应用层通常不用调用
	 * [PING]Ping the server
	 * @return
	 */
	//public void ping();
	
	//hashes
	/**
	 * 删除Hashe对象中的指定字段
	 * [HDEL]Delete a hash field
	 * @param key
	 * @param field
	 * @return
	 */
	public void hashesDel(String key, String field);


	/**
	 * 获取某Hashe对象中的全部字段
	 * [HVALS]Get all the values in a hash
	 * @param key
	 * @return
	 */
	public void hashesGetAllValue(String key);

	/**
	 * 获取指定Hashe对象的长度
	 * [HLEN]Get the number of fields in a hash
	 * @param key
	 * @return
	 */
	public void hashesLength(String key);

	/**
	 * [HSET]Set the string value of a hash field
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public void hashesSet(String key, String field, String value);


	/**
	 * [HEXISTS]Determine if a hash field exists
	 * @param key
	 * @param field
	 * @return
	 */
	public void hashesExists(String key, String field);


	/**
	 * [HINCRBY]Increment the integer value of a hash field by the given number
	 * @param key
	 * @param field
	 * @param increment
	 * @return
	 */
	public void hashesIncrementByValue(String key, String field, int increment);


	/**
	 * [HMGET]Get the values of all the given hash fields
	 * @param key
	 * @param fields
	 * @return
	 */
	public void hashesMultipleFieldGet(String key, String... fields);


	/**
	 * [HSETNX]Set the value of a hash field, only if the field does not exist
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public void hashesSetNotExistField(String key, String field, String value);


	/**
	 * [HGET]Get the value of a hash field
	 * @param key
	 * @param field
	 * @return
	 */
	public void hashesGet(String key, String field);

	/**
	 * [HKEYS]Get all the fields in a hash
	 * @param key
	 * @return
	 */
	public void hashesGetAllField(String key);

	/**
	 * [HMSET]Set multiple hash fields to multiple values
	 * @param key
	 * @param fieldAndValue
	 * @return
	 */
	public void hashesMultipleSet(String key, Map<String, String> fieldAndValue);


	/**
	 * [HGETALL]Get all the fields and values in a hash
	 * @param key
	 * @return
	 */
	public void hashesGetAll(String key);
	
	//keys
	/**
	 * [DEL]Delete a key
	 * @param keys
	 * @return
	 */
	public void del(String... keys);

	/**
	 * [KEYS]Find all keys matching the given pattern
	 * @param pattern
	 * @return
	 */
	public void keys(String pattern);

	/**
	 * [RENAME]Rename a key
	 * @param key
	 * @param newKey
	 * @return
	 */
	public void rename(String key, String newKey);

	/**
	 * [TYPE]Determine the type stored at key
	 * @param key
	 * @return
	 */
	public void type(String key);

	/**
	 * [EXISTS]Determine if a key exists
	 * @param key
	 * @return
	 */
	public void exists(String key);

	/**
	 * [MOVE]Move a key to another database
	 * @param key
	 * @param indexDB
	 * @return
	 */
	public void move(String key, int indexDB);


	/**
	 * [RENAMENX]Rename a key, only if the new key does not exist
	 * @see http://redis.io/commands/renamenx
	 * @param key
	 * @param newKey
	 * @return
	 */
	public void renameOnNotExistNewKey(String key, String newKey);

	/**
	 * [EXPIRE]Set a key's time to live in seconds
	 * @param key
	 * @param seconds
	 * @return
	 */
	public void expire(String key, int seconds);

	/**
	 * [PERSIST]Remove the expiration from a key
	 * @param key
	 * @return
	 */
	public void persist(String key);

	/**
	 * [SORT]Sort the elements in a list, set or sorted set
	 * @param key
	 * @param args
	 * @return
	 * TODO 暂时先写个简单版本的,后面在追加重载版本
	 */
	public void sort(String key, String... args);

	/**
	 * [EXPIREAT]Set the expiration for a key as a UNIX timestamp
	 * @param key
	 * @param timestamp
	 * @return
	 */
	public void expireAsTimestamp(String key, long timestamp);

	/**
	 * [RANDOMKEY]Return a random key from the keyspace
	 * @return
	 */
	public void randomKey();

	/**
	 * [TTL]Get the time to live for a key
	 * @param key
	 * @return
	 */
	public void timeToLive(String key);
	
	//Lists
	/**
	 * [BLPOP]Remove and get the first element in a list, or block until one is available
	 * @param timeout
	 * @param keys
	 * @return
	 */
	public void listBlockLeftPop(int timeout, String... keys);

	/**
	 * [LLEN]Get the length of a list
	 * @param key
	 * @return
	 */
	public void listLength(String key);

	/**
	 * [LREM]Remove elements from a list
	 * @param key
	 * @param count
	 * @param value
	 * @return
	 */
	public void listRemove(String key, int count, String value);

	/**
	 * [RPUSH]Append a value to a list
	 * @param key
	 * @param value
	 * @return
	 */
	public void listRightPush(String key, String value);

	/**
	 * [BRPOP]Remove and get the last element in a list, or block until one is available
	 * @param timeout
	 * @param keys
	 * @return
	 */
	public void listBlockRightPop(int timeout, String... keys);

	/**
	 * [LPOP]Remove and get the first element in a list
	 * @param key
	 * @return
	 */
	public void listLeftPop(String key);

	/**
	 * [LSET]Set the value of an element in a list by its index
	 * @param key
	 * @param index
	 * @param value
	 * @return
	 */
	public void listSet(String key, int index, String value);
	
	/**
	 * [RPUSHX]Append a value to a list, only if the list exists
	 * @param key
	 * @param value
	 * @return
	 */
	public void listRightPushOnExist(String key, String value);

	/**
	 * [BRPOPLPUSH]Pop a value from a list, push it to another list and return it; or block until one is available
	 * @param source
	 * @param destination
	 * @param timeout
	 * @return
	 */
	public void listBlockRightPopLeftPush(String source, String destination, int timeout);

	/**
	 * [LPUSH]Prepend a value to a list
	 * @param key
	 * @param value
	 * @return
	 */
	public void listLeftPush(String key, String value);

	/**
	 * [LTRIM]Trim a list to the specified range
	 * @param key
	 * @param start
	 * @param stop
	 * @return
	 */
	public void listTrim(String key, int start, int stop);

	/**
	 * [LINDEX]Get an element from a list by its index
	 * @param key
	 * @param index
	 * @return
	 */
	public void listIndex(String key, int index);

	/**
	 * [LPUSHX]Prepend a value to a list, only if the list exists
	 * @param key
	 * @param value
	 * @return
	 */
	public void listLeftPushOnExist(String key, String value);

	/**
	 * [RPOP]Remove and get the last element in a list
	 * @param key
	 * @return
	 */
	public void listRightPop(String key);

	/**
	 * [LINSERT]Insert an element before or after another element in a list
	 * @param key
	 * @param beforeOrAfter
	 * @param pivot
	 * @param value
	 * @return
	 */
	public void listInsert(String key, ListPosition beforeOrAfter, String pivot, String value);

	/**
	 * [LRANGE]Get a range of elements from a list
	 * @param key
	 * @param start
	 * @param stop
	 * @return
	 */
	public void listRange(String key, int start, int stop);

	/**
	 * [RPOPLPUSH]Remove the last element in a list, append it to another list and return it
	 * @param source
	 * @param destination
	 * @return
	 */
	public void listRightPopLeftPush(String source, String destination);
	
	//server
	/**
	 * [BGREWRITEAOF]Asynchronously rewrite the append-only file
	 * @return
	 */
	//public void backgroundRewriteAOF();

	/**
	 * [BGSAVE]Asynchronously save the dataset to disk
	 * @return
	 */
	//public void backgroundSave();

	/**
	 * [CONFIG GET]Get the value of a configuration parameter
	 * @param parameter
	 * @return
	 */
	//public void configGet(String parameter);

	/**
	 * [CONFIG RESETSTAT]Reset the stats returned by INFO
	 * @return
	 */
	//public void configResetStat();

	/**
	 * [CONFIG SET]Set a configuration parameter to the given value
	 * @param parameter
	 * @param value
	 * @return
	 */
	//public void configSet(String parameter, String value);

	/**
	 * [DBSIZE]Return the number of keys in the selected database
	 * @return
	 */
	//public void dbSize();

	/**
	 * [DEBUG OBJECT]Get debugging information about a key
	 * @param key
	 * @return
	 */
	//public void debugObject(String key);

	/**
	 * [DEBUG SEGFAULT]Make the server crash
	 * @return
	 */
	//public String[] debugSegfault();

	/**
	 * [FLUSHALL]Remove all keys from all databases
	 * @return
	 */
	//public void flushAllDB();

	/**
	 * [FLUSHDB]Remove all keys from the current database
	 * @return
	 */
	//public void flushCurrentDB();

	/**
	 * [INFO]Get information and statistics about the server
	 * @return
	 */
	//public void info();

	/**
	 * [LASTSAVE]Get the UNIX time stamp of the last successful save to disk
	 * @return
	 */
	//public void lastSave();

	/**
	 * [SAVE]Synchronously save the dataset to disk
	 * @return
	 */
	//public void save();

	/**
	 * [SHUTDOWN]Synchronously save the dataset to disk and then shut down the server
	 * @return
	 */
	//public void shutdownServer();

	/**
	 * [SLAVEOF]Make the server a slave of another instance, or promote it as master
	 * @return
	 */
	//public void slaveOf();

	/**
	 * [SYNC]Internal command used for replication
	 * @return
	 */
	//public void sync();
	
	//Sets
	/**
	 * [SADD]Add a member to a set
	 * @param key
	 * @param member
	 * @return
	 */
	public void setsAdd(String key, String member);

	/**
	 * [SINTER]Intersect multiple sets
	 * @param keys
	 * @return
	 */
	public void setsInter(String... keys);

	/**
	 * [SMOVE]Move a member from one set to another
	 * @param source
	 * @param destination
	 * @param member
	 * @return
	 */
	public void setsMove(String source, String destination, String member);

	/**
	 * [SUNION]Add multiple sets
	 * @param keys
	 * @return
	 */
	public void setsUnion(String... keys);

	/**
	 * [SCARD]Get the number of members in a set
	 * @param key
	 * @return
	 */
	public void setsCard(String key);

	/**
	 * [SINTERSTORE]Intersect multiple sets and store the resulting set in a key
	 * @param destination
	 * @param keys
	 * @return
	 */
	public void setsInterStore(String destination, String... keys);

	/**
	 * [SPOP]Remove and return a random member from a set
	 * @param key
	 * @return
	 */
	public void setsPop(String key);

	/**
	 * [SUNIONSTORE]Add multiple sets and store the resulting set in a key
	 * @param destination
	 * @param keys
	 * @return
	 */
	public void setsUnionStore(String destination, String... keys);

	/**
	 * [SDIFF]Subtract multiple sets
	 * @param keys
	 * @return
	 */
	public void setsDiff(String... keys);

	/**
	 * [SISMEMBER]Determine if a given value is a member of a set
	 * @param key
	 * @param member
	 * @return
	 */
	public void setsIsMember(String key, String member);

	/**
	 * [SRANDMEMBER]Get a random member from a set
	 * @param key
	 * @return
	 */
	public void setsRandMember(String key);

	/**
	 * [SDIFFSTORE]Subtract multiple sets and store the resulting set in a key
	 * @param destination
	 * @param keys
	 * @return
	 */
	public void setsDiffStore(String destination, String... keys);

	/**
	 * [SMEMBERS]Get all the members in a set
	 * @param key
	 * @return
	 */
	public void setsMembers(String key);

	/**
	 * [SREM]Remove a member from a set
	 * @param key
	 * @param member
	 * @return
	 */
	public void setsRemove(String key, String member);
	
	//SortedSets
	/**
	 * [ZADD]Add a member to a sorted set, or update its score if it already exists
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	public void sortedSetsAdd(String key, int score, String member);

	/**
	 * [ZINTERSTORE]Intersect multiple sorted sets and store the resulting sorted set in a new key
	 * @param destination 
	 * @param keys
	 * @return
	 */
	public void sortedSetsInterStore(String destination, String... keys);

	/**
	 * [ZREM]Remove a member from a sorted set
	 * @param key
	 * @param member
	 * @return
	 */
	public void sortedSetsRemove(String key, String member);

	/**
	 * [ZREVRANGEBYSCORE]Return a range of members in a sorted set, by score, with scores ordered from high to low
	 * @param key 
	 * @param max 
	 * @param min 
	 * @return
	 */
	public void sortedSetsRevRangeByScore(String key, int max, int min);

	/**
	 * [ZCARD]Get the number of members in a sorted set
	 * @param key
	 * @return
	 */
	public void sortedSetsCard(String key);

	/**
	 * [ZRANGE]Return a range of members in a sorted set, by index
	 * @param key
	 * @param start
	 * @param stop
	 * @return
	 */
	public void sortedSetsRange(String key, int start, int stop);

	/**
	 * [ZREMRANGEBYRANK]Remove all members in a sorted set within the given indexes
	 * @param key
	 * @param start
	 * @param stop
	 * @return
	 */
	public void sortedSetsRemoveRangeByRank(String key, int start, int stop);

	/**
	 * [ZREVRANK]Determine the index of a member in a sorted set, with scores ordered from high to low
	 * @param key
	 * @param member
	 * @return
	 */
	public void sortedSetsRevRank(String key, String member);

	/**
	 * [ZCOUNT]Count the members in a sorted set with scores within the given values
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public void sortedSetsCount(String key, int min, int max);

	/**
	 * [ZRANGEBYSCORE]Return a range of members in a sorted set, by score
	 * @param key 
	 * @param min 
	 * @param max 
	 * @return
	 */
	public void sortedSetsRangeByScore(String key, int min, int max);

	/**
	 * [ZREMRANGEBYSCORE]Remove all members in a sorted set within the given scores
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public void sortedSetsRemoveRangeByScore(String key, int min, int max);

	/**
	 * [ZSCORE]Get the score associated with the given member in a sorted set
	 * @param key
	 * @param member
	 * @return
	 */
	public void sortedSetsScore(String key, String member);

	/**
	 * [ZINCRBY]Increment the score of a member in a sorted set
	 * @param key
	 * @param increment
	 * @param member
	 * @return
	 */
	public void sortedSetsIncrementByValue(String key, int increment, String member);

	/**
	 * [ZRANK]Determine the index of a member in a sorted set
	 * @param key
	 * @param member
	 * @return
	 */
	public void sortedSetsRank(String key, String member);

	/**
	 * [ZREVRANGE]Return a range of members in a sorted set, by index, with scores ordered from high to low
	 * @param key
	 * @param start
	 * @param stop
	 * @return
	 */
	public void sortedSetsRevRange(String key, int start, int stop);

	/**
	 * [ZUNIONSTORE]Add multiple sorted sets and store the resulting sorted set in a new key
	 * @param destination 
	 * @param keys
	 * @return
	 */
	public void sortedSetsUnionStore(String destination, String... keys);
	
	//Strings
	/**
	 * [APPEND]Append a value to a key
	 * @param key
	 * @param value
	 * @return
	 */
	public void append(String key, String value);

	/**
	 * [GETRANGE]Get a substring of the string stored at a key
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public void getRange(String key, int start, int end);

	/**
	 * [MSET]Set multiple keys to multiple values
	 * @param keyAndValue
	 * @return
	 */
	public void multipleSet(Map<String, String> keyAndValue);

	/**
	 * [SETNX]Set the value of a key, only if the key does not exist
	 * @param key
	 * @param value
	 * @return
	 */
	public void setOnNotExist(String key, String value);

	/**
	 * [DECR]Decrement the integer value of a key by one
	 * @param key
	 * @return
	 */
	public void decrement(String key);

	/**
	 * [GETSET]Set the string value of a key and return its old value
	 * @param key
	 * @param value
	 * @return
	 */
	public void getSet(String key, String value);

	/**
	 * [MSETNX]Set multiple keys to multiple values, only if none of the keys exist
	 * @param keyAndValue
	 * @return
	 */
	public void multipleSetOnNotExist(Map<String, String> keyAndValue);

	/**
	 * [SETRANGE]Overwrite part of a string at key starting at the specified offset
	 * @param key
	 * @param offset
	 * @param value
	 * @return
	 */
	public void setRange(String key, int offset, String value);

	/**
	 * [DECRBY]Decrement the integer value of a key by the given number
	 * @param key
	 * @param decrement
	 * @return
	 */
	public void decrementByValue(String key, int decrement);

	/**
	 * [INCR]Increment the integer value of a key by one
	 * @param key
	 * @return
	 */
	public void increment(String key);

	/**
	 * [SET]Set the string value of a key
	 * @param key
	 * @param value
	 * @return
	 */
	public void set(String key, String value);

	/**
	 * [STRLEN]Get the length of the value stored in a key
	 * @param key
	 * @return
	 */
	public void strLength(String key);

	/**
	 * [GET]Get the value of a key
	 * @param key
	 * @return
	 */
	public void get(String key);

	/**
	 * [INCRBY]Increment the integer value of a key by the given number
	 * @param key
	 * @param increment
	 * @return
	 */
	public void incrementByValue(String key, int increment);

	/**
	 * [SETBIT]Sets or clears the bit at offset in the string value stored at key
	 * @param key
	 * @param offset
	 * @param value
	 * @return
	 */
	public void setBit(String key, int offset, boolean value);

	/**
	 * [GETBIT]Returns the bit value at offset in the string value stored at key
	 * @param key
	 * @param offset
	 * @return
	 */
	public void getBit(String key, int offset);

	/**
	 * [MGET]Get the values of all the given keys
	 * @param keys
	 * @return
	 */
	public void multipleGet(String... keys);

	/**
	 * [SETEX]Set the value and expiration of a key
	 * @param key
	 * @param seconds
	 * @param value
	 * @return
	 */
	public void setAndExpire(String key, int seconds, String value);
}
