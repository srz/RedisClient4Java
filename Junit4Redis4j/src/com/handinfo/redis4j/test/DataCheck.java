package com.handinfo.redis4j.test;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import com.handinfo.redis4j.api.IRedis4j;
import com.handinfo.redis4j.impl.Redis4jClient;

public class DataCheck
{
	static int repeats = 2000;//每个线程循环执行的次数
	static int corePoolSize = 1000;//测试程序启动的工作线程数
	static int connectionPoolSize = 19;//客户端连接池数
	static int dataLength = 100;//数据长度,单位byte
	
	static CountDownLatch cdl;
	static AtomicInteger numberOfAllExecute = new AtomicInteger(0);
	//static BlockingQueue<String> keys = new LinkedBlockingQueue<String>(300000);
	static String[] keys = new String[300000];

	public static void main(String[] args) throws Exception
	{
		String s = "";
		for (int i = 0; i < dataLength; i++)
		{
			s += "0";
		}
		final String tmp = s;
		
		final Redis4jClient client = new Redis4jClient("192.168.1.102", 6379, connectionPoolSize, 10);
		
		for(int i=0; i<keys.length; i++)
		{
			keys[i] = "key_" + i;
			//client.getStrings().set(keys[i], keys[i]);
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
						String value = tmp;//String.valueOf(System.currentTimeMillis()) + tmp;
						String key = keys[(int)(Math.random()*keys.length)];
						
//						//读
//						String b = "";
//						try
//						{
//							b = client.getStrings().get(key);
//						} catch (InterruptedException e2)
//						{
//							// TODO Auto-generated catch block
//							e2.printStackTrace();
//						}
//						if (b == null || !b.equalsIgnoreCase(key))
//							System.out.println("key=|" + key + "|return=" + b);
//						else
//						{
//							numberOfAllExecute.incrementAndGet();
//						}
						
						
						
						
//						
						
						String b = "";;
						try
						{
							b = client.getConnection().echo(key);
						} catch (Exception e2)
						{
							e2.printStackTrace();
						}
						if (!b.equalsIgnoreCase(key))
						{
							//System.out.println("error: threadID=|" + Thread.currentThread().getId() + "|times=" + i);
							System.out.println("error: key=|" + key + "|return=" + b + "|error: threadID=|" + Thread.currentThread().getId() + "|times=" + i);
							break;
						}
						else
						{
							numberOfAllExecute.incrementAndGet();
						}
						
						
						//if(i%2000 == 0)
							//System.out.println("threadID=|" + Thread.currentThread().getId() + "|times=" + i);
						
						try
						{
							Thread.sleep(0);
						} catch (Exception e)
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
		System.out.println("TPS=" + (int)(((double)numberOfAllExecute.get()/(double)allTime)*1000) + " /s");

		//client.getServer().flushall();
		client.getConnection().quit();
	}

}
