package com.github.chenmingq.common.coder.server;

import com.github.chenmingq.common.constant.CommonConst;
import com.github.chenmingq.common.message.HeaderMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;


/**
 * @author : chenmq
 * date : 2019-11-20
 * Project : socket-test
 * Description：解码器
 */
public class DeCoder extends ReplayingDecoder<HeaderMessage> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> list) throws Exception {
        int magic = in.readInt();

        if (magic != CommonConst.MAGIC_CODE) {
            return;
        }

        int moduleId = in.readInt();
        int cmdId = in.readInt();
        int length = in.readInt();
        int bodyLength = length - CommonConst.HEAD_LENGTH;

        if (in.readableBytes() < bodyLength) {
            return;
        }
        byte[] body = new byte[bodyLength];
        in.readBytes(body);

        HeaderMessage message = new HeaderMessage();
        message.setBody(body);
        message.setModuleId(moduleId);
        message.setCmdId(cmdId);
        message.setMagic(magic);

        list.add(message);
    }
}
