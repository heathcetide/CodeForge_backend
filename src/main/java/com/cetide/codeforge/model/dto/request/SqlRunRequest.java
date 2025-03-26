package com.cetide.codeforge.model.dto.request;

public class SqlRunRequest {

    private Long SqlQuestionId;

    private String userSql;

    public String getUserSql() {
        return userSql;
    }

    public void setUserSql(String userSql) {
        this.userSql = userSql;
    }

    public Long getSqlQuestionId() {
        return SqlQuestionId;
    }

    public void setSqlQuestionId(Long sqlQuestionId) {
        SqlQuestionId = sqlQuestionId;
    }
}
