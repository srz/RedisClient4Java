package com.handinfo.redis4j.test;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.handinfo.redis4j.api.IRedis4j;
import com.handinfo.redis4j.impl.Redis4jClient;

public class TestKeys
{
	private static IRedis4j client;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		client = new Redis4jClient("127.0.0.1", 6379, 900, 10);
		//System.out.println("connect to RedisServer... back = " + client.connect());
		//client.select(10);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
		client.getConnection().quit();
	}

	@Before
	public void setUp() throws Exception
	{
		//TODO 调用flushdb清空所有数据
		System.out.println("setUp flushdb 10 = " + client.getServer().flushdb());
	}

	@After
	public void tearDown() throws Exception
	{
		//TODO 调用flushdb清空所有数据
		System.out.println("tearDown flushdb 10 = " + client.getServer().flushdb());
	}

	@Test
	public void testDelForNoExistKey() throws Exception
	{
		assertTrue(client.getKeys().del("NoExistKey")==0);
	}
	
	@Test
	public void testDelForOneKey() throws Exception
	{
		String key = "WillBeDeletedKey";
		boolean b = client.getStrings().set(key, "I am WillBeDeletedKey");
		assertTrue(client.getKeys().del(key)==1);
	}
	
	@Test
	public void testDelForThreeKey() throws Exception
	{
		String key_1 = "WillBeDeletedKey1";
		String key_2 = "WillBeDeletedKey2";
		String key_3 = "WillBeDeletedKey3";
		client.getStrings().set(key_1, "I am WillBeDeletedKey 1");
		client.getStrings().set(key_2, "I am WillBeDeletedKey 2");
		client.getStrings().set(key_3, "I am WillBeDeletedKey 3");
		assertTrue(client.getKeys().del(key_1, key_2, key_3)==3);
	}

	@Test
	public void testRename() throws Exception
	{
		String key_1 = "WillBeRenamedKey_1";
		String value = "I am WillBeRenamedKey";
		String key_2 = "WillBeRenamedKey_2";
		client.getStrings().set(key_1, value);
		client.getKeys().rename(key_1, key_2);
		Object str_1 = client.getStrings().get(key_1);
		Object str_2 = client.getStrings().get(key_2);
		client.getKeys().del(key_1, key_2);

		assertTrue(str_1==null && str_2.toString().equalsIgnoreCase(value));
	}

	@Test
	public void testKeys() throws Exception
	{
		int successStep = 0;
		String[] keys = client.getKeys().keys("*");
		if(keys == null)
		{
			successStep++;
		}
		
		int allKeyNumber = 100;
		for(int i=0; i<allKeyNumber; i++)
		{
			client.getStrings().set("key" + i, String.valueOf(i));
		}
		
		keys = client.getKeys().keys("*");
		if(keys != null)
		{
			if(client.getKeys().keys("*").length == allKeyNumber)
			{
				successStep++;
			}
		}

		assertTrue(successStep==2);
	}
	
	@Test
	public void testType() throws Exception
	{
		int successStep = 0;
		String key_str = "string";
		String key_list = "list";
		String key_set = "set";
		
		client.getStrings().set("key_str", key_str);
		client.getStrings().set("key_list", key_list);
		client.getStrings().set("key_set", key_set);
		
		if(client.getKeys().type("key_str").equalsIgnoreCase(key_str))
		{
			successStep++;
		}
		
		if(client.getKeys().type("key_list").equalsIgnoreCase(key_list))
		{
			successStep++;
		}
		
		if(client.getKeys().type("key_set").equalsIgnoreCase(key_set))
		{
			successStep++;
		}
		
		//TODO 需要增加push及add命令后在修改为3
		assertTrue(successStep==1);
	}
}
