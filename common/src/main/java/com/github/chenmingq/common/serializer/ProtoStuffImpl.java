package com.github.chenmingq.common.serializer;


import com.github.chenmingq.common.serializer.factory.ProtoStuffUtils;

/**
 * @author : chenmq
 * date : 2019-11-20
 * Project : socket-test
 * Description： ProtoStuff 序列化处理
 */


public class ProtoStuffImpl implements SerializerProcess {

    @Override
    public <T> T deserializer(byte[] bytes, Class<T> clazz) {
        return ProtoStuffUtils.deserializer(bytes, clazz);
    }

    @Override
    public byte[] serializer(Object val) {
        return ProtoStuffUtils.serializer(val);
    }
}
