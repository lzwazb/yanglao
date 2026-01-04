/*
 Navicat Premium Dump SQL

 Source Server         : 12345
 Source Server Type    : MySQL
 Source Server Version : 80042 (8.0.42)
 Source Host           : localhost:3306
 Source Schema         : yanglao

 Target Server Type    : MySQL
 Target Server Version : 80042 (8.0.42)
 File Encoding         : 65001

 Date: 04/01/2026 09:00:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '活动标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '活动内容/描述',
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '活动地点',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '结束时间',
  `max_people` int NULL DEFAULT 0 COMMENT '最大人数限制(0为不限)',
  `status` int NULL DEFAULT 0 COMMENT '状态: 0-未开始, 1-进行中, 2-已结束, -1-已取消',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '活动表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for activity_registration
-- ----------------------------
DROP TABLE IF EXISTS `activity_registration`;
CREATE TABLE `activity_registration`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户姓名快照',
  `status` int NULL DEFAULT 1 COMMENT '状态: 1-已报名, 0-已取消',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '报名时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_activity_id`(`activity_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '活动报名表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for administrator
-- ----------------------------
DROP TABLE IF EXISTS `administrator`;
CREATE TABLE `administrator`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `real_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` int NULL DEFAULT 0 COMMENT '状态：1-禁用，0-正常',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_username`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '管理员表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for family
-- ----------------------------
DROP TABLE IF EXISTS `family`;
CREATE TABLE `family`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '姓名',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '手机号',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地址',
  `status` int NOT NULL DEFAULT 0 COMMENT '状态：1-禁用，0-正常',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账号',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '家属表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for health_data
-- ----------------------------
DROP TABLE IF EXISTS `health_data`;
CREATE TABLE `health_data`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `blood_pressure_high` int NULL DEFAULT NULL COMMENT '收缩压(高压)',
  `blood_pressure_low` int NULL DEFAULT NULL COMMENT '舒张压(低压)',
  `heart_rate` int NULL DEFAULT NULL COMMENT '心率(次/分)',
  `blood_sugar` double NULL DEFAULT NULL COMMENT '血糖(mmol/L)',
  `temperature` double NULL DEFAULT NULL COMMENT '体温(℃)',
  `measure_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '测量时间',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_measure_time`(`measure_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '健康数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for health_profile
-- ----------------------------
DROP TABLE IF EXISTS `health_profile`;
CREATE TABLE `health_profile`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `height` double NULL DEFAULT NULL COMMENT '身高(cm)',
  `weight` double NULL DEFAULT NULL COMMENT '体重(kg)',
  `blood_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '血型',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '健康档案表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint NULL DEFAULT NULL COMMENT '留言用户ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '留言用户名',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '留言内容',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '留言时间',
  `reply_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '回复内容',
  `reply_time` datetime NULL DEFAULT NULL COMMENT '回复时间',
  `reply_user` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '回复人(管理员)',
  `status` tinyint NULL DEFAULT 0 COMMENT '状态: 0-未回复, 1-已回复',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '留言板' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for service_item
-- ----------------------------
DROP TABLE IF EXISTS `service_item`;
CREATE TABLE `service_item`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '服务名称',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '服务描述',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图标标识',
  `price` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '参考价格',
  `status` tinyint NULL DEFAULT 1 COMMENT '状态: 0-下架, 1-上架',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '养老服务项目表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for service_order
-- ----------------------------
DROP TABLE IF EXISTS `service_order`;
CREATE TABLE `service_order`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `service_id` bigint NOT NULL COMMENT '服务项目ID',
  `service_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '服务名称快照',
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '服务类型: CHECK_IN(入住), HOME(上门)',
  `address_info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地址信息(房间号/单元号等)',
  `book_time` datetime NOT NULL COMMENT '预约服务时间',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'PENDING' COMMENT '状态: PENDING(待确认), CONFIRMED(已确认), COMPLETED(已完成), CANCELLED(已取消)',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_service_id`(`service_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '服务预约订单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for service_review
-- ----------------------------
DROP TABLE IF EXISTS `service_review`;
CREATE TABLE `service_review`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '评价ID',
  `order_id` bigint NOT NULL COMMENT '关联订单ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `rating` int NULL DEFAULT 5 COMMENT '评分(1-5)',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '评价内容',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评价时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_order_id`(`order_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '服务评价表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '公告标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '公告内容',
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '通知' COMMENT '类型: 通知/重要/活动',
  `author` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '管理员' COMMENT '发布人',
  `status` tinyint NULL DEFAULT 1 COMMENT '状态: 0-撤回, 1-发布',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统公告表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `real_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '真实姓名',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '手机号',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '邮箱',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像URL',
  `status` int NULL DEFAULT 0 COMMENT '状态：1-禁用，0-正常',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `family_id` bigint NOT NULL COMMENT '关联家人id',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_username`(`username` ASC) USING BTREE,
  INDEX `family_id`(`family_id` ASC) USING BTREE,
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`family_id`) REFERENCES `family` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- 插入样例数据
-- ----------------------------

-- 1. 插入管理员 (密码: 123456)
INSERT INTO `administrator` (`username`, `password`, `real_name`, `phone`, `email`, `status`) VALUES
('admin', '123456', '系统管理员', '13800000000', 'admin@yanglao.com', 0);

-- 2. 插入家属 (密码: 123456)
INSERT INTO `family` (`name`, `phone`, `address`, `status`, `username`, `password`) VALUES
('李建国', '13911112222', '北京市朝阳区幸福小区1号楼', 0, 'family_li', '123456'),
('王淑芬', '13933334444', '上海市海淀区阳光花园2号楼', 0, 'family_wang', '123456');

-- 3. 插入用户 (老人) (密码: 123456)
-- 注意：family_id 关联上面的家属ID (假设自增ID从1开始，这里需要根据实际生成ID调整，为演示方便假设为1和2)
-- 实际生产中应先查询家属ID再插入，这里仅为演示SQL结构
INSERT INTO `user` (`username`, `password`, `real_name`, `phone`, `email`, `status`, `family_id`) VALUES
('laoren_zhang', '123456', '张大爷', '13812345678', 'zhang@yanglao.com', 0, 1),
('laoren_liu', '123456', '刘奶奶', '13887654321', 'liu@yanglao.com', 0, 1),
('laoren_chen', '123456', '陈爷爷', '13811223344', 'chen@yanglao.com', 0, 2);

-- 4. 插入服务项目
INSERT INTO `service_item` (`name`, `description`, `icon`, `price`, `status`) VALUES
('家政保洁', '提供专业的室内清洁服务，包括地面、窗户、家具等。', 'Tools', 50.00, 1),
('助餐服务', '营养均衡的老年餐配送，支持午餐和晚餐。', 'Coffee', 20.00, 1),
('陪诊就医', '专业人员陪同老人前往医院就诊，协助挂号、取药等。', 'FirstAidKit', 100.00, 1),
('上门理发', '专业理发师上门为行动不便的老人提供理发服务。', 'User', 30.00, 1);

-- 5. 插入活动
INSERT INTO `activity` (`title`, `content`, `location`, `start_time`, `end_time`, `max_people`, `status`) VALUES
('老年健康知识讲座', '邀请知名专家讲解老年人常见病预防与保健知识。', '社区活动中心', DATE_ADD(NOW(), INTERVAL 2 DAY), DATE_ADD(DATE_ADD(NOW(), INTERVAL 2 DAY), INTERVAL 2 HOUR), 50, 0),
('重阳节书法比赛', '弘扬传统文化，丰富老年人精神生活。', '养老院书画室', DATE_ADD(NOW(), INTERVAL 5 DAY), DATE_ADD(DATE_ADD(NOW(), INTERVAL 5 DAY), INTERVAL 3 HOUR), 30, 0),
('春季户外踏青', '组织前往郊野公园踏青赏花。', '郊野公园', DATE_ADD(NOW(), INTERVAL 10 DAY), DATE_ADD(DATE_ADD(NOW(), INTERVAL 10 DAY), INTERVAL 6 HOUR), 40, 0);

-- 6. 插入公告
INSERT INTO `sys_notice` (`title`, `content`, `type`, `author`, `status`) VALUES
('关于系统维护的通知', '系统将于本周六凌晨进行例行维护，届时将无法访问，请谅解。', '通知', '管理员', 1),
('冬季流感预防指南', '近期流感高发，请各位老人注意保暖，勤洗手，多通风。', '重要', '医务室', 1),
('社区义诊活动预告', '下周一上午将在社区广场举行义诊活动，欢迎参加。', '活动', '居委会', 1);

-- 7. 插入健康档案 (关联用户ID 1, 2)
INSERT INTO `health_profile` (`user_id`, `height`, `weight`, `blood_type`) VALUES
(1, 172.0, 70.5, 'A'),
(2, 160.0, 55.0, 'O');

-- 8. 插入健康数据 (关联用户ID 1, 2)
INSERT INTO `health_data` (`user_id`, `blood_pressure_high`, `blood_pressure_low`, `heart_rate`, `blood_sugar`, `temperature`, `measure_time`) VALUES
(1, 125, 82, 75, 5.8, 36.6, DATE_SUB(NOW(), INTERVAL 1 DAY)),
(1, 128, 84, 78, 6.0, 36.7, NOW()),
(2, 130, 85, 80, 6.2, 36.5, NOW());

-- 9. 插入服务订单 (关联用户ID 1, 2 和服务ID 1, 2)
INSERT INTO `service_order` (`user_id`, `service_id`, `service_name`, `type`, `address_info`, `book_time`, `status`, `remark`) VALUES
(1, 1, '家政保洁', 'HOME', '幸福小区1号楼101室', DATE_ADD(NOW(), INTERVAL 1 DAY), 'PENDING', '请自带清洁工具'),
(2, 2, '助餐服务', 'HOME', '幸福小区1号楼102室', NOW(), 'COMPLETED', '少盐少油');

-- 10. 插入活动报名 (关联活动ID 1, 2 和用户ID 1, 2)
INSERT INTO `activity_registration` (`activity_id`, `user_id`, `user_name`, `status`) VALUES
(1, 1, '张大爷', 1),
(2, 1, '张大爷', 1),
(1, 2, '刘奶奶', 1);

-- 11. 插入留言
INSERT INTO `message` (`user_id`, `username`, `content`, `status`) VALUES
(1, 'laoren_zhang', '请问社区食堂周末开放吗？', 0),
(2, 'laoren_liu', '感谢上次陪诊的小王，服务很周到！', 0);
