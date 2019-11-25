package com.github.chenmmingq.client.test.pool;

import com.github.chenmingq.common.message.AbstractMessage;
import com.github.chenmingq.id.user.User;
import com.github.chenmingq.msg.user.ResHeadMessage;
import com.github.chenmingq.msg.user.ResLoginMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: chenmingqin
 * date: 2019/11/21 20:07
 * description:消息注册
 */

public class ClientMessagePool {

    private static ClientMessagePool instance = new ClientMessagePool();

    public static ClientMessagePool getInstance() {
        return instance;
    }

    public ClientMessagePool() {
    }


    private Map<Integer, Map<Integer, Class<? extends AbstractMessage>>> msgMap = new HashMap<>();

    public void registerMsg() {
        registerReq(User.MODULE_ID_VALUE, User.RES_LOGIN_MESSAGE_VALUE, new ResLoginMessage());
        registerReq(User.MODULE_ID_VALUE, User.RES_HEAD_MESSAGE_VALUE, new ResHeadMessage());
    }


    private void registerReq(int moduleId, int cmdId, AbstractMessage baseMsg) {
        Map<Integer, Class<? extends AbstractMessage>> baseMsgMap = msgMap.get(moduleId);
        if (null != baseMsgMap) {
            if (baseMsgMap.containsKey(cmdId)) {
                throw new RuntimeException("消息模块" + moduleId + "的id重复:" + cmdId);
            }
            baseMsgMap.put(cmdId, baseMsg.getClass());
            return;
        }
        baseMsgMap = new HashMap<>();
        baseMsgMap.put(cmdId, baseMsg.getClass());
        msgMap.put(moduleId, baseMsgMap);
    }

    public Class<? extends AbstractMessage> getResMessage(int moduleId, int cmdId) {
        Map<Integer, Class<? extends AbstractMessage>> baseMsgMap = msgMap.get(moduleId);
        if (null == baseMsgMap) {
            return null;
        }
        return baseMsgMap.get(cmdId);
    }


}
