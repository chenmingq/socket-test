package com.github.chenmmingq.client.test.service.user;

import com.github.chenmingq.common.common.annotation.ServiceImpl;
import com.github.chenmingq.msg.user.ResHeadMessage;
import com.github.chenmingq.msg.user.ResLoginMessage;
import com.github.chenmmingq.client.test.session.ClientSession;

/**
 * @author: chenmingqin
 * date: 2019/11/21 19:58
 * description:
 */


@ServiceImpl
public interface ClientUserService {

    /**
     * 请求登陆
     *
     * @param session
     * @param account
     * @param password
     */
    void sendLogin(ClientSession session, String account, String password);

    /**
     * 注册
     *
     * @param session
     * @param userName
     * @param account
     * @param password
     */
    void sendRegister(ClientSession session, String userName, String account, String password);

    /**
     * 登陆(注册)结果
     *
     * @param resLoginMessage
     */
    void loginResult(ResLoginMessage resLoginMessage);

    /**
     * 接收服务器的心跳消息
     *
     * @param headBeatMessage
     */
    void headBeatMsg(ResHeadMessage headBeatMessage);
}
