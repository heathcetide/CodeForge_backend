package com.cetide.blog.util;

import java.security.SecureRandom;

/**
 * 分组ID随机生成器
 */
public class RandomGenerator {

    private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWSYZabcdefghijklmnopqrstuvwsyz";

    private static final SecureRandom random = new SecureRandom();

    /**
     * 生成随机分组ID
     * @param length 生成多少位
     * @return 分组ID
     */
    public static String generateRandomString(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}
