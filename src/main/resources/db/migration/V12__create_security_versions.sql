CREATE TABLE security_versions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    config_type VARCHAR(50) NOT NULL,
    version_hash VARCHAR(64) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_config_type ON security_versions(config_type); 