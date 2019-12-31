/*
 Navicat Premium Data Transfer

 Source Server         : LOCALHOST
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : crebas

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 30/12/2019 19:12:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dapartment
-- ----------------------------
DROP TABLE IF EXISTS `sys_dapartment`;
CREATE TABLE `sys_dapartment`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号',
  `parent_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父级编号',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位名称',
  `d_order` int(10) NULL DEFAULT NULL COMMENT '排序',
  `unit_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '机构类别',
  `is_pipe` bit(1) NULL DEFAULT b'0' COMMENT '是否管档',
  `del_flag` bit(1) NULL DEFAULT b'0' COMMENT '删除标记',
  `is_candel` bit(1) NULL DEFAULT b'0' COMMENT '能否删除标记',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `pipe_type` char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '管档类别',
  `short_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简称',
  `unit_type_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '机构类型名称',
  `pipe_type_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '档案类型名称',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sys_office_parent_id`(`parent_id`) USING BTREE,
  INDEX `sys_office_del_flag`(`is_candel`) USING BTREE,
  INDEX `sys_office_type`(`unit_type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '机构表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dapartment
-- ----------------------------
INSERT INTO `sys_dapartment` VALUES ('07867b95-7fcc-41fa-85a3-f35c7cb24e9a', '0', '河南省', 6, '2', b'1', b'0', b'0', '2019-12-16 15:20:25', '1', '河南', '机构分组', '档案自管');
INSERT INTO `sys_dapartment` VALUES ('128b6156-fac1-4e7c-9281-77519ff5fa2c', 'f9064aa4-a42e-4e06-a818-92543a6f83fd', '吕梁市', 12, '0', b'1', b'0', b'0', '2019-12-16 15:51:20', '1', '吕梁', '法人单位', '档案自管');
INSERT INTO `sys_dapartment` VALUES ('1db0ce52-c05f-48a4-a5ad-54df67a3b6bd', '0', '广西省', 13, '0', b'1', b'0', b'0', '2019-12-18 09:37:49', '0', '广西', '法人单位', '主管部门');
INSERT INTO `sys_dapartment` VALUES ('2c118032-0c68-477f-9760-bb17964c1a47', '0', '山东省', 4, '1', b'1', b'0', b'0', '2019-12-16 15:20:08', '2', '山东', '内设部门', '其他');
INSERT INTO `sys_dapartment` VALUES ('3b581a77-3897-4ba1-b308-536d461148ba', '6634bded-9bb7-4f7d-a8bf-4974faeac72e', '铁岭市', 10, '0', b'1', b'0', b'0', '2019-12-16 15:26:30', '2', '铁岭', '法人单位', '其他');
INSERT INTO `sys_dapartment` VALUES ('4ba01ccb-3ddf-46c3-9ac1-d2aa1aee7a4e', '07867b95-7fcc-41fa-85a3-f35c7cb24e9a', '洛阳市', 1, '2', b'1', b'0', b'0', '2019-12-16 15:21:49', '1', '洛阳', '机构分组', '档案自管');
INSERT INTO `sys_dapartment` VALUES ('5120f799-aade-4adb-ac03-20b76c253dae', '0', '广东省', 14, '0', b'1', b'0', b'0', '2019-12-18 09:34:58', '0', '广东', '法人单位', '主管部门');
INSERT INTO `sys_dapartment` VALUES ('514de43b-7bd0-4b42-afb8-a8a7f96aaa32', '0', '陕西省', 16, '1', b'1', b'0', b'0', '2019-12-18 09:09:48', '1', '陕西', '内设部门', '档案自管');
INSERT INTO `sys_dapartment` VALUES ('58430b1c-5f6b-4ef1-9825-3a2bad57f27d', '0', '台湾省', 17, '0', b'1', b'0', b'0', '2019-12-18 09:32:23', '0', '台湾', '法人单位', '主管部门');
INSERT INTO `sys_dapartment` VALUES ('6634bded-9bb7-4f7d-a8bf-4974faeac72e', '0', '辽宁省', 5, '0', b'1', b'0', b'0', '2019-12-16 15:26:01', '1', '辽宁', '法人单位', '档案自管');
INSERT INTO `sys_dapartment` VALUES ('7e25bd68-ae66-450f-af56-88b8e1014c04', '0', '河北省', 11, '0', b'1', b'0', b'0', '2019-12-16 15:20:43', '1', '河北', '法人单位', '档案自管');
INSERT INTO `sys_dapartment` VALUES ('8c8ce96b-d74a-427f-85e0-c81590a47529', '6634bded-9bb7-4f7d-a8bf-4974faeac72e', '鞍山市', 3, '0', b'1', b'0', b'0', '2019-12-16 15:29:58', '0', '鞍山', '法人单位', '主管部门');
INSERT INTO `sys_dapartment` VALUES ('9527f5a7-f280-4cb1-b372-19fdeb2d54b4', '2c118032-0c68-477f-9760-bb17964c1a47', '青岛市', 9, '0', b'1', b'0', b'0', '2019-12-16 15:22:16', '1', '青岛', '法人单位', '档案自管');
INSERT INTO `sys_dapartment` VALUES ('a306f2c9-d624-4997-83c6-781742491312', '0', '云南省', 15, '0', b'1', b'0', b'0', '2019-12-18 09:33:40', '1', '云南', '法人单位', '档案自管');
INSERT INTO `sys_dapartment` VALUES ('d1f01952-d79d-44ae-a7b8-929fad3508b3', '07867b95-7fcc-41fa-85a3-f35c7cb24e9a', '郑州市', 2, '0', b'1', b'0', b'0', '2019-12-16 15:25:23', '0', '郑州', '法人单位', '主管部门');
INSERT INTO `sys_dapartment` VALUES ('dd375d61-aa59-416d-9e91-0877d77ae457', 'f9064aa4-a42e-4e06-a818-92543a6f83fd', '太原市', 7, '0', b'1', b'0', b'0', '2019-12-16 15:24:01', '1', '太原', '法人单位', '档案自管');
INSERT INTO `sys_dapartment` VALUES ('f9064aa4-a42e-4e06-a818-92543a6f83fd', '0', '山西省', 8, '0', b'1', b'0', b'0', '2019-12-16 15:19:50', '0', '山西', '法人单位', '主管部门');

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `dict_code` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int(4) NULL DEFAULT 0 COMMENT '字典排序',
  `dict_label` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` bit(1) NULL DEFAULT b'0' COMMENT '状态（false正常 true停用）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 52 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '字典数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `dict_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '字典类型',
  `status` bit(1) NULL DEFAULT b'0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`) USING BTREE,
  UNIQUE INDEX `dict_type`(`dict_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (2, '菜单状态', 'sys_show_hide', b'0', 'admin', '2018-03-16 11:33:00', '1', '2018-03-16 11:33:00', '菜单状态列表');
INSERT INTO `sys_dict_type` VALUES (3, '系统开关', 'sys_normal_disable', b'1', 'admin', '2018-03-16 11:33:00', '1', '2018-03-16 11:33:00', '系统开关列表');
INSERT INTO `sys_dict_type` VALUES (4, '任务状态', 'sys_job_status', b'0', 'admin', '2018-03-16 11:33:00', '1', '2018-03-16 11:33:00', '任务状态列表');
INSERT INTO `sys_dict_type` VALUES (5, '任务分组', 'sys_job_group', b'0', 'admin', '2018-03-16 11:33:00', '1', '2018-03-16 11:33:00', '任务分组列表');
INSERT INTO `sys_dict_type` VALUES (6, '系统是否', 'sys_yes_no', b'1', 'admin', '2018-03-16 11:33:00', '1', '2018-03-16 11:33:00', '系统是否列表');
INSERT INTO `sys_dict_type` VALUES (7, '通知类型', 'sys_notice_type', b'1', 'admin', '2018-03-16 11:33:00', '1', '2018-03-16 11:33:00', '通知类型列表');
INSERT INTO `sys_dict_type` VALUES (8, '通知状态', 'sys_notice_status', b'1', 'admin', '2018-03-16 11:33:00', '1', '2018-03-16 11:33:00', '通知状态列表');
INSERT INTO `sys_dict_type` VALUES (9, '操作类型', 'sys_oper_type', b'0', 'admin', '2018-03-16 11:33:00', '1', '2018-03-16 11:33:00', '操作类型列表');
INSERT INTO `sys_dict_type` VALUES (10, '系统状态', 'sys_common_status', b'1', 'admin', '2018-03-16 11:33:00', '1', '2018-03-16 11:33:00', '登录状态列表');

-- ----------------------------
-- Table structure for sys_function
-- ----------------------------
DROP TABLE IF EXISTS `sys_function`;
CREATE TABLE `sys_function`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '功能主键',
  `function_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '功能名称',
  `icon` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '图标样式classcss',
  `pid` varchar(36) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '所属父节点',
  `url` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '功能请求地址(action)',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '功能备注',
  `forder` int(11) NULL DEFAULT NULL COMMENT '功能排序字段',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `del_flag` bit(1) NULL DEFAULT NULL COMMENT '是否删除',
  `is_candel` bit(1) NULL DEFAULT NULL COMMENT '是否能删除',
  `target` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '打开方式（menuItem页签 menuBlank新窗口）',
  `function_type` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` bit(1) NULL DEFAULT b'0' COMMENT '菜单状态（0显示 1隐藏）',
  `perms` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '权限标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '功能表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_function
-- ----------------------------
INSERT INTO `sys_function` VALUES ('1', '系统管理', 'string', '0', '#', 'fa fa-gear', 1, '2019-12-10 10:35:21', b'0', b'0', '', 'M', b'0', '');
INSERT INTO `sys_function` VALUES ('100', '用户管理', 'string', '1', '/system/user', '#', 1, '2019-12-10 10:35:21', b'0', b'0', '', 'C', b'0', 'system:user:view');
INSERT INTO `sys_function` VALUES ('1000', '用户查询', 'string', '100', '#', '#', 1, '2019-12-10 10:35:21', b'0', b'0', '', 'F', b'0', 'system:user:list');
INSERT INTO `sys_function` VALUES ('1001', '用户新增', 'string', '100', '#', '#', 2, '2019-12-10 10:35:21', b'0', b'0', '', 'F', b'0', 'system:user:add');
INSERT INTO `sys_function` VALUES ('1002', '用户修改', 'string', '100', '#', '#', 3, '2019-12-10 10:35:21', b'0', b'0', '', 'F', b'0', 'system:user:edit');
INSERT INTO `sys_function` VALUES ('1003', '用户删除', 'string', '100', '#', '#', 4, '2019-12-10 10:35:21', b'0', b'0', '', 'F', b'0', 'system:user:remove');
INSERT INTO `sys_function` VALUES ('1004', '用户导出', 'string', '100', '#', '#', 5, '2019-12-10 10:35:21', b'0', b'0', '', 'F', b'0', 'system:user:export');
INSERT INTO `sys_function` VALUES ('1005', '用户导入', 'string', '100', '#', '#', 6, '2019-12-10 10:35:21', b'0', b'1', '', 'F', b'0', 'system:user:import');
INSERT INTO `sys_function` VALUES ('101', '角色管理', 'string', '1', '/system/role', '#', 2, '2019-12-10 10:35:21', b'0', b'0', '', 'C', b'0', 'system:role:view');
INSERT INTO `sys_function` VALUES ('102', '菜单管理', 'string', '1', '/system/menu', '#', 3, '2019-12-10 10:35:21', b'0', b'0', '', 'C', b'0', 'system:menu:view');
INSERT INTO `sys_function` VALUES ('103', '部门管理', 'string', '1', '/system/dept', '#', 4, '2019-12-10 10:35:21', b'0', b'0', '', 'C', b'0', 'system:dept:view');
INSERT INTO `sys_function` VALUES ('104', '岗位管理', 'string', '1', '/system/post', '#', 5, '2019-12-10 10:35:21', b'0', b'0', '', 'C', b'0', 'system:post:view');
INSERT INTO `sys_function` VALUES ('105', '字典管理', 'string', '1', '/system/dict', '#', 6, '2019-12-10 10:35:21', b'0', b'0', '', 'C', b'0', 'system:dict:view');
INSERT INTO `sys_function` VALUES ('106', '参数设置', 'string', '1', '/system/config', '#', 7, '2019-12-10 10:35:21', b'0', b'0', '', 'C', b'0', 'system:config:view');
INSERT INTO `sys_function` VALUES ('107', '通知公告', 'string', '1', '/system/notice', '#', 8, '2019-12-10 10:35:21', b'0', b'0', '', 'C', b'0', 'system:notice:view');
INSERT INTO `sys_function` VALUES ('108', '日志管理', 'string', '1', '#', '#', 9, '2019-12-10 10:35:21', b'0', b'0', '', 'M', b'0', '');
INSERT INTO `sys_function` VALUES ('109', '在线用户', 'string', '2', '/monitor/online', '#', 1, '2019-12-10 10:35:21', b'0', b'0', '', 'C', b'0', 'monitor:online:view');
INSERT INTO `sys_function` VALUES ('110', '定时任务', 'string', '2', '/monitor/job', '#', 2, '2019-12-10 10:35:21', b'0', b'0', '', 'C', b'0', 'monitor:job:view');
INSERT INTO `sys_function` VALUES ('111', '数据监控', 'string', '2', '/monitor/data', '#', 3, '2019-12-10 10:35:21', b'0', b'0', '', 'C', b'0', 'monitor:data:view');
INSERT INTO `sys_function` VALUES ('112', '服务监控', 'string', '2', '/monitor/server', '#', 3, '2019-12-10 10:35:21', b'0', b'0', '', 'C', b'0', 'monitor:server:view');
INSERT INTO `sys_function` VALUES ('113', '表单构建', 'string', '3', '/tool/build', '#', 1, '2019-12-10 10:35:21', b'0', b'0', '', 'C', b'0', 'tool:build:view');
INSERT INTO `sys_function` VALUES ('114', '代码生成', 'string', '3', '/tool/gen', '#', 2, '2019-12-10 10:35:21', b'0', b'0', '', 'C', b'0', 'tool:gen:view');
INSERT INTO `sys_function` VALUES ('115', '系统接口', 'string', '3', '/tool/swagger', '#', 3, '2019-12-10 10:35:21', b'0', b'0', '', 'C', b'0', 'tool:swagger:view');
INSERT INTO `sys_function` VALUES ('2', '系统监控', 'string', '0', '#', 'fa fa-video-camera', 2, '2019-12-10 10:35:21', b'0', b'0', '', 'M', b'0', '');
INSERT INTO `sys_function` VALUES ('3', '系统工具', 'string', '0', '#', 'fa fa-bars', 3, '2019-12-10 10:35:21', b'0', b'0', '', 'M', b'0', '');
INSERT INTO `sys_function` VALUES ('500', '操作日志', 'string', '108', '/monitor/operlog', '#', 1, '2019-12-10 10:35:21', b'0', b'0', '', 'C', b'0', 'monitor:operlog:view');
INSERT INTO `sys_function` VALUES ('501', '登录日志', 'string', '108', '/monitor/logininfor', '#', 2, '2019-12-10 10:35:21', b'0', b'0', '', 'C', b'0', 'monitor:logininfor:view');

-- ----------------------------
-- Table structure for sys_group
-- ----------------------------
DROP TABLE IF EXISTS `sys_group`;
CREATE TABLE `sys_group`  (
  `group_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '功能组id',
  `group_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '功能组名称',
  `icon` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'icon',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '功能组备注',
  `f_order` int(11) NULL DEFAULT NULL COMMENT '功能组排序',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `is_candel` bit(1) NULL DEFAULT NULL COMMENT '是否可删除项',
  `del_flag` bit(1) NULL DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`group_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_oper_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE `sys_oper_log`  (
  `oper_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '模块标题',
  `business_type` int(2) NULL DEFAULT 0 COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '请求方式',
  `operator_type` int(1) NULL DEFAULT 0 COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '操作人员',
  `dept_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '返回参数',
  `status` int(1) NULL DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`oper_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 106 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '操作日志记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_oper_log
-- ----------------------------
INSERT INTO `sys_oper_log` VALUES (100, '角色管理', 2, 'com.ruoyi.web.controller.system.SysRoleController.changeStatus()', 'POST', 1, 'admin', '研发部门', '/system/role/changeStatus', '127.0.0.1', '内网IP', '{\r\n  \"roleId\" : [ \"1\" ],\r\n  \"status\" : [ \"1\" ]\r\n}', 'null', 1, '不允许操作超级管理员角色', '2019-12-06 15:34:19');
INSERT INTO `sys_oper_log` VALUES (101, '角色管理', 2, 'com.ruoyi.web.controller.system.SysRoleController.changeStatus()', 'POST', 1, 'admin', '研发部门', '/system/role/changeStatus', '127.0.0.1', '内网IP', '{\r\n  \"roleId\" : [ \"2\" ],\r\n  \"status\" : [ \"1\" ]\r\n}', '{\r\n  \"msg\" : \"操作成功\",\r\n  \"code\" : 0\r\n}', 0, NULL, '2019-12-06 15:34:22');
INSERT INTO `sys_oper_log` VALUES (102, '角色管理', 2, 'com.ruoyi.web.controller.system.SysRoleController.changeStatus()', 'POST', 1, 'admin', '研发部门', '/system/role/changeStatus', '127.0.0.1', '内网IP', '{\r\n  \"roleId\" : [ \"2\" ],\r\n  \"status\" : [ \"0\" ]\r\n}', '{\r\n  \"msg\" : \"操作成功\",\r\n  \"code\" : 0\r\n}', 0, NULL, '2019-12-06 15:34:25');
INSERT INTO `sys_oper_log` VALUES (103, '用户管理', 2, 'com.ruoyi.web.controller.system.SysUserController.changeStatus()', 'POST', 1, 'admin', '研发部门', '/system/user/changeStatus', '127.0.0.1', '内网IP', '{\r\n  \"userId\" : [ \"2\" ],\r\n  \"status\" : [ \"1\" ]\r\n}', '{\r\n  \"msg\" : \"操作成功\",\r\n  \"code\" : 0\r\n}', 0, NULL, '2019-12-06 16:46:10');
INSERT INTO `sys_oper_log` VALUES (104, '定时任务', 2, 'com.ruoyi.quartz.controller.SysJobController.run()', 'POST', 1, 'admin', '研发部门', '/monitor/job/run', '127.0.0.1', '内网IP', '{\r\n  \"jobId\" : [ \"1\" ],\r\n  \"jobGroup\" : [ \"DEFAULT\" ]\r\n}', '{\r\n  \"msg\" : \"操作成功\",\r\n  \"code\" : 0\r\n}', 0, NULL, '2019-12-11 19:16:39');
INSERT INTO `sys_oper_log` VALUES (105, '参数管理', 2, 'com.ruoyi.web.controller.system.SysConfigController.editSave()', 'POST', 1, 'admin', '研发部门', '/system/config/edit', '127.0.0.1', '内网IP', '{\r\n  \"configId\" : [ \"1\" ],\r\n  \"configName\" : [ \"主框架页-默认皮肤样式名称\" ],\r\n  \"configKey\" : [ \"sys.index.skinName\" ],\r\n  \"configValue\" : [ \"skin-yellow\" ],\r\n  \"configType\" : [ \"Y\" ],\r\n  \"remark\" : [ \"蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow\" ]\r\n}', '{\r\n  \"msg\" : \"操作成功\",\r\n  \"code\" : 0\r\n}', 0, NULL, '2019-12-11 19:22:42');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `en_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '英文名称',
  `is_candel` bit(1) NULL DEFAULT b'0' COMMENT '是否可删除',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `del_flag` bit(1) NULL DEFAULT b'0' COMMENT '删除标记',
  `role_Key` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色权限字符串',
  `data_scope` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `role_status` bit(1) NULL DEFAULT b'0' COMMENT '角色状态（0正常 1停用）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sys_role_del_flag`(`del_flag`) USING BTREE,
  INDEX `sys_role_enname`(`en_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('0674ff60-177d-433b-9240-3ddfaf9ce755', 'pt', 'pt', b'0', '2019-12-27 17:15:33', b'1', NULL, NULL, b'0');
INSERT INTO `sys_role` VALUES ('155822af-1e70-4a6b-bd46-68588b25a156', 'pm', 'pm', b'1', '2019-12-27 17:31:17', b'0', NULL, NULL, b'1');
INSERT INTO `sys_role` VALUES ('39177e1e-cc05-46df-b625-e521ea714a69', '你好', '你好', b'0', '2019-12-27 17:32:52', b'0', NULL, NULL, b'1');
INSERT INTO `sys_role` VALUES ('e4f90515-563d-49e7-a282-9d54e8beec09', 'pp', 'pp', b'1', '2019-12-27 17:15:45', b'0', NULL, NULL, b'0');
INSERT INTO `sys_role` VALUES ('fd365567-4f52-405a-ae02-f99c6009af2a', '超级管理员', 'admin', b'0', '2019-12-27 17:14:22', b'0', NULL, NULL, b'0');

-- ----------------------------
-- Table structure for sys_role_function_r
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_function_r`;
CREATE TABLE `sys_role_function_r`  (
  `r_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `function_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'sysfunction功能表外键',
  `role_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'role表外键',
  PRIMARY KEY (`r_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '角色功能关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_function_r
-- ----------------------------
INSERT INTO `sys_role_function_r` VALUES ('0abcb6c5-d316-4cb4-906c-cde7fcdd114a', '12', '10');
INSERT INTO `sys_role_function_r` VALUES ('39fd98f3-54fe-49b8-98e0-7b33543c9df0', 'string', '39fd98f3-54fe-49b8-98e0-7b33543c9df0');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户表主键',
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户名',
  `pass_word` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户密码',
  `user_real_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户姓名',
  `base64_photo` longtext CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '用户照片的base64码',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户的联系电话',
  `dept_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户绑定所在单位（向下级联）',
  `work_dept` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '工作单位',
  `duty` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '职务',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户备注',
  `user_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户绑定IP',
  `user_type` varchar(5) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户类型   U001 启用   U002  停用   U003  注销  U004  临时账户（用户管理不体现，临时用户管理再提现）',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '该用户创建时间，精确到秒 格式YYYY--mm--dd hh:MM:ss',
  `u_order` int(11) NULL DEFAULT NULL COMMENT '用户排序',
  `del_flag` bit(1) NULL DEFAULT b'0' COMMENT '显示状态默认为1，0为删除',
  `user_sex` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '性别 0 男 1女',
  `is_candel` bit(1) NULL DEFAULT b'0' COMMENT '是否可删除',
  `index_url` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '指定路径',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '档案系统用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('c7b1b4c5-01d4-4cee-ae0e-31ae6572b455', 'admin', '0192023a7bbd73250516f069df18b500', '超级管理员', NULL, NULL, 'a306f2c9-d624-4997-83c6-781742491312', '云南省', '123', NULL, '120.0.0.1', NULL, '2019-12-27 14:28:34', NULL, b'0', NULL, b'0', NULL);

-- ----------------------------
-- Table structure for sys_user_online
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_online`;
CREATE TABLE `sys_user_online`  (
  `sessionId` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '用户会话id',
  `login_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '登录账号',
  `dept_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '部门名称',
  `ipaddr` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '操作系统',
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '在线状态on_line在线off_line离线',
  `start_timestamp` datetime(0) NULL DEFAULT NULL COMMENT 'session创建时间',
  `last_access_time` datetime(0) NULL DEFAULT NULL COMMENT 'session最后访问时间',
  `expire_time` int(5) NULL DEFAULT 0 COMMENT '超时时间，单位为分钟',
  PRIMARY KEY (`sessionId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '在线用户记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_role_r
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role_r`;
CREATE TABLE `sys_user_role_r`  (
  `r_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户编号',
  `role_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色编号',
  PRIMARY KEY (`r_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户-角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role_r
-- ----------------------------
INSERT INTO `sys_user_role_r` VALUES ('e883269b-cf8d-4416-ac75-0d377b378ce1', 'f452d26c-3c3d-49e3-95ff-ade99584a61a', 'fd365567-4f52-405a-ae02-f99c6009af2a');

SET FOREIGN_KEY_CHECKS = 1;
