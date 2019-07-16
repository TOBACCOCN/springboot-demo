SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `name` varchar(30) DEFAULT NULL COMMENT '姓名',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `address` varchar(50) DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'zhangsan', '18', 'beijing');
INSERT INTO `user` VALUES ('2', 'lisi', '19', 'shanghai');
INSERT INTO `user` VALUES ('3', 'wangwu', '20', 'guangzhou');
INSERT INTO `user` VALUES ('4', 'zhaoliu', '21', 'shenzhen');
INSERT INTO `user` VALUES ('5', 'sunqi', '22', 'hangzhou');
