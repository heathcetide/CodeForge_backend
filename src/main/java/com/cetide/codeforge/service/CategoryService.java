package com.cetide.codeforge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cetide.codeforge.model.entity.Category;

import java.util.List;

public interface CategoryService extends IService<Category> {
    void saveCategory(Category category);
    Category getCategoryById(Long id);
    List<Category> getAllCategories();
    void deleteCategory(Long id);
}
