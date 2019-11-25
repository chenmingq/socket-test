package com.github.chenmingq.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: chenmingqin
 * date: 2019/11/16 10:46
 * description: 时间工具
 */

public class DateUtil {


    private final static String PATTERN_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";


    public static String format() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_YYYY_MM_DD_HH_MM_SS);
        return sdf.format(date);
    }

}
