package com.cetide.codeforge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cetide.codeforge.model.entity.Tag;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TagMapper extends BaseMapper<Tag> {
    @Select("SELECT * FROM tags WHERE name = #{name} LIMIT 1")
    Tag findByName(String name);
    
    List<Tag> searchByName(String keyword);
} 