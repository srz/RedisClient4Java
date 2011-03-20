package com.handinfo.redis4j.api;

public interface IServer
{

	public String bgrewriteaof() throws Exception;

	public String bgsave() throws Exception;

	public String[] config_get(String parameter) throws Exception;

	public boolean config_resetstat() throws Exception;

	public boolean config_set(String parameter, String value) throws Exception;

	public int dbsize() throws Exception;

	public String[] debug_object(String key) throws Exception;

	public String[] debug_segfault() throws Exception;

	public boolean flushall() throws Exception;

	public boolean flushdb() throws Exception;

	public String info() throws Exception;

	public int lastsave() throws Exception;

	public void monitor() throws Exception;
	
	public String getMonitorResult() throws Exception;

	public boolean save() throws Exception;

	public boolean shutdown() throws Exception;

	public boolean slaveof() throws Exception;

	public String[] sync() throws Exception;

}