package com.cetide.codeforge.model.dto;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/**
 * 分页查询结果
 *
 * @author DELL
 * @date 2025-03-25 09:59:14
 */
public class PageResult<T> {

    /**
     * 页码
     */
    @ApiModelProperty(value = "页码")
    public Long pageIndex;

    /**
     * 每页显示数量
     */
    @ApiModelProperty(value = "每页显示数量")
    public Long pageSize;

    /**
     * 总条数
     */
    @ApiModelProperty(value = "总条数")
    public Long totalRecords;

    /**
     * 总页数
     */
    @ApiModelProperty(value = "总页数")
    public Long totalPages;

    /**
     * 查询结果集
     */
    @ApiModelProperty(value = "查询结果集")
    public List<T> records;

    public PageResult(long totalRecords, long pageIndex, long pageSize, List<T> records) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalRecords = totalRecords;
        this.records = records;
        this.totalPages = (totalRecords + pageIndex - 1) / pageSize;
    }

    public PageResult(long totalRecords,long totalPages, long pageIndex, long pageSize, List<T> records) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalRecords = totalRecords;
        this.records = records;
        this.totalPages = totalPages;
    }

    public Long getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Long pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }
}

