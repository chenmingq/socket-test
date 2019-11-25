package com.github.chenmingq.server.system.listener;


import com.github.chenmingq.server.basic.listener.IListener;
import com.github.chenmingq.server.basic.listener.ListenerFactory;
import com.github.chenmingq.server.basic.listener.ListenerType;
import com.github.chenmingq.server.system.listener.impl.login.LoginListener;
import com.github.chenmingq.server.system.listener.impl.logout.LogOutListener;
import com.github.chenmingq.server.system.listener.impl.start.GuavaCacheInitListener;

/**
 * projectName: nettyTest
 *
 * @author chenmingqin
 * create: 2019-10-27 21:00
 * description: 监听器注册
 **/
public class ListenerRegister {

    private static ListenerRegister instance = new ListenerRegister();

    public static ListenerRegister getInstance() {
        return instance;
    }

    public ListenerRegister() {
    }

    public void registerPreparedListeners() {
        addServerStartListener(ListenerType.START_SERVER);
        addLoginListener(ListenerType.LOGIN);
        addLogOutListener(ListenerType.LOG_OUT);
    }


    /**
     * 服务启动注册器
     *
     * @param type
     */
    private void addServerStartListener(ListenerType type) {
        addListener(type, new GuavaCacheInitListener());
    }


    /**
     * 登陆监听器
     *
     * @param type
     */
    private void addLoginListener(ListenerType type) {
        addListener(type, new LoginListener());
    }

    /**
     * 退出登陆
     *
     * @param type
     */
    private void addLogOutListener(ListenerType type) {
        addListener(type, new LogOutListener());
    }

    /**
     * 添加监听器
     *
     * @param type
     * @param listener
     */
    private void addListener(ListenerType type, IListener listener) {
        ListenerFactory.addListener(type, listener);
    }

}
