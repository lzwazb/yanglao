# 健康管理模块开发计划

## 1. 开发流程

开发将遵循 **后端先行，前端跟进，最后联调** 的顺序。建议新建一个独立的微服务 `health-server` 来专门处理健康相关的数据和逻辑。

1.  **数据库设计**：设计存储健康档案、体征数据（血压、心率等）等信息的数据表。
2.  **后端开发 (`health-server`)**：
    *   创建 Spring Boot 模块并配置 Nacos。
    *   创建实体类 (Entity)。
    *   开发数据访问层 (Mapper/DAO)。
    *   开发业务逻辑层 (Service)。
    *   开发接口控制层 (Controller)。
3.  **前端开发 (`yanglao-project`)**：
    *   封装 API 请求接口。
    *   开发/完善健康管理页 (用户端)。
    *   开发/完善健康档案管理页 (管理端/家属端)。
    *   联调个人中心“健康数据”展示。
4.  **联调与测试**：验证全流程功能。

## 2. 功能清单

### 用户端 (老人)
*   **健康数据录入**：手动录入每日的血压、血糖、心率、体温等数据。
*   **健康数据查看**：查看历史健康数据的列表。

### 家属端
*   **查看长辈健康**：查看关联老人的健康数据和档案。

### 管理员端 (Web后台)
*   **健康档案管理**：为老人建立和维护电子健康档案。
*   **健康数据监控**：查看所有老人的健康数据。

## 3. 详细开发步骤

### 第一阶段：数据库设计
需要至少两张表：
1.  **`health_profile` (健康档案表)**
    *   `id`: 主键
    *   `user_id`: 关联用户ID
    *   `height`: 身高
    *   `weight`: 体重
    *   `blood_type`: 血型
    *   `create_time`, `update_time`: 时间戳

2.  **`health_data` (健康数据表)**
    *   `id`: 主键
    *   `user_id`: 关联用户ID
    *   `blood_pressure_high`: 收缩压 (高压)
    *   `blood_pressure_low`: 舒张压 (低压)
    *   `heart_rate`: 心率
    *   `blood_sugar`: 血糖
    *   `temperature`: 体温
    *   `measure_time`: 测量时间
    *   `create_time`: 记录时间

### 第二阶段：后端开发 (`health-server`)
1.  **模块初始化**：
    *   新建 `health-server` 模块。
    *   配置 `pom.xml` (Web, Nacos, MyBatis-Plus, MySQL, Druid, Swagger)。
    *   配置 `application.yml` (端口建议 5007)。
2.  **Entity**: 在 `com.healthserver.entity` 下创建 `HealthProfile.java` 和 `HealthData.java`。
3.  **Mapper**: 在 `com.healthserver.mapper` 下创建对应的 Mapper 接口。
4.  **Service**:
    *   `HealthProfileService`: 处理档案的增删改查。
    *   `HealthDataService`: 处理健康数据的录入和查询（支持按时间范围查询）。
5.  **Controller**:
    *   `HealthController`: 提供 `/health/profile` (档案相关) 和 `/health/data` (数据相关) 的接口。

### 第三阶段：前端开发 (`yanglao-project`)
1.  **API 封装**: 在 `src/api` 下新建 `health.js`。
2.  **健康管理页 (`views/health/HealthManagement.vue`)**:
    *   **Tab 1: 健康档案**：展示基本信息，提供编辑按钮（仅管理员或本人可编辑）。
    *   **Tab 2: 数据录入**：表单填写血压、心率等，提交到后端。
    *   **Tab 3: 数据趋势**：使用表格展示历史数据。
3.  **个人中心 (`views/profile/Profile.vue`)**:
    *   修改“健康数据”Tab 页，调用后端接口获取真实数据。
