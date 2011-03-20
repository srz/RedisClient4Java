package com.handinfo.redis4j.api;

public interface IHashes
{

	public Boolean hdel(String key, String field) throws Exception;

	public String[] hgetall(String key) throws Exception;

	public int hlen(String key) throws Exception;

	public Boolean hset(String key, String field, String value) throws Exception;

	public Boolean hexists(String key, String field) throws Exception;

	public int hincrby(String key, String field, int increment) throws Exception;

	public String[] hmget(String key, String field) throws Exception;

	public Boolean hsetnx(String key, String field, String value) throws Exception;

	public String hget(String key, String field) throws Exception;

	public String[] hkeys(String key) throws Exception;

	public boolean hmset(String key, String field, String value) throws Exception;

	public String[] hvals(String key) throws Exception;

}