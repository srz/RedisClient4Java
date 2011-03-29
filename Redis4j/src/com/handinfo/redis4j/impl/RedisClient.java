package com.handinfo.redis4j.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.handinfo.redis4j.api.IConnector;
import com.handinfo.redis4j.api.RedisCommand;
import com.handinfo.redis4j.api.RedisResponse;
import com.handinfo.redis4j.api.RedisResponseMessage;
import com.handinfo.redis4j.api.RedisResponseType;
import com.handinfo.redis4j.api.exception.CleanLockedThreadException;
import com.handinfo.redis4j.api.exception.ErrorCommandException;
import com.handinfo.redis4j.impl.transfers.Connector;
import com.handinfo.redis4j.impl.util.ObjectWrapper;

public abstract class RedisClient
{
	private static final Logger logger = Logger.getLogger(RedisClient.class.getName());
	protected IConnector connector;
	private String host;
	private int port;
	private int indexDB;
	private int heartbeatTime;
	private int reconnectDelay;

	public RedisClient(String host, int port, int indexDB, int heartbeatTime, int reconnectDelay)
	{
		this.host = host;
		this.port = port;
		this.indexDB = indexDB;
		this.heartbeatTime = heartbeatTime;
		this.reconnectDelay = reconnectDelay;

		connector = new Connector(this.host, this.port, this.indexDB, this.heartbeatTime, this.reconnectDelay, true);

		if (!connector.connect())
		{
			logger.log(Level.WARNING, "can not connect to server,client will reconnect after " + this.reconnectDelay + " s");
		}
	}

	/**
	 * @return the host 主机地址
	 */
	public String getHost()
	{
		return host;
	}

	/**
	 * @return the port 主机端口
	 */
	public int getPort()
	{
		return port;
	}

	/**
	 * @return the indexDB 连接到的数据库
	 */
	public int getIndexDB()
	{
		return indexDB;
	}

	/**
	 * @return the heartbeatTime 心跳时间
	 */
	public int getHeartbeatTime()
	{
		return heartbeatTime;
	}

	/**
	 * @return the reconnectDelay 断网重连间隔时间
	 */
	public int getReconnectDelay()
	{
		return reconnectDelay;
	}

