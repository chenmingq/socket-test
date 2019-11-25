package com.github.chenmingq.server.basic.listener;

/**
 * projectName: socket-test
 *
 * @author chenmingqin
 * create: 2019-10-20 20:51
 * description: 监听器
 **/


public interface IListener {


    /**
     * 监听的方法
     *
     * @param obj
     */
    void event(Object obj);

}
