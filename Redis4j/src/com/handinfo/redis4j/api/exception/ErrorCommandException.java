package com.handinfo.redis4j.api.exception;

public class ErrorCommandException extends RedisClientException
{
	/**
	 * 
	 */
	public ErrorCommandException()
	{
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ErrorCommandException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public ErrorCommandException(String message)
	{
		super(message);
		super.fillInStackTrace();
	}

	/**
	 * @param cause
	 */
	public ErrorCommandException(Throwable cause)
	{
		super(cause);
	}

	private static final long serialVersionUID = 1L;
	
}
