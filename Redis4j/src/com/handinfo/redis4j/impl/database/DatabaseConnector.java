package com.handinfo.redis4j.impl.database;

import java.util.ArrayList;
import java.util.List;

import com.handinfo.redis4j.api.ISession;
import com.handinfo.redis4j.api.RedisCommand;
import com.handinfo.redis4j.api.RedisResponse;
import com.handinfo.redis4j.api.Sharding;
import com.handinfo.redis4j.api.database.IDataBaseConnector;
import com.handinfo.redis4j.api.exception.CleanLockedThreadException;
import com.handinfo.redis4j.api.exception.ErrorCommandException;
import com.handinfo.redis4j.api.exception.RedisClientException;
import com.handinfo.redis4j.impl.transfers.SessionManager;

public class DatabaseConnector implements IDataBaseConnector
{
	//private final Logger logger = (new Log(DatabaseConnector.class.getName())).getLogger();
	private SessionManager sessionManager;
	private Sharding sharding;
	private ISession session;


	public DatabaseConnector(Sharding sharding)
	{
		this.sessionManager = new SessionManager();
		this.sharding = sharding;
	}

	@Override
	public boolean isConnected()
	{
		return session==null ? false : session.isConnected();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.handinfo.redis4j.impl.transfers.IConnector#executeCommand(java.lang
	 * .String, java.lang.Object)
	 */
	@Override
	public RedisResponse executeCommand(RedisCommand command, Object... args)
	{
		if(session == null)
			throw new RedisClientException("Connection create failed or client has been quit!");
		return session.executeCommand(command, args);
	}

	@Override
	public List<RedisResponse> executeBatch(ArrayList<Object[]> commandList)
	{
		if(session == null)
			throw new RedisClientException("Connection create failed or client has been quit!");
		return session.executeBatch(commandList);
	}

	/*
	 * (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.transfers.IConnector#disConnect()
	 */
	@Override
	public void disConnect()
	{
		if(session == null)
			throw new RedisClientException("Connection create failed or client has been quit!");
		session.close();
		sessionManager.disconnectAllSession();
	}

	@Override
	public void connect()
	{
		session = sessionManager.createSession(sharding);
	}
}
