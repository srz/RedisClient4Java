package com.handinfo.redis4j.test.database;

import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

import com.handinfo.redis4j.api.database.IDatabaseBatch;
import com.handinfo.redis4j.api.database.IRedisDatabaseClient;
import com.handinfo.redis4j.impl.util.LogUtil;
import com.handinfo.redis4j.test.Helper;

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
					long start = System.currentTimeMillis();
					
					IDatabaseBatch batch = client.getNewBatch();
					
					for(int i=0; i<100; i++)
					{
						batch.echo(String.valueOf(i));
					}

					batch.execute();

					
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
