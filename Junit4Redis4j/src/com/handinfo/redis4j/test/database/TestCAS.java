package com.handinfo.redis4j.test.database;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import com.handinfo.redis4j.api.RedisResponseType;
import com.handinfo.redis4j.api.database.IDatabaseBatch;
import com.handinfo.redis4j.api.database.IDatabaseTransaction;
import com.handinfo.redis4j.api.database.IRedisDatabaseClient;
import com.handinfo.redis4j.impl.RedisClientBuilder;
import com.handinfo.redis4j.impl.database.RedisDatabaseClient;
import com.handinfo.redis4j.test.Helper;

public class TestCAS
{
	/**
	 * @param args
	 * @throws InterruptedException
	 * @throws Exception
	 */
	public static void main(String[] args) throws InterruptedException
	{
		final IRedisDatabaseClient client = Helper.getRedisDatabaseClient();

		int corePoolSize = 1000;
		final int repeats = 1000;
		final ExecutorService pool = Executors.newFixedThreadPool(corePoolSize);
		final CountDownLatch cdl = new CountDownLatch(corePoolSize);
		final AtomicInteger numberOfAllExecute = new AtomicInteger(0);

		try
		{
			client.flushAllDB();

			client.set("foo", "0");
		}
		catch (Exception ex)
		{
			//ex.printStackTrace();
		}

		for (int i = 0; i < corePoolSize; i++)
		{
			pool.execute(new Runnable()
			{
				@Override
				public void run()
				{
					IDatabaseTransaction trans = client.getNewTransaction();
					for (int i = 0; i < repeats; i++)
					{
						try
						{
							trans.watch("foo");
							String foo = client.get("foo");
							if (foo != null)
							{
								int newValue = Integer.valueOf(foo) + 1;
								trans.set("foo", String.valueOf(newValue));
								if (trans.commit())
								{
									numberOfAllExecute.incrementAndGet();
								}
							}
							else
							{
								client.set("foo", "0");
							}
						}
						catch (Exception ex)
						{
							//ex.printStackTrace();
						}


						// try
						// {
						// trans.watch("foo");
						// }
						// catch (Exception ex)
						// {
						// //System.out.println("trans.set 0000");
						// }
						//						
						// int newValue = 0;
						// try
						// {
						// newValue = Integer.valueOf(client.get("foo")) + 1;
						// }
						// catch (Exception ex)
						// {
						// //System.out.println("trans.set 1111");
						// }
						//						
						// try
						// {
						// trans.set("foo", String.valueOf(newValue));
						// if (trans.commit())
						// {
						// numberOfAllExecute.incrementAndGet();
						// }
						// }
						// catch (Exception ex)
						// {
						// //ex.printStackTrace();
						// //trans.discard();
						// //System.out.println("trans.set 2222");
						// }
					}
					cdl.countDown();
				}
			});
		}

		pool.shutdown();
		cdl.await();

		client.quit();
	}

}
