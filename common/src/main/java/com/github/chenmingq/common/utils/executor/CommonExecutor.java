package com.github.chenmingq.common.utils.executor;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.List;
import java.util.concurrent.*;

/**
 * @author : chenmq
 * date : 2019-11-20
 * Project : socket-test
 * Description： 序列化类型常量
 */

public class CommonExecutor extends AbstractExecutor {


    private static final int CORE_POOL_SIZE = 100;
    private static final int MAXIMUM_POOL_SIZE = 100000;
    private static final long KEEP_ALIVE_TIME = 1000;
    private static final int WORK_QUEUE_SIZE = 1024;

    private static final String THREAD_NAME = "common-pool-%d";

    private static final ExecutorService EXECUTOR_SERVICE;
    private static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE;

    static {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat(THREAD_NAME).build();

        EXECUTOR_SERVICE = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
                KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(WORK_QUEUE_SIZE), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());


        SCHEDULED_EXECUTOR_SERVICE = new ScheduledThreadPoolExecutor(CORE_POOL_SIZE,
                new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());

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

    /**
     * 提交一个有返回值的线程任务
     *
     * @param callable
     * @return
     */
    @Override
    public <V> Future<V> submit(Callable<V> callable) {
        return EXECUTOR_SERVICE.submit(callable);
    }

    /**
     * 查看当前线程的状态
     *
     * @return
     */
    @Override
    public boolean state() {
        return EXECUTOR_SERVICE.isShutdown();
    }

    /**
     * 销毁一个线程
     */
    @Override
    public void shutdown() {
        EXECUTOR_SERVICE.shutdown();
    }

    /**
     * 关闭所有的任务
     *
     * @return
     */
    @Override
    public List<Runnable> shutdownNow() {
        return EXECUTOR_SERVICE.shutdownNow();
    }

    /**
     * 定时一个任务
     *
     * @param runnable 要执行的任务
     * @param delay    从现在开始延迟执行的时间
     * @param timeUnit 延时参数的时间单位
     */
    @Override
    public void schedule(Runnable runnable, long delay, TimeUnit timeUnit) {
        SCHEDULED_EXECUTOR_SERVICE.schedule(runnable, delay, timeUnit);
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
    @Override
    public <V> V schedule(Callable<V> callable, long delay, TimeUnit timeUnit) {
        ScheduledFuture<V> schedule = SCHEDULED_EXECUTOR_SERVICE.schedule(callable, delay, timeUnit);
        try {
            return schedule.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

}
