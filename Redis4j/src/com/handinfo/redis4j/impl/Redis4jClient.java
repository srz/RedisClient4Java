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
	private Connector connector = null;

	private IConnection connection = null;
	private IHashes hashes = null;
	private IKeys keys = null;
	private ILists lists = null;
	private IServer server = null;
	private ISets sets = null;
	private ISortedSets sortedSets = null;
	private IStrings strings = null;
	private ITransactions transactions = null;
	
	private String host;
	private int port;
	private int poolMaxSize;
	private int indexDB;

	

	public Redis4jClient(String host, int port, int poolMaxSize, int indexDB) throws Exception
	{
		this.host = host;
		this.port = port;
		this.poolMaxSize = poolMaxSize;
		this.indexDB = indexDB;
		
		connector = new Connector(host, port, poolMaxSize, indexDB);
		
		if(connector.connect())
		{
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
	}
	
	public Redis4jClient(String host, int port) throws Exception
	{
		this(host, port, 1, 0);
	}
	
	public Redis4jClient(String host, int port, int indexDB) throws Exception
	{
		this(host, port, 1, indexDB);
	}
	
	public boolean isConnectSucess()
	{
		return connector == null ? false: true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.impl.IRedis4j#getConnection()
	 */
	public IConnection getConnection()
	{
		return connection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.impl.IRedis4j#getHashes()
	 */
	public IHashes getHashes()
	{
		return hashes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.impl.IRedis4j#getKeys()
	 */
	public IKeys getKeys()
	{
		return keys;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.impl.IRedis4j#getLists()
	 */
	public ILists getLists()
	{
		return lists;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.impl.IRedis4j#getServer()
	 */
	public IServer getServer()
	{
		return server;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.impl.IRedis4j#getSets()
	 */
	public ISets getSets()
	{
		return sets;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.impl.IRedis4j#getSortedSets()
	 */
	public ISortedSets getSortedSets()
	{
		return sortedSets;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.impl.IRedis4j#getStrings()
	 */
	public IStrings getStrings()
	{
		return strings;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handinfo.redis4j.impl.IRedis4j#getTransactions()
	 */
	public ITransactions getTransactions()
	{
		return transactions;
	}
	
	public IRedis4j clone()
	{
		try
		{
			return new Redis4jClient(this.host, this.port, this.poolMaxSize, this.indexDB);
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	// /*
	// * 创建到Redis服务器的连接，需要调用quit()函数关闭此连接
	// */
	// @Override
	// public boolean connect()
	// {
	// connector = new Connector();
	//
	// return connector.connect(host, port);
	// }

	// /**
	// * 获取无法转化为字符串的对象,该功能不受到redis官方支持
	// * 请与{@link #setObject(String, Object)}函数配套使用
	// * @see com.handinfo.redis4j.api.IRedis4j#getObject(java.lang.String)
	// */
	// @Override
	// public Object getObject(String key)
	// {
	// return bulkReply(RedisCommandType.GET, true, key);
	// }
	//	
	// /**
	// * 存储无法转化为字符串的对象,该功能不受到redis官方支持
	// * 由于使用了对象序列化功能,所以使用此方法存储的对象无法使用sort进行排序,也不兼容其它种类的redis客户端
	// * 请谨慎使用
	// * 请与{@link #getObject(String)}函数配套使用
	// * @see com.handinfo.redis4j.api.IRedis4j#setObject(java.lang.String,
	// java.lang.Object)
	// */
	// @SuppressWarnings("unchecked")
	// @Override
	// public <T> boolean setObject(String key, T value)
	// {
	// DataWrapper data = new DataWrapper(value);
	//
	// return singleLineReplyForBoolean(RedisCommandType.SET,
	// RedisResultInfo.OK, key, data);
	// }

}
