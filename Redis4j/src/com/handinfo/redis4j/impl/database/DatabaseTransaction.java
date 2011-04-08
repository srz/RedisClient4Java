package com.handinfo.redis4j.impl.database;

import java.util.List;

import com.handinfo.redis4j.api.RedisCommand;
import com.handinfo.redis4j.api.RedisResponse;
import com.handinfo.redis4j.api.database.IDataBaseConnector;
import com.handinfo.redis4j.api.database.IDatabaseTransaction;

public class DatabaseTransaction extends BatchCommandlist implements IDatabaseTransaction
{
	public DatabaseTransaction(IDataBaseConnector connector)
	{
		super(connector);
		super.addCommand(RedisCommand.MULTI);
	}

	@Override
	public Boolean commit()
	{
		super.addCommand(RedisCommand.EXEC);
		List<RedisResponse> responseList = super.connector.executeBatch(super.commandList);
		return responseList==null ? false: true;
	}

	@Override
	public void discard()
	{
		super.addCommand(RedisCommand.DISCARD);
	}

	@Override
	public void unwatch()
	{
		super.addCommand(RedisCommand.UNWATCH);
	}

	@Override
	public void watch(String... keys)
	{
		//super.connector.executeCommand(RedisCommand.WATCH, (Object[])keys);
		super.addCommand(0, RedisCommand.WATCH, (Object[])keys);
	}
}
