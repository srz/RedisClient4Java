package org.elk.redis4j.test.database.junit;

import java.util.List;
import java.util.logging.Logger;

import org.elk.redis4j.api.database.IRedisDatabaseClient;
import org.elk.redis4j.impl.util.LogUtil;
import org.elk.redis4j.test.Helper;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;


public abstract class RedisCommandTestBase extends Assert
{
	protected static final Logger logger = LogUtil.getLogger(RedisCommandTestBase.class.getName());
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
		logger.info("tearDown flushCurrentDB  = " + client.flushAllDB());
		client.quit();
	}

	@Before
	public void setUp() throws Exception
	{
		logger.info("setUp flushCurrentDB = " + client.flushAllDB());
	}

	@After
	public void tearDown() throws Exception
	{
	}

	protected void assertEquals(String[] expected, String[] actual)
	{
		for (int i = 0; i < expected.length; i++)
		{
			assertEquals(null, expected[i], actual[i]);
		}
	}
	
	protected void assertEquals(List<String> expected, List<String> actual)
	{
		for (int i = 0; i < expected.size(); i++)
		{
			assertEquals(null, expected.get(i), actual.get(i));
		}
	}
}