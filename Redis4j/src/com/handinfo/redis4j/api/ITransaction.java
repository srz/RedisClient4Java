package com.handinfo.redis4j.api;

/**
 * 事物操作
 */
public interface ITransaction
{
	/**
	 * 放弃事物
	 */
	public boolean discard();

	/**
	 * 解除标记某Key
	 */
	public boolean unwatch();

	/**
	 * 标记某Key,以防止事物提交期间该Key被别的线程修改
	 */
	public boolean watch();
	
	/**
	 * 提交事物
	 */
	public void commit();
}
