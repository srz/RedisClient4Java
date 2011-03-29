package com.handinfo.redis4j.api;

public interface IConnection
{
	/**
	 * 默认检测连接空闲发送ping的间隔时间,单位是秒
	 */
	public final static int IDEL_TIMEOUT_PING = 10;
	
	/**
	 * 默认断网重连间隔时间,单位是秒
	 */
	public final static int RECONNECT_DELAY = 10;
	
	/**
	 * 退出客户端程序
	 */
	public boolean quit();
}