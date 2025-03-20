package com.cetide.codeforge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cetide.codeforge.mapper.CategoryMapper;
import com.cetide.codeforge.model.entity.Category;
import com.cetide.codeforge.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void saveCategory(Category category) {
        categoryMapper.insert(category);  // 插入新的分类记录
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryMapper.selectById(id);  // 根据分类ID获取分类信息
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryMapper.selectList(null);  // 获取所有分类
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryMapper.selectById(id);
        if (category != null) {
            category.setDeleted(1);  // 将删除标志设置为1，表示软删除
            categoryMapper.updateById(category);  // 更新分类删除状态
        }
    }
}
