package com.handinfo.redis4j.api.async;

import com.handinfo.redis4j.api.RedisCommand;

public interface IAsyncConnector
{
	public boolean isConnected();
	
	public void connect();

	public void executeAsyncCommand(IRedisAsyncClient.Result result, RedisCommand command, Object... args) throws InterruptedException;
	
	public void disConnect();
}