package org.elk.redis4j.api.cache;

import java.util.List;
import java.util.Map;

import org.elk.redis4j.api.ISession;
import org.elk.redis4j.api.RedisCommand;
import org.elk.redis4j.api.RedisResponse;


public interface ICacheConnector
{
	public void initSession();
	
	public int getNumberOfConnected();
	
	public ISession getSessionByKey(String key);

	public RedisResponse executeCommand(RedisCommand command, String key, Object... args);
	
	public RedisResponse executeMultiKeysNoArgsAndMultiReplay(RedisCommand command, String... keys);
	
	//public List<RedisResponse> executeMultiKeysNoArgsAndSingleReplay(RedisCommand command, String... keys);
	
	public List<RedisResponse> executeMultiKeysWithSameArgAndSingleReplay(RedisCommand command, Object arg, String... keys);
	
	public List<RedisResponse> executeMultiKeyValueAndSingleReplay(RedisCommand command, Map<String, Object> map);
	
	public List<RedisResponse> executeCommandOnAllShardingAndSingleReplay(RedisCommand command, Object... args);
	
	public RedisResponse executeCommandOnAllShardingAndMultiReplay(RedisCommand command, Object... args);

	public void disConnect();
}