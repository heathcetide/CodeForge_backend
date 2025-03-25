-- 课程表
CREATE TABLE course
(
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    title           VARCHAR(100)                                  NOT NULL,
    description     TEXT,
    level           VARCHAR(10)                                   NOT NULL COMMENT 'P2/P3/P4',
    category_id     BIGINT,
    difficulty      ENUM ('BEGINNER', 'INTERMEDIATE', 'ADVANCED') NOT NULL DEFAULT 'BEGINNER',
    cover_image     VARCHAR(255),
    estimated_hours INT COMMENT '预计学习时长（小时）',
    star_rating     DECIMAL(3, 2)                                          DEFAULT 0.00 COMMENT '课程评分（0-5）',
    enroll_count    INT                                                    DEFAULT 0 COMMENT '报名人数',
    is_recommended  TINYINT(1)                                             DEFAULT 0 COMMENT '是否推荐课程',
    keywords        VARCHAR(255) COMMENT '搜索关键词（逗号分隔）',
    status          ENUM ('DRAFT', 'PUBLISHED', 'ARCHIVED')                DEFAULT 'DRAFT' COMMENT '课程状态',
    is_free         TINYINT(1)                                             DEFAULT 0 COMMENT '是否免费'
);

CREATE TABLE chapter
(
    id               BIGINT PRIMARY KEY AUTO_INCREMENT,                                          -- 章节ID
    course_id        BIGINT       NOT NULL,                                                      -- 关联课程ID
    parent_id        BIGINT                            DEFAULT 0 COMMENT '父章节ID，0表示根节点', -- 父章节ID
    title            VARCHAR(100) NOT NULL,                                                      -- 章节标题
    order_index      INT          NOT NULL COMMENT '排序序号',                                   -- 章节排序
    is_leaf          TINYINT(1)   NOT NULL COMMENT '是否是叶子节点（最小节）',                     -- 判断是否为叶子节点（如果是最终小节）
    content_type     ENUM ('VIDEO', 'ARTICLE', 'QUIZ') DEFAULT 'VIDEO' COMMENT '内容类型（视频、文章、测验）',
    duration_minutes INT COMMENT '预计学习时长（分钟）',                                           -- 预计学习时长
    attachment_url   VARCHAR(255) COMMENT '附件资源地址',                                        -- 附件地址（例如课件、文档）
    markdown_content TEXT COMMENT '章节内容，支持Markdown语法'                                    -- 支持Markdown格式的内容，文本+视频
);

-- 作业表
CREATE TABLE assignment
(
    id                 BIGINT PRIMARY KEY AUTO_INCREMENT,
    chapter_id         BIGINT      NOT NULL UNIQUE,
    description        TEXT        NOT NULL,
    template_code      TEXT        NOT NULL,
    test_cases         JSON        NOT NULL COMMENT '测试用例（JSON格式）',
    pass_rule          VARCHAR(50) NOT NULL COMMENT '通过规则（如输出匹配）',
    max_attempts       INT        DEFAULT -1 COMMENT '最大尝试次数（-1表示不限）',
    allow_retry        TINYINT(1) DEFAULT 1 COMMENT '是否允许失败后重试',
    hint               TEXT COMMENT '作业提示',
    start_date         DATETIME COMMENT '作业开放时间',
    due_date           DATETIME COMMENT '作业截止时间',
    reference_answer   TEXT COMMENT '参考答案',
    discussion_enabled TINYINT(1) DEFAULT 1 COMMENT '是否开启讨论区'
);

INSERT INTO chapter (course_id,
                     parent_id,
                     title,
                     order_index,
                     is_leaf,
                     content_type,
                     duration_minutes,
                     attachment_url,
                     markdown_content)
