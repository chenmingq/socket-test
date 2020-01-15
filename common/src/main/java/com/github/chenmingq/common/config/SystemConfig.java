package com.github.chenmingq.common.config;

import com.github.chenmingq.common.constant.CommonConst;
import com.github.chenmingq.common.utils.LordPropertiesUtils;

import java.util.Properties;

/**
 * @author : chengmingqin
 * date : 2020/1/15
 * description :系统配置
 */

public class SystemConfig {
    private final static String SERVER_PORT = "server.port";
    private final static String SCAN_MAPPER_PACKAGE = "scan.mapper.package";
    private final static String SCAN_SERVICE_PACKAGE = "scan.service.package";
    private final static String START_BANNER_PATH = "banner";
    private final static String MY_BATIS_XML_PATH = "mybatis.path";
    private final static String DB_SERVER_PROPERTIES = "db.source.properties";
    private final static String CONFIG_ID = "config.id";


    public static int port;
    public static int configId;
    public static String scanMapperPackage;
    public static String scanServicePackage;
    public static String startBannerPath;
    public static String myBatisXmlPath;
    public static String dbServerProperties;

    public static void initSysConfig() {
        try {
            Properties properties = LordPropertiesUtils.lordProperties(CommonConst.PROPERTIES_NAME);
            if (null == properties) {
                throw new NullPointerException("找不到 " + CommonConst.PROPERTIES_NAME);
            }
            port = Integer.parseInt((String) properties.get(SERVER_PORT));
            configId = Integer.parseInt((String) properties.get(CONFIG_ID));
            scanMapperPackage = (String) properties.get(SCAN_MAPPER_PACKAGE);
            scanServicePackage = (String) properties.get(SCAN_SERVICE_PACKAGE);
            startBannerPath = (String) properties.get(START_BANNER_PATH);
            myBatisXmlPath = (String) properties.get(MY_BATIS_XML_PATH);
            dbServerProperties = (String) properties.get(DB_SERVER_PROPERTIES);
        } catch (Exception e) {
            throw new RuntimeException("加载系统配置异常");
        }
    }


}
