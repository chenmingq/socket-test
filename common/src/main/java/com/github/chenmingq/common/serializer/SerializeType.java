package com.github.chenmingq.common.serializer;

import java.util.Arrays;

/**
 * @author : chenmq
 * date : 2019-11-20
 * Project : socket-test
 * Description： 序列化类型常量
 */

public enum SerializeType {

    /**
     * jdk的serializable序列化类型
     */
    JDK_SERIALIZABLE((byte) 0),

    /**
     * ProtoStuff 序列化类型
     */
    PROTO_STUFF_SERIALIZABLE((byte) 1);

    private byte type;

    SerializeType(byte type) {
        this.type = type;
    }

    public byte getType() {
        return type;
    }

    public static SerializeType valueOf(byte type) {
        return Arrays.stream(SerializeType.values()).filter(v -> v.getType() == type).findFirst().orElse(null);
    }

}
