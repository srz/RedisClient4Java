package org.elk.redis4j.impl.database;

import java.util.ArrayList;
import java.util.Map;

import org.elk.redis4j.api.IPipelining;
import org.elk.redis4j.api.ListPosition;
import org.elk.redis4j.api.RedisCommand;
import org.elk.redis4j.api.database.IDataBaseConnector;
import org.elk.redis4j.impl.util.ParameterConvert;


public abstract class BatchCommandlist implements IPipelining
{
	protected ArrayList<Object[]> commandList;
	protected IDataBaseConnector connector;

	public BatchCommandlist(IDataBaseConnector connector)
	{
		this.connector = connector;
		this.commandList = new ArrayList<Object[]>();
	}

	protected void addCommand(RedisCommand command, Object... args)
	{
		addCommand(this.commandList.size(), command, args);
	}
	
	protected void addCommand(int index, RedisCommand command, Object... args)
	{
		Object[] cmd = new Object[1 + args.length];
		cmd[0] = command;
		System.arraycopy(args, 0, cmd, 1, args.length);
		this.commandList.add(index, cmd);
	}
	
	@Override
	public void append(String key, String value)
	{
		this.addCommand(RedisCommand.APPEND, key, value);
	}
	
	@Override
	public void echo(String message)
	{
		this.addCommand(RedisCommand.ECHO, message);
	}

	@Override
	public void listBlockLeftPop(int timeout, String... keys)
	{
		Object[] args = new Object[keys.length+1];
		System.arraycopy(keys, 0, args, 0, keys.length);
		args[keys.length] = timeout;
		
		this.addCommand(RedisCommand.BLPOP, args);
	}

	@Override
	public void listBlockRightPop(int timeout, String... keys)
	{
		Object[] args = new Object[keys.length+1];
		System.arraycopy(keys, 0, args, 0, keys.length);
		args[keys.length] = timeout;
		
		this.addCommand(RedisCommand.BRPOP, args);
	}

	@Override
	public void listBlockRightPopLeftPush(String source, String destination, int timeout)
	{
		this.addCommand(RedisCommand.BRPOPLPUSH, source, destination, timeout);
	}
	
