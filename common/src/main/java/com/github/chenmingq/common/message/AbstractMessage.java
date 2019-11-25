package com.github.chenmingq.common.message;

import com.github.chenmingq.common.session.entiy.Session;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : chenmq
 * date : 2019-11-20
 * Project : socket-test
 * Description：消息
 */

@Getter
@Setter
public abstract class AbstractMessage {

    /**
     * 消息头
     */
    private HeaderMessage message;

    /**
     * session对象
     */
    private Session session;


    private int moduleId;

    private int cmdId;


    public void deCoder(byte[] body) {
    }

    public byte[] enCoder() {
        return null;
    }

    public int getModuleId() {
        return moduleId;
    }

    public int getCmdId() {
        return cmdId;
    }
}
