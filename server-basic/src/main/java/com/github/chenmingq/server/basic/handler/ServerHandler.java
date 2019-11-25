package com.github.chenmingq.server.basic.handler;

import com.github.chenmingq.common.channel.AttributeUtil;
import com.github.chenmingq.common.channel.ChannelAttrKey;
import com.github.chenmingq.common.message.AbstractMessage;
import com.github.chenmingq.common.message.HeaderMessage;
import com.github.chenmingq.common.session.entiy.Session;
import com.github.chenmingq.common.utils.RemotingHelper;
import com.github.chenmingq.common.utils.RemotingUtil;
import com.github.chenmingq.server.basic.listener.ListenerFactory;
import com.github.chenmingq.server.basic.listener.ListenerType;
import com.github.chenmingq.server.basic.pool.ServerMessagePool;
import com.github.chenmingq.server.basic.processor.MsgProcessorFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;


/**
 * @author : chenmq
 * date : 2019-4-08
 * Project : netty-test
 * Description：
 */
@Slf4j
@ChannelHandler.Sharable
public class ServerHandler extends SimpleChannelInboundHandler<HeaderMessage> {

    /**
     * channel 读取消息
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeaderMessage msg) throws Exception {
        Class<? extends AbstractMessage> clazz = ServerMessagePool.getInstance().getReq(msg.getModuleId(), msg.getCmdId());
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
        Session session = new Session();
        session.setChannel(ctx.channel());
        abstractMessage.setSession(session);
        MsgProcessorFactory.getAbstractMessage(clazz).processor(abstractMessage);
    }


    /**
     * 收到客户端断开
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
        log.info("{} 关闭了客户端", RemotingHelper.parseChannelRemoteAddr(ctx.channel()));
        Session session = AttributeUtil.get(ctx.channel(), ChannelAttrKey.SESSION);
        if (null == session) {
            return;
        }
        ListenerFactory.event(ListenerType.LOG_OUT, session);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(IdleState.ALL_IDLE)) {
                final String remoteAddress = RemotingHelper.parseChannelRemoteAddr(ctx.channel());
                log.warn("NETTY SERVER PIPELINE: IDLE exception [{}]", remoteAddress);
                RemotingUtil.closeChannel(ctx.channel());
            }
        }

        ctx.fireUserEventTriggered(evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (this == ctx.pipeline().last()) {
            cause.printStackTrace();
            ctx.close();
        }
    }
}