	/**
	 * 返回类型
	 */
	public boolean singleLineReplyForBoolean(RedisCommand command, RedisResponseMessage redisResultInfo, Object... args) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException
	{
		RedisResponse response = connector.executeCommand(command, args);

		if (response != null)
		{
			if (response.getType() == RedisResponseType.SingleLineReply)
			{
				if (response.getTextValue().equalsIgnoreCase(redisResultInfo.getValue()))
				{
					return true;
				}
			}
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.handinfo.redis4j.impl.ICommandExecutor#singleLineReplyForString(java
	 * .lang.String, java.lang.Object)
	 */
	public String singleLineReplyForString(RedisCommand command, Object... args) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException
	{
		RedisResponse response = connector.executeCommand(command, args);

		if (response != null)
		{
			if (response.getType() == RedisResponseType.SingleLineReply)
			{
				return response.getTextValue();
			}
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.handinfo.redis4j.impl.ICommandExecutor#integerReply(java.lang.String,
	 * java.lang.Object)
	 */
	public int integerReply(RedisCommand command, Object... args) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException
	{
		RedisResponse response = connector.executeCommand(command, args);

		if (response != null)
		{
			if (response.getType() == RedisResponseType.IntegerReply)
			{
				return Integer.valueOf(response.getTextValue());
			} else if (response.getType() == RedisResponseType.SingleLineReply)
			{
				// System.out.println(response.getTextValue());
				return -2;
			} else if (response.getType() == RedisResponseType.BulkReplies)
			{
				return -3;
			}
		}

		return -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.handinfo.redis4j.impl.ICommandExecutor#bulkReply(java.lang.String,
	 * boolean, java.lang.Object)
	 */
	public Object bulkReply(RedisCommand command, boolean isUseObjectDecoder, Object... args) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException
	{
		RedisResponse response = connector.executeCommand(command, args);

		if (response != null)
		{
			if (response.getType() == RedisResponseType.BulkReplies)
			{
				if (response.getBulkValue() != null)
				{
					if (isUseObjectDecoder)
					{
						ObjectWrapper<?> obj = new ObjectWrapper(response.getBulkValue());
						return obj.getOriginal();
					} else
						return new String(response.getBulkValue());
				}
			}
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.handinfo.redis4j.impl.ICommandExecutor#multiBulkReply(java.lang.String
	 * , boolean, java.lang.Object)
	 */
	public Object[] multiBulkReply(RedisCommand command, boolean isUseObjectDecoder, Object... args) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException
	{
		RedisResponse response = connector.executeCommand(command, args);

		if (response != null)
		{
			if (response.getType() == RedisResponseType.MultiBulkReplies)
			{
				if (response.getMultiBulkValue() != null)
				{
					int returnValueLength = response.getMultiBulkValue().size();

					if (isUseObjectDecoder)
					{
						Object[] returnValue = new Object[returnValueLength];

						for (int i = 0; i < returnValueLength; i++)
						{
							returnValue[i] = new ObjectWrapper(response.getMultiBulkValue().get(i)).getOriginal();
						}
						return returnValue;
					} else
					{
						String[] returnValue = new String[returnValueLength];

						for (int i = 0; i < returnValueLength; i++)
						{
							// TODO getMultiBulkValue很复杂,需要再考虑
							// returnValue[i] = new
							// String(response.getMultiBulkValue().get(i).getBulkValue());
							RedisResponse sonResponse = response.getMultiBulkValue().get(i);
							switch (sonResponse.getType())
							{
							case SingleLineReply:
							{
								returnValue[i] = sonResponse.getTextValue();
								break;
							}
							case ErrorReply:
							{
								returnValue[i] = sonResponse.getTextValue();
								break;
							}
							case IntegerReply:
							{
								returnValue[i] = sonResponse.getTextValue();
								break;
							}
							case BulkReplies:
							{
								returnValue[i] = new String(sonResponse.getBulkValue());
								break;
							}
							case MultiBulkReplies:
							{
								returnValue[i] = "";
								for (RedisResponse res : sonResponse.getMultiBulkValue())
								{
									returnValue[i] += new String(res.getBulkValue()) + " ";
								}
								returnValue[i] = returnValue[i].trim();
								break;
							}
							default:
								break;
							}
						}
						return returnValue;
					}
				}
			}
		}

		return null;
	}

	private <T> T abc(Class<T> classType, String arg)
	{
		if (classType.equals(Integer.class))
			return classType.cast(Integer.valueOf(arg));
		else if (classType.equals(Boolean.class))
			return classType.cast(Boolean.valueOf(arg));
		else if (classType.equals(String.class))
			return classType.cast(arg);
		else
			return null;
	}
	public <T> T sendRequest(Class<T> classType, RedisCommand command, Object... args) throws IllegalStateException, CleanLockedThreadException, ErrorCommandException
	{
		RedisResponse response = connector.executeCommand(command, args);

		if (response != null)
		{
			switch (command.getResponseType())
			{
			case SingleLineReply:
			{
				return classType.cast(Integer.valueOf(response.getTextValue()));
			}
			case IntegerReply:
			{
				return abc(classType, response.getTextValue());
			}
			case BulkReplies:
			{
				return classType.cast(response.getBulkValue());
			}
			case MultiBulkReplies:
			{
				return null;
			}
			case ErrorReply:
			{
				throw new ErrorCommandException(response.getTextValue());
			}
			default:
				return null;
			}
		}

		if (response != null)
		{
			switch (response.getType())
			{
			case SingleLineReply:
			{
				Object obj = null;
				return classType.cast(response.getTextValue());
			}
			case IntegerReply:
			{
				return null;
			}
			case BulkReplies:
			{
				return null;
			}
			case MultiBulkReplies:
			{
				return null;
			}
			case ErrorReply:
			{
				throw new ErrorCommandException(response.getTextValue());
			}
			default:
				return null;
			}
		}

		return null;
	}
}
