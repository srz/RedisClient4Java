package org.elk.redis4j.test.cache.junit.commands;

import java.util.ArrayList;
import java.util.List;

import org.elk.redis4j.test.cache.junit.RedisCommandTestBase;
import org.junit.Test;


public class Sets extends RedisCommandTestBase
{
	@Test
	public void sadd()
	{
		Boolean status = client.setsAdd("foo", "a");
		assertEquals(true, status);

		status = client.setsAdd("foo", "a");
		assertEquals(false, status);
	}

	@Test
	public void scard()
	{
		client.setsAdd("foo", "a");
		client.setsAdd("foo", "b");

		Integer card = client.setsCard("foo");

		assertEquals(2, card.intValue());

		card = client.setsCard("bar");
		assertEquals(0, card.intValue());
	}

	@Test
	public void sismember()
	{
		client.setsAdd("foo", "a");
		client.setsAdd("foo", "b");

		assertTrue(client.setsIsMember("foo", "a"));

		assertFalse(client.setsIsMember("foo", "c"));
	}

	@Test
	public void smembers()
	{
		client.setsAdd("foo", "a");
		client.setsAdd("foo", "b");

		List<String> expected = new ArrayList<String>(2);
		expected.add("a");
		expected.add("b");

		List<String> members = client.setsMembers("foo");

		assertEquals(expected, members);
	}

	@Test
	public void spop()
	{
		client.setsAdd("foo", "a");
		client.setsAdd("foo", "b");

		String member = client.setsPop("foo");

		assertTrue("a".equals(member) || "b".equals(member));
		assertEquals(1, client.setsMembers("foo").size());

		member = client.setsPop("bar");
		assertNull(member);
	}

	@Test
	public void srandmember()
	{
		client.setsAdd("foo", "a");
		client.setsAdd("foo", "b");

		String member = client.setsRandMember("foo");

		assertTrue("a".equals(member) || "b".equals(member));
		assertEquals(2, client.setsMembers("foo").size());

		member = client.setsRandMember("bar");
		assertNull(member);
	}

	@Test
	public void srem()
	{
		client.setsAdd("foo", "a");
		client.setsAdd("foo", "b");

		Boolean status = client.setsRemove("foo", "a");
		assertEquals(true, status);

		List<String> expected = new ArrayList<String>(1);
		expected.add("b");

		assertEquals(expected, client.setsMembers("foo"));

		status = client.setsRemove("foo", "bar");

		assertEquals(false, status);
	}
}