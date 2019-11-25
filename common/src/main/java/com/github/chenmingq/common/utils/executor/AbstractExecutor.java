package com.github.chenmingq.common.utils.executor;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


/**
 * @author : chenmq
 * date : 2019-11-20
 * Project : socket-test
 * Description：
 */

public abstract class AbstractExecutor {

    private static final int CORE_POOL_SIZE = 100;
    private static final int MAXIMUM_POOL_SIZE = 100000;
    private static final long KEEP_ALIVE_TIME = 1000;
    private static final int WORK_QUEUE_SIZE = 1024;

    /**
     * 提价执行一个线程任务
     *
     * @param runnable
     */
    public void execute(Runnable runnable) {
    }

    /**
     * 提交一个有返回值的线程任务
     *
     * @param callable
     * @return
     */
    public <V> Future<V> submit(Callable<V> callable) {
        return null;
    }

    /**
     * 查看当前线程的状态
     *
     * @return
     */
    public boolean state() {
        return false;
    }

    /**
     * 销毁一个线程
     */
    public void shutdown() {
    }

    /**
     * 关闭所有的任务
     *
     * @return
     */
    public List<Runnable> shutdownNow() {
        return null;
    }

    /**
     * 定时一个任务
     *
     * @param runnable 要执行的任务
     * @param delay    从现在开始延迟执行的时间
     * @param timeUnit 延时参数的时间单位
     */
    public void schedule(Runnable runnable, long delay, TimeUnit timeUnit) {
    }

    /**
     * 定时执行一个有返回值的任务
     *
     * @param callable
     * @param delay
     * @param timeUnit
     * @param <V>
     * @return
     */
    public <V> V schedule(Callable<V> callable, long delay, TimeUnit timeUnit) {
        return null;
    }


}
