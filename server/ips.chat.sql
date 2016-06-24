/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50612
Source Host           : localhost:3306
Source Database       : wifi_localization

Target Server Type    : MYSQL
Target Server Version : 50612
File Encoding         : 65001

Date: 2014-05-07 22:57:33
*/

SET FOREIGN_KEY_CHECKS=0;

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
INSERT INTO `ofid` VALUES ('19', '12');
INSERT INTO `ofid` VALUES ('23', '1');
INSERT INTO `ofid` VALUES ('25', '8');
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
INSERT INTO `ofoffline` VALUES ('test1', '8', '001399443959196', '131', '<message id=\"e8jH8-5\" to=\"test1@localhost\" from=\"lynn@localhost/Smack\" type=\"chat\"><body>hi</body><thread>1WJh51</thread></message>');
INSERT INTO `ofoffline` VALUES ('test1', '9', '001399444193556', '132', '<message id=\"Lj8cS-5\" to=\"test1@localhost\" from=\"lynn@localhost/Smack\" type=\"chat\"><body>wow</body><thread>bBA5h1</thread></message>');
INSERT INTO `ofoffline` VALUES ('test1', '10', '001399444862640', '131', '<message id=\"khlx4-5\" to=\"test1@localhost\" from=\"lynn@localhost/Smack\" type=\"chat\"><body>??</body><thread>OWjVK1</thread></message>');
INSERT INTO `ofoffline` VALUES ('test2', '7', '001398599631753', '137', '<message id=\"vu1vc-5\" to=\"test2@localhost\" from=\"lynn@localhost/Smack\" type=\"chat\"><body>[??][??]</body><thread>F5AnW1</thread></message>');
INSERT INTO `ofoffline` VALUES ('wyy', '11', '001399444945485', '139', '<message id=\"khlx4-6\" to=\"wyy@localhost\" from=\"lynn@localhost/Smack\" type=\"chat\"><body>[??][??][??]</body><thread>OWjVK2</thread></message>');

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
INSERT INTO `ofpresence` VALUES ('lynn', null, '001399467615084');
INSERT INTO `ofpresence` VALUES ('test3', null, '001399124959069');

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
INSERT INTO `ofproperty` VALUES ('update.lastCheck', '1399375814491');
INSERT INTO `ofproperty` VALUES ('xmpp.auth.anonymous', 'true');
INSERT INTO `ofproperty` VALUES ('xmpp.domain', 'localhost');
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
