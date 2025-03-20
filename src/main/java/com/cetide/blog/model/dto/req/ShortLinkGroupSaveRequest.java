package com.cetide.blog.model.dto.req;

/**
 * 短链接分组创建参数
 */
public class ShortLinkGroupSaveRequest {

    /**
     * 分组名
     */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
