package com.handinfo.redis4j.test.database.junit.commands;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import com.handinfo.redis4j.test.database.junit.RedisCommandTestBase;

public class Hashes extends RedisCommandTestBase
{
	@Test
	public void hdel()
	{
		Map<String, String> hash = new HashMap<String, String>();
		hash.put("bar", "car");
		hash.put("car", "bar");
		client.hashesMultipleSet("foo", hash);

		assertEquals(false, client.hashesDel("bar", "foo"));
		assertEquals(false, client.hashesDel("foo", "foo"));
		assertEquals(true, client.hashesDel("foo", "bar"));
		assertEquals(null, client.hashesGet("foo", "bar"));
	}

	@Test
	public void hexists()
	{
		Map<String, String> hash = new HashMap<String, String>();
		hash.put("bar", "car");
		hash.put("car", "bar");
		client.hashesMultipleSet("foo", hash);

		assertFalse(client.hashesExists("bar", "foo"));
		assertFalse(client.hashesExists("foo", "foo"));
		assertTrue(client.hashesExists("foo", "bar"));
	}

	@Test
	public void hget()
	{
		client.hashesSet("foo", "bar", "car");
		assertEquals(null, client.hashesGet("bar", "foo"));
		assertEquals(null, client.hashesGet("foo", "car"));
		assertEquals("car", client.hashesGet("foo", "bar"));
	}

	@Test
	public void hgetAll()
	{
		Map<String, String> h = new HashMap<String, String>();
		h.put("bar", "car");
		h.put("car", "bar");
		client.hashesMultipleSet("foo", h);

		Map<String, String> hash = client.hashesGetAll("foo");
		assertEquals(2, hash.size());
		assertEquals("car", hash.get("bar"));
		assertEquals("bar", hash.get("car"));
	}

	@Test
	public void hincrBy()
	{
		Integer value = client.hashesIncrementByValue("foo", "bar", 1);
		assertEquals(1, value.intValue());
		value = client.hashesIncrementByValue("foo", "bar", -1);
		assertEquals(0, value.intValue());
		value = client.hashesIncrementByValue("foo", "bar", -10);
		assertEquals(-10, value.intValue());
	}

	@Test
	public void hkeys()
	{
		Map<String, String> hash = new LinkedHashMap<String, String>();
		hash.put("bar", "car");
		hash.put("car", "bar");
		client.hashesMultipleSet("foo", hash);

		String[] keys = client.hashesGetAllField("foo");
		String[] expected = new String[2];
		expected[0] = "bar";
		expected[1] = "car";
		assertEquals(expected, keys);
	}

	@Test
	public void hlen()
	{
		Map<String, String> hash = new HashMap<String, String>();
		hash.put("bar", "car");
		hash.put("car", "bar");
		client.hashesMultipleSet("foo", hash);

		assertEquals(0, client.hashesLength("bar").intValue());
		assertEquals(2, client.hashesLength("foo").intValue());
	}

	@Test
	public void hmget()
	{
		Map<String, String> hash = new HashMap<String, String>();
		hash.put("bar", "car");
		hash.put("car", "bar");
		client.hashesMultipleSet("foo", hash);

		String[] values = client.hashesMultipleFieldGet("foo", "bar", "car", "foo");
		String[] expected = new String[3];
		expected[0] = "car";
		expected[1] = "bar";
		expected[2] = null;

		assertEquals(expected, values);
	}

	@Test
	public void hmset()
	{
		Map<String, String> hash = new HashMap<String, String>();
		hash.put("bar", "car");
		hash.put("car", "bar");
		Boolean status = client.hashesMultipleSet("foo", hash);
		assertEquals(true, status);
		assertEquals("car", client.hashesGet("foo", "bar"));
		assertEquals("bar", client.hashesGet("foo", "car"));
	}

	@Test
	public void hset()
	{
		Boolean status = client.hashesSet("foo", "bar", "car");
		assertEquals(true, status);
		status = client.hashesSet("foo", "bar", "foo");
		assertEquals(false, status);
	}

	@Test
	public void hsetnx()
	{
		Boolean status = client.hashesSetNotExistField("foo", "bar", "car");
		assertEquals(true, status);
		assertEquals("car", client.hashesGet("foo", "bar"));

		status = client.hashesSetNotExistField("foo", "bar", "foo");
		assertEquals(false, status);
		assertEquals("car", client.hashesGet("foo", "bar"));

		status = client.hashesSetNotExistField("foo", "car", "bar");
		assertEquals(true, status);
		assertEquals("bar", client.hashesGet("foo", "car"));
	}

	@Test
	public void hvals()
	{
		Map<String, String> hash = new LinkedHashMap<String, String>();
		hash.put("barKey", "car");
		hash.put("carKey", "bar");
		client.hashesMultipleSet("foo", hash);

		Map<String, String> vals = client.hashesGetAllValue("foo");
		assertEquals(2, vals.size());
		assertTrue(vals.containsKey("barKey"));
		assertTrue(vals.containsKey("carKey"));
	}
}
