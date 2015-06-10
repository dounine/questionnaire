-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.1.48-community - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win32
-- HeidiSQL 版本:                  8.3.0.4694
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 dounine 的数据库结构
DROP DATABASE IF EXISTS `dounine`;
CREATE DATABASE IF NOT EXISTS `dounine` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `dounine`;


-- 导出  表 dounine.answer 结构
DROP TABLE IF EXISTS `answer`;
CREATE TABLE IF NOT EXISTS `answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `answer` varchar(50) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- 正在导出表  dounine.answer 的数据：7 rows
/*!40000 ALTER TABLE `answer` DISABLE KEYS */;
INSERT INTO `answer` (`id`, `answer`) VALUES
	(1, 'A'),
	(2, 'B'),
	(3, 'C'),
	(4, 'D'),
	(5, 'E'),
	(6, 'F'),
	(7, 'O');
/*!40000 ALTER TABLE `answer` ENABLE KEYS */;


-- 导出  过程 dounine.createChildLst 结构
DROP PROCEDURE IF EXISTS `createChildLst`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `createChildLst`(IN rootId INT,IN nDepth INT)
BEGIN  
      DECLARE done INT DEFAULT 0;  
      DECLARE b INT;  
      DECLARE cur1 CURSOR FOR SELECT id FROM sys_organization where pid=rootId;  
      DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;  
      
      insert into tmpLst values (null,rootId,nDepth);  
  
      
      OPEN cur1;  
      
      FETCH cur1 INTO b;  
      WHILE done=0 DO  
              CALL createChildLst(b,nDepth+1);  
              FETCH cur1 INTO b;  
      END WHILE;  
      
      CLOSE cur1;  
     END//
DELIMITER ;


-- 导出  表 dounine.exam_ask_list 结构
DROP TABLE IF EXISTS `exam_ask_list`;
CREATE TABLE IF NOT EXISTS `exam_ask_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `examuserlistid` int(11) NOT NULL,
  `subjectid` int(11) NOT NULL,
  `answer` varchar(10000) DEFAULT NULL,
  `text` varchar(10000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=587 DEFAULT CHARSET=utf8 COMMENT='考试题目答案表';

-- 正在导出表  dounine.exam_ask_list 的数据：49 rows
/*!40000 ALTER TABLE `exam_ask_list` DISABLE KEYS */;
INSERT INTO `exam_ask_list` (`id`, `examuserlistid`, `subjectid`, `answer`, `text`) VALUES
	(546, 106, 215, 'O', 'fffffffff'),
	(545, 106, 212, 'O', 'asdfasdfasdf'),
	(544, 106, 214, 'E', NULL),
	(543, 106, 214, 'D', NULL),
	(542, 106, 214, 'C', NULL),
	(541, 106, 213, 'F', NULL),
	(538, 106, 210, 'D', NULL),
	(539, 106, 211, 'D', NULL),
	(540, 106, 211, 'E', NULL),
	(547, 107, 210, 'B', NULL),
	(548, 107, 211, 'D', NULL),
	(549, 107, 211, 'E', NULL),
	(550, 107, 213, 'F', NULL),
	(551, 107, 214, 'E', NULL),
	(552, 107, 214, 'F', NULL),
	(553, 107, 215, 'E', NULL),
	(554, 107, 212, 'O', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa'),
	(555, 108, 210, 'B', NULL),
	(556, 108, 211, 'D', NULL),
	(557, 108, 211, 'E', NULL),
	(558, 108, 212, 'F', NULL),
	(559, 108, 213, 'F', NULL),
	(560, 108, 214, 'C', NULL),
	(561, 108, 214, 'E', NULL),
	(562, 108, 215, 'D', NULL),
	(563, 109, 210, 'B', NULL),
	(564, 109, 211, 'E', NULL),
	(565, 109, 212, 'D', NULL),
	(566, 109, 213, 'D', NULL),
	(567, 109, 214, 'D', NULL),
	(568, 109, 215, 'D', NULL),
	(569, 109, 242, 'O', '666666'),
	(570, 110, 210, 'A', NULL),
	(571, 110, 211, 'E', NULL),
	(572, 110, 211, 'F', NULL),
	(573, 110, 212, 'F', NULL),
	(574, 110, 213, 'E', NULL),
	(575, 110, 214, 'D', NULL),
	(576, 110, 215, 'D', NULL),
	(577, 110, 242, 'O', 'aaaaaaaaaee大'),
	(578, 111, 237, 'B', NULL),
	(579, 111, 237, 'C', NULL),
	(580, 111, 238, 'C', NULL),
	(581, 111, 238, 'E', NULL),
	(582, 111, 239, 'B', NULL),
	(583, 111, 239, 'C', NULL),
	(584, 111, 240, 'C', NULL),
	(585, 111, 240, 'E', NULL),
	(586, 111, 241, 'C', NULL);
/*!40000 ALTER TABLE `exam_ask_list` ENABLE KEYS */;


-- 导出  表 dounine.exam_user_list 结构
DROP TABLE IF EXISTS `exam_user_list`;
CREATE TABLE IF NOT EXISTS `exam_user_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `post` int(11) NOT NULL DEFAULT '1',
  `paperid` int(11) NOT NULL,
  `ishidden` int(11) NOT NULL DEFAULT '0',
  `sex` int(11) NOT NULL DEFAULT '0' COMMENT '性别',
  `age` int(11) NOT NULL DEFAULT '0' COMMENT '年龄',
  `educa` int(11) NOT NULL DEFAULT '0' COMMENT '教育',
  `divisionage` int(11) NOT NULL DEFAULT '0' COMMENT '司龄',
  `postlevel` int(11) NOT NULL DEFAULT '0' COMMENT '职级',
  `askdate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` int(11) NOT NULL COMMENT '部门',
  `agescope` int(11) NOT NULL COMMENT '年龄段',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=112 DEFAULT CHARSET=utf8 COMMENT='考试记录表';

-- 正在导出表  dounine.exam_user_list 的数据：6 rows
/*!40000 ALTER TABLE `exam_user_list` DISABLE KEYS */;
INSERT INTO `exam_user_list` (`id`, `userid`, `post`, `paperid`, `ishidden`, `sex`, `age`, `educa`, `divisionage`, `postlevel`, `askdate`, `orgid`, `agescope`) VALUES
	(106, 20, 15, 19, 0, 1, 23, 10, 13, 19, '2014-07-09 23:40:10', 1, 26),
	(107, 5, 1, 19, 1, 0, 30, 10, 20, 18, '2014-07-14 19:23:25', 8, 27),
	(108, 5, 1, 19, 1, 0, 23, 10, 14, 19, '2014-07-15 00:24:58', 7, 26),
	(109, 5, 1, 19, 1, 0, 30, 125, 13, 127, '2014-07-15 18:36:17', 45, 27),
	(110, 5, 1, 19, 1, 0, 30, 9, 12, 127, '2014-07-15 21:23:39', 22, 27),
	(111, 5, 1, 11, 1, 0, 30, 9, 13, 19, '2014-07-15 22:23:14', 17, 27);
/*!40000 ALTER TABLE `exam_user_list` ENABLE KEYS */;


-- 导出  函数 dounine.getChildLst 结构
DROP FUNCTION IF EXISTS `getChildLst`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` FUNCTION `getChildLst`(rootId INT) RETURNS varchar(1000) CHARSET latin1
BEGIN  
       DECLARE sTemp VARCHAR(1000);  
       DECLARE sTempChd VARCHAR(1000);  
      
       SET sTemp = '$';  
       SET sTempChd =cast(rootId as CHAR);  
      
       WHILE sTempChd is not null DO  
         SET sTemp = concat(sTemp,',',sTempChd);  
         SELECT group_concat(id) INTO sTempChd FROM `sys_organization` where FIND_IN_SET(pid,sTempChd)>0;  
       END WHILE;  
       RETURN sTemp;  
     END//
DELIMITER ;


-- 导出  函数 dounine.getcomname 结构
DROP FUNCTION IF EXISTS `getcomname`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` FUNCTION `getcomname`(curdepartname NVARCHAR(512)) RETURNS varchar(512) CHARSET utf8
BEGIN
declare temp_id int;
declare temp_pid int;
declare temp_departname NVARCHAR(512);
declare temp_curname NVARCHAR(512);
set temp_curname = curdepartname;
         
     	select  t_out.pid as ttemp_pid,
          (select t_in.name from sys_organization as t_in where t_in.id=t_out.pid ) as ttemp_departname
          into temp_pid,temp_departname
        from sys_organization as t_out where t_out.name=temp_curname;        
       set   temp_curname = temp_departname;    
   RETURN temp_departname;
END//
DELIMITER ;


-- 导出  函数 dounine.getcompanyname 结构
DROP FUNCTION IF EXISTS `getcompanyname`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` FUNCTION `getcompanyname`(curdepartname NVARCHAR(512)) RETURNS varchar(512) CHARSET utf8
BEGIN
declare temp_id int;
declare temp_pid int;
declare temp_departname NVARCHAR(512);
declare temp_curname NVARCHAR(512);
set temp_departname = curdepartname;

    REPEAT   
    	set   temp_curname = temp_departname;    
     	select  t_out.pid as ttemp_pid,
          (select t_in.name from sys_organization as t_in where t_in.id=t_out.pid ) as ttemp_departname
          into temp_pid,temp_departname
        from sys_organization as t_out where t_out.name=temp_curname;
        
       
       
    UNTIL temp_pid is null or temp_pid=0 END REPEAT;  
  
   RETURN temp_curname;
END//
DELIMITER ;


-- 导出  函数 dounine.getcompanysysname 结构
DROP FUNCTION IF EXISTS `getcompanysysname`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` FUNCTION `getcompanysysname`(
        curdepartname NVARCHAR(512)
    ) RETURNS varchar(512) CHARSET utf8
BEGIN
declare temp_id int;
declare temp_pid int;
declare temp_out_pid int;
declare temp_departname NVARCHAR(512);
declare temp_curname NVARCHAR(512);
set temp_departname = curdepartname;
    REPEAT   
    	set   temp_curname = temp_departname;       
     	select  t_out.pid ,
          (select t_in.name from sys_organization as t_in where t_in.id=t_out.pid )
          into temp_pid,temp_departname
        from sys_organization as t_out where t_out.name=temp_curname;
        
        if temp_pid > 0  then
        	select  sys_organization.pid into temp_out_pid 
          	from sys_organization where sys_organization.id=temp_pid;    
        else
            set temp_curname='公司';
        end if;     
    UNTIL temp_out_pid is null or temp_out_pid=0 END REPEAT;  
   RETURN temp_curname;
END//
DELIMITER ;


-- 导出  表 dounine.paper_mng 结构
DROP TABLE IF EXISTS `paper_mng`;
CREATE TABLE IF NOT EXISTS `paper_mng` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `icon` varchar(32) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  `seq` int(11) NOT NULL DEFAULT '0',
  `limitdate` int(11) NOT NULL DEFAULT '60',
  `faildate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `createdatetime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `description` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='问卷管理';

-- 正在导出表  dounine.paper_mng 的数据：8 rows
/*!40000 ALTER TABLE `paper_mng` DISABLE KEYS */;
INSERT INTO `paper_mng` (`id`, `name`, `icon`, `pid`, `seq`, `limitdate`, `faildate`, `createdatetime`, `description`) VALUES
	(13, '合同调查', 'icon_folder', 10, 0, 60, '2014-06-28 23:55:56', '2014-05-26 00:02:58', '这是人力资源部合同调查问卷'),
	(10, '人力资源部', 'icon_folder', NULL, 0, 60, '2014-07-05 23:22:53', '2014-05-26 00:02:47', ''),
	(11, '培训调查', 'icon_folder', 10, 1, 60, '2014-07-29 12:45:01', '2014-07-15 22:22:34', '这是人力资源部新员工培训调查问卷'),
	(14, '考核调查', 'icon_folder', 10, 0, 1, '2014-07-04 23:57:38', '2014-05-26 00:03:19', '这是人力资源部考核问卷'),
	(15, '伙食调查问卷', 'icon_folder', NULL, 0, 60, '2014-07-05 23:37:42', '2014-05-26 00:03:34', '这是公司食堂伙食调查问卷'),
	(17, '谁最伤', 'icon_folder', NULL, 0, 60, '2014-06-30 11:10:22', '2014-06-29 11:10:31', 'asdf'),
	(18, 'sadfasfdyy试卷1', 'icon_folder', NULL, 0, 60, '2014-07-03 11:18:04', '2014-06-29 11:18:13', '恭恭敬敬恭恭敬敬工    '),
	(19, '测试', 'icon_folder', NULL, 0, 60, '2014-07-31 20:32:28', '2014-07-09 22:37:12', 'asdf');
/*!40000 ALTER TABLE `paper_mng` ENABLE KEYS */;


-- 导出  表 dounine.paper_user_rel 结构
DROP TABLE IF EXISTS `paper_user_rel`;
CREATE TABLE IF NOT EXISTS `paper_user_rel` (
  `paperid` int(11) NOT NULL,
  `userid` int(11) NOT NULL,
  PRIMARY KEY (`paperid`,`userid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='问卷人员关系';

-- 正在导出表  dounine.paper_user_rel 的数据：16 rows
/*!40000 ALTER TABLE `paper_user_rel` DISABLE KEYS */;
INSERT INTO `paper_user_rel` (`paperid`, `userid`) VALUES
	(11, 5),
	(11, 15),
	(11, 54),
	(15, 5),
	(17, 1),
	(17, 5),
	(18, 1),
	(18, 5),
	(19, 1),
	(19, 5),
	(19, 14),
	(19, 15),
	(19, 18),
	(19, 19),
	(19, 20),
	(19, 21);
/*!40000 ALTER TABLE `paper_user_rel` ENABLE KEYS */;


-- 导出  函数 dounine.queryChildrenAreaInfo 结构
DROP FUNCTION IF EXISTS `queryChildrenAreaInfo`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` FUNCTION `queryChildrenAreaInfo`(areaId INT) RETURNS varchar(4000) CHARSET latin1
BEGIN
	DECLARE sTemp VARCHAR(4000);
	DECLARE sTempChd VARCHAR(4000);
	SET sTemp = "$";
	SET sTempChd = cast(areaId as char);
	WHILE sTempChd is not NULL DO
	SET sTemp = CONCAT(sTemp,",",sTempChd);
	SELECT group_concat(id) INTO sTempChd FROM t_areainfo where FIND_IN_SET(parentId,sTempChd)>0;
	END WHILE;
	return sTemp;
END//
DELIMITER ;


-- 导出  函数 dounine.queryChildrenAreaInfo2 结构
DROP FUNCTION IF EXISTS `queryChildrenAreaInfo2`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` FUNCTION `queryChildrenAreaInfo2`(NAME1 varchar(64)) RETURNS varchar(4000) CHARSET latin1
BEGIN
	DECLARE sTemp VARCHAR(4000);
	DECLARE sTempChd VARCHAR(4000);
	SET sTemp = "$";
	SET sTempChd = cast(NAME1 as char);
	WHILE sTempChd is not NULL DO
		SET sTemp = CONCAT(sTemp,",",sTempChd);
		SELECT group_concat(name) INTO sTempChd FROM `sys_organization` where FIND_IN_SET(NAME,sTempChd)>0;
	END WHILE;
	return sTemp;
END//
DELIMITER ;


-- 导出  过程 dounine.showChildLst 结构
DROP PROCEDURE IF EXISTS `showChildLst`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `showChildLst`(IN rootId INT)
BEGIN  
      CREATE TEMPORARY TABLE IF NOT EXISTS tmpLst   
       (sno int primary key auto_increment,id int,depth int);  
      DELETE FROM tmpLst;  
      
      CALL createChildLst(rootId,0);  
      
      select tmpLst.*,`sys_organization`.* from tmpLst,sys_organization where tmpLst.id=sys_organization.id order by tmpLst.sno;  
     END//
DELIMITER ;


-- 导出  表 dounine.subject_mng 结构
DROP TABLE IF EXISTS `subject_mng`;
CREATE TABLE IF NOT EXISTS `subject_mng` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(512) NOT NULL,
  `type` int(11) NOT NULL DEFAULT '0',
  `description` varchar(512) DEFAULT NULL,
  `paperid` int(11) NOT NULL,
  `answerA` varchar(512) DEFAULT NULL,
  `answerB` varchar(512) DEFAULT NULL,
  `answerC` varchar(512) DEFAULT NULL,
  `answerD` varchar(512) DEFAULT NULL,
  `answerE` varchar(512) DEFAULT NULL,
  `answerF` varchar(512) DEFAULT NULL,
  `questiontype` varchar(512) DEFAULT NULL,
  `kind` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=243 DEFAULT CHARSET=utf8 COMMENT='题目答案管理';

-- 正在导出表  dounine.subject_mng 的数据：12 rows
/*!40000 ALTER TABLE `subject_mng` DISABLE KEYS */;
INSERT INTO `subject_mng` (`id`, `name`, `type`, `description`, `paperid`, `answerA`, `answerB`, `answerC`, `answerD`, `answerE`, `answerF`, `questiontype`, `kind`) VALUES
	(210, 'impqF16', 0, NULL, 19, '0', '0', '0', '0', '0', '0', '人力问题', '单选'),
	(211, 'impqF17', 0, NULL, 19, '0', '2', '3', '0', '0', '0', '人力问题', '多选'),
	(212, 'impqF18', 0, NULL, 19, '0', '0', '0', '0', '0', '0', NULL, '文本'),
	(213, 'impqF19', 0, NULL, 19, '0', '0', '0', '0', '0', '0', '人力问题', '单选'),
	(214, 'impqF20', 0, NULL, 19, '0', '2', '3', '0', '0', '0', '人力问题', '多选'),
	(215, 'impqF21', 0, NULL, 19, '0', '0', '0', '0', '0', '0', NULL, '文本'),
	(237, '在日常工作中，您经常需要撰写哪些类型的公文？', 0, '', 11, '请示', '报告', '函', '会议记录与大事记', '批复', '决定、指示、意见', ' ', '多选'),
	(238, '在日常工作中，您经常需要撰写哪些类型的公文？', 0, '', 11, '通知、通报、通告', '调研报告（可行性研究报告）', '计划', '商务电子邮件的基本规范与礼仪', '讲话稿（开幕词、闭幕词、欢迎词、欢送词、答谢词）', '简报', '', '多选'),
	(239, '您再撰写上述公文时，所遇到最大的挑战是？', 0, '', 11, '构思思路', '行文结构', '正文内容', '格式排版', '遣词造句', '其他', '', '多选'),
	(240, '如果参加公文写作的培训课程，您最希望收获什么？', 0, '', 11, '不同类型公文的写作思路与思维模式', '不同类型公文的内容与语言要求', '避免公文写作中容易出现的错误', '不同类型公文的结构与框架', '不同类型公文的格式', '其他', '', '多选'),
	(241, '您更倾向于下列哪种方式将培训所学的知识应用于实际工作当中？', 0, '', 11, '培训后提供知识点以便学习', '让上司了解我的学习内容并督促、检查我应用', '形成&ldquo;边做边学&rdquo;的学习模式', '其他', '', '', '', '多选'),
	(242, 'imprt001', 0, '', 19, 'a', 'b', 'c', '', '', '', '', '文本');
/*!40000 ALTER TABLE `subject_mng` ENABLE KEYS */;


-- 导出  表 dounine.sys_dictionary 结构
DROP TABLE IF EXISTS `sys_dictionary`;
CREATE TABLE IF NOT EXISTS `sys_dictionary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(64) NOT NULL,
  `text` varchar(64) NOT NULL,
  `dictionarytype_id` int(11) NOT NULL,
  `seq` int(11) NOT NULL DEFAULT '0',
  `state` int(11) NOT NULL DEFAULT '0',
  `isdefault` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=136 DEFAULT CHARSET=utf8 COMMENT='数据字典';

-- 正在导出表  dounine.sys_dictionary 的数据：35 rows
/*!40000 ALTER TABLE `sys_dictionary` DISABLE KEYS */;
INSERT INTO `sys_dictionary` (`id`, `code`, `text`, `dictionarytype_id`, `seq`, `state`, `isdefault`) VALUES
	(1, '0', '管理员', 2, 0, 0, 0),
	(9, '0', '本科', 4, 2, 0, 1),
	(10, '1', '硕士', 4, 3, 0, 1),
	(11, '2', '博士', 4, 4, 0, 1),
	(12, '0', '1年以下', 5, 0, 0, 1),
	(13, '1', '1年~3年', 5, 1, 0, 1),
	(14, '2', '4年~6年', 5, 2, 0, 1),
	(15, '0', '行政档案主管', 6, 0, 1, 1),
	(16, '1', '总经办主任', 6, 1, 1, 1),
	(17, '2', '传媒专员', 6, 2, 1, 1),
	(18, '0', '员工', 7, 0, 0, 1),
	(19, '1', '主管', 7, 1, 0, 1),
	(24, '5', '13年以上', 5, 5, 0, 1),
	(21, '3', '文化传媒主管', 6, 3, 1, 1),
	(22, '4', '制度管理主管', 6, 4, 1, 1),
	(20, '3', '7年~9年', 5, 3, 0, 1),
	(23, '4', '10年~12年', 5, 4, 0, 1),
	(25, '0', '18~22', 9, 0, 0, 1),
	(26, '1', '23~27', 9, 1, 0, 1),
	(27, '2', '28~32', 9, 2, 0, 1),
	(28, '3', '33~37', 9, 3, 0, 1),
	(29, '4', '38~42', 9, 4, 0, 1),
	(30, '5', '43~47', 9, 5, 0, 1),
	(31, '6', '48以上', 9, 6, 0, 1),
	(125, '3', '大专', 4, 1, 0, 1),
	(126, '4', '中专及以下', 4, 0, 0, 1),
	(127, '2', '副部长/部长', 7, 2, 0, 1),
	(128, '3', '部长', 7, 3, 1, 1),
	(129, '4', '副总监/总监', 7, 4, 0, 1),
	(130, '5', '高管', 7, 5, 0, 1),
	(131, '5', '销售人员', 6, 5, 0, 1),
	(132, '6', '生产操作人员', 6, 6, 0, 1),
	(133, '7', '文职人员', 6, 7, 0, 1),
	(134, '8', '管理人员', 6, 8, 0, 1),
	(135, '9', '科研人员', 6, 9, 0, 1);
/*!40000 ALTER TABLE `sys_dictionary` ENABLE KEYS */;


-- 导出  表 dounine.sys_dictionarytype 结构
DROP TABLE IF EXISTS `sys_dictionarytype`;
CREATE TABLE IF NOT EXISTS `sys_dictionarytype` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(64) NOT NULL,
  `name` varchar(64) NOT NULL,
  `seq` int(11) NOT NULL DEFAULT '0',
  `description` varchar(255) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='字典分类';

-- 正在导出表  dounine.sys_dictionarytype 的数据：6 rows
/*!40000 ALTER TABLE `sys_dictionarytype` DISABLE KEYS */;
INSERT INTO `sys_dictionarytype` (`id`, `code`, `name`, `seq`, `description`, `pid`) VALUES
	(2, 'usertype', '用户类型', 0, '用户类型', 1),
	(4, 'educa', '学历', 0, '学历', 1),
	(5, 'divisionage', '司龄', 0, '司龄', 1),
	(6, 'post', '职位', 0, '职位', 1),
	(7, 'postlevel', '职级', 0, '职级', 1),
	(9, 'agescope', '年龄段', 0, '年龄段', 1);
/*!40000 ALTER TABLE `sys_dictionarytype` ENABLE KEYS */;


-- 导出  表 dounine.sys_organization 结构
DROP TABLE IF EXISTS `sys_organization`;
CREATE TABLE IF NOT EXISTS `sys_organization` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `address` varchar(100) DEFAULT NULL,
  `code` varchar(64) NOT NULL,
  `icon` varchar(32) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  `seq` int(11) NOT NULL DEFAULT '0',
  `percent` int(4) DEFAULT '50',
  `createdatetime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `deptotalnum` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=54 DEFAULT CHARSET=utf8 COMMENT='组织机构';

-- 正在导出表  dounine.sys_organization 的数据：41 rows
/*!40000 ALTER TABLE `sys_organization` DISABLE KEYS */;
INSERT INTO `sys_organization` (`id`, `name`, `address`, `code`, `icon`, `pid`, `seq`, `percent`, `createdatetime`, `deptotalnum`) VALUES
	(45, '生产保障部', '', '34', 'icon_folder', 27, 0, 50, '2014-07-15 14:49:03', 0),
	(46, '物资管理部', '', '35', 'icon_folder', 27, 0, 50, '2014-07-15 14:54:27', 0),
	(1, '天津红日药业股份有限公司', '', '1', 'icon_company', NULL, 1, 50, '2014-07-15 14:21:51', 1000),
	(13, '总裁办', '', '2', 'icon_folder', 25, 0, 50, '2014-07-15 14:29:29', 20),
	(14, '投资部', '', '3', 'icon_folder', 25, 1, 50, '2014-07-15 14:31:48', 0),
	(15, '高管', '', '4', 'icon_folder', 1, 2, 50, '2014-07-15 14:32:36', 0),
	(16, '战略研究室', '', '5', 'icon_folder', 25, 3, 50, '2014-07-15 14:33:07', 0),
	(17, '证券部', '', '6', 'icon_folder', 25, 4, 50, '2014-07-15 14:33:28', 0),
	(18, '财务部', '', '7', 'icon_folder', 25, 5, 50, '2014-07-15 14:33:49', 0),
	(19, '内审监察部', '', '8', 'icon_folder', 25, 6, 50, '2014-07-15 14:34:34', 0),
	(20, '行政管理部', '', '9', 'icon_folder', 25, 0, 50, '2014-07-15 14:34:51', 0),
	(21, '药事部', '', '10', 'icon_folder', 25, 0, 50, '2014-07-15 14:35:14', 0),
	(22, '药物警戒部', '', '11', 'icon_folder', 25, 0, 50, '2014-07-15 14:35:55', 0),
	(23, '品牌事务部', '', '12', 'icon_folder', 25, 0, 50, '2014-07-15 14:36:11', 0),
	(24, '人力资源部', '', '13', 'icon_folder', 25, 0, 50, '2014-07-15 14:36:24', 0),
	(25, '支持系统', '', '14', 'icon_folder', 1, 0, 50, '2014-07-15 14:38:15', 0),
	(26, '研发系统', '', '15', 'icon_folder', 1, 0, 50, '2014-07-15 14:38:30', 0),
	(27, '生产系统', '', '16', 'icon_folder', 1, 0, 50, '2014-07-15 14:38:48', 0),
	(28, '销售系统', '', '17', 'icon_folder', 1, 0, 50, '2014-07-15 14:39:03', 0),
	(29, '临床前研究部', '', '18', 'icon_folder', 26, 0, 50, '2014-07-15 14:41:56', 0),
	(30, '临床研究部', '', '19', 'icon_folder', 26, 0, 50, '2014-07-15 14:42:12', 0),
	(31, '立项与专利信息事务部', '', '20', 'icon_folder', 26, 0, 50, '2014-07-15 14:42:32', 0),
	(32, '注册与项目管理部', '', '21', 'icon_folder', 26, 0, 50, '2014-07-15 14:42:50', 0),
	(33, '综合事务室', '', '22', 'icon_folder', 26, 0, 50, '2014-07-15 14:43:07', 0),
	(34, '行政办公室', '', '23', 'icon_folder', 28, 0, 50, '2014-07-15 14:43:31', 0),
	(35, '医学部', '', '24', 'icon_folder', 28, 0, 50, '2014-07-15 14:43:44', 0),
	(36, '市场部', '', '25', 'icon_folder', 28, 0, 50, '2014-07-15 14:43:57', 0),
	(37, '直营部', '', '26', 'icon_folder', 28, 0, 50, '2014-07-15 14:44:10', 0),
	(38, '招商部', '', '27', 'icon_folder', 28, 0, 50, '2014-07-15 14:44:28', 0),
	(39, '商务部', '', '28', 'icon_folder', 28, 0, 50, '2014-07-15 14:44:43', 0),
	(40, '生产运营办公室', '', '29', 'icon_folder', 27, 0, 50, '2014-07-15 14:45:12', 0),
	(41, '质量检验部', '', '30', 'icon_folder', 27, 0, 50, '2014-07-15 14:45:25', 0),
	(42, '质量保证部', '', '31', 'icon_folder', 27, 0, 50, '2014-07-15 14:45:38', 0),
	(43, '制剂部', '', '32', 'icon_folder', 27, 0, 50, '2014-07-15 14:45:57', 0),
	(44, '化学合成部', '', '33', 'icon_folder', 27, 0, 50, '2014-07-15 14:46:10', 0),
	(47, '血必净大楼制剂部', '', '36', 'icon_folder', 27, 0, 50, '2014-07-15 14:54:46', 0),
	(48, '血必净大楼提取部', '', '37', 'icon_folder', 27, 0, 50, '2014-07-15 14:55:02', 0),
	(49, '前处理车间', '', '38', 'icon_folder', 27, 0, 50, '2014-07-15 14:55:23', 0),
	(50, '技术总监', '', '39', 'icon_folder', 27, 0, 50, '2014-07-15 14:55:46', 0),
	(51, '技术转化中心', '', '40', 'icon_folder', 27, 0, 50, '2014-07-15 14:56:01', 0),
	(53, 'eeeee', '', 'aaa', 'icon_folder', 45, 0, 60, '2014-07-17 21:16:44', 30);
/*!40000 ALTER TABLE `sys_organization` ENABLE KEYS */;


-- 导出  表 dounine.sys_resource 结构
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE IF NOT EXISTS `sys_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `url` varchar(100) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `icon` varchar(32) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  `seq` int(11) NOT NULL DEFAULT '0',
  `state` int(11) NOT NULL DEFAULT '0',
  `resourcetype` int(11) NOT NULL DEFAULT '0',
  `createdatetime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=111 DEFAULT CHARSET=utf8 COMMENT='资源';

-- 正在导出表  dounine.sys_resource 的数据：43 rows
/*!40000 ALTER TABLE `sys_resource` DISABLE KEYS */;
INSERT INTO `sys_resource` (`id`, `name`, `url`, `description`, `icon`, `pid`, `seq`, `state`, `resourcetype`, `createdatetime`) VALUES
	(1, '系统管理', '', '系统管理', 'icon_sys', NULL, 7, 0, 0, '2014-02-19 01:00:00'),
	(2, '资源管理', '/resource/manager', '资源管理', 'icon_resource', 1, 1, 0, 0, '2014-02-19 01:00:00'),
	(5, '资源列表', '/resource/treeGrid', '资源列表', 'icon_resource', 2, 0, 0, 1, '2014-02-19 01:00:00'),
	(6, '添加', '/resource/add', '资源添加', 'icon_resource', 2, 0, 0, 1, '2014-02-19 01:00:00'),
	(7, '编辑', '/resource/edit', '资源编辑', 'icon_resource', 2, 0, 0, 1, '2014-02-19 01:00:00'),
	(8, '删除', '/resource/delete', '资源删除', 'icon_resource', 2, 0, 0, 1, '2014-02-19 01:00:00'),
	(3, '角色管理', '/role/manager', '角色管理', 'icon_role', 1, 2, 0, 0, '2014-02-19 01:00:00'),
	(10, '角色列表', '/role/dataGrid', '角色列表', 'icon_role', 3, 0, 0, 1, '2014-02-19 01:00:00'),
	(11, '添加', '/role/add', '角色添加', 'icon_role', 3, 0, 0, 1, '2014-02-19 01:00:00'),
	(12, '编辑', '/role/edit', '角色编辑', 'icon_role', 3, 0, 0, 1, '2014-02-19 01:00:00'),
	(13, '删除', '/role/delete', '角色删除', 'icon_role', 3, 0, 0, 1, '2014-02-19 01:00:00'),
	(14, '授权', '/role/grant', '角色授权', 'icon_role', 3, 0, 0, 1, '2014-02-19 01:00:00'),
	(4, '用户管理', '/user/manager', '用户管理', 'icon_user', 1, 3, 0, 0, '2014-02-19 01:00:00'),
	(15, '用户列表', '/user/dataGrid', '用户列表', 'icon_user', 4, 0, 0, 1, '2014-02-19 01:00:00'),
	(16, '添加', '/user/add', '用户添加', 'icon_user', 4, 0, 0, 1, '2014-02-19 01:00:00'),
	(17, '编辑', '/user/edit', '用户编辑', 'icon_user', 4, 0, 0, 1, '2014-02-19 01:00:00'),
	(18, '删除', '/user/delete', '用户删除', 'icon_user', 4, 0, 0, 1, '2014-02-19 01:00:00'),
	(19, '查看', '/user/view', '用户查看', 'icon_user', 4, 0, 0, 1, '2014-02-19 01:00:00'),
	(26, '数据字典', '/dictionary/manager', '数据字典', 'icon_dic', 1, 5, 0, 0, '2014-02-19 01:00:00'),
	(27, '字典列表', '/dictionary/dataGrid', '字典列表', 'icon_dic', 26, 0, 0, 1, '2014-02-19 01:00:00'),
	(28, '字典类别列表', '/dictionarytype/dataGrid', '字典类别列表', 'icon_dic', 26, 0, 0, 1, '2014-02-19 01:00:00'),
	(29, '添加', '/dictionary/add', '字典添加', 'icon_dic', 26, 0, 0, 1, '2014-02-19 01:00:00'),
	(30, '编辑', '/dictionary/edit', '字典编辑', 'icon_dic', 26, 0, 0, 1, '2014-02-19 01:00:00'),
	(31, '删除', '/dictionary/delete', '字典删除', 'icon_dic', 26, 0, 0, 1, '2014-02-19 01:00:00'),
	(32, '问卷配置管理', '', '基础信息管理', 'icon_sys', NULL, 1, 0, 0, '2014-02-19 01:00:00'),
	(39, '部门管理', '/organization/manager', '部门管理', 'icon_org', 1, 1, 0, 0, '2014-02-19 01:00:00'),
	(56, '编辑', '/organization/edit', '', 'icon_org', 39, 0, 0, 1, '2014-04-19 18:53:41'),
	(57, '添加', '/organization/add', '部门管理添加按钮', 'icon_org', 39, 2, 0, 1, '2014-04-19 18:54:51'),
	(58, '查看', '/organization/view', '', 'icon_org', 39, 4, 0, 1, '2014-04-19 18:56:11'),
	(59, '部门列表', '/organization/treeGrid', '', 'icon_org', 39, 5, 0, 1, '2014-04-19 18:57:06'),
	(55, '删除', '/organization/delete', '部门管理删除按钮', 'icon_org', 39, 1, 0, 1, '2014-04-19 18:52:22'),
	(37, '统计管理', '', '统计管理', 'icon_sys', NULL, 6, 0, 0, '2014-02-19 01:00:00'),
	(51, '问卷统计', '/subjectmng/subjectAly', '问卷统计', 'icon_dic', 37, 2, 0, 0, '2014-02-19 01:00:00'),
	(101, '删除', '/papermng/delete', '', '', 53, 0, 0, 1, '2014-05-12 19:20:31'),
	(102, '修改', '/papermng/edit', '', '', 53, 0, 0, 1, '2014-05-12 19:21:01'),
	(100, '添加', '/papermng/add', '', '', 53, 0, 0, 1, '2014-05-12 19:19:53'),
	(53, '问卷管理', '/papermng/manager', '问卷管理', 'icon_org', 32, 1, 0, 0, '2014-02-19 01:00:00'),
	(103, '题目管理', '/subjectmng/manager', '', 'icon_org', 32, 2, 0, 0, '2014-05-13 00:21:13'),
	(104, '添加', '/subjectmng/add', '', '', 103, 0, 0, 1, '2014-05-13 09:38:52'),
	(105, '编辑', '/subjectmng/edit', '', '', 103, 0, 0, 1, '2014-05-13 09:39:15'),
	(106, '删除', '/subjectmng/delete', '', '', 103, 0, 0, 1, '2014-05-13 09:39:37'),
	(107, '题目列表', '/subjectmng/subjectGrid', '', '', 103, 0, 0, 1, '2014-05-13 09:43:36'),
	(109, '分发', '/papermng/grant', '', '', 53, 0, 0, 1, '2014-05-13 17:21:40');
/*!40000 ALTER TABLE `sys_resource` ENABLE KEYS */;


-- 导出  表 dounine.sys_role 结构
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE IF NOT EXISTS `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `seq` int(11) NOT NULL DEFAULT '0',
  `description` varchar(255) DEFAULT NULL,
  `isdefault` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='角色';

-- 正在导出表  dounine.sys_role 的数据：3 rows
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` (`id`, `name`, `seq`, `description`, `isdefault`) VALUES
	(1, '超级管理员', 0, '超级管理员，拥有全部权限', 0),
	(4, '普通用户', 1, '普通用户', 1),
	(9, '匿名用户', 0, '匿名用户', 0);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;


-- 导出  表 dounine.sys_role_resource 结构
DROP TABLE IF EXISTS `sys_role_resource`;
CREATE TABLE IF NOT EXISTS `sys_role_resource` (
  `role_id` int(11) NOT NULL,
  `resource_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`,`resource_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='角色资源';

-- 正在导出表  dounine.sys_role_resource 的数据：43 rows
/*!40000 ALTER TABLE `sys_role_resource` DISABLE KEYS */;
INSERT INTO `sys_role_resource` (`role_id`, `resource_id`) VALUES
	(1, 1),
	(1, 2),
	(1, 3),
	(1, 4),
	(1, 5),
	(1, 6),
	(1, 7),
	(1, 8),
	(1, 10),
	(1, 11),
	(1, 12),
	(1, 13),
	(1, 14),
	(1, 15),
	(1, 16),
	(1, 17),
	(1, 18),
	(1, 19),
	(1, 26),
	(1, 27),
	(1, 28),
	(1, 29),
	(1, 30),
	(1, 31),
	(1, 32),
	(1, 37),
	(1, 39),
	(1, 51),
	(1, 53),
	(1, 55),
	(1, 56),
	(1, 57),
	(1, 58),
	(1, 59),
	(1, 100),
	(1, 101),
	(1, 102),
	(1, 103),
	(1, 104),
	(1, 105),
	(1, 106),
	(1, 107),
	(1, 109);
/*!40000 ALTER TABLE `sys_role_resource` ENABLE KEYS */;


-- 导出  表 dounine.sys_user 结构
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE IF NOT EXISTS `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `loginname` varchar(64) NOT NULL,
  `name` varchar(64) NOT NULL,
  `password` varchar(64) NOT NULL,
  `sex` int(11) NOT NULL DEFAULT '0',
  `age` int(11) NOT NULL DEFAULT '0',
  `usertype` int(11) NOT NULL DEFAULT '1',
  `isdefault` int(11) NOT NULL DEFAULT '0',
  `state` int(11) NOT NULL DEFAULT '0',
  `educa` int(11) NOT NULL DEFAULT '0',
  `divisionage` int(11) NOT NULL DEFAULT '0',
  `post` int(11) NOT NULL DEFAULT '0',
  `postlevel` int(11) NOT NULL DEFAULT '0',
  `organization_id` int(11) NOT NULL DEFAULT '0',
  `createdatetime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  UNIQUE KEY `loginname` (`loginname`)
) ENGINE=MyISAM AUTO_INCREMENT=59 DEFAULT CHARSET=utf8 COMMENT='用户';

-- 正在导出表  dounine.sys_user 的数据：14 rows
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` (`id`, `loginname`, `name`, `password`, `sex`, `age`, `usertype`, `isdefault`, `state`, `educa`, `divisionage`, `post`, `postlevel`, `organization_id`, `createdatetime`) VALUES
	(1, 'admin', '系统管理员', '202cb962ac59075b964b07152d234b70', 0, 18, 1, 0, 0, 0, 0, 0, 0, 1, '2012-06-04 01:00:00'),
	(5, 'normal', '匿名用户', '202cb962ac59075b964b07152d234b70', 0, 40, 1, 1, 0, 9, 13, 16, 19, 1, '2014-05-15 16:45:54'),
	(14, 'lake', 'asdf', '97d986e2afa2c72986972e6433fbeaf9', 0, 20, 1, 1, 0, 9, 13, 16, 19, 1, '2014-07-03 20:39:19'),
	(15, 'any', '匿名用户', '202cb962ac59075b964b07152d234b70', 0, 10, 1, 1, 0, 9, 12, 16, 19, 1, '0000-00-00 00:00:00'),
	(21, 'user2', '用户2', '202cb962ac59075b964b07152d234b70', 1, 23, 0, 1, 0, 11, 23, 16, 18, 1, '2014-07-06 11:42:51'),
	(20, 'user1', '用户1', '202cb962ac59075b964b07152d234b70', 1, 23, 0, 1, 0, 10, 13, 15, 19, 1, '2014-07-06 11:42:51'),
	(18, 'user3', '用户1', '202cb962ac59075b964b07152d234b70', 1, 23, 0, 1, 0, 10, 13, 15, 19, 1, '2014-07-06 11:42:05'),
	(19, 'user4', '用户2', '202cb962ac59075b964b07152d234b70', 1, 23, 0, 1, 0, 11, 23, 16, 18, 1, '2014-07-06 11:42:10'),
	(22, 'a', 'eee', 'cfcd208495d565ef66e7dff9f98764da', 0, 23, 1, 1, 0, 126, 13, 133, 18, 18, '2014-07-15 19:54:00'),
	(54, 'user8', '财务部用户1', '202cb962ac59075b964b07152d234b70', 0, 23, 1, 1, 0, 126, 13, 133, 18, 18, '2014-07-15 21:05:25'),
	(55, 'user9', '财务部用户2', '202cb962ac59075b964b07152d234b70', 1, 23, 1, 1, 0, 126, 20, 133, 18, 18, '2014-07-15 21:05:25'),
	(56, 'user10', '财务部用户3', '202cb962ac59075b964b07152d234b70', 0, 23, 1, 1, 0, 126, 13, 133, 18, 18, '2014-07-15 21:05:25'),
	(57, 'user11', '财务部用户4', '202cb962ac59075b964b07152d234b70', 1, 23, 1, 1, 0, 126, 23, 133, 18, 18, '2014-07-15 21:05:25'),
	(58, 'user12', 'user', 'd41d8cd98f00b204e9800998ecf8427e', 0, 20, 1, 1, 0, 9, 13, 133, 19, 18, '2014-07-16 20:25:02');
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;


-- 导出  表 dounine.sys_user_role 结构
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE IF NOT EXISTS `sys_user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='用户角色';

-- 正在导出表  dounine.sys_user_role 的数据：10 rows
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` (`user_id`, `role_id`) VALUES
	(1, 1),
	(5, 9),
	(14, 4),
	(15, 9),
	(22, 9),
	(54, 4),
	(55, 4),
	(56, 4),
	(57, 4),
	(58, 9);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;


-- 导出  表 dounine.treenodes 结构
DROP TABLE IF EXISTS `treenodes`;
CREATE TABLE IF NOT EXISTS `treenodes` (
  `id` int(11) NOT NULL,
  `nodename` varchar(512) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- 正在导出表  dounine.treenodes 的数据：0 rows
/*!40000 ALTER TABLE `treenodes` DISABLE KEYS */;
/*!40000 ALTER TABLE `treenodes` ENABLE KEYS */;


-- 导出  表 dounine.t_areainfo 结构
DROP TABLE IF EXISTS `t_areainfo`;
CREATE TABLE IF NOT EXISTS `t_areainfo` (
  `id` int(11) NOT NULL DEFAULT '0',
  `level` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `parentId` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 正在导出表  dounine.t_areainfo 的数据：64 rows
/*!40000 ALTER TABLE `t_areainfo` DISABLE KEYS */;
INSERT INTO `t_areainfo` (`id`, `level`, `name`, `parentId`, `status`) VALUES
	(1, 0, '中国', 0, 0),
	(2, 0, '华北区', 1, 0),
	(3, 0, '华南区', 1, 0),
	(4, 0, '北京', 2, 0),
	(5, 0, '海淀区', 4, 0),
	(6, 0, '丰台区', 4, 0),
	(7, 0, '朝阳区', 4, 0),
	(8, 0, '北京XX区1', 4, 0),
	(9, 0, '北京XX区2', 4, 0),
	(10, 0, '北京XX区3', 4, 0),
	(11, 0, '北京XX区4', 4, 0),
	(12, 0, '北京XX区5', 4, 0),
	(13, 0, '北京XX区6', 4, 0),
	(14, 0, '北京XX区7', 4, 0),
	(15, 0, '北京XX区8', 4, 0),
	(16, 0, '北京XX区9', 4, 0),
	(17, 0, '北京XX区10', 4, 0),
	(18, 0, '北京XX区11', 4, 0),
	(19, 0, '北京XX区12', 4, 0),
	(20, 0, '北京XX区13', 4, 0),
	(21, 0, '北京XX区14', 4, 0),
	(22, 0, '北京XX区15', 4, 0),
	(23, 0, '北京XX区16', 4, 0),
	(24, 0, '北京XX区17', 4, 0),
	(25, 0, '北京XX区18', 4, 0),
	(26, 0, '北京XX区19', 4, 0),
	(27, 0, '北京XX区1', 4, 0),
	(28, 0, '北京XX区2', 4, 0),
	(29, 0, '北京XX区3', 4, 0),
	(30, 0, '北京XX区4', 4, 0),
	(31, 0, '北京XX区5', 4, 0),
	(32, 0, '北京XX区6', 4, 0),
	(33, 0, '北京XX区7', 4, 0),
	(34, 0, '北京XX区8', 4, 0),
	(35, 0, '北京XX区9', 4, 0),
	(36, 0, '北京XX区10', 4, 0),
	(37, 0, '北京XX区11', 4, 0),
	(38, 0, '北京XX区12', 4, 0),
	(39, 0, '北京XX区13', 4, 0),
	(40, 0, '北京XX区14', 4, 0),
	(41, 0, '北京XX区15', 4, 0),
	(42, 0, '北京XX区16', 4, 0),
	(43, 0, '北京XX区17', 4, 0),
	(44, 0, '北京XX区18', 4, 0),
	(45, 0, '北京XX区19', 4, 0),
	(46, 0, 'xx省1', 1, 0),
	(47, 0, 'xx省2', 1, 0),
	(48, 0, 'xx省3', 1, 0),
	(49, 0, 'xx省4', 1, 0),
	(50, 0, 'xx省5', 1, 0),
	(51, 0, 'xx省6', 1, 0),
	(52, 0, 'xx省7', 1, 0),
	(53, 0, 'xx省8', 1, 0),
	(54, 0, 'xx省9', 1, 0),
	(55, 0, 'xx省10', 1, 0),
	(56, 0, 'xx省11', 1, 0),
	(57, 0, 'xx省12', 1, 0),
	(58, 0, 'xx省13', 1, 0),
	(59, 0, 'xx省14', 1, 0),
	(60, 0, 'xx省15', 1, 0),
	(61, 0, 'xx省16', 1, 0),
	(62, 0, 'xx省17', 1, 0),
	(63, 0, 'xx省18', 1, 0),
	(64, 0, 'xx省19', 1, 0);
/*!40000 ALTER TABLE `t_areainfo` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
