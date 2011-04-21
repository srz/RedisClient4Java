package org.elk.redis4j.test.cache.junit.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.elk.redis4j.api.ListPosition;
import org.elk.redis4j.api.cache.IRedisCacheClient;
import org.elk.redis4j.api.exception.ErrorCommandException;
import org.elk.redis4j.test.Helper;
import org.elk.redis4j.test.cache.junit.RedisCommandTestBase;
import org.junit.Test;


public class Lists extends RedisCommandTestBase
{
	@Test
	public void blpop() throws InterruptedException
	{
		Entry<String, Object> result = client.listBlockLeftPop("foo", 1);
		assertNull(result);

		new Thread(new Runnable()
		{
			public void run()
			{
				try
				{
					IRedisCacheClient cli = Helper.getRedisCacheClient();
					Integer res = cli.listLeftPush("foo", "bar");
					cli.quit();
					assertEquals(1, res.intValue());
				}
				catch (Exception ex)
				{
					fail(ex.getMessage());
				}
			}
		}).start();

		result = client.listBlockLeftPop("foo", 1);
		assertNotNull(result);
		assertEquals("foo", result.getKey());
		assertEquals("bar", result.getValue());
	}
	
	@Test
	public void brpop() throws InterruptedException
	{
		Entry<String, Object> result = client.listBlockRightPop("foo", 1);
		assertNull(result);

		new Thread(new Runnable()
		{
			public void run()
			{
				try
				{
					IRedisCacheClient cli = Helper.getRedisCacheClient();
					Integer res = cli.listLeftPush("foo", "bar");
					cli.quit();
					assertEquals(1, res.intValue());
				}
				catch (Exception ex)
				{
					fail(ex.getMessage());
				}
			}
		}).start();

		result = client.listBlockRightPop("foo", 1);
		assertNotNull(result);
		assertEquals("foo", result.getKey());
		assertEquals("bar", result.getValue());
	}
	
	@Test
	public void lindex()
	{
		client.listLeftPush("foo", "1");
		client.listLeftPush("foo", "2");
		client.listLeftPush("foo", "3");

		List<String> expected = new ArrayList<String>(3);
		expected.add("3");
		expected.add("bar");
		expected.add("1");

		Boolean status = client.listSet("foo", 1, "bar");
		assertEquals(true, status);
		assertEquals(expected, client.listRange("foo", 0, 100));
	}
	
	@Test
	public void linsert()
	{
		Integer status = client.listInsert("foo", ListPosition.BEFORE, "bar", "car");
		assertEquals(0, status.intValue());

		client.listLeftPush("foo", "a");
		status = client.listInsert("foo", ListPosition.AFTER, "a", "b");
		assertEquals(2, status.intValue());

		List<String> actual = client.listRange("foo", 0, 100);
		List<String> expected = new ArrayList<String>(2);
		expected.add("a");
		expected.add("b");

		assertEquals(expected, actual);

		status = client.listInsert("foo", ListPosition.BEFORE, "bar", "car");
		assertEquals(-1, status.intValue());
	}
	
	@Test
	public void llen()
	{
		assertEquals(0, client.listLength("foo").intValue());
		client.listLeftPush("foo", "bar");
		client.listLeftPush("foo", "car");
		assertEquals(2, client.listLength("foo").intValue());
	}
	
	@Test(expected = ErrorCommandException.class)
	public void llenNotOnList()
	{
		client.set("foo", "bar");
		client.listLength("foo");
	}

	@Test
	public void lpop()
	{
		client.listRightPush("foo", "a");
		client.listRightPush("foo", "b");
		client.listRightPush("foo", "c");

		String element = client.listLeftPop("foo");
		assertEquals("a", element);

		List<String> expected = new ArrayList<String>(2);
		expected.add("b");
		expected.add("c");

		assertEquals(expected, client.listRange("foo", 0, 1000));
		client.listLeftPop("foo");
		client.listLeftPop("foo");

		element = client.listLeftPop("foo");
		assertEquals(null, element);
	}
	
