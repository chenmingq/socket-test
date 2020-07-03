package com.github.chenmingq.server.system.udp.code;

import com.github.chenmingq.common.constant.CommonConst;
import com.github.chenmingq.common.message.HeaderMessage;
import com.github.chenmingq.server.system.udp.msg.UdpMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @author : chengmingqin
 * date : 2020/7/2
 * description :
 */

public class UdpDecoder extends MessageToMessageDecoder<DatagramPacket> {
    @Override
    protected void decode(ChannelHandlerContext ctx, DatagramPacket packet, List<Object> out) throws Exception {
        ByteBuf in = packet.content();
        int magic = in.readInt();
        if (magic != CommonConst.MAGIC_CODE) {
            return;
        }
        int moduleId = in.readInt();
        int cmdId = in.readInt();
        int length = in.readInt();
        int bodyLength = length - CommonConst.HEAD_LENGTH;

//        if (in.readableBytes() < bodyLength) {
//            return;
//        }
//        byte[] body = new byte[bodyLength];
//        in.readBytes(body);

        HeaderMessage message = new HeaderMessage();
//        message.setBody(body);
        message.setModuleId(moduleId);
        message.setCmdId(cmdId);
        message.setMagic(magic);

        UdpMessage udpMessage = new UdpMessage();
        udpMessage.setSocketAddress(packet.sender());
        udpMessage.setHeaderMessage(message);

        out.add(udpMessage);
    }
}
