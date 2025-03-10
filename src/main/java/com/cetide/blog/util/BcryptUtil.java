package com.cetide.blog.util;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class BcryptUtil {
    public static String encode(String rawPassword) {
        return BCrypt.withDefaults().hashToString(12, rawPassword.toCharArray());
    }

    public static boolean matches(String rawPassword, String encodedPassword) {
        return BCrypt.verifyer().verify(rawPassword.toCharArray(), encodedPassword).verified;
    }

    public static boolean isEncrypted(String password) {
        return password.startsWith("$2a$");
    }
} 