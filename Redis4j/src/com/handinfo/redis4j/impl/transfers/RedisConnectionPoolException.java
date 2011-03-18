package com.handinfo.redis4j.impl.transfers;

public class RedisConnectionPoolException extends RuntimeException
{
	private static final long serialVersionUID = -7312948024194056203L;

	/**
	 * 
	 */
	public RedisConnectionPoolException()
	{
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public RedisConnectionPoolException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public RedisConnectionPoolException(String message)
	{
		super(message);
	}

	/**
	 * @param cause
	 */
	public RedisConnectionPoolException(Throwable cause)
	{
		super(cause);
	}

//	/** (non-Javadoc)
//	 * @see java.lang.Throwable#getMessage()
//	 */
//	@Override
//	public String getMessage()
//	{
//		return "create connection pool failed, please check server state";
//	}


}
