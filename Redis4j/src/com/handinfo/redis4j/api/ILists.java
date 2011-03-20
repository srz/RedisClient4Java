package com.handinfo.redis4j.api;

public interface ILists
{

	public String[] blpop(String key, int timeout) throws Exception;

	public int llen(String key) throws Exception;

	public int lrem(String key, int count, String value) throws Exception;

	public int rpush(String key, String value) throws Exception;

	public String[] brpop(String key, int timeout) throws Exception;

	public String lpop(String key) throws Exception;

	public boolean lset(String key, int index, String value) throws Exception;

	public int rpushx(String key, String value) throws Exception;

	public String brpoplpush(String source, String destination, int timeout) throws Exception;

	public int lpush(String key, String value) throws Exception;

	public boolean ltrim(String key, int start, int stop) throws Exception;

	public String lindex(String key, int index) throws Exception;

	public int lpushx(String key, String value) throws Exception;

	public String rpop(String key) throws Exception;

	public int linsert(String key, String BEFORE_AFTER, String pivot, String value) throws Exception;

	public String[] lrange(String key, int start, int stop) throws Exception;

	public String rpoplpush(String source, String destination) throws Exception;

}