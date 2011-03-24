package com.handinfo.redis4j.impl.transfers;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.util.Timer;

import com.handinfo.redis4j.api.IConnector;
import com.handinfo.redis4j.impl.transfers.decoder.FrameToObjectArray;
import com.handinfo.redis4j.impl.transfers.decoder.InputStreamToFrame;
import com.handinfo.redis4j.impl.transfers.encoder.CommandWrapperToBinaryData;
import com.handinfo.redis4j.impl.transfers.handler.HeartbeatHandler;
import com.handinfo.redis4j.impl.transfers.handler.MessageHandler;
import com.handinfo.redis4j.impl.transfers.handler.ReconnectNetworkHandler;

public class PipelineFactory implements ChannelPipelineFactory
{
	private final Timer timer;
	private IConnector connector;

	/**
	 * @param connector
	 */
	public PipelineFactory(IConnector connector, Timer timer)
	{
		super();
		this.timer = timer;
		this.connector = connector;
	}

	public ChannelPipeline getPipeline() throws Exception
	{
		ChannelPipeline pipeline = Channels.pipeline();

		pipeline.addLast("IdleStateHandler", new IdleStateHandler(timer, 0, connector.getHeartbeatTime(), 0));
		pipeline.addLast("HeartbeatHandler", new HeartbeatHandler(connector));
		pipeline.addLast("ReconnectNetworkHandler", new ReconnectNetworkHandler(connector, timer));
		
		pipeline.addLast("ObjectEncoder", new CommandWrapperToBinaryData());
		
		pipeline.addLast("FramerDecoder", new InputStreamToFrame());
		pipeline.addLast("ObjectDecoder", new FrameToObjectArray());

		pipeline.addLast("MessageHandler", new MessageHandler(connector));
		

		return pipeline;
	}
}