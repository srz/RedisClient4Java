package com.handinfo.redis4j.test;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import com.handinfo.redis4j.api.Batch;
import com.handinfo.redis4j.api.IRedis4j;
import com.handinfo.redis4j.api.RedisCommand;
import com.handinfo.redis4j.api.RedisResponseType;
import com.handinfo.redis4j.impl.Redis4jClient;

public class BatchTest
{
	private static CountDownLatch latch = new CountDownLatch(1);

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{
		final IRedis4j client = new Redis4jClient("192.2.9.223", 6379, 10, 30, 30);

		System.out.println(RedisResponseType.BulkReplies.getValue());
		
		Thread t = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					long start = System.currentTimeMillis();
//					for(int i=0; i<10000; i++)
//					{
//						client.getConnection().echo(String.valueOf(i));
//					}
					
					Batch batch = new Batch();
//					for(int i=0; i<10; i++)
//					{
//						//batch.addEcho(String.valueOf(i));
//						batch.addCommand(RedisCommand.ECHO, String.valueOf(i));
//					}
					batch.addCommand(RedisCommand.MULTI);
					batch.addCommand(RedisCommand.MULTI);
					batch.addCommand(RedisCommand.INCR, "abcde");
					batch.addCommand(RedisCommand.KEYS, "*");
					batch.addCommand(RedisCommand.EXEC);
					batch.addCommand(RedisCommand.EXEC);
					
					ArrayList<String> result = client.batch(batch);
//					for(String s : result)
//					{
//						System.out.println(s);
//					}
					
					System.out.println(System.currentTimeMillis()-start);
				}
				catch (Exception e)
				{
					 e.printStackTrace();
				}
				
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
