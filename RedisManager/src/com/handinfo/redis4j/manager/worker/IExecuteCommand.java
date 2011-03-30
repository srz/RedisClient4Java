package com.handinfo.redis4j.manager.worker;

import com.handinfo.redis4j.api.database.IRedisDatabaseClient;

public interface IExecuteCommand
{
	public String executeCommand(IRedisDatabaseClient client);
}
