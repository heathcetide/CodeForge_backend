package com.cetide.codeforge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cetide.codeforge.model.entity.Tag;

import java.util.List;

public interface TagService extends IService<Tag> {

    List<Tag> searchByName(String keyword);

    Tag findByName(String name);

    boolean deleteTag(Long id);

    List<Tag> searchTagsByName(String name);

    boolean permanentDeleteTag(Long id);

    boolean bulkUpdate(List<Tag> tags);

    long count();

    boolean restoreSoftDeletedTag(Long id);

    List<Tag> getTagsByDeletedStatus(boolean deleted);

    boolean checkTagNameExists(String name);
} 