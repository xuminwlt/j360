SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cmg_product
-- ----------------------------
DROP TABLE IF EXISTS `cmg_product`;
CREATE TABLE `cmg_product` (
  `id` varchar(32) NOT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifyDate` datetime DEFAULT NULL,
  `timestamp` varchar(255) NOT NULL,
  `state` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `pinYin` varchar(255) DEFAULT NULL,
  `pinYinHead` varchar(255) DEFAULT NULL,
  `baceMoney` decimal(19,2) DEFAULT NULL,
  `customDaysValue` decimal(19,2) DEFAULT NULL,
  `customDeadlineValue` datetime DEFAULT NULL,
  `customValue` int(11) DEFAULT NULL,
  `customValueType` int(11) DEFAULT NULL,
  `deadline` datetime DEFAULT NULL,
  `money` decimal(19,2) DEFAULT NULL,
  `no` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `targetKey` varchar(255) DEFAULT NULL,
  `volume` varchar(255) DEFAULT NULL,
  `weight` varchar(255) DEFAULT NULL,
  `creater_id` varchar(32) DEFAULT NULL,
  `headImage_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ecs2b39k56y8jqac43s7iok3y` (`timestamp`),
  KEY `FK_td1hnqlomcycclewqy8mvimk2` (`creater_id`),
  KEY `FK_dljt9egechgis66yxlo8m4wnb` (`headImage_id`),
  CONSTRAINT `FK_dljt9egechgis66yxlo8m4wnb` FOREIGN KEY (`headImage_id`) REFERENCES `sys_filemanage` (`id`),
  CONSTRAINT `FK_td1hnqlomcycclewqy8mvimk2` FOREIGN KEY (`creater_id`) REFERENCES `cmg_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cmg_user
-- ----------------------------
DROP TABLE IF EXISTS `cmg_user`;
CREATE TABLE `cmg_user` (
  `id` varchar(32) NOT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifyDate` datetime DEFAULT NULL,
  `timestamp` varchar(255) NOT NULL,
  `state` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `pinYin` varchar(255) DEFAULT NULL,
  `pinYinHead` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `county` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `idCard` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `sex` int(11) NOT NULL,
  `creater_id` varchar(32) DEFAULT NULL,
  `admin_id` varchar(32) DEFAULT NULL,
  `headImage_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_4l9jadgg0t0f5sn6u53j86wbe` (`timestamp`),
  KEY `FK_l8cnax0jl850j79132g5tonh0` (`creater_id`),
  KEY `FK_4jeet4rp1kv06h959h03m11ly` (`admin_id`),
  KEY `FK_77rjls7nu0d0j38gkjwkvn1gv` (`headImage_id`),
  CONSTRAINT `FK_77rjls7nu0d0j38gkjwkvn1gv` FOREIGN KEY (`headImage_id`) REFERENCES `sys_filemanage` (`id`),
  CONSTRAINT `FK_4jeet4rp1kv06h959h03m11ly` FOREIGN KEY (`admin_id`) REFERENCES `sys_admin` (`id`),
  CONSTRAINT `FK_l8cnax0jl850j79132g5tonh0` FOREIGN KEY (`creater_id`) REFERENCES `cmg_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_admin
-- ----------------------------
DROP TABLE IF EXISTS `sys_admin`;
CREATE TABLE `sys_admin` (
  `id` varchar(32) NOT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifyDate` datetime DEFAULT NULL,
  `timestamp` varchar(255) NOT NULL,
  `expireDate` datetime DEFAULT NULL,
  `ifAccountEnabled` int(11) NOT NULL,
  `ifAccountExpired` int(11) NOT NULL,
  `ifAccountLocked` int(11) NOT NULL,
  `ifCredentialsExpired` int(11) NOT NULL,
  `ifSystem` int(11) NOT NULL,
  `lockedDate` datetime DEFAULT NULL,
  `loginDate` datetime DEFAULT NULL,
  `loginFailureCount` int(11) NOT NULL,
  `loginIp` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `openId` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `theme` varchar(255) DEFAULT NULL,
  `useremail` varchar(255) DEFAULT NULL,
  `usermobile` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_2yt3jdqx6tfetsaaqdkptw0p0` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(32) NOT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifyDate` datetime DEFAULT NULL,
  `timestamp` varchar(255) NOT NULL,
  `ifSystem` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `remark` longtext,
  `value` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_cbfgxjlty65xn3n0oams1p2he` (`timestamp`),
  UNIQUE KEY `UK_bqy406dtsr7j7d6fawi1ckyn1` (`name`),
  UNIQUE KEY `UK_js4no3fbry0lxg71b86xub9i0` (`value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_role_sys_admin
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_sys_admin`;
CREATE TABLE `sys_role_sys_admin` (
  `roleSet_id` varchar(32) NOT NULL,
  `adminSet_id` varchar(32) NOT NULL,
  PRIMARY KEY (`roleSet_id`,`adminSet_id`),
  KEY `FK_f9nm5yca555bfd99t2djel9ym` (`adminSet_id`),
  KEY `FK_pdyrk780y1vs339xf6nysl143` (`roleSet_id`),
  CONSTRAINT `FK_pdyrk780y1vs339xf6nysl143` FOREIGN KEY (`roleSet_id`) REFERENCES `sys_role` (`id`),
  CONSTRAINT `FK_f9nm5yca555bfd99t2djel9ym` FOREIGN KEY (`adminSet_id`) REFERENCES `sys_admin` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `cmg_product` VALUES ('4028f18147af812d0147af82b22f0000', '2014-08-07 16:08:03', '2014-08-07 23:59:00', '140739888388011407398883889', '1', 'US电子充值劵（一年）', 'USdianzichongzhijuan（yinian）', 'USdzczj（yn）', '0.00', '1.00', null, '1', '2', null, '198.00', '14073988838800', '', null, '', '', '4028f181478a273701478a2741ca0003', null);
INSERT INTO `cmg_user` VALUES ('4028f181478a273701478a2741ca0003', '2014-07-31 10:02:14', '2014-07-31 10:02:14', '140677213434031406772134346', '1', '系统管理员', 'xitongguanliyuan', 'xtgly', null, null, null, null, null, null, null, null, null, '0', null, '4028f181478a273701478a2740e80002', null);
INSERT INTO `sys_admin` VALUES ('4028f181478a273701478a2740e80002', '2014-07-31 10:02:14', '2014-07-31 10:02:14', '140677213412021406772134120', null, '1', '0', '0', '0', '1', null, null, '0', null, '系统管理员', null, 'password', null, null, null, 'administrator');
INSERT INTO `sys_role` VALUES ('4028f181478a273701478a2740970000', '2014-07-31 10:02:14', '2014-07-31 10:02:14', '140677213404001406772134044', '1', '系统管理员', null, 'sys_admin');
INSERT INTO `sys_role` VALUES ('4028f181478a273701478a2740ce0001', '2014-07-31 10:02:14', '2014-07-31 10:02:14', '140677213409011406772134094', '1', '产品管理员', null, 'cmg_product');
INSERT INTO `sys_role_sys_admin` VALUES ('4028f181478a273701478a2740970000', '4028f181478a273701478a2740e80002');
