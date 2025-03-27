package com.cetide.codeforge.model.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * 响应结果
 *
 * @author DELL
 * @date 2025-03-25 09:59:14
 */
public class RestResult<T> {

    /**
     * 业务返回码
     */
    @ApiModelProperty(value = "业务返回码")
    private String code;

    /**
     * 业务提示信息
     */
    @ApiModelProperty(value = "业务提示信息")
    private String msg;

    /**
     * 业务数据
     */
    @ApiModelProperty(value = "业务数据")
    private T data;

    public RestResult() {
    }

    public RestResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public RestResult(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
