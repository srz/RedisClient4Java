package com.handinfo.redis4j.test.database.junit.commands;

import org.junit.Test;

import com.handinfo.redis4j.test.database.junit.RedisCommandTestBase;

public class Connection extends RedisCommandTestBase
{
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
}
