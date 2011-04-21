package org.elk.redis4j.api.database;

import java.util.ArrayList;
import java.util.List;

import org.elk.redis4j.api.RedisCommand;
import org.elk.redis4j.api.RedisResponse;
import org.elk.redis4j.api.exception.CleanLockedThreadException;
import org.elk.redis4j.api.exception.ErrorCommandException;


public interface IDataBaseConnector
{
	public boolean isConnected();
	
	public void connect();

	public RedisResponse executeCommand(RedisCommand command, Object... args);

	public List<RedisResponse> executeBatch(ArrayList<Object[]> commandList);

	public void disConnect();
}