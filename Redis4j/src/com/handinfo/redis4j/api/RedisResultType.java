package com.handinfo.redis4j.api;

public final class RedisResultType
{
	public final static char SingleLineReply = '+';
	public final static char ErrorReply = '-';
	public final static char IntegerReply = ':';
	public final static char BulkReplies = '$';
	public final static char MultiBulkReplies = '*';
}
