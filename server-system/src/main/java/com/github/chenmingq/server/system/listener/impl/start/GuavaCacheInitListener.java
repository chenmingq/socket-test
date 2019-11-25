package com.github.chenmingq.server.system.listener.impl.start;

import com.github.chenmingq.common.common.annotation.AutoIn;
import com.github.chenmingq.server.basic.listener.IListener;
import com.github.chenmingq.server.system.cache.guava.GuavaCacheTemplate;
import com.github.chenmingq.server.system.entry.UserAll;
import com.github.chenmingq.server.system.mapper.user.UserMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


/**
 * projectName: nettyTest
 *
 * @author chenmingqin
 * create: 2019-10-27 21:00
 * description: Guava缓存
 **/

@Slf4j
public class GuavaCacheInitListener implements IListener {

    @AutoIn
    private UserMapper userMapper;


    @Override
    public void event(Object obj) {
        List<UserAll> userAlls = userMapper.selectAllList();
        if (null == userAlls) {
            return;
        }
        userAlls.forEach(u -> GuavaCacheTemplate.getInstance().setKey(u.getAccount(), u));
//        userAlls.forEach(u -> GuavaCacheTemplate.getInstance().setKey(String.valueOf(u.getId()), u));
    }
}
