package com.cetide.codeforge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cetide.codeforge.model.dto.request.course.category.CourseCategoryDTO;
import com.cetide.codeforge.model.entity.CourseCategory;
import com.cetide.codeforge.model.vo.CourseCategoryVO;

import java.util.List;

public interface CourseCategoryService extends IService<CourseCategory> {

    CourseCategory createCategory(CourseCategoryDTO dto);

    List<CourseCategory> getAllWithPopularCourses(int limit);

    /**
     * 获取课程种类列表
     */
    List<CourseCategoryVO> categoryList(String categoryName);
}
