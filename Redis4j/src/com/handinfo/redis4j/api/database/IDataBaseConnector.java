package com.handinfo.redis4j.api.database;

import java.util.ArrayList;
import java.util.List;

import com.handinfo.redis4j.api.RedisCommand;
import com.handinfo.redis4j.api.RedisResponse;
import com.handinfo.redis4j.api.exception.CleanLockedThreadException;
import com.handinfo.redis4j.api.exception.ErrorCommandException;

public interface IDataBaseConnector
{
	public boolean isConnected();
	
	public void connect();

	public RedisResponse executeCommand(RedisCommand command, Object... args) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException;

	//public void executeAsyncCommand(IRedis4jAsync.Notify notify, RedisCommand command, Object... args) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException, InterruptedException, BrokenBarrierException;
	
	public List<RedisResponse> executeBatch(ArrayList<Object[]> commandList) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException;
	
	public void disConnect();
}