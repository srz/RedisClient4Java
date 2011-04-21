package org.elk.redis4j.manager.worker;

import org.elk.redis4j.api.database.IRedisDatabaseClient;

public interface IExecuteCommand
{
	public String executeCommand(IRedisDatabaseClient client);
}
