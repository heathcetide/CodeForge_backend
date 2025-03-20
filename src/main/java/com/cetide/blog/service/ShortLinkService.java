package com.cetide.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cetide.blog.model.dto.ShortLinkDTO;
import com.cetide.blog.model.entity.ShortLink;

public interface ShortLinkService extends IService<ShortLink> {
    /** 创建短链接 */
    ShortLink createShortLink(ShortLinkDTO shortLinkDTO);

    /** 更新短链接（根据ID） */
    ShortLink updateShortLink(Long id, ShortLinkDTO shortLinkDTO);

    /** 根据ID获取短链接 */
    ShortLink getShortLink(Long id);

    /** 删除短链接（逻辑删除） */
    boolean deleteShortLink(Long id);

    /** 分页查询短链接列表 */
    IPage<ShortLink> listShortLinks(int pageNo, int pageSize);

    /**
     * 根据短码进行重定向处理，同时更新点击量
     */
    String redirect(String shortCode);
}
