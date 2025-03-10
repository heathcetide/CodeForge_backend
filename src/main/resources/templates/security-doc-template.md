# 安全配置文档

**最后更新时间**：${timestamp?datetime}

## 认证配置
- JWT有效期：${jwt.expiration} 分钟
- 密码策略：${security.password.policy}

## 网络防护
- CSP策略：${security.headers.csp}
- HSTS配置：${security.headers.hsts}

## 访问控制
- 管理员IP范围：${security.access.adminIps}
- 接口限流规则：${security.ratelimit.rules} 