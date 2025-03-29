package com.cetide.codeforge.sandbox.model;

import java.util.List;
import java.util.Map;

public class SqlRunResponse {

    // 对比结果："CORRECT" 或 "WRONG"
    private String comparison;
    // 用户 SQL 执行结果
    private List<Map<String, Object>> userResult;
    // 标准答案 SQL 执行结果
    private List<Map<String, Object>> expectedResult;
    // 用户 SQL 执行耗时（毫秒）
    private long userExecutionTime;
    // 标准答案 SQL 执行耗时（毫秒）
    private long expectedExecutionTime;
    // 如果出现错误则返回错误信息，否则为 null
    private String error;

    public SqlRunResponse() {
    }

    public SqlRunResponse(String comparison, List<Map<String, Object>> userResult,
                          List<Map<String, Object>> expectedResult, long userExecutionTime,
                          long expectedExecutionTime, String error) {
        this.comparison = comparison;
        this.userResult = userResult;
        this.expectedResult = expectedResult;
        this.userExecutionTime = userExecutionTime;
        this.expectedExecutionTime = expectedExecutionTime;
        this.error = error;
    }

    public String getComparison() {
        return comparison;
    }

    public void setComparison(String comparison) {
        this.comparison = comparison;
    }

    public List<Map<String, Object>> getUserResult() {
        return userResult;
    }

    public void setUserResult(List<Map<String, Object>> userResult) {
        this.userResult = userResult;
    }

    public List<Map<String, Object>> getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(List<Map<String, Object>> expectedResult) {
        this.expectedResult = expectedResult;
    }

    public long getUserExecutionTime() {
        return userExecutionTime;
    }

    public void setUserExecutionTime(long userExecutionTime) {
        this.userExecutionTime = userExecutionTime;
    }

    public long getExpectedExecutionTime() {
        return expectedExecutionTime;
    }

    public void setExpectedExecutionTime(long expectedExecutionTime) {
        this.expectedExecutionTime = expectedExecutionTime;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}