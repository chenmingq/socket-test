package com.github.chenmingq.server.system.controller.user;

import com.github.chenmingq.common.common.annotation.AutoIn;
import com.github.chenmingq.common.common.annotation.ReqMapping;
import com.github.chenmingq.id.user.User;
import com.github.chenmingq.msg.user.ReqLoginMessage;
import com.github.chenmingq.msg.user.ReqRegisterUserMessage;
import com.github.chenmingq.proto.UserProto;
import com.github.chenmingq.server.system.mapper.user.UserMapper;
import com.github.chenmingq.server.system.service.user.UserService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : chengmingqin
 * @date : 2019/09/23 15:22
 * description : 登陆测试
 */

@Slf4j
@ReqMapping(id = User.MODULE_ID_VALUE)
public class UserController {

    @AutoIn
    private UserMapper userMapper;

    @AutoIn
    private UserService userService;

    @ReqMapping(id = User.REQ_LOGIN_MESSAGE_VALUE)
    public void reqLogin(ReqLoginMessage protoMsg) {
        UserProto.Login login = protoMsg.getProto().getLogin();
        String account = login.getAccount();
        String password = login.getPassword();
        String code = login.getCode();
        userService.reqLogin(protoMsg.getSession(), account, password, code);
    }

    @ReqMapping(id = User.REQ_REGISTER_USER_MESSAGE_VALUE)
    public void reqRegister(ReqRegisterUserMessage msg) {
        userService.reqRegister(msg.getSession(), msg.getProto().getAccount(), msg.getProto().getUserName(), msg.getProto().getPassword());
    }

}
