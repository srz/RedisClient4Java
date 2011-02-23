package com.handinfo.redis4j.impl.transfers;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

import com.handinfo.redis4j.impl.protocol.RedislHandler;
import com.handinfo.redis4j.impl.protocol.decode.ByteArrayToFrameDecoder;
import com.handinfo.redis4j.impl.protocol.decode.FrameToObjectByteArrayDecoder;

public class PipelineFactory implements ChannelPipelineFactory
{

	public ChannelPipeline getPipeline() throws Exception
	{
		ChannelPipeline pipeline = Channels.pipeline();

		pipeline.addLast("framerDecoder", new ByteArrayToFrameDecoder());
		pipeline.addLast("objectDecoder", new FrameToObjectByteArrayDecoder());
		
		pipeline.addLast("handler", new RedislHandler());

		return pipeline;
	}
}