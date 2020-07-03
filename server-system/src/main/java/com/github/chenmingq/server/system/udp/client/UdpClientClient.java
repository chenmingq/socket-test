package com.github.chenmingq.server.system.udp.client;

import com.github.chenmingq.common.constant.CommonConst;
import com.github.chenmingq.common.message.HeaderMessage;
import com.github.chenmingq.server.system.udp.client.initalizer.UdpClientInitializer;
import com.github.chenmingq.server.system.udp.msg.UdpMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetSocketAddress;

/**
 * @author : chengmingqin
 * date : 2020/6/30
 * description :
 */

public class UdpClientClient {

    public void run(int port) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(eventLoopGroup).channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new UdpClientInitializer());

            Channel channel = bootstrap.bind(0).sync().channel();

            HeaderMessage reqMsg = new HeaderMessage();
            reqMsg.setCmdId(1);
            reqMsg.setModuleId(2);
            reqMsg.setBody("你好".getBytes());
            reqMsg.setMagic(CommonConst.MAGIC_CODE);

            UdpMessage udpMessage = new UdpMessage();
            udpMessage.setHeaderMessage(reqMsg);
            udpMessage.setSocketAddress(new InetSocketAddress("127.0.0.1", port));

            channel.writeAndFlush(udpMessage).sync();

            if (!channel.closeFuture().await(15000)) {
                System.out.println("out of time");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new UdpClientClient().run(8081);
    }

}
