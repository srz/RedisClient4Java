package com.handinfo.redis4j.test;

import java.util.concurrent.CountDownLatch;

import com.handinfo.redis4j.api.database.IRedisDatabaseClient;
import com.handinfo.redis4j.impl.database.RedisDatabaseClient;

public class SimpleTest
{
	private static CountDownLatch latch = new CountDownLatch(1);

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{
		//final IRedis4j client = new Redis4jClient("192.2.9.223", 6379, 0, 60, 60);
		final IRedisDatabaseClient client = new RedisDatabaseClient("192.2.9.223", 6379, 0, 60, 60);
		// System.out.println("------------");
		// client.getConnection().echo("xxx");
		Thread t = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				// try
				// {
				// //client.getStrings().set("qqq", "cxc");
				// System.out.println(client.getConnection().echo("xxxx"));
				// System.out.println(client.getConnection().echo("yyyy"));
				// }
				// catch (Exception e)
				// {
				// e.printStackTrace();
				// }
				//
//				try
//				{
//					System.out.println("1......." + client.getTransactions().multi());
//					System.out.println("2......." + client.getStrings().incr("aaa"));
//					System.out.println("3......." + client.getKeys().keys("*"));
//
//				}
//				catch (Exception e)
//				{
//					e.printStackTrace();
//				}
				
				try
				{
					System.out.println("1.......---" + client.append("ppp", "zzzzzz"));


				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

				System.out.println("4.......");
				latch.countDown();
			}
		});
		t.setName("srz");
		t.start();
		System.out.println("run.......");

		latch.await();
		System.out.println("thread srz is over");
//		for (String s : client.getTransactions().exec())
//		{
//			System.out.println("5......." + s);
//		}

		// Thread.sleep(20000);
		System.out.println("sleep is over");
		// try
		// {
		// System.out.println("---" + client.getStrings().get("qqq"));
		// }
		// catch (Exception e)
		// {
		// }
		client.quit();
	}

}
