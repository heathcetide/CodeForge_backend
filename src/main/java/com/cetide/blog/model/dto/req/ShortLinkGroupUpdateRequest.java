package com.cetide.blog.model.dto.req;

/**
 * 短链接分组更新参数
 */
public class ShortLinkGroupUpdateRequest {

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 分组名
     */
    private String name;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
