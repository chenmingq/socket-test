package com.github.chenmmingq.client.test.manager.user;

import com.github.chenmingq.common.common.annotation.AutoIn;
import com.github.chenmmingq.client.test.handler.adapter.ClientFieldHandlerAdapter;
import com.github.chenmmingq.client.test.service.user.ClientUserService;
import com.github.chenmmingq.client.test.session.ClientSession;

/**
 * @author: chenmingqin
 * date: 2019/11/22 10:39
 * description:用户
 */

public class UserManager {

    private static UserManager instance = new UserManager();

    public static UserManager getInstance() {
        return instance;
    }

    public UserManager() {
    }

    static {
        ClientFieldHandlerAdapter.getInstance().newInstanceAll(getInstance());
    }

    @AutoIn
    private ClientUserService userService;

    /**
     * 请求登陆
     *
     * @param session
     * @param account
     * @param password
     */
    public void sendLogin(ClientSession session, String account, String password) {
        userService.sendLogin(session, account, password);
    }

    /**
     * 请求登陆
     *
     * @param session
     * @param userName
     * @param account
     * @param password
     */
    public void sendRegister(ClientSession session, String userName, String account, String password) {
        userService.sendRegister(session, userName, account, password);
    }

}
