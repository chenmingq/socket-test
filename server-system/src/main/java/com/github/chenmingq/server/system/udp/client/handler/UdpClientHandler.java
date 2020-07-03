package com.github.chenmingq.server.system.udp.client.handler;

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
public class UdpClientHandler extends SimpleChannelInboundHandler<UdpMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, UdpMessage msg) throws Exception {
        System.out.println("----------------UdpClientHandler------------------------");
        System.out.println(msg);
        ctx.writeAndFlush(msg);
    }
}
