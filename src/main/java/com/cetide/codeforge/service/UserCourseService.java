package com.cetide.codeforge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cetide.codeforge.model.entity.UserCourse;

public interface UserCourseService extends IService<UserCourse> {

    /**
     * 根据userId获取是否参与了课程
     */
    UserCourse getByUserId(String courseId);
}
