CREATE DATABASE cetide_db;

USE cetide_db;

CREATE TABLE `users`
(
    `id`                  BIGINT       NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username`            VARCHAR(255) NOT NULL COMMENT '用户名',
    `password`            VARCHAR(255) NOT NULL COMMENT '密码',
    `email`               VARCHAR(255) DEFAULT NULL COMMENT '邮箱',
    `avatar`              VARCHAR(255) DEFAULT NULL COMMENT '头像',
    `role`                VARCHAR(255) DEFAULT NULL COMMENT '角色',
    `permissions`         VARCHAR(255) DEFAULT NULL COMMENT '权限',
    `birth_day`           DATE         DEFAULT NULL COMMENT '生日',
    `gender`              INT          DEFAULT NULL COMMENT '性别（0：未知，1：男，2：女）',
    `enabled`             BOOLEAN      DEFAULT NULL COMMENT '是否启用',
    `address`             VARCHAR(255) DEFAULT NULL COMMENT '地址',
    `phone`               VARCHAR(255) DEFAULT NULL COMMENT '电话号码',
    `theme`               VARCHAR(255) DEFAULT NULL COMMENT '主题',
    `points`              BIGINT       DEFAULT NULL COMMENT '积分',
    `login_count`         BIGINT       DEFAULT NULL COMMENT '登录次数',
    `article_count`       BIGINT       DEFAULT NULL COMMENT '文章数量',
    `tags`                JSON         DEFAULT NULL COMMENT '标签',
    `reading_history`     JSON         DEFAULT NULL COMMENT '阅读历史',
    `followers`           JSON         DEFAULT NULL COMMENT '关注者',
    `password_salt`       VARCHAR(255) DEFAULT 'cetide' COMMENT '密码加盐',
    `password_updated_at` DATETIME     DEFAULT NULL COMMENT '密码更新时间',
    `login_attempts`      INT          DEFAULT NULL COMMENT '登录尝试次数',
    `last_login_time`     DATETIME     DEFAULT NULL COMMENT '上次登录时间',
    `lock_time`           DATETIME     DEFAULT NULL COMMENT '锁定时间',
    `account_non_locked`  BOOLEAN      DEFAULT TRUE COMMENT '账户是否未锁定',
    `deleted`             BOOLEAN      DEFAULT FALSE COMMENT '是否删除',
    `created_at`          DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`          DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户表';


CREATE TABLE `article`
(
    `id`            BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '文章ID，主键，自增',
    `user_id`       BIGINT                                 NOT NULL COMMENT '文章所属用户的ID，不能为空',
    `title`         VARCHAR(255)                           NOT NULL COMMENT '文章标题，不能为空',
    `content`       TEXT         DEFAULT NULL COMMENT '文章内容，允许为空',
    `created_at`    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '文章创建时间，默认为当前时间戳，不能为空',
    `updated_at`    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '文章更新时间，默认为当前时间戳，并在更新时自动更新',
    `published_at`  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP NULL COMMENT '文章发布时间，允许为空，用于设置文章实际发布的时间',
    `view_count`    BIGINT       DEFAULT 0 COMMENT '文章的阅读次数，默认为0',
    `comment_count` BIGINT       DEFAULT 0 COMMENT '文章的评论数，默认为0',
    `category`      VARCHAR(100) DEFAULT NULL COMMENT '文章分类，允许为空',
    `tags`          JSON         DEFAULT NULL COMMENT '文章的标签，使用JSON格式存储，允许为空',
    `deleted`       TINYINT(1)   DEFAULT 0 COMMENT '文章删除状态（软删除标志），默认0表示未删除，1表示已删除',
    `slug`          VARCHAR(255)                           NOT NULL UNIQUE COMMENT '文章的slug（URL友好名称），用于SEO优化，唯一且不能为空',
    `excerpt`       VARCHAR(500) DEFAULT NULL COMMENT '文章的简短摘要，允许为空，用于展示在文章列表中',
    `author_name`   VARCHAR(100) DEFAULT NULL COMMENT '文章作者的姓名，允许为空，主要用于展示文章的作者名',
    `is_featured`   TINYINT(1)   DEFAULT 0 COMMENT '是否是精选文章，0表示不是，1表示是',
    `is_draft`      TINYINT(1)   DEFAULT 1 COMMENT '是否为草稿，0表示已发布，1表示草稿',
    `image_url`     VARCHAR(255) DEFAULT NULL COMMENT '文章的封面图片URL，允许为空',
    `like_count`    BIGINT       DEFAULT 0 COMMENT '文章的点赞数，默认为0',
    `share_count`   BIGINT       DEFAULT 0 COMMENT '文章的分享数，默认为0'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='文章表';


INSERT INTO `article`
(`user_id`, `title`, `content`, `published_at`, `view_count`, `comment_count`, `category`, `tags`, `deleted`, `slug`,
 `excerpt`, `author_name`, `is_featured`, `is_draft`, `image_url`, `like_count`, `share_count`)
VALUES (1, '探索深度学习的未来', '深度学习是人工智能的核心技术之一，近年来取得了飞速的进展……', '2025-02-20 10:00:00',
        1200, 30, '深度学习', '[
    "AI",
    "机器学习",
    "技术"
  ]', 0, 'deep-learning-future', '深度学习的未来发展趋势及其潜力探索。', '张伟', 1, 0,
        'https://example.com/images/dl_future.jpg', 150, 50),
       (2, 'JavaScript异步编程全解析',
        'JavaScript的异步编程是开发者经常遇到的挑战，本篇文章深入探讨了异步编程的各个方面……', '2025-02-21 08:30:00', 800,
        15, '编程', '[
         "JavaScript",
         "异步",
         "前端"
       ]', 0, 'js-async-programming', '深入解析JavaScript异步编程的原理与应用。', '李娜', 0, 0,
        'https://example.com/images/js_async.jpg', 200, 80),
       (3, '云计算：改变世界的技术', '云计算已经成为现代IT架构的基础，能够帮助企业大幅提升灵活性和扩展性……',
        '2025-02-22 14:00:00', 950, 25, '云计算', '[
         "云计算",
         "技术",
         "互联网"
       ]', 0, 'cloud-computing-world', '云计算如何推动全球技术变革及未来发展方向。', '王磊', 1, 0,
        'https://example.com/images/cloud_computing.jpg', 180, 60),
       (4, '如何优化前端性能', '前端性能优化是每个前端开发者必须掌握的技能之一，本文将介绍几个常见的性能优化技巧……',
        '2025-02-18 16:30:00', 1400, 50, '前端开发', '[
         "性能优化",
         "前端",
         "开发"
       ]', 0, 'frontend-performance-optimization', '前端性能优化的几种常见技术与方法。', '陈静', 0, 0,
        'https://example.com/images/frontend_performance.jpg', 250, 90),
       (5, '区块链技术的应用与挑战', '区块链技术近年来备受关注，本文探讨了其在金融、供应链等领域的应用及面临的挑战……',
        '2025-02-19 18:45:00', 1300, 40, '区块链', '[
         "区块链",
         "金融",
         "技术"
       ]', 0, 'blockchain-technology-challenges', '区块链技术的应用场景与未来的挑战分析。', '赵强', 1, 0,
        'https://example.com/images/blockchain_tech.jpg', 220, 70);



