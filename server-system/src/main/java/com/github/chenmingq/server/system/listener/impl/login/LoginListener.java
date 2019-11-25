package com.github.chenmingq.server.system.listener.impl.login;

import com.github.chenmingq.common.utils.executor.ExecutorUtil;
import com.github.chenmingq.server.basic.listener.IListener;
import com.github.chenmingq.server.system.beat.ServerHeartbeatMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * projectName: nettyTest
 *
 * @author chenmingqin
 * create: 2019-10-27 21:01
 * description:
 **/
@Slf4j
public class LoginListener implements IListener {

    @Override
    public void event(Object obj) {
        if (!(obj instanceof Long)) {
            return;
        }
        long userId = (long) obj;
        ExecutorUtil.heartbeatExecutor.execute(new ServerHeartbeatMessage(userId));
    }
}
