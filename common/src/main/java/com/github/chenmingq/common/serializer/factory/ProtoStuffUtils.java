package com.github.chenmingq.common.serializer.factory;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : chenmq
 * date : 2019-11-20
 * Project : socket-test
 * Description：Protostuff序列化工具
 */

public class ProtoStuffUtils {

    private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<>();

    private static <T> Schema<T> getSchema(Class<T> clazz) {
        @SuppressWarnings("unchecked")
        Schema<T> schema = (Schema<T>) cachedSchema.get(clazz);
        if (schema == null) {
            schema = RuntimeSchema.getSchema(clazz);
            if (schema != null) {
                cachedSchema.put(clazz, schema);
            }
        }
        return schema;
    }


    /**
     * 序列化
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> byte[] serializer(T t) {
        @SuppressWarnings("unchecked")
        Class<T> clazz = (Class<T>) t.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            Schema<T> schema = getSchema(clazz);
            return ProtostuffIOUtil.toByteArray(t, schema, buffer);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        } finally {
            buffer.clear();
        }
    }

    /**
     * 反序列化
     *
     * @param bytes
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T deserializer(byte[] bytes, Class<T> clazz) {
        try {
            Schema<T> schema = getSchema(clazz);
            T t = schema.newMessage();
            ProtostuffIOUtil.mergeFrom(bytes, t, schema);
            return t;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
}
