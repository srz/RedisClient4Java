package com.handinfo.redis4j.impl.database;

import java.util.List;

import com.handinfo.redis4j.api.RedisCommand;
import com.handinfo.redis4j.api.RedisResponse;
import com.handinfo.redis4j.api.database.IDataBaseConnector;
import com.handinfo.redis4j.api.database.IDatabaseTransaction;
import com.handinfo.redis4j.api.exception.NullBatchException;

public class DatabaseTransaction extends BatchCommandlist implements IDatabaseTransaction
{
	public DatabaseTransaction(IDataBaseConnector connector)
	{
		super(connector);
	}

	@Override
	public Boolean commit()
	{
		super.addCommand(0, RedisCommand.MULTI);
		super.connector.executeTransaction(super.commandList);
		super.commandList.clear();
		RedisResponse response = super.connector.executeCommand(RedisCommand.EXEC);

		return response.getMultiBulkValue()==null ? false: true;
	}

	@Override
	public void discard()
	{
		//super.addCommand(RedisCommand.DISCARD);
		super.connector.executeCommand(RedisCommand.DISCARD);
	}

	@Override
	public void unwatch()
	{
		//super.addCommand(RedisCommand.UNWATCH);
		super.connector.executeCommand(RedisCommand.UNWATCH);
	}

	@Override
	public void watch(String... keys)
	{
		super.commandList.clear();
		super.connector.executeCommand(RedisCommand.WATCH, (Object[])keys);
		//super.addCommand(0, RedisCommand.WATCH, (Object[])keys);
	}
}
