package org.elk.redis4j.extend.cache;

import java.util.Map;

import org.elk.redis4j.api.cache.IRedisCacheClient;

public class CacheBuilder
{
	public interface Callback<X, Y>
	{
		public X searchCache();

		public Boolean isSearchDB(X cacheResult);

		public Y searchDB();

		public X saveToCache(Y dbResult);
	}

	public CacheBuilder(IRedisCacheClient redisCacheClient, Map<String, String> cacheKeyPrefix)
	{
		this.redisCacheClient = redisCacheClient;
		this.cacheKeyPrefix = cacheKeyPrefix;
	}

	private IRedisCacheClient redisCacheClient;
	private Map<String, String> cacheKeyPrefix;

	public <X, Y> X build(String key, Callback<X, Y> callback)
	{
		String globalLockKey = "global_lock_" + key;
		
		// 1、获取缓存数据
		X result = callback.searchCache();

		if (callback.isSearchDB(result))
		{
			// 2、如缓存中无数据,以指定的key创建全局锁
			if (redisCacheClient.setOnNotExist(globalLockKey, System.currentTimeMillis()))
			{
				try
				{
					// 模拟读数据库时间
					// Thread.sleep(30000);

					// 读数据库
					// 把读出来的值存储进缓存
					// 为对象赋值
					result = callback.saveToCache(callback.searchDB());
				} finally
				{
					// 最后删除全局锁
					redisCacheClient.del(globalLockKey);
				}
			} else
			{
				// 创建锁失败,说明全局锁已经存在
				Long time = redisCacheClient.get(globalLockKey);
				if (time != null)
				{
					if (System.currentTimeMillis() - time >= 1000)
					{
						// 删除此锁
						redisCacheClient.del(globalLockKey);
					}
				}
			}
		}

		return result;
	}

	public String generateKeyPrefix(Class<?> classz)
	{
		cacheKeyPrefix.keySet().iterator();

		String keyPrefix = "";
		int packageNameLength = 0;
		
		for (String key : cacheKeyPrefix.keySet())
		{
			if (classz.getName().contains(key))
			{
				if (keyPrefix.isEmpty())
				{
					packageNameLength = key.length();
					keyPrefix = cacheKeyPrefix.get(key);
				}
				else
				{
					if(key.length() > packageNameLength)
					{
						packageNameLength = key.length();
						keyPrefix = cacheKeyPrefix.get(key);
					}
				}
			}
		}

		return keyPrefix + "_";
	}
}
