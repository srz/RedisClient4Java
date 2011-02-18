package com.handinfo.redis4j.test;

import com.handinfo.redis4j.api.IRedis4j;
import com.handinfo.redis4j.impl.Redis4j;

public class Test
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		IRedis4j client = new Redis4j("192.2.8.164", 6379);
		if (client.connect())
		{
			if (!client.ping())
			{
				client.quit();
				return;
			}

			System.out.println("服务器返回=" + client.echo("test srz"));

			client.quit();
		}
	}

}
