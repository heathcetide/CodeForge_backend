package com.cetide.blog.model.enums;

import cn.hutool.core.util.StrUtil;
import com.cetide.blog.common.exception.ServiceException;

/**
 * 用户类型
 *
 * @author wangjr
 */
public enum UserType {
    BONSS("0", "员工"),
    MEDICAL("1", "医护用户"),
    BUSINESS("2", "经销商用户"),
    SERVER("3", "后台用户");

    private final String code;
    private final String info;

    UserType(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static UserType getUserTypeByCode(String code) {
        UserType[] values = UserType.values();
        for (UserType item : values) {
            if (StrUtil.equals(code, item.getCode())) return item;
        }
        throw new ServiceException("用户类型[" + code + "]不存在!");
    }
}
