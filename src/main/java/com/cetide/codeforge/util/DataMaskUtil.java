package com.cetide.codeforge.util;

import org.apache.commons.lang3.StringUtils;

public class DataMaskUtil {
    
    public static String maskPhone(String phone) {
        if (StringUtils.isEmpty(phone)) return "";
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    public static String maskEmail(String email) {
        if (StringUtils.isEmpty(email)) return "";
        int atIndex = email.indexOf('@');
        if (atIndex <= 1) return email;
        return email.substring(0, 1) + "****" + email.substring(atIndex - 1);
    }

    public static String maskIdCard(String idCard) {
        if (StringUtils.isEmpty(idCard)) return "";
        return idCard.replaceAll("(\\d{4})\\d{10}(\\w{4})", "$1**********$2");
    }
} 