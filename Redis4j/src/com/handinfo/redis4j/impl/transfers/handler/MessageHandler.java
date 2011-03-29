package com.handinfo.redis4j.impl.transfers.handler;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.handinfo.redis4j.api.IConnector;
import com.handinfo.redis4j.api.RedisResponse;
import com.handinfo.redis4j.api.exception.CleanLockedThreadException;
import com.handinfo.redis4j.impl.util.CommandWrapper;

public class MessageHandler extends SimpleChannelHandler
{

	private static final Logger logger = Logger.getLogger(MessageHandler.class.getName());
	private BlockingQueue<CommandWrapper> commandQueue;
	private IConnector connector;

	public MessageHandler(IConnector connector)
	{
		super();
		this.connector = connector;
		commandQueue = connector.getCommandQueue();
	}

	@Override
	public void writeRequested(ChannelHandlerContext ctx, MessageEvent e) throws Exception
	{
		CommandWrapper cmdWrapper = (CommandWrapper) e.getMessage();

		connector.getLock().lock();
		try
		{
			if (connector.getIsAllowWrite().get() && e.getChannel().isConnected())
			{
				ctx.sendDownstream(e);
				commandQueue.add(cmdWrapper);
			} else
			{
				cmdWrapper.setException(new CleanLockedThreadException());
			}
		} finally
		{
			connector.getLock().unlock();
		}

		if (connector.getIsAllowWrite().get() && e.getChannel().isConnected())
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
		}
		if (cmdWrapper != null)
		{
			if (cmdWrapper.getType().equals(CommandWrapper.Type.ASYNC))
			{
				cmdWrapper.addResult((RedisResponse) e.getMessage());
				cmdWrapper.resume();
			}
			else
			{

				cmdWrapper.addResult((RedisResponse) e.getMessage());
				if(cmdWrapper.surplusLockedCommand() == 0)
				{
					this.commandQueue.remove(cmdWrapper);
					cmdWrapper.resume();
				}
			}
		} else
		{
			printMsg(Level.WARNING, "If you found this,please tell me,this is a bug!");
		}
		super.messageReceived(ctx, e);
	}

	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception
	{
		e.getCause().printStackTrace();
		ctx.sendDownstream(e);
	}

	private void printMsg(Level level, String msg)
	{
		logger.log(level, "Thread name:" + Thread.currentThread().getName() + " - ID:" + Thread.currentThread().getId() + " - " + msg);
	}

}
