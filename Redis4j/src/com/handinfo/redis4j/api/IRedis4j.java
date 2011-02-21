package com.handinfo.redis4j.api;

import java.io.UnsupportedEncodingException;


public interface IRedis4j
{
	//public IRedis4j getInstance(String ip, int port);
	public boolean connect();
	public boolean ping();
	public boolean auth(String password);
	public boolean select(int dbIndex);
	public String echo(String message);
	public <T> Object get(String key, Class<T> valueType) throws UnsupportedEncodingException, InstantiationException, IllegalAccessException;
	public boolean quit();
	public<T> boolean set(String key, T value);
}
