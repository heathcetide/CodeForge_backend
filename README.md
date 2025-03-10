# CodeForge

<p align="center">
  <a href="LICENSE">
    <img src="https://img.shields.io/badge/license-MIT-blue.svg" alt="MIT License"/>
  </a>
  <a href="#">
    <img src="https://img.shields.io/badge/Spring%20Boot-2.7.0-brightgreen.svg" alt="Spring Boot"/>
  </a>
  <a href="#">
    <img src="https://img.shields.io/badge/JDK-11-orange.svg" alt="JDK"/>
  </a>
  <a href="#">
    <img src="https://img.shields.io/badge/MySQL-5.7-blue.svg" alt="MySQL"/>
  </a>
  <a href="#">
    <img src="https://img.shields.io/badge/Redis-6.0-red.svg" alt="Redis"/>
  </a>
</p>

> CodeForge 是一个现代化的开发平台，旨在帮助开发者高效构建、部署和管理应用。通过集成用户管理、数据访问、智能推荐及通知系统，CodeForge 助你轻松应对复杂业务场景。

[English](README_EN.md) | 简体中文

### 核心功能

- **用户管理**
  - 用户注册、登录、注销
  - JWT 认证与授权
  - 权限控制与角色管理

- **项目管理**
  - 项目创建与配置
  - 模块化开发与插件扩展

- **数据访问**
  - 数据库操作（MyBatis-Plus）
  - 缓存支持（Redis）
  - 数据导入导出（Excel 模板）

- **智能推荐**
  - 代码智能提示
  - 应用性能优化建议

- **通知系统**
  - 邮件通知
  - 站内消息
  - WebSocket 实时推送

- **测试与部署**
  - 单元测试、集成测试
  - Docker 与 Kubernetes 部署支持

### 技术栈

#### 后端技术
<img src="https://img.shields.io/badge/Spring%20Boot-2.7.0-brightgreen.svg"/>
<img src="https://img.shields.io/badge/Spring%20Security-2.7.0-brightgreen.svg"/>
<img src="https://img.shields.io/badge/MyBatis--Plus-3.5.2-blue.svg"/>
<img src="https://img.shields.io/badge/Redis-6.0-red.svg"/>
<img src="https://img.shields.io/badge/MySQL-5.7-blue.svg"/>
<img src="https://img.shields.io/badge/Maven-3.6.3-blue.svg"/>

#### 前端技术
<img src="https://img.shields.io/badge/Vue-3.x-green.svg"/>
<img src="https://img.shields.io/badge/ElementPlus-2.x-blue.svg"/>
<img src="https://img.shields.io/badge/Webpack-5-blue.svg"/>

#### 项目环境
<img src="https://img.shields.io/badge/JDK-11-orange.svg"/>
<img src="https://img.shields.io/badge/IDE-IntelliJ%20IDEA-blue.svg"/>
<img src="https://img.shields.io/badge/Maven-3.6.3-blue.svg"/>
<img src="https://img.shields.io/badge/Docker-20.10-blue.svg"/>

## 系统架构

### 整体架构
<img src=".README/architecture.png" alt="系统架构图"/>

## 快速开始

1. **安装必要的开发工具**
   ```bash
   sudo apt install openjdk-11-jdk
   # 安装 JDK 11
   
   sudo apt install maven
   # 安装 Maven
   
   curl -fsSL https://get.docker.com | bash -s docker
   # 安装 Docker