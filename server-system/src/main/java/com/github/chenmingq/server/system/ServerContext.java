package com.github.chenmingq.server.system;

import com.github.chenmingq.common.constant.CommonConst;
import com.github.chenmingq.common.utils.executor.ExecutorUtil;
import com.github.chenmingq.server.basic.beat.ServerHeartbeatMessage;
import com.github.chenmingq.server.basic.common.banner.Banner;
import com.github.chenmingq.server.basic.db.SqlFactory;
import com.github.chenmingq.server.basic.handler.adapter.FieldHandlerAdapter;
import com.github.chenmingq.server.basic.handler.adapter.MappingHandlerAdapter;
import com.github.chenmingq.server.basic.handler.adapter.ServerPropertiesAdapter;
import com.github.chenmingq.server.basic.listener.ListenerFactory;
import com.github.chenmingq.server.basic.listener.ListenerType;
import com.github.chenmingq.server.basic.pool.ServerMessagePool;
import com.github.chenmingq.server.system.listener.ListenerRegister;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerContext implements Runnable {

    private Class<?> clazz;

    private String[] args;

    public ServerContext(Class<?> clazz, String[] args) {
        this.clazz = clazz;
        this.args = args;
    }

    @Override
    public void run() {
        try {
            Banner.startBanner();
            ServerMessagePool.getInstance().registerMsg();
            ListenerRegister.getInstance().registerPreparedListeners();
            ServerPropertiesAdapter.getInstance().initSysProperties();
            ServerPropertiesAdapter.getInstance().scanMapping(this.clazz);
            MappingHandlerAdapter.getInstance().initServiceImplClazz(CommonConst.SERVICE_PACKAGE);
            FieldHandlerAdapter.getInstance().checkService();
            SqlFactory.getInstance().initDb();
            ListenerFactory.event(ListenerType.START_SERVER);
            ExecutorUtil.heartbeatExecutor.execute(new ServerHeartbeatMessage());
            Server.init();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("{}\n {}", "启动出现异常,强制退出程序", e.getMessage());
            System.exit(1);
        }
    }
}
