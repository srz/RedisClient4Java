package com.handinfo.redis4j.api;

public interface ISortedSets
{

	public Boolean zadd(String key, int score, String member) throws Exception;

	public int zinterstore(String... args) throws Exception;

	public Boolean zrem(String key, String member) throws Exception;

	public String[] zrevrangebyscore(String... args) throws Exception;

	public int zcard(String key) throws Exception;

	public String[] zrange(String key, int start, int stop) throws Exception;

	public int zremrangebyrank(String key, int start, int stop) throws Exception;

	public int zrevrank(String key, String member) throws Exception;

	public int zcount(String key, int min, int max) throws Exception;

	public String[] zrangebyscore(String... args) throws Exception;

	public int zremrangebyscore(String key, int min, int max) throws Exception;

	public String zscore(String key, String member) throws Exception;

	public String zincrby(String key, int increment, String member) throws Exception;

	public int zrank(String key, String member) throws Exception;

	public String[] zrevrange(String key, int start, int stop) throws Exception;

	public int zunionstore(String... args) throws Exception;

}