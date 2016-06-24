/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50612
Source Host           : localhost:3306
Source Database       : wifi_localization

Target Server Type    : MYSQL
Target Server Version : 50612
File Encoding         : 65001

Date: 2014-05-07 22:57:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `access_point`
-- ----------------------------
DROP TABLE IF EXISTS `access_point`;
CREATE TABLE `access_point` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `position_id` int(11) DEFAULT NULL,
  `bssid` varchar(255) DEFAULT NULL,
  `ssid` varchar(255) DEFAULT NULL,
  `rssi` varchar(255) DEFAULT NULL,
  `flag` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5210 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of access_point
-- ----------------------------
INSERT INTO `access_point` VALUES ('5138', '159', 'c0:61:18:87:da:80', 'diaosiqipa', '-61', null);
INSERT INTO `access_point` VALUES ('5139', '159', 'f4:ec:38:2d:8f:76', 'TP-LINK_2D8F76', '-92', null);
INSERT INTO `access_point` VALUES ('5140', '159', '22:7c:8f:6c:81:58', 'LCM', '-73', null);
INSERT INTO `access_point` VALUES ('5141', '159', '40:16:9f:c9:ad:a2', 'zwtblackmuer', '-93', null);
INSERT INTO `access_point` VALUES ('5142', '159', '40:16:9f:cb:05:56', 'TP407', '-63', null);
INSERT INTO `access_point` VALUES ('5143', '159', '14:e6:e4:ac:12:36', '4445', '-94', null);
INSERT INTO `access_point` VALUES ('5144', '159', '14:cf:92:94:a9:08', 'wifi123456', '-87', null);
INSERT INTO `access_point` VALUES ('5145', '159', '8c:21:0a:c7:8f:02', 'Jessica', '-89', null);
INSERT INTO `access_point` VALUES ('5146', '159', 'f4:ec:38:3e:eb:e8', 'TP409', '-83', null);
INSERT INTO `access_point` VALUES ('5147', '159', '40:16:9f:da:d7:92', 'Mr.Link', '-69', null);
INSERT INTO `access_point` VALUES ('5148', '159', '14:e6:e4:5e:de:7c', 'SHADAN-PC_Network', '-57', null);
INSERT INTO `access_point` VALUES ('5149', '159', 'c8:3a:35:5d:b4:d8', 'FuckXJGe', '-94', null);
INSERT INTO `access_point` VALUES ('5150', '159', 'd4:ee:07:04:79:f4', 'HiWiFi_0479F4', '-73', null);
INSERT INTO `access_point` VALUES ('5151', '159', 'f4:ec:38:3c:8d:8e', 'fuck_you', '-69', null);
INSERT INTO `access_point` VALUES ('5152', '159', 'f0:7d:68:4c:53:10', 'djyiran', '-91', null);
INSERT INTO `access_point` VALUES ('5153', '159', 'ec:88:8f:a4:ad:42', 'BIN', '-82', null);
INSERT INTO `access_point` VALUES ('5154', '159', '22:08:ca:29:12:76', 'xxd', '-83', null);
INSERT INTO `access_point` VALUES ('5155', '159', 'c8:3a:35:40:0a:f0', 'FF1083', '-43', null);
INSERT INTO `access_point` VALUES ('5156', '159', 'c8:3a:35:0e:78:c8', 'wwenchang', '-59', null);
INSERT INTO `access_point` VALUES ('5157', '159', '78:54:2e:f9:7e:c8', '405', '-42', null);
INSERT INTO `access_point` VALUES ('5158', '159', '00:25:86:8a:05:ce', 'TP-LINK_PB', '-84', null);
INSERT INTO `access_point` VALUES ('5159', '159', 'a8:57:4e:aa:d1:46', 'lalala', '-85', null);
INSERT INTO `access_point` VALUES ('5160', '159', 'd8:24:bd:76:cc:d1', 'cisco-CCCF', '-55', null);
INSERT INTO `access_point` VALUES ('5161', '159', 'a8:15:4d:5e:29:32', 'qipadadiaosi', '-68', null);
INSERT INTO `access_point` VALUES ('5162', '160', 'c0:61:18:87:da:80', 'diaosiqipa', '-73', null);
INSERT INTO `access_point` VALUES ('5163', '160', '22:7c:8f:6c:81:58', 'LCM', '-83', null);
INSERT INTO `access_point` VALUES ('5164', '160', 'f4:ec:38:2d:8f:76', 'TP-LINK_2D8F76', '-82', null);
INSERT INTO `access_point` VALUES ('5165', '160', '40:16:9f:c9:ad:a2', 'zwtblackmuer', '-75', null);
INSERT INTO `access_point` VALUES ('5166', '160', '8c:a9:82:1f:28:7f', 'yujianyue', '-92', null);
INSERT INTO `access_point` VALUES ('5167', '160', 'd8:42:ac:40:ac:95', 'feixun_40AC95', '-87', null);
INSERT INTO `access_point` VALUES ('5168', '160', '8c:21:0a:c7:8f:02', 'Jessica', '-79', null);
INSERT INTO `access_point` VALUES ('5169', '160', 'f4:ec:38:3e:eb:e8', 'TP409', '-85', null);
INSERT INTO `access_point` VALUES ('5170', '160', '40:16:9f:da:d7:92', 'Mr.Link', '-81', null);
INSERT INTO `access_point` VALUES ('5171', '160', '14:e6:e4:5e:de:7c', 'SHADAN-PC_Network', '-66', null);
INSERT INTO `access_point` VALUES ('5172', '160', 'c8:3a:35:5d:b4:d8', 'FuckXJGe', '-81', null);
INSERT INTO `access_point` VALUES ('5173', '160', 'f0:eb:d0:d5:34:c0', 'yuan', '-83', null);
INSERT INTO `access_point` VALUES ('5174', '160', 'f4:ec:38:3c:8d:8e', 'fuck_you', '-71', null);
INSERT INTO `access_point` VALUES ('5175', '160', '14:e6:e4:4f:f7:a6', 'myhome', '-68', null);
INSERT INTO `access_point` VALUES ('5176', '160', '14:e6:e4:cb:f5:76', 'XiaoYuanWangShangBuQu', '-75', null);
INSERT INTO `access_point` VALUES ('5177', '160', 'ec:88:8f:a4:ad:42', 'BIN', '-70', null);
INSERT INTO `access_point` VALUES ('5178', '160', '22:08:ca:29:12:76', 'xxd', '-86', null);
INSERT INTO `access_point` VALUES ('5179', '160', 'c8:3a:35:40:0a:f0', 'FF1083', '-58', null);
INSERT INTO `access_point` VALUES ('5180', '160', 'c8:3a:35:0e:78:c8', 'wwenchang', '-62', null);
INSERT INTO `access_point` VALUES ('5181', '160', '78:54:2e:f9:7e:c8', '405', '-50', null);
INSERT INTO `access_point` VALUES ('5182', '160', '00:25:86:8a:05:ce', 'TP-LINK_PB', '-80', null);
INSERT INTO `access_point` VALUES ('5183', '160', 'b0:48:7a:32:69:38', 'TP-LINK_326938', '-81', null);
INSERT INTO `access_point` VALUES ('5184', '160', 'a8:57:4e:aa:d1:46', 'lalala', '-76', null);
INSERT INTO `access_point` VALUES ('5185', '160', 'd8:24:bd:76:cc:d1', 'cisco-CCCF', '-64', null);
INSERT INTO `access_point` VALUES ('5186', '160', '22:25:d3:f8:06:8d', 'WiFi110', '-85', null);
INSERT INTO `access_point` VALUES ('5187', '160', 'a8:15:4d:5e:29:32', 'qipadadiaosi', '-80', null);
INSERT INTO `access_point` VALUES ('5188', '161', 'c0:61:18:87:da:80', 'diaosiqipa', '-52', null);
INSERT INTO `access_point` VALUES ('5189', '161', '90:94:e4:a7:05:34', 'Keng', '-84', null);
INSERT INTO `access_point` VALUES ('5190', '161', '9c:21:6a:1b:8a:fa', 'FAST_HUHU', '-84', null);
INSERT INTO `access_point` VALUES ('5191', '161', '22:7c:8f:6c:81:58', 'LCM', '-72', null);
INSERT INTO `access_point` VALUES ('5192', '161', '40:16:9f:cb:05:56', 'TP407', '-78', null);
INSERT INTO `access_point` VALUES ('5193', '161', '16:2f:68:6c:57:33', '360WiFi-6264', '-93', null);
INSERT INTO `access_point` VALUES ('5194', '161', '14:cf:92:94:a9:08', 'wifi123456', '-85', null);
INSERT INTO `access_point` VALUES ('5195', '161', 'f4:ec:38:3e:eb:e8', 'TP409', '-83', null);
INSERT INTO `access_point` VALUES ('5196', '161', '40:16:9f:da:d7:92', 'Mr.Link', '-73', null);
INSERT INTO `access_point` VALUES ('5197', '161', '14:e6:e4:5e:de:7c', 'SHADAN-PC_Network', '-63', null);
INSERT INTO `access_point` VALUES ('5198', '161', 'd4:ee:07:04:79:f4', 'HiWiFi_0479F4', '-74', null);
INSERT INTO `access_point` VALUES ('5199', '161', 'f4:ec:38:3c:8d:8e', 'fuck_you', '-71', null);
INSERT INTO `access_point` VALUES ('5200', '161', 'f0:eb:d0:d5:34:c0', 'yuan', '-91', null);
INSERT INTO `access_point` VALUES ('5201', '161', '14:e6:e4:4f:f7:a6', 'myhome', '-75', null);
INSERT INTO `access_point` VALUES ('5202', '161', '22:08:ca:29:12:76', 'xxd', '-86', null);
INSERT INTO `access_point` VALUES ('5203', '161', 'c8:3a:35:40:0a:f0', 'FF1083', '-54', null);
INSERT INTO `access_point` VALUES ('5204', '161', 'c8:3a:35:0e:78:c8', 'wwenchang', '-58', null);
INSERT INTO `access_point` VALUES ('5205', '161', '78:54:2e:f9:7e:c8', '405', '-41', null);
INSERT INTO `access_point` VALUES ('5206', '161', 'a8:57:4e:aa:d1:46', 'lalala', '-84', null);
INSERT INTO `access_point` VALUES ('5207', '161', 'd8:24:bd:76:cc:d1', 'cisco-CCCF', '-53', null);
INSERT INTO `access_point` VALUES ('5208', '161', 'a8:15:4d:5e:29:32', 'qipadadiaosi', '-71', null);
INSERT INTO `access_point` VALUES ('5209', '161', 'ec:17:2f:ea:3d:7e', 'lushendebaonvhenbuhen', '-79', null);

