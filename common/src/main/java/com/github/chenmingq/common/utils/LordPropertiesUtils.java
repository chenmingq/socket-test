package com.github.chenmingq.common.utils;

import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * @author : chenmq
 * date : 2019-11-20
 * Project : socket-test
 * Description： 配置读取
 */

public class LordPropertiesUtils {

    private final static Logger LOG = LoggerFactory.getLogger(LordPropertiesUtils.class);

    /**
     * 读取本地配置
     *
     * @param source
     * @return
     */
    public static Properties lordProperties(String source) {
        if (StringUtil.isNullOrEmpty(source)) {
            throw new RuntimeException("配置文件为空");
        }
        InputStream inputStream = null;
        Properties properties = null;
        try {
            URL resource = Thread.currentThread().getContextClassLoader().getResource(source);
            if (null == resource) {
                throw new RuntimeException("the source is not");
            }
            properties = new Properties();
            inputStream = resource.openStream();
            //加载配置
            properties.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }
}
