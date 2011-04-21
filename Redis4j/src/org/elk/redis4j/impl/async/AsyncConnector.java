package org.elk.redis4j.impl.async;

import java.util.concurrent.BrokenBarrierException;
import java.util.logging.Logger;

import org.elk.redis4j.api.ISession;
import org.elk.redis4j.api.RedisCommand;
import org.elk.redis4j.api.Sharding;
import org.elk.redis4j.api.async.IAsyncConnector;
import org.elk.redis4j.api.async.IRedisAsyncClient;
import org.elk.redis4j.api.exception.CleanLockedThreadException;
import org.elk.redis4j.api.exception.ErrorCommandException;
import org.elk.redis4j.impl.transfers.SessionManager;
import org.elk.redis4j.impl.transfers.handler.ReconnectNetworkHandler;
import org.elk.redis4j.impl.util.LogUtil;


public class AsyncConnector implements IAsyncConnector
{
	//private final Logger logger = (new Log(AsyncConnector.class.getName())).getLogger();
	private SessionManager sessionManager;
	private Sharding sharding;
	private ISession session;

	public AsyncConnector(Sharding sharding)
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
	public void executeAsyncCommand(IRedisAsyncClient.Result notify, RedisCommand command, Object... args) throws InterruptedException
	{
		session.executeAsyncCommand(notify, command, args);
	}

	/*
	 * (non-Javadoc)
	 * @see org.elk.redis4j.impl.transfers.IConnector#disConnect()
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
