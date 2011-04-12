package com.handinfo.redis4j.test;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import com.handinfo.redis4j.api.database.IDatabaseBatch;
import com.handinfo.redis4j.api.database.IRedisDatabaseClient;
import com.handinfo.redis4j.impl.RedisClientBuilder;
import com.handinfo.redis4j.impl.database.RedisDatabaseClient;
import com.handinfo.redis4j.impl.transfers.handler.ReconnectNetworkHandler;
import com.handinfo.redis4j.impl.util.Log;

public class DataCheckOfBatch
{
	private final static Logger logger = (new Log(DataCheckOfBatch.class.getName())).getLogger();
	
	static int repeats = 10;//每个线程循环执行的次数
	static int corePoolSize = 1500;//测试程序启动的工作线程数
	
	static CountDownLatch cdl;
	static AtomicInteger numberOfAllExecute = new AtomicInteger(0);
	static String[] keys = new String[300000];

	public static void main(String[] args) throws Exception
	{
		final IRedisDatabaseClient client = RedisClientBuilder.buildDatabaseClient("192.2.9.223", 6379, 0, "");
		
		for(int i=0; i<keys.length; i++)
		{
			keys[i] = "key_" + i;
		}
		
		
		final ExecutorService pool = Executors.newFixedThreadPool(corePoolSize);
		cdl = new CountDownLatch(corePoolSize);

		
		long AllTimeStart = System.nanoTime();
		for (int i = 0; i < corePoolSize; i++)
		{
			pool.execute(new Runnable()
			{
				@Override
				public void run()
				{
					for (int i = 0; i < repeats; i++)
					{
						String[] key = new String[100];

						try
						{
							IDatabaseBatch  batch = client.getNewBatch();
							for (int j = 0; j < key.length; j++)
							{
								key[j] = keys[(int)(Math.random()*keys.length)];
								batch.echo(key[j]);
							}
							batch.execute();
							numberOfAllExecute.incrementAndGet();
						} catch (Exception ex)
						{
							ex.printStackTrace();
							break;
							//System.out.println("error: key=|" + key[0] + "|"+ key[1]+ "|"+ key[2] + "|error: threadID=|" + Thread.currentThread().getId() + "|times=" + i);
						}
//						if (result != null)
//						{
//							for (int j = 0; j < key.length; j++)
//							{
//								if(!result.get(j).equalsIgnoreCase(key[j]))
//								{
//									System.out.println("error: key=|" + key + "|return=" + result + "|error: threadID=|" + Thread.currentThread().getId() + "|times=" + i);
//									break;
//								}
//							}
//							numberOfAllExecute.incrementAndGet();
//						}
//						else
//						{
//							break;
//						}

						try
						{
							Thread.sleep(0);
						} catch (Exception ex)
						{
							ex.printStackTrace();
						}
					}
					cdl.countDown();
				}

			});
		}

		pool.shutdown();
		cdl.await();
		
		long allTime = (System.nanoTime() - AllTimeStart)/1000000;
		logger.info("Program execute AllTime=" + allTime + "ms");
		logger.info("AllTimes=" + numberOfAllExecute.get());
		logger.info("TPS=" + (int)(((double)numberOfAllExecute.get()/(double)allTime)*1000) + " /s");

		client.quit();
	}

}
