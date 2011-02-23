package com.handinfo.redis4j.impl.protocol.encode;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.handinfo.redis4j.api.DataWrapper;

public class RedisCommandEncoder
{
	@SuppressWarnings("unchecked")
	public static ChannelBuffer getBinaryCommand(Object[] command)
	{
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();

		// 写入头
		buffer.writeBytes("*".getBytes());
		buffer.writeBytes(String.valueOf(command.length).getBytes());
		buffer.writeBytes("\r\n".getBytes());

		// 写入参数
		for (int i = 0; i < command.length; i++)
		{
			Object value = command[i];
			byte[] bytes = null;
			if (value instanceof String || value instanceof Integer || value instanceof Long || value instanceof Float || value instanceof Double || value instanceof Short || value instanceof Boolean)
			{
				bytes = String.valueOf(value).getBytes();
			} else if (value instanceof DataWrapper)
			{
				Schema<DataWrapper> schema = RuntimeSchema.getSchema(DataWrapper.class);
				LinkedBuffer tmpBuffer = LinkedBuffer.allocate(256);

				bytes = ProtostuffIOUtil.toByteArray((DataWrapper) value, schema, tmpBuffer);
				tmpBuffer.clear();
			} else
			{
				// TODO 其他情况?
			}
			if (bytes != null)
			{
				buffer.writeBytes("$".getBytes());
				buffer.writeBytes(String.valueOf(bytes.length).getBytes());
				buffer.writeBytes("\r\n".getBytes());
				buffer.writeBytes(bytes);
				buffer.writeBytes("\r\n".getBytes());
			}
		}

		return buffer;
	}
}
