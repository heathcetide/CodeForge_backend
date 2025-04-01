package com.cetide.codeforge.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cetide.codeforge.common.auth.AuthContext;
import com.cetide.codeforge.exception.ResourceNotFoundException;
import com.cetide.codeforge.model.dto.ArticleCreateRequest;
import com.cetide.codeforge.model.dto.ArticleWithWeight;
import com.cetide.codeforge.model.entity.Article;
import com.cetide.codeforge.model.entity.ArticleActivityRecord;
import com.cetide.codeforge.model.entity.ArticleLike;
import com.cetide.codeforge.model.entity.user.User;
import com.cetide.codeforge.service.ArticleActivityRecordService;
import com.cetide.codeforge.service.ArticleService;
import com.cetide.codeforge.service.UserService;
import com.cetide.codeforge.service.impl.ArticleLikeServiceImpl;
import com.cetide.codeforge.common.ApiResponse;
import com.google.protobuf.ServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.cetide.codeforge.common.constants.ArticleConstants.ARTICLE_COMMENT_LIST;
import static com.cetide.codeforge.common.constants.ArticleConstants.VIEW_COUNT_PREFIX;
import static com.cetide.codeforge.util.algorithm.AlgorithmUtils.*;

/**
 * 文章模块
 */
@Api(tags = "文章管理")
@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;

    private final RedisTemplate<String, Object> redisTemplate;

    private final ArticleActivityRecordService articleActivityRecordService;

    private final UserService userService;

    private final ArticleLikeServiceImpl likeService;

    public ArticleController(ArticleService articleService, RedisTemplate<String, Object> redisTemplate, ArticleActivityRecordService articleActivityRecordService, UserService userService, ArticleLikeServiceImpl likeService) {
        this.articleService = articleService;
        this.redisTemplate = redisTemplate;
        this.articleActivityRecordService = articleActivityRecordService;
        this.userService = userService;
        this.likeService = likeService;
    }

    /**
     * 文章点赞功能
     */
    @PutMapping("/{articleId}")
    @ApiOperation("点赞")
    public ApiResponse<String> like(@PathVariable("articleId") Long articleId) throws ServiceException {
        likeService.like(articleId);
        return ApiResponse.success("点赞成功");
    }

    /**
     * 文章取消点赞功能
     */
    @PutMapping("/cancel/{articleId}")
    @ApiOperation("取消点赞")
    public ApiResponse<String> disLike(@PathVariable("articleId") Long articleId){
        likeService.disLike(articleId);
        return ApiResponse.success("取消点赞");
    }


    /**
     * 根据用户id查询数据
     * @return 分页用户数据
     */
    @GetMapping("/author")
    @ApiOperation("获取用户文章分页列表")
    public ApiResponse<Page<Article>> getAllArticlesByUserId(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "keyword", required = false) String keyword
    ) {
        User currentUser = AuthContext.getCurrentUser();
        Page<Article> articlesPage = articleService.getArticlesByUserId(new Page<>(page, size), keyword, currentUser.getId());
        return ApiResponse.success(articlesPage);
    }

    /**
     * 分页查询用户
     *
     * @param page    页码，从1开始，默认值为1
     * @param size    每页记录数，默认值为10
     * @param keyword 关键字，可选，用于模糊搜索用户信息
     * @return 分页用户数据
     */
    @GetMapping("/get/page")
    @Operation(summary = "获取文章分页列表")
    public ApiResponse<Page<Article>> getAllUsers(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "keyword", required = false) String keyword) {
        Page<Article> userPage = articleService.getArticles(new Page<>(page, size), keyword);
        return ApiResponse.success(userPage);
    }

    /**
     * 上传文章封面图片
     * @param file 文件
     * @return 文件url
     */
    @ApiOperation("上传文章封面图片")
    @PostMapping("/image")
    public ApiResponse<String> uploadImage(@RequestPart MultipartFile file) throws IOException {
        String imageUrl = articleService.uploadArticleImage(file);
        return imageUrl != null ? ApiResponse.success(imageUrl) : ApiResponse.error(500, "上传失败");
    }

    /**
     * 创建文章
     * @param request 文章创建请求
     * @return 文章
     */
    @ApiOperation("创建文章")
    @PostMapping
    public ApiResponse<String> createArticle(@RequestBody ArticleCreateRequest request) {
        try {
            User user = AuthContext.getCurrentUser();
            String id  = articleService.createArticle(request, user);
            LocalDate currentDate = LocalDate.now();
            int year = currentDate.getYear();
            int month = currentDate.getMonthValue();
            int day = currentDate.getDayOfMonth();
            QueryWrapper<ArticleActivityRecord> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("year", year).eq("month", month).eq("user_id", user.getId()).eq("day",day);
            ArticleActivityRecord one = articleActivityRecordService.getOne(queryWrapper);
            if (one == null){
                one = new ArticleActivityRecord();
                one.setYear(year);
                one.setMonth(month);
                one.setDay(day);
                one.setUserId(user.getId());
                one.setComposeCount(1);
                one.setCreatedAt(new Date());
                one.setUpdatedAt(new Date());
                one.setDeleted(0);
                articleActivityRecordService.save(one);
            }
            one.setComposeCount(one.getComposeCount() + 1);
            articleActivityRecordService.updateById(one);
            return ApiResponse.success(id);
        } catch (Exception e) {
            return ApiResponse.error(500, "文章创建失败: " + e.getMessage());
        }
    }

    /**
     * 根据文章id获取文章
     * @param id 文章id
     */
    @ApiOperation("根据ID获取文章")
    @GetMapping("/views/{id}")
    public ApiResponse<Article> getArticleById(@Parameter(description = "文章ID") @PathVariable Long id) {
        // 获取文章
        Article article = articleService.getById(id);
        if (article == null){
            throw new ResourceNotFoundException("暂无此文章");
        }
        // 使用 Redis 的 INCR 命令来增加文章的阅读量
        redisTemplate.opsForValue().increment(VIEW_COUNT_PREFIX + id, 1);
        User currentUser = AuthContext.getCurrentUser();
        if (currentUser != null){
            LambdaQueryWrapper<ArticleLike> likeQueryWrapper = new LambdaQueryWrapper<>();
            likeQueryWrapper.eq(ArticleLike::getUserId, currentUser.getId());
            likeQueryWrapper.eq(ArticleLike::getArticleId, id);
            ArticleLike one = likeService.getOne(likeQueryWrapper);
            article.setLiked(one != null);
        } else {
            article.setLiked(false);
        }
        return ApiResponse.success(article);
    }

    /**
     * 更新文章内容
     */
    @ApiOperation("更新文章")
    @PutMapping("/views/{id}")
    public ApiResponse<Article> updateArticle(
            @Parameter(description = "文章ID") @PathVariable Long id,
            @Parameter(description = "文章数据", required = true) @RequestBody Article article) {
        article.setId(id);
        boolean updated = articleService.updateById(article);
        return updated ? ApiResponse.success(article) : ApiResponse.error(400, "更新失败");
    }

    /**
     * 删除文章
     */
    @ApiOperation("软删除文章")
    @DeleteMapping("/views/{id}/soft")
    public ApiResponse<Boolean> softDeleteArticle(@Parameter(description = "文章ID") @PathVariable Long id) {
        boolean isSoftDeleted = articleService.softDelete(id);
        return isSoftDeleted ? ApiResponse.success(true) : ApiResponse.error(400, "软删除失败，文章未找到");
    }

    /**
     * 根据分类获取文章
     */
    @ApiOperation("根据分类获取文章")
    @GetMapping("/category/{category}")
    public ApiResponse<List<Article>> getByCategory(@PathVariable String category) {
        List<Article> articles = articleService.lambdaQuery()
                .eq(Article::getCategory, category)
                .list();
        return ApiResponse.success(articles);
    }

    /**
     * 根据推荐数量推荐相关文章
     */
    @ApiOperation("根据推荐数量推荐相关文章")
    @GetMapping("/recommend")
    public ApiResponse<List<Article>> recommendArticles(
            @Parameter(description = "推荐数量") @RequestParam(defaultValue = "8") Integer num) {
        if (num > 20){
            throw new IllegalStateException("接口参数错误");
        }
        List<Article> articles = (List<Article>) redisTemplate.opsForValue().get(ARTICLE_COMMENT_LIST + num);
        if (articles == null){
            LambdaQueryWrapper<Article> query = new LambdaQueryWrapper<>();
            query.last("ORDER BY (like_count + share_count + view_count + comment_count) DESC");
            Page<Article> articlePage = articleService.page(new Page<>(1, num), query);
            articles = articlePage.getRecords();
            for (Article article : articles){
                LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(User::getId, article.getUserId());
                User userById = userService.getOne(queryWrapper);
                if (userById != null){
                    article.setUserAvatar(userById.getAvatar());
                }
            }
            redisTemplate.opsForValue().set(ARTICLE_COMMENT_LIST + num, articles, 10, TimeUnit.MINUTES);
        }
        return ApiResponse.success(articles);
    }



    @ApiOperation("根据目标文章 ID 和推荐数量推荐相关文章")
    @GetMapping("/recommendById")
    public ApiResponse<List<Article>> recommendArticlesById(
            @Parameter(description = "目标文章 ID") @RequestParam Long id,
            @Parameter(description = "推荐数量") @RequestParam(defaultValue = "6") Integer num) {
        // 获取目标文章
        Article targetArticle = articleService.getById(id);
        if (targetArticle == null) {
            return ApiResponse.error(400,"目标文章不存在");
        }

        // 获取所有文章（根据需要可以做更多筛选）
        LambdaQueryWrapper<Article> query = new LambdaQueryWrapper<>();
        query.ne(Article::getId, id);  // 排除掉目标文章本身
        Page<Article> articlePage = articleService.page(new Page<>(1, 100));  // 获取前100篇文章
        List<Article> articles = articlePage.getRecords();

        // 为每篇文章计算相似度权重
        List<ArticleWithWeight> articlesWithWeights = calculateArticleWeightsWithTarget(articles, targetArticle);

        // 根据权重进行加权随机选择
        List<Article> finalRecommendations = selectWeightedRandomArticles(articlesWithWeights, num);
        return ApiResponse.success(finalRecommendations);
    }

    /**
     * 获取指定用户本周每天的文章阅读量
     * @return 每天阅读量的列表，索引0代表周一，依次到索引6代表周日
     */
    @GetMapping("/readTrend")
    public ApiResponse<List<Long>> getUserReadTrend() {
        User currentUser = AuthContext.getCurrentUser();
        // 计算当前周的起始和结束日期（这里以周一为本周开始）
        LocalDate today = LocalDate.now();
        LocalDate monday = today.with(DayOfWeek.MONDAY);
        LocalDate sunday = monday.plusDays(6);
        LocalDateTime startOfWeek = monday.atStartOfDay();
        LocalDateTime endOfWeek = sunday.atTime(LocalTime.MAX);

        // 查询该用户本周已发布的文章（确保 published_at 不为空）
        LambdaQueryWrapper<Article> query = new LambdaQueryWrapper<>();
        query.eq(Article::getUserId, currentUser.getId())
                .isNotNull(Article::getPublishedAt)
                .ge(Article::getPublishedAt, startOfWeek)
                .le(Article::getPublishedAt, endOfWeek);

        List<Article> articles = articleService.list(query);
        List<Long> weeklyViews = new ArrayList<>(Arrays.asList(0L, 0L, 0L, 0L, 0L, 0L, 0L));

        // 遍历文章，根据 published_at 的日期对应到具体的星期，将 view_count 累加
        for (Article article : articles) {
            LocalDate publishedDate = article.getPublishedAt().toLocalDate();
            // Java 中 DayOfWeek 的 Monday 值为 1，故减1作为索引
            int index = publishedDate.getDayOfWeek().getValue() - 1;
            weeklyViews.set(index, weeklyViews.get(index) + article.getViewCount());
        }
        return ApiResponse.success(weeklyViews);
    }

    /**
     * 获取上周总的阅读量
     * @return 上周所有已发布文章的总阅读量
     */
    @GetMapping("/previousReadTotal")
    public ApiResponse<Long> getPreviousWeekReadTotal() {
        User currentUser = AuthContext.getCurrentUser();
        // 当前日期
        LocalDate today = LocalDate.now();
        // 获取本周周一日期
        LocalDate currentMonday = today.with(DayOfWeek.MONDAY);
        // 上周的周一和周日（上周结束于本周周一的前一天）
        LocalDate previousMonday = currentMonday.minusWeeks(1);
        LocalDate previousSunday = currentMonday.minusDays(1);
        // 构造上周的起始和结束时间
        LocalDateTime startOfPreviousWeek = previousMonday.atStartOfDay();
        LocalDateTime endOfPreviousWeek = previousSunday.atTime(LocalTime.MAX);

        // 查询该用户上周已发布的文章（确保 published_at 不为空）
        LambdaQueryWrapper<Article> query = new LambdaQueryWrapper<>();
        query.eq(Article::getUserId, currentUser.getId())
                .isNotNull(Article::getPublishedAt)
                .ge(Article::getPublishedAt, startOfPreviousWeek)
                .le(Article::getPublishedAt, endOfPreviousWeek);

        List<Article> articles = articleService.list(query);
        // 计算上周所有文章的总阅读量
        Long totalReadCount = articles.stream().mapToLong(Article::getViewCount).sum();

        return ApiResponse.success(totalReadCount);
    }

    @ApiOperation("根据文章的标题、内容、标签、作者、slug 和 excerpt 进行模糊搜索，同时排除草稿或已删除的文章")
    @GetMapping("/full-search")
    public ApiResponse<Page<Article>> fullSearchArticles(
            @Parameter(description = "搜索关键词") @RequestParam String keyword,
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer page,
            @Parameter(description = "每页数量") @RequestParam(defaultValue = "10") Integer size) {
        LambdaQueryWrapper<Article> query = new LambdaQueryWrapper<>();
        // 只搜索发布状态且未删除的文章
        query.eq(Article::getIsDraft, 0)
                .eq(Article::getDeleted, 0)
                .and(wrapper ->
                        wrapper.like(Article::getTitle, keyword)
                                .or().like(Article::getContent, keyword)
                                .or().like(Article::getTags, keyword)
                                .or().like(Article::getExcerpt, keyword)
                );
        Page<Article> articlePage = articleService.page(new Page<>(page, size), query);
        return ApiResponse.success(articlePage);
    }
}
