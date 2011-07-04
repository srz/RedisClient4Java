package org.elk.redis4j.api.exception;

public class CleanLockedThreadException extends RedisClientException
{
	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage()
	{
		return "Network disconnected, cleaning locked thread, please wait...";
	}
}
