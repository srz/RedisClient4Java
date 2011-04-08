package com.handinfo.redis4j.test.database.junit.commands;

import org.junit.Test;

import com.handinfo.redis4j.test.database.junit.RedisCommandTestBase;

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
	public void sdiff()
	{
		client.setsAdd("foo", "x");
		client.setsAdd("foo", "a");
		client.setsAdd("foo", "b");
		client.setsAdd("foo", "c");

		client.setsAdd("bar", "c");

		client.setsAdd("car", "a");
		client.setsAdd("car", "d");

		String[] expected = new String[2];
		expected[0] = "x";
		expected[1] = "b";

		String[] diff = client.setsDiff("foo", "bar", "car");
		assertEquals(expected, diff);
	}

	@Test
	public void sdiffstore()
	{
		client.setsAdd("foo", "x");
		client.setsAdd("foo", "a");
		client.setsAdd("foo", "b");
		client.setsAdd("foo", "c");

		client.setsAdd("bar", "c");

		client.setsAdd("car", "a");
		client.setsAdd("car", "d");

		String[] expected = new String[2];
		expected[0] = "d";
		expected[1] = "a";

		Integer status = client.setsDiffStore("tar", "foo", "bar", "car");
		assertEquals(2, status.intValue());
		assertEquals(expected, client.setsMembers("car"));
	}

	@Test
	public void sinter()
	{
		client.setsAdd("foo", "a");
		client.setsAdd("foo", "b");

		client.setsAdd("bar", "b");
		client.setsAdd("bar", "c");

		String[] expected = new String[1];
		expected[0] = "b";

		String[] intersection = client.setsInter("foo", "bar");
		assertEquals(expected, intersection);
	}

	@Test
	public void sinterstore()
	{
		client.setsAdd("foo", "a");
		client.setsAdd("foo", "b");

		client.setsAdd("bar", "b");
		client.setsAdd("bar", "c");

		Integer status = client.setsInterStore("car", "foo", "bar");
		assertEquals(1, status.intValue());

		String[] expected = new String[1];
		expected[0] = "b";
		assertEquals(expected, client.setsMembers("car"));
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

		String[] expected = new String[2];
		expected[0] = "a";
		expected[1] = "b";

		String[] members = client.setsMembers("foo");

		assertEquals(expected, members);
	}

	@Test
	public void smove()
	{
		client.setsAdd("foo", "a");
		client.setsAdd("foo", "b");

		client.setsAdd("bar", "c");

		Boolean status = client.setsMove("foo", "bar", "a");
		assertEquals(status, true);

		String[] expectedSrc = new String[1];
		expectedSrc[0] = "b";

		String[] expectedDst = new String[2];
		expectedDst[0] = "c";
		expectedDst[1] = "a";

		assertEquals(expectedSrc, client.setsMembers("foo"));
		assertEquals(expectedDst, client.setsMembers("bar"));

		status = client.setsMove("foo", "bar", "a");

		assertEquals(status, false);
	}

	@Test
	public void spop()
	{
		client.setsAdd("foo", "a");
		client.setsAdd("foo", "b");

		String member = client.setsPop("foo");

		assertTrue("a".equals(member) || "b".equals(member));
		assertEquals(1, client.setsMembers("foo").length);

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
		assertEquals(2, client.setsMembers("foo").length);

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

		String[] expected = new String[1];
		expected[0] = "b";

		assertEquals(expected, client.setsMembers("foo"));

		status = client.setsRemove("foo", "bar");

		assertEquals(false, status);
	}

	@Test
	public void sunion()
	{
		client.setsAdd("foo", "a");
		client.setsAdd("foo", "b");

		client.setsAdd("bar", "b");
		client.setsAdd("bar", "c");

		String[] expected = new String[3];
		expected[0] = "c";
		expected[1] = "a";
		expected[2] = "b";

		String[] union = client.setsUnion("foo", "bar");
		assertEquals(expected, union);
	}

	@Test
	public void sunionstore()
	{
		client.setsAdd("foo", "a");
		client.setsAdd("foo", "b");

		client.setsAdd("bar", "b");
		client.setsAdd("bar", "c");

		String[] expected = new String[3];
		expected[0] = "c";
		expected[1] = "a";
		expected[2] = "b";

		Integer status = client.setsUnionStore("car", "foo", "bar");
		assertEquals(3, status.intValue());

		assertEquals(expected, client.setsMembers("car"));
	}
}