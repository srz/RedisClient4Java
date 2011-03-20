package com.handinfo.redis4j.api;

public interface IStrings
{

	public int append(String key, String value) throws Exception;

	public String getrange(String key, int start, int end) throws Exception;

	public Boolean mset(String key, String value) throws Exception;

	public Boolean setnx(String key, String value) throws Exception;

	public int decr(String key) throws Exception;

	public String getset(String key, String value) throws Exception;

	public Boolean msetnx(String key, String value) throws Exception;

	public int setrange(String key, int offset, String value) throws Exception;

	public int decrby(String key, int decrement) throws Exception;

	public int incr(String key) throws Exception;

	public boolean set(String key, String value) throws Exception;

	public int strlen(String key) throws Exception;

	public String get(String key) throws Exception;

	public int incrby(String key, int increment) throws Exception;

	public int setbit(String key, int offset, int value) throws Exception;

	public int getbit(String key, int offset) throws Exception;

	public String[] mget(String... keys) throws Exception;

	public boolean setex(String key, int seconds, String value) throws Exception;

}