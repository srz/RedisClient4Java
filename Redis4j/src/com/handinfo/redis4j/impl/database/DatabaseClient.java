package com.handinfo.redis4j.impl.database;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.handinfo.redis4j.api.RedisCommand;
import com.handinfo.redis4j.api.RedisResponse;
import com.handinfo.redis4j.api.RedisResponseMessage;
import com.handinfo.redis4j.api.RedisResponseType;
import com.handinfo.redis4j.api.Sharding;
import com.handinfo.redis4j.api.database.IDataBaseConnector;
import com.handinfo.redis4j.api.exception.CleanLockedThreadException;
import com.handinfo.redis4j.api.exception.ErrorCommandException;
import com.handinfo.redis4j.impl.util.LogUtil;

public abstract class DatabaseClient
{
	private final Logger logger = LogUtil.getLogger(DatabaseClient.class.getName());
	protected IDataBaseConnector connector;
	private Sharding sharding;

	public DatabaseClient(Sharding sharding)
	{
		this.sharding = sharding;
		connector = new DatabaseConnector(sharding);
		connector.connect();
		if (!connector.isConnected())
		{
			logger.severe("can not connect to server,client will reconnect after " + this.sharding.getReconnectDelay() + " s");
		}
	}

	public int totalOfConnected()
	{
		return connector.isConnected() ? 1 : 0;
	}

	/**
	 * @return the host 连接信息
	 */
	public Sharding getShardInfo()
	{
		return this.sharding.clone();
	}

	private <T, V> T castResult(Class<T> classType, V arg, RedisResponseMessage compareValue)
	{
		if (arg == null)
			return null;
		if (classType == String.class)
		{
			return classType.cast(arg);
		} else if (classType == Integer.class)
			return classType.cast(Integer.valueOf((String) arg));
		else if (classType == Long.class)
			return classType.cast(Long.valueOf((String) arg));
		else if (classType == Double.class)
			return classType.cast(Double.valueOf((String) arg));
		else if (classType == Boolean.class)
		{
			if (compareValue != null)
				return classType.cast(Boolean.valueOf(((String) arg).equalsIgnoreCase(compareValue.getValue())));
			else
				return classType.cast(Boolean.FALSE);
		} else
			return null;
	}

	/**
	 * 发送请求
	 * 
	 * @param <T>
	 *                classType 返回数据类型
	 * @param compareValue
	 *                与返回结果做对比用的数据,以生成boolean型返回值
	 * @param command
	 *                发送的命令
	 * @param args
	 *                命令参数
	 * @return
	 * @throws IllegalStateException
	 * @throws CleanLockedThreadException
	 * @throws ErrorCommandException
	 */
	public <T> T sendRequest(Class<T> classType, RedisResponseMessage compareValue, RedisCommand command, Object... args)
	{
		RedisResponse response = connector.executeCommand(command, args);

		if (response != null)
		{
			// 判断返回类型是否为预期的结果类型
			if (command.getResponseType() == response.getType())
			{
				// 返回正确结果
				switch (response.getType())
				{
				case SingleLineReply:
				{
					return castResult(classType, response.getTextValue(), compareValue);
				}
				case IntegerReply:
				{
					return castResult(classType, response.getTextValue(), compareValue);
				}
				case BulkReplies:
				{
					if (response.getBulkValue() == null)
						return null;
					else
					{
						return castResult(classType, new String(response.getBulkValue(), Charset.forName("UTF-8")), compareValue);
					}
				}
				default:
					return null;
				}
			} else
			{
				// 返回的值有问题
				if (response.getType() == RedisResponseType.ErrorReply)
				{
					if (classType == Boolean.class)
						return castResult(classType, response.getTextValue(), compareValue);
					else
						throw new ErrorCommandException(response.getTextValue());
				}
			}
		}

		return null;
	}

	public List<String> sendRequestWithMultiReplay(RedisCommand command, Object... args)
	{
		RedisResponse response = connector.executeCommand(command, args);

		if (response != null)
		{
			if (RedisResponseType.MultiBulkReplies == response.getType())
			{
				if (response.getMultiBulkValue() != null)
				{
					List<String> result = new ArrayList<String>(response.getMultiBulkValue().size());

					for (RedisResponse res : response.getMultiBulkValue())
					{
						if (res.getBulkValue() != null)
						{
							result.add(new String(res.getBulkValue(), Charset.forName("UTF-8")));
						} else
						{
							result.add(null);
						}
					}
					return result;
				}
			}
			else if (RedisResponseType.ErrorReply == response.getType())
			{
				throw new ErrorCommandException(response.getTextValue());
			}
		}

		return null;
	}
}
