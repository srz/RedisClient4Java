package com.handinfo.redis4j.api.exception;

public class RedisClientException extends RuntimeException
{
	private static final long serialVersionUID = 4283639473043811185L;

	/**
	 * 
	 */
	public RedisClientException()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public RedisClientException(String message, Throwable cause)
	{
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public RedisClientException(String message)
	{
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public RedisClientException(Throwable cause)
	{
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
}
