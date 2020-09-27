package com.github.chenmingq.server.system.udp.server.handler;

import com.github.chenmingq.common.message.HeaderMessage;
import com.github.chenmingq.server.system.udp.msg.UdpMessage;
import com.github.chenmingq.server.system.udp.proto.ReqGetMoney;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.math.BigDecimal;

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
        HeaderMessage headerMessage = msg.getHeaderMessage();

        ReqGetMoney money = new ReqGetMoney().deCoder(ReqGetMoney.class, headerMessage.getBody());
        System.out.println(money);

        ReqGetMoney reqGetMoney = new ReqGetMoney();
        reqGetMoney.setCode("AAPL");
        reqGetMoney.setTime(System.currentTimeMillis());
        reqGetMoney.setMoney(new BigDecimal("300.5"));
        headerMessage.setBody(reqGetMoney.enCoder(reqGetMoney));

        msg.setHeaderMessage(headerMessage);

        ctx.writeAndFlush(msg);
    }
}
