package com.handinfo.redis4j.api;

import java.util.concurrent.BrokenBarrierException;

import com.handinfo.redis4j.api.exception.CleanLockedThreadException;
import com.handinfo.redis4j.api.exception.ErrorCommandException;

public interface IRedis4jAsync
{

	public interface Notify
	{
		public void onNotify(String result);
	}
	
	public void executeCommand(RedisCommand command, Notify notify) throws CleanLockedThreadException, ErrorCommandException, IllegalStateException, InterruptedException, BrokenBarrierException;

	public void quit();

}