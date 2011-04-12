package com.handinfo.redis4j.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

import com.handinfo.redis4j.impl.util.Log;

public class TestCondition
{
	private final Logger logger = (new Log(TestCondition.class.getName())).getLogger();
	
	private final ReentrantLock lock = new ReentrantLock();
	private final Condition condition = lock.newCondition();
	private int total = 0;
	private Thread thread = null;

	public void abc(int ii) throws InterruptedException
	{
		lock.lock();
		if (ii == 1)
		{
			while (true)
			{
				if (thread != null)
				{
					if (!thread.equals(Thread.currentThread()))
					{
						condition.await();
					} else
					{
						break;
					}
				} else
				{
					thread = Thread.currentThread();
					break;
				}
			}
		}

		// System.out.println(ii);
		// if(ii == 989)
		// {
		// System.out.println("Thread.sleep");
		// Thread.sleep(1000*60*60);
		// }

		// total++;
		if (ii == 2)
		{
			total = 0;
			thread = null;

			// if (ii == 2)
			condition.signal();
		}
		logger.info("finish lock==" + Thread.currentThread().getName() + " total=" + total);
		lock.unlock();
		
//		final CountDownLatch cdl_1 = new CountDownLatch(1);
//		new Thread(new Runnable(){
//
//			@Override
//			public void run()
//			{
//				try
//				{
//					Thread.sleep(1);
//				} catch (InterruptedException e)
//				{
//					e.printStackTrace();
//				}
//				cdl_1.countDown();
//			}}).start();
//		cdl_1.await();
	}

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException
	{
		final AtomicInteger tt = new AtomicInteger(0);

		final TestCondition tc = new TestCondition();

		int corePoolSize = 2;
		final CountDownLatch cdl = new CountDownLatch(corePoolSize);
		final ExecutorService pool = Executors.newFixedThreadPool(corePoolSize);
		for (int i = 0; i < corePoolSize; i++)
		{
			pool.execute(new Runnable()
			{

				@Override
				public void run()
				{
					try
					{
						// for (int j = 0; j < 5; j++)
						// {
						// tc.abc(tt.getAndIncrement());
						// }
						tc.abc(1);
						tc.abc(3);
						tc.abc(4);
						tc.abc(2);
						
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}

					cdl.countDown();
				}
			});
		}

		pool.shutdown();
		cdl.await();

		// Thread.sleep(3000);
		// tc.condition.signal();
	}
}
