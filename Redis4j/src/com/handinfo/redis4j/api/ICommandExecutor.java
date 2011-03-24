package com.handinfo.redis4j.api;

public interface ICommandExecutor
{

	/**
	 * 返回类型为状态码的命令统一执行此函数
	 * 
	 * @param redisCommandType
	 *            命令类型
	 * @param RedisResultInfo
	 *            返回结果的期望值,如不符合则认为操作失败
	 * @return 操作结果是否成功
	 * @throws Exception 
	 */
	public boolean singleLineReplyForBoolean(String redisCommandType, String RedisResultInfo, Object... args) throws Exception;

	public String singleLineReplyForString(String redisCommandType, Object... args) throws Exception;

	/**
	 * 返回类型为状态码的命令统一执行此函数
	 * 
	 * @param redisCommandType
	 *            命令类型
	 * @param RedisResultInfo
	 *            返回结果的期望值,如不符合则认为操作失败
	 * @return 操作结果是否成功
	 * @throws Exception 
	 */
	public int integerReply(String redisCommandType, Object... args) throws Exception;

	/**
	 * 返回类型为单行数据的命令统一执行此函数
	 * 发送给redis的数据,如果被DataWrapper包装过,则参数isUseObjectDecoder应该为true,否则为false
	 * 
	 * @param redisCommandType
	 *            命令类型
	 * @param isUseObjectDecoder 是否使用对象序列化功能
	 * @param args
	 *            其它参数
	 * @return 从redis取得的对象,
	 *         因为解码时统一按照DataWrapper类型来解码
	 * @throws Exception 
	 */
	public Object bulkReply(String redisCommandType, boolean isUseObjectDecoder, Object... args) throws Exception;

	public Object[] multiBulkReply(String redisCommandType, boolean isUseObjectDecoder, Object... args) throws Exception;

}