create database ds0master character set utf8;
create database ds0slave character set utf8;
create database ds1master character set utf8;
create database ds1slave character set utf8;
create user 'test'@'%' identified by 'test';
grant all PRIVILEGES on ds0master.* to 'test'@"%";
grant all PRIVILEGES on ds0slave.* to 'test'@"%";
grant all PRIVILEGES on ds1master.* to 'test'@"%";
grant all PRIVILEGES on ds1slave.* to 'test'@"%";

use ds0master;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user0`;
CREATE TABLE `user0` (
                         `id` bigint(20) NOT NULL COMMENT '主键ID',
                         `name` varchar(30) DEFAULT NULL COMMENT '姓名',
                         `age` int(11) DEFAULT NULL COMMENT '年龄',
                         `address` varchar(50) DEFAULT NULL COMMENT '邮箱',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user1`;
CREATE TABLE `user1` (
                         `id` bigint(20) NOT NULL COMMENT '主键ID',
                         `name` varchar(30) DEFAULT NULL COMMENT '姓名',
                         `age` int(11) DEFAULT NULL COMMENT '年龄',
                         `address` varchar(50) DEFAULT NULL COMMENT '邮箱',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

use ds0slave;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user0`;
CREATE TABLE `user0` (
                         `id` bigint(20) NOT NULL COMMENT '主键ID',
                         `name` varchar(30) DEFAULT NULL COMMENT '姓名',
                         `age` int(11) DEFAULT NULL COMMENT '年龄',
                         `address` varchar(50) DEFAULT NULL COMMENT '邮箱',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user1`;
CREATE TABLE `user1` (
                         `id` bigint(20) NOT NULL COMMENT '主键ID',
                         `name` varchar(30) DEFAULT NULL COMMENT '姓名',
                         `age` int(11) DEFAULT NULL COMMENT '年龄',
                         `address` varchar(50) DEFAULT NULL COMMENT '邮箱',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user0` VALUES ('1', 'zhangsan', '18', 'beijing');
INSERT INTO `user0` VALUES ('2', 'lisi', '19', 'shanghai');
INSERT INTO `user0` VALUES ('3', 'wangwu', '20', 'guangzhou');
INSERT INTO `user0` VALUES ('4', 'zhaoliu', '21', 'shenzhen');
INSERT INTO `user0` VALUES ('5', 'sunqi', '22', 'hangzhou');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
                           `id` bigint(20) NOT NULL COMMENT '主键ID',
                           `name` varchar(30) DEFAULT NULL COMMENT '姓名',
                           `age` int(11) DEFAULT NULL COMMENT '年龄',
                           `address` varchar(50) DEFAULT NULL COMMENT '邮箱',
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('1', 'zhangsan', '18', 'beijing');
INSERT INTO `student` VALUES ('2', 'lisi', '19', 'shanghai');
INSERT INTO `student` VALUES ('3', 'wangwu', '20', 'guangzhou');
INSERT INTO `student` VALUES ('4', 'zhaoliu', '21', 'shenzhen');
INSERT INTO `student` VALUES ('5', 'sunqi', '22', 'hangzhou');

use ds1master;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user0`;
CREATE TABLE `user0` (
                         `id` bigint(20) NOT NULL COMMENT '主键ID',
                         `name` varchar(30) DEFAULT NULL COMMENT '姓名',
                         `age` int(11) DEFAULT NULL COMMENT '年龄',
                         `address` varchar(50) DEFAULT NULL COMMENT '邮箱',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user1`;
CREATE TABLE `user1` (
                         `id` bigint(20) NOT NULL COMMENT '主键ID',
                         `name` varchar(30) DEFAULT NULL COMMENT '姓名',
                         `age` int(11) DEFAULT NULL COMMENT '年龄',
                         `address` varchar(50) DEFAULT NULL COMMENT '邮箱',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

use ds1slave;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user0`;
CREATE TABLE `user0` (
                         `id` bigint(20) NOT NULL COMMENT '主键ID',
                         `name` varchar(30) DEFAULT NULL COMMENT '姓名',
                         `age` int(11) DEFAULT NULL COMMENT '年龄',
                         `address` varchar(50) DEFAULT NULL COMMENT '邮箱',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user1`;
CREATE TABLE `user1` (
                         `id` bigint(20) NOT NULL COMMENT '主键ID',
                         `name` varchar(30) DEFAULT NULL COMMENT '姓名',
                         `age` int(11) DEFAULT NULL COMMENT '年龄',
                         `address` varchar(50) DEFAULT NULL COMMENT '邮箱',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;