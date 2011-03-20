package com.handinfo.redis4j.api;

public interface ISets
{

	public Boolean sadd(String key, String member) throws Exception;

	public String[] sinter(String... keys) throws Exception;

	public Boolean smove(String source, String destination, String member) throws Exception;

	public String[] sunion(String... keys) throws Exception;

	public int scard(String key) throws Exception;

	public int sinterstore(String destination, String... keys) throws Exception;

	public String spop(String key) throws Exception;

	public int sunionstore(String destination, String... keys) throws Exception;

	public String[] sdiff(String... keys) throws Exception;

	public Boolean sismember(String key, String member) throws Exception;

	public String srandmember(String key) throws Exception;

	public int sdiffstore(String destination, String... keys) throws Exception;

	public String[] smembers(String key) throws Exception;

	public Boolean srem(String key, String member) throws Exception;

}