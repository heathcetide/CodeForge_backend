package com.cetide.codeforge.controller;

import com.cetide.codeforge.common.ApiResponse;
import com.cetide.codeforge.model.dto.request.SqlRunRequest;
import com.cetide.codeforge.sandbox.MultiTenantSqlSandboxExecutor;
import com.cetide.codeforge.sandbox.model.SqlRunResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sql-runner")
public class SqlRunnerController {

    @PostMapping("/run")
    public ApiResponse<SqlRunResponse> runSql(@RequestBody SqlRunRequest request) {
        return ApiResponse.success(MultiTenantSqlSandboxExecutor.runAndCompare(
                request.getInitSql(),
                request.getUserSql(),
                request.getExpectedSql(),
                request.getUserId()
        ));
    }
}
