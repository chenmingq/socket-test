package com.github.chenmingq.common.utils;


import com.github.chenmingq.common.common.annotation.SerializableTag;
import org.apache.commons.lang3.StringUtils;

/**
 * @author : chenmq
 * date : 2019-11-20
 * Project : socket-test
 * Description： object对象工具
 */

public class ObjectUtils {

    /**
     * 检查是否为jdk原生对象
     *
     * @param object
     * @return
     */
    public static boolean checkJdkObj(Object object) {
        if (null == object) {
            throw new RuntimeException("not this object");
        }
        boolean result = false;
        String name = object.getClass().getName();
        switch (name) {
            case "java.lang.Object":
            case "java.lang.String":
            case "java.lang.Integer":
            case "java.util.List":
            case "java.util.Map":
            case "java.util.Set":
                result = true;
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * 是否需要序列化的对象
     *
     * @param obj
     * @return
     */
    public static boolean checkSerializableObj(Object obj) {
        if (null == obj) {
            throw new RuntimeException("not this object");
        }
        return obj.getClass().isAnnotationPresent(SerializableTag.class);
    }


    /**
     * 下划线转驼峰
     *
     * @param str
     * @return
     */
    public static String underlineToHump(String str) {
        if (StringUtils.isEmpty(str)) {
            throw new RuntimeException("string is not");
        }
        char[] chars = str.toCharArray();
        StringBuilder sbd = new StringBuilder();
        boolean flage = false;
        for (char c : chars) {
            if (flage) {
                char toUpperCase = Character.toUpperCase(c);
                sbd.append(toUpperCase);
                flage = false;
                continue;
            }
            if ('_' == c) {
                flage = true;
                continue;
            }
            sbd.append(c);
        }
        return sbd.toString();
    }


    /**
     * 驼峰转下划线
     *
     * @param str
     * @return
     */
    public static String humpToUnderline(String str) {
        if (StringUtils.isEmpty(str)) {
            throw new RuntimeException("string is not");
        }
        char[] chars = str.toCharArray();
        StringBuilder sbd = new StringBuilder();

        for (char c : chars) {
            if (Character.isUpperCase(c)) {
                char toLowerCase = Character.toLowerCase(c);
                sbd.append('_');
                sbd.append(toLowerCase);
                continue;
            }
            sbd.append(c);
        }
        return sbd.toString();
    }

    public static void main(String[] args) {
        System.out.println(underlineToHump("ldD1d_dq1ewq"));
        System.out.println(humpToUnderline("asd1hiDGhs_d"));
    }


}
