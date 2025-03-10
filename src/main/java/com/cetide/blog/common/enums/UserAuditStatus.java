package com.cetide.blog.common.enums;

import cn.hutool.core.util.StrUtil;
import com.cetide.blog.common.exception.ServiceException;

/**
 * 用户审核状态
 *
 * @author wangjr
 */
public enum UserAuditStatus {
    APPROVED("0", "审核通过"),
    PENDING("1", "待审核"),
    REJECTED("2", "审核拒绝"),
    EXPIRED("3", "已过期"),
    REVIEW("4", "再次审核"),;

    private final String code;
    private final String info;

    UserAuditStatus(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public static UserAuditStatus getUserAuditStatusByCode(String code) {
        UserAuditStatus[] values = UserAuditStatus.values();
        for (UserAuditStatus item : values) {
            if (StrUtil.equals(code, item.getCode())) return item;
        }
        throw new ServiceException("用户审核状[" + code + "]态不存在!");
    }
}
