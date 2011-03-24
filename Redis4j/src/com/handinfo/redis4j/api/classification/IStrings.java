package com.handinfo.redis4j.api.classification;

public interface IStrings
{

	public int append(String key, String value);

	public String getrange(String key, int start, int end);

	public Boolean mset(String key, String value);

	public Boolean setnx(String key, String value);

	public int decr(String key);

	public String getset(String key, String value);

	public Boolean msetnx(String key, String value);

	public int setrange(String key, int offset, String value);

	public int decrby(String key, int decrement);

	public int incr(String key);

	public boolean set(String key, String value);

	public int strlen(String key);

	public String get(String key);

	public int incrby(String key, int increment);

	public int setbit(String key, int offset, int value);

	public int getbit(String key, int offset);

	public String[] mget(String... keys);

	public boolean setex(String key, int seconds, String value);

}