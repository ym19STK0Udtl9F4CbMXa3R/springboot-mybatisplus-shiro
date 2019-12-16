/*
Navicat MySQL Data Transfer

Source Server         : nines
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : scaffold

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-12-16 14:02:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '父级ID',
  `name` varchar(50) DEFAULT NULL COMMENT '权限名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `type` smallint(1) DEFAULT '0' COMMENT '类型 0、菜单 1、功能',
  `sort` smallint(6) DEFAULT NULL COMMENT '排序',
  `url` varchar(100) DEFAULT NULL COMMENT '链接地址',
  `perm_code` varchar(50) DEFAULT NULL COMMENT '权限编码',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '0是禁用,1是启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1199641970907832322', '0', '测试', '测试模块', '0', '200', '/sys/test', null, '1', '2019-11-27 18:51:48', '2019-11-27 18:58:42');
INSERT INTO `sys_permission` VALUES ('1199641970907832547', '1199641970907832322', '测试001', '测试功能', '1', '200', '/sys/test', null, '1', null, '2019-12-02 17:34:36');
INSERT INTO `sys_permission` VALUES ('1199644275610828802', '0', '系统管理', '系统管理员目录', '0', '100', null, null, '1', '2019-11-27 19:00:58', '2019-11-27 19:00:58');
INSERT INTO `sys_permission` VALUES ('1199645863003897858', '1199644275610828802', '用户管理', '用户相关权限管理', '1', '110', '', '', '1', '2019-11-27 19:07:17', '2019-12-09 14:38:28');
INSERT INTO `sys_permission` VALUES ('1199689500345532417', '1199645863003897858', '添加用户', '添加用户权限', '2', '112', '/sys/user/add', 'user:add', '1', '2019-11-27 22:00:40', '2019-12-09 14:38:59');
INSERT INTO `sys_permission` VALUES ('1199689699004547073', '1199645863003897858', '修改用户', '修改用户权限', '2', '113', '/sys/user/update', 'user:update', '1', '2019-11-27 22:01:28', '2019-12-09 14:39:18');
INSERT INTO `sys_permission` VALUES ('1199690139469381633', '1199645863003897858', '冻结用户', '冻结用户权限', '2', '114', '/sys/user/{id}', 'user:freeze', '1', '2019-11-27 22:03:13', '2019-12-09 14:39:27');
INSERT INTO `sys_permission` VALUES ('1199690294184673282', '1199645863003897858', '解冻用户', '解冻用户权限', '2', '115', '/sys/user/{id}', 'user:recover', '1', '2019-11-27 22:03:50', '2019-12-09 14:39:32');
INSERT INTO `sys_permission` VALUES ('1199691655437647874', '1199645863003897858', '分配角色', '分配角色权限', '2', '116', '/sys/user/modifyUserRole', 'user:distribution', '1', '2019-11-27 22:09:15', '2019-12-09 14:39:43');
INSERT INTO `sys_permission` VALUES ('1201427760549736450', '1199641970907832322', '测试002', '测试002功能', '2', '210', '/sys/test', 'test:test', '1', '2019-12-02 17:07:54', '2019-12-02 17:07:54');
INSERT INTO `sys_permission` VALUES ('1201433464509595650', '1199641970907832322', '测试节点', '测试', '2', '210', null, null, '1', '2019-12-02 17:30:34', '2019-12-02 17:34:22');
INSERT INTO `sys_permission` VALUES ('1201434977076281345', '1199645863003897858', '用户测试', 'userTest', '2', '210', null, null, '1', '2019-12-02 17:36:35', '2019-12-02 17:36:35');
INSERT INTO `sys_permission` VALUES ('1202880631099133953', '1199645863003897858', '查看用户', '查看用户列表', '2', '111', '/sys/user/show', 'user:show', '1', '2019-12-06 17:21:05', '2019-12-06 17:21:05');
INSERT INTO `sys_permission` VALUES ('1203926813648998402', '1199644275610828802', '角色管理', '角色相关权限管理', '1', '120', null, null, '1', '2019-12-09 14:38:14', '2019-12-09 14:48:12');
INSERT INTO `sys_permission` VALUES ('1203929381007642625', '1199644275610828802', '权限管理', '权限相关权限管理', '1', '130', null, null, '1', '2019-12-09 14:48:27', '2019-12-09 14:48:27');
INSERT INTO `sys_permission` VALUES ('1203930078658809857', '1203926813648998402', '查看角色', '查看角色列表', '2', '121', '/sys/role/findPage', 'role:show', '1', '2019-12-09 14:51:13', '2019-12-09 14:51:13');
INSERT INTO `sys_permission` VALUES ('1203937594926989313', '1203926813648998402', '添加角色', '添加角色权限', '2', '122', '/sys/role/add', 'role:add', '1', '2019-12-09 15:21:05', '2019-12-09 15:21:32');
INSERT INTO `sys_permission` VALUES ('1203938684955295746', '1203926813648998402', '修改角色', '修改角色权限', '2', '123', '/sys/role/update', 'role:update', '1', '2019-12-09 15:25:25', '2019-12-09 16:26:49');
INSERT INTO `sys_permission` VALUES ('1203946412226412546', '1203926813648998402', '冻结角色', '冻结角色权限', '2', '124', '/sys/role/{id}', 'role:freeze', '1', '2019-12-09 15:56:08', '2019-12-09 15:56:08');
INSERT INTO `sys_permission` VALUES ('1203947787400282113', '1203926813648998402', '解冻角色', '解冻角色权限', '2', '125', '/sys/role/{id}', 'role:recover', '1', '2019-12-09 16:01:35', '2019-12-09 16:05:00');
INSERT INTO `sys_permission` VALUES ('1203948566483861505', '1203926813648998402', '分配权限', '分配权限', '2', '126', '/sys/permission/modifyRolePermission', 'role:distribution', '1', '2019-12-09 16:04:41', '2019-12-09 16:04:41');
INSERT INTO `sys_permission` VALUES ('1203954644026163202', '1203929381007642625', '查看权限', '查看权限列表', '2', '131', '/sys/permission/findPage', 'permission:show', '1', '2019-12-09 16:28:50', '2019-12-09 16:28:50');
INSERT INTO `sys_permission` VALUES ('1203955728534765570', '1203929381007642625', '添加权限', '添加新权限', '2', '132', '/sys/permission/add', 'permission:add', '1', '2019-12-09 16:33:09', '2019-12-09 16:33:09');
INSERT INTO `sys_permission` VALUES ('1204306255571361793', '1203929381007642625', '修改权限', '修改权限内容', '2', '133', '/sys/permission/update', 'permission:update', '1', '2019-12-10 15:46:00', '2019-12-10 15:46:00');
INSERT INTO `sys_permission` VALUES ('1204306718769324034', '1203929381007642625', '冻结权限', '冻结权限功能', '2', '134', '/sys/permission/{id}', 'permission:freeze', '1', '2019-12-10 15:47:51', '2019-12-10 15:47:51');
INSERT INTO `sys_permission` VALUES ('1204307998409531394', '1203929381007642625', '解冻权限', '解冻权限功能', '2', '135', '/sys/permission/{id}', 'permission:recover', '1', '2019-12-10 15:52:56', '2019-12-10 15:52:56');
INSERT INTO `sys_permission` VALUES ('1204309305778282498', '1199641970907832322', '测试003', '测试003', '2', '220', null, null, '1', '2019-12-10 15:58:08', '2019-12-10 16:00:33');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `name` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '0是禁用,1是启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1199233215972061186', 'SuperAdmin', '超级管理员', '1', '2019-11-26 15:47:34', '2019-12-10 16:44:45');
INSERT INTO `sys_role` VALUES ('1199233513377574913', 'tester', '测试员', '1', '2019-11-26 15:48:45', '2019-12-10 16:44:49');
INSERT INTO `sys_role` VALUES ('1199233593077739521', 'admin', '普通管理员', '1', '2019-11-26 15:49:04', '2019-12-06 17:28:32');
INSERT INTO `sys_role` VALUES ('1199233734740357121', 'user', '用户', '1', '2019-11-26 15:49:38', '2019-12-01 20:37:17');
INSERT INTO `sys_role` VALUES ('1201124386402996226', 'test01', '测试角色01', '1', '2019-12-01 21:02:24', '2019-12-01 21:02:24');
INSERT INTO `sys_role` VALUES ('1201125058263388161', 'test02', '测试角色02', '1', '2019-12-01 21:05:04', '2019-12-01 21:05:04');
INSERT INTO `sys_role` VALUES ('1201125135581188097', 'test03', '测试角色03', '1', '2019-12-01 21:05:23', '2019-12-01 21:05:23');
INSERT INTO `sys_role` VALUES ('1201126729303461890', 'test04', '测试角色04', '1', '2019-12-01 21:11:43', '2019-12-06 14:27:29');
INSERT INTO `sys_role` VALUES ('1203941190536036354', 'test05', '测试05', '1', '2019-12-09 15:35:23', '2019-12-09 15:35:23');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `permission_id` bigint(20) NOT NULL COMMENT '权限ID',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '0是禁用,1是启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色与权限关系表';

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES ('1202836940254998530', '1201126729303461890', '1199641970907832547', '1', '2019-12-06 14:27:29', '2019-12-06 14:27:29');
INSERT INTO `sys_role_permission` VALUES ('1202836940254998531', '1201126729303461890', '1201427760549736450', '1', '2019-12-06 14:27:29', '2019-12-06 14:27:29');
INSERT INTO `sys_role_permission` VALUES ('1202836940254998532', '1201126729303461890', '1201433464509595650', '1', '2019-12-06 14:27:29', '2019-12-06 14:27:29');
INSERT INTO `sys_role_permission` VALUES ('1202882502870208513', '1199233593077739521', '1199690139469381633', '1', '2019-12-06 17:28:32', '2019-12-06 17:28:32');
INSERT INTO `sys_role_permission` VALUES ('1202882502870208514', '1199233593077739521', '1199690294184673282', '1', '2019-12-06 17:28:32', '2019-12-06 17:28:32');
INSERT INTO `sys_role_permission` VALUES ('1202882502870208515', '1199233593077739521', '1202880631099133953', '1', '2019-12-06 17:28:32', '2019-12-06 17:28:32');
INSERT INTO `sys_role_permission` VALUES ('1204321038337073154', '1199233215972061186', '1202880631099133953', '1', '2019-12-10 16:44:45', '2019-12-10 16:44:45');
INSERT INTO `sys_role_permission` VALUES ('1204321038337073155', '1199233215972061186', '1199689500345532417', '1', '2019-12-10 16:44:45', '2019-12-10 16:44:45');
INSERT INTO `sys_role_permission` VALUES ('1204321038337073156', '1199233215972061186', '1199689699004547073', '1', '2019-12-10 16:44:45', '2019-12-10 16:44:45');
INSERT INTO `sys_role_permission` VALUES ('1204321038337073157', '1199233215972061186', '1199690139469381633', '1', '2019-12-10 16:44:45', '2019-12-10 16:44:45');
INSERT INTO `sys_role_permission` VALUES ('1204321038337073158', '1199233215972061186', '1199690294184673282', '1', '2019-12-10 16:44:45', '2019-12-10 16:44:45');
INSERT INTO `sys_role_permission` VALUES ('1204321038337073159', '1199233215972061186', '1199691655437647874', '1', '2019-12-10 16:44:45', '2019-12-10 16:44:45');
INSERT INTO `sys_role_permission` VALUES ('1204321038337073160', '1199233215972061186', '1201434977076281345', '1', '2019-12-10 16:44:45', '2019-12-10 16:44:45');
INSERT INTO `sys_role_permission` VALUES ('1204321038337073161', '1199233215972061186', '1203930078658809857', '1', '2019-12-10 16:44:45', '2019-12-10 16:44:45');
INSERT INTO `sys_role_permission` VALUES ('1204321038337073162', '1199233215972061186', '1203937594926989313', '1', '2019-12-10 16:44:45', '2019-12-10 16:44:45');
INSERT INTO `sys_role_permission` VALUES ('1204321038337073163', '1199233215972061186', '1203938684955295746', '1', '2019-12-10 16:44:45', '2019-12-10 16:44:45');
INSERT INTO `sys_role_permission` VALUES ('1204321038337073164', '1199233215972061186', '1203946412226412546', '1', '2019-12-10 16:44:45', '2019-12-10 16:44:45');
INSERT INTO `sys_role_permission` VALUES ('1204321038337073165', '1199233215972061186', '1203947787400282113', '1', '2019-12-10 16:44:45', '2019-12-10 16:44:45');
INSERT INTO `sys_role_permission` VALUES ('1204321038337073166', '1199233215972061186', '1203948566483861505', '1', '2019-12-10 16:44:45', '2019-12-10 16:44:45');
INSERT INTO `sys_role_permission` VALUES ('1204321038337073167', '1199233215972061186', '1203954644026163202', '1', '2019-12-10 16:44:45', '2019-12-10 16:44:45');
INSERT INTO `sys_role_permission` VALUES ('1204321038337073168', '1199233215972061186', '1203955728534765570', '1', '2019-12-10 16:44:45', '2019-12-10 16:44:45');
INSERT INTO `sys_role_permission` VALUES ('1204321038337073169', '1199233215972061186', '1204306255571361793', '1', '2019-12-10 16:44:45', '2019-12-10 16:44:45');
INSERT INTO `sys_role_permission` VALUES ('1204321038337073170', '1199233215972061186', '1204306718769324034', '1', '2019-12-10 16:44:45', '2019-12-10 16:44:45');
INSERT INTO `sys_role_permission` VALUES ('1204321038337073171', '1199233215972061186', '1204307998409531394', '1', '2019-12-10 16:44:45', '2019-12-10 16:44:45');
INSERT INTO `sys_role_permission` VALUES ('1204321054657110017', '1199233513377574913', '1199641970907832547', '1', '2019-12-10 16:44:49', '2019-12-10 16:44:49');
INSERT INTO `sys_role_permission` VALUES ('1204321054657110018', '1199233513377574913', '1201427760549736450', '1', '2019-12-10 16:44:49', '2019-12-10 16:44:49');
INSERT INTO `sys_role_permission` VALUES ('1204321054657110019', '1199233513377574913', '1201433464509595650', '1', '2019-12-10 16:44:49', '2019-12-10 16:44:49');
INSERT INTO `sys_role_permission` VALUES ('1204321054657110020', '1199233513377574913', '1204309305778282498', '1', '2019-12-10 16:44:49', '2019-12-10 16:44:49');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `nick_name` varchar(20) DEFAULT NULL COMMENT '昵称',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `salt` varchar(50) NOT NULL COMMENT '加密盐',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '0是禁用,1是启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1198916181618946049', '超级管理员', 'root', '829e20343def132e62eb985a2e253699', 'SGhYREnHQWohXtLGjtozgQ==', 'root@root.com', '1', '2019-11-25 18:47:47', '2019-12-04 14:44:59');
INSERT INTO `sys_user` VALUES ('1198916629293735937', '测试账号', 'test', '5c16fb3115d4b8871d723d1bc8cff436', 'OWGwBje+UaxBodC/0Z2dyQ==', 'test@test.qq.com', '1', '2019-11-25 18:49:33', '2019-12-11 10:08:38');
INSERT INTO `sys_user` VALUES ('1199212953423634434', '张三', 'zs', '614c62b372115e785f6f1f076203dd3b', '8+RJKe69OFA5MWBcaccqOQ==', 'zs@zs.com', '1', '2019-11-26 14:27:03', '2019-12-06 17:38:00');
INSERT INTO `sys_user` VALUES ('1199213021472022529', '李肆', 'ls', '46cb53f4e08c813043691d3682eb5998', 'MAT4KKj4mFH0plEa3RjRnA==', 'ls@ls.com', '1', '2019-11-26 14:27:19', '2019-12-01 17:56:39');
INSERT INTO `sys_user` VALUES ('1199213101197352962', '王五', 'ww', '593adc7c2eda8a359f06fcd3b6d2c400', '1wC7zuq8yMUW1WFse37fRg==', 'ww@qq.com', '1', '2019-11-26 14:27:38', '2019-12-01 19:29:50');
INSERT INTO `sys_user` VALUES ('1201070325918081026', '赵六', 'zl', 'f5e0d2bab3e175c4e0f410f68be21992', 'W/oomWbIIVN7D0l+AxCH2Q==', null, '1', '2019-12-01 17:27:34', '2019-12-01 20:25:13');
INSERT INTO `sys_user` VALUES ('1201070932640931841', '测试账号1', 'test01', '82148c539f09f1de2cc48fd2d341ca37', 'Stgzk8K/Sl+G4Y9QAOyMqg==', null, '1', '2019-12-01 17:30:00', '2019-12-01 20:25:17');
INSERT INTO `sys_user` VALUES ('1201071233976508418', '测试账号02', 'test02', 'e21a648a06a70364661e79aa1348759a', 'g7DbV3bwBg5LRNJ2RpDrKg==', null, '1', '2019-12-01 17:31:12', '2019-12-01 20:25:20');
INSERT INTO `sys_user` VALUES ('1201124766985752577', '测试账号03', 'test03', 'e42acb7e5da71a90facf032576155e15', '349ljIlX0KKFhXXoGjGykQ==', null, '1', '2019-12-01 21:03:55', '2019-12-10 16:44:14');
INSERT INTO `sys_user` VALUES ('1202834260216262657', '测试04', 'test04', 'c82991c3a658eff6202285d0818bec47', 'fc21MnyxrYdfZDour6iLYQ==', null, '1', '2019-12-06 14:16:49', '2019-12-06 14:16:49');
INSERT INTO `sys_user` VALUES ('1202834387324645378', '测试05', 'test05', '043ef8ac6a030078b39170c49aea09d6', 'UdzKOImHMp7S4qODLFgNJg==', null, '1', '2019-12-06 14:17:20', '2019-12-06 14:17:20');
INSERT INTO `sys_user` VALUES ('1202835674263056386', '测试06', 'test06', '911dae536cb0964c7406516754b9273c', 'EwA6AkojytAqevmM4zcc7Q==', null, '1', '2019-12-06 14:22:26', '2019-12-06 14:22:26');
INSERT INTO `sys_user` VALUES ('1202836299109494785', '测试07', 'test07', '095dfe469ffeb0cdf7fe51b7db57d9d0', 'uHpn7yqyprQVBEsILKGAtw==', null, '1', '2019-12-06 14:24:56', '2019-12-06 14:24:56');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '0是禁用,1是启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户与角色关系表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1201101092786278402', '1199213101197352962', '1199233734740357121', '1', '2019-12-01 19:29:51', '2019-12-01 20:37:17');
INSERT INTO `sys_user_role` VALUES ('1201114488067854338', '1198916181618946049', '1199233215972061186', '1', '2019-12-01 20:23:04', '2019-12-04 14:44:59');
INSERT INTO `sys_user_role` VALUES ('1201115029875462145', '1201070325918081026', '1199233734740357121', '1', '2019-12-01 20:25:13', '2019-12-01 20:37:17');
INSERT INTO `sys_user_role` VALUES ('1201115044798795778', '1201070932640931841', '1199233513377574913', '1', '2019-12-01 20:25:17', '2019-12-04 16:47:00');
INSERT INTO `sys_user_role` VALUES ('1201115057075523585', '1201071233976508418', '1199233513377574913', '1', '2019-12-01 20:25:20', '2019-12-04 16:47:00');
INSERT INTO `sys_user_role` VALUES ('1202102526885572600', '1198916181618946049', '1199233513377574913', '1', null, '2019-12-04 16:47:00');
INSERT INTO `sys_user_role` VALUES ('1202884888946814978', '1199212953423634434', '1199233593077739521', '1', '2019-12-06 17:38:00', '2019-12-06 17:38:00');
INSERT INTO `sys_user_role` VALUES ('1204583743298277378', '1198916629293735937', '1199233215972061186', '1', '2019-12-11 10:08:38', '2019-12-11 10:08:38');
INSERT INTO `sys_user_role` VALUES ('1204583743298277379', '1198916629293735937', '1199233513377574913', '1', '2019-12-11 10:08:38', '2019-12-11 10:08:38');
INSERT INTO `sys_user_role` VALUES ('1204583743298277380', '1198916629293735937', '1199233215972061186', '1', '2019-12-11 10:08:38', '2019-12-11 10:08:38');
INSERT INTO `sys_user_role` VALUES ('1204583743298277381', '1198916629293735937', '1199233513377574913', '1', '2019-12-11 10:08:38', '2019-12-11 10:08:38');
INSERT INTO `sys_user_role` VALUES ('1204583743298277382', '1198916629293735937', '1199233734740357121', '1', '2019-12-11 10:08:38', '2019-12-11 10:08:38');
