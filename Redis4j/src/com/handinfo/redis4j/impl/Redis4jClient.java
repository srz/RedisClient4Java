package com.handinfo.redis4j.impl;

import com.handinfo.redis4j.api.IConnection;
import com.handinfo.redis4j.api.IHashes;
import com.handinfo.redis4j.api.IKeys;
import com.handinfo.redis4j.api.ILists;
import com.handinfo.redis4j.api.IRedis4j;
import com.handinfo.redis4j.api.IServer;
import com.handinfo.redis4j.api.ISets;
import com.handinfo.redis4j.api.ISortedSets;
import com.handinfo.redis4j.api.IStrings;
import com.handinfo.redis4j.api.ITransactions;
import com.handinfo.redis4j.impl.transfers.Connector;

public class Redis4jClient implements IRedis4j
{
	private Connector connector;
	
	private IConnection connection;
	private IHashes hashes;
	private IKeys keys;
	private ILists lists;
	private IServer server;
	private ISets sets;
	private ISortedSets sortedSets;
	private IStrings strings;
	private ITransactions transactions;

	public Redis4jClient(String host, int port, int poolSize, int indexDB)
	{
		connector = new Connector(host, port, poolSize, indexDB);
		
		connection = new Connection(connector);
		hashes = new Hashes(connector);
		keys = new Keys(connector);
		lists = new Lists(connector);
		server = new Server(connector);
		sets = new Sets(connector);
		sortedSets = new SortedSets(connector);
		strings = new Strings(connector);
		transactions = new Transactions(connector);
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IRedis4j#getConnection()
	 */
	public IConnection getConnection()
	{
		return connection;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IRedis4j#getHashes()
	 */
	public IHashes getHashes()
	{
		return hashes;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IRedis4j#getKeys()
	 */
	public IKeys getKeys()
	{
		return keys;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IRedis4j#getLists()
	 */
	public ILists getLists()
	{
		return lists;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IRedis4j#getServer()
	 */
	public IServer getServer()
	{
		return server;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IRedis4j#getSets()
	 */
	public ISets getSets()
	{
		return sets;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IRedis4j#getSortedSets()
	 */
	public ISortedSets getSortedSets()
	{
		return sortedSets;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IRedis4j#getStrings()
	 */
	public IStrings getStrings()
	{
		return strings;
	}

	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IRedis4j#getTransactions()
	 */
	public ITransactions getTransactions()
	{
		return transactions;
	}


//	/*
//	 * 创建到Redis服务器的连接，需要调用quit()函数关闭此连接
//	 */
//	@Override
//	public boolean connect()
//	{
//		connector = new Connector();
//
//		return connector.connect(host, port);
//	}
	
//	/**
//	 * 获取无法转化为字符串的对象,该功能不受到redis官方支持
//	 * 请与{@link #setObject(String, Object)}函数配套使用
//	 * @see com.handinfo.redis4j.api.IRedis4j#getObject(java.lang.String)
//	 */
//	@Override
//	public Object getObject(String key)
//	{
//		return bulkReply(RedisCommandType.GET, true, key);
//	}
//	
//	/**
//	 * 存储无法转化为字符串的对象,该功能不受到redis官方支持
//	 * 由于使用了对象序列化功能,所以使用此方法存储的对象无法使用sort进行排序,也不兼容其它种类的redis客户端
//	 * 请谨慎使用
//	 * 请与{@link #getObject(String)}函数配套使用
//	 * @see com.handinfo.redis4j.api.IRedis4j#setObject(java.lang.String, java.lang.Object)
//	 */
//	@SuppressWarnings("unchecked")
//	@Override
//	public <T> boolean setObject(String key, T value)
//	{
//		DataWrapper data = new DataWrapper(value);
//
//		return singleLineReplyForBoolean(RedisCommandType.SET, RedisResultInfo.OK, key, data);
//	}

}
