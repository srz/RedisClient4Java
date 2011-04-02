package com.handinfo.redis4j.impl;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.handinfo.redis4j.api.IDataBaseConnector;
import com.handinfo.redis4j.api.IRedis4jAsync;
import com.handinfo.redis4j.api.RedisCommand;
import com.handinfo.redis4j.api.Sharding;
import com.handinfo.redis4j.api.exception.CleanLockedThreadException;
import com.handinfo.redis4j.api.exception.ErrorCommandException;
import com.handinfo.redis4j.impl.database.DatabaseConnector;

public class Redis4jAsyncClient implements IRedis4jAsync
{
	private static final Logger logger = Logger.getLogger(Redis4jAsyncClient.class.getName());
	private AtomicBoolean isExecute;
	private IDataBaseConnector connector;
	
	public Redis4jAsyncClient(Sharding sharding) //throws Exception
	{
		this.isExecute = new AtomicBoolean(false);
		sharding.setUseHeartbeat(false);
		
		connector = new DatabaseConnector(sharding);
		
		connector.connect();
		if (!connector.isConnected())
		{
			logger.log(Level.WARNING, "can not connect to server,client will reconnect after " + sharding.getReconnectDelay() + " s");
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
