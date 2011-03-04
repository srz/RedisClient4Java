package com.handinfo.redis4j.api;

public interface ISets
{

	public Boolean sadd(String key, String member);

	public String[] sinter(String... keys);

	public Boolean smove(String source, String destination, String member);

	public String[] sunion(String... keys);

	public int scard(String key);

	public int sinterstore(String destination, String... keys);

	public String spop(String key);

	public int sunionstore(String destination, String... keys);

	public String[] sdiff(String... keys);

	public Boolean sismember(String key, String member);

	public String srandmember(String key);

	public int sdiffstore(String destination, String... keys);

	public String[] smembers(String key);

	public Boolean srem(String key, String member);

}