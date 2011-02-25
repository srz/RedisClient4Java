package com.handinfo.redis4j.impl.transfers;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class ConnectionPool
{
	private AtomicBoolean isNeedClose;
	private int poolSize;
	private BlockingQueue<Connector> pool;
	private int numberOfCreateSucess = 0;

	/**
	 * @param serverAddress
	 * @param serverPort
	 * @param poolSize
	 * @param pool
	 */
	@SuppressWarnings("finally")
	public ConnectionPool(String serverAddress, int serverPort, int poolSize, int indexDB)
	{
		isNeedClose = new AtomicBoolean(false);
		this.poolSize = poolSize;
		this.pool = new LinkedBlockingQueue<Connector>(poolSize);

		for (int i = 0; i < poolSize; i++)
		{
			Connector connector = new Connector();
			if (connector.connect(serverAddress, serverPort))
			{
				connector.selectDB(indexDB);
				try
				{
					pool.put(connector);
					numberOfCreateSucess++;
				}
				catch (InterruptedException e)
				{
					numberOfCreateSucess--;
					e.printStackTrace();
				}
			} else
			{
				try
				{
					throw new RedisConnectionException();
				}
				catch (RedisConnectionException e)
				{
					e.printStackTrace();
				}
				finally
				{
					break;
				}
			}
		}
		if (numberOfCreateSucess == poolSize)
			System.out.printf("create %d connector in pool\n", poolSize);
		else
		{
			try
			{
				throw new RedisConnectionPoolException();
			}
			catch (RedisConnectionPoolException e)
			{
				e.printStackTrace();
			}
			finally
			{
				closePool();
			}
		}
	}

	public Connector getConnector()
	{
		Connector connector = null;
		try
		{
			connector = pool.take();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		return connector;
	}

	public void releaseConnector(Connector connector)
	{
		try
		{
			pool.put(connector);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public synchronized void closePool()
	{
		if (!isNeedClose.getAndSet(true))
		{
			if(numberOfCreateSucess == poolSize && pool.size() != poolSize)
			{
				System.out.printf("there are %d connection leak!\n", poolSize - pool.size());
			}
			for (Connector conn : pool)
			{
				conn.disconnect();
			}
			System.out.printf("there is %d connection closed", pool.size());
			pool.clear();
		}
	}
}
