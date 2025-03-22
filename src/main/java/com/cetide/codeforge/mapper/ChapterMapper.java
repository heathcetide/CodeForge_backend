package com.cetide.codeforge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cetide.codeforge.model.entity.Chapter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ChapterMapper extends BaseMapper<Chapter> {

    // 根据章节ID查询其所有子章节
    @Select("SELECT * FROM chapter WHERE parent_id = #{parentId} ORDER BY order_index")
    List<Chapter> selectByParentId(Long parentId);

    // 根据课程ID和父章节ID查询根章节
    @Select("SELECT * FROM chapter WHERE course_id = #{courseId} AND parent_id = 0 ORDER BY order_index LIMIT 1")
    List<Chapter> selectByCourseIdAndParentId(Long courseId, Long parentId);

    @Select("SELECT * FROM chapter " +
            "WHERE course_id = #{courseId} " +
            "AND order_index > (SELECT order_index FROM chapter WHERE id = #{currentChapterId}) " +
            "ORDER BY order_index " +
            "LIMIT 1")
    Chapter selectNextChapterGlobally(@Param("courseId") Long courseId, @Param("currentChapterId") Long currentChapterId);


    @Select("SELECT * FROM chapter WHERE course_id = #{courseId} AND id = #{chapterId}")
    Chapter selectByCourseIdAndChapterId(@Param("courseId") Long courseId, @Param("chapterId") Long chapterId);

    @Select("SELECT * FROM chapter WHERE course_id = #{courseId} ORDER BY order_index")
    List<Chapter> selectByCourseId(Long courseId);
}
