package com.qinjie.demo.utils;

import java.util.Random;

public class CommonUtils {
    /**
     * 功能描述: 验证邮箱
     * @param email 邮箱
     * @return true | false
     */
    public static boolean isEmail(String email) {
        if (email.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")) {
            return true;
        }
        return false;
    }
    /**
     *
     * 功能描述: 得到指定长度的随机字符串，存在重复的可能性
     *
     * @param: [length]生成随机字符串的长度
     * @return: java.lang.String
     * @auther: 秦杰
     */
    public static String getRandomString(int length) { //length表示生成字符串的长度

        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
