package com.handinfo.redis4j.api;

import java.util.ArrayList;

/**
 * @author srz 
 */
public class Batch
{
	private ArrayList<String[]> commandList;

	public Batch()
	{
		commandList = new ArrayList<String[]>();
	}

	public void addEcho(String value)
	{
		String[] echo = new String[RedisCommand.ECHO.getValue().length+1];
		System.arraycopy(RedisCommand.ECHO.getValue(), 0, echo, 0, RedisCommand.ECHO.getValue().length);

		echo[RedisCommand.ECHO.getValue().length] = value;
		commandList.add(echo);
	}
	
	public void addCommand(RedisCommand command, String...args)
	{
		String[] cmd = new String[command.getValue().length + args.length];
		System.arraycopy(command.getValue(), 0, cmd, 0, command.getValue().length);
		System.arraycopy(args, 0, cmd, command.getValue().length, args.length);
		commandList.add(cmd);
	}

	public ArrayList<String[]> getCommandList()
	{
		return commandList;
	}
}
