package com.handinfo.redis4j.manager.worker;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.SwingWorker;

import com.handinfo.redis4j.api.IRedis4j;

public class RedisInfo extends SwingWorker<String, String>
{
	private IResult result;
	private IRedis4j client;
	private AtomicBoolean isCycle;
	private boolean isStartOnce;

	public RedisInfo(IRedis4j client, IResult result, boolean isAutomaticUpdate)
	{
		this.isStartOnce = false;
		this.result = result;
		this.client = client;
		this.isCycle = new AtomicBoolean(isAutomaticUpdate);
	}

	public void setAutomaticUpdate(boolean isAutomaticUpdate)
	{
		isCycle.set(isAutomaticUpdate);
	}

	@Override
	public String doInBackground()
	{
		try
		{
			while (!isCancelled())
			{
				if (isCycle.get() || !isStartOnce)
				{
					publish("-------------------------------\n");
					try
					{
						publish(client.getServer().info());
					}
					catch (Exception ex)
					{
						publish(ex.getMessage() + "\n");
					}
					publish("-------------------------------");
					isStartOnce = true;
					Thread.sleep(4000);
				}
				Thread.sleep(1000);
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}

		return "";
	}

	@Override
	protected void process(List<String> chunks)
	{
		String strResult = "";
		for (String str : chunks)
		{
			strResult += str;
		}
		this.result.setResult(java.util.Calendar.getInstance().getTime() + "\n" + strResult);
	}
}
