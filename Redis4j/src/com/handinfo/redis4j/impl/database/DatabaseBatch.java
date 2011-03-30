/**
 * 
 */
package com.handinfo.redis4j.impl.database;

import com.handinfo.redis4j.api.IConnector;
import com.handinfo.redis4j.api.RedisCommand;
import com.handinfo.redis4j.api.database.IDatabaseBatch;

/**
 * @author Administrator
 * 
 */
public class DatabaseBatch extends BatchCommandlist implements IDatabaseBatch
{
	public DatabaseBatch(IConnector connector)
	{
		super(connector);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.api.IBatch#commit()
	 */
	@Override
	public void execute()
	{
		this.connector.executeBatch(this.commandList);
	}
}
