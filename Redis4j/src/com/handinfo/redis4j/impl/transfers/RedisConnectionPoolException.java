package com.handinfo.redis4j.impl.transfers;

public class RedisConnectionPoolException extends Exception
{
	private static final long serialVersionUID = -7312948024194056203L;

	/** (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage()
	{
		return "create connection pool failed, please check server state";
	}


}
