package com.handinfo.redis4j.test.cache;

import java.util.concurrent.CountDownLatch;

import com.handinfo.redis4j.api.cache.IRedisCacheClient;
import com.handinfo.redis4j.impl.RedisClientBuilder;
import com.handinfo.redis4j.test.User;

public class SimpleCacheTest
{
	private static CountDownLatch latch = new CountDownLatch(1);

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{
		RedisClientBuilder builder = new RedisClientBuilder();
		builder.addSharding("192.2.9.223", 6380, 1, "");
		//builder.addSharding("192.2.9.223", 6379, 2, "");
		//builder.addSharding("192.2.9.223", 6380, 3, "");
		final IRedisCacheClient client = builder.buildCacheClient();

		Thread t = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					User user = new User();
					user.setId(11);
					user.setName("srz");
					boolean b = client.set("qqq", user);
					System.out.println(b);
					User s = client.get("qqq");
					System.out.println(s.getName());
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
		//Thread.sleep(50000000);
		System.out.println("thread " + t.getName() + " is finished");

		client.quit();
	}

}
