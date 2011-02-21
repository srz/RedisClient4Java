package com.handinfo.redis4j.api;

public interface IRedis4j
{
	//public IRedis4j getInstance(String ip, int port);
	public boolean connect();
	public boolean ping();
	public boolean auth(String password);
	public boolean select(int dbIndex);
	public String echo(String message);
	public String get(String key);
	public boolean quit();
}
