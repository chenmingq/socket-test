package com.github.chenmingq.common.utils;


import com.github.chenmingq.common.constant.CommonConst;
import com.github.chenmingq.common.message.AbstractMessage;
import com.github.chenmingq.common.message.HeaderMessage;
import com.github.chenmingq.common.message.ResActionMessage;
import com.github.chenmingq.common.message.ResNoUserActionMessage;
import com.github.chenmingq.common.utils.executor.ExecutorUtil;

/**
 * @author : chenmq
 * date : 2019-11-20
 * Project : socket-test
 * Description： 消息发送
 */

public class MessageUtil {


    public static void sendMsg(long userId, AbstractMessage abstractMessage) {
        abstractMessage.setMessage(buildHeadMessage(abstractMessage));
        ExecutorUtil.commonExecutor.execute(new ResActionMessage(userId, abstractMessage));
    }

    public static void sendMsg(AbstractMessage abstractMessage) {
        abstractMessage.setMessage(buildHeadMessage(abstractMessage));
        ExecutorUtil.commonExecutor.execute(new ResNoUserActionMessage(abstractMessage));
    }

    private static HeaderMessage buildHeadMessage(AbstractMessage abstractMessage) {
        HeaderMessage headerMessage = new HeaderMessage();
        headerMessage.setBody(abstractMessage.enCoder());
        headerMessage.setCmdId(abstractMessage.getCmdId());
        headerMessage.setModuleId(abstractMessage.getModuleId());
        headerMessage.setMagic(CommonConst.MAGIC_CODE);
        return headerMessage;
    }
}
