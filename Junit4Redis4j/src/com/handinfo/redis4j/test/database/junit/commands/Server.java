package com.handinfo.redis4j.test.database.junit.commands;

import java.util.Map;
import java.util.concurrent.BrokenBarrierException;

import org.junit.Test;

import com.handinfo.redis4j.api.RedisCommand;
import com.handinfo.redis4j.api.async.IRedisAsyncClient;
import com.handinfo.redis4j.api.async.IRedisAsyncClient.Result;
import com.handinfo.redis4j.api.database.IRedisDatabaseClient;
import com.handinfo.redis4j.api.exception.CleanLockedThreadException;
import com.handinfo.redis4j.api.exception.ErrorCommandException;
import com.handinfo.redis4j.test.Helper;
import com.handinfo.redis4j.test.database.junit.RedisCommandTestBase;

public class Server extends RedisCommandTestBase
{
	@Test
	public void bgrewriteaof()
	{
		String status = client.backgroundRewriteAOF();
		assertEquals("Background append only file rewriting started", status);
	}

	@Test
	public void bgsave()
	{
		try
		{
			String status = client.backgroundSave();
			assertEquals("Background saving started", status);
		}
		catch (Exception e)
		{
			assertTrue("ERR Background save already in progress".equalsIgnoreCase(e.getMessage()));
		}
	}

	@Test
	public void configGet()
	{
		Map<String, String> info = client.configGet("m*");
		assertNotNull(info);
	}

	@Test
	public void configSet()
	{
		Map<String, String> info = client.configGet("maxmemory");
		String memory = info.get("maxmemory");
		Boolean status = client.configSet("maxmemory", "200");
		assertEquals(true, status);
		client.configSet("maxmemory", memory);
	}

	@Test
	public void configResetStat()
	{
		Boolean info = client.configResetStat();
		assertEquals(true, info);
	}

	@Test
	public void dbsize()
	{
		Integer info = client.dbSize();
		assertNotNull(info);
		assertEquals(info.intValue(), 0);
		
		client.set("foo", "bar");
		info = client.dbSize();
		assertEquals(info.intValue(), 1);
	}

	@Test
	public void debugObject()
	{
		client.set("foo", "bar");
		String resp = client.debugObject("foo");
		assertNotNull(resp);
	}

	@Test
	public void info()
	{
		String info = client.info();
		assertNotNull(info);
	}

	@Test
	public void lastsave() throws InterruptedException
	{
		long before = client.lastSave();
		Boolean st = false;
		while (!st)
		{
			try
			{
				Thread.sleep(1000);
				st = client.save();
			}
			catch (Exception e)
			{

			}
		}
		long after = client.lastSave();
		assertTrue((after - before) > 0);
	}

	@Test
	public void monitor()
	{
		final IRedisAsyncClient asyncClient = Helper.getRedisAsyncClient();
		try
		{
			asyncClient.executeCommand(RedisCommand.MONITOR, new Result()
			{
				@Override
				public void doInCurrentThread(String result)
				{
					assertTrue(result.contains("OK"));
					asyncClient.quit();
				}
			});
		}
		catch (CleanLockedThreadException e)
		{
			e.printStackTrace();
		}
		catch (ErrorCommandException e)
		{
			e.printStackTrace();
		}
		catch (IllegalStateException e)
		{
			e.printStackTrace();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		catch (BrokenBarrierException e)
		{
			e.printStackTrace();
		}
	}

	@Test
	public void save()
	{
		Boolean status = client.save();
		assertEquals(true, status);
	}

	@Test
	public void sync()
	{
		IRedisDatabaseClient cli = Helper.getRedisDatabaseClient();
		String result = cli.sync();
		assertNotNull(result);
		cli.quit();
	}
}