package com.handinfo.redis4j.api;

public class RedisCommandType
{
	public static final String AUTH = "AUTH";
	public static final String PING = "PING";
	public static final String SELECT = "SELECT";
	public static final String ECHO = "ECHO";
	public static final String QUIT = "QUIT";
	
	public static final String SET = "SET";
	public static final String GET = "GET";
	
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
	
	public static final String FLUSHDB = "FLUSHDB";
	public static final String FLUSHALL = "FLUSHALL";
}
