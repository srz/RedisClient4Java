package com.handinfo.redis4j.api;

import java.net.InetSocketAddress;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

import com.handinfo.redis4j.api.exception.CleanLockedThreadException;
import com.handinfo.redis4j.api.exception.ErrorCommandException;
import com.handinfo.redis4j.api.exception.RedisClientException;
import com.handinfo.redis4j.impl.util.CommandWrapper;

public interface IConnector
{

	public boolean connect() throws IllegalStateException, CleanLockedThreadException, ErrorCommandException;
	
	public ReentrantLock getLock();
	
	public AtomicBoolean getIsAllowWrite();
	
	public BlockingQueue<CommandWrapper> getCommandQueue();
	
	public int getHeartbeatTime();
	
	public int getReconnectDelay();
	
	public InetSocketAddress getRemoteAddress();
	
	public boolean getIsStartClose();

	public Object[] executeCommand(String commandType, Object... args) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException;

	public void disConnect();
	
	public void cleanCommandQueue();

}