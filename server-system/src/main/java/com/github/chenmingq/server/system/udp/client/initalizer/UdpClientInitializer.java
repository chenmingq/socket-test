package com.github.chenmingq.server.system.udp.client.initalizer;

import com.github.chenmingq.server.system.udp.client.handler.UdpClientHandler;
import com.github.chenmingq.server.system.udp.code.UdpDecoder;
import com.github.chenmingq.server.system.udp.code.UdpEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * @author : chengmingqin
 * date : 2020/7/2
 * description :
 */

public class UdpClientInitializer extends ChannelInitializer<NioDatagramChannel> {
    @Override
    protected void initChannel(NioDatagramChannel ch) throws Exception {
        ch.pipeline().addLast(new UdpDecoder());
        ch.pipeline().addLast(new UdpEncoder());
        ch.pipeline().addLast(new UdpClientHandler());
    }
}
