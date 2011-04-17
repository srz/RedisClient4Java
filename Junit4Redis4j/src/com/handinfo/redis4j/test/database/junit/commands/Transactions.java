package com.handinfo.redis4j.test.database.junit.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import com.handinfo.redis4j.api.database.IDatabaseTransaction;
import com.handinfo.redis4j.api.database.IRedisDatabaseClient;
import com.handinfo.redis4j.test.Helper;
import com.handinfo.redis4j.test.database.junit.RedisCommandTestBase;

public class Transactions extends RedisCommandTestBase
{
	@Test
	public void multi()
	{
		IDatabaseTransaction trans = client.getNewTransaction();

		trans.setsAdd("foo", "a");
		trans.setsAdd("foo", "b");
		trans.setsCard("foo");

		Boolean result = trans.commit();
		assertEquals(true, result);

		List<String> expected = new ArrayList<String>(2);
		expected.add("a");
		expected.add("b");

		assertEquals(expected, client.setsMembers("foo"));
	}

	@Test
	public void testSingleClientCAS() throws InterruptedException
	{
		int corePoolSize = 100;
		final int repeats = 100;
		final ExecutorService pool = Executors.newFixedThreadPool(corePoolSize);
		final CountDownLatch cdl = new CountDownLatch(corePoolSize);
		final AtomicInteger numberOfAllExecute = new AtomicInteger(0);

		client.set("foo", numberOfAllExecute.toString());

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
							} else
							{
								client.set("foo", "0");
							}
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

		logger.info("numberOfAllExecute=" + numberOfAllExecute.toString());
		assertEquals(numberOfAllExecute.toString(), client.get("foo"));
	}

	@Test
	public void testMultiClientCAS() throws InterruptedException
	{
		int corePoolSize = 100;
		final int repeats = 100;
		final ExecutorService pool = Executors.newFixedThreadPool(corePoolSize);
		final CountDownLatch cdl = new CountDownLatch(corePoolSize);
		final AtomicInteger numberOfAllExecute = new AtomicInteger(0);

		client.set("foo", numberOfAllExecute.toString());

		for (int i = 0; i < corePoolSize; i++)
		{
			pool.execute(new Runnable()
			{
				@Override
				public void run()
				{
					IRedisDatabaseClient cli = Helper.getRedisDatabaseClient();
					IDatabaseTransaction trans = cli.getNewTransaction();
					for (int i = 0; i < repeats; i++)
					{
						try
						{
							trans.watch("foo");
							String foo = cli.get("foo");
							if (foo != null)
							{
								int newValue = Integer.valueOf(foo) + 1;
								trans.set("foo", String.valueOf(newValue));
								if (trans.commit())
								{
									numberOfAllExecute.incrementAndGet();
								}
								else
								{
									logger.info("commit false!");
								}
							} else
							{
								client.set("foo", "0");
							}
						}
						catch (Exception ex)
						{
							ex.printStackTrace();
						}
					}
					cli.quit();
					cdl.countDown();
				}
			});
		}

		pool.shutdown();
		cdl.await();

		logger.info("numberOfAllExecute=" + numberOfAllExecute.toString());
		assertEquals(numberOfAllExecute.toString(), client.get("foo"));
	}

	@Test
	public void unwatch() throws InterruptedException
	{
		final CountDownLatch cdl = new CountDownLatch(1);
		IDatabaseTransaction trans = client.getNewTransaction();
		trans.watch("mykey");
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				IDatabaseTransaction transNew = client.getNewTransaction();
				transNew.watch("mykey");
				transNew.set("mykey", "newThread");
				transNew.commit();

				cdl.countDown();
			}
		}).start();

		Thread.sleep(3000);
		client.set("mykey", "trans");
		trans.unwatch();

		cdl.await();
		
		assertEquals("newThread", client.get("mykey"));
	}
}