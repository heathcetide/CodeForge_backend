package com.cetide.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cetide.blog.mapper.ShortLinkMapper;
import com.cetide.blog.model.dto.ShortLinkDTO;
import com.cetide.blog.model.entity.ShortLink;
import com.cetide.blog.service.ShortLinkService;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShortLinkServiceImpl extends ServiceImpl<ShortLinkMapper, ShortLink>
        implements ShortLinkService {

    // 用于生成短码的字符集（62 进制：0-9A-Za-z）
    private static final char[] BASE62 =
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    /** 将数字转换为 62 进制短字符串 */
    private static String encodeBase62(long num) {
        if (num == 0) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            int remainder = (int) (num % 62);
            sb.append(BASE62[remainder]);
            num = num / 62;
        }
        return sb.reverse().toString();
    }

    @Transactional
    @Override
    public ShortLink createShortLink(ShortLinkDTO dto) {
        // 利用雪花算法生成全局唯一 ID，保证短码不冲突
        long id = IdWorker.getId();
        // 基于ID生成短码（Base62 编码），一一对应避免冲突
        String shortCode = encodeBase62(id);
        // 规范化域名，确保没有多余斜杠
        String domain = dto.getDomain();
        if (domain != null && domain.endsWith("/")) {
            domain = domain.substring(0, domain.length() - 1);
        }
        String fullShortUrl = domain + "/" + shortCode;
        // 构造 ShortLink 实体对象
        ShortLink shortLink = new ShortLink();
        shortLink.setId(id);
        shortLink.setGid(id);  // 可选：将 gid 设置为同样的值，用于跟踪或分组
        shortLink.setDescription(dto.getDescription());
        shortLink.setDomain(domain);
        shortLink.setShortUrl(shortCode);
        shortLink.setFullShortUrl(fullShortUrl);
        shortLink.setClickNum(0L);
        shortLink.setOriginUrl(dto.getOriginUrl());
        shortLink.setEnabled(dto.getEnabled() != null ? dto.getEnabled() : true);
        shortLink.setValidDateType(dto.getValidDateType());
        shortLink.setValidDate(dto.getValidDate());
        shortLink.setDeleted(false);
        LocalDateTime now = LocalDateTime.now();
        shortLink.setCreatedAt(now);
        shortLink.setUpdatedAt(now);
        // 保存到数据库
        baseMapper.insert(shortLink);
        return shortLink;
    }

    @Transactional
    @Override
    public ShortLink updateShortLink(Long id, ShortLinkDTO dto) {
        ShortLink existing = baseMapper.selectById(id);
        if (existing == null || Boolean.TRUE.equals(existing.getDeleted())) {
            return null;  // 记录不存在或已被删除
        }
        // 更新允许修改的字段
        if (dto.getDescription() != null) {
            existing.setDescription(dto.getDescription());
        }
        if (dto.getOriginUrl() != null) {
            existing.setOriginUrl(dto.getOriginUrl());
        }
        if (dto.getDomain() != null) {
            String newDomain = dto.getDomain();
            if (newDomain.endsWith("/")) {
                newDomain = newDomain.substring(0, newDomain.length() - 1);
            }
            // 如果域名改变，更新 fullShortUrl（短码保持不变）
            existing.setDomain(newDomain);
            String newFullShortUrl = newDomain + "/" + existing.getShortUrl();
            existing.setFullShortUrl(newFullShortUrl);
            // 注意：如有必要，应确保新域名下短码不重复，这里假设不会冲突
        }
        if (dto.getEnabled() != null) {
            existing.setEnabled(dto.getEnabled());
        }
        if (dto.getValidDateType() != null) {
            existing.setValidDateType(dto.getValidDateType());
        }
        if (dto.getValidDate() != null) {
            existing.setValidDate(dto.getValidDate());
        }
        // 更新更新时间
        existing.setUpdatedAt(LocalDateTime.now());
        // 提交更新到数据库
        baseMapper.updateById(existing);
        return existing;
    }

    @Override
    public ShortLink getShortLink(Long id) {
        // 查询单条记录（MyBatis-Plus 会自动排除已逻辑删除的记录）
        return baseMapper.selectById(id);
    }

    @Override
    public boolean deleteShortLink(Long id) {
        // 逻辑删除记录（@TableLogic 自动将 deleted 字段置1）
        int rows = baseMapper.deleteById(id);
        return rows > 0;
    }

    @Override
    public IPage<ShortLink> listShortLinks(int pageNo, int pageSize) {
        // 构建查询条件，排除已删除的记录
        QueryWrapper<ShortLink> query = new QueryWrapper<>();
        query.eq("deleted", 0);
        // 可按创建时间倒序排列，最新的短链接优先
        query.orderByDesc("created_at");
        // 分页查询
        Page<ShortLink> page = new Page<>(pageNo, pageSize);
        return baseMapper.selectPage(page, query);
    }

    @Transactional
    @Override
    public String redirect(String shortCode) {
        // 根据短码查找启用且未删除的短链接记录
        QueryWrapper<ShortLink> query = new QueryWrapper<>();
        query.eq("short_url", shortCode)
                .eq("enabled", true)
                .eq("deleted", 0);
        ShortLink shortLink = baseMapper.selectOne(query);
        if (shortLink == null) {
            return null;
        }
        // 更新点击量：同步更新（在高并发场景下可考虑异步更新或使用Redis计数）
        long currentClicks = shortLink.getClickNum() == null ? 0L : shortLink.getClickNum();
        shortLink.setClickNum(currentClicks + 1);
        baseMapper.updateById(shortLink);
        return shortLink.getOriginUrl();
    }
}
