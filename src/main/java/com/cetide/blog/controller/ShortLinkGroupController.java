package com.cetide.blog.controller;

import com.cetide.blog.model.dto.ShortLinkGroupDTO;
import com.cetide.blog.model.dto.req.ShortLinkGroupSaveRequest;
import com.cetide.blog.model.dto.req.ShortLinkGroupSortRequest;
import com.cetide.blog.model.dto.req.ShortLinkGroupUpdateRequest;
import com.cetide.blog.model.entity.ShortLinkGroup;
import com.cetide.blog.model.entity.user.User;
import com.cetide.blog.model.vo.ShortLinkGroupVO;
import com.cetide.blog.service.ShortLinkGroupService;
import com.cetide.blog.util.ApiResponse;
import com.cetide.blog.util.AuthContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/short-link-group")
public class ShortLinkGroupController {

    @Resource
    private ShortLinkGroupService shortLinkGroupService;

    /**
     * 新增短链接分组功能
     */
    @PostMapping
    public ApiResponse<String> create(@RequestBody ShortLinkGroupSaveRequest shortLinkGroupSaveRequest) {
        shortLinkGroupService.saveGroup(shortLinkGroupSaveRequest.getName());
        return ApiResponse.success("创建成功");
    }

    /**
     * 获取用户短链接分组链表
     */
    @GetMapping("/")
    public ApiResponse<List<ShortLinkGroupVO>> listLinkGroup() {
        return ApiResponse.success(shortLinkGroupService.listGroup());
    }

    /**
     * 更新短链接分组信息
     */
    @PutMapping("/")
    public ApiResponse<String> updateGroup(@RequestBody ShortLinkGroupUpdateRequest shortLinkGroupUpdateRequest) {
        shortLinkGroupService.updateGroup(shortLinkGroupUpdateRequest);
        return ApiResponse.success("更新成功");
    }

    /**
     * 更新短链接分组信息
     */
    @PutMapping("/sort")
    public ApiResponse<String> sortGroup(@RequestBody List<ShortLinkGroupSortRequest> requestList) {
        shortLinkGroupService.sortGroup(requestList);
        return ApiResponse.success("更新成功");
    }

    /**
     * 删除短链接分组信息
     */
    @DeleteMapping("/")
    public ApiResponse<String> delete(@RequestParam String gid) {
        shortLinkGroupService.deleteGroup(gid);
        return ApiResponse.success("删除成功");
    }

    /**
     * 阿里的TTL，一个安全的ThreadLocal，也就是TransmittableThreadLocal
     */
}
