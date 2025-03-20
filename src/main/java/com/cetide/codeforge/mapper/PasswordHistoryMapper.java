package com.cetide.codeforge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cetide.codeforge.model.entity.user.PasswordHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PasswordHistoryMapper extends BaseMapper<PasswordHistory> {
    @Select("SELECT password_hash FROM password_history WHERE user_id = #{userId} ORDER BY created_at DESC LIMIT #{limit}")
    List<String> selectRecentPasswords(@Param("userId") Long userId, @Param("limit") int limit);
} 