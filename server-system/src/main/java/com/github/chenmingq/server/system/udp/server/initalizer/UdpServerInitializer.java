package com.github.chenmingq.server.system.udp.server.initalizer;

import com.github.chenmingq.server.system.udp.code.UdpDecoder;
import com.github.chenmingq.server.system.udp.code.UdpEncoder;
import com.github.chenmingq.server.system.udp.server.handler.UdpServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * @author : chengmingqin
 * date : 2020/7/2
 * description :
 */

public class UdpServerInitializer extends ChannelInitializer<NioDatagramChannel> {
    @Override
    protected void initChannel(NioDatagramChannel ch) throws Exception {

        ch.pipeline().addLast(new UdpDecoder());
        ch.pipeline().addLast(new UdpEncoder());
        ch.pipeline().addLast(new UdpServerHandler());

    }
}
