/**
 * 
 */
package org.elk.redis4j.api;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.elk.redis4j.api.async.IRedisAsyncClient;
import org.elk.redis4j.api.exception.CleanLockedThreadException;
import org.elk.redis4j.api.exception.ErrorCommandException;
import org.elk.redis4j.impl.util.CommandWrapper;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelPipeline;


/**
 * @author srz
 */
public interface ISession
{
	public AtomicInteger getTotalOfWaitCondition();
	
	public Condition getCleanCondition();
	
	public Condition getCondition();
	
	public String getName();
	
	public void setChannel(Channel channel);
	
	public void setPipiline(ChannelPipeline pipeline);
	
	public ChannelPipeline getPipiline();
	
	public void reConnect();

	public boolean isUseHeartbeat();

	public boolean isConnected();
	
	public int getHeartbeatTime();

	public int getReconnectDelay();

	public ReentrantLock getChannelSyncLock();

	public AtomicBoolean isAllowWrite();

	public BlockingQueue<CommandWrapper> getCommandQueue();

	public InetSocketAddress getRemoteAddress();

	public boolean isStartClose();

	public RedisResponse executeCommand(RedisCommand command, Object... args);
	
	public List<RedisResponse> executeBatch(List<Object[]> commandList);

	public void executeAsyncCommand(IRedisAsyncClient.Result notify, RedisCommand command, Object... args) throws InterruptedException;
	
	public void cleanCommandQueue();
	
	public void close();
}
