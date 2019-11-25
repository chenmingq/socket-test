package com.github.chenmingq.server.basic.handler.adapter;


import com.github.chenmingq.common.common.annotation.ReqMapping;
import com.github.chenmingq.common.common.cache.CacheClass;
import com.github.chenmingq.common.message.AbstractMessage;
import com.github.chenmingq.common.message.HeaderMessage;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author : chengmingqin
 * @date : 2019/10/20 13:31
 * description :消息发送处理
 */
public class MessageProcessAdapter {

    private static MessageProcessAdapter instance = new MessageProcessAdapter();

    public static MessageProcessAdapter getInstance() {
        return instance;
    }

    public MessageProcessAdapter() {
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
            Map<Integer, Class<?>> reqMappingMap = CacheClass.REQ_MAPPING_MAP;
            if (null == reqMappingMap) {
                return;
            }
            Class<?> aClass = reqMappingMap.get(message.getModuleId());
            if (null == aClass) {
                return;
            }

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
                    FieldHandlerAdapter.getInstance().newInstanceAll(newInstance);

                    method.invoke(newInstance, abstractMessage);
                }
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
