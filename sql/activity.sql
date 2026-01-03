-- 活动表
CREATE TABLE `activity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(100) NOT NULL COMMENT '活动标题',
  `content` text COMMENT '活动内容/描述',
  `location` varchar(255) DEFAULT NULL COMMENT '活动地点',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `max_people` int(11) DEFAULT '0' COMMENT '最大人数限制(0为不限)',
  `status` int(11) DEFAULT '0' COMMENT '状态: 0-未开始, 1-进行中, 2-已结束, -1-已取消',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='活动表';

-- 活动报名表
CREATE TABLE `activity_registration` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `activity_id` bigint(20) NOT NULL COMMENT '活动ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户姓名快照',
  `status` int(11) DEFAULT '1' COMMENT '状态: 1-已报名, 0-已取消',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '报名时间',
  PRIMARY KEY (`id`),
  KEY `idx_activity_id` (`activity_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='活动报名表';

-- 插入活动样例数据
INSERT INTO `activity` (`title`, `content`, `location`, `start_time`, `end_time`, `max_people`, `status`) VALUES
('老年健康知识讲座', '邀请知名专家讲解老年人常见病预防与保健知识，现场提供免费血压测量。', '社区活动中心多功能厅', DATE_ADD(NOW(), INTERVAL 2 DAY), DATE_ADD(DATE_ADD(NOW(), INTERVAL 2 DAY), INTERVAL 2 HOUR), 50, 0),
('重阳节书法比赛', '弘扬传统文化，丰富老年人精神生活，欢迎各位书法爱好者踊跃报名。', '养老院书画室', DATE_ADD(NOW(), INTERVAL 5 DAY), DATE_ADD(DATE_ADD(NOW(), INTERVAL 5 DAY), INTERVAL 3 HOUR), 30, 0),
('春季户外踏青', '组织前往郊野公园踏青赏花，呼吸新鲜空气，享受大自然的美好。', '郊野公园（统一乘车）', DATE_ADD(NOW(), INTERVAL 10 DAY), DATE_ADD(DATE_ADD(NOW(), INTERVAL 10 DAY), INTERVAL 6 HOUR), 40, 0),
('手工编织兴趣班', '学习简单的毛衣、围巾编织技巧，锻炼手脑协调能力，成品可带回家。', '手工活动室', DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_ADD(DATE_SUB(NOW(), INTERVAL 1 DAY), INTERVAL 2 HOUR), 20, 2);

