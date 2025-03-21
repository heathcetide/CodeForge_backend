package com.cetide.codeforge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cetide.codeforge.model.entity.CourseCategory;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

public interface CourseCategoryMapper extends BaseMapper<CourseCategory> {

    @Select("SELECT * FROM course_category WHERE name = #{name}")
    Optional<CourseCategory> selectByName(String name);
    
    @Select("SELECT * FROM course_category ORDER BY id ASC")
    List<CourseCategory> selectAllCategories();
} 