package com.handinfo.redis4j.impl.transfers.handler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.handinfo.redis4j.api.ISession;
import com.handinfo.redis4j.api.RedisResponse;
import com.handinfo.redis4j.api.exception.CleanLockedThreadException;
import com.handinfo.redis4j.impl.util.CommandWrapper;

public class MessageHandler extends SimpleChannelHandler
{
	private BlockingQueue<CommandWrapper> commandQueue;
	private ISession session;
	//private Condition condition;

	private AtomicBoolean isWatchFinish = new AtomicBoolean(true);

	// private ThreadLocal<Boolean> isHaveLocked = new
	// ThreadLocal<Boolean>()
	// {
	// @Override
	// protected Boolean initialValue()
	// {
	// return false;
	// }
	// };

	public MessageHandler(ISession session)
	{
		super();
		this.session = session;
		//this.condition = session.getChannelSyncLock().newCondition();
		commandQueue = session.getCommandQueue();
	}

	@Override
	public void writeRequested(ChannelHandlerContext ctx, MessageEvent e) throws Exception
	{
		CommandWrapper cmdWrapper = (CommandWrapper) e.getMessage();

		// if (isHaveLocked.get() == false)
		{
			session.getChannelSyncLock().lock();
			// isHaveLocked.set(true);
		}

		//boolean b = false;
		switch (cmdWrapper.getCommand())
		{
		case WATCH:
			// if (isWatchFinish.get())
			// {
			// isWatchFinish.set(false);
			// } else
			// {
			// condition.await();
			// isWatchFinish.set(false);
			// }

			if (isWatchFinish.get())
			{
				isWatchFinish.set(false);
			} else
			{
				while (!isWatchFinish.get())
				{
					this.session.getCondition().await();
				}
			}
			break;
		case EXEC:
			isWatchFinish.set(true);
			this.session.getCondition().signalAll();
			//b = true;
			// isHaveLocked.set(false);
			break;
		case UNWATCH:
			isWatchFinish.set(true);
			this.session.getCondition().signalAll();
			// isHaveLocked.set(false);
			break;
		case DISCARD:
			isWatchFinish.set(true);
			this.session.getCondition().signalAll();
			// isHaveLocked.set(false);
			break;
		default:
			break;
		}

		// if (!isWatchFinish.get())
		// {
		// condition.await();
		// }
		try
		{
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

//			if (b && isWatchFinish.get())
//				condition.signalAll();
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

		RedisResponse res = (RedisResponse) e.getMessage();

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
