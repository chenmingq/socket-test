package com.github.chenmmingq.client.test;


import com.github.chenmmingq.client.test.handler.adapter.ClientMappingHandlerAdapter;
import com.github.chenmmingq.client.test.handler.adapter.ClientPropertiesAdapter;
import com.github.chenmmingq.client.test.pool.ClientMessagePool;
import com.github.chenmmingq.client.test.util.executor.ClientExecutorUtil;

/**
 * @author: chenmingqin
 * date: 2019/11/21 20:09
 * description:
 */

public class ClientContext implements Runnable {

    private Class<?> clazz;

    private String[] args;

    public ClientContext(Class<?> clazz, String[] args) {
        this.clazz = clazz;
        this.args = args;
        ClientExecutorUtil.clientExecutor.execute(this);
    }

    @Override
    public void run() {
        ClientMessagePool.getInstance().registerMsg();
        ClientPropertiesAdapter.getInstance().scanMapping(this.clazz);
        ClientMappingHandlerAdapter.getInstance().initServiceImplClazz("com.github.chenmmingq.client.test.service.impl");
        new Client().startClient();
    }
}
