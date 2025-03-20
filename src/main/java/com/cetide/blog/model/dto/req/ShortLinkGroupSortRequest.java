package com.cetide.blog.model.dto.req;

/**
 * 短链接分组排序参数
 */
public class ShortLinkGroupSortRequest {

    /**
     * 短链接分组标识符
     */
    private String gid;

    /**
     * 排序字段
     */
    private Integer sortOrder;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}
