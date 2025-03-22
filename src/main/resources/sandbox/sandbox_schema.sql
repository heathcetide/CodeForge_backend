-- src/main/resources/schema.sql
-- 初始化示例表：学生表、课程表、教师表

CREATE TABLE IF NOT EXISTS student
(
    id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    age  INT
);

CREATE TABLE IF NOT EXISTS course
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    title       VARCHAR(255) NOT NULL,
    description TEXT
);

CREATE TABLE IF NOT EXISTS teacher
(
    id      BIGINT PRIMARY KEY AUTO_INCREMENT,
    name    VARCHAR(255) NOT NULL,
    subject VARCHAR(255)
);