	@Test
	public void lpush()
	{
		Integer size = client.listLeftPush("foo", "bar");
		assertEquals(1, size.intValue());
		size = client.listLeftPush("foo", "foo");
		assertEquals(2, size.intValue());
	}
	
	@Test
	public void lpushx()
	{
		Integer status = client.listLeftPushOnExist("foo", "bar");
		assertEquals(0, status.intValue());

		client.listLeftPush("foo", "a");
		status = client.listLeftPushOnExist("foo", "b");
		assertEquals(2, status.intValue());
	}
	
	@Test
	public void lrange()
	{
		client.listRightPush("foo", "a");
		client.listRightPush("foo", "b");
		client.listRightPush("foo", "c");

		List<String> expected = new ArrayList<String>(3);
		expected.add("a");
		expected.add("b");
		expected.add("c");

		List<String> range = client.listRange("foo", 0, 2);
		assertEquals(expected, range);

		range = client.listRange("foo", 0, 20);
		assertEquals(expected, range);

		expected = new ArrayList<String>(2);
		expected.add("b");
		expected.add("c");

		range = client.listRange("foo", 1, 2);
		assertEquals(expected, range);

		expected = new ArrayList<String>(0);
		range = client.listRange("foo", 2, 1);
		assertEquals(expected, range);
	}
	
	@Test
	public void lrem()
	{
		client.listLeftPush("foo", "hello");
		client.listLeftPush("foo", "hello");
		client.listLeftPush("foo", "x");
		client.listLeftPush("foo", "hello");
		client.listLeftPush("foo", "c");
		client.listLeftPush("foo", "b");
		client.listLeftPush("foo", "a");

		Integer count = client.listRemove("foo", -2, "hello");
		assertEquals(2, count.intValue());

		List<String> expected = new ArrayList<String>(5);
		expected.add("a");
		expected.add("b");
		expected.add("c");
		expected.add("hello");
		expected.add("x");
		
		assertEquals(expected, client.listRange("foo", 0, 1000));
		assertEquals(0, client.listRemove("bar", 100, "foo").intValue());
	}

	@Test
	public void lset()
	{
		client.listLeftPush("foo", "1");
		client.listLeftPush("foo", "2");
		client.listLeftPush("foo", "3");

		assertEquals("3", client.listIndex("foo", 0));
		assertEquals(null, client.listIndex("foo", 100));
	}
	
	@Test
	public void ltrim()
	{
		client.listLeftPush("foo", "1");
		client.listLeftPush("foo", "2");
		client.listLeftPush("foo", "3");
		Boolean status = client.listTrim("foo", 0, 1);
		assertEquals(true, status);

		List<String> expected = new ArrayList<String>(2);
		expected.add("3");
		expected.add("2");
		assertEquals(2, client.listLength("foo").intValue());
		assertEquals(expected, client.listRange("foo", 0, 100));
	}

	@Test
	public void rpop()
	{
		client.listRightPush("foo", "a");
		client.listRightPush("foo", "b");
		client.listRightPush("foo", "c");

		String element = client.listRightPop("foo");
		assertEquals("c", element);

		List<String> expected = new ArrayList<String>(2);
		expected.add("a");
		expected.add("b");

		assertEquals(expected, client.listRange("foo", 0, 1000));
		client.listRightPop("foo");
		client.listRightPop("foo");

		element = client.listRightPop("foo");
		assertEquals(null, element);
	}
	
	@Test
	public void rpush()
	{
		Integer size = client.listRightPush("foo", "bar");
		assertEquals(1, size.intValue());
		size = client.listRightPush("foo", "foo");
		assertEquals(2, size.intValue());
	}
	
	@Test
	public void rpushx()
	{
		Integer status = client.listRightPushOnExist("foo", "bar");
		assertEquals(0, status.intValue());

		client.listLeftPush("foo", "a");
		status = client.listRightPushOnExist("foo", "b");
		assertEquals(2, status.intValue());
	}
}