package com.handinfo.redis4j.test.database.junit.commands;

import org.junit.Test;

import com.handinfo.redis4j.api.database.IDatabaseTransaction;
import com.handinfo.redis4j.test.database.junit.RedisCommandTestBase;

public class Transactions extends RedisCommandTestBase
{
	@Test
	public void multi()
	{
		IDatabaseTransaction trans = client.getNewTransaction();

		trans.setsAdd("foo", "a");
		trans.setsAdd("foo", "b");
		trans.setsCard("foo");

		Boolean result = trans.commit();
		assertEquals(true, result);

		String[] expected = new String[2];
		expected[0] = "a";
		expected[1] = "b";

		assertEquals(expected, client.setsMembers("foo"));
	}

	@Test
	public void watch()
	{
		IDatabaseTransaction trans = client.getNewTransaction();
		
		trans.watch("mykey");

		trans.set("mykey", "foo1");
		trans.set("mykey", "foo2");
		trans.set("mykey", "foo3");
		Boolean result = trans.commit();
		assertEquals(true, result);

		assertEquals("foo3", client.get("mykey"));
	}

	// @Test
	// public void unwatch() throws UnknownHostException, IOException
	// {
	// client.watch("mykey");
	// String val = client.get("mykey");
	// val = "foo";
	// String status = client.unwatch();
	// assertEquals("OK", status);
	// Transaction t = client.multi();
	//
	// nj.connect();
	// nj.auth("foobared");
	// nj.set("mykey", "bar");
	// nj.disconnect();
	//
	// t.set("mykey", val);
	// List<Object> resp = t.exec();
	// assertEquals(1, resp.size());
	// assertEquals("OK", resp.get(0));
	//
	// // Binary
	// client.watch(bmykey);
	// byte[] bval = client.get(bmykey);
	// bval = bfoo;
	// status = client.unwatch();
	// assertEquals(Keyword.OK.name(), status);
	// t = client.multi();
	//
	// nj.connect();
	// nj.auth("foobared");
	// nj.set(bmykey, bbar);
	// nj.disconnect();
	//
	// t.set(bmykey, bval);
	// resp = t.exec();
	// assertEquals(1, resp.size());
	// assertEquals("OK", resp.get(0));
	// }
	//
	// @Test(expected = JedisDataException.class)
	// public void validateWhenInMulti()
	// {
	// client.multi();
	// client.ping();
	// }
	//
	// @Test
	// public void discard()
	// {
	// Transaction t = client.multi();
	// String status = t.discard();
	// assertEquals("OK", status);
	// }
	//
	// @Test
	// public void transactionResponse()
	// {
	// client.set("string", "foo");
	// client.lpush("list", "foo");
	// client.hset("hash", "foo", "bar");
	// client.zadd("zset", 1, "foo");
	// client.sadd("set", "foo");
	//
	// Transaction t = client.multi();
	// Response<String> string = t.get("string");
	// Response<String> list = t.lpop("list");
	// Response<String> hash = t.hget("hash", "foo");
	// Response<Set<String>> zset = t.zrange("zset", 0, -1);
	// Response<String> set = t.spop("set");
	// t.exec();
	//
	// assertEquals("foo", string.get());
	// assertEquals("foo", list.get());
	// assertEquals("bar", hash.get());
	// assertEquals("foo", zset.get().iterator().next());
	// assertEquals("foo", set.get());
	// }
	//
	// @Test(expected = JedisDataException.class)
	// public void transactionResponseWithinPipeline()
	// {
	// client.set("string", "foo");
	//
	// Transaction t = client.multi();
	// Response<String> string = t.get("string");
	// string.get();
	// t.exec();
	// }
}