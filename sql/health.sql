-- 健康档案表
CREATE TABLE `health_profile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `height` double DEFAULT NULL COMMENT '身高(cm)',
  `weight` double DEFAULT NULL COMMENT '体重(kg)',
  `blood_type` varchar(10) DEFAULT NULL COMMENT '血型',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='健康档案表';

-- 健康数据表
CREATE TABLE `health_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `blood_pressure_high` int(11) DEFAULT NULL COMMENT '收缩压(高压)',
  `blood_pressure_low` int(11) DEFAULT NULL COMMENT '舒张压(低压)',
  `heart_rate` int(11) DEFAULT NULL COMMENT '心率(次/分)',
  `blood_sugar` double DEFAULT NULL COMMENT '血糖(mmol/L)',
  `temperature` double DEFAULT NULL COMMENT '体温(℃)',
  `measure_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '测量时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '记录时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_measure_time` (`measure_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='健康数据表';

-- 插入健康档案样例数据
INSERT INTO `health_profile` (`user_id`, `height`, `weight`, `blood_type`) VALUES
(1, 170.5, 65.0, 'A'),
(2, 160.0, 55.5, 'O');

-- 插入健康数据样例数据
INSERT INTO `health_data` (`user_id`, `blood_pressure_high`, `blood_pressure_low`, `heart_rate`, `blood_sugar`, `temperature`, `measure_time`) VALUES
(1, 120, 80, 75, 5.6, 36.5, DATE_SUB(NOW(), INTERVAL 1 DAY)),
(1, 125, 82, 78, 5.8, 36.6, NOW()),
(2, 130, 85, 80, 6.1, 36.7, NOW());
