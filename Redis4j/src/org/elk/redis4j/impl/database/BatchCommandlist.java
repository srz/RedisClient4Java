package org.elk.redis4j.impl.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.elk.redis4j.api.IDatabase;
import org.elk.redis4j.api.ListPosition;
import org.elk.redis4j.api.RedisCommand;
import org.elk.redis4j.api.RedisResponse;
import org.elk.redis4j.api.database.IDataBaseConnector;
import org.elk.redis4j.impl.util.ParameterConvert;


public abstract class BatchCommandlist implements IDatabase
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
//		Object[] cmd = new Object[command.getValue().length + args.length];
//		System.arraycopy(command.getValue(), 0, cmd, 0, command.getValue().length);
//		System.arraycopy(args, 0, cmd, command.getValue().length, args.length);
//		this.commandList.add(cmd);
		addCommand(this.commandList.size(), command, args);
	}
	
	protected void addCommand(int index, RedisCommand command, Object... args)
	{
		Object[] cmd = new Object[1 + args.length];
		cmd[0] = command;
		System.arraycopy(args, 0, cmd, 1, args.length);
		//System.arraycopy(command.getValue(), 0, cmd, 0, command.getValue().length);
		//System.arraycopy(args, 0, cmd, command.getValue().length, args.length);
		this.commandList.add(index, cmd);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#append(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Integer append(String key, String value)
	{
		this.addCommand(RedisCommand.APPEND, key, value);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#bgrewriteaof()
	 */
	@Override
	public String backgroundRewriteAOF()
	{
		this.addCommand(RedisCommand.BGREWRITEAOF);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#bgsave()
	 */
	@Override
	public String backgroundSave()
	{
		this.addCommand(RedisCommand.BGSAVE);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#blpop(java.lang.String, int)
	 */
	@Override
	public Entry<String, String> listBlockLeftPop(int timeout, String... keys)
	{
		Object[] args = new Object[keys.length+1];
		System.arraycopy(keys, 0, args, 0, keys.length);
		args[keys.length] = timeout;
		
		this.addCommand(RedisCommand.BLPOP, args);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#brpop(java.lang.String, int)
	 */
	@Override
	public Entry<String, String> listBlockRightPop(int timeout, String... keys)
	{
		Object[] args = new Object[keys.length+1];
		System.arraycopy(keys, 0, args, 0, keys.length);
		args[keys.length] = timeout;
		
		this.addCommand(RedisCommand.BRPOP, args);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#brpoplpush(java.lang.String,
	 * java.lang.String, int)
	 */
	@Override
	public String listBlockRightPopLeftPush(String source, String destination, int timeout)
	{
		this.addCommand(RedisCommand.BRPOPLPUSH, source, destination, timeout);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#config_get(java.lang.String)
	 */
	@Override
	public Map<String, String> configGet(String parameter)
	{
		this.addCommand(RedisCommand.CONFIG_GET, parameter);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#config_resetstat()
	 */
	@Override
	public Boolean configResetStat()
	{
		this.addCommand(RedisCommand.CONFIG_RESETSTAT);
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#config_set(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Boolean configSet(String parameter, String value)
	{
		this.addCommand(RedisCommand.CONFIG_SET, parameter, value);
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#dbsize()
	 */
	@Override
	public Integer dbSize()
	{
		this.addCommand(RedisCommand.DBSIZE);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#debug_object(java.lang.String)
	 */
	@Override
	public String debugObject(String key)
	{
		this.addCommand(RedisCommand.DEBUG_OBJECT, key);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#debug_segfault()
	 */
//	@Override
//	public String[] debugSegfault()
//	{
//		this.addCommand(RedisCommand.DEBUG_SEGFAULT);
//		return null;
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#decr(java.lang.String)
	 */
	@Override
	public Long decrement(String key)
	{
		this.addCommand(RedisCommand.DECR, key);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#decrby(java.lang.String, int)
	 */
	@Override
	public Long decrementByValue(String key, int decrement)
	{
		this.addCommand(RedisCommand.DECRBY, key, decrement);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#del(java.lang.String[])
	 */
	@Override
	public Integer del(String... keys)
	{
		this.addCommand(RedisCommand.DEL, (Object[])keys);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#echo(java.lang.String)
	 */
	@Override
	public String echo(String message)
	{
		this.addCommand(RedisCommand.ECHO, message);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#exists(java.lang.String)
	 */
	@Override
	public Boolean exists(String key)
	{
		this.addCommand(RedisCommand.EXISTS, key);
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#expire(java.lang.String, int)
	 */
	@Override
	public Boolean expire(String key, int seconds)
	{
		this.addCommand(RedisCommand.EXPIRE, key, seconds);
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#expireat(java.lang.String, long)
	 */
	@Override
	public Boolean expireAsTimestamp(String key, long timestamp)
	{
		this.addCommand(RedisCommand.EXPIREAT, key, timestamp);
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#flushall()
	 */
	@Override
	public Boolean flushAllDB()
	{
		this.addCommand(RedisCommand.FLUSHALL);
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#flushdb()
	 */
	@Override
	public Boolean flushCurrentDB()
	{
		this.addCommand(RedisCommand.FLUSHDB);
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#get(java.lang.String)
	 */
	@Override
	public String get(String key)
	{
		this.addCommand(RedisCommand.GET, key);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#getbit(java.lang.String, int)
	 */
	@Override
	public Boolean getBit(String key, int offset)
	{
		this.addCommand(RedisCommand.GETBIT, key, offset);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#getrange(java.lang.String, int,
	 * int)
	 */
	@Override
	public String getRange(String key, int start, int end)
	{
		this.addCommand(RedisCommand.GETRANGE, key, start, end);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#getset(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public String getSet(String key, String value)
	{
		this.addCommand(RedisCommand.GETSET, key, value);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#hdel(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Boolean hashesDel(String key, String field)
	{
		this.addCommand(RedisCommand.HDEL, key, field);
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#hexists(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Boolean hashesExists(String key, String field)
	{
		this.addCommand(RedisCommand.HEXISTS, key, field);
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#hget(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public String hashesGet(String key, String field)
	{
		this.addCommand(RedisCommand.HGET, key, field);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#hgetall(java.lang.String)
	 */
	@Override
	public List<String> hashesGetAllValue(String key)
	{
		this.addCommand(RedisCommand.HVALS, key);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#hincrby(java.lang.String,
	 * java.lang.String, int)
	 */
	@Override
	public Integer hashesIncrementByValue(String key, String field, int increment)
	{
		this.addCommand(RedisCommand.HINCRBY, key, field, increment);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#hkeys(java.lang.String)
	 */
	@Override
	public List<String> hashesGetAllField(String key)
	{
		this.addCommand(RedisCommand.HKEYS, key);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#hlen(java.lang.String)
	 */
	@Override
	public Integer hashesLength(String key)
	{
		this.addCommand(RedisCommand.HLEN, key);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#hmget(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<String> hashesMultipleFieldGet(String key, String... fields)
	{
		Object[] args = new Object[fields.length+1];
		args[0] = key;
		System.arraycopy(fields, 0, args, 1, fields.length);
		this.addCommand(RedisCommand.HMGET, args);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#hmset(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public Boolean hashesMultipleSet(String key, Map<String, String> fieldAndValue)
	{
		String[] allKey = ParameterConvert.mapToStringArray(fieldAndValue);
		Object[] args = new Object[allKey.length+1];
		args[0] = key;
		System.arraycopy(allKey, 0, args, 1, allKey.length);
		
		this.addCommand(RedisCommand.HMSET, args);
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#hset(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public Boolean hashesSet(String key, String field, String value)
	{
		this.addCommand(RedisCommand.HSET, key, field, value);
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#hsetnx(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public Boolean hashesSetNotExistField(String key, String field, String value)
	{
		this.addCommand(RedisCommand.HSETNX, key, field, value);
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#hvals(java.lang.String)
	 */
	@Override
	public Map<String, String> hashesGetAll(String key)
	{
		this.addCommand(RedisCommand.HGETALL, key);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#incr(java.lang.String)
	 */
	@Override
	public Long increment(String key)
	{
		this.addCommand(RedisCommand.INCR, key);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#incrby(java.lang.String, int)
	 */
	@Override
	public Long incrementByValue(String key, int increment)
	{
		this.addCommand(RedisCommand.INCRBY, key, increment);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#info()
	 */
	@Override
	public String info()
	{
		this.addCommand(RedisCommand.INFO);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#keys(java.lang.String)
	 */
	@Override
	public List<String> keys(String pattern)
	{
		this.addCommand(RedisCommand.KEYS, pattern);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#lastsave()
	 */
	@Override
	public Long lastSave()
	{
		this.addCommand(RedisCommand.LASTSAVE);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#lindex(java.lang.String, int)
	 */
	@Override
	public String listIndex(String key, int index)
	{
		this.addCommand(RedisCommand.LINDEX, key, index);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#linsert(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Integer listInsert(String key, ListPosition beforeOrAfter, String pivot, String value)
	{
		this.addCommand(RedisCommand.LINSERT, key, beforeOrAfter.toString(), pivot, value);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#llen(java.lang.String)
	 */
	@Override
	public Integer listLength(String key)
	{
		this.addCommand(RedisCommand.LLEN, key);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#lpop(java.lang.String)
	 */
	@Override
	public String listLeftPop(String key)
	{
		this.addCommand(RedisCommand.LPOP, key);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#lpush(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Integer listLeftPush(String key, String value)
	{
		this.addCommand(RedisCommand.LPUSH, key, value);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#lpushx(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Integer listLeftPushOnExist(String key, String value)
	{
		this.addCommand(RedisCommand.LPUSHX, key, value);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#lrange(java.lang.String, int,
	 * int)
	 */
	@Override
	public List<String> listRange(String key, int start, int stop)
	{
		this.addCommand(RedisCommand.LRANGE, key, start, stop);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#lrem(java.lang.String, int,
	 * java.lang.String)
	 */
	@Override
	public Integer listRemove(String key, int count, String value)
	{
		this.addCommand(RedisCommand.LREM, count, value);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#lset(java.lang.String, int,
	 * java.lang.String)
	 */
	@Override
	public Boolean listSet(String key, int index, String value)
	{
		this.addCommand(RedisCommand.LSET, key, index, value);
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#ltrim(java.lang.String, int, int)
	 */
	@Override
	public Boolean listTrim(String key, int start, int stop)
	{
		this.addCommand(RedisCommand.LTRIM, key, start, stop);
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#mget(java.lang.String[])
	 */
	@Override
	public List<String> multipleGet(String... keys)
	{
		this.addCommand(RedisCommand.MGET, (Object[])keys);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#move(java.lang.String, int)
	 */
	@Override
	public Boolean move(String key, int indexDB)
	{
		this.addCommand(RedisCommand.MOVE, key, indexDB);
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#mset(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Boolean multipleSet(Map<String, String> keyAndValue)
	{
		Object[] allKey = ParameterConvert.mapToStringArray(keyAndValue);
		this.addCommand(RedisCommand.MSET, allKey);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#msetnx(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Boolean multipleSetOnNotExist(Map<String, String> keyAndValue)
	{
		Object[] allKey = ParameterConvert.mapToStringArray(keyAndValue);
		this.addCommand(RedisCommand.MSETNX, allKey);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#persist(java.lang.String)
	 */
	@Override
	public Boolean persist(String key)
	{
		this.addCommand(RedisCommand.PERSIST, key);
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#ping()
	 */
	@Override
	public Boolean ping()
	{
		this.addCommand(RedisCommand.PING);
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#randomkey()
	 */
	@Override
	public String randomKey()
	{
		this.addCommand(RedisCommand.RANDOMKEY);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#rename(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Boolean rename(String key, String newKey)
	{
		this.addCommand(RedisCommand.RENAME, key, newKey);
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#renamenx(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Boolean renameOnNotExistNewKey(String key, String newKey)
	{
		this.addCommand(RedisCommand.RENAMENX, key, newKey);
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#rpop(java.lang.String)
	 */
	@Override
	public String listRightPop(String key)
	{
		this.addCommand(RedisCommand.RPOP, key);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#rpoplpush(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public String listRightPopLeftPush(String source, String destination)
	{
		this.addCommand(RedisCommand.RPOPLPUSH, source, destination);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#rpush(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Integer listRightPush(String key, String value)
	{
		this.addCommand(RedisCommand.RPUSH, key, value);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#rpushx(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Integer listRightPushOnExist(String key, String value)
	{
		this.addCommand(RedisCommand.RPUSHX, key, value);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#sadd(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Boolean setsAdd(String key, String member)
	{
		this.addCommand(RedisCommand.SADD, key, member);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#save()
	 */
	@Override
	public Boolean save()
	{
		this.addCommand(RedisCommand.SAVE);
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#scard(java.lang.String)
	 */
	@Override
	public Integer setsCard(String key)
	{
		this.addCommand(RedisCommand.SCARD, key);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#sdiff(java.lang.String[])
	 */
	@Override
	public List<String> setsDiff(String... keys)
	{
		this.addCommand(RedisCommand.SDIFF, (Object[])keys);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#sdiffstore(java.lang.String,
	 * java.lang.String[])
	 */
	@Override
	public Integer setsDiffStore(String destination, String... keys)
	{
		Object[] args = new Object[keys.length+1];
		args[0] = destination;
		System.arraycopy(keys, 0, args, 1, keys.length);
		
		this.addCommand(RedisCommand.SDIFFSTORE, args);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#set(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Boolean set(String key, String value)
	{
		//RedisResponse res = this.connector.executeCommand(RedisCommand.SET, key, value);
		//System.err.println("--------------" + res.getTextValue());
		this.addCommand(RedisCommand.SET, key, value);
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#setbit(java.lang.String, int,
	 * int)
	 */
	@Override
	public Boolean setBit(String key, int offset, boolean value)
	{
		this.addCommand(RedisCommand.SETBIT, key, offset, value ? 1: 0);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#setex(java.lang.String, int,
	 * java.lang.String)
	 */
	@Override
	public Boolean setAndExpire(String key, int seconds, String value)
	{
		this.addCommand(RedisCommand.SETEX, key, seconds, value);
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#setnx(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Boolean setOnNotExist(String key, String value)
	{
		this.addCommand(RedisCommand.SETNX, key, value);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#setrange(java.lang.String, int,
	 * java.lang.String)
	 */
	@Override
	public Integer setRange(String key, int offset, String value)
	{
		this.addCommand(RedisCommand.SETRANGE, key, offset, value);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#shutdown()
	 */
	@Override
	public Boolean shutdownServer()
	{
		this.addCommand(RedisCommand.SHUTDOWN);
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#sinter(java.lang.String[])
	 */
	@Override
	public List<String> setsInter(String... keys)
	{
		this.addCommand(RedisCommand.SINTER, (Object[])keys);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#sinterstore(java.lang.String,
	 * java.lang.String[])
	 */
	@Override
	public Integer setsInterStore(String destination, String... keys)
	{
		Object[] args = new Object[keys.length+1];
		args[0] = destination;
		System.arraycopy(keys, 0, args, 1, keys.length);
		
		this.addCommand(RedisCommand.SINTERSTORE, args);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#sismember(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Boolean setsIsMember(String key, String member)
	{
		this.addCommand(RedisCommand.SISMEMBER, key, member);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#slaveof()
	 */
	@Override
	public Boolean slaveOf()
	{
		this.addCommand(RedisCommand.SLAVEOF);
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#smembers(java.lang.String)
	 */
	@Override
	public List<String> setsMembers(String key)
	{
		this.addCommand(RedisCommand.SMEMBERS, key);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#smove(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public Boolean setsMove(String source, String destination, String member)
	{
		this.addCommand(RedisCommand.SMOVE, source, destination, member);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#sort(java.lang.String,
	 * java.lang.String[])
	 */
	@Override
	public List<String> sort(String key, String... params)
	{
		Object[] args = new Object[params.length+1];
		args[0] = key;
		System.arraycopy(params, 0, args, 1, params.length);
		
		this.addCommand(RedisCommand.SORT, args);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#spop(java.lang.String)
	 */
	@Override
	public String setsPop(String key)
	{
		this.addCommand(RedisCommand.SPOP, key);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#srandmember(java.lang.String)
	 */
	@Override
	public String setsRandMember(String key)
	{
		this.addCommand(RedisCommand.SRANDMEMBER, key);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#srem(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Boolean setsRemove(String key, String member)
	{
		this.addCommand(RedisCommand.SREM, key, member);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#strlen(java.lang.String)
	 */
	@Override
	public Integer strLength(String key)
	{
		this.addCommand(RedisCommand.STRLEN, key);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#sunion(java.lang.String[])
	 */
	@Override
	public List<String> setsUnion(String... keys)
	{
		this.addCommand(RedisCommand.SUNION, (Object[])keys);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#sunionstore(java.lang.String,
	 * java.lang.String[])
	 */
	@Override
	public Integer setsUnionStore(String destination, String... keys)
	{
		Object[] args = new Object[keys.length+1];
		args[0] = destination;
		System.arraycopy(keys, 0, args, 1, keys.length);
		
		this.addCommand(RedisCommand.SUNIONSTORE, args);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#sync()
	 */
	@Override
	public String sync()
	{
		this.addCommand(RedisCommand.SYNC);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#ttl(java.lang.String)
	 */
	@Override
	public Integer timeToLive(String key)
	{
		this.addCommand(RedisCommand.TTL, key);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#type(java.lang.String)
	 */
	@Override
	public String type(String key)
	{
		this.addCommand(RedisCommand.TYPE, key);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#zadd(java.lang.String, int,
	 * java.lang.String)
	 */
	@Override
	public Boolean sortedSetsAdd(String key, int score, String member)
	{
		this.addCommand(RedisCommand.ZADD, key, score, member);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#zcard(java.lang.String)
	 */
	@Override
	public Integer sortedSetsCard(String key)
	{
		this.addCommand(RedisCommand.ZCARD, key);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#zcount(java.lang.String, int,
	 * int)
	 */
	@Override
	public Integer sortedSetsCount(String key, int min, int max)
	{
		this.addCommand(RedisCommand.ZCOUNT, key, min, max);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#zincrby(java.lang.String, int,
	 * java.lang.String)
	 */
	@Override
	public Double sortedSetsIncrementByValue(String key, int increment, String member)
	{
		this.addCommand(RedisCommand.ZINCRBY, key, increment, member);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#zinterstore(java.lang.String[])
	 */
	@Override
	public Integer sortedSetsInterStore(String destination, String... keys)
	{
		Object[] args = new Object[keys.length + 2];
		args[0] = destination;
		args[1] = keys.length;
		System.arraycopy(keys, 0, args, 2, keys.length);
		this.addCommand(RedisCommand.ZINTERSTORE, args);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#zrange(java.lang.String, int,
	 * int)
	 */
	@Override
	public List<String> sortedSetsRange(String key, int start, int stop)
	{
		this.addCommand(RedisCommand.ZRANGE, key, start, stop);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#zrangebyscore(java.lang.String[])
	 */
	@Override
	public List<String> sortedSetsRangeByScore(String key, int min, int max)
	{
		this.addCommand(RedisCommand.ZRANGEBYSCORE, key, min, max);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#zrank(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Integer sortedSetsRank(String key, String member)
	{
		this.addCommand(RedisCommand.ZRANK, key, member);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#zrem(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Boolean sortedSetsRemove(String key, String member)
	{
		this.addCommand(RedisCommand.ZREM, key, member);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#zremrangebyrank(java.lang.String,
	 * int, int)
	 */
	@Override
	public Integer sortedSetsRemoveRangeByRank(String key, int start, int stop)
	{
		this.addCommand(RedisCommand.ZREMRANGEBYRANK, key, start, stop);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.elk.redis4j.api.IDatabase#zremrangebyscore(java.lang.String,
	 * int, int)
	 */
	@Override
	public Integer sortedSetsRemoveRangeByScore(String key, int min, int max)
	{
		this.addCommand(RedisCommand.ZREMRANGEBYSCORE, key, min, max);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#zrevrange(java.lang.String, int,
	 * int)
	 */
	@Override
	public List<String> sortedSetsRevRange(String key, int start, int stop)
	{
		this.addCommand(RedisCommand.ZREVRANGE, key, start, stop);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.elk.redis4j.api.IDatabase#zrevrangebyscore(java.lang.String[])
	 */
	@Override
	public List<String> sortedSetsRevRangeByScore(String key, int max, int min)
	{
		this.addCommand(RedisCommand.ZREVRANGEBYSCORE, key, max, min);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#zrevrank(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Integer sortedSetsRevRank(String key, String member)
	{
		this.addCommand(RedisCommand.ZREVRANK, key, member);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#zscore(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Integer sortedSetsScore(String key, String member)
	{
		this.addCommand(RedisCommand.ZSCORE, key, member);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.elk.redis4j.api.IDatabase#zunionstore(java.lang.String[])
	 */
	@Override
	public Integer sortedSetsUnionStore(String destination, String... keys)
	{
		Object[] args = new Object[keys.length + 2];
		args[0] = destination;
		args[1] = keys.length;
		System.arraycopy(keys, 0, args, 2, keys.length);
		this.addCommand(RedisCommand.ZUNIONSTORE, args);
		return null;
	}
}
