package com.cetide.codeforge.sandbox;

import com.cetide.codeforge.sandbox.model.SqlRunResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class MultiTenantSqlSandboxExecutor {

    // 使用内存中的 H2 数据库，DB_CLOSE_DELAY=-1 表示服务关闭前数据库一直保持活动
    private static final String BASE_JDBC_URL = "jdbc:h2:mem:multiTenantDB;DB_CLOSE_DELAY=-1";

    /**
     * 执行用户 SQL 和标准 SQL，并返回详细的执行结果数据（包含查询结果、执行时间、对比结果等）。
     *
     * @param initSql     初始化测试环境的 SQL（例如创建临时表、插入数据等）
     * @param userSql     用户提交的 SQL
     * @param expectedSql 标准答案 SQL
     * @param userId      用户ID，用于构造专属的 Schema（如 schema_123）
     * @return SqlRunResponse 对象，其中包含：
     *         - comparison: "CORRECT" 或 "WRONG"
     *         - userResult: 用户 SQL 执行结果（List<Map<String,Object>>）
     *         - expectedResult: 标准答案 SQL 执行结果
     *         - userExecutionTime: 用户 SQL 执行耗时（毫秒）
     *         - expectedExecutionTime: 标准 SQL 执行耗时（毫秒）
     *         - error: 如有错误则记录错误信息
     */
    public static SqlRunResponse runAndCompare(String initSql, String userSql, String expectedSql, Long userId) {
        SqlRunResponse response = new SqlRunResponse();
        try (Connection conn = DriverManager.getConnection(BASE_JDBC_URL)) {
            String schemaName = "schema_" + userId;
            // ① 创建并切换到专属 Schema，并初始化租户专属 Schema（加载 sandbox_schema.sql 文件中的表结构）
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(String.format("CREATE SCHEMA IF NOT EXISTS %s", schemaName));
                stmt.execute(String.format("SET SCHEMA %s", schemaName));
                // 通过资源文件初始化表结构（例如学生、课程、教师表）
                initTenantSchema(conn);
            }

            // ② 使用事务隔离测试环境（执行用户 SQL 和标准 SQL 各一次，中间通过 rollback 重置环境）
            conn.setAutoCommit(false);
            List<Map<String, Object>> userResult;
            List<Map<String, Object>> expectedResult;
            long userExecutionTime;
            long expectedExecutionTime;
            try (Statement stmt = conn.createStatement()) {
                // 执行测试环境初始化 SQL（例如创建考试相关的表、插入数据）
                for (String sql : initSql.split(";")) {
                    if (!sql.trim().isEmpty()) {
                        stmt.execute(sql);
                    }
                }
                // 执行用户提交的 SQL并计时
                long startUser = System.currentTimeMillis();
                ResultSet userRs = stmt.executeQuery(userSql);
                userExecutionTime = System.currentTimeMillis() - startUser;
                userResult = extractResult(userRs);
                // 回滚，清除测试过程中对环境的修改
                conn.rollback();

                // 重置所有基础表的自增列
                resetIdentities(stmt);

                // 再次执行初始化 SQL，为标准答案的执行提供环境
                for (String sql : initSql.split(";")) {
                    if (!sql.trim().isEmpty()) {
                        stmt.execute(sql);
                    }
                }
                // 执行标准答案 SQL并计时
                long startExpected = System.currentTimeMillis();
                ResultSet expectedRs = stmt.executeQuery(expectedSql);
                expectedExecutionTime = System.currentTimeMillis() - startExpected;
                expectedResult = extractResult(expectedRs);
                conn.rollback();
            }
            // ③ 比较用户 SQL 和标准答案 SQL 的执行结果
            String comparison = userResult.equals(expectedResult) ? "CORRECT" : "WRONG";
            response.setComparison(comparison);
            response.setUserResult(userResult);
            response.setExpectedResult(expectedResult);
            response.setUserExecutionTime(userExecutionTime);
            response.setExpectedExecutionTime(expectedExecutionTime);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setError(e.getMessage());
            return response;
        }
    }

    // 辅助方法：将 ResultSet 中的查询结果转换为 List<Map<String, Object>>
    private static List<Map<String, Object>> extractResult(ResultSet rs) throws SQLException {
        List<Map<String, Object>> result = new ArrayList<>();
        ResultSetMetaData meta = rs.getMetaData();
        int cols = meta.getColumnCount();
        while (rs.next()) {
            Map<String, Object> row = new LinkedHashMap<>();
            for (int i = 1; i <= cols; i++) {
                row.put(meta.getColumnLabel(i), rs.getObject(i));
            }
            result.add(row);
        }
        return result;
    }

    /**
     * 通过读取资源文件 sandbox/sandbox_schema.sql 初始化租户专属 Schema，
     * 该文件中包含学生、课程、教师等示例表的建表语句。
     *
     * @param conn 当前数据库连接，已切换到用户专属 Schema
     * @throws Exception 如果读取或执行 SQL 脚本失败
     */
    private static void initTenantSchema(Connection conn) throws Exception {
        InputStream in = MultiTenantSqlSandboxExecutor.class
                .getClassLoader()
                .getResourceAsStream("sandbox/sandbox_schema.sql");
        if (in == null) {
            throw new RuntimeException("无法找到 sandbox_schema.sql 文件，请确认其位于 resources/sandbox 目录下。");
        }
        String schemaSql = new BufferedReader(new InputStreamReader(in))
                .lines()
                .collect(Collectors.joining("\n"));
        try (Statement stmt = conn.createStatement()) {
            for (String sql : schemaSql.split(";")) {
                if (!sql.trim().isEmpty()) {
                    stmt.execute(sql);
                }
            }
        }
    }

    /**
     * 重置 teacher、student、course 表的自增列，使其从 1 开始计数。
     *
     * @param stmt 当前的 Statement 对象
     * @throws SQLException 如果执行 SQL 失败
     */
    private static void resetIdentities(Statement stmt) throws SQLException {
        stmt.execute("ALTER TABLE teacher ALTER COLUMN id RESTART WITH 1");
        stmt.execute("ALTER TABLE student ALTER COLUMN id RESTART WITH 1");
        stmt.execute("ALTER TABLE course ALTER COLUMN id RESTART WITH 1");
    }
}
