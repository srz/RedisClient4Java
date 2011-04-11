package com.handinfo.redis4j.api.exception;

public class UnknownException extends RedisClientException
{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public UnknownException()
	{
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public UnknownException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public UnknownException(String message)
	{
		super(message);
	}

	/**
	 * @param cause
	 */
	public UnknownException(Throwable cause)
	{
		super(cause);
	}
}
