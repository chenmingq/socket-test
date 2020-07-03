package com.github.chenmingq.server.system.udp.code;

import com.github.chenmingq.common.constant.CommonConst;
import com.github.chenmingq.common.message.HeaderMessage;
import com.github.chenmingq.server.system.udp.msg.UdpMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author : chengmingqin
 * date : 2020/7/2
 * description :
 */

public class UdpEncoder extends MessageToMessageEncoder<UdpMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, UdpMessage msg, List<Object> out) throws Exception {
        ByteBuf byteBuf = Unpooled.buffer(1024);
        HeaderMessage request = msg.getHeaderMessage();
        InetSocketAddress socketAddress = msg.getSocketAddress();


        int magic = request.getMagic();
        byte[] body = "你好".getBytes();
//        byte[] body = request.getBody();
        int moduleId = request.getModuleId();
        int cmdId = request.getCmdId();

        int length = CommonConst.HEAD_LENGTH + body.length;

        byteBuf.writeInt(magic)
                .writeInt(moduleId)
                .writeInt(cmdId)
                .writeInt(length)
                .writeBytes(body);

        out.add(new DatagramPacket(byteBuf,socketAddress));
    }
}
