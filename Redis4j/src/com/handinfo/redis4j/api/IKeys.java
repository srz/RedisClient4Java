package com.handinfo.redis4j.api;

public interface IKeys
{

	public int del(String... keys);

	public String[] keys(String key);

	public boolean rename(String key, String newKey);

	public String type(String key);

	public boolean exists(String key);

	public boolean move(String key, int indexDB);

	public boolean renamenx(String key, String newKey);

	public boolean expire(String key, int seconds);

	public boolean persist(String key);

	//TODO 暂时先写个简单版本的,后面在追加重载版本
	public Object[] sort(String key, String... args);

	public boolean expireat(String key, long timestamp);

	public String randomkey();

	public int ttl(String key);

}