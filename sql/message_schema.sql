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

 Date: 02/01/2026 20:29:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` bigint NOT NULL COMMENT '主键ID',
  `user_id` bigint COMMENT '留言用户ID',
  `username` varchar(50) COMMENT '留言用户名',
  `content` text NOT NULL COMMENT '留言内容',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '留言时间',
  `reply_content` text COMMENT '回复内容',
  `reply_time` datetime COMMENT '回复时间',
  `reply_user` varchar(50) COMMENT '回复人(管理员)',
  `status` tinyint DEFAULT 0 COMMENT '状态: 0-未回复, 1-已回复',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='留言板';

SET FOREIGN_KEY_CHECKS = 1;
