package com.handinfo.redis4j.impl.transfers.handler;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.handinfo.redis4j.api.IConnector;
import com.handinfo.redis4j.api.exception.CleanLockedThreadException;
import com.handinfo.redis4j.impl.Redis4jClient;
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
		CommandWrapper cmd = (CommandWrapper) e.getMessage();

		connector.getLock().lock();
		try
		{
			if (connector.getIsAllowWrite().get() && e.getChannel().isConnected())
			{
				ctx.sendDownstream(e);
				commandQueue.add(cmd);
			} else
			{
				cmd.setException(new CleanLockedThreadException());
			}
		} finally
		{
			connector.getLock().unlock();
		}

		if (connector.getIsAllowWrite().get() && e.getChannel().isConnected())
			cmd.pause();
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception
	{
		CommandWrapper cmd = null;
		while (true)
		{
			cmd = this.commandQueue.peek();
			if(cmd != null)
			{
				break;
			}
		}
		if (cmd != null)
		{
			
			if (cmd.getType().equals(CommandWrapper.Type.ASYNC))
			{
				cmd.addResult((Object[]) e.getMessage());
				cmd.resume();
			}
			else
			{

				cmd.addResult((Object[]) e.getMessage());
				if(cmd.surplusLockedCommand() == 0)
				{
					this.commandQueue.remove(cmd);
					cmd.resume();
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
