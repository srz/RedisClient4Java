package com.handinfo.redis4j.test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TestCondition
{
	private final ReentrantLock lock = new ReentrantLock();
	private final Condition condition = lock.newCondition();
	private int total = 0;
	private Thread thread = null;

	public void abc(int ii) throws InterruptedException
	{
		lock.lock();
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
		
		//System.out.println(ii);
		if(ii == 989)
		{
			System.out.println("Thread.sleep");
			Thread.sleep(1000*60*60);
		}
		
		total++;
		if (total == 5)
		{
			total = 0;
			thread = null;
			
			condition.signal();
		}
		//System.out.println("finish lock==" + Thread.currentThread().getName() + " total=" + total);
		lock.unlock();
	}

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException
	{
		final AtomicInteger tt = new AtomicInteger(0);
		
		final TestCondition tc = new TestCondition();

		for (int i = 0; i < 200; i++)
		{
			new Thread(new Runnable()
			{

				@Override
				public void run()
				{
					try
					{
						for (int j = 0; j < 5; j++)
						{
							tc.abc(tt.getAndIncrement());
						}
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}, "Thread#" + i).start();
		}
		
		Thread.sleep(3000);
		tc.condition.signal();
	}
}
