package com.cetide.blog.controller;

import com.cetide.blog.model.entity.Category;
import com.cetide.blog.service.CategoryService;
import com.cetide.blog.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ApiResponse<Category> createCategory(@RequestBody Category category) {
        categoryService.saveCategory(category);
        return ApiResponse.success(category);
    }

    @GetMapping("/{id}")
    public ApiResponse<Category> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        if (category != null) {
            return ApiResponse.success(category);
        }
        return ApiResponse.error(404, "Category not found");
    }

    @GetMapping
    public ApiResponse<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ApiResponse.success(categories);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Boolean> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ApiResponse.success(true);
    }
}
