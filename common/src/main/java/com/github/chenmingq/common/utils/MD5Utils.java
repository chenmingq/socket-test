package com.github.chenmingq.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author : chenmq
 * date : 2019-11-20
 * Project : socket-test
 * Description： md5
 */

public class MD5Utils {

    public static String md5Password(String password) {

        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(password.getBytes());
            StringBuilder builder = new StringBuilder();
            // 把每一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算
                int number = b & 0xff; // 加盐
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    builder.append("0");
                }
                builder.append(str);
            }

            // 标准的md5加密后的结果
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

}
