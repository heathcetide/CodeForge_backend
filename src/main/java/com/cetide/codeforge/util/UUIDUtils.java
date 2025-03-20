package com.cetide.codeforge.util;

import java.util.UUID;

/**
 * UUID工具类
 *
 * @author heathcetide
 */
public class UUIDUtils {
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}