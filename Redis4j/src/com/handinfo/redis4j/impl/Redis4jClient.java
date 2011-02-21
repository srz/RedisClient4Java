package com.handinfo.redis4j.impl;

import com.handinfo.redis4j.api.IRedis4j;
import com.handinfo.redis4j.api.RedisCommandType;
import com.handinfo.redis4j.api.RedisResultType;
import com.handinfo.redis4j.impl.transfers.Connector;

public class Redis4jClient implements IRedis4j
{
	private String host;
	private int port;
	private Connector connector;

	public Redis4jClient(String host, int port)
	{
		this.host = host;
		this.port = port;
	}

	@Override
	public boolean auth(String password)
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String echo(String message)
	{
		Object result = connector.executeCommand(RedisCommandType.ECHO, message);
		if (result instanceof String)
		{
			return (String) result;
		}
		return null;
	}

	@Override
	public boolean ping()
	{
		Object result = connector.executeCommand(RedisCommandType.PING);
		if (result instanceof String)
		{
			if (((String) result).equalsIgnoreCase(RedisResultType.PONG))
			{
				return true;
			}
		}
		return false;
	}

	//
	@Override
	public boolean quit()
	{
		return connector.disconnect();
	}

	@Override
	public boolean select(int dbIndex)
	{
		// TODO Auto-generated method stub
		return true;
	}

	/*
	 * 创建到Redis服务器的连接，需要调用quit()函数关闭此连接
	 */
	@Override
	public boolean connect()
	{
		connector = new Connector();

		return connector.connect(host, port);
	}

	@Override
	public String get(String key)
	{
		Object result = connector.executeCommand(RedisCommandType.GET, key);
		if (result instanceof String)
		{
			return (String) result;
		}
		return null;
	}

}
