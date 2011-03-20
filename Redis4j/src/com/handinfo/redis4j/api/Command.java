package com.handinfo.redis4j.api;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

public class Command
{
	private String type;
	private ChannelBuffer value;
	private CountDownLatch latch;
	private Exception exception;
	private Object[] result;

	public Command(Object[] commandList)
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
			if (value instanceof String || value instanceof Integer || value instanceof Long || value instanceof Float || value instanceof Double || value instanceof Short || value instanceof Boolean)
			{
				bytes = String.valueOf(value).getBytes();
			} else
			{
				Schema<DataWrapper> schema = RuntimeSchema.getSchema(DataWrapper.class);
				LinkedBuffer tmpBuffer = LinkedBuffer.allocate(256);

				bytes = ProtostuffIOUtil.toByteArray((DataWrapper) value, schema, tmpBuffer);
				tmpBuffer.clear();
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
		latch.await();
	}

	public void pause(long timeout, TimeUnit unit) throws InterruptedException
	{
		latch.await(timeout, unit);
	}

	public void resume()
	{
		latch.countDown();
	}

	public Exception getException()
	{
		return exception;
	}

	public void setException(Exception exception)
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
