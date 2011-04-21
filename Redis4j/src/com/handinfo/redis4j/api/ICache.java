package com.handinfo.redis4j.api;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 当把Redis作为缓存时,实现以下基础功能
 * 这里会对所有操作的key对象进行序列化存储,所以无法使用sort等redis的全部功能
 */
public interface ICache
{
	/**
	 * [FLUSHALL]Remove all keys from all databases
	 * @return
	 */
	public Boolean flushAllDB();

	/**
	 * [FLUSHDB]Remove all keys from the current database
	 * @return
	 */
	public Boolean flushCurrentDB();
	
	//hashes
	/**
	 * 删除Hashe对象中的指定字段
	 * [HDEL]Delete a hash field
	 * @param key
	 * @param field
	 * @return
	 */
	public Boolean hashesDel(String key, String field);


	/**
	 * 获取某Hashe对象中的全部字段
	 * [HVALS]Get all the values in a hash
	 * @param key
	 * @return 
	 */
	public <T> List<T> hashesGetAllValue(String key);

	/**
	 * 获取指定Hashe对象的长度
	 * [HLEN]Get the number of fields in a hash
	 * @param key
	 * @return
	 */
	public Integer hashesLength(String key);

	/**
	 * [HSET]Set the string value of a hash field
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public <T> Boolean hashesSet(String key, String field, T value);


	/**
	 * [HEXISTS]Determine if a hash field exists
	 * @param key
	 * @param field
	 * @return
	 */
	public Boolean hashesExists(String key, String field);


	/**
	 * [HINCRBY]Increment the integer value of a hash field by the given number
	 * @param key
	 * @param field
	 * @param increment
	 * @return
	 */
	public Integer hashesIncrementByValue(String key, String field, int increment);


	/**
	 * [HMGET]Get the values of all the given hash fields
	 * @param key
	 * @param fields
	 * @return
	 */
	public <T> List<T> hashesMultipleFieldGet(String key, String... fields);


	/**
	 * [HSETNX]Set the value of a hash field, only if the field does not exist
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public <T> Boolean hashesSetNotExistField(String key, String field, T value);


	/**
	 * [HGET]Get the value of a hash field
	 * @param key
	 * @param field
	 * @return
	 */
	public <T> T hashesGet(String key, String field);

	/**
	 * [HKEYS]Get all the fields in a hash
	 * @param key
	 * @return
	 */
	public List<String> hashesGetAllField(String key);

	/**
	 * [HMSET]Set multiple hash fields to multiple values
	 * @param key
	 * @param fieldAndValue
	 * @return
	 */
	public <T> Boolean hashesMultipleSet(String key, Map<String, T> fieldAndValue);


	/**
	 * [HGETALL]Get all the fields and values in a hash
	 * @param key
	 * @return
	 */
	public <T> Map<String, T> hashesGetAll(String key);
	
	//keys
	/**
	 * [DEL]Delete a key
	 * @param keys
	 * @return
	 */
	public Integer del(String... keys);

	/**
	 * [KEYS]Find all keys matching the given pattern
	 * @param pattern
	 * @return
	 */
	public List<String> keys(String pattern);

	/**
	 * [RENAME]Rename a key
	 * @param key
	 * @param newKey
	 * @return
	 */
	//public Boolean rename(String key, String newKey);

	/**
	 * [TYPE]Determine the type stored at key
	 * @param key
	 * @return
	 */
	public String type(String key);

	/**
	 * [EXISTS]Determine if a key exists
	 * @param key
	 * @return
	 */
	public Boolean exists(String key);

	/**
	 * [MOVE]Move a key to another database
	 * @param key
	 * @param indexDB
	 * @return
	 */
	public Boolean move(String key, int indexDB);


	/**
	 * [RENAMENX]Rename a key, only if the new key does not exist
	 * @see http://redis.io/commands/renamenx
	 * @param key
	 * @param newKey
	 * @return
	 */
	//public Boolean renameOnNotExistNewKey(String key, String newKey);

	/**
	 * [EXPIRE]Set a key's time to live in seconds
	 * @param key
	 * @param seconds
	 * @return
	 */
	public Boolean expire(String key, int seconds);

	/**
	 * [PERSIST]Remove the expiration from a key
	 * @param key
	 * @return
	 */
	public Boolean persist(String key);

