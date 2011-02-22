package com.handinfo.redis4j.impl.protocol.encode;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.handinfo.redis4j.api.PackData;

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
			if (command[i] instanceof String)
			{
				String data = (String) command[i];
				buffer.writeBytes("$".getBytes());
				buffer.writeBytes(String.valueOf(data.length()).getBytes());
				buffer.writeBytes("\r\n".getBytes());
				buffer.writeBytes(data.getBytes());
				buffer.writeBytes("\r\n".getBytes());
			}
			else if(command[i] instanceof PackData)
			{
				Schema<PackData> schema = RuntimeSchema.getSchema(PackData.class);
				LinkedBuffer tmpBuffer = LinkedBuffer.allocate(256);

				byte[] data = ProtostuffIOUtil.toByteArray((PackData)command[i], schema, tmpBuffer);
				tmpBuffer.clear();

				buffer.writeBytes("$".getBytes());
				buffer.writeBytes(String.valueOf(data.length).getBytes());
				buffer.writeBytes("\r\n".getBytes());
				buffer.writeBytes(data);
				buffer.writeBytes("\r\n".getBytes());
			}
			else
			{
				//TODO 其他情况?
			}
		}
	
		return buffer;
	}
}
