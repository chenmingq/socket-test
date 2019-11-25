package com.github.chenmmingq.client.test.handler.adapter;

import com.github.chenmingq.common.common.annotation.ReqMapping;
import com.github.chenmingq.common.common.annotation.ServiceImpl;
import com.github.chenmingq.common.utils.ClassUtil;
import com.github.chenmmingq.client.test.common.ClientCacheClass;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author: chenmingqin
 * date: 2019/11/21 20:29
 * description:对象扫描
 */

@Slf4j
public class ClientMappingHandlerAdapter {


    private static ClientMappingHandlerAdapter instance = new ClientMappingHandlerAdapter();

    public static ClientMappingHandlerAdapter getInstance() {
        return instance;
    }

    public ClientMappingHandlerAdapter() {
    }

    public void initReqMappingClazz(String packageName) {
        Map<Integer, Class<?>> classMap = new HashMap<>();
        Set<Class<?>> classes = ClassUtil.lordClazz(packageName);
        for (Class<?> aClass : classes) {
            ReqMapping annotation = aClass.getAnnotation(ReqMapping.class);
            if (null == annotation) {
                continue;
            }
            log.info("lord client controller class {}", aClass.getSimpleName());
            classMap.put(annotation.id(), aClass);
        }
        ClientCacheClass.CLIENT_RES_MAPPING_MAP = classMap;
    }


    public void initServiceImplClazz(String packageName) {
        Map<Class<?>, Class<?>> classMap = new HashMap<>();
        Set<Class<?>> implClazzSet = ClassUtil.lordClazz(packageName);
        for (Class<?> implClazz : implClazzSet) {
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
                log.info("lord client service class {} implements {}", implClazz.getSimpleName(), serviceInterfaceClass.getSimpleName());
            }
        }
        if (classMap.isEmpty()) {
            return;
        }
        ClientCacheClass.SERVICE_IMPL_MAP = classMap;
    }

}
