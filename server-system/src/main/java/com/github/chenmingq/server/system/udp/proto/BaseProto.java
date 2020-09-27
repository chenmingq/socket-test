package com.github.chenmingq.server.system.udp.proto;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author : chengmingqin
 * date : 2020/7/4
 * description :
 */

public abstract class BaseProto {

    /**
     * 编码
     *
     * @param proto
     * @return
     */
    public byte[] enCoder(BaseProto proto) {
        if (null == proto) {
            return null;
        }
        return JSONObject.toJSONString(proto).getBytes();
    }

    /**
     * 解码
     *
     * @param clazz
     * @param proto
     * @param <T>
     * @return
     */
    public <T> T deCoder(Class<? extends T> clazz, byte[] proto) {
        if (null == proto || proto.length < 1) {
            return null;
        }
        String jsonStr = new String(proto, StandardCharsets.UTF_8);

        if (StringUtils.isBlank(jsonStr)) {
            return null;
        }
        return JSONObject.parseObject(jsonStr, clazz);
    }

}
