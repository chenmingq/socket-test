package com.github.chenmmingq.client.test;

import com.github.chenmmingq.client.test.manager.user.UserManager;
import com.github.chenmmingq.client.test.session.ClientSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author: chenmingqin
 * date: 2019/11/23 10:15
 * description:
 */

@Slf4j
public class ClientAll {



    public ClientAll(ClientSession session) {
        reqRegister(session);
    }
    /**
     * 请求登陆
     *
     * @param session
     */
    public static void reqLogin(ClientSession session) {
        log.info("请求登陆");
        UserManager.getInstance().sendLogin(session, "account2", "123456");
    }

    /**
     * 注册账号
     *
     * @param session
     */
    public static void reqRegister(ClientSession session) {
        log.info("注册账号");
        UserManager.getInstance().sendRegister(session, RandomStringUtils.randomAlphanumeric(10), RandomStringUtils.randomAlphabetic(10), "123456");
    }
}
