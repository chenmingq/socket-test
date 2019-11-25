package com.github.chenmingq.common.constant;

/**
 * @author : chenmq
 * date : 2019-11-20
 * Project : socket-test
 * Description：公共常量
 */

public class CommonConst {

    /**
     * 唯一通信标志
     */
    public final static int MAGIC_CODE = 0x0CAFFEE0;

    /**
     * 消息头长度
     */
    public final static int HEAD_LENGTH = 0x16;

    /**
     * 启动banner
     */
    public static final String START_BANNER_NAME = "banner.txt";

    /**
     * 端口
     */
    public static Integer PORT = 0;

    /**
     * 配置文件名称
     */
    public static final String PROPERTIES_NAME = "application.properties";

    /**
     * 数据库连接池配置文件名称
     */
    public static final String DB_SERVER_PROPERTIES = "db_server.properties";

    /**
     *  mapper 操作对象对象地址
     */
    public static String MAPPER_PACKAGE;

    /**
     * service所在的包路径
     */
    public static String SERVICE_PACKAGE;

    /**
     * mybatis 配置名称
     */
    public static String MY_BATIS_XML_PATH = "mybatis-config.xml";


}
