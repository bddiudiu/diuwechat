--
-- ��Ľṹ `message`
--

CREATE TABLE IF NOT EXISTS `message` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tousername` char(32) NOT NULL,
  `fromusername` char(32) NOT NULL,
  `msgtype` enum('TEXT','IMAGE','LOCATION','LINK','EVENT') NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `msgId` char(64) NOT NULL,
  `content` varchar(128) DEFAULT NULL,
  `picurl` varchar(128) DEFAULT NULL,
  `title` varchar(32) DEFAULT NULL,
  `description` varchar(128) DEFAULT NULL,
  `url` varchar(128) DEFAULT NULL,
  `locationx` char(10) DEFAULT NULL,
  `locationy` char(10) DEFAULT NULL,
  `scale` char(8) DEFAULT NULL,
  `label` varchar(32) DEFAULT NULL,
  `event` varchar(16) DEFAULT NULL,
  `eventkey` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_user` (`msgtype`,`tousername`,`fromusername`,`createtime`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;


-- --------------------------------------------------------

--
-- ��Ľṹ `reply`
--

CREATE TABLE IF NOT EXISTS `reply` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tousername` char(32) NOT NULL,
  `fromusername` char(32) NOT NULL,
  `msgtype` enum('TEXT','MUSIC','NEWS') NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `content` varchar(512) NOT NULL COMMENT '�ظ���Ϣ����',
  `musicurl` varchar(128) DEFAULT NULL,
  `hqmusicurl` varchar(128) DEFAULT NULL,
  `articlecount` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_user` (`msgtype`,`tousername`,`fromusername`,`createtime`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- ��Ľṹ `reply_article`
--
CREATE TABLE IF NOT EXISTS `reply_article` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `replyid` int(10) unsigned NOT NULL,
  `title` varchar(32) NOT NULL,
  `description` varchar(64) NOT NULL,
  `picurl` varchar(128) NOT NULL,
  `url` varchar(128) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_replyid` (`replyid`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;



--
-- ��Ľṹ `bills`
--

CREATE TABLE IF NOT EXISTS `bills` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `orderid` varchar(36) NOT NULL,
  `goods` varchar(2000) NOT NULL,
  `tel` varchar(128) ,
  `name` varchar(512) ,
  `date` varchar(128) ,
  `ordertime` varchar(128) ,
  PRIMARY KEY (`id`),
  KEY `index_user` (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- ��Ľṹ `call`
--

CREATE TABLE IF NOT EXISTS `backcontent` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `content` varchar(4000) NOT NULL,
  `type` varchar(128) ,
  `state` varchar(512) ,
  `pic` varchar(128) ,
  `picurl` varchar(128) ,
  PRIMARY KEY (`id`),
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;