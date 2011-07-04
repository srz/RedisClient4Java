package org.elk.redis4j.test.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

import org.elk.redis4j.api.cache.IRedisCacheClient;
import org.elk.redis4j.impl.util.LogUtil;
import org.elk.redis4j.test.Helper;


public class SimpleCacheTest
{
	private final static Logger logger = LogUtil.getLogger(SimpleCacheTest.class.getName());
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
					Map<String, User> map = new HashMap<String, User>();
					map.put("f1", new User(11, "f1f1f1", 0));
					map.put("f2", new User(22, "f2ff2f2f2", 0));
					boolean b = client.hashesMultipleSet("qqq", map);
					logger.info("===" + String.valueOf(b));
					
					map.clear();
					map = client.hashesGetAll("qqq");
					logger.info("===oooo");
					
//					User user = new User();
//					user.setId(11);
//					user.setName("qqq");
//					boolean b = client.set("qqq", user);
//					logger.info(String.valueOf(b));
//
//					user.setId(22);
//					user.setName("qqw1");
//					b = client.set("qqw1", user);
//					logger.info(String.valueOf(b));
//
//					//List<User> result = client.multipleGet("qqq", "xx", "qqw1", "yy");
//					List<User> result = client.multipleGet("sdsd");
//					for (User cacheUser : result)
//					{
//						if (cacheUser != null)
//							logger.info("=======" + cacheUser.getName());
//						else
//							logger.info("=======null");
//					}
//
//					int i = client.del("qqw1", "qqq");
//					logger.info("+++++" + String.valueOf(i));

					// User s = client.get("qqq");
					// logger.info(s.getName());

					// Integer r = client.get("qqw");
					// int b = client.listLeftPush("list_a", "aaaa");
					// System.out.println(b);
					// b = client.listLeftPush("list_a", "bbbb");
					// System.out.println(b);
					// b = client.listLeftPush("list_a", "cccc");
					// System.out.println(b);
					// b = client.listLeftPush("list_a", "dddd");
					// System.out.println(b);
					// b = client.listLeftPush("list_a", "eeee");
					// System.out.println(b);
					// String[] s = client.listBlockLeftPop(1, "list_a");
					// System.out.println("==" + s[0]);
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
		// Thread.sleep(50000000);
		logger.info("thread " + t.getName() + " is finished");

		client.quit();
	}

}
