package com.github.chenmmingq.client.test.handler.adapter;

import com.github.chenmingq.common.common.annotation.ScanMapping;
import lombok.extern.slf4j.Slf4j;


/**
 * @author : chenmq
 * date : 2019-4-08
 * Project : netty-test
 * Description： 加载配置文件
 */

@Slf4j
public class ClientPropertiesAdapter {


    private static ClientPropertiesAdapter instance = new ClientPropertiesAdapter();

    public static ClientPropertiesAdapter getInstance() {
        return instance;
    }

    public ClientPropertiesAdapter() {
    }

    /**
     * 扫描包路径
     *
     * @param clazz
     */
    public void scanMapping(Class<?> clazz) {
        ScanMapping scanMapping = clazz.getAnnotation(ScanMapping.class);
        String name;
        if (null != scanMapping) {
            name = scanMapping.name();
        } else {
            Package aPackage = clazz.getPackage();
            name = aPackage.getName();
        }
        ClientMappingHandlerAdapter.getInstance().initReqMappingClazz(name);
    }

}
