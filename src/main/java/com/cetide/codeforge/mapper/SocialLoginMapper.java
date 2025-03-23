package com.cetide.codeforge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cetide.codeforge.model.entity.user.SocialLogin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SocialLoginMapper extends BaseMapper<SocialLogin> {
}
