package com.handinfo.redis4j.test.database.junit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;

import com.handinfo.redis4j.api.database.IRedisDatabaseClient;
import com.handinfo.redis4j.test.Helper;

public abstract class RedisCommandTestBase extends Assert
{
	protected static IRedisDatabaseClient client;

	public RedisCommandTestBase()
	{
		super();
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		client = Helper.getRedisDatabaseClient();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
		System.out.println("tearDown flushCurrentDB  = " + client.flushAllDB());
		client.quit();
	}

	@Before
	public void setUp() throws Exception
	{
		System.out.println("setUp flushCurrentDB = " + client.flushAllDB());
	}

	@After
	public void tearDown() throws Exception
	{
	}
	
	protected void assertEquals(String[] expected, String[] actual )
	 {
	  for(int i = 0; i < expected.length; i++)
	  {
	   assertEquals(null, expected[i], actual[i]);
	  }
	 }
}