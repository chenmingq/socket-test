package com.github.chenmmingq.client.test.util.executor;

/**
 * @author: chenmingqin
 * date: 2019/11/22 10:22
 * description:
 */

public class ClientExecutorUtil {
    public static ClientExecutor clientExecutor;

    static {
        clientExecutor = new ClientExecutor();
    }
}
