package com.handinfo.redis4j.impl.util;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

import com.handinfo.redis4j.api.IConnector;
import com.handinfo.redis4j.api.exception.RedisClientException;
import com.handinfo.redis4j.impl.transfers.Connector;

public class CommandWrapper
{
	private String type;
	private ChannelBuffer value;
	private CountDownLatch latch;
	private RedisClientException exception;
	private Object[] result;
	
	private AtomicBoolean isPause = new AtomicBoolean(false);
	private AtomicBoolean isResume = new AtomicBoolean(false);

	public CommandWrapper(Object[] commandList)
	{
		this.type = String.valueOf(commandList[0]);
		this.value = getBinaryCommand(commandList);
		this.result = null;
		this.latch = new CountDownLatch(1);
		this.exception = null;
	}

	private ChannelBuffer getBinaryCommand(Object[] commandList)
	{
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();

		// 写入头
		buffer.writeBytes("*".getBytes());
		buffer.writeBytes(String.valueOf(commandList.length).getBytes());
		buffer.writeBytes("\r\n".getBytes());

		// 写入参数
		for (int i = 0; i < commandList.length; i++)
		{
			Object value = commandList[i];
			byte[] bytes = null;
			if (value instanceof ObjectWrapper<?>)
			{
				bytes = ((ObjectWrapper<?>) value).getByteArray();
			} else
			{
				bytes = String.valueOf(value).getBytes();
			}
			if (bytes != null)
			{
				buffer.writeBytes("$".getBytes());
				buffer.writeBytes(String.valueOf(bytes.length).getBytes());
				buffer.writeBytes("\r\n".getBytes());
				buffer.writeBytes(bytes);
				buffer.writeBytes("\r\n".getBytes());
			}
		}

		return buffer;
	}

	public String getType()
	{
		return type;
	}

	public ChannelBuffer getValue()
	{
		return value;
	}

	public void pause() throws InterruptedException
	{
		if (!isPause.getAndSet(true))
		{
			latch.await();
		}
	}

	public void pause(long timeout, TimeUnit unit) throws InterruptedException
	{
		if (!isPause.getAndSet(true))
		{
			latch.await(timeout, unit);
		}
	}

	public void resume()
	{
		if (!isResume.getAndSet(true))
		{
			latch.countDown();
		}
	}

	public RedisClientException getException()
	{
		return exception;
	}

	public void setException(RedisClientException exception)
	{
		this.exception = exception;
	}

	public Object[] getResult()
	{
		return result;
	}

	public void setResult(Object[] result)
	{
		this.result = result;
	}
}
