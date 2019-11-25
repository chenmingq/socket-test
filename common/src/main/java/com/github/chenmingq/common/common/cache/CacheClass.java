package com.github.chenmingq.common.common.cache;

import java.util.Map;

/**
 * @author : chenmq
 * date : 2019-11-20
 * Project : socket-test
 * Description：对象缓存
 */

public class CacheClass {

    /**
     * 控制器对象缓存
     */
    public static Map<Integer, Class<?>> REQ_MAPPING_MAP;

    /**
     * service对象
     */
    public static Map<Class<?>, Class<?>> SERVICE_IMPL_MAP;


}
