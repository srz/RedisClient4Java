package com.handinfo.redis4j.api.cache;

import com.handinfo.redis4j.api.ISession;
import com.handinfo.redis4j.api.RedisCommand;
import com.handinfo.redis4j.api.RedisResponse;
import com.handinfo.redis4j.api.exception.CleanLockedThreadException;
import com.handinfo.redis4j.api.exception.ErrorCommandException;

public interface ICacheConnector
{
	public void initSession();
	
	public int getNumberOfConnected();
	
	public ISession getSessionByKey(String key);

	public RedisResponse executeCommand(RedisCommand command, String key, Object... args) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException;
	
	public RedisResponse executeCommand(RedisCommand command, String... keys) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException;

	public void disConnect();
}