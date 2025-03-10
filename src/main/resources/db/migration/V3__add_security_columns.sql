ALTER TABLE user 
    ADD COLUMN login_attempts INT DEFAULT 0,
    ADD COLUMN lock_time DATETIME,
    ADD COLUMN account_non_locked BOOLEAN DEFAULT true;

CREATE TABLE password_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id)
); 