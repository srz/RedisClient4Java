package com.handinfo.redis4j.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.handinfo.redis4j.api.IRedis4j;
import com.handinfo.redis4j.impl.Redis4jClient;

public class TestConnection
{
	private static IRedis4j client;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		client = new Redis4jClient("192.2.8.164", 6379, 10, 10);
		//System.out.println("connect to RedisServer... back=" + client.connect());;
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
		client.getConnection().quit();
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
	public void testAuth()
	{
		assertTrue(client.getConnection().auth("pwd"));
	}

	@Test
	public void testEcho()
	{
		String str = "i am server response echo!";
		assertEquals("there is a bug in here!", str, client.getConnection().echo("i am server response echo!"));
	}

	@Test
	public void testPing()
	{
		assertTrue(client.getConnection().ping());
	}

//	@Test
//	public void testSelect()
//	{
//		assertTrue(client.select(0));
//	}
}
