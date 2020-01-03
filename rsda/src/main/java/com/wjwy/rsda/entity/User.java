/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: zgr
 * @Date: 2019-11-30 22:58:25
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-03 14:56:31
 */
package com.wjwy.rsda.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.alibaba.fastjson.annotation.JSONField;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户=>实体")
@Table(name = "sys_user")
public class User implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("ID")
	@Column(name = "user_id")
	private String userId; // 表主键

	@ApiModelProperty("名")
	@Column(name = "user_name")
	private String userName; // 名

	@ApiModelProperty("密码")
	@Column(name = "pass_word")
	private String passWord; // 密码

	@ApiModelProperty("姓名")
	@Column(name = "user_real_name")
	private String userRealName; // 姓名

	@ApiModelProperty("头像")
	@Column(name = "base64_photo")
	private String base64Photo; // 照片的base64码

	@ApiModelProperty("手机")
	@Column(name = "phone")
	private String phone; // 的联系电话

	@ApiModelProperty("绑定所在单位（向下级联）")
	@Column(name = "dept_id")
	private String deptId; // 绑定所在单位（向下级联）

	@ApiModelProperty("工作单位")
	@Column(name = "work_dept")
	private String workDept; // 工作单位

	@ApiModelProperty("职务")
	@Column(name = "duty")
	private String duty; // 职务

	@ApiModelProperty("绑定IP")
	@Column(name = "user_ip")
	private String userIp; // 绑定IP

	@ApiModelProperty("性别")
	@Column(name = "user_sex")
	private String userSex; // 性别

	@ApiModelProperty("类型   U001 启用   U002  停用   U003  注销  U004  临时账户（管理不体现，临时管理再提现）")
	@Column(name = "user_type")
	private String userType; // 类型 U001 启用 U002 停用 U003 注销 U004 临时账户（管理不体现，临时管理再提现）

	@ApiModelProperty("排序")
	@Column(name = "u_order")
	private Integer uorder; // 排序

	@ApiModelProperty("备注")
	@Column(name = "remark")
	private String remark; // 备注

	@ApiModelProperty("是否删除")
	@Column(name = "is_candel")
	private Boolean isCandel; // 是否删除

	@ApiModelProperty("删除标记")
	@Column(name = "del_flag")
	private Boolean delFlag; // 删除标记

	@ApiModelProperty("创建时间")
	@Column(name = "create_time")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createTime; // 创建时间

	@ApiModelProperty("指定路径")
	@Column(name = "index_url")
	private String indexUrl;

	@Transient
	@ApiModelProperty(name = "角色列表")
	private List<Role> roles;

	@Transient
	@ApiModelProperty(name = "是否分配")
	private Boolean iSRole;

	public boolean isAdmin() {
		return isAdmin(this.userId);
	}

	public static boolean isAdmin(String userId) {
		return userId != null;
	}

}