package org.elk.redis4j.impl.cache;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.elk.redis4j.api.ISession;
import org.elk.redis4j.api.RedisCommand;
import org.elk.redis4j.api.RedisResponse;
import org.elk.redis4j.api.RedisResponseType;
import org.elk.redis4j.api.Sharding;
import org.elk.redis4j.api.cache.ICacheConnector;
import org.elk.redis4j.api.cache.IRedisCacheClient;
import org.elk.redis4j.impl.transfers.Session;
import org.elk.redis4j.impl.transfers.SessionManager;
import org.elk.redis4j.impl.util.LogUtil;


public class CacheConnector implements ICacheConnector
{
	private final Logger logger = LogUtil.getLogger(CacheConnector.class.getName());
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
	 * @see org.elk.redis4j.impl.transfers.IConnector#disConnect()
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
	public RedisResponse executeCommand(RedisCommand command, String key, Object... args)
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

	private Map<ISession, List<String>> getSessionAndKeysMap(RedisCommand command, String... keys)
	{
		Map<ISession, List<String>> sessionList = new LinkedHashMap<ISession, List<String>>(sessions.length);
		for (String key : keys)
		{
			ISession session = getSessionByKey(key);
			if (sessionList.containsKey(session))
			{
				sessionList.get(session).add(key);
			} else
			{
				List<String> keyList = new ArrayList<String>(keys.length);
				keyList.add(key);
				sessionList.put(session, keyList);
			}
		}
		return sessionList;
	}

	private Map<ISession, Map<String, Integer>> getSessionAndKeysMapWithIndex(RedisCommand command, String... keys)
	{
		Map<ISession, Map<String, Integer>> sessionList = new LinkedHashMap<ISession, Map<String, Integer>>(sessions.length);
		for (int i = 0; i < keys.length; i++)
		{
			ISession session = getSessionByKey(keys[i]);
			if (sessionList.containsKey(session))
			{
				sessionList.get(session).put(keys[i], i);
			} else
			{
				Map<String, Integer> keyList = new LinkedHashMap<String, Integer>(keys.length);
				keyList.put(keys[i], i);
				sessionList.put(session, keyList);
			}
		}
		return sessionList;
	}

	@Override
	public RedisResponse executeMultiKeysNoArgsAndMultiReplay(RedisCommand command, String... keys)
	{
		Map<ISession, Map<String, Integer>> sessionList = getSessionAndKeysMapWithIndex(command, keys);

		List<RedisResponse> responseList = new ArrayList<RedisResponse>(keys.length);
		for (int i = 0; i < keys.length; i++)
		{
			responseList.add(i, null);
		}

		Iterator<Entry<ISession, Map<String, Integer>>> iterator = sessionList.entrySet().iterator();
		while (iterator.hasNext())
		{
			Entry<ISession, Map<String, Integer>> entry = iterator.next();
			ISession session = entry.getKey();
			Map<String, Integer> keyList = entry.getValue();

			List<RedisResponse> responseMultiValue = session.executeCommand(command, keyList.keySet().toArray()).getMultiBulkValue();

			Iterator<Entry<String, Integer>> keyItem = keyList.entrySet().iterator();
			int i = 0;
			while (keyItem.hasNext())
			{
				Entry<String, Integer> keyEntry = keyItem.next();
				responseList.set(keyEntry.getValue(), responseMultiValue.get(i++));
			}
		}
		RedisResponse response = new RedisResponse(RedisResponseType.MultiBulkReplies);
		response.setMultiBulkValue(responseList);

		return response;
	}

	// @Override
	public List<RedisResponse> executeMultiKeysNoArgsAndSingleReplay(RedisCommand command, String... keys)
	{
		Map<ISession, List<String>> sessionList = getSessionAndKeysMap(command, keys);

		List<RedisResponse> responseList = new ArrayList<RedisResponse>(sessionList.size());

		Iterator<Entry<ISession, List<String>>> iterator = sessionList.entrySet().iterator();
		while (iterator.hasNext())
		{
			Entry<ISession, List<String>> entry = iterator.next();
			ISession session = entry.getKey();
			List<String> keyList = entry.getValue();

			RedisResponse response = session.executeCommand(command, keyList.toArray());

			if (response != null)
			{
				responseList.add(response);
			}
		}

		return responseList;
	}

