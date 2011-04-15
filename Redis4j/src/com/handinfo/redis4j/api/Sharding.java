package com.handinfo.redis4j.api;

import java.net.InetSocketAddress;

public class Sharding
{
	private InetSocketAddress serverAddress;
	private int defaultIndexDB;
	private String password;

	private int heartbeatTime;
	private int reconnectDelay;
	private String name;

	public Sharding()
	{
		this.defaultIndexDB = 0;
		this.heartbeatTime = IConnection.IDEL_TIMEOUT_PING;
		this.reconnectDelay = IConnection.RECONNECT_DELAY;
		this.isUseHeartbeat = true;
		this.password = "";
		this.name = "";
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	private boolean isUseHeartbeat;

	/**
	 * @return the serverAddress
	 */
	public InetSocketAddress getServerAddress()
	{
		return serverAddress;
	}

	/**
	 * @param serverAddress
	 *            the serverAddress to set
	 */
	public void setServerAddress(InetSocketAddress serverAddress)
	{
		this.serverAddress = serverAddress;
	}

	/**
	 * @return the defaultIndexDB
	 */
	public int getDefaultIndexDB()
	{
		return defaultIndexDB;
	}

	/**
	 * @param defaultIndexDB
	 *            the defaultIndexDB to set
	 */
	public void setDefaultIndexDB(int defaultIndexDB)
	{
		this.defaultIndexDB = defaultIndexDB;
	}

	/**
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * @return the heartbeatTime
	 */
	public int getHeartbeatTime()
	{
		return heartbeatTime;
	}

	/**
	 * @param heartbeatTime
	 *            the heartbeatTime to set
	 */
	public void setHeartbeatTime(int heartbeatTime)
	{
		this.heartbeatTime = heartbeatTime;
	}

	/**
	 * @return the reconnectDelay
	 */
	public int getReconnectDelay()
	{
		return reconnectDelay;
	}

	/**
	 * @param reconnectDelay
	 *            the reconnectDelay to set
	 */
	public void setReconnectDelay(int reconnectDelay)
	{
		this.reconnectDelay = reconnectDelay;
	}

	/**
	 * @return the isUseHeartbeat
	 */
	public boolean isUseHeartbeat()
	{
		return isUseHeartbeat;
	}

	/**
	 * @param isUseHeartbeat
	 *            the isUseHeartbeat to set
	 */
	public void setUseHeartbeat(boolean isUseHeartbeat)
	{
		this.isUseHeartbeat = isUseHeartbeat;
	}
	
	public Sharding clone()
	{
		Sharding sharding = new Sharding();
		sharding.setDefaultIndexDB(defaultIndexDB);
		sharding.setHeartbeatTime(heartbeatTime);
		sharding.setPassword(password);
		sharding.setReconnectDelay(reconnectDelay);
		sharding.setServerAddress(serverAddress);
		sharding.setUseHeartbeat(isUseHeartbeat);
		
		return sharding;
	}
}
