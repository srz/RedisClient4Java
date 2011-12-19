package org.elk.redis4j.test;

import org.elk.redis4j.api.async.IRedisAsyncClient;
import org.elk.redis4j.api.cache.IRedisCacheClient;
import org.elk.redis4j.api.database.IRedisDatabaseClient;
import org.elk.redis4j.api.exception.ErrorCommandException;
import org.elk.redis4j.impl.RedisClientBuilder;


public class Helper
{
	private static String ip = "172.19.32.46";//"192.2.9.223";//"192.168.1.103";
	private static int port = 6379;//6379;
	private static int defaultIndexDB = 0;
	private static String password = "abc";
	
	
	public static IRedisDatabaseClient getRedisDatabaseClient()
	{
		return RedisClientBuilder.buildDatabaseClient(ip, port, defaultIndexDB, password);
	}
	
	public static IRedisCacheClient getRedisCacheClient()
	{
		RedisClientBuilder builder = new RedisClientBuilder();
		builder.addSharding(ip, port, 1, password);
		builder.addSharding(ip, port, 2, password);
//		builder.addSharding(ip, port, 3, password);
//		builder.addSharding(ip, port, 4, password);
//		builder.addSharding(ip, port, 5, password);
		return builder.buildCacheClient();
	}
	
	public static IRedisAsyncClient getRedisAsyncClient()
	{
		return RedisClientBuilder.buildAsyncClient(ip, 6379, defaultIndexDB, password);
	}
}
