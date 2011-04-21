package org.elk.redis4j.api;

public enum RedisResponseType
{
	SingleLineReply('+'), ErrorReply('-'), IntegerReply(':'), BulkReplies('$'), MultiBulkReplies('*'), NONE('#');

	private char value;

	public char getValue()
	{
		return value;
	}

	private RedisResponseType(char value)
	{
		this.value = value;
	}

	public static RedisResponseType fromChar(char c)
	{
		switch (c)
		{
		case '+':
			return SingleLineReply;
		case '-':
			return ErrorReply;
		case ':':
			return IntegerReply;
		case '$':
			return BulkReplies;
		case '*':
			return MultiBulkReplies;
		default:
			return null;
		}
	}
}
