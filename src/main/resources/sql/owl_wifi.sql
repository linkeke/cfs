/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.5.46-0ubuntu0.12.04.2 : Database - owl_wifi
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`owl_wifi` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `owl_wifi`;

/*Table structure for table `t_menu` */

DROP TABLE IF EXISTS `t_menu`;

CREATE TABLE `t_menu` (
  `n_menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `c_menu_icon` varchar(50) DEFAULT NULL COMMENT '菜单Icon',
  `c_menu_name` varchar(100) DEFAULT NULL COMMENT '菜单名称',
  `c_menu_url` varchar(250) DEFAULT NULL COMMENT '菜单URL',
  `n_menu_level` int(1) DEFAULT NULL COMMENT '菜单级别',
  `n_parent_menu_id` bigint(20) DEFAULT NULL COMMENT '父级菜单ID',
  `n_status` int(1) DEFAULT '1' COMMENT '有效标识0无效1有效',
  `t_create_time` datetime DEFAULT NULL,
  `t_update_time` datetime DEFAULT NULL,
  `n_create_by` bigint(20) DEFAULT NULL,
  `n_update_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`n_menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_owl_data` */

DROP TABLE IF EXISTS `t_owl_data`;

CREATE TABLE `t_owl_data` (
  `mac` char(12) NOT NULL COMMENT 'mac',
  `imei` varchar(20) DEFAULT NULL COMMENT '国际移动设备标识',
  `idfa` varchar(50) DEFAULT NULL COMMENT '苹果体系内设备唯一标识',
  `android_id` varchar(50) DEFAULT NULL COMMENT '安卓体系内设备唯一标识',
  `sex_ind` tinyint(4) DEFAULT NULL COMMENT '0=Male 1=Female',
  `age_txt` varchar(20) DEFAULT NULL,
  `work_txt` varchar(20) DEFAULT NULL,
  `married_ind` tinyint(4) DEFAULT NULL,
  `car_ind` tinyint(4) DEFAULT NULL,
  `baby_txt` varchar(20) DEFAULT NULL,
  `usual_place_type_txt` varchar(20) DEFAULT NULL COMMENT '常去场所类型查询服务',
  `usual_night_address_txt` varchar(20) DEFAULT NULL COMMENT '夜间活跃区域查询服务',
  `leisure_activity_txt` varchar(20) DEFAULT NULL COMMENT '游戏兴趣标签查询服务',
  `consumption_habits_txt` varchar(20) DEFAULT NULL COMMENT '线下消费偏好标签查询服务',
  `interests_txt` varchar(20) DEFAULT NULL COMMENT '应用兴趣标签查询服务',
  `financial_products_txt` varchar(20) DEFAULT NULL COMMENT '金融标签查询服务',
  `device_brand` varchar(30) DEFAULT NULL COMMENT '标准品牌',
  `device_model` varchar(30) DEFAULT NULL COMMENT '标准机型',
  `device_type` varchar(30) DEFAULT NULL COMMENT '设备类型，包含手机、平板、智能电视、智能手表、模拟器',
  `device_screen` varchar(30) DEFAULT NULL COMMENT '屏幕尺寸，尺寸分布： 6.0英寸以上 6.0-5.6英寸 5.5-5.1英寸 5.0-4.6英寸 4.5-3.1英寸 3英寸及以下',
  `device_price` varchar(30) DEFAULT NULL COMMENT '价格区间，区间分布： 1～499 500～999 1000~1999 2000~3999 4000以上',
  `device_special_function` varchar(30) DEFAULT NULL COMMENT '功能特性，包含音乐手机、美颜手机、老人机、摄影手机、高端商务手机、高性价比手机等，可包含多个特征，中间用逗号分隔',
  `device_hardware` varchar(100) DEFAULT NULL COMMENT '硬件特性，包含8核芯片，陀螺仪，NFC芯片，双卡双待，指纹等，可包含多个特征，中间用逗号分隔',
  PRIMARY KEY (`mac`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_shop` */

DROP TABLE IF EXISTS `t_shop`;

CREATE TABLE `t_shop` (
  `shop_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name_txt` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_shop_probe` */

DROP TABLE IF EXISTS `t_shop_probe`;

CREATE TABLE `t_shop_probe` (
  `shop_probe_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `shop_id` int(10) unsigned NOT NULL,
  `install_date` date NOT NULL,
  `active_ind` tinyint(4) NOT NULL,
  `rssi_filter` int(10) unsigned NOT NULL COMMENT 'Filter signal strength',
  `probe_mac` char(12) NOT NULL COMMENT '设备MAC地址',
  `probe_name` varchar(200) DEFAULT NULL COMMENT '设备名称',
  `probe_sn` varchar(100) DEFAULT NULL COMMENT '设备序列号',
  `probe_type` varchar(50) DEFAULT NULL COMMENT '设备类型',
  `probe_hardware_version` varchar(50) DEFAULT NULL COMMENT '设备硬件版本',
  `probe_software_version` varchar(50) DEFAULT NULL COMMENT '设备软件版本',
  `probe_datetime` datetime DEFAULT NULL COMMENT '设备时间',
  `probe_activity_interval` int(11) DEFAULT '10' COMMENT '设备记录终端活动的间隔',
  `probe_save_interval` int(11) DEFAULT '8' COMMENT '设备保存记录活动的间隔',
  `probe_upload_interval` int(11) DEFAULT '12' COMMENT '设备上传服务器的间隔',
  `probe_wan_ip` varchar(50) DEFAULT NULL COMMENT '广域网IP',
  `probe_lan_ip` varchar(50) DEFAULT NULL COMMENT '局域网IP',
  PRIMARY KEY (`shop_probe_id`),
  KEY `fk_shop_probe_shop_id` (`shop_id`),
  CONSTRAINT `fk_shop_probe_shop_id` FOREIGN KEY (`shop_id`) REFERENCES `t_shop` (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_talkingdata_device` */

DROP TABLE IF EXISTS `t_talkingdata_device`;

CREATE TABLE `t_talkingdata_device` (
  `mac` char(12) NOT NULL COMMENT 'MAC地址',
  `imei` varchar(20) DEFAULT NULL COMMENT '国际移动设备标识',
  `tdid` varchar(50) DEFAULT NULL COMMENT '此设备在TalkingData平台的唯一设备ID',
  `idfa` varchar(50) DEFAULT NULL COMMENT '苹果体系内设备唯一标识',
  `android_id` varchar(50) DEFAULT NULL COMMENT '安卓体系内设备唯一标识',
  `standard_brand` varchar(30) DEFAULT NULL COMMENT '标准品牌',
  `standard_model` varchar(30) DEFAULT NULL COMMENT '标准机型',
  `device_type` varchar(30) DEFAULT NULL COMMENT '设备类型，包含手机、平板、智能电视、智能手表、模拟器',
  `screen` varchar(30) DEFAULT NULL COMMENT '屏幕尺寸，尺寸分布： 6.0英寸以上 6.0-5.6英寸 5.5-5.1英寸 5.0-4.6英寸 4.5-3.1英寸 3英寸及以下',
  `price` varchar(30) DEFAULT NULL COMMENT '价格区间，区间分布： 1～499 500～999 1000~1999 2000~3999 4000以上',
  `function_type` varchar(30) DEFAULT NULL COMMENT '功能特性，包含音乐手机、美颜手机、老人机、摄影手机、高端商务手机、高性价比手机等，可包含多个特征，中间用逗号分隔',
  `hardware_type` varchar(100) DEFAULT NULL COMMENT '硬件特性，包含8核芯片，陀螺仪，NFC芯片，双卡双待，指纹等，可包含多个特征，中间用逗号分隔',
  PRIMARY KEY (`mac`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_talkingdata_more` */

DROP TABLE IF EXISTS `t_talkingdata_more`;

CREATE TABLE `t_talkingdata_more` (
  `mac` char(50) NOT NULL COMMENT 'MAC地址',
  `label` char(10) NOT NULL DEFAULT '' COMMENT '标签ID',
  `name` varchar(100) DEFAULT NULL COMMENT '标签名',
  `weight` char(5) DEFAULT NULL COMMENT '权重，值越大可信度越大',
  PRIMARY KEY (`mac`,`label`),
  KEY `idx_label` (`label`),
  KEY `idx_mac` (`mac`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_talkingdata_port_code` */

DROP TABLE IF EXISTS `t_talkingdata_port_code`;

CREATE TABLE `t_talkingdata_port_code` (
  `port_id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `port_txt` varchar(100) NOT NULL,
  PRIMARY KEY (`port_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_talkingdata_port_label` */

DROP TABLE IF EXISTS `t_talkingdata_port_label`;

CREATE TABLE `t_talkingdata_port_label` (
  `port_id` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT 'talkingdata接口表ID',
  `label` char(10) NOT NULL COMMENT '标签ID',
  PRIMARY KEY (`port_id`,`label`),
  CONSTRAINT `t_talkingdata_port_label_port_id` FOREIGN KEY (`port_id`) REFERENCES `t_talkingdata_port_code` (`port_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_talkingdata_query` */

DROP TABLE IF EXISTS `t_talkingdata_query`;

CREATE TABLE `t_talkingdata_query` (
  `mac` char(12) NOT NULL COMMENT 'MAC地址',
  `port_id` smallint(5) unsigned NOT NULL,
  `query_datetime` datetime DEFAULT NULL COMMENT '设备时间',
  PRIMARY KEY (`mac`,`port_id`),
  KEY `t_talkingdata_query_port_id` (`port_id`),
  CONSTRAINT `t_talkingdata_query_port_id` FOREIGN KEY (`port_id`) REFERENCES `t_talkingdata_port_code` (`port_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_talkingdata_tag_loc_office_latlng` */

DROP TABLE IF EXISTS `t_talkingdata_tag_loc_office_latlng`;

CREATE TABLE `t_talkingdata_tag_loc_office_latlng` (
  `mac` char(12) NOT NULL COMMENT 'MAC地址',
  `tdid` char(30) NOT NULL COMMENT '权重，值越大可信度越大',
  `latlng` varchar(100) NOT NULL COMMENT '位置经纬度',
  PRIMARY KEY (`mac`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) DEFAULT NULL COMMENT '登录名',
  `user_pwd` varchar(50) DEFAULT NULL COMMENT '登录密码',
  `industry_pic` varchar(50) DEFAULT NULL COMMENT '对应的行业图片',
  `hik_account` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_user_shop` */

DROP TABLE IF EXISTS `t_user_shop`;

CREATE TABLE `t_user_shop` (
  `user_id` int(10) unsigned NOT NULL,
  `shop_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`user_id`,`shop_id`),
  KEY `fk_user_shop_shop_id` (`shop_id`),
  CONSTRAINT `fk_user_shop_shop_id` FOREIGN KEY (`shop_id`) REFERENCES `t_shop` (`shop_id`),
  CONSTRAINT `fk_user_shop_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_visit_shop` */

DROP TABLE IF EXISTS `t_visit_shop`;

CREATE TABLE `t_visit_shop` (
  `visit_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `shop_id` int(10) NOT NULL COMMENT '门店ID',
  `visit_date` date NOT NULL,
  `customer_mac` char(20) NOT NULL,
  `start_time` time NOT NULL,
  `end_time` time DEFAULT NULL,
  PRIMARY KEY (`visit_id`),
  KEY `idx_mac` (`customer_mac`),
  KEY `idx_visit_date` (`visit_date`)
) ENGINE=InnoDB AUTO_INCREMENT=540399 DEFAULT CHARSET=utf8mb4;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
