package com.github.chenmmingq.client.test;

import com.github.chenmmingq.client.test.handler.ClientInitializer;
import com.github.chenmmingq.client.test.session.ClientSession;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: chenmingqin
 * date: 2019/11/21 19:50
 * description:
 */
@Slf4j
public class Client {

    private static final int SERVER_PORT = 9902;
    private static final String SERVER_IP = "localhost";

    public void startClient() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    // 保持连接
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    // 有数据立即发送
                    .option(ChannelOption.TCP_NODELAY, true)
                    // 绑定处理group
                    .remoteAddress(SERVER_IP, SERVER_PORT)
                    .handler(new ClientInitializer());

            // Make a new connection.
            ChannelFuture future = b.connect(SERVER_IP, SERVER_PORT).sync();

            if (future.isSuccess()) {
                log.info("{}", "客户端开启成功...");
                new ClientAll(buildSession(future.channel()));
            } else {
                log.info("{}", "客户端开启失败...");
            }
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    private static ClientSession buildSession(Channel channel) {
        ClientSession session = new ClientSession();
        session.setChannel(channel);
        return session;
    }


}
