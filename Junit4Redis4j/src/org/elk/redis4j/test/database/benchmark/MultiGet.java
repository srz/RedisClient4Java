package org.elk.redis4j.test.database.benchmark;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import org.elk.redis4j.api.database.IRedisDatabaseClient;
import org.elk.redis4j.impl.util.LogUtil;
import org.elk.redis4j.test.Helper;


public class MultiGet
{
	private final static Logger logger = LogUtil.getLogger(MultiGet.class.getName());

	static int repeats = 200;// 每个线程循环执行的次数
	static int corePoolSize = 200;// 测试程序启动的工作线程数

	static CountDownLatch cdl;
	static AtomicInteger numberOfAllExecute = new AtomicInteger(0);
	static List<String> keys = new ArrayList<String>(20);

	public static void main(String[] args) throws Exception
	{
		final IRedisDatabaseClient client = Helper.getRedisDatabaseClient();

		String value = "_";
		for (int i = 0; i < 2048; i++)
		{
			value += "0";
		}

		for (int i = 0; i < 20; i++)
		{
			keys.add("key_" + i);
			boolean b = client.set("key_" + i, i + value);
			System.out.println(b);
		}
		
		final String[] strArray = keys.toArray(new String[0]);

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
						List<String> result = null;
						try
						{
							//IDatabaseBatch batch = client.getNewBatch();
							//for (int j = 0; j < 20; j++)
							{
								//batch.get("key_" + j);
//								String b = client.get("key_" + j);
//								if (b != null)
//									numberOfAllExecute.incrementAndGet();
//								else
//								{
//									logger.info("error=========");
//								}
							}
							//batch.execute();
							//numberOfAllExecute.addAndGet(20);
							
							result = client.multipleGet(strArray);
//							for(String s : result)
//							{
//								logger.info(s);
//							}
							//numberOfAllExecute.incrementAndGet();
							if (result != null)
								numberOfAllExecute.incrementAndGet();
							else
							{
								logger.info("error=========");
							}
						}
						catch (Exception ex)
						{
							ex.printStackTrace();
						}

						try
						{
							Thread.sleep(0);
						}
						catch (Exception ex)
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

		long allTime = (System.nanoTime() - AllTimeStart) / 1000000;
		logger.info("Program execute AllTime=" + allTime + "ms");
		logger.info("AllTimes=" + numberOfAllExecute.get());
		logger.info("TPS=" + (int) (((double) numberOfAllExecute.get() / (double) allTime) * 1000) + " /s");

		client.quit();
	}

}
