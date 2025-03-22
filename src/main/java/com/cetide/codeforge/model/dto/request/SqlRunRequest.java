package com.cetide.codeforge.model.dto.request;

public class SqlRunRequest {

    private String initSql;
    private String userSql;
    private String expectedSql;
    private Long userId;

    public String getInitSql() {
        return initSql;
    }

    public void setInitSql(String initSql) {
        this.initSql = initSql;
    }

    public String getUserSql() {
        return userSql;
    }

    public void setUserSql(String userSql) {
        this.userSql = userSql;
    }

    public String getExpectedSql() {
        return expectedSql;
    }

    public void setExpectedSql(String expectedSql) {
        this.expectedSql = expectedSql;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
