package com.github.chenmingq.server.basic.handler.adapter;

import com.github.chenmingq.common.common.annotation.ScanMapping;
import lombok.extern.slf4j.Slf4j;


/**
 * @author : chengmingqin
 * @date : 2019/10/20 13:31
 * description :加载配置文件
 */

@Slf4j
public class ServerPropertiesAdapter {


    private static ServerPropertiesAdapter instance = new ServerPropertiesAdapter();

    public static ServerPropertiesAdapter getInstance() {
        return instance;
    }

    public ServerPropertiesAdapter() {
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
        MappingHandlerAdapter.getInstance().initReqMappingClazz(name);
    }
}
