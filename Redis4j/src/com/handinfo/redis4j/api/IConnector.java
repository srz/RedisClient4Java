package com.handinfo.redis4j.api;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;

import com.handinfo.redis4j.api.exception.CleanLockedThreadException;
import com.handinfo.redis4j.api.exception.ErrorCommandException;
import com.handinfo.redis4j.impl.util.CommandWrapper;

public interface IConnector
{

	public boolean connect() throws IllegalStateException, CleanLockedThreadException, ErrorCommandException;
	
	public boolean isUseHeartbeat();
	
	public boolean getIsConnected();
	
	public ReentrantLock getLock();
	
	public AtomicBoolean getIsAllowWrite();
	
	public BlockingQueue<CommandWrapper> getCommandQueue();
	
	public int getHeartbeatTime();
	
	public int getReconnectDelay();
	
	public InetSocketAddress getRemoteAddress();
	
	public boolean getIsStartClose();

	public RedisResponse executeCommand(RedisCommand command, Object... args) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException;

	public void executeAsyncCommand(IRedis4jAsync.Notify notify, RedisCommand command, Object... args) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException, InterruptedException, BrokenBarrierException;
	
	public RedisResponse[] executeBatch(ArrayList<String[]> commandList) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException;
	
	public void disConnect();
	
	public void cleanCommandQueue();

}