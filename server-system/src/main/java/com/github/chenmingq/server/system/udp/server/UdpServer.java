package com.github.chenmingq.server.system.udp.server;

import com.github.chenmingq.server.system.udp.server.initalizer.UdpServerInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import lombok.SneakyThrows;

/**
 * @author : chengmingqin
 * date : 2020/6/30
 * description : UDP服务
 */

public class UdpServer {

    public void start(int port) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new UdpServerInitializer());

            System.out.println("---UDP服务启动---");
            bootstrap.bind(port).sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        UdpServer server = new UdpServer();
        server.start(8081);
    }

}
