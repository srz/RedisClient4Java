package com.handinfo.redis4j.test.database.junit.commands;

import java.util.List;

import org.junit.Test;

import com.handinfo.redis4j.test.database.junit.RedisCommandTestBase;

public class Keys extends RedisCommandTestBase
{
	@Test
	public void testDelForNoExistKey()
	{
		assertEquals(0, client.del("NoExistKey").intValue());
	}

	@Test
	public void testDelForOneKey() 
	{
		String key = "WillBeDeletedKey";
		client.set(key, "I am WillBeDeletedKey");
		assertEquals(1, client.del(key).intValue());
	}

	@Test
	public void testDelForThreeKey() 
	{
		String key_1 = "WillBeDeletedKey1";
		String key_2 = "WillBeDeletedKey2";
		String key_3 = "WillBeDeletedKey3";
		client.set(key_1, "I am WillBeDeletedKey 1");
		client.set(key_2, "I am WillBeDeletedKey 2");
		client.set(key_3, "I am WillBeDeletedKey 3");
		assertEquals(3, client.del(key_1, key_2, key_3).intValue());
	}

	@Test
	public void testExistsKey() 
	{
		String key = "WillBeDeletedKey1";

		assertEquals(false, client.exists(key));

		client.set(key, "I am WillBeDeletedKey 1");

		assertEquals(true, client.exists(key));
	}

	@Test
	public void testExpireAndTTL() 
	{
		String key = "WillBeDeletedKey1";
		int time = 10;

		assertTrue(!client.expire(key, time));
		assertTrue(client.timeToLive(key) == -1);

		client.set(key, "I am WillBeDeletedKey 1");

		assertTrue(client.expire(key, time));
		assertTrue(client.timeToLive(key) == time);
	}

	@Test
	public void testExpireat() 
	{
		String key = "WillBeDeletedKey1";
		long time = System.currentTimeMillis();

		assertEquals(false, client.expireAsTimestamp(key, time));

		client.set(key, "I am WillBeDeletedKey 1");

		time = 1303036700L;//System.currentTimeMillis();1303036700937
		assertEquals(true, client.expireAsTimestamp(key, time));
	}

	@Test
	public void testKeys() 
	{
		int successStep = 0;
		List<String> keys = client.keys("*");
		if (keys == null)
		{
			successStep++;
		}

		int allKeyNumber = 100;
		for (int i = 0; i < allKeyNumber; i++)
		{
			client.set("key" + i, String.valueOf(i));
		}

		keys = client.keys("*");
		if (keys != null)
		{
			if (client.keys("*").size() == allKeyNumber)
			{
				successStep++;
			}
		}

		assertEquals(2, successStep);
	}

	@Test
	public void testMove() 
	{
		String key = "WillBeMovedKey";
		String value = "I am WillBeMovedKey";
		assertTrue(!client.move(key, 1));

		client.set(key, value);

		assertTrue(client.move(key, 1));
	}

	@Test
	public void testPersist() 
	{
		String key = "WillBeDeletedKey1";
		int time = 10;

		assertTrue(!client.persist(key));

		client.set(key, "I am WillBeDeletedKey 1");

		assertTrue(client.expire(key, time));
		assertTrue(client.timeToLive(key) == time);
		assertTrue(client.persist(key));
		assertTrue(client.timeToLive(key) == -1);
	}

	@Test
	public void testRandomKey() 
	{
		String key = "WillBeDeletedKey1";

		assertEquals(null, client.randomKey());

		client.set(key, "I am WillBeDeletedKey 1");

		assertTrue(client.randomKey().equalsIgnoreCase(key));
	}

	@Test
	public void testRename() 
	{
		String key_1 = "WillBeRenamedKey_1";
		String value = "I am WillBeRenamedKey";
		String key_2 = "WillBeRenamedKey_2";

		assertTrue(!client.rename(key_1, key_2));

		client.set(key_1, value);

		assertTrue(client.rename(key_1, key_2));

		String str_1 = client.get(key_1);
		String str_2 = client.get(key_2);
		client.del(key_1, key_2);

		assertTrue(str_1 == null && str_2.equalsIgnoreCase(value));
	}

	@Test
	public void testRenameNX() 
	{
		String key_1 = "WillBeRenamedKey_1";
		String value = "I am WillBeRenamedKey";
		String key_2 = "WillBeRenamedKey_2";

		assertTrue(!client.renameOnNotExistNewKey(key_1, key_2));

		client.set(key_1, value);
		client.set(key_2, value);

		assertTrue(!client.renameOnNotExistNewKey(key_1, key_2));

		client.del(key_2);

		assertTrue(client.renameOnNotExistNewKey(key_1, key_2));
	}

	@Test
	public void testSort() 
	{
		// TODO 待定
	}

	@Test
	public void testType() 
	{
		String key_str = "string";
		String key_list = "list";
		String key_set = "set";

		client.set("key_str", key_str);
		assertTrue(client.type("key_str").equalsIgnoreCase(key_str));

		client.listLeftPush("key_list", key_list);
		assertTrue(client.type("key_list").equalsIgnoreCase(key_list));

		client.setsAdd("key_set", key_set);
		assertTrue(client.type("key_set").equalsIgnoreCase(key_set));
	}
}
