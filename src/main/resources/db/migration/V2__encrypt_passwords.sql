-- 新增password_hash列
ALTER TABLE user ADD COLUMN password_hash VARCHAR(255);
-- 回滚脚本
-- ALTER TABLE user DROP COLUMN password_hash; 