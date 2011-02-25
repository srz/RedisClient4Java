package com.handinfo.redis4j.test;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Test
{
	public static void main(String[] args)
	{
		final BlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>(3);
		final Random random = new Random();

		queue.clear();
		
		class Producer implements Runnable
		{
			@Override
			public void run()
			{
				try
				{
					Thread.sleep(1000);
				}
				catch (InterruptedException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				while (true)
				{
					try
					{
						int i = random.nextInt(100);
						queue.put(i);// 当队列达到容量时候，会自动阻塞的
						System.out.println("+++++++++++++++++++++++queue.put===" + queue.size());
						if (queue.size() == 3)
						{
							System.out.println("full");
						}
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}
		}

		class Consumer implements Runnable
		{
			@Override
			public void run()
			{
				while (true)
				{
					try
					{
						Thread.sleep(3000);
						int i = queue.take();// 当队列为空时，也会自动阻塞
						System.out.println("----------take===" + queue.size());
						
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}
		}

		new Thread(new Producer()).start();
		new Thread(new Consumer()).start();
	}

}
