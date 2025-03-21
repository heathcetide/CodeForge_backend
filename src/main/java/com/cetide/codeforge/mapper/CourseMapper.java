package com.cetide.codeforge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cetide.codeforge.model.entity.Course;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CourseMapper extends BaseMapper<Course> {
    @Select("<script>" +
            "SELECT * FROM course " +
            "<where>" +
            "   <if test='keyword != null'>" +
            "       (title LIKE CONCAT('%',#{keyword},'%') OR description LIKE CONCAT('%',#{keyword},'%'))" +
            "   </if>" +
            "   <if test='difficulty != null'>" +
            "       AND difficulty = #{difficulty}" +
            "   </if>" +
            "   <if test='categoryId != null'>" +
            "       AND category_id = #{categoryId}" +
            "   </if>" +
            "   <if test='status != null'>" +
            "       AND status = #{status}" +
            "   </if>" +
            "</where>" +
            "ORDER BY ${orderBy} ${orderDir}" +
            "</script>")
    List<Course> searchCourses(@Param("keyword") String keyword,
                               @Param("difficulty") String difficulty,
                               @Param("categoryId") Long categoryId,
                               @Param("status") String status,
                               @Param("orderBy") String orderBy,
                               @Param("orderDir") String orderDir);
} 