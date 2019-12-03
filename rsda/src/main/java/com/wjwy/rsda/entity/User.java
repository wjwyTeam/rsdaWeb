/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: zgr
 * @Date: 2019-11-30 22:58:25
 * @LastEditors: zgr
 * @LastEditTime: 2019-11-30 23:17:54
 */
package com.wjwy.rsda.entity;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class User{
  
	 private  String userId;   //用户表主键

	 private  String userName;   //用户名

	 private  String passWord;   //用户密码

	 private  String userRealName;   //用户姓名

	 private  String userEmail;   //用户邮箱

	 private  String base64Photo;   //用户照片的base64码

	 private  String phone;   //用户的联系电话

	 private  String deptId;   //用户绑定所在单位（向下级联）

	 private  String workDept;   //工作单位

	 private  String duty;   //用户职务

	 private  String userIp;   //用户绑定IP

	 private  String userSex;   //用户性别

	 private  String userType;   //用户类型   U001 启用   U002  停用   U003  注销  U004  临时账户（用户管理不体现，临时用户管理再提现）

	 private  Integer uorder;   //用户排序

	 private  String remark;    //备注

	 @JSONField(format = "yyyy-MM-dd HH:mm:ss")
	 private Date createTime;

	 


}