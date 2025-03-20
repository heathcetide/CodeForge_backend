package com.cetide.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cetide.blog.model.entity.ShortLink;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ShortLinkMapper extends BaseMapper<ShortLink> {
    /** 根据域名和短码查询短链接（用于避免冲突或快速查找） */
    ShortLink selectByShortUrlAndDomain(@Param("domain") String domain,
                                        @Param("shortUrl") String shortUrl);
}