	/**
	 * [EXPIREAT]Set the expiration for a key as a UNIX timestamp
	 * @param key
	 * @param timestamp
	 * @return
	 */
	public Boolean expireAsTimestamp(String key, long timestamp);

	/**
	 * [TTL]Get the time to live for a key
	 * @param key
	 * @return
	 */
	public Integer timeToLive(String key);
	
	//Lists
	/**
	 * [BLPOP]Remove and get the first element in a list, or block until one is available
	 * @param key 
	 * @param timeout
	 * @return
	 */
	public <T> Entry<String, T> listBlockLeftPop(String key, int timeout);

	/**
	 * [LLEN]Get the length of a list
	 * @param key
	 * @return
	 */
	public Integer listLength(String key);

	/**
	 * [LREM]Remove elements from a list
	 * @param key
	 * @param count
	 * @param value
	 * @return
	 */
	public <T> Integer listRemove(String key, int count, T value);

	/**
	 * [RPUSH]Append a value to a list
	 * @param key
	 * @param value
	 * @return
	 */
	public <T> Integer listRightPush(String key, T value);

	/**
	 * [BRPOP]Remove and get the last element in a list, or block until one is available
	 * @param key 
	 * @param timeout
	 * @return
	 */
	public <T> Entry<String, T> listBlockRightPop(String key, int timeout);

	/**
	 * [LPOP]Remove and get the first element in a list
	 * @param key
	 * @return
	 */
	public <T> T listLeftPop(String key);

	/**
	 * [LSET]Set the value of an element in a list by its index
	 * @param key
	 * @param index
	 * @param value
	 * @return
	 */
	public <T> Boolean listSet(String key, int index, T value);
	
	/**
	 * [RPUSHX]Append a value to a list, only if the list exists
	 * @param key
	 * @param value
	 * @return
	 */
	public <T> Integer listRightPushOnExist(String key, T value);

	/**
	 * [BRPOPLPUSH]Pop a value from a list, push it to another list and return it; or block until one is available
	 * @param source
	 * @param destination
	 * @param timeout
	 * @return
	 */
	//public <T> T listBlockRightPopLeftPush(String source, String destination, int timeout);

	/**
	 * [LPUSH]Prepend a value to a list
	 * @param key
	 * @param value
	 * @return
	 */
	public <T> Integer listLeftPush(String key, T value);

	/**
	 * [LTRIM]Trim a list to the specified range
	 * @param key
	 * @param start
	 * @param stop
	 * @return
	 */
	public Boolean listTrim(String key, int start, int stop);

	/**
	 * [LINDEX]Get an element from a list by its index
	 * @param key
	 * @param index
	 * @return
	 */
	public <T> T listIndex(String key, int index);

	/**
	 * [LPUSHX]Prepend a value to a list, only if the list exists
	 * @param key
	 * @param value
	 * @return
	 */
	public <T> Integer listLeftPushOnExist(String key, T value);

	/**
	 * [RPOP]Remove and get the last element in a list
	 * @param key
	 * @return
	 */
	public <T> T listRightPop(String key);

	/**
	 * [LINSERT]Insert an element before or after another element in a list
	 * @param key
	 * @param beforeOrAfter
	 * @param pivot
	 * @param value
	 * @return
	 */
	public <T> Integer listInsert(String key, ListPosition beforeOrAfter, T pivot, T value);

	/**
	 * [LRANGE]Get a range of elements from a list
	 * @param key
	 * @param start
	 * @param stop
	 * @return
	 */
	public <T> List<T> listRange(String key, int start, int stop);

	/**
	 * [RPOPLPUSH]Remove the last element in a list, append it to another list and return it
	 * @param source
	 * @param destination
	 * @return
	 */
	//public <T> T listRightPopLeftPush(String source, String destination);
	
	//Sets
	/**
	 * [SADD]Add a member to a set
	 * @param key
	 * @param member
	 * @return
	 */
	public <T> Boolean setsAdd(String key, T member);

	/**
	 * [SINTER]Intersect multiple sets
	 * @param keys
	 * @return
	 */
	//public <T> List<T> setsInter(String... keys);

