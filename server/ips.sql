/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50612
Source Host           : localhost:3306
Source Database       : ips

Target Server Type    : MYSQL
Target Server Version : 50612
File Encoding         : 65001

Date: 2014-05-28 10:03:51
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
) ENGINE=InnoDB AUTO_INCREMENT=6624 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of access_point
-- ----------------------------

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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('1', '0', '出口', '安全出口', null, null, '1');
INSERT INTO `category` VALUES ('2', '0', '洗手间', '洗手间', null, null, '1');
INSERT INTO `category` VALUES ('3', '0', 'ATM', '银行自动取款机', null, null, '1');
INSERT INTO `category` VALUES ('4', '0', '电梯', '电梯', null, null, '1');
INSERT INTO `category` VALUES ('5', '0', '办公室', '办公室', null, null, '1');
INSERT INTO `category` VALUES ('6', '0', '休闲娱乐', '休闲娱乐', null, null, '1');
INSERT INTO `category` VALUES ('7', '0', '餐饮', '餐饮', null, null, '1');
INSERT INTO `category` VALUES ('8', '0', '其他', '其他', null, null, '1');
INSERT INTO `category` VALUES ('9', '6', '健身房', '健身房', null, null, '2');
INSERT INTO `category` VALUES ('10', '6', '乒乓球', '乒乓球', null, null, '2');
INSERT INTO `category` VALUES ('11', '6', 'KTV', '卡拉OK', null, null, '2');
INSERT INTO `category` VALUES ('12', '7', '饭堂', '学生饭堂', null, null, '2');

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------

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
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of image
-- ----------------------------

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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of map
-- ----------------------------

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
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of node
-- ----------------------------

