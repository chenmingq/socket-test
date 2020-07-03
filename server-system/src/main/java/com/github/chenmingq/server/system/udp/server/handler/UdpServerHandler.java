package com.github.chenmingq.server.system.udp.server.handler;

import com.github.chenmingq.server.system.udp.msg.UdpMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author : chengmingqin
 * date : 2020/7/2
 * description :
 */


@ChannelHandler.Sharable
public class UdpServerHandler extends SimpleChannelInboundHandler<UdpMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, UdpMessage msg) throws Exception {
        System.out.println("----------------UdpServerHandler---------------");
        System.out.println(msg);
        ctx.writeAndFlush(msg);
    }
}
