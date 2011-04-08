package com.handinfo.redis4j.api;

/**
 * 事物操作
 */
public interface ITransaction
{
	/**
	 * 放弃事物
	 */
	public void discard();

	/**
	 * 解除标记某Key
	 */
	public void unwatch();

	/**
	 * 标记某Key,以防止事物提交期间该Key被别的线程修改
	 * @param keys TODO
	 */
	public void watch(String... keys);
	
	/**
	 * 提交事物
	 * @return TODO
	 */
	public Boolean commit();
}
