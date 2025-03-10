package com.cetide.blog.common.enums;

import cn.hutool.core.util.StrUtil;
import com.cetide.blog.common.exception.ServiceException;

public enum UserLiveActionType {

    RESERVE("0", "预约"), FAVORITE("1", "收藏"),HISTORY("2", "历史记录");

    private final String code;
    private final String info;

    UserLiveActionType(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static UserLiveActionType getUserLiveActionTypeByCode(String code) {
        UserLiveActionType[] values = UserLiveActionType.values();
        for (UserLiveActionType item : values) {
            if (StrUtil.equals(code, item.getCode())) return item;
        }
        throw new ServiceException("直播或收藏类型[" + code + "]不存在!");
    }

}
