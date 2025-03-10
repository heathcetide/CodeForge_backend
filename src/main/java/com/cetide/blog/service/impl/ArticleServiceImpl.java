package com.cetide.blog.service.impl;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cetide.blog.mapper.ArticleMapper;
import com.cetide.blog.model.dto.ArticleCreateRequest;
import com.cetide.blog.model.dto.ArticleSearchParam;
import com.cetide.blog.model.entity.Article;
import com.cetide.blog.model.entity.user.User;
import com.cetide.blog.service.ArticleService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.cetide.blog.util.CosUtils.uploadFile;

@Service
@Transactional
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Value("${file.upload-dir}")
    private String uploadDir; // 文件存储目录


    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public String createArticle(ArticleCreateRequest request, User author) {
        Article article = new Article();
        article.setTitle(request.getTitle());
        article.setContent(request.getContent());
        article.setUserId(author.getId());
        article.setTags(request.getTags());
        article.setPublishedAt(LocalDateTime.now());
        article.setCategory(request.getCategory());
        article.setImageUrl(request.getImageUrl());
        article.setIsDraft(request.getIsDraft());
        article.setExcerpt(request.getExcerpt());
        article.setDeleted(0);
        article.setViewCount(0L);
        article.setCommentCount(0L);
        article.setCreatedAt(new Date());
        article.setUpdatedAt(new Date());
        article.setAuthorName(author.getUsername());
        // 生成 slug，通常是标题的 URL 友好的版本
        String slug = generateSlug(request.getTitle());
        article.setSlug(slug);
        int insert = articleMapper.insert(article);
        if (insert == 1) {
            return String.valueOf(article.getId());
        }
        return null;
    }

    private String generateSlug(String title) {
        // 将标题转换为小写，并替换掉空格和特殊字符
        String normalizedTitle = Normalizer.normalize(title, Normalizer.Form.NFD);
        return normalizedTitle.replaceAll("[^a-zA-Z0-9\\s]", "").replaceAll("\\s+", "-").toLowerCase();
    }

    @Override
    public Page<Article> getPublishedArticles(int page, int size) {
        Page<Article> pageParam = new Page<>(page, size);
        pageParam.addOrder(OrderItem.desc("created_at"));
        return articleMapper.findLatestArticles(pageParam);
    }

    @Cacheable(value = "articles", key = "#id", unless = "#result == null")
    public Article getById(Long id) {
        return baseMapper.selectById(id);
    }

    @Caching(evict = {
        @CacheEvict(value = "articles", key = "#article.id"),
        @CacheEvict(value = "articleList", allEntries = true)
    })

    @Override
    public List<Article> getTopViewedArticles(int limit) {
        return lambdaQuery()
            .orderByDesc(Article::getViewCount)
            .last("LIMIT " + limit)
            .list();
    }

    @Override
    public boolean softDelete(Long id) {
        return lambdaUpdate()
                .set(Article::getDeleted, true)
                .eq(Article::getId, id)
                .update();
    }

    @Cacheable(value = "articles", key = "#param.hashCode()")
    @Override
    public Page<Article> advancedSearch(ArticleSearchParam param, Page<Article> page) {
        LambdaQueryWrapper<Article> query = new LambdaQueryWrapper<>();
        
        if (StringUtils.hasText(param.getKeyword())) {
            query.and(wq -> wq
                .like(Article::getTitle, param.getKeyword())
                .or()
                .like(Article::getContent, param.getKeyword())
            );
        }
        return baseMapper.selectPage(page, query);
    }

    @CacheEvict(value = "articles", allEntries = true)
    @Override
    public boolean updateById(Article entity) {
        return super.updateById(entity);
    }

    // 上传文章封面图片
    /**
     * 上传文章封面图片
     * @param file 文件
     * @return 图片URL
     */
    public String uploadArticleImage(MultipartFile file) throws IOException {
        // 检查上传的文件是否为空
        if (file.isEmpty()) {
            throw new IllegalArgumentException("文件为空");
        }

        // 校验文件类型，可以根据需要添加文件类型限制
        String originalFilename = file.getOriginalFilename();
        String extension = FilenameUtils.getExtension(originalFilename).toLowerCase();
        if (!isValidFileExtension(extension)) {
            throw new IllegalArgumentException("不支持的文件类型");
        }

        // 生成唯一的文件名
        String fileName = UUID.randomUUID() + "." + extension;
        return uploadFile(file, fileName);
//        // 保存文件路径
//        Path targetLocation = Paths.get(uploadDir + File.separator + fileName);
//
//        // 创建存储目录（如果不存在）
//        try {
//            Files.createDirectories(targetLocation.getParent());
//            file.transferTo(targetLocation.toFile()); // 将文件保存到本地
//        } catch (IOException e) {
//            throw new RuntimeException("文件上传失败", e);
//        }
//
//        // 返回文件的相对路径
//        return "/uploads/" + fileName;
    }

    // 检查文件扩展名是否符合要求
    private boolean isValidFileExtension(String extension) {
        // 允许的文件类型（例如图片类型）
        return extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png") || extension.equals("gif");
    }

    @Override
    public Page<Article> getArticles(Page<Article> objectPage, String keyword) {
        Page<Article> pageParam = new Page<>(objectPage.getCurrent(), objectPage.getSize());
        if (keyword != null){
            return articleMapper.selectArticles(pageParam, keyword);
        }else{
            return articleMapper.selectArticle(pageParam);
        }
    }

    @Override
    public Page<Article> getArticlesByUserId(Page<Article> objectPage, String keyword, Long id) {
        return articleMapper.selectArticlesByUserId(objectPage, keyword, id);
    }

    @Override
    public long getTotalCount() {
        return articleMapper.selectArticleCount();
    }

    @Override
    public List<Article> selectByPage(int offset, int i) {
        return articleMapper.selectArticleByPage(offset, i);
    }

    @Override
    public void updateViewCount(Long articleId, Integer count) {
        articleMapper.updateViewCount(articleId, count);
    }

    @Override
    public List<Long> getAllArticleIds() {
        return articleMapper.selectList(null).stream()
                .map(Article::getId)
                .collect(Collectors.toList());
    }
} 