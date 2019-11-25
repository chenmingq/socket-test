package com.github.chenmingq.common.serializer;

/**
 * @author : chenmq
 * date : 2019-11-20
 * Project : socket-test
 * Description： 序列化处理
 */

public interface SerializerProcess {


    /**
     * 反序列化
     *
     * @param bytes
     * @param clazz
     * @return
     */
    <T> T deserializer(byte[] bytes, Class<T> clazz);

    /**
     * 序列化
     *
     * @param val
     * @return
     */
    byte[] serializer(Object val);

}
