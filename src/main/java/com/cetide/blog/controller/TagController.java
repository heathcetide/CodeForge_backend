package com.cetide.blog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cetide.blog.model.entity.Tag;
import com.cetide.blog.service.TagService;
import com.cetide.blog.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {
    @Autowired
    private TagService tagService;

    // Create new tag
    @PostMapping
    public ApiResponse<Tag> createTag(@RequestBody Tag tag) {
        tagService.save(tag);
        return ApiResponse.success(tag);
    }

    // Get all tags with pagination
    @GetMapping
    public ApiResponse<Page<Tag>> getTagsByPage(@RequestParam(defaultValue = "1") Integer page,
                                                @RequestParam(defaultValue = "10") Integer size) {
        return ApiResponse.success(tagService.page(new Page<>(page, size)));
    }

    // Get a tag by id
    @GetMapping("/{id}")
    public ApiResponse<Tag> getTagById(@PathVariable Long id) {
        Tag tag = tagService.getById(id);
        if (tag == null) {
            return ApiResponse.error(301,"Tag not found");
        }
        return ApiResponse.success(tag);
    }

    // Update tag
    @PutMapping("/{id}")
    public ApiResponse<Tag> updateTag(@PathVariable Long id, @RequestBody Tag tag) {
        tag.setId(id);
        boolean updated = tagService.updateById(tag);
        if (updated) {
            return ApiResponse.success(tag);
        }
        return ApiResponse.error(301,"Failed to update tag");
    }

    // Soft delete tag
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteTag(@PathVariable Long id) {
        boolean deleted = tagService.deleteTag(id);
        if (deleted) {
            return ApiResponse.success("Tag deleted successfully");
        }
        return ApiResponse.error(301,"Failed to delete tag");
    }

    // Search tags by name
    @GetMapping("/search")
    public ApiResponse<List<Tag>> searchTags(@RequestParam String name) {
        List<Tag> tags = tagService.searchTagsByName(name);
        return ApiResponse.success(tags);
    }

    // Permanent deletion of tag
    @DeleteMapping("/{id}/permanent")
    public ApiResponse<String> permanentDeleteTag(@PathVariable Long id) {
        boolean deleted = tagService.permanentDeleteTag(id);
        if (deleted) {
            return ApiResponse.success("Tag permanently deleted");
        }
        return ApiResponse.error(301,"Failed to permanently delete tag");
    }

    // Bulk update tags
    @PutMapping("/bulk")
    public ApiResponse<List<Tag>> bulkUpdateTags(@RequestBody List<Tag> tags) {
        boolean updated = tagService.bulkUpdate(tags);
        if (updated) {
            return ApiResponse.success(tags);
        }
        return ApiResponse.error(301,"Failed to update tags");
    }

    // Count the total number of tags
    @GetMapping("/count")
    public ApiResponse<Long> countTags() {
        long count = tagService.count();
        return ApiResponse.success(count);
    }

    // Batch creation of tags
    @PostMapping("/bulk")
    public ApiResponse<List<Tag>> createTagsInBulk(@RequestBody List<Tag> tags) {
        tagService.saveBatch(tags);
        return ApiResponse.success(tags);
    }

    // Update only tag name
    @PutMapping("/name/{id}")
    public ApiResponse<Tag> updateTagName(@PathVariable Long id, @RequestBody String name) {
        Tag tag = tagService.getById(id);
        if (tag == null) {
            return ApiResponse.error(301, "Tag not found");
        }
        tag.setName(name);
        tagService.updateById(tag);
        return ApiResponse.success(tag);
    }

    // Restore soft deleted tag
    @PutMapping("/{id}/restore")
    public ApiResponse<String> restoreTag(@PathVariable Long id) {
        boolean restored = tagService.restoreSoftDeletedTag(id);
        if (restored) {
            return ApiResponse.success("Tag restored successfully");
        }
        return ApiResponse.error(301, "Failed to restore tag");
    }

    // Get tags by deletion status
    @GetMapping("/deleted")
    public ApiResponse<List<Tag>> getTagsByDeletedStatus(@RequestParam boolean deleted) {
        List<Tag> tags = tagService.getTagsByDeletedStatus(deleted);
        return ApiResponse.success(tags);
    }

    // Check if tag name exists
    @GetMapping("/exists")
    public ApiResponse<Boolean> checkTagNameExists(@RequestParam String name) {
        boolean exists = tagService.checkTagNameExists(name);
        return ApiResponse.success(exists);
    }
} 