VALUES (1,
        0,
        'MySQL 安装与配置',
        1,
        1,
        'ARTICLE',
        60,
        NULL,
        '# MySQL 安装与配置\n\n在本章节中，我们将详细介绍如何在不同操作系统下安装和配置MySQL数据库。MySQL作为全球最流行的开源关系型数据库之一，其安装和配置过程对于初学者来说是必不可少的基础知识。通过本章的学习，大家可以掌握MySQL的基本安装步骤、配置文件的调整以及常见问题的解决方案。\n\n## 1. Windows下的安装\n\n在Windows平台上安装MySQL通常采用下载安装包的方式。首先，访问MySQL官方网站下载适合Windows系统的MySQL Installer。下载完成后，双击运行安装程序，按照提示进行安装。在安装过程中，可以选择开发者默认安装或者自定义安装。开发者默认安装会自动选择一些常用组件，而自定义安装则允许用户根据实际需求选择要安装的组件。安装完成后，需要进行初始配置，如设置root用户密码、选择默认字符集和调整端口号等。\n\n- 下载MySQL Installer\n- 选择安装类型（开发者默认或自定义）\n- 设置初始配置参数（如root密码、字符集、端口号）\n\n## 2. Linux下的安装\n\n在Linux系统上，安装MySQL通常使用包管理器完成。例如，在Ubuntu系统上，可以通过以下命令安装MySQL：\n\n```bash\nsudo apt-get update\nsudo apt-get install mysql-server\n```\n\n安装过程中，系统会提示设置root用户的密码。安装完成后，可以使用mysql_secure_installation脚本进一步增强安全性，如禁用远程root登录、删除匿名用户等。在CentOS系统上，可以使用yum包管理器安装：\n\n```bash\nsudo yum update\nsudo yum install mysql-server\n```\n\n安装完成后，使用systemctl start mysqld启动服务，并使用mysql_secure_installation配置安全设置。\n\n## 3. 配置MySQL\n\n无论是在Windows还是Linux平台上，安装完成后都需要对MySQL进行基本配置。主要配置文件通常位于：\n- Windows：C:\\ProgramData\\MySQL\\MySQL Server X.Y\\my.ini\n- Linux：/etc/my.cnf 或 /etc/mysql/my.cnf\n\n常见的配置参数包括：\n- **innodb_buffer_pool_size**：设置InnoDB存储引擎的缓存池大小，通常建议设置为物理内存的50%-70%。\n- **max_connections**：设置允许的最大连接数，适当调整可以避免资源不足。\n- **query_cache_size**：查询缓存大小，根据实际查询需求进行配置。\n- **default_storage_engine**：默认存储引擎，一般设置为InnoDB以获得更好的事务支持。\n\n通过调整这些参数，可以显著提升MySQL的性能和稳定性。在配置完成后，重启MySQL服务使配置生效。\n\n## 4. 常见问题及解决方案\n\n在安装和配置过程中，可能会遇到一些常见问题：\n- **服务启动失败**：可能由于端口冲突或配置文件错误导致，建议检查日志文件获取详细信息。\n- **连接超时**：检查网络防火墙设置，确保MySQL默认端口3306已开放。\n- **字符集问题**：安装前需明确选择合适的字符集，避免后续数据存储出现乱码。\n\n通过本章节的学习，你将对MySQL的安装与配置有一个全面的认识，为后续深入学习MySQL打下坚实基础。');


INSERT INTO chapter (course_id,
                     parent_id,
                     title,
                     order_index,
                     is_leaf,
                     content_type,
                     duration_minutes,
                     attachment_url,
                     markdown_content)
