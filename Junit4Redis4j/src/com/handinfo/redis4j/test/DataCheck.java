package com.handinfo.redis4j.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import com.handinfo.redis4j.api.database.IRedisDatabaseClient;
import com.handinfo.redis4j.impl.RedisClientBuilder;
import com.handinfo.redis4j.impl.database.RedisDatabaseClient;

public class DataCheck
{
	static int repeats = 200;//每个线程循环执行的次数
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
						String key = keys[(int)(Math.random()*keys.length)];

						String result = null;;
						try
						{
							result = client.echo(key);
						} catch (Exception ex)
						{
							ex.printStackTrace();
						}
						if (result != null && !result.equalsIgnoreCase(key))
						{
							System.out.println("error: key=|" + key + "|return=" + result + "|error: threadID=|" + Thread.currentThread().getId() + "|times=" + i);
							break;
						}
						else
						{
							numberOfAllExecute.incrementAndGet();
						}

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
		System.out.println("Program execute AllTime=" + allTime + "ms");
		System.out.println("AllTimes=" + numberOfAllExecute.get());
		System.out.println("TPS=" + (int)(((double)numberOfAllExecute.get()/(double)allTime)*1000) + " /s");

		client.quit();
	}

}
