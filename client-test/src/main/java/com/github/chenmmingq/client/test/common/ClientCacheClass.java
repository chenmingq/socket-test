package com.github.chenmmingq.client.test.common;

import java.util.Map;

/**
 * @author: chenmingqin
 * date: 2019/11/21 20:29
 * description:对象缓存
 */

public class ClientCacheClass {

    /**
     * 客户端返回对于的对象
     */
    public static Map<Integer, Class<?>> CLIENT_RES_MAPPING_MAP;

    /**
     * service对象
     */
    public static Map<Class<?>, Class<?>> SERVICE_IMPL_MAP;

}
