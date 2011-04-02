package com.handinfo.redis4j.test.benchmark;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import com.handinfo.redis4j.api.database.IDatabaseBatch;
import com.handinfo.redis4j.api.database.IRedisDatabaseClient;
import com.handinfo.redis4j.impl.RedisClientBuilder;
import com.handinfo.redis4j.impl.database.RedisDatabaseClient;

public class BenchmarkOfRedis4j
{
	static int repeats = 40000;// 每个线程循环执行的次数
	static int corePoolSize = 10;// 测试程序启动的工作线程数
	static int dataLength = 1024;// 数据长度,单位byte

	static CountDownLatch latch;
	static AtomicInteger numberOfAllExecute = new AtomicInteger(0);

	static String[] keys = new String[512000];// 500MB内存

	public static void main(String[] args) throws Exception
	{
		String s = "";
		for (int i = 0; i < dataLength; i++)
		{
			s += "0";
		}
		final String tmp = s;

		final IRedisDatabaseClient client = RedisClientBuilder.buildDatabaseClient("192.2.9.223", 6379, 0, "");

		int numOfBatch = 1000;
		IDatabaseBatch batch = client.getNewBatch();
		long st = System.currentTimeMillis();
		for (int i = 0; i < keys.length; i++)
		{
			keys[i] = "key_" + i;
//			if (i != 0)
//			{
//				if (i % numOfBatch != 0 && i < keys.length-1)
//				{
//					batch.set(keys[i], keys[i]);
//				}
//				else
//				{
//					batch.execute();
//				}
//			}
//			else
//			{
//				batch.set(keys[i], keys[i]);
//			}
		}
//		System.out.println(System.currentTimeMillis()- st);
//		System.exit(0);

		final ExecutorService pool = Executors.newFixedThreadPool(corePoolSize);
		latch = new CountDownLatch(corePoolSize);

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
						String value = tmp;// String.valueOf(System.currentTimeMillis()) + tmp;
						String key = keys[(int) (Math.random() * keys.length)];

						if (i % 5 == 0)
						{
							// 写
							boolean result = false;
							try
							{
								result = client.set(key, value);
							}
							catch (Exception e2)
							{
								e2.printStackTrace();
							}
							if (!result)
								System.out.println(key);
							else
							{
								numberOfAllExecute.incrementAndGet();
							}
						} else
						{
							// 读
							String b = "";
							try
							{
								b = client.get(key);
							}
							catch (Exception e2)
							{
								e2.printStackTrace();
							}
							numberOfAllExecute.incrementAndGet();
						}

						try
						{
							Thread.sleep(0);
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
					latch.countDown();
				}

			});
		}

		pool.shutdown();
		latch.await();

		long allTime = (System.nanoTime() - AllTimeStart) / 1000000;
		System.out.println("Program execute AllTime=" + allTime + "ms");
		System.out.println("AllTimes=" + numberOfAllExecute.get());
		System.out.println("TPS=" + (int) (((double) numberOfAllExecute.get() / (double) allTime) * 1000) + " /s");

		client.flushAllDB();
		client.quit();
	}

}