	/**
	 * [SMOVE]Move a member from one set to another
	 * @param source
	 * @param destination
	 * @param member
	 * @return
	 */
	//public <T> Boolean setsMove(String source, String destination, T member);

	/**
	 * [SUNION]Add multiple sets
	 * @param keys
	 * @return
	 */
	//public <T> List<T> setsUnion(String... keys);

	/**
	 * [SCARD]Get the number of members in a set
	 * @param key
	 * @return
	 */
	public Integer setsCard(String key);

	/**
	 * [SINTERSTORE]Intersect multiple sets and store the resulting set in a key
	 * @param destination
	 * @param keys
	 * @return
	 */
	//public Integer setsInterStore(String destination, String... keys);

	/**
	 * [SPOP]Remove and return a random member from a set
	 * @param key
	 * @return
	 */
	public <T> T setsPop(String key);

	/**
	 * [SUNIONSTORE]Add multiple sets and store the resulting set in a key
	 * @param destination
	 * @param keys
	 * @return
	 */
	//public Integer setsUnionStore(String destination, String... keys);

	/**
	 * [SDIFF]Subtract multiple sets
	 * @param keys
	 * @return
	 */
	//public <T> List<T> setsDiff(String... keys);

	/**
	 * [SISMEMBER]Determine if a given value is a member of a set
	 * @param key
	 * @param member
	 * @return
	 */
	public <T> Boolean setsIsMember(String key, T member);

	/**
	 * [SRANDMEMBER]Get a random member from a set
	 * @param key
	 * @return
	 */
	public <T> T setsRandMember(String key);

	/**
	 * [SDIFFSTORE]Subtract multiple sets and store the resulting set in a key
	 * @param destination
	 * @param keys
	 * @return
	 */
	//public Integer setsDiffStore(String destination, String... keys);

	/**
	 * [SMEMBERS]Get all the members in a set
	 * @param key
	 * @return
	 */
	public <T> List<T> setsMembers(String key);

	/**
	 * [SREM]Remove a member from a set
	 * @param key
	 * @param member
	 * @return
	 */
	public <T> Boolean setsRemove(String key, T member);
	
	//SortedSets
	/**
	 * [ZADD]Add a member to a sorted set, or update its score if it already exists
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	public <T> Boolean sortedSetsAdd(String key, int score, T member);

	/**
	 * [ZINTERSTORE]Intersect multiple sorted sets and store the resulting sorted set in a new key
	 * @param destination 
	 * @param keys
	 * @return
	 */
	//public Integer sortedSetsInterStore(String destination, String... keys);

	/**
	 * [ZREM]Remove a member from a sorted set
	 * @param key
	 * @param member
	 * @return
	 */
	public <T> Boolean sortedSetsRemove(String key, T member);

	/**
	 * [ZREVRANGEBYSCORE]Return a range of members in a sorted set, by score, with scores ordered from high to low
	 * @param key 
	 * @param max 
	 * @param min 
	 * @return
	 */
	public <T> List<T> sortedSetsRevRangeByScore(String key, int max, int min);

	/**
	 * [ZCARD]Get the number of members in a sorted set
	 * @param key
	 * @return
	 */
	public Integer sortedSetsCard(String key);

	/**
	 * [ZRANGE]Return a range of members in a sorted set, by index
	 * @param key
	 * @param start
	 * @param stop
	 * @return
	 */
	public <T> List<T> sortedSetsRange(String key, int start, int stop);

	/**
	 * [ZREMRANGEBYRANK]Remove all members in a sorted set within the given indexes
	 * @param key
	 * @param start
	 * @param stop
	 * @return
	 */
	public Integer sortedSetsRemoveRangeByRank(String key, int start, int stop);

	/**
	 * [ZREVRANK]Determine the index of a member in a sorted set, with scores ordered from high to low
	 * @param key
	 * @param member
	 * @return
	 */
	public <T> Integer sortedSetsRevRank(String key, T member);

	/**
	 * [ZCOUNT]Count the members in a sorted set with scores within the given values
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public Integer sortedSetsCount(String key, int min, int max);

	/**
	 * [ZRANGEBYSCORE]Return a range of members in a sorted set, by score
	 * @param key 
	 * @param min 
	 * @param max
	 * @return
	 */
	public <T> List<T> sortedSetsRangeByScore(String key, int min, int max);

