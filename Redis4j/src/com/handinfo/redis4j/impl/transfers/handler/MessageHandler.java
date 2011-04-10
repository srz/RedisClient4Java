package com.handinfo.redis4j.impl.transfers.handler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.handinfo.redis4j.api.ISession;
import com.handinfo.redis4j.api.RedisCommand;
import com.handinfo.redis4j.api.RedisResponse;
import com.handinfo.redis4j.api.exception.CleanLockedThreadException;
import com.handinfo.redis4j.impl.util.CommandWrapper;

public class MessageHandler extends SimpleChannelHandler
{
	private BlockingQueue<CommandWrapper> commandQueue;
	private ISession session;
	private Thread thread = null;

	public MessageHandler(ISession session)
	{
		super();
		this.session = session;
		commandQueue = session.getCommandQueue();
	}

	@Override
	public void writeRequested(ChannelHandlerContext ctx, MessageEvent e) throws Exception
	{
		CommandWrapper cmdWrapper = (CommandWrapper) e.getMessage();

		session.getChannelSyncLock().lock();

		try
		{
			if (cmdWrapper.getCommand() != null)
			{
				switch (cmdWrapper.getCommand())
				{
				case WATCH:
					while (true)
					{
						if (thread != null)
						{
							if (!thread.equals(Thread.currentThread()))
							{
								this.session.getCondition().await();
								//TODO 这里逻辑有问题
								if(!e.getChannel().isConnected())
								{
									break;
								}
							} else
							{
								break;
							}
						} else
						{
							thread = Thread.currentThread();
							break;
						}
					}
					break;
				case EXEC:
					thread = null;
					this.session.getCondition().signal();
					break;
				case UNWATCH:
					break;
				case DISCARD:
					break;
				default:
					break;
				}
			}

			if (session.isAllowWrite().get() && e.getChannel().isConnected())
			{
				ctx.sendDownstream(e);
				commandQueue.add(cmdWrapper);
			} else
			{
				cmdWrapper.setException(new CleanLockedThreadException());
			}
		} finally
		{
			session.getChannelSyncLock().unlock();
		}

		if (session.isAllowWrite().get() && e.getChannel().isConnected() && cmdWrapper.getType() == CommandWrapper.Type.SYNC)
			cmdWrapper.pause();
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception
	{
		CommandWrapper cmdWrapper = null;

		while (true)
		{
			cmdWrapper = this.commandQueue.peek();
			if (cmdWrapper != null)
			{
				break;
			}
			Thread.sleep(0);
		}

		if (cmdWrapper.getType() == CommandWrapper.Type.ASYNC)
		{
			cmdWrapper.addAsyncResult((RedisResponse) e.getMessage());
		} else
		{
			if (cmdWrapper.surplusLockedCommand() == 0)
			{
				cmdWrapper.addSyncResult((RedisResponse) e.getMessage());
				this.commandQueue.remove(cmdWrapper);
				cmdWrapper.resume();
			}
		}

		super.messageReceived(ctx, e);
	}

	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception
	{
		e.getCause().printStackTrace();
		ctx.sendDownstream(e);
	}
}
