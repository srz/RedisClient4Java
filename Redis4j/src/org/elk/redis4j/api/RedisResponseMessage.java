package org.elk.redis4j.api;

public enum RedisResponseMessage
{
	PONG("PONG", 0),
	OK("OK", 1),
	SHUTDOWNERROR("0", 2),
	QUEUE("QUEUE", 3),
	INTEGER_1("1", 4),
	INTEGER_0("0", 5);

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