-- ----------------------------
-- Table structure for `category`
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `flag` int(11) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('1', '0', '洗手间', null, null, null, '1');
INSERT INTO `category` VALUES ('2', '0', '出口', null, null, null, '1');
INSERT INTO `category` VALUES ('3', '0', '美食', null, null, null, '1');
INSERT INTO `category` VALUES ('4', '0', '生活服务', null, null, null, '1');
INSERT INTO `category` VALUES ('5', '4', 'ATM', '自助取款机', null, null, '2');
INSERT INTO `category` VALUES ('6', '0', '住所', null, null, null, '1');
INSERT INTO `category` VALUES ('7', '6', '宿舍', null, null, null, '2');
INSERT INTO `category` VALUES ('8', '0', '其他', null, null, null, '1');

-- ----------------------------
-- Table structure for `comment`
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `content_type_id` int(11) DEFAULT NULL,
  `content_id` int(11) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `create_time` varchar(255) DEFAULT NULL,
  `flag` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES ('1', '1', null, '23', '啦啦', '1399274956', null);
INSERT INTO `comment` VALUES ('2', '1', null, '23', '啦啦', '1399277251', null);
INSERT INTO `comment` VALUES ('3', '1', null, '23', '哈哈[忧郁][忧郁][囧]', '1399277271', null);
INSERT INTO `comment` VALUES ('4', '1', null, '23', '454646734', '1399278034', null);
INSERT INTO `comment` VALUES ('5', '1', null, '26', '哈哈', '1399443169', null);
INSERT INTO `comment` VALUES ('6', '1', null, '26', '啦啦', '1399443177', null);

