package com.handinfo.redis4j.api.exception;

public class NetworkException extends RedisClientException
{
	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage()
	{
		return "Network disconnected";
	}
}
