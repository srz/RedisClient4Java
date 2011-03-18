package com.handinfo.redis4j.impl.transfers;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timer;

import com.handinfo.redis4j.impl.protocol.Idelhandler;
import com.handinfo.redis4j.impl.protocol.RedislHandler;
import com.handinfo.redis4j.impl.protocol.decode.ByteArrayToFrameDecoder;
import com.handinfo.redis4j.impl.protocol.decode.FrameToObjectByteArrayDecoder;

public class PipelineFactory implements ChannelPipelineFactory
{
	private final Timer timer;

	/**
	 * @param connector
	 */
	public PipelineFactory()
	{
		super();
		this.timer = new HashedWheelTimer();
	}

	public ChannelPipeline getPipeline() throws Exception
	{
		ChannelPipeline pipeline = Channels.pipeline();

		//pipeline.addLast("idelCheck", new IdleStateHandler(timer, 0, 3, 0));
		pipeline.addLast("idelhandler", new Idelhandler());
		
		pipeline.addLast("framerDecoder", new ByteArrayToFrameDecoder());
		pipeline.addLast("objectDecoder", new FrameToObjectByteArrayDecoder());

		pipeline.addLast("handler", new RedislHandler());

		return pipeline;
	}
}