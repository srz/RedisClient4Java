package com.handinfo.redis4j.api.classification;

public interface IServer
{

	public String bgrewriteaof();

	public String bgsave();

	public String[] config_get(String parameter);

	public boolean config_resetstat();

	public boolean config_set(String parameter, String value);

	public int dbsize();

	public String[] debug_object(String key);

	public String[] debug_segfault();

	public boolean flushall();

	public boolean flushdb();

	public String info();

	public int lastsave();

	public boolean save();

	public boolean shutdown();

	public boolean slaveof();

	public String[] sync();

}