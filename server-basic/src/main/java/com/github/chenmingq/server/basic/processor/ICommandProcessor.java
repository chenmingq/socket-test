package com.github.chenmingq.server.basic.processor;


import com.github.chenmingq.common.message.AbstractMessage;

/**
 * 处理消息分发
 */
public interface ICommandProcessor<T extends AbstractMessage> {

    /**
     * 消息处理
     *
     * @param t
     */
    void processor(T t);

}
