package com.handinfo.redis4j.api.cache;

import java.util.List;

import com.handinfo.redis4j.api.ISession;
import com.handinfo.redis4j.api.RedisCommand;
import com.handinfo.redis4j.api.RedisResponse;

public interface ICacheConnector
{
	public void initSession();
	
	public int getNumberOfConnected();
	
	public ISession getSessionByKey(String key);

	public RedisResponse executeCommand(RedisCommand command, String key, Object... args);
	
	public RedisResponse executeMultiKeysNoArgsAndMultiReplay(RedisCommand command, String... keys);
	
	public List<RedisResponse> executeMultiKeysNoArgsAndSingleReplay(RedisCommand command, String... keys);

	public void disConnect();
}