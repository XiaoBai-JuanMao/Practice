/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50560
 Source Host           : localhost:3306
 Source Schema         : hrm

 Target Server Type    : MySQL
 Target Server Version : 50560
 File Encoding         : 65001

 Date: 30/11/2020 15:33:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dept_inf
-- ----------------------------
DROP TABLE IF EXISTS `dept_inf`;
CREATE TABLE `dept_inf`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `REMARK` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `state` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of dept_inf
-- ----------------------------
INSERT INTO `dept_inf` VALUES (16, '部门1', '部门2', 0);
INSERT INTO `dept_inf` VALUES (17, '部门21', '部门2分部1', 0);

-- ----------------------------
-- Table structure for document_inf
-- ----------------------------
DROP TABLE IF EXISTS `document_inf`;
CREATE TABLE `document_inf`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TITLE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `filename` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `filetype` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `fileUrl` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `REMARK` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CREATE_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `USER_ID` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `FK_DOCUMENT_USER`(`USER_ID`) USING BTREE,
  CONSTRAINT `FK_DOCUMENT_USER` FOREIGN KEY (`USER_ID`) REFERENCES `user_inf` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of document_inf
-- ----------------------------
INSERT INTO `document_inf` VALUES (2, '备忘录', '备忘录.txt', 'txt', '/ueditor/jsp/upload/file', '这是学校毕业设计的备忘内容', '2020-11-27 10:27:10', 1);
INSERT INTO `document_inf` VALUES (3, '项目需求文档', '需求.txt', 'txt', '/ueditor/jsp/upload/file', '这是人事管理系统项目的需求文档', '2020-11-27 10:28:24', 1);
INSERT INTO `document_inf` VALUES (4, '自我介绍', '自我介绍.doc', 'doc', '/ueditor/jsp/upload/file', '面试时的自我介绍', '2020-11-27 10:40:19', 1);
INSERT INTO `document_inf` VALUES (9, '面试宝典', 'v3.0-JavaGuide面试突击版.pdf', 'pdf', '/ueditor/jsp/upload/file', 'v3.0-JavaGuide面试突击版.pdf', '2020-11-27 14:03:36', 1);

