package com.handinfo.redis4j.api.async;

import java.util.concurrent.BrokenBarrierException;

import com.handinfo.redis4j.api.RedisCommand;
import com.handinfo.redis4j.api.exception.CleanLockedThreadException;
import com.handinfo.redis4j.api.exception.ErrorCommandException;

public interface IAsyncConnector
{
	public boolean isConnected();
	
	public void connect();

	public void executeAsyncCommand(IRedisAsyncClient.Result result, RedisCommand command, Object... args) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException, InterruptedException, BrokenBarrierException;
	
	public void disConnect();
}