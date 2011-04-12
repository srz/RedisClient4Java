package com.handinfo.redis4j.test.database;

import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

import com.handinfo.redis4j.api.database.IRedisDatabaseClient;
import com.handinfo.redis4j.impl.RedisClientBuilder;
import com.handinfo.redis4j.impl.database.RedisDatabaseClient;
import com.handinfo.redis4j.impl.transfers.handler.ReconnectNetworkHandler;
import com.handinfo.redis4j.impl.util.Log;
import com.handinfo.redis4j.test.Helper;

public class SimpleTest
{
	private final static Logger logger = (new Log(SimpleTest.class.getName())).getLogger();
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
					logger.info(String.valueOf(b));
					logger.info(client.get("qqq"));
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
		//Thread.sleep(1000000);
		logger.info("thread " + t.getName() + " is finished");

		client.quit();
	}

}
