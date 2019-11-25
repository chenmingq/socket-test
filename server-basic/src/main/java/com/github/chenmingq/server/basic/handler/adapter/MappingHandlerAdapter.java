package com.github.chenmingq.server.basic.handler.adapter;

import com.github.chenmingq.common.common.annotation.ReqMapping;
import com.github.chenmingq.common.common.annotation.ServiceImpl;
import com.github.chenmingq.common.common.cache.CacheClass;
import com.github.chenmingq.common.utils.ClassUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@Slf4j
public class MappingHandlerAdapter {


    private static MappingHandlerAdapter instance = new MappingHandlerAdapter();

    public static MappingHandlerAdapter getInstance() {
        return instance;
    }

    public MappingHandlerAdapter() {
    }

    public void initReqMappingClazz(String packageName) {
        Map<Integer, Class<?>> classMap = new HashMap<>();
        Set<Class<?>> classes = ClassUtil.lordClazz(ReqMapping.class,packageName);
        for (Class<?> aClass : classes) {
            ReqMapping annotation = aClass.getAnnotation(ReqMapping.class);
            if (null == annotation) {
                continue;
            }
            log.info("lord controller class {}", aClass.getSimpleName());
            classMap.put(annotation.id(), aClass);
        }
        CacheClass.REQ_MAPPING_MAP = classMap;
    }


    public void initServiceImplClazz(String packageName) {
        Map<Class<?>, Class<?>> classMap = new HashMap<>();
        Set<Class<?>> implClazzSet = ClassUtil.lordClazz(ServiceImpl.class,packageName);
        for (Class<?> implClazz : implClazzSet) {
            if (implClazz.isInterface()) {
                continue;
            }
            Class<?>[] implClazzArr = implClazz.getInterfaces();
            if (implClazzArr.length < 1) {
                throw new RuntimeException("没有实现接口");
            }
            for (Class<?> serviceInterfaceClass : implClazzArr) {
                ServiceImpl annotation = serviceInterfaceClass.getAnnotation(ServiceImpl.class);
                if (null == annotation) {
                    throw new RuntimeException("没有使用 ServiceImpl 注解");
                }
                if (classMap.containsKey(serviceInterfaceClass)) {
                    throw new RuntimeException("Service重复了 " + serviceInterfaceClass.getName());
                }
                if (classMap.containsValue(implClazz)) {
                    throw new RuntimeException("Service重复了 " + serviceInterfaceClass.getName());
                }
                classMap.put(serviceInterfaceClass, implClazz);
                log.info("lord service class {} implements {}", implClazz.getSimpleName(), serviceInterfaceClass.getSimpleName());
            }
        }
        if (classMap.isEmpty()) {
            return;
        }
        CacheClass.SERVICE_IMPL_MAP = classMap;
    }

}
