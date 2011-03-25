package com.handinfo.redis4j.test;

import java.util.concurrent.CountDownLatch;

import com.handinfo.redis4j.api.IRedis4j;
import com.handinfo.redis4j.impl.Redis4jClient;

public class SimpleTest
{
	private static CountDownLatch latch = new CountDownLatch(1);

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{
		final IRedis4j client = new Redis4jClient("192.2.9.223", 6379, 10, 3, 3);
		// System.out.println("------------");
		// client.getConnection().echo("xxx");
		Thread t = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					client.getStrings().set("qqq", "cxc");
				}
				catch (Exception e)
				{
					 e.printStackTrace();
				}
//
//				try
//				{
//					client.getConnection().echo("www");
//				}
//				catch (Exception e)
//				{
//					// e.printStackTrace();
//				}

				latch.countDown();
			}
		});
		t.setName("srz");
		t.start();
		System.out.println("run.......");

		latch.await();
		System.out.println("thread srz is over");

		//Thread.sleep(20000);
		System.out.println("sleep is over");
		try
		{
			System.out.println("---" + client.getStrings().get("qqq"));
		}
		catch (Exception e)
		{
		}
		client.getConnection().quit();
	}

}
