package com.cetide.codeforge.controller;

import com.cetide.codeforge.common.ApiResponse;
import com.cetide.codeforge.model.dto.request.course.CourseAddDTO;
import com.cetide.codeforge.model.dto.request.course.CourseSearchDTO;
import com.cetide.codeforge.model.dto.request.course.category.CourseCategoryDTO;
import com.cetide.codeforge.model.entity.Course;
import com.cetide.codeforge.model.vo.CourseCategoryVO;
import com.cetide.codeforge.model.vo.CourseVO;
import com.cetide.codeforge.service.CourseCategoryService;
import com.cetide.codeforge.service.CourseService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 课程模块
 */
@Api(tags = "课程模块")
@RestController
@RequestMapping("/api/courses")
public class CourseController {


    private final CourseService courseService;

    private final CourseCategoryService courseCategoryService;

    public CourseController(CourseService courseService, CourseCategoryService courseCategoryService) {
        this.courseService = courseService;
        this.courseCategoryService = courseCategoryService;
    }

    /**
     * 根据分组获取课程列表
     */
    @GetMapping("/category/list/{groupName}")
    public ApiResponse<List<CourseCategoryVO>> listCategory(@PathVariable String groupName) {
        return ApiResponse.success(courseCategoryService.categoryList(groupName));
    }

    /**
     * 根据categoryId获取课程列表
     */
    @GetMapping("/course/{id}")
    public ApiResponse<List<CourseVO>> listCourse(@PathVariable String id) {
        return ApiResponse.success(courseService.getCourseByCategoryId(id));
    }



    @GetMapping("/{courseId}")
    public ApiResponse<Course> getCourseDetail(@PathVariable Long courseId) {
        return ApiResponse.success(courseService.getCourseById(courseId));
    }

    @PostMapping("/search")
    public ApiResponse<List<Course>> searchCourses(@Valid @RequestBody CourseSearchDTO searchDTO) {
        return ApiResponse.success(courseService.searchCourses(searchDTO));
    }

    @PostMapping
    public ApiResponse<Course> createCourse(@Valid @RequestBody CourseAddDTO createDTO) {
        return ApiResponse.success(courseService.createCourse(createDTO));
    }

    @PutMapping("/{courseId}/status")
    public ApiResponse<Course> updateStatus(
            @PathVariable Long courseId,
            @RequestParam @NotBlank String status) {
        return ApiResponse.success(courseService.updateCourseStatus(courseId, status));
    }

} 