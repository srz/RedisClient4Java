package com.handinfo.redis4j.impl.database;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.logging.Logger;

import com.handinfo.redis4j.api.ISession;
import com.handinfo.redis4j.api.RedisCommand;
import com.handinfo.redis4j.api.RedisResponse;
import com.handinfo.redis4j.api.Sharding;
import com.handinfo.redis4j.api.async.IRedisAsyncClient;
import com.handinfo.redis4j.api.database.IDataBaseConnector;
import com.handinfo.redis4j.api.exception.CleanLockedThreadException;
import com.handinfo.redis4j.api.exception.ErrorCommandException;
import com.handinfo.redis4j.impl.transfers.SessionManager;
import com.handinfo.redis4j.impl.transfers.handler.ReconnectNetworkHandler;
import com.handinfo.redis4j.impl.util.Log;

public class DatabaseConnector implements IDataBaseConnector
{
	//private final Logger logger = (new Log(DatabaseConnector.class.getName())).getLogger();
	private SessionManager sessionManager;
	private Sharding sharding;
	private ISession session;


	public DatabaseConnector(Sharding sharding) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException
	{
		this.sessionManager = new SessionManager();
		this.sharding = sharding;
	}

	@Override
	public boolean isConnected()
	{
		return session.isConnected();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.handinfo.redis4j.impl.transfers.IConnector#executeCommand(java.lang
	 * .String, java.lang.Object)
	 */
	@Override
	public RedisResponse executeCommand(RedisCommand command, Object... args) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException
	{
		return session.executeCommand(command, args);
	}

	@Override
	public List<RedisResponse> executeBatch(ArrayList<Object[]> commandList) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException
	{
		return session.executeBatch(commandList);
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
