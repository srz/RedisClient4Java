package org.elk.redis4j.test.cache.junit.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.elk.redis4j.api.exception.ErrorCommandException;
import org.elk.redis4j.test.cache.junit.RedisCommandTestBase;
import org.junit.Test;


public class Strings extends RedisCommandTestBase
{
	@Test
	public void append()
	{
		long value = client.append("foo", "bar");
		assertEquals(3, value);
		assertEquals("bar", client.getRange("foo", 0, -1));
		value = client.append("foo", "bar");
		assertEquals(6, value);
		assertEquals("barbar", client.getRange("foo", 0, -1));
	}
	
	@Test(expected = ErrorCommandException.class)
	public void decrWrongValue()
	{
		client.set("foo", "bar");
		client.decrement("foo");
	}
	
	@Test
	public void decr()
	{
		long value = client.decrement("foo");
		assertEquals(-1, value);
		value = client.decrement("foo");
		assertEquals(-2, value);
	}
	
	@Test(expected = ErrorCommandException.class)
	public void decrByWrongValue()
	{
		client.set("foo", "bar");
		client.decrementByValue("foo", 2);
	}

	@Test
	public void decrBy()
	{
		long value = client.decrementByValue("foo", 2);
		assertEquals(-2, value);
		value = client.decrementByValue("foo", 2);
		assertEquals(-4, value);
	}
	
	@Test
	public void getAndSet()
	{
		Boolean status = client.set("foo", "bar");
		assertEquals(true, status);

		String value = client.get("foo");
		assertEquals("bar", value);

		assertEquals(null, client.get("bar"));
	}
	
	@Test
	public void getAndSetbit()
	{
		boolean bit = client.setBit("foo", 0, true);
		assertEquals(false, bit);

		bit = client.getBit("foo", 0);
		assertEquals(true, bit);
	}

	@Test
	public void setAndGetRange()
	{
		client.append("key1", "Hello World");
		Integer reply = client.setRange("key1", 6, "Redis");
		assertEquals(11, reply.intValue());

		assertEquals("Hello Redis", client.getRange("key1", 0, -1));

		assertEquals("Hello", client.getRange("key1", 0, 4));
		assertEquals("Redis", client.getRange("key1", 6, 11));
	}
	
	@Test
	public void getRange()
	{
		client.append("s", "This is a string");
		assertEquals("This", client.getRange("s", 0, 3));
		assertEquals("ing", client.getRange("s", -3, -1));
		assertEquals("This is a string", client.getRange("s", 0, -1));
		assertEquals(" string", client.getRange("s", 9, 100000));
	}
	
	@Test
	public void getSet()
	{
		String value = client.getSet("foo", "bar");
		assertEquals(null, value);
		value = client.get("foo");
		assertEquals("bar", value);
	}

	@Test(expected = ErrorCommandException.class)
	public void incrWrongValue()
	{
		client.set("foo", "bar");
		client.increment("foo");
	}

	@Test
	public void incr()
	{
		long value = client.increment("foo");
		assertEquals(1, value);
		value = client.increment("foo");
		assertEquals(2, value);
	}

	@Test(expected = ErrorCommandException.class)
	public void incrByWrongValue()
	{
		client.set("foo", "bar");
		client.incrementByValue("foo", 2);
	}

	@Test
	public void incrBy()
	{
		long value = client.incrementByValue("foo", 2);
		assertEquals(2, value);
		value = client.incrementByValue("foo", 2);
		assertEquals(4, value);
	}
	
	@Test
	public void incrLargeNumbers()
	{
		long value = client.increment("foo");
		assertEquals(1, value);
		assertEquals(1L + Integer.MAX_VALUE, (long) client.incrementByValue("foo", Integer.MAX_VALUE));
	}

	@Test(expected = ErrorCommandException.class)
	public void incrReallyLargeNumbers()
	{
		client.set("foo", Long.toString(Long.MAX_VALUE));
		long value = client.increment("foo");
		assertEquals(Long.MIN_VALUE, value);
	}
	
	@Test
	public void mget()
	{
		List<String> values = client.multipleGet("foo", "bar");
		List<String> expected = new ArrayList<String>(2);
		expected.add(null);
		expected.add(null);
		assertEquals(expected, values);

		client.set("foo", "bar");

		expected = new ArrayList<String>(2);
		expected.add("bar");
		expected.add(null);
		values = client.multipleGet("foo", "bar");

		assertEquals(expected, values);

		client.set("bar", "foo");

		expected = new ArrayList<String>(2);
		expected.add("bar");
		expected.add("foo");
		values = client.multipleGet("foo", "bar");

		assertEquals(expected, values);
	}

	@Test
	public void mset()
	{
		HashMap<String, String> keyAndValue = new HashMap<String, String>();
		keyAndValue.put("foo", "bar");
		keyAndValue.put("bar", "foo");
		Boolean status = client.multipleSet(keyAndValue);
		assertEquals(true, status);
		assertEquals("bar", client.get("foo"));
		assertEquals("foo", client.get("bar"));
	}
	
//	@Test
//	public void msetnx()
//	{
//		HashMap<String, String> keyAndValue = new HashMap<String, String>();
//		keyAndValue.put("foo", "bar");
//		keyAndValue.put("bar", "foo");
//		Boolean status = client.multipleSetOnNotExist(keyAndValue);
//		assertEquals(true, status);
//		assertEquals("bar", client.get("foo"));
//		assertEquals("foo", client.get("bar"));
//		
//		keyAndValue = new HashMap<String, String>();
//		keyAndValue.put("foo", "bar1");
//		keyAndValue.put("bar2", "foo2");
//
//		status = client.multipleSetOnNotExist(keyAndValue);
//		assertEquals(false, status);
//		assertEquals("bar", client.get("foo"));
//		assertEquals("foo", client.get("bar"));
//	}
	
	@Test
	public void setex()
	{
		Boolean status = client.setAndExpire("foo", 20, "bar");
		assertEquals(true, status);
		long ttl = client.timeToLive("foo");
		assertTrue(ttl > 0 && ttl <= 20);
	}
	
	@Test
	public void setnx()
	{
		Boolean status = client.setOnNotExist("foo", "bar");
		assertEquals(true, status);
		assertEquals("bar", client.get("foo"));

		status = client.setOnNotExist("foo", "bar2");
		assertEquals(false, status);
		assertEquals("bar", client.get("foo"));
	}
	
	
	@Test
	public void setRange()
	{
		assertEquals(11, client.setRange("key2", 6, "Redis").intValue());
		client.append("key1", "Hello World");
		assertEquals(11, client.setRange("key1", 6, "Redis").intValue());
		assertEquals("Hello Redis", client.getRange("key1", 0, -1));
	}
	

	@Test
	public void strlen()
	{
		assertEquals(0, client.strLength("s").intValue());
		client.append("s", "This is a string");
		assertEquals("This is a string".length(), client.strLength("s").intValue());
	}
}