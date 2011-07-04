package org.elk.redis4j.test.database;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

import org.elk.redis4j.api.database.IDatabaseBatch;
import org.elk.redis4j.api.database.IRedisDatabaseClient;
import org.elk.redis4j.impl.util.LogUtil;
import org.elk.redis4j.test.Helper;


public class BatchTest
{
	private final static Logger logger = LogUtil.getLogger(BatchTest.class.getName());
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
					//long start = System.currentTimeMillis();
					
					IDatabaseBatch batch = client.getNewBatch();
					
					//Map<String, String> map = new HashMap<String, String>();
					for(int i=0; i<1000; i++)
					{
						batch.set(String.valueOf(i), String.valueOf(i));
						//client.set(String.valueOf(i), String.valueOf(i));
						//map.put(String.valueOf(i), String.valueOf(i));
						
					}

					
					long start = System.currentTimeMillis();
					
					batch.execute();
					//client.multipleSet(map);

					
					logger.info(String.valueOf(System.currentTimeMillis()-start));
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
		logger.info("thread " + t.getName() + " is finished");


		client.quit();
	}

}
