package com.handinfo.redis4j.api.cache;

import com.handinfo.redis4j.api.ICache;
import com.handinfo.redis4j.api.IConnection;

public interface IRedisCacheClient extends ICache, IConnection
{
	public ICacheBatch getBatch();
	public ICacheTransaction getTransaction();
}
