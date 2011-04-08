package com.handinfo.redis4j.test.database;

import java.util.concurrent.CountDownLatch;

import com.handinfo.redis4j.api.database.IRedisDatabaseClient;
import com.handinfo.redis4j.impl.RedisClientBuilder;
import com.handinfo.redis4j.impl.database.RedisDatabaseClient;
import com.handinfo.redis4j.test.Helper;

public class SimpleTest
{
	private static CountDownLatch latch = new CountDownLatch(1);

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{
		final IRedisDatabaseClient client = Helper.getRedisDatabaseClient();

		Thread t = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					boolean b = client.set("qqq", "cxc1");
					System.out.println(b);
					System.out.println(client.get("qqq"));
//					int b = client.listLeftPush("list_a", "aaaa");
//					System.out.println(b);
//					b = client.listLeftPush("list_a", "bbbb");
//					System.out.println(b);
//					b = client.listLeftPush("list_a", "cccc");
//					System.out.println(b);
//					b = client.listLeftPush("list_a", "dddd");
//					System.out.println(b);
//					b = client.listLeftPush("list_a", "eeee");
//					System.out.println(b);
//					String[] s = client.listBlockLeftPop(1, "list_a");
//					System.out.println("==" + s[0]);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

				latch.countDown();
			}
		});
		t.setName("NewThread");
		System.out.println("run.......");
		t.start();

		latch.await();
		Thread.sleep(1000000);
		System.out.println("thread " + t.getName() + " is finished");

		client.quit();
	}

}
