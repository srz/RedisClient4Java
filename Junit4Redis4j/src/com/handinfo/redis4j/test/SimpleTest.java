package com.handinfo.redis4j.test;

import com.handinfo.redis4j.api.IRedis4j;
import com.handinfo.redis4j.impl.Redis4jClient;

public class SimpleTest
{

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception
	{
		final IRedis4j client = new Redis4jClient("192.168.1.102", 6379, 1, 10);
		//System.out.println("------------");
		//client.getConnection().echo("xxx");
		System.out.println(client.getConnection().echo("xxx"));
		client.getConnection().quit();
	}

}
