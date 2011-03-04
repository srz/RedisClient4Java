package com.handinfo.redis4j.api;

public interface IServer
{

	public boolean bgrewriteaof();

	public boolean bgsave();

	public String[] config_get(String parameter);

	public boolean config_resetstat();

	public boolean config_set(String parameter, String value);

	public int dbsize();

	public String[] debug_object(String key);

	public String[] debug_segfault();

	public boolean flushall();

	public boolean flushdb();

	public String[] info();

	public int lastsave();

	public String monitor();

	public boolean save();

	public boolean shutdown();

	public boolean slaveof();

	public String[] sync();

}