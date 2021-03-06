package com.github.chenmingq.common.utils;

import com.github.chenmingq.common.config.SystemConfig;
import org.apache.commons.lang3.RandomUtils;

/**
 * @author: chenmingqin
 * date: 2019/11/23 15:35
 * description: id生成
 */

public class IdUtil {

    public static long get() {
        String valueOf = String.valueOf(SystemConfig.configId + System.currentTimeMillis());
        int i = RandomUtils.nextInt(10000, 99999);
        return Long.parseLong(valueOf + i);
    }

}
