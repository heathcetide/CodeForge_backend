package com.cetide.codeforge.model.enums;

import cn.hutool.core.util.StrUtil;
import com.google.protobuf.ServiceException;

/**
 * 登录类型
 *
 * @author wangjr
 */
public enum LoginType {
    USERNAME("0", "用户名登录"), WECHAT("1", "微信登录");

    private final String code;
    private final String info;

    LoginType(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static LoginType getLoginTypeByCode(String code) throws ServiceException {
        LoginType[] values = LoginType.values();
        for (LoginType item : values) {
            if (StrUtil.equals(code, item.getCode())) return item;
        }
        throw new ServiceException("登录类型[" + code + "]不存在!");
    }
}
