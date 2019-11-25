package com.github.chenmingq.server.system.listener.impl.logout;


import com.github.chenmingq.common.common.annotation.AutoIn;
import com.github.chenmingq.common.session.entiy.Session;
import com.github.chenmingq.server.basic.listener.IListener;
import com.github.chenmingq.server.system.service.user.UserService;

/**
 * @author: chenmingqin
 * date: 2019/11/22 17:12
 * description:
 */

public class LogOutListener implements IListener {

    @AutoIn
    private UserService userService;

    @Override
    public void event(Object obj) {
        if (null == obj) {
            return;
        }
        Session session = (Session) obj;
        userService.logOut(session);
    }
}
