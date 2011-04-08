/**
 * 
 */
package com.handinfo.redis4j.impl.database;

import com.handinfo.redis4j.api.database.IDataBaseConnector;
import com.handinfo.redis4j.api.database.IDatabaseBatch;
import com.handinfo.redis4j.api.exception.NullBatchException;

/**
 * @author Administrator
 */
public class DatabaseBatch extends BatchCommandlist implements IDatabaseBatch
{
	public DatabaseBatch(IDataBaseConnector connector)
	{
		super(connector);
	}

	/*
	 * (non-Javadoc)
	 * @see com.handinfo.redis4j.api.IBatch#commit()
	 */
	@Override
	public void execute()
	{
		if (super.commandList.size() != 0)
		{
			this.connector.executeBatch(super.commandList);
			super.commandList.clear();
		}
		else
			throw new NullBatchException("please add some command to batch!");
	}
}
