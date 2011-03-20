package com.handinfo.redis4j.impl.transfers;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timer;

import com.handinfo.redis4j.impl.protocol.Idelhandler;
import com.handinfo.redis4j.impl.protocol.Receiver;
import com.handinfo.redis4j.impl.protocol.decode.ByteArrayToFrameDecoder;
import com.handinfo.redis4j.impl.protocol.decode.FrameToObjectByteArrayDecoder;
import com.handinfo.redis4j.impl.protocol.encode.CommandToBinaryData;
import com.handinfo.redis4j.impl.protocol.encode.Sender;

public class PipelineFactory implements ChannelPipelineFactory
{
	private final Timer timer;
	private Connector connector;

	/**
	 * @param connector
	 */
	public PipelineFactory(Connector connector)
	{
		super();
		this.timer = new HashedWheelTimer();
		this.connector = connector;
	}

	public ChannelPipeline getPipeline() throws Exception
	{
		ChannelPipeline pipeline = Channels.pipeline();

		//pipeline.addLast("idelCheck", new IdleStateHandler(timer, 0, 3, 0));
		pipeline.addLast("idelhandler", new Idelhandler());
		
		pipeline.addLast("objectEncoder", new CommandToBinaryData());
		pipeline.addLast("sender", new Sender());
		
		pipeline.addLast("framerDecoder", new ByteArrayToFrameDecoder());
		pipeline.addLast("objectDecoder", new FrameToObjectByteArrayDecoder());

		pipeline.addLast("receiver", new Receiver(connector));

		return pipeline;
	}
}