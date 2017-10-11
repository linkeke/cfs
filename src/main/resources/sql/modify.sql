/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.5.46-0ubuntu0.12.04.2 : Database - owl_wifi
*********************************************************************
*/

/*以后有修改数据库的全部要用sql来记录 */

/*往user表加入字段*/
ALTER TABLE `owl_wifi`.`t_user`   
  ADD COLUMN `n_create_id` INT(10) DEFAULT NULL   COMMENT '创建者ID' AFTER `hik_account`,
  ADD COLUMN `t_create_time` DATETIME DEFAULT NULL   COMMENT '创建时间' AFTER `n_create_id`,
  ADD COLUMN `n_update_id` INT(10) DEFAULT NULL   COMMENT '更新者ID' AFTER `t_create_time`,
  ADD COLUMN `t_update_time` DATETIME DEFAULT NULL   COMMENT '更新时间' AFTER `n_update_id`;
  
  
 /*各模块的权限名称和路径表*/
CREATE TABLE IF NOT EXISTS `t_permission` (
  `n_permission_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `c_permission_name` VARCHAR(200) DEFAULT NULL COMMENT '权限名称',
  `c_permission_url` VARCHAR(200) DEFAULT NULL COMMENT '权限链接',
  `t_create_time` DATETIME DEFAULT NULL,
  `t_update_time` DATETIME DEFAULT NULL,
  `n_create_by` BIGINT(20) DEFAULT NULL,
  `n_update_by` BIGINT(20) DEFAULT NULL,
  `n_status` TINYINT(1) DEFAULT '1' COMMENT '有效状态：0无效，1有效',
  PRIMARY KEY (`n_permission_id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

/*创建角色表*/
CREATE TABLE IF NOT EXISTS `t_role` (
  `n_role_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `n_role_code` INT(11) DEFAULT NULL COMMENT '角色编码',
  `c_role_name` VARCHAR(200) DEFAULT NULL COMMENT '角色名称',
  `n_system_role` TINYINT(1) DEFAULT '0' COMMENT '是否为系统角色，0不是，1是',
  `t_create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `t_update_time` DATETIME DEFAULT NULL COMMENT '修改时间',
  `n_status` TINYINT(1) DEFAULT '1' COMMENT '是否有效，0无效1有效',
  `n_create_by` BIGINT(20) DEFAULT NULL COMMENT '创建人员ID',
  `n_update_by` BIGINT(20) DEFAULT NULL COMMENT '修改人员ID',
  `c_role_desc` VARCHAR(200) DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`n_role_id`),
  UNIQUE KEY `n_role_code` (`n_role_code`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

/*角色菜单关系表*/
CREATE TABLE IF NOT EXISTS `t_role_menu` (
  `n_role_menu_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '角色菜单关系ID',
  `n_role_code` BIGINT(20) DEFAULT NULL COMMENT '角色Code',
  `n_menu_id` BIGINT(20) DEFAULT NULL COMMENT '菜单ID',
  `n_status` INT(1) DEFAULT '1' COMMENT '有效标识0无效1有效',
  `t_create_time` DATETIME DEFAULT NULL,
  `t_update_time` DATETIME DEFAULT NULL,
  `n_create_by` BIGINT(20) DEFAULT NULL,
  `n_update_by` BIGINT(20) DEFAULT NULL,
  PRIMARY KEY (`n_role_menu_id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

/*权限角色关系表*/
CREATE TABLE IF NOT EXISTS `t_role_permission` (
  `n_role_permission` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `n_role_code` BIGINT(20) DEFAULT NULL COMMENT '角色编码',
  `n_permission_id` BIGINT(20) DEFAULT NULL COMMENT '权限ID',
  PRIMARY KEY (`n_role_permission`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

/*用户角色关系表*/
CREATE TABLE IF NOT EXISTS `t_user_role` (
  `n_user_role_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `n_user_id` BIGINT(20) DEFAULT NULL COMMENT '用户ID',
  `n_role_code` BIGINT(20) DEFAULT NULL COMMENT '角色编号',
  PRIMARY KEY (`n_user_role_id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

/*权限菜单关系表*/
CREATE TABLE IF NOT EXISTS `t_menu_permission` (
  `n_menu_permission_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '菜单权限ID',
  `n_menu_id` BIGINT(20) DEFAULT NULL COMMENT '菜单ID',
  `n_permission_id` BIGINT(20) DEFAULT NULL COMMENT '权限ID',
  PRIMARY KEY (`n_menu_permission_id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

/*行业表*/
CREATE TABLE IF NOT EXISTS `t_industry` (
    `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name` varchar(20) NOT NULL COMMENT '行业名称',
    `image` varchar(100) NULL COMMENT '行业图片',
    PRIMARY KEY(`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

alter table t_shop
    add column `industry_id` int(10) not null COMMENT '外键行业ID',
    add column `business_start_time` int(5) not null default 9 COMMENT '营业开始时间',
    add column `business_end_time` int(5) not null default 22 COMMENT '营业结束时间',
    add column `contact` varchar(50) null COMMENT '联系人',
    add column `tel` varchar(50) null COMMENT '联系电话',
    add column `address` varchar(50) null COMMENT '详细地址';
    
alter table t_shop
    add column `lat` decimal(10, 6) null COMMENT '经度',
    add column `lng` decimal(10, 6) null COMMENT '纬度';


CREATE TABLE IF NOT EXISTS `t_talkingdata_position` (
`id`  int(32) NOT NULL ,
`mac`  char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'mac地址' ,
`lat`  decimal(8,3) NULL COMMENT '纬度' ,
`lng`  decimal(8,3) NULL COMMENT '经度' ,
`count`  tinyint(4) NULL COMMENT '探测次数' ,
PRIMARY KEY (`id`)
)
;

 ALTER TABLE `owl_wifi`.`t_shop_probe`   
  ADD COLUMN `lastupload_time` DATETIME DEFAULT NULL   COMMENT '最后上传时间' AFTER `probe_upload_interval`;
  
  ALTER TABLE `owl_wifi`.`t_shop`   
  ADD COLUMN `talkingdata_flag` INT(1) DEFAULT 0   COMMENT '控制门店的用户调用talkingdata' AFTER `lng`;
  
  
  /**
   * 人脸识别sql
   */
  CREATE TABLE `t_face_lips_code` (
  `lips_id` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增长ID',
  `lips_txt` varchar(20) NOT NULL COMMENT 'open,together嘴唇状态（关闭，张开）',
  PRIMARY KEY (`lips_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4

CREATE TABLE `t_face_race_code` (
  `race_id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `race_txt` varchar(20) NOT NULL COMMENT '种族（亚洲、白人、黑人、墨西哥人、混血人）',
  PRIMARY KEY (`race_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4

CREATE TABLE `t_shop_camera` (
  `shop_camera_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `shop_id` int(10) unsigned NOT NULL,
  `device_id` varchar(32) DEFAULT NULL COMMENT '设备ID,比如 owl10000202',
  `install_date` date NOT NULL,
  `active_ind` tinyint(3) unsigned NOT NULL,
  `active_start_time` time NOT NULL,
  `active_end_time` time NOT NULL,
  `camera_hardware_version` varchar(50) DEFAULT NULL COMMENT '设备硬件版本',
  `camera_software_version` varchar(50) DEFAULT NULL COMMENT '设备软件版本',
  PRIMARY KEY (`shop_camera_id`),
  KEY `fk_shop_camera_shop_id` (`shop_id`),
  CONSTRAINT `fk_shop_camera_shop_id` FOREIGN KEY (`shop_id`) REFERENCES `t_shop` (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4

CREATE TABLE `t_shop_face` (
  `visit_date` date NOT NULL COMMENT 'yyyy-MM-dd',
  `visit_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增长',
  `shop_camera_id` int(10) unsigned NOT NULL COMMENT 't_shop_camera表的ID',
  `visit_time` time NOT NULL COMMENT '具体时间',
  `face_id` int(10) unsigned NOT NULL COMMENT 't_face表ID',
  PRIMARY KEY (`visit_date`,`visit_id`),
  KEY `fk_shop_face_shop_camera_id` (`shop_camera_id`),
  KEY `fk_shop_face_face_id` (`face_id`),
  KEY `visit_id` (`visit_id`),
  CONSTRAINT `fk_shop_face_face_id` FOREIGN KEY (`face_id`) REFERENCES `t_face` (`face_id`),
  CONSTRAINT `fk_shop_face_shop_camera_id` FOREIGN KEY (`shop_camera_id`) REFERENCES `t_shop_camera` (`shop_camera_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4

  
  CREATE TABLE `t_face` (
  `face_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `male_ind` tinyint(3) unsigned NOT NULL,
  `age_val` tinyint(3) unsigned NOT NULL,
  `race_id` smallint(5) unsigned NOT NULL,
  `lips_id` smallint(5) unsigned NOT NULL,
  `glasses_ind` tinyint(3) unsigned NOT NULL,
  `images_txt` varchar(200) NOT NULL COMMENT '比如 z19zzaq_1.jpg,z19zzaq_2.jpg,z19zzaq_3.jpg,z19zzaq_4.jpg',
  PRIMARY KEY (`face_id`),
  KEY `fk_face_race_id` (`race_id`),
  KEY `fk_face_lips_id` (`lips_id`),
  CONSTRAINT `fk_face_lips_id` FOREIGN KEY (`lips_id`) REFERENCES `t_face_lips_code` (`lips_id`),
  CONSTRAINT `fk_face_race_id` FOREIGN KEY (`race_id`) REFERENCES `t_face_race_code` (`race_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4

