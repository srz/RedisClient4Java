package com.handinfo.redis4j.impl.protocol.encode;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

public class RedisCommandEncoder
{
	public static ChannelBuffer getBinaryCommand(Object[] command)
	{
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		
		//写入头
		buffer.writeBytes("*".getBytes());
		buffer.writeBytes(String.valueOf(command.length).getBytes());
		buffer.writeBytes("\r\n".getBytes());
		
		//写入参数
		for (int i = 0; i < command.length; i++)
		{
			if(command[i] instanceof String)
			{
				String data = (String) command[i];
				buffer.writeBytes("$".getBytes());
				buffer.writeBytes(String.valueOf(data.length()).getBytes());
				buffer.writeBytes("\r\n".getBytes());
				buffer.writeBytes(data.getBytes());
				buffer.writeBytes("\r\n".getBytes());
			}
		}
		
		return buffer;
	}
}
