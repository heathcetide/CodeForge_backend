package com.cetide.blog.common.core.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 表格分页数据对象
 *
 * @author wangjr
 */

public class TableDataInfo<T> implements Serializable {

    public TableDataInfo() {

    }

    private static final long serialVersionUID = 1L;

    /**
     * 总记录数
     */
    private long total;

    /**
     * 列表数据
     */
    private List<T> rows;


    /**
     * 分页
     *
     * @param list  列表数据
     * @param total 总记录数
     */
    public TableDataInfo(List<T> list, long total) {
        this.rows = list;
        this.total = total;
    }

    public static <T> TableDataInfo<T> build(IPage<T> page) {
        TableDataInfo<T> rspData = new TableDataInfo<>();
        rspData.setRows(page.getRecords());
        rspData.setTotal(page.getTotal());
        return rspData;
    }

    public static <T> TableDataInfo<T> build(List<T> list, long total) {
        TableDataInfo<T> rspData = new TableDataInfo<>();
        rspData.setRows(list);
        rspData.setTotal(total);
        return rspData;
    }

    public static <T> TableDataInfo<T> buildEmpty() {
        TableDataInfo<T> rspData = new TableDataInfo<>();
        rspData.setRows(Collections.emptyList());
        rspData.setTotal(0L);
        return rspData;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
