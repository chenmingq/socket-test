package com.github.chenmingq.server.basic.handler;

import com.github.chenmingq.common.coder.server.DeCoder;
import com.github.chenmingq.common.coder.server.EnCoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author : chenmq
 * date : 2019-4-08
 * Project : netty-test
 * Description：
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new DeCoder());
        pipeline.addLast(new EnCoder());
        pipeline.addLast(new ServerHandler());
        pipeline.addLast(new IdleStateHandler(0, 0, 120));
//        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
    }
}