VALUES (1, -- course_id, 这里是课程ID，假设为1
        1, -- parent_id, 父章节ID为1，表示它是"MySQL 安装与配置"章节的子章节
        'Windows 系统 MySQL 安装',
        1, -- order_index, 子章节排序序号
        1, -- is_leaf, 设置为1，表示这是叶子节点（最小节）
        'ARTICLE', -- content_type, 文章类型
        60, -- duration_minutes, 预计学习时长（分钟）
        NULL, -- attachment_url, 无附件
        '# Windows 系统 MySQL 安装\n\n在本章节中，我们将详细讲解如何在Windows系统中安装MySQL数据库。Windows操作系统是MySQL支持的平台之一，我们将通过MySQL Installer工具进行安装。\n\n## 1. 下载MySQL Installer\n\n首先，访问[MySQL官网](https://dev.mysql.com/downloads/installer/)，选择合适的安装包。下载完毕后，运行安装程序。\n\n## 2. 启动安装向导\n\n安装程序启动后，选择“Developer Default”安装选项。这样可以确保安装MySQL服务器、MySQL Workbench等常用组件。\n\n## 3. 配置MySQL\n\n在安装过程中，MySQL安装向导会引导你完成必要的配置步骤，包括：\n- 设置MySQL的root用户密码\n- 配置MySQL的端口号\n- 选择字符集（推荐使用utf8mb4字符集）\n\n这些配置完成后，点击“Execute”按钮开始安装。\n\n## 4. 安装完成与测试\n\n安装完成后，安装程序会提示你MySQL安装成功。你可以通过MySQL Workbench连接MySQL数据库，输入root密码，检查是否能够成功登录。\n\n## 5. 常见问题\n\n在安装过程中可能会遇到一些常见问题：\n- **安装程序卡住**：可能是防火墙或杀毒软件阻止了安装程序的某些操作，建议暂时禁用防火墙或杀毒软件再试。\n- **端口冲突**：如果MySQL默认端口3306已被其他应用占用，可以在安装过程中修改端口号。\n\n通过本章节的学习，你已经掌握了如何在Windows操作系统中安装和配置MySQL。');



INSERT INTO chapter (course_id,
                     parent_id,
                     title,
                     order_index,
                     is_leaf,
                     content_type,
                     duration_minutes,
                     attachment_url,
                     markdown_content)
VALUES (1,
        0,
        'MySQL 基本SQL语法与数据操作',
        2,
        1,
        'ARTICLE',
        80,
        NULL,
        '# MySQL 基本SQL语法与数据操作\n\n本章节将介绍MySQL中最常用的SQL语法和数据操作，包括数据的增、删、改、查等基本操作。SQL（结构化查询语言）是与关系型数据库交互的标准语言，掌握SQL语法是进行数据库开发的基础。通过本章学习，你将了解如何编写简单而高效的SQL语句来管理数据库中的数据。\n\n## 1. 数据库与数据表的创建\n\n创建数据库和数据表是数据库管理的第一步。在MySQL中，可以使用\'CREATE DATABASE\'语句创建数据库，然后使用\'CREATE TABLE\'语句创建数据表。例如：\n\n```sql\nCREATE DATABASE my_database;\nUSE my_database;\nCREATE TABLE users (\n    id BIGINT PRIMARY KEY AUTO_INCREMENT,\n    username VARCHAR(50) NOT NULL,\n    email VARCHAR(100) NOT NULL,\n    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP\n);\n```\n\n通过这些语句，你可以创建一个简单的用户表，并为每个用户自动生成一个唯一的ID。\n\n## 2. 数据的增、删、改、查操作\n\n在数据表创建后，常用的操作包括插入数据、更新数据、删除数据和查询数据。下面是一些基本语句示例：\n- **插入数据**：\n```sql\nINSERT INTO users (username, email) VALUES (\'Alice\', \'alice@example.com\');\n```\n- **更新数据**：\n```sql\nUPDATE users SET email = \'newalice@example.com\' WHERE id = 1;\n```\n- **删除数据**：\n```sql\nDELETE FROM users WHERE id = 1;\n```\n- **查询数据**：\n```sql\nSELECT * FROM users;\n```\n\n这些操作构成了日常数据库管理和应用开发的基础。\n\n## 3. 条件查询与排序\n\nSQL中，条件查询和排序是非常重要的。你可以使用`WHERE`子句进行条件过滤，使用`ORDER BY`子句进行排序。例如：\n\n```sql\nSELECT * FROM users WHERE username LIKE \'A%\' ORDER BY created_at DESC;\n```\n\n该语句查询用户名以A开头的所有用户，并按创建时间倒序排列。\n\n## 4. 聚合函数与分组查询\n\n在数据分析中，聚合函数和分组查询十分常用。常见的聚合函数包括`COUNT()`、`SUM()`、`AVG()`、`MAX()`和`MIN()`。例如：\n\n```sql\nSELECT COUNT(*) AS user_count, AVG(id) AS avg_id FROM users;\n```\n\n该语句统计用户总数，并计算ID的平均值。使用`GROUP BY`子句，可以对数据进行分组统计：\n\n```sql\nSELECT username, COUNT(*) AS user_count FROM users GROUP BY username;\n```\n\n## 5. 子查询与联合查询\n\n在复杂查询中，子查询和联合查询可以帮助你实现更复杂的数据筛选。子查询是在一个查询中嵌套另一个查询，而联合查询（`UNION`）可以合并多个查询结果。通过这些高级语法，可以灵活应对各种业务需求。\n\n## 6. 实际案例分析\n\n假设你需要统计每个用户注册后的活跃情况，可以结合多个SQL语句进行数据分析。通过对数据表的联合查询和分组统计，你可以快速得到用户行为的数据报告。这种灵活的数据操作方式，是MySQL在实际应用中非常强大的优势之一。\n\n通过本章节的学习，你将全面掌握MySQL的基本SQL语法和数据操作技术，为后续的数据库开发和应用打下坚实基础。');

