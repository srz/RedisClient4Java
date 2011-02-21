package com.handinfo.redis4j.impl;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.handinfo.redis4j.api.IRedis4j;
import com.handinfo.redis4j.api.RedisCommandType;
import com.handinfo.redis4j.api.RedisResultType;
import com.handinfo.redis4j.impl.transfers.Connector;

public class Redis4jClient implements IRedis4j
{
	private String host;
	private int port;
	private Connector connector;

	public Redis4jClient(String host, int port)
	{
		this.host = host;
		this.port = port;
	}

	@Override
	public boolean auth(String password)
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String echo(String message)
	{
		Object[] result = connector.executeCommand(RedisCommandType.ECHO, message);
		if (result.length > 1)
		{
			if (result[0].toString().equalsIgnoreCase("$"))
			{
				return (String) result[1];
			}
		}

		return null;
	}

	@Override
	public boolean ping()
	{
		Object[] result = connector.executeCommand(RedisCommandType.PING);
		if (result.length > 1)
		{
			if (result[0].toString().equalsIgnoreCase("+"))
			{
				if (((String) result[1]).equalsIgnoreCase(RedisResultType.PONG))
				{
					return true;
				}
			}
		}

		return false;
	}

	//
	@Override
	public boolean quit()
	{
		return connector.disconnect();
	}

	@Override
	public boolean select(int dbIndex)
	{
		// TODO Auto-generated method stub
		return true;
	}

	/*
	 * 创建到Redis服务器的连接，需要调用quit()函数关闭此连接
	 */
	@Override
	public boolean connect()
	{
		connector = new Connector();

		return connector.connect(host, port);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Object get(String key, Class<T> valueType) throws UnsupportedEncodingException, InstantiationException, IllegalAccessException
	{
		Object[] result = connector.executeCommand(RedisCommandType.GET, key);
		if (result.length > 1)
		{
			if (result[0].toString().equalsIgnoreCase("$"))
			{
				if (valueType.equals(String.class))
				{
					// 字符串反解码
					return new String((byte[]) result[1], Charset.forName("UTF-8"));
				} else
				{
					// 二进制反解码
					Schema<T> schema = RuntimeSchema.getSchema(valueType.getClass().getName(), true);
					T object = (T) new Object();//valueType.getClass().newInstance();
					ProtostuffIOUtil.mergeFrom((byte[])result[1], object, schema);
					return object;
				}
			}
		}

		return null;
	}

	@Override
	public <T> boolean set(String key, T value)
	{
		byte[] binaryData = null;

		if (value instanceof String)
		{
			binaryData = ((String) value).getBytes();
		} else
		{
			Schema<T> schema = RuntimeSchema.getSchema(value.getClass().getName(), true);
			LinkedBuffer buffer = LinkedBuffer.allocate(256);

			binaryData = ProtostuffIOUtil.toByteArray(value, schema, buffer);
			buffer.clear();
		}

		Object[] result = connector.executeCommand(RedisCommandType.SET, key, binaryData);
		if (result.length > 1)
		{
			if (result[0].toString().equalsIgnoreCase("+"))
			{
				if (((String) result[1]).equalsIgnoreCase(RedisResultType.OK))
				{
					return true;
				}
			}
		}

		return false;
	}

}
