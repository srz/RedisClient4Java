package com.handinfo.redis4j.test;

import com.handinfo.redis4j.impl.Redis4jClient;

public class TestConnection
{

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception
	{
		Redis4jClient client = new Redis4jClient("127.0.0.1", 6379);
		if (client.connect())
		{
			boolean isAuth = client.auth("pwd");
			System.out.println("Server back isAuth = " + isAuth);
			
			boolean isPing = client.ping();
			System.out.println("Server back isPing = " + isPing);
			
			boolean isSelect = client.select(0);
			System.out.println("Server back isSelect = " + isSelect);
			
			String echo = client.echo("i am server response echo!");
			System.out.println("Server back echo = " + echo==null ? "there is a bug in here!" : echo);

			boolean isQuit = client.quit();
			System.out.println("Server back isQuit = " + isQuit);
		}
	}

}
