package com.github.chenmmingq.client.test.handler.adapter;

import com.github.chenmingq.common.common.annotation.AutoIn;
import com.github.chenmingq.common.common.annotation.ServiceImpl;
import com.github.chenmmingq.client.test.common.ClientCacheClass;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: chenmingqin
 * date: 2019/11/21 20:29
 * description:对象中属性设置值
 */

@Slf4j
public class ClientFieldHandlerAdapter {

    private static ClientFieldHandlerAdapter instance = new ClientFieldHandlerAdapter();

    public static ClientFieldHandlerAdapter getInstance() {
        return instance;
    }

    public ClientFieldHandlerAdapter() {
    }


    /**
     * 存放已经创建的扫描过得对象 {来源的对象,[service的实现对象列表]}
     */
    private Map<Class<?>, List<Class<?>>> classListMap = new ConcurrentHashMap<>();

    /**
     * 已经实例化的对象列表 {需要被实例化的class,实例化成功的对象}
     */
    private Map<Class<?>, Object> newInstanceMap = new ConcurrentHashMap<>();

    /**
     * 有属性需要被创建对象的对列
     */
    private BlockingQueue<Object> fieldClazzQueue = new ArrayBlockingQueue<>(10);

    /**
     * 创建service对象
     *
     * @param sourceInstance 来源的对象
     * @param check          是否检查有重复写入当前的对象
     */
    private void newInstanceService(Object sourceInstance, boolean check) {
        if (classListMap.containsKey(sourceInstance.getClass())) {
            return;
        }
        List<Class<?>> newInstanceClazzList = null;
        if (!check) {
            newInstanceClazzList = new ArrayList<>();
        }

        Field[] declaredFields = sourceInstance.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            Annotation[] annotations = declaredField.getAnnotations();
            for (Annotation annotation : annotations) {
                if (!(annotation instanceof AutoIn)) {
                    continue;
                }
                Class<?> fieldTypeClass = declaredField.getType();
                ServiceImpl serviceImpl = fieldTypeClass.getAnnotation(ServiceImpl.class);
                if (null == serviceImpl) {
                    continue;
                }

                try {
                    Class<?> serviceImplClazz = ClientCacheClass.SERVICE_IMPL_MAP.get(fieldTypeClass);
                    if (null == serviceImplClazz) {
                        continue;
                    }
                    // 是否为服务启动的时候检查每个对象写入了与当前实现的接口重复
                    if (check) {
                        Field[] fields = serviceImplClazz.getDeclaredFields();
                        if (fields.length < 1) {
                            continue;
                        }
                        if (Arrays.stream(fields).anyMatch(field -> field.getType() == fieldTypeClass)) {
                            throw new RuntimeException(serviceImplClazz.getName() +
                                    " 写入了与当前实现的接口重复 " + fieldTypeClass.getName());
                        }
                        continue;
                    }

                    if (newInstanceClazzList.contains(serviceImplClazz)) {
                        continue;
                    }
                    newInstanceClazzList.add(serviceImplClazz);

                    declaredField.setAccessible(true);
                    Object serviceImplInstance = newInstanceMap.get(serviceImplClazz);
                    if (null == serviceImplInstance) {
                        serviceImplInstance = serviceImplClazz.newInstance();
                        newInstanceMap.put(serviceImplClazz, serviceImplInstance);
                    }

                    fieldClazzQueue.add(serviceImplInstance);
                    declaredField.set(sourceInstance, serviceImplInstance);
                } catch (IllegalAccessException | InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }
        if (check) {
            return;
        }
        classListMap.put(sourceInstance.getClass(), newInstanceClazzList);
        while (!fieldClazzQueue.isEmpty()) {
            newInstanceService(fieldClazzQueue.poll(), false);
        }
        classListMap.clear();
    }


    /**
     * 检查service写入的对象是否有重复的
     */
    public void checkService() {
        Map<Class<?>, Class<?>> serviceImplMap = ClientCacheClass.SERVICE_IMPL_MAP;
        if (null == serviceImplMap) {
            return;
        }
        serviceImplMap.values().forEach(v -> {
            try {
                log.info("检查是否有写入的当前service对象 -> {}", v.getSimpleName());
                newInstanceService(v.newInstance(), true);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 创建多个对象
     *
     * @param sourceInstance
     */
    public void newInstanceAll(Object sourceInstance) {
        newInstanceService(sourceInstance, false);
    }

}
