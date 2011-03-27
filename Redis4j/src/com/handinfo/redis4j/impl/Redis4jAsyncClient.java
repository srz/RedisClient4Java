package com.handinfo.redis4j.impl;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.handinfo.redis4j.api.IConnector;
import com.handinfo.redis4j.api.IRedis4jAsync;
import com.handinfo.redis4j.api.RedisCommand;
import com.handinfo.redis4j.api.exception.CleanLockedThreadException;
import com.handinfo.redis4j.api.exception.ErrorCommandException;
import com.handinfo.redis4j.impl.transfers.Connector;

public class Redis4jAsyncClient implements IRedis4jAsync
{
	private static final Logger logger = Logger.getLogger(Redis4jAsyncClient.class.getName());
	private AtomicBoolean isExecute;
	private IConnector connector;
	private String host;
	private int port;
	private int indexDB;
	private int heartbeatTime;
	private int reconnectDelay;
	private final static int IDEL_TIMEOUT_PING = 10;//默认检测连接空闲发送ping的间隔时间,单位是秒
	private final static int RECONNECT_DELAY = 10;//默认断网重连间隔时间,单位是秒
	
	public Redis4jAsyncClient(String host, int port, int indexDB, int heartbeatTime, int reconnectDelay) //throws Exception
	{
		this.host = host;
		this.port = port;
		this.indexDB = indexDB;
		this.heartbeatTime = heartbeatTime;
		this.reconnectDelay = reconnectDelay;
		this.isExecute = new AtomicBoolean(false);
		
		connector = new Connector(host, port, indexDB, heartbeatTime, reconnectDelay, false);
		
		if(!connector.connect())
		{
			logger.log(Level.WARNING, "can not connect to server,client will reconnect after " + this.reconnectDelay + " s");
		}
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IRedis4jAsyncClient#executeCommand(com.handinfo.redis4j.api.IRedis4j.AsyncCommand, com.handinfo.redis4j.api.IRedis4j.Notify)
	 */
	public void executeCommand(RedisCommand command, Notify notify) throws CleanLockedThreadException, ErrorCommandException, IllegalStateException, InterruptedException, BrokenBarrierException
	{
		if(command.getOperateType() != RedisCommand.OperateType.ASYNC)
		{
			throw new IllegalStateException("please use async command! example monitor...");
		}
		if(!this.isExecute.getAndSet(true))
		{
			connector.executeAsyncCommand(notify, command);
		}
		else
		{
			throw new IllegalStateException("this founction can be execute only once!");
		}
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IRedis4jAsyncClient#quit()
	 */
	public void quit()
	{
		connector.disConnect();
	}
}
