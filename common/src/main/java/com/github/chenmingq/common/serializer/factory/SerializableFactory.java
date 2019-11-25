package com.github.chenmingq.common.serializer.factory;


import com.github.chenmingq.common.serializer.ProtoStuffImpl;
import com.github.chenmingq.common.serializer.SerializableImpl;
import com.github.chenmingq.common.serializer.SerializeType;
import com.github.chenmingq.common.serializer.SerializerProcess;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : chenmq
 * date : 2019-11-20
 * Project : socket-test
 * Description：序列化工厂
 */

public class SerializableFactory {

    private static Map<Byte, SerializerProcess> map = new HashMap<>();


    public <T> T deserializer(byte type, byte[] bytes, Class<T> t) {
        if (!map.containsKey(type)) {
            throw new RuntimeException("反序列化操作失败");
        }
        return map.get(type).deserializer(bytes, t);
    }

    public byte[] serializer(byte type, Object val) {
        if (!map.containsKey(type)) {
            throw new RuntimeException("序列化操作失败");
        }
        return map.get(type).serializer(val);
    }

    static {
        map.put(SerializeType.JDK_SERIALIZABLE.getType(), new SerializableImpl());
        map.put(SerializeType.PROTO_STUFF_SERIALIZABLE.getType(), new ProtoStuffImpl());
    }


}
