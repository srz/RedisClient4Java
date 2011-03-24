package com.handinfo.redis4j.api.classification;

public interface ISortedSets
{

	public Boolean zadd(String key, int score, String member);

	public int zinterstore(String... args);

	public Boolean zrem(String key, String member);

	public String[] zrevrangebyscore(String... args);

	public int zcard(String key);

	public String[] zrange(String key, int start, int stop);

	public int zremrangebyrank(String key, int start, int stop);

	public int zrevrank(String key, String member);

	public int zcount(String key, int min, int max);

	public String[] zrangebyscore(String... args);

	public int zremrangebyscore(String key, int min, int max);

	public String zscore(String key, String member);

	public String zincrby(String key, int increment, String member);

	public int zrank(String key, String member);

	public String[] zrevrange(String key, int start, int stop);

	public int zunionstore(String... args);

}