package com.cetide.codeforge.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cetide.codeforge.exception.BusinessException;
import com.cetide.codeforge.mapper.CourseCategoryMapper;
import com.cetide.codeforge.mapper.CourseMapper;
import com.cetide.codeforge.model.dto.request.course.category.CourseCategoryDTO;
import com.cetide.codeforge.model.entity.CourseCategory;
import com.cetide.codeforge.model.vo.CourseCategoryVO;
import com.cetide.codeforge.service.CourseCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseCategoryServiceImpl extends ServiceImpl<CourseCategoryMapper, CourseCategory>
        implements CourseCategoryService {

    private final CourseCategoryMapper categoryMapper;
    private final CourseMapper courseMapper;

    public CourseCategoryServiceImpl(CourseCategoryMapper categoryMapper, CourseMapper courseMapper) {
        this.categoryMapper = categoryMapper;
        this.courseMapper = courseMapper;
    }

    @Override
    public CourseCategory createCategory(CourseCategoryDTO dto) {
        // 检查分类名称是否已存在
        if (categoryMapper.selectByName(dto.getName()).isPresent()) {
            throw new BusinessException("分类名称已存在");
        }

        CourseCategory category = new CourseCategory();
        BeanUtils.copyProperties(dto, category);
        categoryMapper.insert(category);
        return category;
    }

    @Override
    public List<CourseCategory> getAllWithPopularCourses(int limit) {
        List<CourseCategory> categories = categoryMapper.selectAllCategories();
        return categories;
    }

    @Override
    public List<CourseCategoryVO> categoryList(String groupName) {
        LambdaQueryWrapper<CourseCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CourseCategory::getGroupName, groupName);
        return categoryMapper.selectList(queryWrapper).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private CourseCategoryVO convertToDTO(CourseCategory courseCategory) {
        CourseCategoryVO dto = new CourseCategoryVO();
        dto.setId(String.valueOf(courseCategory.getId()));
        dto.setIcon("https://cetide-1325039295.cos.ap-chengdu.myqcloud.com/codeforge/course/"+courseCategory.getIcon());
        dto.setName(courseCategory.getName());
        return dto;
    }
} 