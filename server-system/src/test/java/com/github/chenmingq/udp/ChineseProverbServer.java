package com.github.chenmingq.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author : chengmingqin
 * date : 2020/7/3
 * description :
 */

public class ChineseProverbServer {
    public void run(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        try {
            //通过NioDatagramChannel创建Channel，并设置Socket参数支持广播
            //UDP相对于TCP不需要在客户端和服务端建立实际的连接，因此不需要为连接（ChannelPipeline）设置handler
            Bootstrap b = new Bootstrap();
            b.group(bossGroup)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new ChineseProverbServerHandler());
            b.bind(port).sync().channel().closeFuture().await();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8888;
        if (args != null && args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        new ChineseProverbServer().run(port);
    }

    public static class ChineseProverbServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

        private static final String[] DICTIONARY = {"hi，hello", "客户端你好啊，hi client"};

        private String nextQuote() {
            //线程安全岁基类，避免多线程环境发生错误
            int quote = ThreadLocalRandom.current().nextInt(DICTIONARY.length);
            return DICTIONARY[quote];
        }

        //接收Netty封装的DatagramPacket对象，然后构造响应消息
        @Override
        public void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
            //利用ByteBuf的toString()方法获取请求消息
            String req = packet.content().toString(CharsetUtil.UTF_8);
            System.out.println(req);
            if ("你好".equals(req)) {
                //创建新的DatagramPacket对象，传入返回消息和目的地址（IP和端口）
                ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(
                        "服务端回复消息：" + nextQuote(), CharsetUtil.UTF_8), packet.sender()));
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
                throws Exception {
            ctx.close();
            cause.printStackTrace();
        }
    }
}
