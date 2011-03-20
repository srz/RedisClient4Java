package com.handinfo.redis4j.api;

public interface IKeys
{

	public int del(String... keys) throws Exception;

	public String[] keys(String key) throws Exception;

	public boolean rename(String key, String newKey) throws Exception;

	public String type(String key) throws Exception;

	public boolean exists(String key) throws Exception;

	public boolean move(String key, int indexDB) throws Exception;

	public boolean renamenx(String key, String newKey) throws Exception;

	public boolean expire(String key, int seconds) throws Exception;

	public boolean persist(String key) throws Exception;

	//TODO 暂时先写个简单版本的,后面在追加重载版本
	public Object[] sort(String key, String... args) throws Exception;

	public boolean expireat(String key, long timestamp) throws Exception;

	public String randomkey() throws Exception;

	public int ttl(String key) throws Exception;

}