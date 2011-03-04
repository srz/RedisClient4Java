package com.handinfo.redis4j.test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import com.handinfo.redis4j.api.IRedis4j;
import com.handinfo.redis4j.impl.Redis4jClient;

public class Test
{
	static AtomicInteger ai = new AtomicInteger();
	static long start = System.currentTimeMillis();
	static CountDownLatch cdl;
	static int repeats = 400;
	static BlockingQueue<String> keys = new LinkedBlockingQueue<String>(300000);

	public static void main(String[] args) throws InterruptedException
	{
		
		String s = "";
		for (int i = 0; i < 3000; i++)
		{
			s += "0";
		}
		final String tmp = s;
		
		for(int i=0; i<300000; i++)
		{
			keys.put("key_" + i);
		}
		
		final IRedis4j client = new Redis4jClient("127.0.0.1", 6379, 10, 10);
		// System.out.println(client.set("testKey", s));
		//		
		int corePoolSize = 300;
		final ExecutorService pool = Executors.newFixedThreadPool(corePoolSize);
		cdl = new CountDownLatch(corePoolSize);

		long start = System.nanoTime();
		
		for (int i = 0; i < corePoolSize; i++)
		{
			pool.execute(new Runnable()
			{
				@Override
				public void run()
				{
					for (int i = 0; i < repeats; i++)
					{
						String value = String.valueOf(System.currentTimeMillis()) + tmp;
						String key = null;
						try
						{
							key = keys.take();
						} catch (InterruptedException e1)
						{
							e1.printStackTrace();
						}
						boolean result = client.getStrings().set(key, value);
						//String b = client.get(key);
						if (!result)
							System.out.println(key);
						else
							ai.incrementAndGet();
						
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
		
		System.out.println("TPS=" + ai.get()/((System.nanoTime()-start)/1000000000));

		client.getConnection().quit();
	}

}
