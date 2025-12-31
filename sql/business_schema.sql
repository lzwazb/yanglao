-- 数据库：yanglao_business (建议新建库或使用现有库)

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for service_item
-- ----------------------------
DROP TABLE IF EXISTS `service_item`;
CREATE TABLE `service_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(100) NOT NULL COMMENT '服务名称',
  `description` varchar(500) DEFAULT NULL COMMENT '服务描述',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标标识',
  `price` decimal(10,2) DEFAULT '0.00' COMMENT '参考价格',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态: 0-下架, 1-上架',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='养老服务项目表';

-- ----------------------------
-- Table structure for service_order
-- ----------------------------
DROP TABLE IF EXISTS `service_order`;
CREATE TABLE `service_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `service_id` bigint(20) NOT NULL COMMENT '服务项目ID',
  `service_name` varchar(100) DEFAULT NULL COMMENT '服务名称快照',
  `type` varchar(20) NOT NULL COMMENT '服务类型: CHECK_IN(入住), HOME(上门)',
  `address_info` varchar(255) DEFAULT NULL COMMENT '地址信息(房间号/单元号等)',
  `book_time` datetime NOT NULL COMMENT '预约服务时间',
  `status` varchar(20) DEFAULT 'PENDING' COMMENT '状态: PENDING(待确认), CONFIRMED(已确认), COMPLETED(已完成), CANCELLED(已取消)',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_service_id` (`service_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='服务预约订单表';

-- ----------------------------
-- Table structure for service_review
-- ----------------------------
DROP TABLE IF EXISTS `service_review`;
CREATE TABLE `service_review` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '评价ID',
  `order_id` bigint(20) NOT NULL COMMENT '关联订单ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `rating` int(11) DEFAULT '5' COMMENT '评分(1-5)',
  `content` text COMMENT '评价内容',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '评价时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_id` (`order_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='服务评价表';

SET FOREIGN_KEY_CHECKS = 1;
