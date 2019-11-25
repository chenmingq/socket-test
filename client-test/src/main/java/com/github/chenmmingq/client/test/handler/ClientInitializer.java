package com.github.chenmmingq.client.test.handler;

import com.github.chenmingq.common.coder.server.DeCoder;
import com.github.chenmingq.common.coder.server.EnCoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author : chenmq
 * @date : 2019-10-17
 * Project :socket-test
 * Description： 客户端端的处理器
 */
public class ClientInitializer extends ChannelInitializer<SocketChannel> {




    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new EnCoder());
        pipeline.addLast(new DeCoder());
        pipeline.addLast(new ClientHandler());
//        pipeline.addLast(new LoggingHandler(LogLevel.INFO));

    }
}
