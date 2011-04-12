package com.handinfo.redis4j.test.cache;

import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

import com.handinfo.redis4j.api.cache.IRedisCacheClient;
import com.handinfo.redis4j.impl.util.Log;
import com.handinfo.redis4j.test.Helper;

public class SimpleCacheTest
{
	private final static Logger logger = (new Log(SimpleCacheTest.class.getName())).getLogger();
	private static CountDownLatch latch = new CountDownLatch(1);

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{
		final IRedisCacheClient client = Helper.getRedisCacheClient();

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
					logger.info(String.valueOf(b));
					User s = client.get("qqq");
					logger.info(s.getName());
					
					Integer r = client.get("qqw");
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
		logger.info("run.......");
		t.start();

		latch.await();
		//Thread.sleep(50000000);
		logger.info("thread " + t.getName() + " is finished");

		client.quit();
	}

}
