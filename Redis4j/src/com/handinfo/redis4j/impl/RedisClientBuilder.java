package com.handinfo.redis4j.impl;

public class RedisClientBuilder
{
	private String host;
	private int port;
	private int poolMaxSize;
	private int indexDB;
	private int heartbeatTime;
	private int reconnectDelay;
	private final static int IDEL_TIMEOUT_PING = 10;//默认检测连接空闲发送ping的间隔时间,单位是秒
	private final static int RECONNECT_DELAY = 10;//默认断网重连间隔时间,单位是秒
}
