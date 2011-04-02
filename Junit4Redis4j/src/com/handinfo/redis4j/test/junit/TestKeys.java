package com.handinfo.redis4j.test.junit;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.handinfo.redis4j.api.database.IRedisDatabaseClient;
import com.handinfo.redis4j.impl.RedisClientBuilder;
import com.handinfo.redis4j.impl.database.RedisDatabaseClient;

public class TestKeys
{
	private static IRedisDatabaseClient client;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		client = RedisClientBuilder.buildDatabaseClient("192.2.9.223", 6379, 0, "");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
		client.quit();
	}

	@Before
	public void setUp() throws Exception
	{
		//TODO 调用flushdb清空所有数据
		System.out.println("setUp flushCurrentDB = " + client.flushCurrentDB());
	}

	@After
	public void tearDown() throws Exception
	{
		//TODO 调用flushdb清空所有数据
		System.out.println("tearDown flushCurrentDB  = " + client.flushCurrentDB());
	}

	@Test
	public void testDelForNoExistKey() throws Exception
	{
		assertTrue(client.del("NoExistKey")==0);
	}
	
	@Test
	public void testDelForOneKey() throws Exception
	{
		String key = "WillBeDeletedKey";
		boolean b = client.set(key, "I am WillBeDeletedKey");
		assertTrue(client.del(key)==1);
	}
	
	@Test
	public void testDelForThreeKey() throws Exception
	{
		String key_1 = "WillBeDeletedKey1";
		String key_2 = "WillBeDeletedKey2";
		String key_3 = "WillBeDeletedKey3";
		client.set(key_1, "I am WillBeDeletedKey 1");
		client.set(key_2, "I am WillBeDeletedKey 2");
		client.set(key_3, "I am WillBeDeletedKey 3");
		assertTrue(client.del(key_1, key_2, key_3)==3);
	}

	@Test
	public void testRename() throws Exception
	{
		String key_1 = "WillBeRenamedKey_1";
		String value = "I am WillBeRenamedKey";
		String key_2 = "WillBeRenamedKey_2";
		client.set(key_1, value);
		client.rename(key_1, key_2);
		Object str_1 = client.get(key_1);
		Object str_2 = client.get(key_2);
		client.del(key_1, key_2);

		assertTrue(str_1==null && str_2.toString().equalsIgnoreCase(value));
	}

	@Test
	public void testKeys() throws Exception
	{
		int successStep = 0;
		String[] keys = client.keys("*");
		if(keys == null)
		{
			successStep++;
		}
		
		int allKeyNumber = 100;
		for(int i=0; i<allKeyNumber; i++)
		{
			client.set("key" + i, String.valueOf(i));
		}
		
		keys = client.keys("*");
		if(keys != null)
		{
			if(client.keys("*").length == allKeyNumber)
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
		
		client.set("key_str", key_str);
		client.set("key_list", key_list);
		client.set("key_set", key_set);
		
		if(client.type("key_str").equalsIgnoreCase(key_str))
		{
			successStep++;
		}
		
		if(client.type("key_list").equalsIgnoreCase(key_list))
		{
			successStep++;
		}
		
		if(client.type("key_set").equalsIgnoreCase(key_set))
		{
			successStep++;
		}
		
		//TODO 需要增加push及add命令后在修改为3
		assertTrue(successStep==1);
	}
}
