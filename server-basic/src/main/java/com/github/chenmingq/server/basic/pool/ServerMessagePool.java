package com.github.chenmingq.server.basic.pool;


import com.github.chenmingq.common.message.AbstractMessage;
import com.github.chenmingq.id.user.User;
import com.github.chenmingq.msg.user.ReqLogOutMessage;
import com.github.chenmingq.msg.user.ReqLoginMessage;
import com.github.chenmingq.msg.user.ReqRegisterUserMessage;

import java.util.HashMap;
import java.util.Map;


/**
 * projectName: socket-test
 *
 * @author chenmingqin
 * create: 2019-10-20 20:51
 * description: 消息池注册消息
 **/

public class ServerMessagePool {

    private static ServerMessagePool instance = new ServerMessagePool();

    public static ServerMessagePool getInstance() {
        return instance;
    }

    public ServerMessagePool() {
    }

    private Map<Integer, Map<Integer, Class<? extends AbstractMessage>>> msgMap = new HashMap<>();

    public void registerMsg() {
        registerReq(User.MODULE_ID_VALUE, User.REQ_LOGIN_MESSAGE_VALUE, new ReqLoginMessage());
        registerReq(User.MODULE_ID_VALUE, User.REQ_REGISTER_USER_MESSAGE_VALUE, new ReqRegisterUserMessage());
        registerReq(User.MODULE_ID_VALUE, User.REQ_LOG_OUT_MESSAGE_VALUE, new ReqLogOutMessage());
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

    public Class<? extends AbstractMessage> getReq(int moduleId, int cmdId) {
        Map<Integer, Class<? extends AbstractMessage>> baseMsgMap = msgMap.get(moduleId);
        if (null == baseMsgMap) {
            return null;
        }
        return baseMsgMap.get(cmdId);
    }

}