-- ----------------------------
-- Table structure for `ofextcomponentconf`
-- ----------------------------
DROP TABLE IF EXISTS `ofextcomponentconf`;
CREATE TABLE `ofextcomponentconf` (
  `subdomain` varchar(255) NOT NULL,
  `wildcard` tinyint(4) NOT NULL,
  `secret` varchar(255) DEFAULT NULL,
  `permission` varchar(10) NOT NULL,
  PRIMARY KEY (`subdomain`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ofextcomponentconf
-- ----------------------------

-- ----------------------------
-- Table structure for `ofgroup`
-- ----------------------------
DROP TABLE IF EXISTS `ofgroup`;
CREATE TABLE `ofgroup` (
  `groupName` varchar(50) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`groupName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ofgroup
-- ----------------------------

-- ----------------------------
-- Table structure for `ofgroupprop`
-- ----------------------------
DROP TABLE IF EXISTS `ofgroupprop`;
CREATE TABLE `ofgroupprop` (
  `groupName` varchar(50) NOT NULL,
  `name` varchar(100) NOT NULL,
  `propValue` text NOT NULL,
  PRIMARY KEY (`groupName`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ofgroupprop
-- ----------------------------

-- ----------------------------
-- Table structure for `ofgroupuser`
-- ----------------------------
DROP TABLE IF EXISTS `ofgroupuser`;
CREATE TABLE `ofgroupuser` (
  `groupName` varchar(50) NOT NULL,
  `username` varchar(100) NOT NULL,
  `administrator` tinyint(4) NOT NULL,
  PRIMARY KEY (`groupName`,`username`,`administrator`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ofgroupuser
-- ----------------------------

-- ----------------------------
-- Table structure for `ofid`
-- ----------------------------
DROP TABLE IF EXISTS `ofid`;
CREATE TABLE `ofid` (
  `idType` int(11) NOT NULL,
  `id` bigint(20) NOT NULL,
  PRIMARY KEY (`idType`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ofid
-- ----------------------------
INSERT INTO `ofid` VALUES ('18', '1');
INSERT INTO `ofid` VALUES ('19', '28');
INSERT INTO `ofid` VALUES ('23', '1');
INSERT INTO `ofid` VALUES ('25', '9');
INSERT INTO `ofid` VALUES ('26', '2');

-- ----------------------------
-- Table structure for `ofmucaffiliation`
-- ----------------------------
DROP TABLE IF EXISTS `ofmucaffiliation`;
CREATE TABLE `ofmucaffiliation` (
  `roomID` bigint(20) NOT NULL,
  `jid` text NOT NULL,
  `affiliation` tinyint(4) NOT NULL,
  PRIMARY KEY (`roomID`,`jid`(70))
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ofmucaffiliation
-- ----------------------------

-- ----------------------------
-- Table structure for `ofmucconversationlog`
-- ----------------------------
DROP TABLE IF EXISTS `ofmucconversationlog`;
CREATE TABLE `ofmucconversationlog` (
  `roomID` bigint(20) NOT NULL,
  `sender` text NOT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `logTime` char(15) NOT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `body` text,
  KEY `ofMucConversationLog_time_idx` (`logTime`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ofmucconversationlog
-- ----------------------------

-- ----------------------------
-- Table structure for `ofmucmember`
-- ----------------------------
DROP TABLE IF EXISTS `ofmucmember`;
CREATE TABLE `ofmucmember` (
  `roomID` bigint(20) NOT NULL,
  `jid` text NOT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `firstName` varchar(100) DEFAULT NULL,
  `lastName` varchar(100) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `faqentry` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`roomID`,`jid`(70))
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ofmucmember
-- ----------------------------

-- ----------------------------
-- Table structure for `ofmucroom`
-- ----------------------------
DROP TABLE IF EXISTS `ofmucroom`;
CREATE TABLE `ofmucroom` (
  `serviceID` bigint(20) NOT NULL,
  `roomID` bigint(20) NOT NULL,
  `creationDate` char(15) NOT NULL,
  `modificationDate` char(15) NOT NULL,
  `name` varchar(50) NOT NULL,
  `naturalName` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `lockedDate` char(15) NOT NULL,
  `emptyDate` char(15) DEFAULT NULL,
  `canChangeSubject` tinyint(4) NOT NULL,
  `maxUsers` int(11) NOT NULL,
  `publicRoom` tinyint(4) NOT NULL,
  `moderated` tinyint(4) NOT NULL,
  `membersOnly` tinyint(4) NOT NULL,
  `canInvite` tinyint(4) NOT NULL,
  `roomPassword` varchar(50) DEFAULT NULL,
  `canDiscoverJID` tinyint(4) NOT NULL,
  `logEnabled` tinyint(4) NOT NULL,
  `subject` varchar(100) DEFAULT NULL,
  `rolesToBroadcast` tinyint(4) NOT NULL,
  `useReservedNick` tinyint(4) NOT NULL,
  `canChangeNick` tinyint(4) NOT NULL,
  `canRegister` tinyint(4) NOT NULL,
  PRIMARY KEY (`serviceID`,`name`),
  KEY `ofMucRoom_roomid_idx` (`roomID`),
  KEY `ofMucRoom_serviceid_idx` (`serviceID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ofmucroom
-- ----------------------------

-- ----------------------------
-- Table structure for `ofmucroomprop`
-- ----------------------------
DROP TABLE IF EXISTS `ofmucroomprop`;
CREATE TABLE `ofmucroomprop` (
  `roomID` bigint(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `propValue` text NOT NULL,
  PRIMARY KEY (`roomID`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ofmucroomprop
-- ----------------------------

-- ----------------------------
-- Table structure for `ofmucservice`
-- ----------------------------
DROP TABLE IF EXISTS `ofmucservice`;
CREATE TABLE `ofmucservice` (
  `serviceID` bigint(20) NOT NULL,
  `subdomain` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `isHidden` tinyint(4) NOT NULL,
  PRIMARY KEY (`subdomain`),
  KEY `ofMucService_serviceid_idx` (`serviceID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ofmucservice
-- ----------------------------
INSERT INTO `ofmucservice` VALUES ('1', 'conference', null, '0');

-- ----------------------------
-- Table structure for `ofmucserviceprop`
-- ----------------------------
DROP TABLE IF EXISTS `ofmucserviceprop`;
CREATE TABLE `ofmucserviceprop` (
  `serviceID` bigint(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `propValue` text NOT NULL,
  PRIMARY KEY (`serviceID`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ofmucserviceprop
-- ----------------------------

-- ----------------------------
-- Table structure for `ofoffline`
-- ----------------------------
DROP TABLE IF EXISTS `ofoffline`;
CREATE TABLE `ofoffline` (
  `username` varchar(64) NOT NULL,
  `messageID` bigint(20) NOT NULL,
  `creationDate` char(15) NOT NULL,
  `messageSize` int(11) NOT NULL,
  `stanza` text NOT NULL,
  PRIMARY KEY (`username`,`messageID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ofoffline
-- ----------------------------
INSERT INTO `ofoffline` VALUES ('admin', '15', '001399640732772', '97', '<message from=\"127.0.0.1\" to=\"admin@127.0.0.1\"><body>???????????: Openfire 3.9.3</body></message>');
INSERT INTO `ofoffline` VALUES ('test1', '18', '001401239723882', '131', '<message id=\"2XOKs-5\" to=\"test1@localhost\" from=\"lynn@localhost/Smack\" type=\"chat\"><body>hi</body><thread>l1q9h1</thread></message>');
INSERT INTO `ofoffline` VALUES ('test1', '19', '001401240679460', '131', '<message id=\"MU39K-7\" to=\"test1@localhost\" from=\"lynn@localhost/Smack\" type=\"chat\"><body>??</body><thread>CVPbm5</thread></message>');
INSERT INTO `ofoffline` VALUES ('test1', '20', '001401240683482', '141', '<message id=\"MU39K-8\" to=\"test1@localhost\" from=\"lynn@localhost/Smack\" type=\"chat\"><body>[??][??][??]</body><thread>CVPbm5</thread></message>');
INSERT INTO `ofoffline` VALUES ('test1', '22', '001401241305589', '133', '<message id=\"MU39K-22\" to=\"test1@localhost\" from=\"lynn@localhost/Smack\" type=\"chat\"><body>??</body><thread>CVPbm13</thread></message>');
INSERT INTO `ofoffline` VALUES ('test1', '23', '001401241321866', '55067', '<message id=\"MU39K-23\" to=\"test1@localhost\" from=\"lynn@localhost/Smack\" type=\"chat\"><body>[1]/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAMCAgMCAgMDAwMEAwMEBQgFBQQEBQoHBwYIDAoMDAsK\nCwsNDhIQDQ4RDgsLEBYQERMUFRUVDA8XGBYUGBIUFRT/2wBDAQMEBAUEBQkFBQkUDQsNFBQUFBQU\nFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBT/wAARCAJkAzADASIA\nAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQA\nAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3\nODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWm\np6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEA\nAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSEx\nBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElK\nU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3\nuLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD510vS\nRqugmOZQ3y45rwH4k+CpNA1F5UTELe31r6i0Wz+y6fGh9M4Nc94/8JR65pkoKDcV9K1aurHLzany\nIvI+npXY+FvF0tjE1s7ZRuMGuf1vSZNG1Ca3kGNpwPpVSJtpBHasdjW10emWFwrlm9TnkZxXsPwz\nvNxVGyPavAdB1HfGsbdfrXr3wz1ErdBc/WiPxGb7HtsmBaynrhe1fIHxquPM8TuCfujH419eXDY0\n6Rs/wng+lfHHxbl87xRPkjA4x+day0QQ3OI65xzz1qMqR0qSM4JyMU/AbnpWJuQZGOaah606RAAc\ncUkfSncdxcfnSrgAjgUgAYHNAAGRSJAGjt70v86b1zSAABil4xQOM80cHvn8aaAAC2ep+h6UuMUB\n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\nAxQOlFFAAOfzoHSiigABwDS9KKKAP//Z</body><thread>CVPbm13</thread></message>');
INSERT INTO `ofoffline` VALUES ('test1', '25', '001401241341789', '134', '<message id=\"MU39K-25\" to=\"test1@localhost\" from=\"lynn@localhost/Smack\" type=\"chat\"><body>111</body><thread>CVPbm13</thread></message>');
INSERT INTO `ofoffline` VALUES ('test1', '27', '001401241371599', '143', '<message id=\"MU39K-27\" to=\"test1@localhost\" from=\"lynn@localhost/Smack\" type=\"chat\"><body>[??][??][??]</body><thread>CVPbm13</thread></message>');
INSERT INTO `ofoffline` VALUES ('test2', '7', '001398599631753', '137', '<message id=\"vu1vc-5\" to=\"test2@localhost\" from=\"lynn@localhost/Smack\" type=\"chat\"><body>[??][??]</body><thread>F5AnW1</thread></message>');
INSERT INTO `ofoffline` VALUES ('wyy', '11', '001399444945485', '139', '<message id=\"khlx4-6\" to=\"wyy@localhost\" from=\"lynn@localhost/Smack\" type=\"chat\"><body>[??][??][??]</body><thread>OWjVK2</thread></message>');
INSERT INTO `ofoffline` VALUES ('wyy', '12', '001399517927124', '3120', '<message id=\"h2Br3-5\" to=\"wyy@localhost\" from=\"lynn@localhost/Smack\" type=\"chat\"><body>[2]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</body><thread>i30be1</thread></message>');
INSERT INTO `ofoffline` VALUES ('wyy', '13', '001399517931612', '139', '<message id=\"h2Br3-6\" to=\"wyy@localhost\" from=\"lynn@localhost/Smack\" type=\"chat\"><body>[??][??][??]</body><thread>i30be1</thread></message>');

-- ----------------------------
-- Table structure for `ofpresence`
-- ----------------------------
DROP TABLE IF EXISTS `ofpresence`;
CREATE TABLE `ofpresence` (
  `username` varchar(64) NOT NULL,
  `offlinePresence` text,
  `offlineDate` char(15) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ofpresence
-- ----------------------------
INSERT INTO `ofpresence` VALUES ('lynn', null, '001401241743453');
INSERT INTO `ofpresence` VALUES ('test3', null, '001399124959069');
INSERT INTO `ofpresence` VALUES ('zqx', null, '001401201159454');

-- ----------------------------
-- Table structure for `ofprivacylist`
-- ----------------------------
DROP TABLE IF EXISTS `ofprivacylist`;
CREATE TABLE `ofprivacylist` (
  `username` varchar(64) NOT NULL,
  `name` varchar(100) NOT NULL,
  `isDefault` tinyint(4) NOT NULL,
  `list` text NOT NULL,
  PRIMARY KEY (`username`,`name`),
  KEY `ofPrivacyList_default_idx` (`username`,`isDefault`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ofprivacylist
-- ----------------------------

-- ----------------------------
-- Table structure for `ofprivate`
-- ----------------------------
DROP TABLE IF EXISTS `ofprivate`;
CREATE TABLE `ofprivate` (
  `username` varchar(64) NOT NULL,
  `name` varchar(100) NOT NULL,
  `namespace` varchar(200) NOT NULL,
  `privateData` text NOT NULL,
  PRIMARY KEY (`username`,`name`,`namespace`(100))
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ofprivate
-- ----------------------------

-- ----------------------------
-- Table structure for `ofproperty`
-- ----------------------------
DROP TABLE IF EXISTS `ofproperty`;
CREATE TABLE `ofproperty` (
  `name` varchar(100) NOT NULL,
  `propValue` text NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ofproperty
-- ----------------------------
INSERT INTO `ofproperty` VALUES ('jdbcAuthProvider.passwordSQL', 'SELECT password FROM user WHERE username = ?');
INSERT INTO `ofproperty` VALUES ('jdbcAuthProvider.passwordType', 'plain');
INSERT INTO `ofproperty` VALUES ('jdbcAuthProvider.useConnectionProvider', 'true');
INSERT INTO `ofproperty` VALUES ('jdbcUserProvider.allUsersSQL', 'SELECT username FROM user');
INSERT INTO `ofproperty` VALUES ('jdbcUserProvider.emailField', 'email');
INSERT INTO `ofproperty` VALUES ('jdbcUserProvider.loadUserSQL', 'SELECT nickname, email FROM user WHERE username = ?');
INSERT INTO `ofproperty` VALUES ('jdbcUserProvider.nameField', 'nickname');
INSERT INTO `ofproperty` VALUES ('jdbcUserProvider.searchSQL', 'SELECT username FROM user WHERE');
INSERT INTO `ofproperty` VALUES ('jdbcUserProvider.useConnectionProvider', 'true');
INSERT INTO `ofproperty` VALUES ('jdbcUserProvider.userCountSQL', 'SELECT COUNT(*) FROM user');
INSERT INTO `ofproperty` VALUES ('jdbcUserProvider.usernameField', 'username');
INSERT INTO `ofproperty` VALUES ('passwordKey', 'reGTznU08HonlU2');
INSERT INTO `ofproperty` VALUES ('provider.admin.className', 'org.jivesoftware.openfire.admin.DefaultAdminProvider');
INSERT INTO `ofproperty` VALUES ('provider.auth.className', 'org.jivesoftware.openfire.auth.JDBCAuthProvider');
INSERT INTO `ofproperty` VALUES ('provider.group.className', 'org.jivesoftware.openfire.group.DefaultGroupProvider');
INSERT INTO `ofproperty` VALUES ('provider.lockout.className', 'org.jivesoftware.openfire.lockout.DefaultLockOutProvider');
INSERT INTO `ofproperty` VALUES ('provider.securityAudit.className', 'org.jivesoftware.openfire.security.DefaultSecurityAuditProvider');
INSERT INTO `ofproperty` VALUES ('provider.user.className', 'org.jivesoftware.openfire.user.JDBCUserProvider');
INSERT INTO `ofproperty` VALUES ('provider.vcard.className', 'org.jivesoftware.openfire.vcard.DefaultVCardProvider');
INSERT INTO `ofproperty` VALUES ('update.lastCheck', '1401071067664');
INSERT INTO `ofproperty` VALUES ('xmpp.auth.anonymous', 'true');
INSERT INTO `ofproperty` VALUES ('xmpp.domain', 'localhost');
INSERT INTO `ofproperty` VALUES ('xmpp.offline.quota', '2097152');
INSERT INTO `ofproperty` VALUES ('xmpp.offline.type', 'store_and_bounce');
INSERT INTO `ofproperty` VALUES ('xmpp.session.conflict-limit', '0');
INSERT INTO `ofproperty` VALUES ('xmpp.socket.ssl.active', 'true');

-- ----------------------------
-- Table structure for `ofpubsubaffiliation`
-- ----------------------------
DROP TABLE IF EXISTS `ofpubsubaffiliation`;
CREATE TABLE `ofpubsubaffiliation` (
  `serviceID` varchar(100) NOT NULL,
  `nodeID` varchar(100) NOT NULL,
  `jid` varchar(255) NOT NULL,
  `affiliation` varchar(10) NOT NULL,
  PRIMARY KEY (`serviceID`,`nodeID`,`jid`(70))
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ofpubsubaffiliation
-- ----------------------------
INSERT INTO `ofpubsubaffiliation` VALUES ('pubsub', '', 'localhost', 'owner');

-- ----------------------------
-- Table structure for `ofpubsubdefaultconf`
-- ----------------------------
DROP TABLE IF EXISTS `ofpubsubdefaultconf`;
CREATE TABLE `ofpubsubdefaultconf` (
  `serviceID` varchar(100) NOT NULL,
  `leaf` tinyint(4) NOT NULL,
  `deliverPayloads` tinyint(4) NOT NULL,
  `maxPayloadSize` int(11) NOT NULL,
  `persistItems` tinyint(4) NOT NULL,
  `maxItems` int(11) NOT NULL,
  `notifyConfigChanges` tinyint(4) NOT NULL,
  `notifyDelete` tinyint(4) NOT NULL,
  `notifyRetract` tinyint(4) NOT NULL,
  `presenceBased` tinyint(4) NOT NULL,
  `sendItemSubscribe` tinyint(4) NOT NULL,
  `publisherModel` varchar(15) NOT NULL,
  `subscriptionEnabled` tinyint(4) NOT NULL,
  `accessModel` varchar(10) NOT NULL,
  `language` varchar(255) DEFAULT NULL,
  `replyPolicy` varchar(15) DEFAULT NULL,
  `associationPolicy` varchar(15) NOT NULL,
  `maxLeafNodes` int(11) NOT NULL,
  PRIMARY KEY (`serviceID`,`leaf`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ofpubsubdefaultconf
-- ----------------------------
INSERT INTO `ofpubsubdefaultconf` VALUES ('pubsub', '0', '0', '0', '0', '0', '1', '1', '1', '0', '0', 'publishers', '1', 'open', 'English', null, 'all', '-1');
INSERT INTO `ofpubsubdefaultconf` VALUES ('pubsub', '1', '1', '5120', '0', '-1', '1', '1', '1', '0', '1', 'publishers', '1', 'open', 'English', null, 'all', '-1');

-- ----------------------------
-- Table structure for `ofpubsubitem`
-- ----------------------------
DROP TABLE IF EXISTS `ofpubsubitem`;
CREATE TABLE `ofpubsubitem` (
  `serviceID` varchar(100) NOT NULL,
  `nodeID` varchar(100) NOT NULL,
  `id` varchar(100) NOT NULL,
  `jid` varchar(255) NOT NULL,
  `creationDate` char(15) NOT NULL,
  `payload` mediumtext,
  PRIMARY KEY (`serviceID`,`nodeID`,`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ofpubsubitem
-- ----------------------------

-- ----------------------------
-- Table structure for `ofpubsubnode`
-- ----------------------------
DROP TABLE IF EXISTS `ofpubsubnode`;
CREATE TABLE `ofpubsubnode` (
  `serviceID` varchar(100) NOT NULL,
  `nodeID` varchar(100) NOT NULL,
  `leaf` tinyint(4) NOT NULL,
  `creationDate` char(15) NOT NULL,
  `modificationDate` char(15) NOT NULL,
  `parent` varchar(100) DEFAULT NULL,
  `deliverPayloads` tinyint(4) NOT NULL,
  `maxPayloadSize` int(11) DEFAULT NULL,
  `persistItems` tinyint(4) DEFAULT NULL,
  `maxItems` int(11) DEFAULT NULL,
  `notifyConfigChanges` tinyint(4) NOT NULL,
  `notifyDelete` tinyint(4) NOT NULL,
  `notifyRetract` tinyint(4) NOT NULL,
  `presenceBased` tinyint(4) NOT NULL,
  `sendItemSubscribe` tinyint(4) NOT NULL,
  `publisherModel` varchar(15) NOT NULL,
  `subscriptionEnabled` tinyint(4) NOT NULL,
  `configSubscription` tinyint(4) NOT NULL,
  `accessModel` varchar(10) NOT NULL,
  `payloadType` varchar(100) DEFAULT NULL,
  `bodyXSLT` varchar(100) DEFAULT NULL,
  `dataformXSLT` varchar(100) DEFAULT NULL,
  `creator` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `language` varchar(255) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `replyPolicy` varchar(15) DEFAULT NULL,
  `associationPolicy` varchar(15) DEFAULT NULL,
  `maxLeafNodes` int(11) DEFAULT NULL,
  PRIMARY KEY (`serviceID`,`nodeID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ofpubsubnode
-- ----------------------------
INSERT INTO `ofpubsubnode` VALUES ('pubsub', '', '0', '001398518469493', '001398518469493', null, '0', '0', '0', '0', '1', '1', '1', '0', '0', 'publishers', '1', '0', 'open', '', '', '', 'localhost', '', 'English', '', null, 'all', '-1');

-- ----------------------------
-- Table structure for `ofpubsubnodegroups`
-- ----------------------------
DROP TABLE IF EXISTS `ofpubsubnodegroups`;
CREATE TABLE `ofpubsubnodegroups` (
  `serviceID` varchar(100) NOT NULL,
  `nodeID` varchar(100) NOT NULL,
  `rosterGroup` varchar(100) NOT NULL,
  KEY `ofPubsubNodeGroups_idx` (`serviceID`,`nodeID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ofpubsubnodegroups
-- ----------------------------

-- ----------------------------
-- Table structure for `ofpubsubnodejids`
-- ----------------------------
DROP TABLE IF EXISTS `ofpubsubnodejids`;
CREATE TABLE `ofpubsubnodejids` (
  `serviceID` varchar(100) NOT NULL,
  `nodeID` varchar(100) NOT NULL,
  `jid` varchar(255) NOT NULL,
  `associationType` varchar(20) NOT NULL,
  PRIMARY KEY (`serviceID`,`nodeID`,`jid`(70))
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ofpubsubnodejids
-- ----------------------------

-- ----------------------------
-- Table structure for `ofpubsubsubscription`
-- ----------------------------
DROP TABLE IF EXISTS `ofpubsubsubscription`;
CREATE TABLE `ofpubsubsubscription` (
  `serviceID` varchar(100) NOT NULL,
  `nodeID` varchar(100) NOT NULL,
  `id` varchar(100) NOT NULL,
  `jid` varchar(255) NOT NULL,
  `owner` varchar(255) NOT NULL,
  `state` varchar(15) NOT NULL,
  `deliver` tinyint(4) NOT NULL,
  `digest` tinyint(4) NOT NULL,
  `digest_frequency` int(11) NOT NULL,
  `expire` char(15) DEFAULT NULL,
  `includeBody` tinyint(4) NOT NULL,
  `showValues` varchar(30) DEFAULT NULL,
  `subscriptionType` varchar(10) NOT NULL,
  `subscriptionDepth` tinyint(4) NOT NULL,
  `keyword` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`serviceID`,`nodeID`,`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ofpubsubsubscription
-- ----------------------------

-- ----------------------------
-- Table structure for `ofremoteserverconf`
-- ----------------------------
DROP TABLE IF EXISTS `ofremoteserverconf`;
CREATE TABLE `ofremoteserverconf` (
  `xmppDomain` varchar(255) NOT NULL,
  `remotePort` int(11) DEFAULT NULL,
  `permission` varchar(10) NOT NULL,
  PRIMARY KEY (`xmppDomain`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ofremoteserverconf
-- ----------------------------

-- ----------------------------
-- Table structure for `ofroster`
-- ----------------------------
DROP TABLE IF EXISTS `ofroster`;
CREATE TABLE `ofroster` (
  `rosterID` bigint(20) NOT NULL,
  `username` varchar(64) NOT NULL,
  `jid` varchar(1024) NOT NULL,
  `sub` tinyint(4) NOT NULL,
  `ask` tinyint(4) NOT NULL,
  `recv` tinyint(4) NOT NULL,
  `nick` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`rosterID`),
  KEY `ofRoster_unameid_idx` (`username`),
  KEY `ofRoster_jid_idx` (`jid`(255))
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ofroster
-- ----------------------------

-- ----------------------------
-- Table structure for `ofrostergroups`
-- ----------------------------
DROP TABLE IF EXISTS `ofrostergroups`;
CREATE TABLE `ofrostergroups` (
  `rosterID` bigint(20) NOT NULL,
  `rank` tinyint(4) NOT NULL,
  `groupName` varchar(255) NOT NULL,
  PRIMARY KEY (`rosterID`,`rank`),
  KEY `ofRosterGroup_rosterid_idx` (`rosterID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ofrostergroups
-- ----------------------------

-- ----------------------------
-- Table structure for `ofsaslauthorized`
-- ----------------------------
DROP TABLE IF EXISTS `ofsaslauthorized`;
CREATE TABLE `ofsaslauthorized` (
  `username` varchar(64) NOT NULL,
  `principal` text NOT NULL,
  PRIMARY KEY (`username`,`principal`(200))
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ofsaslauthorized
-- ----------------------------

-- ----------------------------
-- Table structure for `ofsecurityauditlog`
-- ----------------------------
DROP TABLE IF EXISTS `ofsecurityauditlog`;
CREATE TABLE `ofsecurityauditlog` (
  `msgID` bigint(20) NOT NULL,
  `username` varchar(64) NOT NULL,
  `entryStamp` bigint(20) NOT NULL,
  `summary` varchar(255) NOT NULL,
  `node` varchar(255) NOT NULL,
  `details` text,
  PRIMARY KEY (`msgID`),
  KEY `ofSecurityAuditLog_tstamp_idx` (`entryStamp`),
  KEY `ofSecurityAuditLog_uname_idx` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ofsecurityauditlog
-- ----------------------------
INSERT INTO `ofsecurityauditlog` VALUES ('1', 'admin', '1398519514555', 'set password for user admin', 'A-Lin', null);
INSERT INTO `ofsecurityauditlog` VALUES ('2', 'admin', '1398563886268', 'edited offline message settings', 'A-Lin', 'quote = 100.0\ntype = store');
INSERT INTO `ofsecurityauditlog` VALUES ('3', 'admin', '1398563928306', 'edited offline message settings', 'A-Lin', 'quote = 2048.0\ntype = store');
INSERT INTO `ofsecurityauditlog` VALUES ('4', 'admin', '1398567064953', 'edited offline message settings', 'A-Lin', 'quote = 100.0\ntype = store');
INSERT INTO `ofsecurityauditlog` VALUES ('5', 'admin', '1398567081931', 'updated stanza audit policy', 'A-Lin', null);
INSERT INTO `ofsecurityauditlog` VALUES ('6', 'admin', '1398567855182', 'created new user test1', 'A-Lin', 'name = null, email = null, admin = false');
INSERT INTO `ofsecurityauditlog` VALUES ('7', 'admin', '1398567875316', 'created new user lynn', 'A-Lin', 'name = null, email = null, admin = false');
INSERT INTO `ofsecurityauditlog` VALUES ('8', 'admin', '1401241220831', 'edited offline message settings', 'A-Lin', 'quote = 2048.0\ntype = store_and_bounce');

-- ----------------------------
-- Table structure for `ofuser`
-- ----------------------------
DROP TABLE IF EXISTS `ofuser`;
CREATE TABLE `ofuser` (
  `username` varchar(64) NOT NULL,
  `plainPassword` varchar(32) DEFAULT NULL,
  `encryptedPassword` varchar(255) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `creationDate` char(15) NOT NULL,
  `modificationDate` char(15) NOT NULL,
  PRIMARY KEY (`username`),
  KEY `ofUser_cDate_idx` (`creationDate`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ofuser
-- ----------------------------
INSERT INTO `ofuser` VALUES ('admin', null, 'e44d6a8ba084d67429bb3baab6d520c3da4285022370bc66', 'Administrator', 'admin@example.com', '001398518462195', '0');
INSERT INTO `ofuser` VALUES ('lynn', null, 'be57d0b5e11deb07f8a418e22fad1543cdb319292718f660', null, null, '001398567875207', '001398567875207');
INSERT INTO `ofuser` VALUES ('test1', null, '75e9d2acd560de57a1493ac57d8d382012228b212088acb7', null, null, '001398567855091', '001398567855091');

-- ----------------------------
-- Table structure for `ofuserflag`
-- ----------------------------
DROP TABLE IF EXISTS `ofuserflag`;
CREATE TABLE `ofuserflag` (
  `username` varchar(64) NOT NULL,
  `name` varchar(100) NOT NULL,
  `startTime` char(15) DEFAULT NULL,
  `endTime` char(15) DEFAULT NULL,
  PRIMARY KEY (`username`,`name`),
  KEY `ofUserFlag_sTime_idx` (`startTime`),
  KEY `ofUserFlag_eTime_idx` (`endTime`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ofuserflag
-- ----------------------------

-- ----------------------------
-- Table structure for `ofuserprop`
-- ----------------------------
DROP TABLE IF EXISTS `ofuserprop`;
CREATE TABLE `ofuserprop` (
  `username` varchar(64) NOT NULL,
  `name` varchar(100) NOT NULL,
  `propValue` text NOT NULL,
  PRIMARY KEY (`username`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ofuserprop
-- ----------------------------

-- ----------------------------
-- Table structure for `ofvcard`
-- ----------------------------
DROP TABLE IF EXISTS `ofvcard`;
CREATE TABLE `ofvcard` (
  `username` varchar(64) NOT NULL,
  `vcard` mediumtext NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ofvcard
-- ----------------------------

-- ----------------------------
-- Table structure for `ofversion`
-- ----------------------------
DROP TABLE IF EXISTS `ofversion`;
CREATE TABLE `ofversion` (
  `name` varchar(50) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ofversion
-- ----------------------------
INSERT INTO `ofversion` VALUES ('openfire', '21');

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
  `ahead_degree` int(11) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=227 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of position
-- ----------------------------

-- ----------------------------
-- Table structure for `position_neighbour`
-- ----------------------------
DROP TABLE IF EXISTS `position_neighbour`;
CREATE TABLE `position_neighbour` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `position_id` int(11) DEFAULT NULL,
  `neighbour_id` int(11) DEFAULT NULL,
  `distance` double DEFAULT NULL,
  `flag` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of position_neighbour
-- ----------------------------

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', null, 'lynn', '123456', 'upload/20140528092921.jpg', null, null, null, null, '我叫alin', 'Welcome to IPS!', null);
INSERT INTO `user` VALUES ('2', null, 'test1', '123456', null, null, null, null, null, '我叫test1', 'Hello world!', null);

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of user_relationship
-- ----------------------------
INSERT INTO `user_relationship` VALUES ('1', '1', '2', null, null, '1', null, null);
