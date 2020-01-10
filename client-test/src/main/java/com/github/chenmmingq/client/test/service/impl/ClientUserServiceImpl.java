package com.github.chenmmingq.client.test.service.impl;

import com.github.chenmingq.msg.user.ReqLoginMessage;
import com.github.chenmingq.msg.user.ReqRegisterUserMessage;
import com.github.chenmingq.msg.user.ResHeadMessage;
import com.github.chenmingq.msg.user.ResLoginMessage;
import com.github.chenmingq.proto.UserProto;
import com.github.chenmmingq.client.test.service.user.ClientUserService;
import com.github.chenmmingq.client.test.session.ClientSession;
import com.github.chenmmingq.client.test.util.ClientMessageUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: chenmingqin
 * date: 2019/11/21 19:58
 * description:
 */

@Slf4j
public class ClientUserServiceImpl implements ClientUserService {


    @Override
    public void sendLogin(ClientSession session, String account, String password) {
        log.info("请求登陆账号,[{}]", account);
        UserProto.ReqLoginMessage.Builder proto = UserProto.ReqLoginMessage.newBuilder();
        UserProto.Login.Builder loginProto = UserProto.Login.newBuilder();
        loginProto.setAccount(account);
        loginProto.setPassword(password);
        proto.setLogin(loginProto);

        ReqLoginMessage msg = new ReqLoginMessage();
        msg.setProto(proto.build());
        ClientMessageUtil.sendMsg(session, msg);

    }

    @Override
    public void sendRegister(ClientSession session, String userName, String account, String password) {
        log.info("请求注册账号,[{}]", account);
        UserProto.ReqRegisterMessage.Builder builder = UserProto.ReqRegisterMessage.newBuilder();
        builder.setAccount(account);
        builder.setPassword(password);
        builder.setUserName(userName);

        ReqRegisterUserMessage msg = new ReqRegisterUserMessage();
        msg.setProto(builder.build());
        ClientMessageUtil.sendMsg(session, msg);
    }

    @Override
    public void loginResult(ResLoginMessage resLoginMessage) {
        UserProto.ResLoginMessage proto = resLoginMessage.getProto();
        int state = proto.getState();
        if (state == UserProto.ResLoginMessage.LOGIN_STATE.FAILED_TO_LOGIN_VALUE) {
            log.info("登陆失败,可能是账号密码错误");
            return;
        }
        log.info("登陆结果 -> [{}]", proto.getUserInfo());
    }

    @Override
    public void headBeatMsg(ResHeadMessage headBeatMessage) {
        UserProto.ResHeadMessage proto = headBeatMessage.getProto();
        log.info("登陆成功服务器返回心跳时间戳-> [{}] ", proto.getTimestamp());
    }
}

