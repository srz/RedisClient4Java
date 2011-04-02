package com.handinfo.redis4j.impl.util;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

import com.handinfo.redis4j.api.RedisResponse;
import com.handinfo.redis4j.api.exception.RedisClientException;

public class CommandWrapper
{
	private Type type;
	private ChannelBuffer value;
	private CountDownLatch latch;
	private CyclicBarrier barrier;
	private RedisClientException exception;
	private RedisResponse[] result;
	private int resultIndex = 0;;
	
	private AtomicInteger totalOfCommand = new AtomicInteger();

	private AtomicBoolean isPause = new AtomicBoolean(false);
	private AtomicBoolean isResume = new AtomicBoolean(false);
	private ArrayList<Object[]> cmdList;

	public ArrayList<Object[]> getCmdList()
	{
		return cmdList;
	}

	public enum Type
	{
		SYNC, ASYNC;
	}

	public CommandWrapper(Type type, Object[] command)
	{
		this.type = type;
		this.value = getBinaryCommand(command);
		this.totalOfCommand.set(1);
		result = new RedisResponse[this.totalOfCommand.get()];

		this.exception = null;

		if (this.type == CommandWrapper.Type.SYNC)
			this.latch = new CountDownLatch(1);
		else
			this.barrier = new CyclicBarrier(2);
	}

	public CommandWrapper(ArrayList<Object[]> commandList)
	{
		cmdList = commandList;
		this.type = CommandWrapper.Type.SYNC;
		this.value = ChannelBuffers.dynamicBuffer();
		for (Object[] cmd : commandList)
		{
			this.value.writeBytes(getBinaryCommand(cmd));
		}
		this.totalOfCommand.set(commandList.size());
		result = new RedisResponse[this.totalOfCommand.get()];

		this.exception = null;

		if (this.type == CommandWrapper.Type.SYNC)
			this.latch = new CountDownLatch(1);
		else
			this.barrier = new CyclicBarrier(2);
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
			}
			else
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

	public void pause() throws InterruptedException, BrokenBarrierException
	{
		if (type == CommandWrapper.Type.SYNC)
		{
			if (!isPause.getAndSet(true))
			{
				latch.await();
			}
		} else
		{
			barrier.reset();
			barrier.await();
		}
	}

	public void pause(long timeout, TimeUnit unit) throws InterruptedException, BrokenBarrierException, TimeoutException
	{
		if (type == CommandWrapper.Type.SYNC)
		{
			if (!isPause.getAndSet(true))
			{
				latch.await(timeout, unit);
			}
		} else
		{
			barrier.reset();
			barrier.await(timeout, unit);
		}
	}

	public int surplusLockedCommand()
	{
		return this.totalOfCommand.decrementAndGet();
	}
	
	public void resume() throws InterruptedException, BrokenBarrierException
	{
		if (type == CommandWrapper.Type.SYNC)
		{
			if (!isResume.getAndSet(true))
			{
				latch.countDown();
			}
		} else
		{
			barrier.await();
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

	public RedisResponse[] getResult()
	{
		return result;
	}

	public void addResult(RedisResponse response)
	{
		result[resultIndex] = response;
		resultIndex++;
	}
}
