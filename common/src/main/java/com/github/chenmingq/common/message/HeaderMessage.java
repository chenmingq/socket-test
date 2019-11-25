package com.github.chenmingq.common.message;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

/**
 * @author : chenmq
 * date : 2019-11-20
 * Project : socket-test
 * Description：消息头
 */

@Getter
@Setter
public class HeaderMessage {

    /**
     * 唯一通信标志
     */
    private int magic;

    /**
     * 模块id
     */
    private int moduleId;

    /**
     * 指定某个的子模块
     */
    private int cmdId;

    /**
     * 消息体
     */
    private byte[] body;

    @Override
    public String toString() {
        return "Message{" +
                "magic=" + magic +
                ", moduleId=" + moduleId +
                ", cmdId=" + cmdId +
                ", body=" + Arrays.toString(body) +
                '}';
    }
}
