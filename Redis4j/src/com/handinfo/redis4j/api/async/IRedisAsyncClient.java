package com.handinfo.redis4j.api.async;

import java.util.concurrent.BrokenBarrierException;

import com.handinfo.redis4j.api.RedisCommand;
import com.handinfo.redis4j.api.exception.CleanLockedThreadException;
import com.handinfo.redis4j.api.exception.ErrorCommandException;

public interface IRedisAsyncClient
{

	/**
	 * 异步返回服务器的结果
	 */
	public interface Result
	{

		/**
		 * 在创建此接口的当前线程中执行,在无返回结果时线程处于等待状态,在有返回结果时,线程自动唤醒并执行doInCurrentThread函数
		 * 
		 * @param result
		 *            服务器发回的结果
		 */
		public void doInCurrentThread(String result);
	}

	/**
	 * 执行异步命令
	 * 
	 * @param command
	 *            执行的异步命令, 如果传入非异步命令会抛出异常
	 * @param result
	 *            处理异步返回的结果
	 * @throws CleanLockedThreadException
	 * @throws ErrorCommandException
	 * @throws IllegalStateException
	 * @throws InterruptedException
	 * @throws BrokenBarrierException
	 */
	public void executeCommand(RedisCommand command, Result result) throws InterruptedException;

	/**
	 * 退出客户端
	 */
	public void quit();

}