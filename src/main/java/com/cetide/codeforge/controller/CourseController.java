package com.cetide.codeforge.controller;

import com.cetide.codeforge.common.ApiResponse;
import com.cetide.codeforge.common.auth.AuthContext;
import com.cetide.codeforge.model.entity.UserCourse;
import com.cetide.codeforge.model.entity.user.User;
import com.cetide.codeforge.model.vo.CourseCategoryVO;
import com.cetide.codeforge.model.vo.CourseVO;
import com.cetide.codeforge.service.CourseCategoryService;
import com.cetide.codeforge.service.CourseService;
import com.cetide.codeforge.service.UserCourseService;
import com.cetide.codeforge.util.bean.BeanUtils;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static com.cetide.codeforge.model.enums.CourseProgress.NOT_STARTED;

/**
 * 课程模块
 */
@Api(tags = "课程模块")
@RestController
@RequestMapping("/api/courses")
public class CourseController {


    private final CourseService courseService;

    private final CourseCategoryService courseCategoryService;

    private final UserCourseService userCourseService;

    public CourseController(CourseService courseService, CourseCategoryService courseCategoryService, UserCourseService userCourseService) {
        this.courseService = courseService;
        this.courseCategoryService = courseCategoryService;
        this.userCourseService = userCourseService;
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
        List<CourseVO> courseByCategoryId = courseService.getCourseByCategoryId(id);
        User currentUser = AuthContext.getCurrentUser();
        if (currentUser != null){
            courseByCategoryId.forEach(courseVO -> {
                UserCourse byUserId = userCourseService.getByUserId(courseVO.getId());
                if (byUserId != null){
                    courseVO.setStudy(true);
                }
            });
        }
        return ApiResponse.success(courseByCategoryId);
    }

    @PostMapping("/add/course/{courseId}")
    public ApiResponse<UserCourse> addCourse(@PathVariable String courseId) {
        User currentUser = AuthContext.getCurrentUser();
        UserCourse userCourse = new UserCourse();
        userCourse.setCourseId(Long.valueOf(courseId));
        userCourse.setUserId(currentUser.getId());
        userCourse.setEnrollDate(new Date());
        userCourse.setProgress(NOT_STARTED.getValue());
        boolean save = userCourseService.save(userCourse);
        if (save){
            return ApiResponse.success(userCourse);
        }else{
            return ApiResponse.error(500,"报名课程失败");
        }
    }
} 