package com.cetide.codeforge.task.circulate;

import com.cetide.codeforge.mapper.ArticleActivityRecordMapper;
import com.cetide.codeforge.model.entity.ArticleActivityRecord;
import com.cetide.codeforge.model.entity.user.User;
import com.cetide.codeforge.service.ArticleService;
import com.cetide.codeforge.service.UserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

import static com.cetide.codeforge.common.constants.ArticleConstants.VIEW_COUNT_PREFIX;

@Component
public class ArticleSync {

    /**
     * 文章服务
     */
    @Resource
    private ArticleService articleService;

    /**
     * redis
     */
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 用户服务
     */
    @Resource
    private UserService userService;

    /**
     * 文章活动服务
     */
    @Resource
    private ArticleActivityRecordMapper articleActivityRecordMapper;

    @Scheduled(cron = "0 */10 * * * ?")
    public void syncViewCountToDatabase() {
        // 假设你有一个获取所有文章ID的逻辑
        List<Long> articleIds = articleService.getAllArticleIds();

        for (Long articleId : articleIds) {
            String key = VIEW_COUNT_PREFIX + articleId;
            Integer count = (Integer) redisTemplate.opsForValue().get(key);

            if (count != null) {
                // 更新数据库中的阅读量
                articleService.updateViewCount(articleId, count);
                // 清空 Redis 中的阅读量
                redisTemplate.delete(key);
            }
        }
    }

    @Scheduled(fixedRate = 5 * 60 * 1000) // 每五分钟执行一次
    public void syncData() {
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();
        int day = currentDate.getDayOfMonth();
        String dateKey = currentDate.toString();
        List<User> list = userService.list();
        for (User user : list) {
            Long userId = user.getId();
            Integer count = (Integer) redisTemplate.opsForValue().get(dateKey + "_" + userId);
            if (count != null) {
                ArticleActivityRecord articleActivityRecord = new ArticleActivityRecord();
                articleActivityRecord.setComposeCount(count);
                articleActivityRecord.setDay(day);
                articleActivityRecord.setMonth(month);
                articleActivityRecord.setYear(year);
                articleActivityRecord.setDeleted(0);
                articleActivityRecordMapper.insert(articleActivityRecord);
            }
        }
    }
}
