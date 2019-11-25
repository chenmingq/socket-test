package com.github.chenmingq.server.basic.handler.adapter;

import com.github.chenmingq.common.common.annotation.ScanMapping;
import com.github.chenmingq.common.constant.CommonConst;
import com.github.chenmingq.common.utils.LordPropertiesUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Properties;


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
     * 初始化系统服务配置
     */
    public void initSysProperties() {
        Properties properties = LordPropertiesUtils.lordProperties(CommonConst.PROPERTIES_NAME);
        if (null == properties) {
            log.error("{}", "初始化配置为空");
            return;
        }
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            Object k = entry.getKey();
            Object v = entry.getValue();
            if (null == k || null == v) {
                continue;
            }
            String key = (String) k;
            String value = (String) v;
            switch (key) {
                case "server.port":
                    CommonConst.PORT = Integer.parseInt(value);
                    break;
                case "app.mapper":
                    CommonConst.MAPPER_PACKAGE = value;
                    break;
                case "app.service.package":
                    CommonConst.SERVICE_PACKAGE = value;
                    break;
                default:
                    break;
            }
        }
        log.info("{}", "配置加载完成");
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
