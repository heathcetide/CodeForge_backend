package com.cetide.codeforge.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cetide.codeforge.common.auth.AuthContext;
import com.cetide.codeforge.exception.ResourceNotFoundException;
import com.cetide.codeforge.mapper.UserCourseMapper;
import com.cetide.codeforge.model.entity.UserCourse;
import com.cetide.codeforge.model.entity.user.User;
import com.cetide.codeforge.service.UserCourseService;
import org.springframework.stereotype.Service;

@Service
public class UserCourseServiceImpl extends ServiceImpl<UserCourseMapper, UserCourse> implements UserCourseService {

    @Override
    public UserCourse getByUserId(String courseId) {
        User currentUser = AuthContext.getCurrentUser();
        LambdaQueryWrapper<UserCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserCourse::getCourseId, courseId);
        queryWrapper.eq(UserCourse::getUserId, currentUser.getId());
        return baseMapper.selectOne(queryWrapper);
    }
}
