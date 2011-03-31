package com.handinfo.redis4j.test;

import java.util.concurrent.CountDownLatch;

import com.handinfo.redis4j.api.RedisResponseType;
import com.handinfo.redis4j.api.database.IDatabaseBatch;
import com.handinfo.redis4j.api.database.IRedisDatabaseClient;
import com.handinfo.redis4j.impl.database.RedisDatabaseClient;

public class BatchTest
{
	private static CountDownLatch latch = new CountDownLatch(1);

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{
		final IRedisDatabaseClient client = new RedisDatabaseClient("192.2.9.223", 6379, 10);

		System.out.println(RedisResponseType.BulkReplies.getValue());
		
		Thread t = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					long start = System.currentTimeMillis();
					
					IDatabaseBatch batch = client.getNewBatch();
					
					for(int i=0; i<10; i++)
					{
						batch.echo(String.valueOf(i));
					}

					batch.execute();

					
					System.out.println(System.currentTimeMillis()-start);
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
		System.out.println("thread " + t.getName() + " is finished");


		client.quit();
	}

}
