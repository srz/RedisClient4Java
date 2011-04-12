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
		if (super.commandList.size() != 0)
		{
			super.addCommand(0, RedisCommand.MULTI);
			super.addCommand(RedisCommand.EXEC);
			List<RedisResponse> responseList = super.connector.executeBatch(super.commandList);
			super.commandList.clear();
			//RedisResponse response = super.connector.executeCommand(RedisCommand.EXEC);
			RedisResponse transactionResponse = responseList.get(responseList.size()-1);

			return transactionResponse.getMultiBulkValue()==null ? false: true;
		}
		else
			throw new NullBatchException("please add some command to transaction!");
	}

	@Override
	public void discard()
	{
		//super.addCommand(RedisCommand.DISCARD);
		super.connector.executeCommand(RedisCommand.DISCARD);
		super.commandList.clear();
	}

	@Override
	public void unwatch()
	{
		//super.addCommand(RedisCommand.UNWATCH);
		super.connector.executeCommand(RedisCommand.UNWATCH);
		super.commandList.clear();
	}

	@Override
	public void watch(String... keys)
	{
		super.commandList.clear();
		super.connector.executeCommand(RedisCommand.WATCH, (Object[])keys);
		//super.addCommand(0, RedisCommand.WATCH, (Object[])keys);
	}
}
