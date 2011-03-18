package com.handinfo.redis4j.manager.worker;

import com.handinfo.redis4j.api.IRedis4j;

public interface IExecuteCommand
{
	public String executeCommand(IRedis4j client);
}
