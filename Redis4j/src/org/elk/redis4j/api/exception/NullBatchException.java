package org.elk.redis4j.api.exception;

public class NullBatchException extends RedisClientException
{
	/**
	 * 
	 */
	public NullBatchException()
	{
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NullBatchException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public NullBatchException(String message)
	{
		super(message);
	}

	/**
	 * @param cause
	 */
	public NullBatchException(Throwable cause)
	{
		super(cause);
	}

	private static final long serialVersionUID = 1L;
	
}
