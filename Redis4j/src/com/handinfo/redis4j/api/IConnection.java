package com.handinfo.redis4j.api;

public interface IConnection
{

	public boolean auth(String password);

	public String echo(String message);

	public boolean ping();

	//
	public boolean quit();

}