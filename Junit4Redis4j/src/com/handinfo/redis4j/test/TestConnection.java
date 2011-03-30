package com.handinfo.redis4j.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.handinfo.redis4j.api.database.IRedisDatabaseClient;
import com.handinfo.redis4j.impl.database.RedisDatabaseClient;

public class TestConnection
{
	private static IRedisDatabaseClient client;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		client = new RedisDatabaseClient("192.2.9.223", 6379, 10);
		//System.out.println("connect to RedisServer... back=" + client.connect());;
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
		client.quit();
	}

//	@Before
//	public void setUp() throws Exception
//	{
//		System.out.println("3");
//	}
//
//	@After
//	public void tearDown() throws Exception
//	{
//		System.out.println("4");
//	}

	@Test
	public void testEcho() throws Exception
	{
		String str = "i am server response echo!";
		assertEquals("there is a bug in here!", str, client.echo("i am server response echo!"));
	}

	@Test
	public void testPing() throws Exception
	{
		assertTrue(client.ping());
	}

//	@Test
//	public void testSelect()
//	{
//		assertTrue(client.select(0));
//	}
}
