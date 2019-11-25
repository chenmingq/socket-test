package com.github.chenmmingq.client.test.handler.adapter;


import com.github.chenmingq.common.common.annotation.ReqMapping;
import com.github.chenmingq.common.message.AbstractMessage;
import com.github.chenmingq.common.message.HeaderMessage;
import com.github.chenmmingq.client.test.common.ClientCacheClass;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author : chenmq
 * date : 2019-4-08
 * Project : netty-test
 * Description：消息发送处理
 */
public class ClientMessageProcessAdapter {

    private static ClientMessageProcessAdapter instance = new ClientMessageProcessAdapter();

    public static ClientMessageProcessAdapter getInstance() {
        return instance;
    }

    public ClientMessageProcessAdapter() {
    }

    /**
     * 消息请求
     *
     * @param abstractMessage
     */
    public void requestProtoExecute(AbstractMessage abstractMessage) {
        HeaderMessage message = abstractMessage.getMessage();
        if (null == message) {
            return;
        }
        try {
            Map<Integer, Class<?>> reqMappingMap = ClientCacheClass.CLIENT_RES_MAPPING_MAP;
            if (null == reqMappingMap) {
                return;
            }
            boolean b = reqMappingMap.containsKey(message.getModuleId());
            if (!b) {
                return;
            }
            Class<?> aClass = reqMappingMap.get(message.getModuleId());
            Method[] methods = aClass.getMethods();
            for (Method method : methods) {
                synchronized (this) {
                    ReqMapping methodAnnotation = method.getAnnotation(ReqMapping.class);
                    if (null == methodAnnotation) {
                        continue;
                    }
                    if (methodAnnotation.id() != message.getCmdId()) {
                        continue;
                    }
                    Object newInstance = aClass.newInstance();
                    ClientFieldHandlerAdapter.getInstance().newInstanceAll(newInstance);

                    method.invoke(newInstance, abstractMessage);
                }
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
