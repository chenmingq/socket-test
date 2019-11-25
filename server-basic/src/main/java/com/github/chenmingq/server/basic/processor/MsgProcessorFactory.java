package com.github.chenmingq.server.basic.processor;


import com.github.chenmingq.common.message.AbstractMessage;
import com.github.chenmingq.msg.user.ReqLogOutMessage;
import com.github.chenmingq.msg.user.ReqLoginMessage;
import com.github.chenmingq.msg.user.ReqRegisterUserMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * projectName: nettyTest
 *
 * @author chenmingqin
 * create: 2019-11-01 23:49
 * description: 消息分发工厂
 **/

public class MsgProcessorFactory {


    private static Map<Class<? extends AbstractMessage>, ICommandProcessor> processorMap = new HashMap<>();


    static {
        processorMap.put(ReqLoginMessage.class, new LoginProcessor());
        processorMap.put(ReqRegisterUserMessage.class, new RegisterProcessor());
        processorMap.put(ReqLogOutMessage.class, new LogOutProcessor());
    }

    public static ICommandProcessor getAbstractMessage(Class<? extends AbstractMessage> clazz) {
        ICommandProcessor iCommandProcessor = processorMap.get(clazz);
        // 如果为空执行公共的处理
        if (null == iCommandProcessor) {
            return new CommonProcessor();
        }
        return iCommandProcessor;
    }


}
