/**
 * 
 */
package org.elk.redis4j.impl.database;

import org.elk.redis4j.api.database.IDataBaseConnector;
import org.elk.redis4j.api.database.IDatabaseBatch;
import org.elk.redis4j.api.exception.NullBatchException;

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
	 * @see org.elk.redis4j.api.IBatch#commit()
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
