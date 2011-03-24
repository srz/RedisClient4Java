package com.handinfo.redis4j.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.handinfo.redis4j.api.IConnector;
import com.handinfo.redis4j.api.IRedis4j;
import com.handinfo.redis4j.api.classification.IConnection;
import com.handinfo.redis4j.api.classification.IHashes;
import com.handinfo.redis4j.api.classification.IKeys;
import com.handinfo.redis4j.api.classification.ILists;
import com.handinfo.redis4j.api.classification.IServer;
import com.handinfo.redis4j.api.classification.ISets;
import com.handinfo.redis4j.api.classification.ISortedSets;
import com.handinfo.redis4j.api.classification.IStrings;
import com.handinfo.redis4j.api.classification.ITransactions;
import com.handinfo.redis4j.impl.classification.Connection;
import com.handinfo.redis4j.impl.classification.Hashes;
import com.handinfo.redis4j.impl.classification.Keys;
import com.handinfo.redis4j.impl.classification.Lists;
import com.handinfo.redis4j.impl.classification.Server;
import com.handinfo.redis4j.impl.classification.Sets;
import com.handinfo.redis4j.impl.classification.SortedSets;
import com.handinfo.redis4j.impl.classification.Strings;
import com.handinfo.redis4j.impl.classification.Transactions;
import com.handinfo.redis4j.impl.transfers.Connector;
import com.handinfo.redis4j.impl.transfers.handler.HeartbeatHandler;

public class Redis4jClient implements IRedis4j
{
	private static final Logger logger = Logger.getLogger(Redis4jClient.class.getName());
	private IConnector connector = null;
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
	private int heartbeatTime;
	private int reconnectDelay;
	private final static int IDEL_TIMEOUT_PING = 10;//默认检测连接空闲发送ping的间隔时间,单位是秒
	private final static int RECONNECT_DELAY = 10;//默认断网重连间隔时间,单位是秒

	public Redis4jClient(String host, int port, int poolMaxSize, int indexDB, int heartbeatTime, int reconnectDelay) //throws Exception
	{
		this.host = host;
		this.port = port;
		this.poolMaxSize = poolMaxSize;
		this.indexDB = indexDB;
		this.heartbeatTime = heartbeatTime;
		this.reconnectDelay = reconnectDelay;
		
		connector = new Connector(host, port, poolMaxSize, indexDB, heartbeatTime, reconnectDelay);
		connection = new Connection(connector);
		hashes = new Hashes(connector);
		keys = new Keys(connector);
		lists = new Lists(connector);
		server = new Server(connector);
		sets = new Sets(connector);
		sortedSets = new SortedSets(connector);
		strings = new Strings(connector);
		transactions = new Transactions(connector);
		
		if(!connector.connect())
		{
			//throw new Exception("can not connect to server");
			logger.log(Level.WARNING, "can not connect to server,client will reconnect after " + this.reconnectDelay + " s");
		}
	}
	
	public Redis4jClient(String host, int port)// throws Exception
	{
		this(host, port, 1, 0, IDEL_TIMEOUT_PING, RECONNECT_DELAY);
	}
	
	public Redis4jClient(String host, int port, int indexDB)// throws Exception
	{
		this(host, port, 1, indexDB, IDEL_TIMEOUT_PING, RECONNECT_DELAY);
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
			return new Redis4jClient(this.host, this.port, this.poolMaxSize, this.indexDB, this.heartbeatTime, this.reconnectDelay);
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