CREATE TABLE `followers`
(
    `id`                BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '唯一ID',
    `follower_id`       BIGINT                                                                NOT NULL COMMENT '粉丝ID',
    `followed_user_id`  BIGINT                                                                NOT NULL COMMENT '被关注者ID',
    `created_at`        TIMESTAMP                                   DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '关注时间',
    `first_followed_at` TIMESTAMP                                   DEFAULT CURRENT_TIMESTAMP COMMENT '首次关注时间',
    `status`            ENUM ('following', 'unfollowed', 'pending') DEFAULT 'following'       NOT NULL COMMENT '关注状态',
    `is_verified`       BOOLEAN                                     DEFAULT FALSE COMMENT '是否验证过的关注关系',
    `tags`              JSON                                        DEFAULT NULL COMMENT '关注标签',
    `follow_weight`     INT                                         DEFAULT 0 COMMENT '关注关系的权重',
    `unfollowed_at`     TIMESTAMP                                   DEFAULT CURRENT_TIMESTAMP NULL COMMENT '取消关注时间',
    UNIQUE KEY `uk_follower_followed` (`follower_id`, `followed_user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='关注关系表';


CREATE TABLE `password_history`
(
    `id`            BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '唯一ID，自增长',
    `user_id`       BIGINT                              NOT NULL COMMENT '用户ID',
    `password_hash` VARCHAR(255)                        NOT NULL COMMENT '密码哈希值',
    `created_at`    TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '密码创建时间，默认为当前时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='密码历史表';

CREATE TABLE `activity`
(
    `id`            BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '唯一ID，自增长',
    `user_id`       BIGINT                                                                                 NOT NULL COMMENT '用户ID，不能为空',
    `activity_type` ENUM ('login', 'post', 'comment', 'like', 'share', 'follow') DEFAULT NULL COMMENT '活动类型，限制类型',
    `description`   TEXT                                                         DEFAULT NULL COMMENT '活动描述',
    `created_at`    TIMESTAMP                                                    DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    `updated_at`    TIMESTAMP                                                    DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`       TINYINT(1)                                                   DEFAULT 0 COMMENT '是否删除，0表示未删除，1表示已删除',
    `tags`          JSON                                                         DEFAULT NULL COMMENT '活动标签，存储为JSON格式',
    `ended_at`      TIMESTAMP                                                    DEFAULT CURRENT_TIMESTAMP NULL COMMENT '活动结束时间，允许为空',
    `status`        ENUM ('in_progress', 'completed', 'canceled')                DEFAULT 'in_progress' COMMENT '活动状态'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='活动表';

-- 分类表
CREATE TABLE `category`
(
    `id`                 BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '唯一ID，自增长',
    `name`               VARCHAR(255)                         NOT NULL COMMENT '分类名称',
    `description`        TEXT       DEFAULT NULL COMMENT '分类描述，允许为空',
    `parent_category_id` BIGINT     DEFAULT NULL COMMENT '父分类ID，允许为空，表示子分类',
    `created_at`         TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间，默认为当前时间',
    `updated_at`         TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`            TINYINT(1) DEFAULT 0 COMMENT '是否删除，0表示未删除，1表示已删除'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='分类表';

-- 通知表
CREATE TABLE `notification`
(
    `id`            BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键，通知ID，自增',
    `user_id`       BIGINT                                 NOT NULL COMMENT '接收用户ID，不能为空',
    `type`          INT                                    NOT NULL COMMENT '通知类型，使用整数值表示（假设 NotificationType 使用整数值）',
    `title`         VARCHAR(255)                           NOT NULL COMMENT '通知标题，最大长度255字符',
    `content`       TEXT         DEFAULT NULL COMMENT '通知内容',
    `level`         INT                                    NOT NULL COMMENT '通知级别，使用整数值表示（假设 NotificationLevel 使用整数值）',
    `read`          BOOLEAN      DEFAULT 0 COMMENT '是否已读，默认为 false',
    `business_id`   BIGINT       DEFAULT NULL COMMENT '关联业务ID',
    `business_type` VARCHAR(255) DEFAULT NULL COMMENT '关联业务类型，最大长度255字符',
    `extra_data`    TEXT         DEFAULT NULL COMMENT '额外数据（JSON格式）',
    `send_time`     TIMESTAMP    DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '发送时间，默认当前时间',
    `read_time`     TIMESTAMP    DEFAULT CURRENT_TIMESTAMP NULL COMMENT '读取时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='通知表';

-- 评论表
CREATE TABLE `comment`
(
    `id`         BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '评论ID，自增长',
    `article_id` BIGINT                              NOT NULL COMMENT '文章ID，不能为空',
    `user_id`    BIGINT                              NOT NULL COMMENT '用户ID，不能为空',
    `content`    TEXT      DEFAULT NULL COMMENT '评论内容',
    `parent_id`  BIGINT    DEFAULT NULL COMMENT '父评论ID，允许为空',
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`    TINYINT   DEFAULT 0 COMMENT '是否删除，0表示未删除，1表示已删除',
    CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `comment` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='评论表';

CREATE INDEX `idx_parent_id` ON `comment` (`parent_id`);


INSERT INTO comment (article_id, user_id, content, parent_id, created_at, updated_at, deleted)
VALUES (4, 1, '这篇文章对前端性能优化的建议很有用，特别是图片懒加载的部分，值得借鉴。', NULL, '2025-02-26 08:30:00',
        '2025-02-26 08:30:00', 0),
       (4, 2, '我觉得提升前端性能最重要的还是减少 HTTP 请求，尤其是合并 CSS 和 JS 文件。', NULL, '2025-02-26 08:45:00',
        '2025-02-26 08:45:00', 0),
       (4, 3, '能不能详细解释一下如何优化 React 应用的渲染性能？', NULL, '2025-02-26 09:00:00', '2025-02-26 09:00:00',
        0),
       (4, 4, '虽然文章提到了很多性能优化的策略，但感觉缺少了对 CDN 使用的介绍。', NULL, '2025-02-26 09:30:00',
        '2025-02-26 09:30:00', 0),
       (4, 5, '我觉得文章提到的异步加载和懒加载技巧特别实用，提升了很多页面加载速度。', NULL, '2025-02-26 10:00:00',
        '2025-02-26 10:00:00', 0),
       (4, 6, '前端性能优化不仅是减少请求，还需要考虑缓存策略。希望能补充更多关于缓存的内容。', NULL,
        '2025-02-26 10:15:00', '2025-02-26 10:15:00', 0),
       (4, 7, '关于第 4 部分的多线程和 Web Worker，能否详细说明一下如何实现？', 3, '2025-02-26 10:30:00',
        '2025-02-26 10:30:00', 0),
       (4, 8, '文章提到的“减少重排重绘”很有意义，希望能再举几个实际案例来说明。', 2, '2025-02-26 11:00:00',
        '2025-02-26 11:00:00', 0),
       (4, 9, '这篇文章让我对前端性能优化有了更深入的了解，尤其是对于 Web Vitals 的讲解很清楚。', NULL,
        '2025-02-26 11:30:00', '2025-02-26 11:30:00', 0),
       (4, 10, '能否做一个关于性能优化工具（如 Lighthouse）的实操案例分析？这样对开发者会更有帮助。', NULL,
        '2025-02-26 12:00:00', '2025-02-26 12:00:00', 0);

INSERT INTO comment (article_id, user_id, content, parent_id, created_at, updated_at, deleted)
VALUES (4, 11, '这篇文章的内容很详细，尤其是在讲解网络优化时，很多细节很有帮助。', NULL, '2025-02-26 12:30:00',
        '2025-02-26 12:30:00', 0),
       (4, 12, '优化前端性能不仅仅是减少请求，还要通过图片压缩、懒加载等方式提升页面加载速度。', NULL,
        '2025-02-26 12:45:00', '2025-02-26 12:45:00', 0),
       (4, 13, '能不能在文章中提到一些具体的工具和框架，比如 React 和 Vue 在性能优化方面的支持？', NULL,
        '2025-02-26 13:00:00', '2025-02-26 13:00:00', 0),
       (4, 14, '异步加载与懒加载在提升页面速度方面的效果非常明显，感谢分享这篇文章。', NULL, '2025-02-26 13:15:00',
        '2025-02-26 13:15:00', 0),
       (4, 15, '缓存机制的优化非常重要，可以进一步详细说明一下 Cache API 和 Service Worker 的使用吗？', NULL,
        '2025-02-26 13:30:00', '2025-02-26 13:30:00', 0),
       (4, 16, '对于图片优化，可以尝试使用 WebP 格式，大大减少图片的体积，加载速度更快。', NULL, '2025-02-26 13:45:00',
        '2025-02-26 13:45:00', 0),
       (4, 17, '我觉得可以多说一些关于 JavaScript 性能优化的技巧，比如防抖和节流。', NULL, '2025-02-26 14:00:00',
        '2025-02-26 14:00:00', 0),
       (4, 18, '这篇文章让我更清楚地认识到前端性能优化的多方面问题，期待更多类似的内容！', NULL, '2025-02-26 14:15:00',
        '2025-02-26 14:15:00', 0),
       (4, 19, '关于 Web Worker 的使用，可以考虑加入具体的代码示例，让开发者更容易理解。', NULL, '2025-02-26 14:30:00',
        '2025-02-26 14:30:00', 0),
       (4, 20, '我非常认同文章中提到的“尽量减少重排重绘”的建议，这对于提升页面渲染效率非常重要。', NULL,
        '2025-02-26 14:45:00', '2025-02-26 14:45:00', 0),
       (4, 21, '在前端性能优化的过程中，调试工具如 Lighthouse 确实是一个非常实用的帮助。', NULL, '2025-02-26 15:00:00',
        '2025-02-26 15:00:00', 0),
       (4, 22, '我希望文章能提供一些性能优化的实战案例，帮助开发者更好地理解和运用这些技巧。', NULL,
        '2025-02-26 15:15:00', '2025-02-26 15:15:00', 0),
       (4, 23, '减少 HTTP 请求和使用合适的 CDN，能够显著提高页面加载速度，值得参考。', NULL, '2025-02-26 15:30:00',
        '2025-02-26 15:30:00', 0),
       (4, 24, '我觉得除了优化加载速度，还应该讲解一下如何优化前端的代码结构和组织方式。', NULL, '2025-02-26 15:45:00',
        '2025-02-26 15:45:00', 0),
       (4, 25, '文章非常实用，关于前端性能优化的策略很具体，很容易应用到实际项目中。', NULL, '2025-02-26 16:00:00',
        '2025-02-26 16:00:00', 0);

CREATE TABLE `article_activity_record` (
                                           `id` INT AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
                                           `year` INT NOT NULL COMMENT '年份',
                                           `month` INT NOT NULL COMMENT '月份',
                                           `day` INT NOT NULL COMMENT '日',
                                           `compose_count` INT NOT NULL COMMENT 'compose_count 活跃度等级（0到9之间的整数）',
                                           `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                           `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                           UNIQUE KEY `unique_date` (`year`, `month`, `day`) COMMENT '每日唯一约束'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章活跃度记录表';

CREATE INDEX `idx_year_month_day` ON `article_activity_record` (`year`, `month`, `day`);

-- 创建存储过程用于插入模拟数据
DELIMITER //
CREATE PROCEDURE mock_article_activity_data()
BEGIN
    DECLARE i INT DEFAULT 0;
    DECLARE random_year INT;
    DECLARE random_month INT;
    DECLARE random_day INT;
    DECLARE random_compose_count INT;
    WHILE i < 28
        DO -- 模拟近4个星期的数据，假设每个星期7天，共28条数据
    -- 随机生成年份（2023到2025之间）
            SET random_year = FLOOR(RAND() * (2025 - 2023 + 1) + 2023);
            -- 随机生成月份（1到12之间）
            SET random_month = FLOOR(RAND() * 12 + 1);
            -- 随机生成日（1到31之间，简单处理未考虑大小月及2月特殊情况）
            SET random_day = FLOOR(RAND() * 31 + 1);
            -- 随机生成compose_count（0到9之间的整数）
            SET random_compose_count = FLOOR(RAND() * 10);

            -- 插入数据
            INSERT INTO article_activity_record (year, month, day, compose_count)
            VALUES (random_year, random_month, random_day, random_compose_count);

            SET i = i + 1;
        END WHILE;
END //
DELIMITER ;

-- 调用存储过程插入模拟数据
CALL mock_article_activity_data();