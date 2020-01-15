package com.github.chenmingq.server.system.listener.impl.login;

import com.github.chenmingq.server.basic.beat.BeatManager;
import com.github.chenmingq.server.basic.listener.IListener;
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
        BeatManager.getInstance().offer(userId);
    }
}
