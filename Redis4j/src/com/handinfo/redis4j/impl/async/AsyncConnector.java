package com.handinfo.redis4j.impl.async;

import java.util.concurrent.BrokenBarrierException;
import java.util.logging.Logger;

import com.handinfo.redis4j.api.ISession;
import com.handinfo.redis4j.api.RedisCommand;
import com.handinfo.redis4j.api.Sharding;
import com.handinfo.redis4j.api.async.IAsyncConnector;
import com.handinfo.redis4j.api.async.IRedisAsyncClient;
import com.handinfo.redis4j.api.exception.CleanLockedThreadException;
import com.handinfo.redis4j.api.exception.ErrorCommandException;
import com.handinfo.redis4j.impl.transfers.SessionManager;

public class AsyncConnector implements IAsyncConnector
{
	private static final Logger logger = Logger.getLogger(AsyncConnector.class.getName());
	private SessionManager sessionManager;
	private Sharding sharding;
	private ISession session;

	public AsyncConnector(Sharding sharding) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException
	{
		this.sessionManager = new SessionManager();
		this.sharding = sharding;
	}

	@Override
	public boolean isConnected()
	{
		return session.isConnected();
	}

	@Override
	public void executeAsyncCommand(IRedisAsyncClient.Result notify, RedisCommand command, Object... args) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException, InterruptedException, BrokenBarrierException
	{
		session.executeAsyncCommand(notify, command, args);
	}

	/*
	 * (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.transfers.IConnector#disConnect()
	 */
	@Override
	public void disConnect()
	{
		session.close();
		sessionManager.disconnectAllSession();
	}

	@Override
	public void connect()
	{
		session = sessionManager.createSession(sharding);
	}
}
