package org.elk.redis4j.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import org.elk.redis4j.api.database.IRedisDatabaseClient;
import org.elk.redis4j.impl.util.LogUtil;


public class DataCheck
{
	private final static Logger logger = LogUtil.getLogger(DataCheck.class.getName());
	
	static int repeats = 200;//每个线程循环执行的次数
	static int corePoolSize = 1500;//测试程序启动的工作线程数
	
	static CountDownLatch cdl;
	static AtomicInteger numberOfAllExecute = new AtomicInteger(0);
	static String[] keys = new String[300000];

	public static void main(String[] args) throws Exception
	{
		final IRedisDatabaseClient client = Helper.getRedisDatabaseClient();
		
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
							logger.info("error: key=|" + key + "|return=" + result + "|error: threadID=|" + Thread.currentThread().getId() + "|times=" + i);
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
		logger.info("Program execute AllTime=" + allTime + "ms");
		logger.info("AllTimes=" + numberOfAllExecute.get());
		logger.info("TPS=" + (int)(((double)numberOfAllExecute.get()/(double)allTime)*1000) + " /s");

		client.quit();
	}

}
