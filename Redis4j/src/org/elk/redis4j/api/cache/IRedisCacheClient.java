package org.elk.redis4j.api.cache;

import java.util.Set;

import org.elk.redis4j.api.ICache;
import org.elk.redis4j.api.IConnection;
import org.elk.redis4j.api.Sharding;


public interface IRedisCacheClient extends ICache, IConnection
{
	public final int VIRTUAL_NODE_COUNT = 160;
	
	/**
	 * 获取连接相关信息
	 * @return
	 */
	public Set<Sharding> getShardGroupInfo();
}
