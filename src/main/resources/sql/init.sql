-- CREATE DATABASE `njaucommunity` DEFAULT CHARACTER SET utf8;

USE `njaucommunity`;

SET FOREIGN_KEY_CHECKS =0;

/*创建用户表*/
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    `uid` int(11) NOT NULL AUTO_INCREMENT,
    `phonenumber` varchar(11) NOT NULL,
    `nickname` varchar(64) DEFAULT NULL ,
    `password` varchar(255) DEFAULT NULL,
    `isAdmin`  tinyint(1) default '0',
    `avatarurl`  varchar (255) DEFAULT NULL,
    PRIMARY KEY (`uid`,`phonenumber`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

INSERT INTO `user` VALUES('1','11111111111','','','0','');
INSERT INTO `user` VALUES('2','22222222222','测试2','','0','');

-- 关注的人
DROP TABLE IF EXISTS `following`;
CREATE TABLE `following`(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `uid` int(11) NOT NULL ,
    `followinguid` int(11) DEFAULT NULL ,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

INSERT INTO `following` VALUES('1','1','2');


-- 粉丝
DROP TABLE IF EXISTS `followed`;
CREATE TABLE `followed`(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `uid` int(11) NOT NULL ,
    `followeduid` int(11) DEFAULT NULL ,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

INSERT INTO `followed` VALUES('1','1','2');

DROP TABLE IF EXISTS `posters`;
CREATE TABLE `posters`(
    `pid` int(11) NOT NULL AUTO_INCREMENT,
    `uid` int(11) NOT NULL,
    `clickcount` int(11) DEFAULT 0,
    `likecount`  int(11) DEFAULT 0,
    `commentcount` int(11) DEFAULT 0,
    `content` varchar(400) DEFAULT NULL ,
    `datetime` datetime DEFAULT NULL,
    PRIMARY KEY (`pid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

INSERT INTO `posters` VALUES('1','1','0','0','0','这是一条测试的动态',NOW());

DROP TABLE IF EXISTS `likes`;
CREATE TABLE `likes`(
    `id`  int(11) NOT NULL AUTO_INCREMENT,
    `pid` int(11) NOT NULL,
    `uid` int(11) DEFAULT  NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

INSERT INTO `likes` VALUES('1','1','2');


DROP TABLE IF EXISTS `postersImage`;
CREATE TABLE `postersImage`(
    `id`  int(11) NOT NULL AUTO_INCREMENT,
    `pid` int(11) NOT NULL,
    `imageURL` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

INSERT INTO `postersImage` VALUES('1','1','cloud://cloud1-9gzmjfae73c670a2.636c-cloud1-9gzmjfae73c670a2-1305631524/Corporate_Sunrise.png');

DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments`(
    `cid`  int(11) NOT NULL AUTO_INCREMENT,
    `pid` int(11) NOT NULL,
    `uid` int(11) NOT NULL,
    `content` varchar(255) DEFAULT NULL,
    `createtime` datetime DEFAULT NULL,
    `replyid` int(11) NOT NULL,
    PRIMARY KEY (`cid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

    INSERT INTO `comments` VALUES('1','33','1','comment测试','2021-04-22 16:09:20','-1');
SET FOREIGN_KEY_CHECKS = 1;