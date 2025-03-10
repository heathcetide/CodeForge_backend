package com.cetide.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cetide.blog.mapper.TagMapper;
import com.cetide.blog.model.entity.Tag;
import com.cetide.blog.service.TagService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    @Override
    public List<Tag> searchByName(String keyword) {
        return baseMapper.searchByName(keyword);
    }

    @Override
    public Tag findByName(String name) {
        return baseMapper.findByName(name);
    }

    @Override
    public boolean deleteTag(Long id) {
        // Soft delete by updating the "deleted" field
        Tag tag = this.getById(id);
        if (tag != null) {
            tag.setDeleted(1);
            return this.updateById(tag);
        }
        return false;
    }

    @Override
    public List<Tag> searchTagsByName(String name) {
        // Search for tags with names that match the provided name (case-insensitive)
        return this.lambdaQuery().like(Tag::getName, name).list();
    }

    @Override
    public boolean permanentDeleteTag(Long id) {
        // Permanently delete the tag from the database
        return this.removeById(id);
    }

    @Override
    public boolean bulkUpdate(List<Tag> tags) {
        // Bulk update tags
        return this.updateBatchById(tags);
    }

    @Override
    public long count() {
        // Count the total number of tags in the system
        return this.count();
    }

    @Override
    public boolean restoreSoftDeletedTag(Long id) {
        // Restore a soft-deleted tag
        Tag tag = this.getById(id);
        if (tag != null && tag.getDeleted() == 1) {
            tag.setDeleted(0); // Mark as not deleted
            return this.updateById(tag);
        }
        return false;
    }

    @Override
    public List<Tag> getTagsByDeletedStatus(boolean deleted) {
        // Get tags based on their deleted status
        return this.lambdaQuery().eq(Tag::getDeleted, (byte) (deleted ? 1 : 0)).list();
    }

    @Override
    public boolean checkTagNameExists(String name) {
        // Check if a tag with the specified name exists
        return this.lambdaQuery().eq(Tag::getName, name).count() > 0;
    }
} 