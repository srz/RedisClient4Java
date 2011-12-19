package org.elk.redis4j.api.database;

import org.elk.redis4j.api.IConnection;
import org.elk.redis4j.api.IDatabase;
import org.elk.redis4j.api.Sharding;

public interface IRedisDatabaseClient extends IDatabase, IConnection
{
	/**
	 * 获取一个批处理对象,此批处理不是事物类型
	 */
	public IDatabaseBatch getNewBatch();
	
	/**
	 * 获取一个事物对象
	 */
	public IDatabaseTransaction getNewTransaction();
	
	/**
	 * 获取连接相关信息
	 * @return 返回当前连接到Redis服务器的所有连接的信息
	 */
	public Sharding getShardInfo();
}
