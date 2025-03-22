package com.cetide.codeforge.controller;

import com.cetide.codeforge.model.entity.exams.SqlQuestion;
import com.cetide.codeforge.service.SqlQuestionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sql-questions")
public class SqlQuestionController {

    private final SqlQuestionService sqlQuestionService;

    public SqlQuestionController(SqlQuestionService sqlQuestionService) {
        this.sqlQuestionService = sqlQuestionService;
    }

    @GetMapping
    public List<SqlQuestion> getAll() {
        return sqlQuestionService.list();
    }

    @GetMapping("/{id}")
    public SqlQuestion getById(@PathVariable Long id) {
        return sqlQuestionService.getById(id);
    }

    @PostMapping
    public boolean create(@RequestBody SqlQuestion question) {
        return sqlQuestionService.save(question);
    }

    @PutMapping
    public boolean update(@RequestBody SqlQuestion question) {
        return sqlQuestionService.updateById(question);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return sqlQuestionService.removeById(id);
    }
}
