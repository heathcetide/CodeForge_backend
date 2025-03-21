package com.cetide.codeforge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cetide.codeforge.model.dto.request.course.CourseAddDTO;
import com.cetide.codeforge.model.dto.request.course.CourseSearchDTO;
import com.cetide.codeforge.model.entity.Course;
import com.cetide.codeforge.model.vo.CourseVO;

import java.util.List;

public interface CourseService extends IService<Course> {
    Course getCourseById(Long courseId);
    List<Course> searchCourses(CourseSearchDTO searchDTO);
    Course createCourse(CourseAddDTO createDTO);
    Course updateCourseStatus(Long courseId, String status);

    /**
     * 根据种类名获取课程列表
     */
    List<CourseVO> getCourseByCategoryId(String id);
}