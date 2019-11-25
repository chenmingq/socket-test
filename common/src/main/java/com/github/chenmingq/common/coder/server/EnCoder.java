package com.github.chenmingq.common.coder.server;


import com.github.chenmingq.common.constant.CommonConst;
import com.github.chenmingq.common.message.HeaderMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author : chenmq
 * date : 2019-11-20
 * Project : socket-test
 * Description：编码器
 */
public class EnCoder extends MessageToByteEncoder<HeaderMessage> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, HeaderMessage msg, ByteBuf out) throws Exception {
        doEncodeRequest(msg, out);
    }

    private void doEncodeRequest(HeaderMessage request, ByteBuf out) {

        int magic = request.getMagic();
        byte[] body = request.getBody();
        int moduleId = request.getModuleId();
        int cmdId = request.getCmdId();

        int length = CommonConst.HEAD_LENGTH + body.length;

        out.writeInt(magic)
                .writeInt(moduleId)
                .writeInt(cmdId)
                .writeInt(length)
                .writeBytes(body);

    }

}
