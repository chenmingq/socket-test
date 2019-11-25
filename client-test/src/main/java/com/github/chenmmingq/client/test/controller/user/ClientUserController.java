package com.github.chenmmingq.client.test.controller.user;

import com.github.chenmingq.common.common.annotation.AutoIn;
import com.github.chenmingq.common.common.annotation.ReqMapping;
import com.github.chenmingq.id.user.User;
import com.github.chenmingq.msg.user.ResHeadMessage;
import com.github.chenmingq.msg.user.ResLoginMessage;
import com.github.chenmmingq.client.test.service.user.ClientUserService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: chenmingqin
 * date: 2019/11/21 19:53
 * description:接收服务端返回的消息
 */
@Slf4j
@ReqMapping(id = User.MODULE_ID_VALUE)
public class ClientUserController {

    @AutoIn
    private ClientUserService userService;

    @ReqMapping(id = User.RES_LOGIN_MESSAGE_VALUE)
    public void loginResult(ResLoginMessage resLoginMessage) {
        userService.loginResult(resLoginMessage);
    }

    @ReqMapping(id = User.RES_HEAD_MESSAGE_VALUE)
    public void headBeatMsg(ResHeadMessage headBeatMessage) {
        userService.headBeatMsg(headBeatMessage);
    }


}
