package com.cetide.blog.controller;

import com.cetide.blog.model.dto.ShortLinkDTO;
import com.cetide.blog.model.entity.ShortLink;
import com.cetide.blog.model.vo.ShortLinkVO;
import com.cetide.blog.service.ShortLinkService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/short-links")
public class ShortLinkController {

    @Autowired
    private ShortLinkService shortLinkService;

    /** 分页获取短链接列表 */
    @GetMapping
    public Page<ShortLinkVO> listShortLinks(
            @RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        IPage<ShortLink> page = shortLinkService.listShortLinks(pageNo, pageSize);
        // 将实体列表转换为视图对象列表
        List<ShortLinkVO> voList = page.getRecords().stream()
                .map(ShortLinkVO::fromEntity)
                .collect(Collectors.toList());
        // 封装 Page 对象用于返回
        Page<ShortLinkVO> resultPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        resultPage.setRecords(voList);
        return resultPage;
    }

    /** 获取指定ID的短链接详情 */
    @GetMapping("/{id}")
    public ShortLinkVO getShortLink(@PathVariable Long id) {
        ShortLink shortLink = shortLinkService.getShortLink(id);
        if (shortLink == null) {
            // 未找到对应短链接，返回 404
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Short link not found");
        }
        return ShortLinkVO.fromEntity(shortLink);
    }

    /** 创建新的短链接 */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShortLinkVO createShortLink(@RequestBody ShortLinkDTO shortLinkDTO) {
        ShortLink saved = shortLinkService.createShortLink(shortLinkDTO);
        return ShortLinkVO.fromEntity(saved);
    }

    /** 更新现有短链接 */
    @PutMapping("/{id}")
    public ShortLinkVO updateShortLink(@PathVariable Long id, @RequestBody ShortLinkDTO shortLinkDTO) {
        ShortLink updated = shortLinkService.updateShortLink(id, shortLinkDTO);
        if (updated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Short link not found");
        }
        return ShortLinkVO.fromEntity(updated);
    }

    /** 删除短链接（逻辑删除） */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteShortLink(@PathVariable Long id) {
        boolean removed = shortLinkService.deleteShortLink(id);
        if (!removed) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Short link not found");
        }
        // 删除成功返回 204，无内容
    }

    /**
     * 重定向入口：用户访问 /r/{shortCode} 后自动跳转到对应原始链接，
     * 同时更新点击量
     */
    @GetMapping("/r/{shortCode}")
    public void redirect(@PathVariable("shortCode") String shortCode, HttpServletResponse response) throws IOException {
        String originUrl = shortLinkService.redirect(shortCode);
        if (originUrl == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Short link not found");
        }
        // 使用 HTTP 302 重定向到原始链接
        response.sendRedirect(originUrl);
    }
}
