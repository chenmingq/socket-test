package com.github.chenmingq.common.utils;

import io.netty.channel.epoll.Epoll;

import java.lang.management.ManagementFactory;

/**
 * @author : chenmq
 * date : 2019-11-20
 * Project : socket-test
 * Description： 工具
 */

public class Utils {


    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取pid
     *
     * @return
     */
    public static int fetchProcessId() {
        try {
            return Integer.parseInt(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * 当前系统是否支持epoll
     *
     * @return
     */
    public static boolean useEpoll() {
        return RemotingUtil.isLinuxPlatform()
                && Epoll.isAvailable();
    }
}