-- ----------------------------
-- Table structure for `flow`
-- ----------------------------
DROP TABLE IF EXISTS `flow`;
CREATE TABLE `flow` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `position_id` int(11) DEFAULT NULL,
  `time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of flow
-- ----------------------------

-- ----------------------------
-- Table structure for `image`
-- ----------------------------
DROP TABLE IF EXISTS `image`;
CREATE TABLE `image` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content_id` int(11) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `width` varchar(255) DEFAULT NULL,
  `height` varchar(255) DEFAULT NULL,
  `size` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of image
-- ----------------------------
INSERT INTO `image` VALUES ('35', '20', 'upload/d5b0ad2dceb9746da60ef46eb20026b5.jpg', null, null, null, null, null);
INSERT INTO `image` VALUES ('36', '20', 'upload/e40a3278b4ec0764a1f1b108445031a2.jpg', null, null, null, null, null);
INSERT INTO `image` VALUES ('37', '20', 'upload/324f32d733f0a01ebe7b0d6000483e85.jpg', null, null, null, null, null);
INSERT INTO `image` VALUES ('38', '20', 'upload/98487f1c33f1dae5d86a508e85c36495.jpg', null, null, null, null, null);
INSERT INTO `image` VALUES ('39', '21', 'upload/ee1b88b73ad1048e27e0ae8c76475869.jpg', null, null, null, null, null);
INSERT INTO `image` VALUES ('40', '22', 'upload/1fc9b68e1560d8e87a44da1a04d27c5f.jpg', null, null, null, null, null);
INSERT INTO `image` VALUES ('41', '22', 'upload/49def995ccf75cbc57f32de93604d58f.jpg', null, null, null, null, null);
INSERT INTO `image` VALUES ('42', '22', 'upload/b3ef47c2bef225b0ca9a7faee1a25079.jpg', null, null, null, null, null);
INSERT INTO `image` VALUES ('43', '22', 'upload/9a19035c04ef62efde73298d20c6a373.jpg', null, null, null, null, null);
INSERT INTO `image` VALUES ('44', '23', 'upload/c4b9a986300c2c723b1303d2dd3ac8af.jpg', null, null, null, null, null);
INSERT INTO `image` VALUES ('45', '23', 'upload/0fc942b962e02278374d11a4e279f439.jpg', null, null, null, null, null);
INSERT INTO `image` VALUES ('46', '23', 'upload/14f919f7f46f5d0ca36a97222f2f47ff.jpg', null, null, null, null, null);
INSERT INTO `image` VALUES ('47', '23', 'upload/4cea6cac6d3f722b49499a98b39c7e1f.jpg', null, null, null, null, null);
INSERT INTO `image` VALUES ('48', '24', 'upload/4d0e2c7963ba423cb57ca14be8d21a20.jpg', null, null, null, null, null);
INSERT INTO `image` VALUES ('49', '25', 'upload/ecee4cd3c83c0d9cc6dd8af25551c17b.jpg', null, null, null, null, null);
INSERT INTO `image` VALUES ('50', '26', 'upload/890f709011e2d6ccacaff7afc51ee884.jpg', null, null, null, null, null);
INSERT INTO `image` VALUES ('51', '27', 'upload/524bcb419e7e3bf7c10597de48dd5592.jpg', null, null, null, null, null);
INSERT INTO `image` VALUES ('52', '28', 'upload/2e0ca1dfd31d7757fb47638d265a528e.jpg', null, null, null, null, null);

