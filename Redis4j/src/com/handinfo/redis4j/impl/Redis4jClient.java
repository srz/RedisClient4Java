package com.handinfo.redis4j.impl;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.handinfo.redis4j.api.DataType;
import com.handinfo.redis4j.api.IRedis4j;
import com.handinfo.redis4j.api.PackData;
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

	@Override
	public Object get(String key)
	{
		Object[] result = connector.executeCommand(RedisCommandType.GET, key);
		if (result.length > 1)
		{
			if (result[0].toString().equalsIgnoreCase("$"))
			{				
				PackData packData = new PackData();
				// 二进制反解码
				Schema<PackData> schema = RuntimeSchema.getSchema(PackData.class);
				ProtostuffIOUtil.mergeFrom((byte[]) result[1], packData, schema);
				return packData;
			}
		}
		return null;
	}


	@SuppressWarnings("unchecked")
	@Override
	public <T> boolean set(String key, T value)
	{
		PackData packData = new PackData();
		packData.setDataType(value.getClass());
		packData.setOriginal(value);
		Object[] result = connector.executeCommand(RedisCommandType.SET, key, packData);
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

	private <T> byte[] encodeObjectToByteArray(T value)
	{
		byte[] binaryData = null;

		if (value instanceof String || value instanceof Integer || value instanceof Long || value instanceof Float || value instanceof Double || value instanceof Short || value instanceof Boolean)
		{
			binaryData = String.valueOf(value).getBytes();
		} else
		{
			Schema<T> schema = RuntimeSchema.getSchema(value.getClass().getName(), true);
			LinkedBuffer buffer = LinkedBuffer.allocate(256);

			binaryData = ProtostuffIOUtil.toByteArray(value, schema, buffer);
			buffer.clear();
		}
		return binaryData;
	}

	private <T> void decodeObjectFromByteArray(T returnValue, byte[] data)
	{
		if (returnValue instanceof StringBuilder)
		{
			// 字符串反解码
			String backVal = new String(data, Charset.forName("UTF-8"));
			((StringBuilder) returnValue).append(backVal);
		} else if (returnValue instanceof Integer || returnValue instanceof Long || returnValue instanceof Float || returnValue instanceof Double || returnValue instanceof Short || returnValue instanceof Boolean)
		{
			String backVal = new String(data, Charset.forName("UTF-8"));
			// backVal.
		} else
		{
			// 二进制反解码
			Schema<T> schema = RuntimeSchema.getSchema(returnValue.getClass().getName(), true);
			ProtostuffIOUtil.mergeFrom((byte[]) data, returnValue, schema);
		}
	}
}
