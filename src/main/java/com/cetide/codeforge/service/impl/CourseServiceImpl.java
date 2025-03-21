package com.cetide.codeforge.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cetide.codeforge.exception.BusinessException;
import com.cetide.codeforge.mapper.CourseMapper;
import com.cetide.codeforge.mapper.CourseCategoryMapper;
import com.cetide.codeforge.model.dto.request.course.CourseAddDTO;
import com.cetide.codeforge.model.dto.request.course.CourseSearchDTO;
import com.cetide.codeforge.model.entity.Course;
import com.cetide.codeforge.model.vo.CourseVO;
import com.cetide.codeforge.service.CourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {


    private final CourseMapper courseMapper;

    private final CourseCategoryMapper categoryMapper;

    public CourseServiceImpl(CourseMapper courseMapper, CourseCategoryMapper categoryMapper) {
        this.courseMapper = courseMapper;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Course getCourseById(Long courseId) {
        return null;
    }

    @Override
    public List<Course> searchCourses(CourseSearchDTO searchDTO) {
        return null;
    }

    @Transactional
    @Override
    public Course createCourse(CourseAddDTO createDTO) {
        // 验证分类是否存在
        Course course = new Course();
        BeanUtils.copyProperties(createDTO, course);
        course.setEnrollCount(0L);
        course.setStarRating(0);
        course.setIsRecommended(false);
        courseMapper.insert(course);
        return course;
    }

    @Transactional
    @Override
    public Course updateCourseStatus(Long courseId, String status) {
        Course course = getCourseById(courseId);
        course.setStatus(status);
        courseMapper.updateById(course);
        return course;
    }

    @Override
    public List<CourseVO> getCourseByCategoryId(String id) {
        LambdaQueryWrapper<Course> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Course::getCategoryId, id);
        return courseMapper.selectList(queryWrapper).stream()
                .map(course -> new CourseVO.Builder()
                        .setId(String.valueOf(course.getId()))
                        .setTitle(course.getTitle())
                        .setDescription(course.getDescription())
                        .setCoverImage(course.getCoverImage())
                        .setDifficulty(course.getDifficulty())
                        .setStatus(course.getStatus())
                        .setFree(course.getIsFree())
                        .setLevel(course.getLevel())
                        .setEstimatedHours(course.getEstimatedHours())
                        .setRecommended(course.getIsRecommended())
                        .setEnrollCount(course.getEnrollCount())
                        .setStarRating(course.getStarRating())
                        .build())
                .collect(Collectors.toList());
    }
} 