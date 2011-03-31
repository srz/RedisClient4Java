package com.handinfo.redis4j.impl.database;

import com.handinfo.redis4j.api.IConnector;
import com.handinfo.redis4j.api.RedisCommand;
import com.handinfo.redis4j.api.database.IDatabaseTransaction;

public class DatabaseTransaction extends BatchCommandlist implements IDatabaseTransaction
{
	public DatabaseTransaction(IConnector connector)
	{
		super(connector);
		super.addCommand(RedisCommand.MULTI);
	}

	@Override
	public void commit()
	{
		super.addCommand(RedisCommand.EXEC);
		super.connector.executeBatch(super.commandList);
	}

	@Override
	public boolean discard()
	{
		super.addCommand(RedisCommand.DISCARD);
		return false;
	}

	@Override
	public boolean unwatch()
	{
		super.addCommand(RedisCommand.UNWATCH);
		return false;
	}

	@Override
	public boolean watch()
	{
		super.addCommand(RedisCommand.WATCH);
		return false;
	}
}
