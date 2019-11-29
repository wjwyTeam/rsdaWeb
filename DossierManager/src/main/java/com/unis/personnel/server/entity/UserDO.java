package com.unis.personnel.server.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @version 1.0
 * @author 张琪 
 * @description 用户实体类
 */
@ToString
@SuppressWarnings("serial")
@Getter
@Setter
@Table(name = "user")
public class UserDO extends BaseEntity {
	
	@Column(name = "user_id") 
	 private  String userId;   //用户表主键
	@Column(name = "user_name") 
	 private  String userName;   //用户名
	@Column(name = "pass_word") 
	 private  String passWord;   //用户密码
	@Column(name = "user_real_name") 
	 private  String userRealName;   //用户姓名
	@Column(name = "user_email") 
	 private  String userEmail;   //用户邮箱
	@Column(name = "base64_photo") 
	 private  String base64Photo;   //用户照片的base64码
	@Column(name = "phone") 
	 private  String phone;   //用户的联系电话
	@Column(name = "dept_id") 
	 private  String deptId;   //用户绑定所在单位（向下级联）
	@Column(name = "work_dept") 
	 private  String workDept;   //工作单位
	@Column(name = "duty") 
	 private  String duty;   //用户职务
	@Column(name = "user_ip") 
	 private  String userIp;   //用户绑定IP
	@Column(name = "user_sex") 
	private  String userSex;   //用户性别
	@Column(name = "user_type") 
	 private  String userType;   //用户类型   U001 启用   U002  停用   U003  注销  U004  临时账户（用户管理不体现，临时用户管理再提现）
	@Column(name = "uorder") 
	 private  int uorder;   //用户排序
	@Column(name = "filed1") 
	 private  String filed1;   //备用字段1
	@Column(name = "filed2") 
	 private  String filed2;   //备用字段2
	@Column(name = "filed3") 
	 private  String filed3;   //备用字段3
	@Column(name = "filed4") 
	 private  String filed4;   //备用字段4
	@Column(name = "filed5") 
	 private  String filed5;   //备用字段5
	@Column(name = "filed6") 
	 private  String filed6;   //备用字段6


}