-- ----------------------------
-- Table structure for `map`
-- ----------------------------
DROP TABLE IF EXISTS `map`;
CREATE TABLE `map` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `front_degree` varchar(255) DEFAULT NULL,
  `flag` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of map
-- ----------------------------
INSERT INTO `map` VALUES ('1', 'dorm 3 c district neu', null, '268', null);

-- ----------------------------
-- Table structure for `node`
-- ----------------------------
DROP TABLE IF EXISTS `node`;
CREATE TABLE `node` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `position_id` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `flag` int(11) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of node
-- ----------------------------
INSERT INTO `node` VALUES ('25', '159', '405第三床', null, '谢志宁的床位', null, '8');
INSERT INTO `node` VALUES ('26', '152', '405阳台', null, '阳光很好', null, '7');
INSERT INTO `node` VALUES ('27', '153', '405门口', null, '无', null, '7');
INSERT INTO `node` VALUES ('28', '154', '一楼大厅西侧', null, '东北大学', null, '8');

-- ----------------------------
-- Table structure for `position`
-- ----------------------------
DROP TABLE IF EXISTS `position`;
CREATE TABLE `position` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `map_x` float DEFAULT NULL,
  `map_y` float DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=162 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of position
-- ----------------------------
INSERT INTO `position` VALUES ('159', null, null, '113', '537', null, null);
INSERT INTO `position` VALUES ('160', null, null, '20', '615', null, null);
INSERT INTO `position` VALUES ('161', null, null, '289', '616', null, null);

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `position_id` int(11) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `age` varchar(255) DEFAULT NULL,
  `gender` int(11) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `signature` varchar(255) DEFAULT NULL,
  `flag` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '159', 'lynn', '123456', 'upload/20140427111826.jpg', null, null, null, null, '我叫alin', '哈哈', null);
