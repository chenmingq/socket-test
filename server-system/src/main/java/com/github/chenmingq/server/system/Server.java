package com.github.chenmingq.server.system;

import com.github.chenmingq.common.config.SystemConfig;
import com.github.chenmingq.common.utils.Utils;
import com.github.chenmingq.server.basic.handler.ServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author : chenmq
 * date : 2019-4-08
 * Project : netty-test
 * Description： netty端口服务启动
 */

@Slf4j
public class Server {

    private static final ScheduledExecutorService SERVICE_START_EXECUTOR;

    private static final long START_TIME;

    static {
        START_TIME = System.currentTimeMillis();

        SERVICE_START_EXECUTOR = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
            private AtomicInteger threadIndex = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "NettyMainServer-" + this.threadIndex.incrementAndGet());
            }
        });
    }

    /**
     * 初始化服务
     */
    public static void startServer(Class<?> clazz, String[] args) {
        SERVICE_START_EXECUTOR.execute(new ServerContext(clazz, args));
    }

    public static void init() {
        if (SystemConfig.port == 0) {
            throw new RuntimeException("系统配置未初始化");
        }
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        EventLoopGroup workerGroup = new NioEventLoopGroup();
        EventLoopGroup bossGroup = new NioEventLoopGroup();

        serverBootstrap.group(bossGroup, workerGroup)
                .channel(Utils.useEpoll() ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                //保持连接数
                .option(ChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.SO_REUSEADDR, true)
                //保持连接
//                .option(ChannelOption.SO_KEEPALIVE, true)
                //有数据立即发送
                .childOption(ChannelOption.TCP_NODELAY, true)

                .childOption(ChannelOption.SO_SNDBUF, 65535)
                .childOption(ChannelOption.SO_RCVBUF, 65535)
                .localAddress(new InetSocketAddress(SystemConfig.port))
                .childHandler(new ServerInitializer());

        serverBootstrap.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

        try {
            ChannelFuture sync = serverBootstrap.bind().sync();
            InetSocketAddress addr = (InetSocketAddress) sync.channel().localAddress();
            if (sync.isSuccess()) {
                log.info("服务启动成功 PID - {} , 端口 - {}", Utils.fetchProcessId(), addr.getPort());
            } else {
                log.info("服务启动失败 InetSocketAddress - {}", addr);
                System.exit(1);
            }
            log.info("启动耗时 - {} s", System.currentTimeMillis() / 1000 - START_TIME / 1000);
            //等待服务监听端口关闭,就是由于这里会将线程阻塞,导致无法发送信息
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException("this.serverBootstrap.bind().sync() InterruptedException", e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

}
