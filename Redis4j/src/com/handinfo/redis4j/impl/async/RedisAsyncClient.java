package com.handinfo.redis4j.impl.async;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.handinfo.redis4j.api.RedisCommand;
import com.handinfo.redis4j.api.Sharding;
import com.handinfo.redis4j.api.async.IAsyncConnector;
import com.handinfo.redis4j.api.async.IRedisAsyncClient;
import com.handinfo.redis4j.api.exception.CleanLockedThreadException;
import com.handinfo.redis4j.api.exception.ErrorCommandException;
import com.handinfo.redis4j.impl.transfers.handler.ReconnectNetworkHandler;
import com.handinfo.redis4j.impl.util.Log;

public class RedisAsyncClient implements IRedisAsyncClient
{
	private final Logger logger = (new Log(RedisAsyncClient.class.getName())).getLogger();
	private AtomicBoolean isExecute;
	private IAsyncConnector connector;
	
	public RedisAsyncClient(Sharding sharding)
	{
		this.isExecute = new AtomicBoolean(false);
		sharding.setUseHeartbeat(false);
		
		connector = new AsyncConnector(sharding);
		
		connector.connect();
		if (!connector.isConnected())
		{
			logger.severe("can not connect to server,client will reconnect after " + sharding.getReconnectDelay() + " s");
		}
	}
	
	/* (non-Javadoc)
	 * @see com.handinfo.redis4j.impl.IRedis4jAsyncClient#executeCommand(com.handinfo.redis4j.api.IRedis4j.AsyncCommand, com.handinfo.redis4j.api.IRedis4j.Notify)
	 */
	public void executeCommand(RedisCommand command, Result result) throws CleanLockedThreadException, ErrorCommandException, IllegalStateException, InterruptedException, BrokenBarrierException
	{
		if(command.getOperateType() != RedisCommand.OperateType.ASYNC)
		{
			throw new IllegalStateException("please use async command! example monitor...");
		}
		if(!this.isExecute.getAndSet(true))
		{
			connector.executeAsyncCommand(result, command);
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
