package com.github.chenmingq.server.system.service.user;


import com.github.chenmingq.common.common.annotation.ServiceImpl;
import com.github.chenmingq.common.session.entiy.Session;

@ServiceImpl
public interface UserService {


    /**
     * 请求登陆
     *
     * @param session
     * @param account
     * @param password
     * @param code
     */
    void reqLogin(Session session, String account, String password, String code);


    /**
     * 注册用户
     *
     * @param session
     * @param account
     * @param userName
     * @param password
     */
    void reqRegister(Session session, String account, String userName, String password);

    /**
     * 退出登陆
     *
     * @param session
     */
    void logOut(Session session);

}
