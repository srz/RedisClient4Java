package com.handinfo.redis4j.impl.database;

import java.util.ArrayList;

import com.handinfo.redis4j.api.IConnector;
import com.handinfo.redis4j.api.RedisCommand;

public abstract class BatchCommandlist
{
	protected ArrayList<String[]> commandList;
	protected IConnector connector;

	public BatchCommandlist(IConnector connector)
	{
		this.connector = connector;
		this.commandList = new ArrayList<String[]>();
	}

	protected void addCommand(RedisCommand command, String... args)
	{
		String[] cmd = new String[command.getValue().length + args.length];
		System.arraycopy(command.getValue(), 0, cmd, 0, command.getValue().length);
		System.arraycopy(args, 0, cmd, command.getValue().length, args.length);
		this.commandList.add(cmd);
	}
}
