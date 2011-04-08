package com.handinfo.redis4j.impl;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;

import com.handinfo.redis4j.api.Sharding;
import com.handinfo.redis4j.api.async.IRedisAsyncClient;
import com.handinfo.redis4j.api.cache.IRedisCacheClient;
import com.handinfo.redis4j.api.database.IRedisDatabaseClient;
import com.handinfo.redis4j.impl.async.RedisAsyncClient;
import com.handinfo.redis4j.impl.cache.RedisCacheClient;
import com.handinfo.redis4j.impl.database.RedisDatabaseClient;

/**
 * @author srz
 *
 */
public class RedisClientBuilder
{
	private final Set<Sharding> shardingList = new HashSet<Sharding>();

	public static IRedisDatabaseClient buildDatabaseClient(String host, int port, int defaultIndexDB, String password)
	{
		if (!checkIpAndPort(host, port))
		{
			throw new IllegalArgumentException("host or port is error!");
		}
		
		Sharding sharding = new Sharding();
		sharding.setServerAddress(new InetSocketAddress(host, port));
		sharding.setDefaultIndexDB(defaultIndexDB);
		sharding.setPassword(password);
		sharding.setUseHeartbeat(true);
		
		return new RedisDatabaseClient(sharding);
	}
	
	public static IRedisAsyncClient buildAsyncClient(String host, int port, int defaultIndexDB, String password)
	{
		if (!checkIpAndPort(host, port))
		{
			throw new IllegalArgumentException("host or port is error!");
		}
		
		Sharding sharding = new Sharding();
		sharding.setServerAddress(new InetSocketAddress(host, port));
		sharding.setDefaultIndexDB(defaultIndexDB);
		sharding.setPassword(password);
		sharding.setUseHeartbeat(false);
		
		return new RedisAsyncClient(sharding);
	}
	
	public static IRedisAsyncClient buildAsyncClient(Sharding sharding)
	{
		sharding.setUseHeartbeat(false);
		
		return new RedisAsyncClient(sharding);
	}
	
	public IRedisCacheClient buildCacheClient()
	{
		return new RedisCacheClient(shardingList);
	}

	/**
	 * 添加服务器地址
	 * @param host
	 * @param port
	 */
	public void addSharding(String host, int port, int defaultIndexDB, String password)
	{
		if (!checkIpAndPort(host, port))
		{
			throw new IllegalArgumentException("host or port is error!");
		} else
		{
			Sharding sharding = new Sharding();
			sharding.setServerAddress(new InetSocketAddress(host, port));
			sharding.setDefaultIndexDB(defaultIndexDB);
			sharding.setPassword(password);
			sharding.setUseHeartbeat(true);
			sharding.setName(host + "_" + String.valueOf(port) + "_" + String.valueOf(defaultIndexDB));
			this.shardingList.add(sharding);
		}
	}

	private static boolean checkIpAndPort(String ip, int port)
	{
		boolean result = false;
		boolean isIP = false;
		boolean isPort = false;

		ip = ip.trim();
		if (ip.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}"))
		{
			String s[] = ip.split("\\.");
			if (Integer.parseInt(s[0]) < 255)
				if (Integer.parseInt(s[1]) < 255)
					if (Integer.parseInt(s[2]) < 255)
						if (Integer.parseInt(s[3]) < 255)
							isIP = true;
		}

		if (port >= 0 && port <= 65535)
		{
			isPort = true;
		}

		if (isIP && isPort)
		{
			result = true;
		}

		return result;
	}
}
