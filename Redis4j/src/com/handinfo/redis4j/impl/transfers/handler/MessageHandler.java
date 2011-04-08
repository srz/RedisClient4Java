package com.handinfo.redis4j.impl.transfers.handler;

import java.util.concurrent.BlockingQueue;

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
			if(cmdWrapper != null)
			{
				break;
			}
			Thread.sleep(0);
		}
		
		if (cmdWrapper.getType() == CommandWrapper.Type.ASYNC)
		{
			cmdWrapper.addAsyncResult((RedisResponse) e.getMessage());
		}
		else
		{
			if(cmdWrapper.surplusLockedCommand() == 0)
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