INSERT INTO `user` VALUES ('2', null, 'test1', '123456', 'upload/20140427194743.jpg', null, null, null, null, null, '哈哈', null);
INSERT INTO `user` VALUES ('3', null, 'test2', '123456', 'upload/20140424154501.jpg', null, null, null, null, null, null, null);
INSERT INTO `user` VALUES ('4', null, 'test3', '123456', 'upload/20140424154501.jpg', null, null, null, null, null, null, null);
INSERT INTO `user` VALUES ('5', null, 'wyy', 'wyystc', 'upload/20140424154501.jpg', null, null, null, null, 'wyy', null, null);

-- ----------------------------
-- Table structure for `user_relationship`
-- ----------------------------
DROP TABLE IF EXISTS `user_relationship`;
CREATE TABLE `user_relationship` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `friend_id` int(11) DEFAULT NULL,
  `user_screen_name` char(255) DEFAULT NULL,
  `friend_srceen_name` char(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `create_time` char(255) DEFAULT NULL,
  `flag` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of user_relationship
-- ----------------------------
INSERT INTO `user_relationship` VALUES ('1', '1', '2', null, null, '1', null, null);
INSERT INTO `user_relationship` VALUES ('2', '1', '3', null, null, '1', null, null);
INSERT INTO `user_relationship` VALUES ('3', '2', '3', null, null, '1', null, null);
INSERT INTO `user_relationship` VALUES ('5', '4', '2', null, null, '1', null, null);
INSERT INTO `user_relationship` VALUES ('14', '1', '4', null, null, '1', null, null);
INSERT INTO `user_relationship` VALUES ('15', '1', '5', null, null, '1', null, null);
