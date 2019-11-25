package com.github.chenmmingq.client.test.handler;

import com.github.chenmingq.common.message.AbstractMessage;
import com.github.chenmingq.common.message.HeaderMessage;
import com.github.chenmmingq.client.test.handler.adapter.ClientActionMessageAdapter;
import com.github.chenmmingq.client.test.pool.ClientMessagePool;
import com.github.chenmmingq.client.test.util.executor.ClientExecutorUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : chenmq
 * @date : 2019-10-17
 * Project : socket-test
 * Description：
 */
@Slf4j
@ChannelHandler.Sharable
public class ClientHandler extends SimpleChannelInboundHandler<HeaderMessage> {

    /**
     * channel 读取消息
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeaderMessage msg) throws Exception {

        Class<? extends AbstractMessage> clazz = ClientMessagePool.getInstance().getResMessage(msg.getModuleId(), msg.getCmdId());
        if (null == clazz) {
            return;
        }
        AbstractMessage abstractMessage = null;
        try {
            abstractMessage = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        if (null == abstractMessage) {
            return;
        }
        abstractMessage.setMessage(msg);
        abstractMessage.deCoder(msg.getBody());
        ClientExecutorUtil.clientExecutor.execute(new ClientActionMessageAdapter(abstractMessage));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
