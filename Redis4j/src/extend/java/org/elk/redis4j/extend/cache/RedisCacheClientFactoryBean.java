package org.elk.redis4j.extend.cache;

import java.util.Set;

import org.elk.redis4j.api.cache.IRedisCacheClient;
import org.elk.redis4j.impl.RedisClientBuilder;
import org.springframework.beans.factory.FactoryBean;

public class RedisCacheClientFactoryBean implements FactoryBean<IRedisCacheClient>
{
	private Set<String> redisUrlList;

	public void setRedisUrlList(Set<String> redisUrlList)
	{
		this.redisUrlList = redisUrlList;
	}
	
	private IRedisCacheClient redisCacheClient;

	@Override
	public IRedisCacheClient getObject() throws Exception
	{
		RedisClientBuilder builder = new RedisClientBuilder();
		
		String ip = "";
		int port = 6379;
		int defaultIndexDB = 0;
		String password = "";
		
		for(String url : redisUrlList)
		{
			String[] all = url.split(":");
			if(all.length == 4)
			{
				ip = all[0];
				port = Integer.parseInt(all[1]);
				defaultIndexDB = Integer.parseInt(all[2]);
				password = all[3];
				
				builder.addSharding(ip, port, defaultIndexDB, password);
			}
		}
		
		redisCacheClient = builder.buildCacheClient();
		return redisCacheClient;
	}

	@Override
	public Class<IRedisCacheClient> getObjectType()
	{
		return IRedisCacheClient.class;
	}

	@Override
	public boolean isSingleton()
	{
		return true;
	}
	
	public void shutdown()
	{
		redisCacheClient.quit();
	}
}
