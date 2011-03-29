package com.handinfo.redis4j.api;

public enum RedisResponseMessage
{
	PONG("PONG", 0),
	OK("OK", 1),
	SHUTDOWNERROR("0", 2),
	QUEUE("0", 3);

	private String value;
	
	public String getValue()
	{
		return value;
	}

	private RedisResponseMessage(String value, int ordinal)
	{
		this.value = value;
	}

}