	/**
	 * [ZREMRANGEBYSCORE]Remove all members in a sorted set within the given scores
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public Integer sortedSetsRemoveRangeByScore(String key, int min, int max);

	/**
	 * [ZSCORE]Get the score associated with the given member in a sorted set
	 * @param key
	 * @param member
	 * @return
	 */
	public <T> Integer sortedSetsScore(String key, T member);

	/**
	 * [ZINCRBY]Increment the score of a member in a sorted set
	 * @param key
	 * @param increment
	 * @param member
	 * @return
	 */
	public <T> Double sortedSetsIncrementByValue(String key, int increment, T member);

	/**
	 * [ZRANK]Determine the index of a member in a sorted set
	 * @param key
	 * @param member
	 * @return
	 */
	public <T> Integer sortedSetsRank(String key, T member);

	/**
	 * [ZREVRANGE]Return a range of members in a sorted set, by index, with scores ordered from high to low
	 * @param key
	 * @param start
	 * @param stop
	 * @return
	 */
	public <T> List<T> sortedSetsRevRange(String key, int start, int stop);
	
	//Strings
	/**
	 * [APPEND]Append a value to a key
	 * @param key
	 * @param value
	 * @return
	 */
	public Integer append(String key, String value);

	/**
	 * [GETRANGE]Get a substring of the string stored at a key
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public String getRange(String key, int start, int end);

	/**
	 * [MSET]Set multiple keys to multiple values
	 * @param keyAndValue
	 * @return
	 */
	public <T> Boolean multipleSet(Map<String, T> keyAndValue);

	/**
	 * [SETNX]Set the value of a key, only if the key does not exist
	 * @param key
	 * @param value
	 * @return
	 */
	public <T> Boolean setOnNotExist(String key, T value);

	/**
	 * [DECR]Decrement the integer value of a key by one
	 * @param key
	 * @return
	 */
	public Integer decrement(String key);

	/**
	 * [GETSET]Set the string value of a key and return its old value
	 * @param key
	 * @param value
	 * @return
	 */
	public <T> T getSet(String key, T value);

	/**
	 * [MSETNX]Set multiple keys to multiple values, only if none of the keys exist
	 * @param keyAndValue
	 * @return
	 */
	public Boolean multipleSetOnNotExist(Map<String, String> keyAndValue);

	/**
	 * [SETRANGE]Overwrite part of a string at key starting at the specified offset
	 * @param key
	 * @param offset
	 * @param value
	 * @return
	 */
	public Integer setRange(String key, int offset, String value);

	/**
	 * [DECRBY]Decrement the integer value of a key by the given number
	 * @param key
	 * @param decrement
	 * @return
	 */
	public Integer decrementByValue(String key, int decrement);

	/**
	 * [INCR]Increment the integer value of a key by one
	 * @param key
	 * @return
	 */
	public Long increment(String key);

	/**
	 * [SET]Set the string value of a key
	 * @param key
	 * @param value
	 * @return
	 */
	public <T> Boolean set(String key, T value);

	/**
	 * [STRLEN]Get the length of the value stored in a key
	 * @param key
	 * @return
	 */
	public Integer strLength(String key);

	/**
	 * [GET]Get the value of a key
	 * @param key
	 * @return
	 */
	public <T> T get(String key);

	/**
	 * [INCRBY]Increment the integer value of a key by the given number
	 * @param key
	 * @param increment
	 * @return
	 */
	public Long incrementByValue(String key, int increment);

	/**
	 * [SETBIT]Sets or clears the bit at offset in the string value stored at key
	 * @param key
	 * @param offset
	 * @param value
	 * @return
	 */
	public Boolean setBit(String key, int offset, boolean value);

	/**
	 * [GETBIT]Returns the bit value at offset in the string value stored at key
	 * @param key
	 * @param offset
	 * @return
	 */
	public Boolean getBit(String key, int offset);

	/**
	 * [MGET]Get the values of all the given keys
	 * @param keys
	 * @return
	 */
	public <T> List<T> multipleGet(String... keys);

	/**
	 * [SETEX]Set the value and expiration of a key
	 * @param key
	 * @param seconds
	 * @param value
	 * @return
	 */
	public <T> Boolean setAndExpire(String key, int seconds, T value);
}