-- 学习进度表
CREATE TABLE user_progress
(
    id                 BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id            BIGINT                                           NOT NULL,
    chapter_id         BIGINT                                           NOT NULL,
    status             ENUM ('NOT_STARTED', 'IN_PROGRESS', 'COMPLETED') NOT NULL DEFAULT 'NOT_STARTED',
    completed_at       DATETIME,
    total_time_spent   INT                                                       DEFAULT 0 COMMENT '总学习时长（分钟）',
    last_accessed      DATETIME COMMENT '最后访问时间',
    quiz_score         INT COMMENT '测验分数',
    first_completed_at DATETIME COMMENT '首次完成时间',
    best_score         INT COMMENT '最佳成绩',
    review_count       INT                                                       DEFAULT 0 COMMENT '复习次数',
    learning_path      JSON COMMENT '个性化学习路径配置'
);

CREATE TABLE user_course
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,  -- 中间表的ID
    user_id      BIGINT NOT NULL,                    -- 用户ID
    course_id    BIGINT NOT NULL,                    -- 课程ID
    enroll_date  DATETIME DEFAULT CURRENT_TIMESTAMP, -- 用户报名日期
    progress     ENUM ('NOT_STARTED', 'IN_PROGRESS', 'COMPLETED') DEFAULT 'NOT_STARTED' -- 学习进度
);

-- 新增课程分类表
CREATE TABLE course_category
(
    id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE,
    icon VARCHAR(100)
);

-- 新增考试表
CREATE TABLE exam
(
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    chapter_id          BIGINT                              NOT NULL UNIQUE,
    exam_type           ENUM ('CHAPTER_TEST', 'FINAL_EXAM') NOT NULL,
    pass_score          INT                                 NOT NULL,
    description         TEXT COMMENT '考试说明',
    max_attempts        INT        DEFAULT 1 COMMENT '最大尝试次数',
    available_from      DATETIME COMMENT '考试开放时间',
    available_until     DATETIME COMMENT '考试截止时间',
    question_bank       JSON COMMENT '题库配置',
    shuffle_questions   TINYINT(1) DEFAULT 1 COMMENT '是否乱序题目',
    show_answers        TINYINT(1) DEFAULT 0 COMMENT '是否显示正确答案',
    proctoring_settings JSON COMMENT '监考配置（如屏幕录制）'
);

# 题目表
CREATE TABLE sql_question
(
    id            BIGINT PRIMARY KEY AUTO_INCREMENT,
    exam_id       BIGINT       NOT NULL,
    title         VARCHAR(255) NOT NULL,
    description   TEXT,
    schema_sql    TEXT         NOT NULL COMMENT '建表语句',
    seed_data_sql TEXT         NOT NULL COMMENT '插入数据语句',
    expected_json JSON         NOT NULL COMMENT '预期结果的 JSON',
    answer_sql    TEXT COMMENT '标准 SQL 答案',
    score         INT DEFAULT 5,
    FOREIGN KEY (exam_id) REFERENCES exam (id) ON DELETE CASCADE
);

