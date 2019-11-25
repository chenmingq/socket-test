package com.github.chenmmingq.client.test.util;


import com.github.chenmingq.common.constant.CommonConst;
import com.github.chenmingq.common.message.AbstractMessage;
import com.github.chenmingq.common.message.HeaderMessage;
import com.github.chenmmingq.client.test.session.ClientSession;

/**
 * @author: chenmingqin
 * date: 2019/11/22 10:30
 * description: 客户端发送消息
 */

public class ClientMessageUtil {

    /**
     * 发送消息
     *
     * @param session
     * @param abstractMessage
     */
    public static void sendMsg(ClientSession session, AbstractMessage abstractMessage) {
        HeaderMessage message = new HeaderMessage();
        message.setMagic(CommonConst.MAGIC_CODE);
        message.setModuleId(abstractMessage.getModuleId());
        message.setCmdId(abstractMessage.getCmdId());
        message.setBody(abstractMessage.enCoder());
        session.sendMsg(message);
    }
}
