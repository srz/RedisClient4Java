package com.handinfo.redis4j.impl.transfers;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

import com.handinfo.redis4j.impl.protocol.RedislHandler;
import com.handinfo.redis4j.impl.protocol.decode.ProtocolReceiveFrameDecoder;
import com.handinfo.redis4j.impl.protocol.decode.ProtocolReceiveObjectDecoder;

public class PipelineFactory implements ChannelPipelineFactory
{

	public ChannelPipeline getPipeline() throws Exception
	{
		ChannelPipeline pipeline = Channels.pipeline();

		pipeline.addLast("framerDecoder", new ProtocolReceiveFrameDecoder());
		pipeline.addLast("objectDecoder", new ProtocolReceiveObjectDecoder());
		
		pipeline.addLast("handler", new RedislHandler());

		return pipeline;
	}
}