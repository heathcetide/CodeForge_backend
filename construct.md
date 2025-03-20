multi-module/
├── domain/                  # 核心领域
│   └── src/main/java/com/example/domain/
│       ├── model/           # 领域对象（贫血/充血模型）
│       ├── service/         # 领域服务（业务逻辑）
│       ├── event/           # 领域事件
│       └── repository/      # 仓储接口（端口定义）
│
├── application/             # 应用服务层
│   └── src/main/java/com/example/application/
│       ├── service/         # 应用服务（用例实现）
│       ├── dto/             # 业务传输对象
│       └── config/          # 应用层配置（验证、缓存等）
│
├── adapter/                 # 适配器层
│   ├── web/                 # Web适配器
│   │   └── src/main/java/com/example/web/
│   │       ├── controller/  # REST接口
│   │       ├── dto/         # API传输对象
│   │       ├── advice/      # 全局异常处理
│   │       └── config/      # Web配置（CORS、Swagger等）
│   │
│   └── persistence/         # 持久化适配器
│       └── src/main/java/com/example/persistence/
│           ├── mapper/      # MyBatis Plus Mapper
│           ├── repository/  # 仓储实现
│           └── config/      # 数据源、事务等配置
│
├── infrastructure/          # 基础设施
│   └── src/main/java/com/example/infrastructure/
│       ├── cache/           # 缓存实现
│       ├── mq/              # 消息队列
│       └── security/        # 安全实现
│
└── start/                   # 启动模块
└── src/main/java/com/example/start/
├── Application.java # 启动类
└── resources/       # 全局配置
├── application.yml
└── mapper/      # SQL映射文件



详细分层说明：
领域层 (Domain Layer)
├── model/
│   ├── User.java          # 领域对象（含业务行为）
│   └── Order.java         # 聚合根
├── service/
│   └── DomainService.java # 领域服务
└── repository/
└── UserRepository.java # 领域仓库接口

2. 应用层 (Application Layer)
   ├── service/
   │   └── AppService.java    # 应用服务（协调领域对象）
   ├── dto/
   │   ├── UserRequest.java   # 入参DTO
   │   └── UserResponse.java  # 出参DTO
   └── port/
   ├── EmailPort.java      # 外部服务端口
   └── PaymentPort.java    # 支付网关端口

适配器层 (Adapter Layer)
web-adapter/
├── controller/
│   └── UserController.java # REST接口
├── dto/
│   ├── UserDTO.java        # API传输对象
│   └── ApiResponse.java    # 统一响应格式
└── config/
├── WebConfig.java      # Web配置
└── SwaggerConfig.java  # API文档

persistence-adapter/
├── mapper/
│   └── UserMapper.java     # MyBatis Plus Mapper
├── repository/
│   └── UserRepositoryImpl.java # 仓库实现
└── config/
├── MyBatisPlusConfig.java # MP配置
└── MultiDataSourceConfig.java # 多数据源

基础设施层 (Infrastructure)
├── cache/
│   └── RedisCache.java     # Redis实现
├── mq/
│   └── RabbitMQService.java # 消息队列
└── security/
└── SecurityConfig.java # 安全配置


启动模块 (Bootstrap)
start/
├── Application.java        # 启动类
└── resources/
├── application.yml     # 主配置
└── logback-spring.xml  # 日志配置

关键数据流向：
HTTP Request
→ Web Adapter (Controller)
→ Application Service
→ Domain Service
→ Persistence Adapter (Repository/Mapper)
→ Database

Domain Event
→ Infrastructure (MQ)
→ External Systems


架构特点：
领域核心：所有业务逻辑集中在domain模块
单向依赖：适配器→应用→领域（禁止反向依赖）
3. 端口适配：通过接口隔离技术实现细节
   技术中立：领域层不依赖任何框架注解
   可测试性：各层可独立进行单元测试