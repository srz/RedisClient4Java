package com.handinfo.redis4j.impl;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.Delimiters;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

public class PipelineFactory implements ChannelPipelineFactory
{

	public ChannelPipeline getPipeline() throws Exception
	{
		// Create a default pipeline implementation.
		ChannelPipeline pipeline = Channels.pipeline();

		// pipeline.addLast("frameDecoder", new
		// ProtobufVarint32FrameDecoder());
		// pipeline.addLast("protobufDecoder", new
		// ProtobufDecoder(LocalTimeProtocol.LocalTimes.getDefaultInstance()));

		// pipeline.addLast("frameEncoder", new
		// ProtobufVarint32LengthFieldPrepender());
		// pipeline.addLast("protobufEncoder", new ProtobufEncoder());

		pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
		pipeline.addLast("decoder", new StringDecoder());

		//pipeline.addLast("encoder", new StringEncoder());
		pipeline.addLast("frameEncoder", new ProtocolSendHandler());

		// and then business logic.
		pipeline.addLast("handler", new RedislHandler());

		return pipeline;
	}
}