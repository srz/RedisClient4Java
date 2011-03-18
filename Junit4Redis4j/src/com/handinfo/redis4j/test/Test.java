package com.handinfo.redis4j.test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.handinfo.redis4j.api.IRedis4j;
import com.handinfo.redis4j.impl.Redis4jClient;

public class Test
{
	static int repeats = 200;//每个线程循环执行的次数
	static int corePoolSize = 500;//测试程序启动的工作线程数
	static int connectionPoolSize = 19;//客户端连接池数
	static int dataLength = 100;//数据长度,单位byte
	
	static CountDownLatch cdl;
	static AtomicInteger numberOfAllExecute = new AtomicInteger(0);
	static BlockingQueue<String> keys = new LinkedBlockingQueue<String>(300000);

	public static void main(String[] args) throws InterruptedException
	{
		
		String s = "";
		for (int i = 0; i < dataLength; i++)
		{
			s += "0";
		}
		final String tmp = s;
		
		for(int i=0; i<300000; i++)
		{
			keys.put("key_" + i);
		}
		
		final IRedis4j client = new Redis4jClient("192.2.9.223", 6379, connectionPoolSize, 10);
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
						String value = tmp;//String.valueOf(System.currentTimeMillis()) + tmp;
						String key = null;
						try
						{
							key = keys.take();
						} catch (InterruptedException e1)
						{
							e1.printStackTrace();
						}

						
						String b = client.getStrings().get(key);
						if (b == null)
							System.out.println(key);
						else
						{
							numberOfAllExecute.incrementAndGet();
						}
						
//						boolean result = client.getStrings().set(key, value);
//						if (!result)
//							System.out.println(key);
//						else
//						{
//							numberOfAllExecute.incrementAndGet();
//						}
						
						try
						{
							keys.put(key);
						} catch (InterruptedException e1)
						{
							e1.printStackTrace();
						}
						
						try
						{
							Thread.sleep(0);
						} catch (InterruptedException e)
						{
							e.printStackTrace();
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
		System.out.println("TPS=" + numberOfAllExecute.get()/(allTime/1000));

		//client.getServer().flushall();
		client.getConnection().quit();
	}

}
