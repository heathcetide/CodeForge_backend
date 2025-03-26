package com.cetide.codeforge.controller;

import com.cetide.codeforge.common.ApiResponse;
import com.cetide.codeforge.common.auth.AuthContext;
import com.cetide.codeforge.model.dto.request.SqlRunRequest;
import com.cetide.codeforge.model.entity.exams.SqlQuestion;
import com.cetide.codeforge.model.entity.user.User;
import com.cetide.codeforge.sandbox.MultiTenantSqlSandboxExecutor;
import com.cetide.codeforge.sandbox.model.SqlRunResponse;
import com.cetide.codeforge.service.SqlQuestionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sql-runner")
public class SqlRunnerController {

    private final SqlQuestionService sqlQuestionService;

    public SqlRunnerController(SqlQuestionService sqlQuestionService) {
        this.sqlQuestionService = sqlQuestionService;
    }

    @PostMapping("/run")
    public ApiResponse<SqlRunResponse> runSql(@RequestBody SqlRunRequest request) {
        User currentUser = AuthContext.getCurrentUser();
        SqlQuestion byId = sqlQuestionService.getById(request.getSqlQuestionId());
        if (byId.getSeedDataSql() != null){
            return ApiResponse.success(MultiTenantSqlSandboxExecutor.runAndCompare(
                    byId.getSchemaSql() + byId.getSeedDataSql(),
                    request.getUserSql(),
                    byId.getAnswerSql(),
                    currentUser.getId()
            ));
        }else {
            return ApiResponse.success(MultiTenantSqlSandboxExecutor.runAndCompare(
                    byId.getSchemaSql(),
                    request.getUserSql(),
                    byId.getAnswerSql(),
                    currentUser.getId()
            ));
        }
    }
}