	@Override
	public void decrement(String key)
	{
		this.addCommand(RedisCommand.DECR, key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#decrby(java.lang.String, int)
	 */
	@Override
	public void decrementByValue(String key, int decrement)
	{
		this.addCommand(RedisCommand.DECRBY, key, decrement);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#del(java.lang.String[])
	 */
	@Override
	public void del(String... keys)
	{
		this.addCommand(RedisCommand.DEL, (Object[])keys);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#exists(java.lang.String)
	 */
	@Override
	public void exists(String key)
	{
		this.addCommand(RedisCommand.EXISTS, key);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#expire(java.lang.String, int)
	 */
	@Override
	public void expire(String key, int seconds)
	{
		this.addCommand(RedisCommand.EXPIRE, key, seconds);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#expireat(java.lang.String, long)
	 */
	@Override
	public void expireAsTimestamp(String key, long timestamp)
	{
		this.addCommand(RedisCommand.EXPIREAT, key, timestamp);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#get(java.lang.String)
	 */
	@Override
	public void get(String key)
	{
		this.addCommand(RedisCommand.GET, key);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#getbit(java.lang.String, int)
	 */
	@Override
	public void getBit(String key, int offset)
	{
		this.addCommand(RedisCommand.GETBIT, key, offset);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#getrange(java.lang.String, int,
	 * int)
	 */
	@Override
	public void getRange(String key, int start, int end)
	{
		this.addCommand(RedisCommand.GETRANGE, key, start, end);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#getset(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void getSet(String key, String value)
	{
		this.addCommand(RedisCommand.GETSET, key, value);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#hdel(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void hashesDel(String key, String field)
	{
		this.addCommand(RedisCommand.HDEL, key, field);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#hexists(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void hashesExists(String key, String field)
	{
		this.addCommand(RedisCommand.HEXISTS, key, field);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#hget(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void hashesGet(String key, String field)
	{
		this.addCommand(RedisCommand.HGET, key, field);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#hgetall(java.lang.String)
	 */
	@Override
	public void hashesGetAllValue(String key)
	{
		this.addCommand(RedisCommand.HVALS, key);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#hincrby(java.lang.String,
	 * java.lang.String, int)
	 */
	@Override
	public void hashesIncrementByValue(String key, String field, int increment)
	{
		this.addCommand(RedisCommand.HINCRBY, key, field, increment);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#hkeys(java.lang.String)
	 */
	@Override
	public void hashesGetAllField(String key)
	{
		this.addCommand(RedisCommand.HKEYS, key);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#hlen(java.lang.String)
	 */
	@Override
	public void hashesLength(String key)
	{
		this.addCommand(RedisCommand.HLEN, key);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#hmget(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void hashesMultipleFieldGet(String key, String... fields)
	{
		Object[] args = new Object[fields.length+1];
		args[0] = key;
		System.arraycopy(fields, 0, args, 1, fields.length);
		this.addCommand(RedisCommand.HMGET, args);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#hmset(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void hashesMultipleSet(String key, Map<String, String> fieldAndValue)
	{
		String[] allKey = ParameterConvert.mapToStringArray(fieldAndValue);
		Object[] args = new Object[allKey.length+1];
		args[0] = key;
		System.arraycopy(allKey, 0, args, 1, allKey.length);
		
		this.addCommand(RedisCommand.HMSET, args);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#hset(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void hashesSet(String key, String field, String value)
	{
		this.addCommand(RedisCommand.HSET, key, field, value);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#hsetnx(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void hashesSetNotExistField(String key, String field, String value)
	{
		this.addCommand(RedisCommand.HSETNX, key, field, value);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#hvals(java.lang.String)
	 */
	@Override
	public void hashesGetAll(String key)
	{
		this.addCommand(RedisCommand.HGETALL, key);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#incr(java.lang.String)
	 */
	@Override
	public void increment(String key)
	{
		this.addCommand(RedisCommand.INCR, key);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#incrby(java.lang.String, int)
	 */
	@Override
	public void incrementByValue(String key, int increment)
	{
		this.addCommand(RedisCommand.INCRBY, key, increment);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#keys(java.lang.String)
	 */
	@Override
	public void keys(String pattern)
	{
		this.addCommand(RedisCommand.KEYS, pattern);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#lindex(java.lang.String, int)
	 */
	@Override
	public void listIndex(String key, int index)
	{
		this.addCommand(RedisCommand.LINDEX, key, index);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#linsert(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void listInsert(String key, ListPosition beforeOrAfter, String pivot, String value)
	{
		this.addCommand(RedisCommand.LINSERT, key, beforeOrAfter.toString(), pivot, value);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#llen(java.lang.String)
	 */
	@Override
	public void listLength(String key)
	{
		this.addCommand(RedisCommand.LLEN, key);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#lpop(java.lang.String)
	 */
	@Override
	public void listLeftPop(String key)
	{
		this.addCommand(RedisCommand.LPOP, key);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#lpush(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void listLeftPush(String key, String value)
	{
		this.addCommand(RedisCommand.LPUSH, key, value);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#lpushx(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void listLeftPushOnExist(String key, String value)
	{
		this.addCommand(RedisCommand.LPUSHX, key, value);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#lrange(java.lang.String, int,
	 * int)
	 */
	@Override
	public void listRange(String key, int start, int stop)
	{
		this.addCommand(RedisCommand.LRANGE, key, start, stop);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#lrem(java.lang.String, int,
	 * java.lang.String)
	 */
	@Override
	public void listRemove(String key, int count, String value)
	{
		this.addCommand(RedisCommand.LREM, count, value);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#lset(java.lang.String, int,
	 * java.lang.String)
	 */
	@Override
	public void listSet(String key, int index, String value)
	{
		this.addCommand(RedisCommand.LSET, key, index, value);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#ltrim(java.lang.String, int, int)
	 */
	@Override
	public void listTrim(String key, int start, int stop)
	{
		this.addCommand(RedisCommand.LTRIM, key, start, stop);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#mget(java.lang.String[])
	 */
	@Override
	public void multipleGet(String... keys)
	{
		this.addCommand(RedisCommand.MGET, (Object[])keys);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#move(java.lang.String, int)
	 */
	@Override
	public void move(String key, int indexDB)
	{
		this.addCommand(RedisCommand.MOVE, key, indexDB);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#mset(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void multipleSet(Map<String, String> keyAndValue)
	{
		Object[] allKey = ParameterConvert.mapToStringArray(keyAndValue);
		this.addCommand(RedisCommand.MSET, allKey);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#msetnx(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void multipleSetOnNotExist(Map<String, String> keyAndValue)
	{
		Object[] allKey = ParameterConvert.mapToStringArray(keyAndValue);
		this.addCommand(RedisCommand.MSETNX, allKey);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#persist(java.lang.String)
	 */
	@Override
	public void persist(String key)
	{
		this.addCommand(RedisCommand.PERSIST, key);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#randomkey()
	 */
	@Override
	public void randomKey()
	{
		this.addCommand(RedisCommand.RANDOMKEY);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#rename(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void rename(String key, String newKey)
	{
		this.addCommand(RedisCommand.RENAME, key, newKey);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#renamenx(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void renameOnNotExistNewKey(String key, String newKey)
	{
		this.addCommand(RedisCommand.RENAMENX, key, newKey);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#rpop(java.lang.String)
	 */
	@Override
	public void listRightPop(String key)
	{
		this.addCommand(RedisCommand.RPOP, key);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#rpoplpush(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void listRightPopLeftPush(String source, String destination)
	{
		this.addCommand(RedisCommand.RPOPLPUSH, source, destination);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#rpush(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void listRightPush(String key, String value)
	{
		this.addCommand(RedisCommand.RPUSH, key, value);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#rpushx(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void listRightPushOnExist(String key, String value)
	{
		this.addCommand(RedisCommand.RPUSHX, key, value);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#sadd(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void setsAdd(String key, String member)
	{
		this.addCommand(RedisCommand.SADD, key, member);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#scard(java.lang.String)
	 */
	@Override
	public void setsCard(String key)
	{
		this.addCommand(RedisCommand.SCARD, key);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#sdiff(java.lang.String[])
	 */
	@Override
	public void setsDiff(String... keys)
	{
		this.addCommand(RedisCommand.SDIFF, (Object[])keys);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#sdiffstore(java.lang.String,
	 * java.lang.String[])
	 */
	@Override
	public void setsDiffStore(String destination, String... keys)
	{
		Object[] args = new Object[keys.length+1];
		args[0] = destination;
		System.arraycopy(keys, 0, args, 1, keys.length);
		
		this.addCommand(RedisCommand.SDIFFSTORE, args);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#set(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void set(String key, String value)
	{
		//RedisResponse res = this.connector.executeCommand(RedisCommand.SET, key, value);
		//System.err.println("--------------" + res.getTextValue());
		this.addCommand(RedisCommand.SET, key, value);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#setbit(java.lang.String, int,
	 * int)
	 */
	@Override
	public void setBit(String key, int offset, boolean value)
	{
		this.addCommand(RedisCommand.SETBIT, key, offset, value ? 1: 0);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#setex(java.lang.String, int,
	 * java.lang.String)
	 */
	@Override
	public void setAndExpire(String key, int seconds, String value)
	{
		this.addCommand(RedisCommand.SETEX, key, seconds, value);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#setnx(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void setOnNotExist(String key, String value)
	{
		this.addCommand(RedisCommand.SETNX, key, value);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#setrange(java.lang.String, int,
	 * java.lang.String)
	 */
	@Override
	public void setRange(String key, int offset, String value)
	{
		this.addCommand(RedisCommand.SETRANGE, key, offset, value);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#sinter(java.lang.String[])
	 */
	@Override
	public void setsInter(String... keys)
	{
		this.addCommand(RedisCommand.SINTER, (Object[])keys);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#sinterstore(java.lang.String,
	 * java.lang.String[])
	 */
	@Override
	public void setsInterStore(String destination, String... keys)
	{
		Object[] args = new Object[keys.length+1];
		args[0] = destination;
		System.arraycopy(keys, 0, args, 1, keys.length);
		
		this.addCommand(RedisCommand.SINTERSTORE, args);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#sismember(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void setsIsMember(String key, String member)
	{
		this.addCommand(RedisCommand.SISMEMBER, key, member);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#smembers(java.lang.String)
	 */
	@Override
	public void setsMembers(String key)
	{
		this.addCommand(RedisCommand.SMEMBERS, key);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#smove(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void setsMove(String source, String destination, String member)
	{
		this.addCommand(RedisCommand.SMOVE, source, destination, member);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#sort(java.lang.String,
	 * java.lang.String[])
	 */
	@Override
	public void sort(String key, String... params)
	{
		Object[] args = new Object[params.length+1];
		args[0] = key;
		System.arraycopy(params, 0, args, 1, params.length);
		
		this.addCommand(RedisCommand.SORT, args);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#spop(java.lang.String)
	 */
	@Override
	public void setsPop(String key)
	{
		this.addCommand(RedisCommand.SPOP, key);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#srandmember(java.lang.String)
	 */
	@Override
	public void setsRandMember(String key)
	{
		this.addCommand(RedisCommand.SRANDMEMBER, key);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#srem(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void setsRemove(String key, String member)
	{
		this.addCommand(RedisCommand.SREM, key, member);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#strlen(java.lang.String)
	 */
	@Override
	public void strLength(String key)
	{
		this.addCommand(RedisCommand.STRLEN, key);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#sunion(java.lang.String[])
	 */
	@Override
	public void setsUnion(String... keys)
	{
		this.addCommand(RedisCommand.SUNION, (Object[])keys);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#sunionstore(java.lang.String,
	 * java.lang.String[])
	 */
	@Override
	public void setsUnionStore(String destination, String... keys)
	{
		Object[] args = new Object[keys.length+1];
		args[0] = destination;
		System.arraycopy(keys, 0, args, 1, keys.length);
		
		this.addCommand(RedisCommand.SUNIONSTORE, args);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#ttl(java.lang.String)
	 */
	@Override
	public void timeToLive(String key)
	{
		this.addCommand(RedisCommand.TTL, key);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#type(java.lang.String)
	 */
	@Override
	public void type(String key)
	{
		this.addCommand(RedisCommand.TYPE, key);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#zadd(java.lang.String, int,
	 * java.lang.String)
	 */
	@Override
	public void sortedSetsAdd(String key, int score, String member)
	{
		this.addCommand(RedisCommand.ZADD, key, score, member);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#zcard(java.lang.String)
	 */
	@Override
	public void sortedSetsCard(String key)
	{
		this.addCommand(RedisCommand.ZCARD, key);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#zcount(java.lang.String, int,
	 * int)
	 */
	@Override
	public void sortedSetsCount(String key, int min, int max)
	{
		this.addCommand(RedisCommand.ZCOUNT, key, min, max);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#zincrby(java.lang.String, int,
	 * java.lang.String)
	 */
	@Override
	public void sortedSetsIncrementByValue(String key, int increment, String member)
	{
		this.addCommand(RedisCommand.ZINCRBY, key, increment, member);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#zinterstore(java.lang.String[])
	 */
	@Override
	public void sortedSetsInterStore(String destination, String... keys)
	{
		Object[] args = new Object[keys.length + 2];
		args[0] = destination;
		args[1] = keys.length;
		System.arraycopy(keys, 0, args, 2, keys.length);
		this.addCommand(RedisCommand.ZINTERSTORE, args);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#zrange(java.lang.String, int,
	 * int)
	 */
	@Override
	public void sortedSetsRange(String key, int start, int stop)
	{
		this.addCommand(RedisCommand.ZRANGE, key, start, stop);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#zrangebyscore(java.lang.String[])
	 */
	@Override
	public void sortedSetsRangeByScore(String key, int min, int max)
	{
		this.addCommand(RedisCommand.ZRANGEBYSCORE, key, min, max);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#zrank(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void sortedSetsRank(String key, String member)
	{
		this.addCommand(RedisCommand.ZRANK, key, member);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#zrem(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void sortedSetsRemove(String key, String member)
	{
		this.addCommand(RedisCommand.ZREM, key, member);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#zremrangebyrank(java.lang.String,
	 * int, int)
	 */
	@Override
	public void sortedSetsRemoveRangeByRank(String key, int start, int stop)
	{
		this.addCommand(RedisCommand.ZREMRANGEBYRANK, key, start, stop);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.elk.redis4j.api.IDatabase#zremrangebyscore(java.lang.String,
	 * int, int)
	 */
	@Override
	public void sortedSetsRemoveRangeByScore(String key, int min, int max)
	{
		this.addCommand(RedisCommand.ZREMRANGEBYSCORE, key, min, max);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#zrevrange(java.lang.String, int,
	 * int)
	 */
	@Override
	public void sortedSetsRevRange(String key, int start, int stop)
	{
		this.addCommand(RedisCommand.ZREVRANGE, key, start, stop);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.elk.redis4j.api.IDatabase#zrevrangebyscore(java.lang.String[])
	 */
	@Override
	public void sortedSetsRevRangeByScore(String key, int max, int min)
	{
		this.addCommand(RedisCommand.ZREVRANGEBYSCORE, key, max, min);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#zrevrank(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void sortedSetsRevRank(String key, String member)
	{
		this.addCommand(RedisCommand.ZREVRANK, key, member);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#zscore(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void sortedSetsScore(String key, String member)
	{
		this.addCommand(RedisCommand.ZSCORE, key, member);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#zunionstore(java.lang.String[])
	 */
	@Override
	public void sortedSetsUnionStore(String destination, String... keys)
	{
		Object[] args = new Object[keys.length + 2];
		args[0] = destination;
		args[1] = keys.length;
		System.arraycopy(keys, 0, args, 2, keys.length);
		this.addCommand(RedisCommand.ZUNIONSTORE, args);
		
	}
}
