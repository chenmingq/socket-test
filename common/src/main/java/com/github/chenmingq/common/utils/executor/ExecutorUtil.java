package com.github.chenmingq.common.utils.executor;


/**
 * @author : chenmq
 * date : 2019-11-20
 * Project : socket-test
 * Description：
 */

public class ExecutorUtil {

    public static AbstractExecutor commonExecutor;
    public static AbstractExecutor loginExecutor;
    public static HeartbeatExecutor heartbeatExecutor;


    static {
        commonExecutor = new CommonExecutor();
        loginExecutor = new LoginExecutor();
        heartbeatExecutor = new HeartbeatExecutor();
    }
}
