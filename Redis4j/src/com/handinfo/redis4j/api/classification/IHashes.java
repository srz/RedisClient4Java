package com.handinfo.redis4j.api.classification;

public interface IHashes
{

	public Boolean hdel(String key, String field);

	public String[] hgetall(String key);

	public int hlen(String key);

	public Boolean hset(String key, String field, String value);

	public Boolean hexists(String key, String field);

	public int hincrby(String key, String field, int increment);

	public String[] hmget(String key, String field);

	public Boolean hsetnx(String key, String field, String value);

	public String hget(String key, String field);

	public String[] hkeys(String key);

	public boolean hmset(String key, String field, String value);

	public String[] hvals(String key);

}