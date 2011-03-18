package com.handinfo.redis4j.manager.worker;

import java.util.List;

import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import com.handinfo.redis4j.api.IRedis4j;
import com.handinfo.redis4j.impl.Redis4jClient;

public class RedisMonitor extends SwingWorker<String, String>
{
	private IResult result;
	private IRedis4j client;
	
	public RedisMonitor(IRedis4j client, IResult result)
	{
		this.client = client.clone();
		this.result = result;
	}

	@Override
	public String doInBackground()
	{
		client.getServer().monitor();
		try
		{
			while (!isCancelled())
			{
				String result = client.getServer().getMonitorResult();
				if(!isCancelled())
				{
					publish(result + "\n");
				}
				Thread.sleep(0);
			}
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		finally
		{
			client.getConnection().quit();
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
		this.result.setResult(java.util.Calendar.getInstance().getTime() + "\n" + strResult + "-------------------------------");
	}
}
