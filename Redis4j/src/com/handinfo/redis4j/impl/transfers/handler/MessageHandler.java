package com.handinfo.redis4j.impl.transfers.handler;

import java.util.concurrent.BlockingQueue;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.handinfo.redis4j.api.ISession;
import com.handinfo.redis4j.api.RedisCommand;
import com.handinfo.redis4j.api.RedisResponse;
import com.handinfo.redis4j.api.exception.CleanLockedThreadException;
import com.handinfo.redis4j.api.exception.NetworkException;
import com.handinfo.redis4j.api.exception.UnknownException;
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

	/**
	 * 进入锁
	 * if(如果清理线程已经启动,不允许写入 或者 网络连接是断开状态) 保证开始清理后和网络不通的情况下写入的请求被放弃，直接返回异常
	 * 设置返回值的异常
	 * 解锁
	 * return;
	 * 判断要发送的命令
	 * if(如果是watch)
	 * {
	 * while(true)
	 * if(如果清理线程已经启动)
	 * {
	 * [标记Thread变量为null]
	 * 设置返回值的异常
	 * if(全部唤醒完毕)
	 * {
	 * 唤醒清理线程
	 * }
	 * 解锁
	 * return;
	 * }
	 * if(Thread变量 == null)
	 * {
	 * 说明是第一次进来的线程
	 * 来的是第一个watch,[标记Thread变量为当前线程],继续执行下面代码
	 * break;
	 * }
	 * else
	 * {
	 * if(如果当前线程 == Thread变量)
	 * break;
	 * 说明紧接着又来了一个watch
	 * Condition wait当前线程
	 * 恢复wait后
	 * }
	 * }
	 * else if(如果是exec)
	 * {
	 * if(如果当前线程是前一个watch进来时的线程)
	 * {
	 * 标记Thread变量为null,以保证下一个 watch指令能够正确被判断
	 * 对一个之前被Condition设置为wait状态的随机线程进行取消wait操作
	 * }
	 * else
	 * {
	 * 说明此exec所在的线程之前没有执行watch命令,否则会在前面被wait,这里应该等待到前面的watch线程中的exec执行完再执行此处代码
	 * }
	 * }
	 * else
	 * {
	 * 其它非控制指令
	 * }
	 * try(ABC)
	 * 发送消息
	 * 写入发送命令到队列
	 * catch
	 * {
	 * 断网时会触发清理线程
	 * }
	 * 解锁
	 * if(try(ABC) == true)
	 * {
	 * 当前线程pause,等待服务器返回后在resume
	 * }
	 */
	@Override
	public void writeRequested(ChannelHandlerContext ctx, MessageEvent e) throws Exception
	{
		CommandWrapper cmdWrapper = (CommandWrapper) e.getMessage();

		boolean isSendSucess = false;

		session.getChannelSyncLock().lock();

		if (!session.isAllowWrite().get() || !e.getChannel().isConnected())
		{
			if (!session.isAllowWrite().get())
			{
				cmdWrapper.setException(new CleanLockedThreadException());
			} else
			{
				cmdWrapper.setException(new NetworkException());
			}
			session.getChannelSyncLock().unlock();
			return;
		}

		try
		{
			RedisCommand lastCommand = cmdWrapper.getCommand();
			if (lastCommand == null)
			{
				lastCommand = (RedisCommand) cmdWrapper.getCmdList().get(cmdWrapper.getCmdList().size()-1)[0];
			}
			if (lastCommand != null)
			{
				switch (lastCommand)
				{
				case WATCH:
					while (true)
					{
						if (!session.isAllowWrite().get())
						{
							thread = null;
							cmdWrapper.setException(new CleanLockedThreadException());
							if (session.getTotalOfWaitCondition().get() == 0)
							{
								session.getCleanCondition().signal();
							}
							session.getChannelSyncLock().unlock();
							return;
						}

						if (thread != null)
						{
							if (Thread.currentThread().equals(thread))
							{
								break;
							}

							session.getTotalOfWaitCondition().incrementAndGet();
							this.session.getCondition().await();
							session.getTotalOfWaitCondition().decrementAndGet();
						} else
						{
							thread = Thread.currentThread();
							break;
						}
					}
					break;
				case EXEC:
					if (Thread.currentThread().equals(thread))
					{
						thread = null;
						this.session.getCondition().signal();
					}
					break;
				case UNWATCH:
					if (Thread.currentThread().equals(thread))
					{
						thread = null;
						this.session.getCondition().signal();
					}
					break;
				case DISCARD:
					if (Thread.currentThread().equals(thread))
					{
						thread = null;
						this.session.getCondition().signal();
					}
					break;
				default:
					break;
				}
			}

			try
			{
				ctx.sendDownstream(e);
				commandQueue.add(cmdWrapper);
				isSendSucess = true;
			}
			catch (Exception ex)
			{
				isSendSucess = false;
				cmdWrapper.setException(new UnknownException(ex.getCause()));
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			session.getChannelSyncLock().unlock();
		}

		if (isSendSucess)
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
		// e.getCause().printStackTrace();
		ctx.sendDownstream(e);
	}
}