# 用户答题记录表
CREATE TABLE sql_submission
(
    id            BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id       BIGINT NOT NULL,
    question_id   BIGINT NOT NULL,
    submitted_sql TEXT   NOT NULL,
    result_json   JSON COMMENT '执行结果 JSON（可选保存）',
    is_correct    TINYINT(1) DEFAULT 0,
    created_at    DATETIME   DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (question_id) REFERENCES sql_question (id) ON DELETE CASCADE
);


-- 新增用户笔记表
CREATE TABLE user_note
(
    id             BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id        BIGINT     NOT NULL,
    chapter_id     BIGINT     NOT NULL,
    content        TEXT       NOT NULL,
    is_public      TINYINT(1) NOT NULL                      DEFAULT 0,
    created_at     DATETIME                                 DEFAULT CURRENT_TIMESTAMP,
    updated_at     DATETIME ON UPDATE CURRENT_TIMESTAMP,
    like_count     INT                                      DEFAULT 0 COMMENT '点赞数',
    bookmark_count INT                                      DEFAULT 0 COMMENT '收藏数',
    comment_count  INT                                      DEFAULT 0 COMMENT '评论数',
    tags           VARCHAR(255) COMMENT '笔记标签（逗号分隔）',
    language       VARCHAR(10)                              DEFAULT 'zh' COMMENT '笔记语言',
    version        INT                                      DEFAULT 1 COMMENT '版本号（支持历史版本）',
    audit_status   ENUM ('PENDING', 'APPROVED', 'REJECTED') DEFAULT 'PENDING' COMMENT '审核状态',
    audit_remark   TEXT COMMENT '审核备注'
);

-- 新增积分系统表
CREATE TABLE point_record
(
    id            BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id       BIGINT                                                 NOT NULL,
    type          ENUM ('DAILY_LOGIN', 'COMPLETE_CHAPTER', 'SHARE_NOTE') NOT NULL,
    points        INT                                                    NOT NULL,
    description   VARCHAR(255),
    created_at    DATETIME DEFAULT CURRENT_TIMESTAMP,
    related_id    BIGINT COMMENT '关联ID（如章节ID、考试ID等）',
    expired_at    DATETIME COMMENT '积分过期时间',
    source_detail VARCHAR(255) COMMENT '来源详情',
    INDEX idx_user_points (user_id, created_at)
);

INSERT INTO course (title, description, level, category_id, difficulty, cover_image, estimated_hours, star_rating,
                    enroll_count, is_recommended, keywords, status, is_free)
VALUES ('MySQL 入门教程', '本课程适合零基础学习者，讲解MySQL数据库的安装、配置以及基本操作。', 'P2', 1, 'BEGINNER',
        'https://via.placeholder.com/200', 20, 4.2, 2000, 1, 'MySQL,入门,基础', 'PUBLISHED', 1),
       ('MySQL 基础操作与 SQL 语法', '通过本课程，学习者可以掌握MySQL的基本SQL语法与常用操作。', 'P2', 1, 'BEGINNER',
        'https://via.placeholder.com/200', 25, 4.5, 1500, 1, 'MySQL,SQL,基础', 'PUBLISHED', 1),
       ('MySQL 数据库设计与建模', '本课程详细介绍数据库设计原则和建模技术，帮助你设计高效、规范的数据库。', 'P3', 1,
        'INTERMEDIATE', 'https://via.placeholder.com/200', 30, 4.6, 1000, 1, 'MySQL,数据库设计,建模', 'PUBLISHED', 0),
       ('MySQL 索引与查询优化', '讲解MySQL索引机制与查询优化方法，帮助你提升数据库查询性能。', 'P3', 1, 'INTERMEDIATE',
        'https://via.placeholder.com/200', 35, 4.7, 900, 1, 'MySQL,索引,查询优化', 'PUBLISHED', 0),
       ('MySQL 事务与并发控制', '本课程介绍事务管理和并发控制技术，确保数据一致性与完整性。', 'P3', 1, 'INTERMEDIATE',
        'https://via.placeholder.com/200', 40, 4.5, 800, 0, 'MySQL,事务,并发', 'PUBLISHED', 0),
       ('MySQL 性能优化实战', '深入讲解MySQL性能调优技巧，通过案例分析教你如何优化实际数据库性能。', 'P4', 1, 'ADVANCED',
        'https://via.placeholder.com/200', 45, 4.8, 700, 1, 'MySQL,性能优化,实战', 'PUBLISHED', 0),
       ('MySQL 分库分表与集群架构', '介绍分库分表和集群架构的设计思想，解决大数据量下的性能瓶颈。', 'P4', 1, 'ADVANCED',
        'https://via.placeholder.com/200', 50, 4.9, 600, 1, 'MySQL,分库分表,集群', 'PUBLISHED', 0),
       ('MySQL 高级备份与恢复方案', '讲解多种备份与恢复策略，确保数据库数据安全与高可用。', 'P4', 1, 'ADVANCED',
        'https://via.placeholder.com/200', 40, 4.7, 500, 0, 'MySQL,备份,恢复', 'PUBLISHED', 0),
       ('MySQL 内部原理与源码分析', '深入解析MySQL内部原理和源码，探索数据库设计背后的奥秘。', 'P4', 1, 'ADVANCED',
        'https://via.placeholder.com/200', 60, 5.0, 400, 1, 'MySQL,源码,内部原理', 'PUBLISHED', 0),
       ('MySQL 高级实践：从入门到精通', '综合性课程，从基础知识到高级应用，全方位覆盖MySQL的各个方面。', 'P4', 1,
        'ADVANCED', 'https://via.placeholder.com/200', 70, 5.0, 300, 1, 'MySQL,高级实践,精通', 'PUBLISHED', 0);


