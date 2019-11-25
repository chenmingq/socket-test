package com.github.chenmingq.common.utils.executor;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;


/**
 * @author : chenmq
 * date : 2019-11-20
 * Project : socket-test
 * Description
 */

public class LoginExecutor extends AbstractExecutor {


    private static final int CORE_POOL_SIZE = 100;
    private static final int MAXIMUM_POOL_SIZE = 100000;
    private static final long KEEP_ALIVE_TIME = 1000;
    private static final int WORK_QUEUE_SIZE = 1024;

    private static final String THREAD_NAME = "login-pool-%d";

    private static final ExecutorService EXECUTOR_SERVICE;

    static {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat(THREAD_NAME).build();

        EXECUTOR_SERVICE = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
                KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(WORK_QUEUE_SIZE), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

    }


    /**
     * 提价执行一个线程任务
     *
     * @param runnable
     */
    @Override
    public void execute(Runnable runnable) {
        EXECUTOR_SERVICE.execute(runnable);
    }

}
