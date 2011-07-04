package org.elk.redis4j.api;

/**
 * 批处理操作
 */
public interface IBatch
{
	/**
	 * 提交此前的全部操作
	 */
	public void execute();
}