CREATE TABLE user_matches (
                              match_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '比赛ID',
                              user_id1 INT NOT NULL COMMENT '用户1 ID',
                              user_id2 INT NOT NULL COMMENT '用户2 ID',
                              match_start_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '比赛开始时间',
                              match_end_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '比赛结束时间',
                              winner_user_id INT COMMENT '胜者用户ID',
                              create_by INT COMMENT '创建人',
                              create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              update_by INT COMMENT '修改人',
                              update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'
) COMMENT '用户匹配PK表';

CREATE TABLE user_answers (
                              answer_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '答案记录ID',
                              user_id INT NOT NULL COMMENT '用户ID',
                              question_id INT NOT NULL COMMENT '题目ID',
                              user_answer TEXT NOT NULL COMMENT '用户答案',
                              is_correct BOOLEAN COMMENT '答案是否正确',
                              answer_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '作答时间',
                              create_by INT COMMENT '创建人',
                              create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              update_by INT COMMENT '修改人',
                              update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'
) COMMENT '用户作答记录表';

CREATE TABLE question_popularity (
                                     popularity_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '热度ID',
                                     question_id INT NOT NULL COMMENT '题目ID',
                                     popularity_score INT DEFAULT 0 COMMENT '热度分数',
                                     last_tested TIMESTAMP COMMENT '最后被测试的时间',
                                     create_by INT COMMENT '创建人',
                                     create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                     update_by INT COMMENT '修改人',
                                     update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'
) COMMENT '题目热度表';

CREATE TABLE interview_questions (
                                     question_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '题目ID',
                                     category_id INT NOT NULL COMMENT '类别ID',
                                     topic_id INT NOT NULL COMMENT '专题ID',
                                     question_text TEXT NOT NULL COMMENT '题目内容',
                                     answer_text TEXT NOT NULL COMMENT '答案内容',
                                     create_by INT COMMENT '创建人',
                                     create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                     update_by INT COMMENT '修改人',
                                     update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'
) COMMENT '面试题库表';

CREATE TABLE interview_question_topics (
                                           topic_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '专题ID',
                                           topic_name VARCHAR(255) NOT NULL COMMENT '专题名称',
                                           create_by INT COMMENT '创建人',
                                           create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                           update_by INT COMMENT '修改人',
                                           update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'
) COMMENT '面试题专题表';

CREATE TABLE interview_question_categories (
                                               category_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '类别ID',
                                               category_name VARCHAR(255) NOT NULL COMMENT '类别名称',
                                               create_by INT COMMENT '创建人',
                                               create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                               update_by INT COMMENT '修改人',
                                               update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'
) COMMENT '面试题分类表';