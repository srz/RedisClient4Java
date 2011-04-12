package com.handinfo.redis4j.impl.cache;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.handinfo.redis4j.api.ISession;
import com.handinfo.redis4j.api.RedisCommand;
import com.handinfo.redis4j.api.RedisResponse;
import com.handinfo.redis4j.api.Sharding;
import com.handinfo.redis4j.api.cache.ICacheConnector;
import com.handinfo.redis4j.api.cache.IRedisCacheClient;
import com.handinfo.redis4j.api.exception.CleanLockedThreadException;
import com.handinfo.redis4j.api.exception.ErrorCommandException;
import com.handinfo.redis4j.impl.transfers.Session;
import com.handinfo.redis4j.impl.transfers.SessionManager;
import com.handinfo.redis4j.impl.transfers.handler.ReconnectNetworkHandler;
import com.handinfo.redis4j.impl.util.Log;

public class CacheConnector implements ICacheConnector
{
	private final Logger logger = (new Log(CacheConnector.class.getName())).getLogger();
	private SessionManager sessionManager;
	private Set<Sharding> serverList;
	private ISession[] sessions;
	private KetamaNodeLocator locator;

	public CacheConnector(Set<Sharding> serverList)
	{
		this.sessionManager = new SessionManager();
		this.serverList = serverList;
		this.sessions = new Session[serverList.size()];
	}

	/*
	 * (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.transfers.IConnector#disConnect()
	 */
	@Override
	public void disConnect()
	{
		for (ISession session : sessions)
		{
			session.close();
		}
		sessionManager.disconnectAllSession();
	}

	@Override
	public RedisResponse executeCommand(RedisCommand command, String key, Object... args) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException
	{
		Object[] newArgs = new Object[args.length + 1];
		newArgs[0] = key;
		System.arraycopy(args, 0, newArgs, 1, args.length);

		return getSessionByKey(key).executeCommand(command, newArgs);
	}

	@Override
	public ISession getSessionByKey(String key)
	{
		if (sessions.length == 1)
			return sessions[0];
		ISession session = locator.getPrimary(key);
		logger.info("session=" + session.getName() + " key=" + key);
		return session;
	}

	@Override
	public void initSession()
	{
		int i = 0;
		for (Sharding server : serverList)
		{
			sessions[i] = sessionManager.createSession(server);
			i++;
		}

		locator = new KetamaNodeLocator(sessions, HashAlgorithm.KETAMA_HASH, IRedisCacheClient.VIRTUAL_NODE_COUNT);
	}

	@Override
	public RedisResponse executeCommand(RedisCommand command, String... keys) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumberOfConnected()
	{
		int i = 0;
		for(ISession session : sessions)
		{
			if(session.isConnected())
				i++;
		}
		return i;
	}
}
