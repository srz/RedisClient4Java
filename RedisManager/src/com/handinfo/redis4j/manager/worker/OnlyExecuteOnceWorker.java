package com.handinfo.redis4j.manager.worker;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import com.handinfo.redis4j.api.IRedis4j;

public class OnlyExecuteOnceWorker extends SwingWorker<String, String>
{
	private IResult result;
	private IRedis4j client;
	private IExecuteCommand executor;


	public OnlyExecuteOnceWorker(IRedis4j client, IResult result, IExecuteCommand executor)
	{
		this.result = result;
		this.client = client;
		this.executor = executor;
	}

	@Override
	public String doInBackground()
	{
		try
		{
			if (!isCancelled())
			{
				publish("-------------------------------\n");
				publish(this.executor.executeCommand(client));
				publish("\n-------------------------------");
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
