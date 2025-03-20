package com.cetide.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cetide.blog.model.dto.req.ShortLinkGroupSortRequest;
import com.cetide.blog.model.dto.req.ShortLinkGroupUpdateRequest;
import com.cetide.blog.model.entity.ShortLinkGroup;
import com.cetide.blog.model.vo.ShortLinkGroupVO;

import java.util.List;

public interface ShortLinkGroupService extends IService<ShortLinkGroup> {

    /**
     * 新增短链接分组
     */
    void saveGroup(String groupName);

    /**
     * 获取短链接分组列表
     */
    List<ShortLinkGroupVO> listGroup();

    /**
     * 更新短链接分组
     */
    void updateGroup(ShortLinkGroupUpdateRequest shortLinkGroupUpdateRequest);

    /**
     * 删除短链接分组
     */
    void deleteGroup(String gid);

    /**
     * 短链接分组排序
     */
    void sortGroup(List<ShortLinkGroupSortRequest> requestList);
}
