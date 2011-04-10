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

public class DatabaseConnector implements IDataBaseConnector
{
	private static final Logger logger = Logger.getLogger(DatabaseConnector.class.getName());
	private SessionManager sessionManager;
	private Sharding sharding;
	private ISession session;


	public DatabaseConnector(Sharding sharding) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException
	{
		this.sessionManager = new SessionManager();
		this.sharding = sharding;
	}

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
	public RedisResponse executeCommand(RedisCommand command, Object... args) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException
	{
		return session.executeCommand(command, args);
	}

	public List<RedisResponse> executeBatch(ArrayList<Object[]> commandList) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException
	{
		return session.executeBatch(commandList);
	}

	public void executeAsyncCommand(IRedisAsyncClient.Result notify, RedisCommand command, Object... args) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException, InterruptedException, BrokenBarrierException
	{
		session.executeAsyncCommand(notify, command, args);
	}

	/*
	 * (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.transfers.IConnector#disConnect()
	 */
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

	@Override
	public void executeTransaction(ArrayList<Object[]> commandList) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException
	{
		session.executeTransaction(commandList);
	}
}
