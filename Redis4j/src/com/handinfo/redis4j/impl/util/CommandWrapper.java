package com.handinfo.redis4j.impl.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

import com.handinfo.redis4j.api.RedisCommand;
import com.handinfo.redis4j.api.RedisResponse;
import com.handinfo.redis4j.api.exception.RedisClientException;

public class CommandWrapper
{
	private Type type;
	private ChannelBuffer value;
	private CountDownLatch latch;
	private RedisClientException exception;
	private List<RedisResponse> result;

	private AtomicInteger totalOfCommand = new AtomicInteger();

	private AtomicBoolean isPause = new AtomicBoolean(false);
	private AtomicBoolean isResume = new AtomicBoolean(false);
	private List<Object[]> cmdList;
	private BlockingQueue<RedisResponse> asyncResult;
	
	private RedisCommand command;

	public List<Object[]> getCmdList()
	{
		return cmdList;
	}

	public enum Type
	{
		SYNC, ASYNC;
	}

	public CommandWrapper(RedisCommand cmd, Type type, Object[] commandContent)
	{
		this.command = cmd;
		this.type = type;
		this.value = getBinaryCommand(commandContent);
		this.totalOfCommand.set(1);
		this.result = new ArrayList<RedisResponse>(1);

		this.exception = null;

		if (this.type == CommandWrapper.Type.SYNC)
			this.latch = new CountDownLatch(1);
		else
			asyncResult = new LinkedBlockingQueue<RedisResponse>();
	}

	public CommandWrapper(List<Object[]> commandList)
	{
		cmdList = commandList;
		this.type = CommandWrapper.Type.SYNC;
		this.value = ChannelBuffers.dynamicBuffer();
		for (Object[] singleCmd : commandList)
		{
			RedisCommand command = (RedisCommand)singleCmd[0];
			Object[] fullCmd = new Object[command.getValue().length + singleCmd.length - 1];
			System.arraycopy(command.getValue(), 0, fullCmd, 0, command.getValue().length);
			System.arraycopy(singleCmd, 1, fullCmd, command.getValue().length, singleCmd.length - 1);
			this.value.writeBytes(getBinaryCommand(fullCmd));
		}
		this.totalOfCommand.set(commandList.size());
		this.result = new ArrayList<RedisResponse>(commandList.size());

		this.exception = null;

		if (this.type == CommandWrapper.Type.SYNC)
			this.latch = new CountDownLatch(1);
		else
			asyncResult = new LinkedBlockingQueue<RedisResponse>();
	}

	public RedisCommand getCommand()
	{
		return command;
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

	public Type getType()
	{
		return type;
	}

	public ChannelBuffer getValue()
	{
		return value;
	}

	public void pause() throws InterruptedException
	{
		if (type == CommandWrapper.Type.SYNC)
		{
			if (!isPause.getAndSet(true))
			{
				latch.await();
			}
		}
	}

	public void pause(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException
	{
		if (type == CommandWrapper.Type.SYNC)
		{
			if (!isPause.getAndSet(true))
			{
				latch.await(timeout, unit);
			}
		}
	}

	public int surplusLockedCommand()
	{
		return this.totalOfCommand.decrementAndGet();
	}

	public void resume() throws InterruptedException
	{
		if (type == CommandWrapper.Type.SYNC)
		{
			if (!isResume.getAndSet(true))
			{
				latch.countDown();
			}
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

	public List<RedisResponse> getSyncResult()
	{
		return result;
	}

	public void addSyncResult(RedisResponse response)
	{
		this.result.add(response);
	}

	public RedisResponse getAsyncResult() throws InterruptedException
	{
		return asyncResult.take();
	}
	
	public void addAsyncResult(RedisResponse response) throws InterruptedException
	{
		if (this.type == CommandWrapper.Type.ASYNC)
			asyncResult.put(response);
	}
}
