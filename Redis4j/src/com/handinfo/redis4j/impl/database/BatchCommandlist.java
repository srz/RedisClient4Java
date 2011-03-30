package com.handinfo.redis4j.impl.database;

import java.util.ArrayList;
import java.util.HashMap;

import com.handinfo.redis4j.api.IConnector;
import com.handinfo.redis4j.api.IDatabase;
import com.handinfo.redis4j.api.RedisCommand;

public abstract class BatchCommandlist implements IDatabase
{
	protected ArrayList<String[]> commandList;
	protected IConnector connector;

	public BatchCommandlist(IConnector connector)
	{
		this.connector = connector;
		this.commandList = new ArrayList<String[]>();
	}

	protected void addCommand(RedisCommand command, String... args)
	{
		String[] cmd = new String[command.getValue().length + args.length];
		System.arraycopy(command.getValue(), 0, cmd, 0, command.getValue().length);
		System.arraycopy(args, 0, cmd, command.getValue().length, args.length);
		this.commandList.add(cmd);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#append(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public int append(String key, String value)
	{
		this.addCommand(RedisCommand.APPEND, key, value);
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#bgrewriteaof()
	 */
	@Override
	public String backgroundRewriteAOF()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#bgsave()
	 */
	@Override
	public String backgroundSave()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#blpop(java.lang.String, int)
	 */
	@Override
	public String[] listBlockLeftPop(int timeout, String... keys)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#brpop(java.lang.String, int)
	 */
	@Override
	public String[] listBlockRightPop(int timeout, String... keys)
	{
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#config_get(java.lang.String)
	 */
	@Override
	public String[] configGet(String parameter)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#config_resetstat()
	 */
	@Override
	public boolean configResetStat()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#config_set(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean configSet(String parameter, String value)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#dbsize()
	 */
	@Override
	public int dbSize()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#debug_object(java.lang.String)
	 */
	@Override
	public String[] debugObject(String key)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#debug_segfault()
	 */
	@Override
	public String[] debugSegfault()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#decr(java.lang.String)
	 */
	@Override
	public int decrement(String key)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#decrby(java.lang.String, int)
	 */
	@Override
	public int decrementByValue(String key, int decrement)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#del(java.lang.String[])
	 */
	@Override
	public int del(String... keys)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#echo(java.lang.String)
	 */
	@Override
	public String echo(String message)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#exists(java.lang.String)
	 */
	@Override
	public boolean exists(String key)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#expire(java.lang.String, int)
	 */
	@Override
	public boolean expire(String key, int seconds)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#expireat(java.lang.String, long)
	 */
	@Override
	public boolean expireAsTimestamp(String key, long timestamp)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#flushall()
	 */
	@Override
	public boolean flushAllDB()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#flushdb()
	 */
	@Override
	public boolean flushCurrentDB()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#get(java.lang.String)
	 */
	@Override
	public String get(String key)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#getbit(java.lang.String, int)
	 */
	@Override
	public int getBit(String key, int offset)
	{
		// TODO Auto-generated method stub
		return 0;
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
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#hdel(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean hashesDel(String key, String field)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#hexists(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean hashesExists(String key, String field)
	{
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#hgetall(java.lang.String)
	 */
	@Override
	public String[] hashesGetAllValue(String key)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#hincrby(java.lang.String,
	 * java.lang.String, int)
	 */
	@Override
	public int hashesIncrementByValue(String key, String field, int increment)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#hkeys(java.lang.String)
	 */
	@Override
	public String[] hashesGetAllField(String key)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#hlen(java.lang.String)
	 */
	@Override
	public int hashesLength(String key)
	{
		// TODO Auto-generated method stub
		return 0;
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
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#hmset(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public boolean hashesMultipleSet(String key, HashMap<String, String> fieldAndValue)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#hset(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public boolean hashesSet(String key, String field, String value)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#hsetnx(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public boolean hashesSetNotExistField(String key, String field, String value)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#hvals(java.lang.String)
	 */
	@Override
	public HashMap<String, String> hashesGetAll(String key)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#incr(java.lang.String)
	 */
	@Override
	public int increment(String key)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#incrby(java.lang.String, int)
	 */
	@Override
	public int incrementByValue(String key, int increment)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#info()
	 */
	@Override
	public String info()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#keys(java.lang.String)
	 */
	@Override
	public String[] keys(String pattern)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#lastsave()
	 */
	@Override
	public int lastSave()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#lindex(java.lang.String, int)
	 */
	@Override
	public String listIndex(String key, int index)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#linsert(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public int listLeftInsert(String key, String BEFORE_AFTER, String pivot, String value)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#llen(java.lang.String)
	 */
	@Override
	public int listLength(String key)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#lpop(java.lang.String)
	 */
	@Override
	public String listLeftPop(String key)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#lpush(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public int listLeftPush(String key, String value)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#lpushx(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public int listLeftPushOnExist(String key, String value)
	{
		// TODO Auto-generated method stub
		return 0;
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
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#lrem(java.lang.String, int,
	 * java.lang.String)
	 */
	@Override
	public int listRemove(String key, int count, String value)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#lset(java.lang.String, int,
	 * java.lang.String)
	 */
	@Override
	public boolean listSet(String key, int index, String value)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#ltrim(java.lang.String, int, int)
	 */
	@Override
	public boolean listTrim(String key, int start, int stop)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#mget(java.lang.String[])
	 */
	@Override
	public String[] multipleGet(String... keys)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#move(java.lang.String, int)
	 */
	@Override
	public boolean move(String key, int indexDB)
	{
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#persist(java.lang.String)
	 */
	@Override
	public boolean persist(String key)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#ping()
	 */
	@Override
	public boolean ping()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#randomkey()
	 */
	@Override
	public String randomKey()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#rename(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean rename(String key, String newKey)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#renamenx(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean renameOnNotExistNewKey(String key, String newKey)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#rpop(java.lang.String)
	 */
	@Override
	public String listRightPop(String key)
	{
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#rpush(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public int listRightPush(String key, String value)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#rpushx(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public int listRightPushOnExist(String key, String value)
	{
		// TODO Auto-generated method stub
		return 0;
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
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#save()
	 */
	@Override
	public boolean save()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#scard(java.lang.String)
	 */
	@Override
	public int setsCard(String key)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#sdiff(java.lang.String[])
	 */
	@Override
	public String[] setsDiff(String... keys)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#sdiffstore(java.lang.String,
	 * java.lang.String[])
	 */
	@Override
	public int setsDiffStore(String destination, String... keys)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#set(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean set(String key, String value)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#setbit(java.lang.String, int,
	 * int)
	 */
	@Override
	public int setBit(String key, int offset, int value)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#setex(java.lang.String, int,
	 * java.lang.String)
	 */
	@Override
	public boolean setAndExpire(String key, int seconds, String value)
	{
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#setrange(java.lang.String, int,
	 * java.lang.String)
	 */
	@Override
	public int setRange(String key, int offset, String value)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#shutdown()
	 */
	@Override
	public boolean shutdown()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#sinter(java.lang.String[])
	 */
	@Override
	public String[] setsInter(String... keys)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#sinterstore(java.lang.String,
	 * java.lang.String[])
	 */
	@Override
	public int setsInterStore(String destination, String... keys)
	{
		// TODO Auto-generated method stub
		return 0;
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
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#slaveof()
	 */
	@Override
	public boolean slaveOf()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#smembers(java.lang.String)
	 */
	@Override
	public String[] setsMembers(String key)
	{
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#sort(java.lang.String,
	 * java.lang.String[])
	 */
	@Override
	public Object[] sort(String key, String... args)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#spop(java.lang.String)
	 */
	@Override
	public String setsPop(String key)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#srandmember(java.lang.String)
	 */
	@Override
	public String setsRandMember(String key)
	{
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#strlen(java.lang.String)
	 */
	@Override
	public int strLength(String key)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#sunion(java.lang.String[])
	 */
	@Override
	public String[] setsUnion(String... keys)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#sunionstore(java.lang.String,
	 * java.lang.String[])
	 */
	@Override
	public int setsUnionStore(String destination, String... keys)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#sync()
	 */
	@Override
	public String[] sync()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#ttl(java.lang.String)
	 */
	@Override
	public int timeToLive(String key)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#type(java.lang.String)
	 */
	@Override
	public String type(String key)
	{
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#zcard(java.lang.String)
	 */
	@Override
	public int sortedSetsCard(String key)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#zcount(java.lang.String, int,
	 * int)
	 */
	@Override
	public int sortedSetsCount(String key, int min, int max)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#zincrby(java.lang.String, int,
	 * java.lang.String)
	 */
	@Override
	public String sortedSetsIncrementByValue(String key, int increment, String member)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#zinterstore(java.lang.String[])
	 */
	@Override
	public int sortedSetsInterStore(String... args)
	{
		// TODO Auto-generated method stub
		return 0;
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
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#zrangebyscore(java.lang.String[])
	 */
	@Override
	public String[] sortedSetsRangeByScore(String... args)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#zrank(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public int sortedSetsRank(String key, String member)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#zrem(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Boolean sortedSetsRem(String key, String member)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#zremrangebyrank(java.lang.String,
	 * int, int)
	 */
	@Override
	public int sortedSetsRemoveRangeByRank(String key, int start, int stop)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.handinfo.redis4j.api.IDatabase#zremrangebyscore(java.lang.String,
	 * int, int)
	 */
	@Override
	public int sortedSetsRemoveRangeByScore(String key, int min, int max)
	{
		// TODO Auto-generated method stub
		return 0;
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
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.handinfo.redis4j.api.IDatabase#zrevrangebyscore(java.lang.String[])
	 */
	@Override
	public String[] sortedSetsRevRangeByScore(String... args)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#zrevrank(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public int sortedSetsRevRank(String key, String member)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#zscore(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public String sortedSetsScore(String key, String member)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IDatabase#zunionstore(java.lang.String[])
	 */
	@Override
	public int sortedSetsUnionStore(String... args)
	{
		// TODO Auto-generated method stub
		return 0;
	}
}
