package com.github.chenmingq.common.serializer;

import com.github.chenmingq.common.serializer.factory.SerializableUtils;

import java.io.Serializable;

/**
 * @author : chenmq
 * date : 2019-11-20
 * Project : socket-test
 * Description： Serializable 序列化处理
 */

public class SerializableImpl implements SerializerProcess {


    @Override
    public <T> T deserializer(byte[] bytes, Class<T> clazz) {
        return (T) SerializableUtils.deserializer(bytes);
    }

    @Override
    public byte[] serializer(Object val) {
        return SerializableUtils.serializer((Serializable) val);
    }
}
