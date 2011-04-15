/**
 * 
 */
package com.handinfo.redis4j.impl.database;

import java.util.HashMap;
import java.util.Map;

import com.handinfo.redis4j.api.ListPosition;
import com.handinfo.redis4j.api.RedisCommand;
import com.handinfo.redis4j.api.RedisResponseMessage;
import com.handinfo.redis4j.api.Sharding;
import com.handinfo.redis4j.api.database.IDatabaseBatch;
import com.handinfo.redis4j.api.database.IDatabaseTransaction;
import com.handinfo.redis4j.api.database.IRedisDatabaseClient;
import com.handinfo.redis4j.api.exception.ErrorCommandException;
import com.handinfo.redis4j.impl.util.ParameterConvert;

/**
 * Database版本客户端
 */
public final class RedisDatabaseClient extends DatabaseClient implements IRedisDatabaseClient
{
	public RedisDatabaseClient(Sharding sharding)
	{
		super(sharding);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.database.IRedisDatabaseClient#getBatch()
	 */
	@Override
	public IDatabaseBatch getNewBatch()
	{
		return new DatabaseBatch(super.connector);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.handinfo.redis4j.api.database.IRedisDatabaseClient#getBatch(boolean)
	 */
	@Override
	public IDatabaseTransaction getNewTransaction()
	{
		return new DatabaseTransaction(super.connector);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#append(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Integer append(String key, String value)
	{
		return super.sendRequest(Integer.class, null, RedisCommand.APPEND, key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#bgrewriteaof()
	 */
	@Override
	public String backgroundRewriteAOF()
	{
		return super.sendRequest(String.class, null, RedisCommand.BGREWRITEAOF);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#bgsave()
	 */
	@Override
	public String backgroundSave()
	{
		return super.sendRequest(String.class, null, RedisCommand.BGSAVE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#blpop(java.lang.String, int)
	 */
	@Override
	public String[] listBlockLeftPop(int timeout, String... keys)
	{
		Object[] args = new Object[keys.length+1];
		System.arraycopy(keys, 0, args, 0, keys.length);
		args[keys.length] = timeout;
		return super.sendRequest(String[].class, null, RedisCommand.BLPOP, args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#brpop(java.lang.String, int)
	 */
	@Override
	public String[] listBlockRightPop(int timeout, String... keys)
	{
		Object[] args = new Object[keys.length+1];
		System.arraycopy(keys, 0, args, 0, keys.length);
		args[keys.length] = timeout;
		return super.sendRequest(String[].class, null, RedisCommand.BRPOP, args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#brpoplpush(java.lang.String,
	 * java.lang.String, int)
	 */
	@Override
	public String listBlockRightPopLeftPush(String source, String destination, int timeout)
	{
		return super.sendRequest(String.class, null, RedisCommand.BRPOPLPUSH, source, destination, timeout);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#config_get(java.lang.String)
	 */
	@Override
	public Map<String, String> configGet(String parameter)
	{
		return ParameterConvert.stringArrayToMap(super.sendRequest(String[].class, null, RedisCommand.CONFIG_GET, parameter));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#config_resetstat()
	 */
	@Override
	public Boolean configResetStat()
	{
		return super.sendRequest(Boolean.class, RedisResponseMessage.OK, RedisCommand.CONFIG_RESETSTAT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#config_set(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Boolean configSet(String parameter, String value)
	{
		return super.sendRequest(Boolean.class, RedisResponseMessage.OK, RedisCommand.CONFIG_SET, parameter, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#dbsize()
	 */
	@Override
	public Integer dbSize()
	{
		return super.sendRequest(Integer.class, null, RedisCommand.DBSIZE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#debug_object(java.lang.String)
	 */
	@Override
	public String debugObject(String key)
	{
		return super.sendRequest(String.class, null, RedisCommand.DEBUG_OBJECT, key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#debug_segfault()
	 */
//	@Override
//	public String[] debugSegfault()
//	{
//		return super.sendRequest(String[].class, null, RedisCommand.DEBUG_SEGFAULT);
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#decr(java.lang.String)
	 */
	@Override
	public Long decrement(String key)
	{
		return super.sendRequest(Long.class, null, RedisCommand.DECR, key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#decrby(java.lang.String, int)
	 */
	@Override
	public Long decrementByValue(String key, int decrement)
	{
		return super.sendRequest(Long.class, null, RedisCommand.DECRBY, key, decrement);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#del(java.lang.String[])
	 */
	@Override
	public Integer del(String... keys)
	{
		return super.sendRequest(Integer.class, null, RedisCommand.DEL, (Object[]) keys);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#echo(java.lang.String)
	 */
	@Override
	public String echo(String message)
	{
		return super.sendRequest(String.class, null, RedisCommand.ECHO, message);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#exists(java.lang.String)
	 */
	@Override
	public Boolean exists(String key)
	{
		return super.sendRequest(Boolean.class, RedisResponseMessage.INTEGER_1, RedisCommand.EXISTS, key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#expire(java.lang.String, int)
	 */
	@Override
	public Boolean expire(String key, int seconds)
	{
		return super.sendRequest(Boolean.class, RedisResponseMessage.INTEGER_1, RedisCommand.EXPIRE, key, seconds);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#expireat(java.lang.String, long)
	 */
	@Override
	public Boolean expireAsTimestamp(String key, long timestamp)
	{
		return super.sendRequest(Boolean.class, RedisResponseMessage.INTEGER_1, RedisCommand.EXPIREAT, key, timestamp);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#flushall()
	 */
	@Override
	public Boolean flushAllDB()
	{
		return super.sendRequest(Boolean.class, RedisResponseMessage.OK, RedisCommand.FLUSHALL);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#flushdb()
	 */
	@Override
	public Boolean flushCurrentDB()
	{
		return super.sendRequest(Boolean.class, RedisResponseMessage.OK, RedisCommand.FLUSHDB);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#get(java.lang.String)
	 */
	@Override
	public String get(String key)
	{
		return super.sendRequest(String.class, null, RedisCommand.GET, key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#getbit(java.lang.String, int)
	 */
	@Override
	public Boolean getBit(String key, int offset)
	{
		return super.sendRequest(Boolean.class, RedisResponseMessage.INTEGER_1, RedisCommand.GETBIT, key, offset);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#getrange(java.lang.String, int,
	 * int)
	 */
	@Override
	public String getRange(String key, int start, int end)
	{
		return super.sendRequest(String.class, null, RedisCommand.GETRANGE, key, start, end);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#getset(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public String getSet(String key, String value)
	{
		return super.sendRequest(String.class, null, RedisCommand.GETSET, key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#hdel(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Boolean hashesDel(String key, String field)
	{
		return super.sendRequest(Boolean.class, RedisResponseMessage.INTEGER_1, RedisCommand.HDEL, key, field);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#hexists(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Boolean hashesExists(String key, String field)
	{
		return super.sendRequest(Boolean.class, RedisResponseMessage.INTEGER_1, RedisCommand.HEXISTS, key, field);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#hget(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public String hashesGet(String key, String field)
	{
		return super.sendRequest(String.class, null, RedisCommand.HGET, key, field);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#hgetall(java.lang.String)
	 */
	@Override
	public Map<String, String> hashesGetAllValue(String key)
	{
		String[] result = super.sendRequest(String[].class, null, RedisCommand.HGETALL, key);
		return ParameterConvert.stringArrayToMap(result);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#hincrby(java.lang.String,
	 * java.lang.String, int)
	 */
	@Override
	public Integer hashesIncrementByValue(String key, String field, int increment)
	{
		return super.sendRequest(Integer.class, null, RedisCommand.HINCRBY, key, field, increment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#hkeys(java.lang.String)
	 */
	@Override
	public String[] hashesGetAllField(String key)
	{
		return super.sendRequest(String[].class, null, RedisCommand.HKEYS, key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#hlen(java.lang.String)
	 */
	@Override
	public Integer hashesLength(String key)
	{
		return super.sendRequest(Integer.class, null, RedisCommand.HLEN, key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#hmget(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public String[] hashesMultipleFieldGet(String key, String... fields)
	{
		Object[] args = new Object[fields.length+1];
		args[0] = key;
		System.arraycopy(fields, 0, args, 1, fields.length);
		
		return super.sendRequest(String[].class, null, RedisCommand.HMGET, args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#hmset(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public Boolean hashesMultipleSet(String key, Map<String, String> fieldAndValue)
	{
		String[] allKey = ParameterConvert.mapToStringArray(fieldAndValue);
		Object[] args = new Object[allKey.length+1];
		args[0] = key;
		System.arraycopy(allKey, 0, args, 1, allKey.length);
		
		return super.sendRequest(Boolean.class, RedisResponseMessage.OK, RedisCommand.HMSET, args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#hset(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public Boolean hashesSet(String key, String field, String value)
	{
		return super.sendRequest(Boolean.class, RedisResponseMessage.INTEGER_1, RedisCommand.HSET, key, field, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#hsetnx(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public Boolean hashesSetNotExistField(String key, String field, String value)
	{
		return super.sendRequest(Boolean.class, RedisResponseMessage.INTEGER_1, RedisCommand.HSETNX, key, field, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#hashesGetAll(java.lang.String)
	 */
	@Override
	public Map<String, String> hashesGetAll(String key)
	{
		String[] resultList = super.sendRequest(String[].class, null, RedisCommand.HGETALL, key);
		return ParameterConvert.stringArrayToMap(resultList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#incr(java.lang.String)
	 */
	@Override
	public Long increment(String key)
	{
		return super.sendRequest(Long.class, null, RedisCommand.INCR, key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#incrby(java.lang.String, int)
	 */
	@Override
	public Long incrementByValue(String key, int increment)
	{
		return super.sendRequest(Long.class, null, RedisCommand.INCRBY, key, increment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#info()
	 */
	@Override
	public String info()
	{
		return super.sendRequest(String.class, null, RedisCommand.INFO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#keys(java.lang.String)
	 */
	@Override
	public String[] keys(String pattern)
	{
		return super.sendRequest(String[].class, null, RedisCommand.KEYS, pattern);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#lastsave()
	 */
	@Override
	public Long lastSave()
	{
		return super.sendRequest(Long.class, null, RedisCommand.LASTSAVE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#lindex(java.lang.String, int)
	 */
	@Override
	public String listIndex(String key, int index)
	{
		return super.sendRequest(String.class, null, RedisCommand.LINDEX, key, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#linsert(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Integer listLeftInsert(String key, ListPosition beforeOrAfter, String pivot, String value)
	{
		return super.sendRequest(Integer.class, null, RedisCommand.LINSERT, key, beforeOrAfter.toString(), pivot, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#llen(java.lang.String)
	 */
	@Override
	public Integer listLength(String key)
	{
		return super.sendRequest(Integer.class, null, RedisCommand.LLEN, key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#lpop(java.lang.String)
	 */
	@Override
	public String listLeftPop(String key)
	{
		return super.sendRequest(String.class, null, RedisCommand.LPOP, key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#lpush(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Integer listLeftPush(String key, String value)
	{
		return super.sendRequest(Integer.class, null, RedisCommand.LPUSH, key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#lpushx(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Integer listLeftPushOnExist(String key, String value)
	{
		return super.sendRequest(Integer.class, null, RedisCommand.LPUSHX, key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#lrange(java.lang.String, int,
	 * int)
	 */
	@Override
	public String[] listRange(String key, int start, int stop)
	{
		return super.sendRequest(String[].class, null, RedisCommand.LRANGE, key, start, stop);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#lrem(java.lang.String, int,
	 * java.lang.String)
	 */
	@Override
	public Integer listRemove(String key, int count, String value)
	{
		return super.sendRequest(Integer.class, null, RedisCommand.LREM, key, count, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#lset(java.lang.String, int,
	 * java.lang.String)
	 */
	@Override
	public Boolean listSet(String key, int index, String value)
	{
		return super.sendRequest(Boolean.class, RedisResponseMessage.OK, RedisCommand.LSET, key, index, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#ltrim(java.lang.String, int, int)
	 */
	@Override
	public Boolean listTrim(String key, int start, int stop)
	{
		return super.sendRequest(Boolean.class, RedisResponseMessage.OK, RedisCommand.LTRIM, key, start, stop);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#mget(java.lang.String[])
	 */
	@Override
	public String[] multipleGet(String... keys)
	{
		return super.sendRequest(String[].class, null, RedisCommand.MGET, (Object[])keys);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#move(java.lang.String, int)
	 */
	@Override
	public Boolean move(String key, int indexDB)
	{
		return super.sendRequest(Boolean.class, RedisResponseMessage.INTEGER_1, RedisCommand.MOVE, key, indexDB);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#mset(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Boolean multipleSet(HashMap<String, String> keyAndValue)
	{
		Object[] allKey = ParameterConvert.mapToStringArray(keyAndValue);
		
		return super.sendRequest(Boolean.class, RedisResponseMessage.OK, RedisCommand.MSET, allKey);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#msetnx(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Boolean multipleSetOnNotExist(HashMap<String, String> keyAndValue)
	{
		Object[] allKey = ParameterConvert.mapToStringArray(keyAndValue);
		return super.sendRequest(Boolean.class, RedisResponseMessage.INTEGER_1, RedisCommand.MSETNX, allKey);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#persist(java.lang.String)
	 */
	@Override
	public Boolean persist(String key)
	{
		return super.sendRequest(Boolean.class, RedisResponseMessage.INTEGER_1, RedisCommand.PERSIST, key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#ping()
	 */
	@Override
	public Boolean ping()
	{
		return super.sendRequest(Boolean.class, RedisResponseMessage.PONG, RedisCommand.PING);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#randomkey()
	 */
	@Override
	public String randomKey()
	{
		return super.sendRequest(String.class, null, RedisCommand.RANDOMKEY);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#rename(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Boolean rename(String key, String newKey)
	{
		return super.sendRequest(Boolean.class, RedisResponseMessage.OK, RedisCommand.RENAME, key, newKey);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#renamenx(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Boolean renameOnNotExistNewKey(String key, String newKey)
	{
		return super.sendRequest(Boolean.class, RedisResponseMessage.INTEGER_1, RedisCommand.RENAMENX, key, newKey);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#rpop(java.lang.String)
	 */
	@Override
	public String listRightPop(String key)
	{
		return super.sendRequest(String.class, null, RedisCommand.RPOP, key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#rpoplpush(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public String listRightPopLeftPush(String source, String destination)
	{
		return super.sendRequest(String.class, null, RedisCommand.RPOPLPUSH, source, destination);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#rpush(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Integer listRightPush(String key, String value)
	{
		return super.sendRequest(Integer.class, null, RedisCommand.RPUSH, key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#rpushx(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Integer listRightPushOnExist(String key, String value)
	{
		return super.sendRequest(Integer.class, null, RedisCommand.RPUSHX, key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#sadd(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Boolean setsAdd(String key, String member)
	{
		return super.sendRequest(Boolean.class, RedisResponseMessage.INTEGER_1, RedisCommand.SADD, key, member);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#save()
	 */
	@Override
	public Boolean save()
	{
		return super.sendRequest(Boolean.class, RedisResponseMessage.OK, RedisCommand.SAVE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#scard(java.lang.String)
	 */
	@Override
	public Integer setsCard(String key)
	{
		return super.sendRequest(Integer.class, null, RedisCommand.SCARD, key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#sdiff(java.lang.String[])
	 */
	@Override
	public String[] setsDiff(String... keys)
	{
		return super.sendRequest(String[].class, null, RedisCommand.SDIFF, (Object[])keys);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#sdiffstore(java.lang.String,
	 * java.lang.String[])
	 */
	@Override
	public Integer setsDiffStore(String destination, String... keys)
	{
		Object[] args = new Object[keys.length+1];
		args[0] = destination;
		System.arraycopy(keys, 0, args, 1, keys.length);

		return super.sendRequest(Integer.class, null, RedisCommand.SDIFFSTORE, args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#set(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Boolean set(String key, String value)
	{
		return super.sendRequest(Boolean.class, RedisResponseMessage.OK, RedisCommand.SET, key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#setbit(java.lang.String, int,
	 * int)
	 */
	@Override
	public Boolean setBit(String key, int offset, boolean value)
	{
		return super.sendRequest(Boolean.class, RedisResponseMessage.INTEGER_1, RedisCommand.SETBIT, key, offset, value ? 1: 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#setex(java.lang.String, int,
	 * java.lang.String)
	 */
	@Override
	public Boolean setAndExpire(String key, int seconds, String value)
	{
		return super.sendRequest(Boolean.class, RedisResponseMessage.OK, RedisCommand.SETEX, key, seconds, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#setnx(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Boolean setOnNotExist(String key, String value)
	{
		return super.sendRequest(Boolean.class, RedisResponseMessage.INTEGER_1, RedisCommand.SETNX, key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#setrange(java.lang.String, int,
	 * java.lang.String)
	 */
	@Override
	public Integer setRange(String key, int offset, String value)
	{
		return super.sendRequest(Integer.class, null, RedisCommand.SETRANGE, key, offset, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#shutdown()
	 */
	@Override
	public Boolean shutdownServer()
	{
		return super.sendRequest(Boolean.class, null, RedisCommand.SHUTDOWN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#sinter(java.lang.String[])
	 */
	@Override
	public String[] setsInter(String... keys)
	{
		return super.sendRequest(String[].class, null, RedisCommand.SINTER, (Object[])keys);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#sinterstore(java.lang.String,
	 * java.lang.String[])
	 */
	@Override
	public Integer setsInterStore(String destination, String... keys)
	{
		Object[] args = new Object[keys.length+1];
		args[0] = destination;
		System.arraycopy(keys, 0, args, 1, keys.length);
		
		return super.sendRequest(Integer.class, null, RedisCommand.SINTERSTORE, args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#sismember(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Boolean setsIsMember(String key, String member)
	{
		return super.sendRequest(Boolean.class, RedisResponseMessage.INTEGER_1, RedisCommand.SISMEMBER, key, member);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#slaveof()
	 */
	@Override
	public Boolean slaveOf()
	{
		return super.sendRequest(Boolean.class, RedisResponseMessage.OK, RedisCommand.SLAVEOF);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#smembers(java.lang.String)
	 */
	@Override
	public String[] setsMembers(String key)
	{
		return super.sendRequest(String[].class, null, RedisCommand.SMEMBERS, key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#smove(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public Boolean setsMove(String source, String destination, String member)
	{
		return super.sendRequest(Boolean.class, RedisResponseMessage.INTEGER_1, RedisCommand.SMOVE, source, destination, member);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#sort(java.lang.String,
	 * java.lang.String[])
	 */
	@Override
	public Object[] sort(String key, String... params)
	{
		Object[] args = new Object[params.length+1];
		args[0] = key;
		System.arraycopy(params, 0, args, 1, params.length);

		
		return super.sendRequest(String[].class, null, RedisCommand.SORT, args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#spop(java.lang.String)
	 */
	@Override
	public String setsPop(String key)
	{
		return super.sendRequest(String.class, null, RedisCommand.SPOP, key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#srandmember(java.lang.String)
	 */
	@Override
	public String setsRandMember(String key)
	{
		return super.sendRequest(String.class, null, RedisCommand.SRANDMEMBER, key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#srem(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Boolean setsRemove(String key, String member)
	{
		return super.sendRequest(Boolean.class, RedisResponseMessage.INTEGER_1, RedisCommand.SREM, key, member);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#strlen(java.lang.String)
	 */
	@Override
	public Integer strLength(String key)
	{
		return super.sendRequest(Integer.class, null, RedisCommand.STRLEN, key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#sunion(java.lang.String[])
	 */
	@Override
	public String[] setsUnion(String... keys)
	{
		return super.sendRequest(String[].class, null, RedisCommand.SUNION, (Object[])keys);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#sunionstore(java.lang.String,
	 * java.lang.String[])
	 */
	@Override
	public Integer setsUnionStore(String destination, String... keys)
	{
		Object[] args = new Object[keys.length+1];
		args[0] = destination;
		System.arraycopy(keys, 0, args, 1, keys.length);
		
		return super.sendRequest(Integer.class, null, RedisCommand.SUNIONSTORE, args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#sync()
	 */
	@Override
	public String sync()
	{
		return super.sendRequest(String.class, null, RedisCommand.SYNC);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#ttl(java.lang.String)
	 */
	@Override
	public Integer timeToLive(String key)
	{
		return super.sendRequest(Integer.class, null, RedisCommand.TTL, key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#type(java.lang.String)
	 */
	@Override
	public String type(String key)
	{
		return super.sendRequest(String.class, null, RedisCommand.TYPE, key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#zadd(java.lang.String, int,
	 * java.lang.String)
	 */
	@Override
	public Boolean sortedSetsAdd(String key, int score, String member)
	{
		return super.sendRequest(Boolean.class, RedisResponseMessage.INTEGER_1, RedisCommand.ZADD, key, score, member);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#zcard(java.lang.String)
	 */
	@Override
	public Integer sortedSetsCard(String key)
	{
		return super.sendRequest(Integer.class, null, RedisCommand.ZCARD, key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#zcount(java.lang.String, int,
	 * int)
	 */
	@Override
	public Integer sortedSetsCount(String key, int min, int max)
	{
		return super.sendRequest(Integer.class, null, RedisCommand.ZCOUNT, key, min, max);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#zincrby(java.lang.String, int,
	 * java.lang.String)
	 */
	@Override
	public Double sortedSetsIncrementByValue(String key, int increment, String member)
	{
		return super.sendRequest(Double.class, null, RedisCommand.ZINCRBY, key, increment, member);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#zinterstore(java.lang.String[])
	 */
	@Override
	public Integer sortedSetsInterStore(String destination, String... keys)
	{
		Object[] args = new Object[keys.length + 2];
		args[0] = destination;
		args[1] = keys.length;
		System.arraycopy(keys, 0, args, 2, keys.length);
		return super.sendRequest(Integer.class, null, RedisCommand.ZINTERSTORE, args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#zrange(java.lang.String, int,
	 * int)
	 */
	@Override
	public String[] sortedSetsRange(String key, int start, int stop)
	{
		return super.sendRequest(String[].class, null, RedisCommand.ZRANGE, key, start, stop);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#zrangebyscore(java.lang.String[])
	 */
	@Override
	public String[] sortedSetsRangeByScore(String key, int min, int max)
	{
		return super.sendRequest(String[].class, null, RedisCommand.ZRANGEBYSCORE, key, min, max);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#zrank(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Integer sortedSetsRank(String key, String member)
	{
		return super.sendRequest(Integer.class, null, RedisCommand.ZRANK, key, member);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#zrem(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Boolean sortedSetsRemove(String key, String member)
	{
		return super.sendRequest(Boolean.class, RedisResponseMessage.INTEGER_1, RedisCommand.ZREM, key, member);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#zremrangebyrank(java.lang.String,
	 * int, int)
	 */
	@Override
	public Integer sortedSetsRemoveRangeByRank(String key, int start, int stop)
	{
		return super.sendRequest(Integer.class, null, RedisCommand.ZREMRANGEBYRANK, key, start, stop);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.handinfo.redis4j.api.IDatabase#zremrangebyscore(java.lang.String,
	 * int, int)
	 */
	@Override
	public Integer sortedSetsRemoveRangeByScore(String key, int min, int max)
	{
		return super.sendRequest(Integer.class, null, RedisCommand.ZREMRANGEBYSCORE, key, min, max);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#zrevrange(java.lang.String, int,
	 * int)
	 */
	@Override
	public String[] sortedSetsRevRange(String key, int start, int stop)
	{
		return super.sendRequest(String[].class, null, RedisCommand.ZREVRANGE, key, start, stop);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.handinfo.redis4j.api.IDatabase#zrevrangebyscore(java.lang.String[])
	 */
	@Override
	public String[] sortedSetsRevRangeByScore(String key, int max, int min)
	{
		return super.sendRequest(String[].class, null, RedisCommand.ZREVRANGEBYSCORE, key, max, min);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#zrevrank(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Integer sortedSetsRevRank(String key, String member)
	{
		return super.sendRequest(Integer.class, null, RedisCommand.ZREVRANK, key, member);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#zscore(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Integer sortedSetsScore(String key, String member)
	{
		return super.sendRequest(Integer.class, null, RedisCommand.ZSCORE, key, member);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#zunionstore(java.lang.String[])
	 */
	@Override
	public Integer sortedSetsUnionStore(String destination, String... keys)
	{
		Object[] args = new Object[keys.length + 2];
		args[0] = destination;
		args[1] = keys.length;
		System.arraycopy(keys, 0, args, 2, keys.length);
		return super.sendRequest(Integer.class, null, RedisCommand.ZUNIONSTORE, args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IConnection#quit()
	 */
	@Override
	public boolean quit()
	{
		super.connector.disConnect();
		return true;
	}
}