-- ----------------------------
-- Table structure for employee_inf
-- ----------------------------
DROP TABLE IF EXISTS `employee_inf`;
CREATE TABLE `employee_inf`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `CARD_ID` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ADDRESS` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `POST_CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `TEL` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PHONE` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `QQ_NUM` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `EMAIL` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `SEX` int(11) NOT NULL DEFAULT 1,
  `PARTY` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `BIRTHDAY` timestamp NULL DEFAULT NULL,
  `RACE` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `EDUCATION` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `SPECIALITY` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `HOBBY` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `REMARK` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CREATE_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `state` int(11) NULL DEFAULT 0,
  `dept_id` int(11) NULL DEFAULT NULL,
  `job_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `FK_dept_inf`(`dept_id`) USING BTREE,
  INDEX `FK_job_inf`(`job_id`) USING BTREE,
  CONSTRAINT `FK_dept_inf` FOREIGN KEY (`dept_id`) REFERENCES `dept_inf` (`ID`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `FK_job_inf` FOREIGN KEY (`job_id`) REFERENCES `job_inf` (`ID`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of employee_inf
-- ----------------------------
INSERT INTO `employee_inf` VALUES (1, '爱丽丝', '441324198412031122', '广州天河', '510000', '020-77777777', '13902001111', '36750066', '251425887@qq.com', 2, '党员', '1980-01-01 00:00:00', '满', '本科', '美声', '唱歌', NULL, '2016-03-14 11:35:18', 0, NULL, 3);
INSERT INTO `employee_inf` VALUES (2, '杰克', '440681198503155112', '广西粤嵌', '528000', '075783311111', '18111111111', '251425887', '251425887@qq.com', 1, '群众', '1981-09-24 00:00:00', '汉', '本科', '计科', '无', '有点厉害', '2016-03-14 11:35:18', 0, 17, 3);
INSERT INTO `employee_inf` VALUES (5, '员工1', '44068119980913311X', '广东省广州市粤嵌', '528000', '075783311111', '15301111111', '2622315551', '2133132@qq.com', 1, '共青团员', '1998-08-13 00:00:00', '汉', '本科', '计科', '篮球', '真不戳', '2020-11-26 16:39:07', 0, NULL, 1);
INSERT INTO `employee_inf` VALUES (6, '主管1', '44068119980913811X', '北京粤嵌', '528000', '075781162412', '15305252624', '265654665', '265654665@qq.com', 2, '党员', '1975-11-10 00:00:00', '汉', '硕士', '管理', '无', '一姐', '2020-11-26 18:35:55', 0, NULL, 7);
INSERT INTO `employee_inf` VALUES (10, '员工33', '44068119980914311X', '广东省广州市粤嵌', '528000', '075783310392', '15307576789', '1561354', '1561354@qq.com', 1, '群众', '2020-11-03 00:00:00', '汉', '本科', '计科', '无', '本质1', '2020-11-30 09:22:17', 0, 17, 1);

-- ----------------------------
-- Table structure for job_inf
-- ----------------------------
DROP TABLE IF EXISTS `job_inf`;
CREATE TABLE `job_inf`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `REMARK` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `state` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of job_inf
-- ----------------------------
INSERT INTO `job_inf` VALUES (1, '职员', '职员', 0);
INSERT INTO `job_inf` VALUES (3, 'Java中级开发工程师', 'Java中级开发工程师', 0);
INSERT INTO `job_inf` VALUES (4, 'Java高级开发工程师', 'Java高级开发工程师', 0);
INSERT INTO `job_inf` VALUES (5, '系统管理员', '系统管理员', 0);
INSERT INTO `job_inf` VALUES (6, '架构师', '架构师', 0);
INSERT INTO `job_inf` VALUES (7, '主管', '主管', 0);
INSERT INTO `job_inf` VALUES (8, '经理', '经理', 0);
INSERT INTO `job_inf` VALUES (9, '总经理', '总经理', 0);
INSERT INTO `job_inf` VALUES (13, '职员1', '职员1', 0);

-- ----------------------------
-- Table structure for notice_inf
-- ----------------------------
DROP TABLE IF EXISTS `notice_inf`;
CREATE TABLE `notice_inf`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_date` datetime NULL DEFAULT NULL,
  `type_id` int(11) NULL DEFAULT NULL,
  `content` varchar(10000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` int(11) NULL DEFAULT NULL,
  `modify_date` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `type_id`(`type_id`) USING BTREE,
  CONSTRAINT `notice_inf_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `type_inf` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of notice_inf
-- ----------------------------
INSERT INTO `notice_inf` VALUES (10, '公告1', '2020-11-30 14:32:08', 5, '<p>ad<span style=text-decoration:>f</span><span style=text-decoration:>adfdafasd</span>f</p>', 1, '2020-11-30 14:33:18');
INSERT INTO `notice_inf` VALUES (11, '公告2', '2020-11-30 14:32:25', 4, '<p>ad<strong>司法</strong>程<span style=text-decoration:>序打</span><strong><span style=text-decoration:>算发的萨芬多</span></strong><span style=text-decoration:>大</span><span style=text-decoration:>的</span></p>', 1, '2020-11-30 15:18:12');

-- ----------------------------
-- Table structure for type_inf
-- ----------------------------
DROP TABLE IF EXISTS `type_inf`;
CREATE TABLE `type_inf`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_date` datetime NULL DEFAULT NULL,
  `state` int(11) NULL DEFAULT NULL,
  `user_id` int(11) NULL DEFAULT NULL,
  `modify_date` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of type_inf
-- ----------------------------
INSERT INTO `type_inf` VALUES (3, '其他', '2020-11-26 21:12:57', 0, 1, '2020-11-26 21:12:57');
INSERT INTO `type_inf` VALUES (4, '新闻', '2020-11-26 21:33:20', 0, 1, '2020-11-26 21:33:20');
INSERT INTO `type_inf` VALUES (5, '通知3', '2020-11-27 08:42:38', 0, 1, '2020-11-30 09:25:12');
INSERT INTO `type_inf` VALUES (6, '娱乐', '2020-11-27 11:37:44', 0, 1, '2020-11-27 11:37:44');

-- ----------------------------
-- Table structure for user_inf
-- ----------------------------
DROP TABLE IF EXISTS `user_inf`;
CREATE TABLE `user_inf`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `loginname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `PASSWORD` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `STATUS` int(11) NOT NULL DEFAULT 1,
  `createdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_inf
-- ----------------------------
INSERT INTO `user_inf` VALUES (1, 'admin', '123456', 2, '2016-03-12 09:34:28', '超级管理员');
INSERT INTO `user_inf` VALUES (23, 'admin1', '123456', 1, '2020-11-27 11:27:50', '管理员1');
INSERT INTO `user_inf` VALUES (29, 'admin4', '1234567', 1, '2020-11-30 09:15:29', '管理员5');

SET FOREIGN_KEY_CHECKS = 1;
