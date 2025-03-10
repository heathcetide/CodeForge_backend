CREATE INDEX idx_permission_url_method 
ON permissions(url(200), method);

ALTER TABLE security_versions 
ADD UNIQUE INDEX idx_version_unique (config_type, version_hash); 