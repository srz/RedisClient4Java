package com.handinfo.redis4j.api;

public interface IConnection
{

	public boolean auth(String password) throws Exception;

	public String echo(String message) throws Exception;

	public boolean ping() throws Exception;

	//
	public boolean quit();

}