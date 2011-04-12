/**
 * 
 */
package com.handinfo.redis4j.api;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelPipeline;

import com.handinfo.redis4j.api.async.IRedisAsyncClient;
import com.handinfo.redis4j.api.exception.CleanLockedThreadException;
import com.handinfo.redis4j.api.exception.ErrorCommandException;
import com.handinfo.redis4j.impl.util.CommandWrapper;

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

	public RedisResponse executeCommand(RedisCommand command, Object... args) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException;
	
	//TODO 可能用不上,最后再删
	public RedisResponse executeCommand(RedisCommand command, String key, Object... args) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException;
	
	public List<RedisResponse> executeBatch(ArrayList<Object[]> commandList) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException;

	public void executeAsyncCommand(IRedisAsyncClient.Result notify, RedisCommand command, Object... args) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException, InterruptedException, BrokenBarrierException;
	
	public void cleanCommandQueue();
	
	public void close();
}