	@Override
	public List<RedisResponse> executeMultiKeysWithSameArgAndSingleReplay(RedisCommand command, Object arg, String... keys)
	{
		Map<ISession, List<String>> sessionList = getSessionAndKeysMap(command, keys);

		List<RedisResponse> responseList = new ArrayList<RedisResponse>(sessionList.size());

		Iterator<Entry<ISession, List<String>>> iterator = sessionList.entrySet().iterator();
		while (iterator.hasNext())
		{
			Entry<ISession, List<String>> entry = iterator.next();
			ISession session = entry.getKey();
			List<String> keyList = entry.getValue();

			Object[] args = null;
			if (arg == null)
			{
				args = keyList.toArray();
			} else
			{
				args = new Object[keyList.size() + 1];
				args[0] = arg;
				System.arraycopy(keyList.toArray(), 0, args, 1, keyList.size());
			}
			RedisResponse response = session.executeCommand(command, args);

			if (response != null)
			{
				responseList.add(response);
			}
		}

		return responseList;
	}

	private Map<ISession, List<Object>> getSessionAndKeyValueMap(RedisCommand command, Map<String, Object> map)
	{
		Map<ISession, List<Object>> sessionList = new LinkedHashMap<ISession, List<Object>>(sessions.length);
		for (String key : map.keySet())
		{
			ISession session = getSessionByKey(key);
			if (sessionList.containsKey(session))
			{
				sessionList.get(session).add(key);
				sessionList.get(session).add(map.get(key));
			} else
			{
				List<Object> keyList = new ArrayList<Object>(map.size());
				keyList.add(key);
				keyList.add(map.get(key));
				sessionList.put(session, keyList);
			}
		}
		return sessionList;
	}

	@Override
	public List<RedisResponse> executeMultiKeyValueAndSingleReplay(RedisCommand command, Map<String, Object> map)
	{
		Map<ISession, List<Object>> sessionList = getSessionAndKeyValueMap(command, map);

		List<RedisResponse> responseList = new ArrayList<RedisResponse>(sessionList.size());

		Iterator<Entry<ISession, List<Object>>> iterator = sessionList.entrySet().iterator();
		while (iterator.hasNext())
		{
			Entry<ISession, List<Object>> entry = iterator.next();
			ISession session = entry.getKey();
			List<Object> keyList = entry.getValue();

			RedisResponse response = session.executeCommand(command, keyList.toArray());

			if (response != null)
			{
				responseList.add(response);
			}
		}

		return responseList;
	}

	@Override
	public List<RedisResponse> executeCommandOnAllShardingAndSingleReplay(RedisCommand command, Object... args)
	{
		List<RedisResponse> responseList = new ArrayList<RedisResponse>(sessions.length);

		for (ISession session : sessions)
		{
			RedisResponse response = session.executeCommand(command, args);

			if (response != null)
			{
				responseList.add(response);
			}
		}
		return responseList;
	}

	@Override
	public RedisResponse executeCommandOnAllShardingAndMultiReplay(RedisCommand command, Object... args)
	{
		List<RedisResponse> responseList = new ArrayList<RedisResponse>(sessions.length);

		for (ISession session : sessions)
		{
			RedisResponse response = session.executeCommand(command, args);

			if (response != null)
			{
				responseList.addAll(response.getMultiBulkValue());
			}
		}
		
		RedisResponse response = new RedisResponse(RedisResponseType.MultiBulkReplies);
		response.setMultiBulkValue(responseList);
		
		return response;
	}

	@Override
	public int getNumberOfConnected()
	{
		int i = 0;
		for (ISession session : sessions)
		{
			if (session.isConnected())
				i++;
		}
		return i;
	}
}
