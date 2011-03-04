package com.handinfo.redis4j.api;

public interface ILists
{

	public String[] blpop(String key, int timeout);

	public int llen(String key);

	public int lrem(String key, int count, String value);

	public int rpush(String key, String value);

	public String[] brpop(String key, int timeout);

	public String lpop(String key);

	public boolean lset(String key, int index, String value);

	public int rpushx(String key, String value);

	public String brpoplpush(String source, String destination, int timeout);

	public int lpush(String key, String value);

	public boolean ltrim(String key, int start, int stop);

	public String lindex(String key, int index);

	public int lpushx(String key, String value);

	public String rpop(String key);

	public int linsert(String key, String BEFORE_AFTER, String pivot, String value);

	public String[] lrange(String key, int start, int stop);

	public String rpoplpush(String source, String destination);

}