package org.elk.redis4j.impl.async;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

import org.elk.redis4j.api.RedisCommand;
import org.elk.redis4j.api.Sharding;
import org.elk.redis4j.api.async.IAsyncConnector;
import org.elk.redis4j.api.async.IRedisAsyncClient;
import org.elk.redis4j.impl.util.LogUtil;


public class RedisAsyncClient implements IRedisAsyncClient
{
	private final Logger logger = LogUtil.getLogger(RedisAsyncClient.class.getName());
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
	 * @see org.elk.redis4j.impl.IRedis4jAsyncClient#executeCommand(org.elk.redis4j.api.IRedis4j.AsyncCommand, org.elk.redis4j.api.IRedis4j.Notify)
	 */
	public void executeCommand(RedisCommand command, Result result) throws InterruptedException
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
	 * @see org.elk.redis4j.impl.IRedis4jAsyncClient#quit()
	 */
	public void quit()
	{
		connector.disConnect();
	}
}
