package com.unis.personnel.common.vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 张琪
 * @date 2019年11月20日
 * @Description 
 */

@Getter
@Setter
@ToString
@ApiModel("列表>实体")
public class UserVO extends BaseEntity{
	/**
	  * 用户ID
	 */
	@ApiModelProperty("用户ID")
	private String userId;
	
	/**
	 * 用户名称
	 */
	@ApiModelProperty("用户名称")
	private String userName;
	
	/**
	 * 密码
	 */
	@ApiModelProperty("密码")
	private String passWord;
	
	/**
	 * 用户实际姓名
	 */
	@ApiModelProperty("用户实际姓名")
	private String userRealName;
	
	/**
	 * 用户头像
	 */
	@ApiModelProperty("用户头像")
	private String base64Photo;
	
	/**
	 * 用户电话
	 */
	@ApiModelProperty("用户名称")
	private String phone;
	
	/**
	 * 用户部门编号
	 */
	@ApiModelProperty("用户名称")
	private String deptId;
	
	/**
	 * 用户部门名称
	 */
	@ApiModelProperty("用户名称")
	private String workDept;
	
	/**
	 * 用户职责
	 */
	@ApiModelProperty("用户名称")
	private String duty;
	
	/**
	 * 备注
	 */
	@ApiModelProperty("备注")
	private String remark;
	
	/**
	 * 用户IP
	 */
	@ApiModelProperty("用户IP")
	private String userIp;
	
	/**
	 * 用户ID (用户id + 001)
	 */
	@ApiModelProperty("用户类型")
	private String userType;
	
	/**
	 * 排序
	 */
	@ApiModelProperty("排序")
	private Integer uorder;
	
	/*
	 * 用户邮箱
	 */
	@ApiModelProperty("用户邮箱")
	 private  String userEmail;   //用户邮箱
	
	@ApiModelProperty("备用字段")
	 private  String filed1;   //备用字段
	@ApiModelProperty("备用字段")
	 private  String filed2;   //备用字段
	@ApiModelProperty("备用字段")
	 private  String filed3;   //备用字段
	@ApiModelProperty("备用字段")
	 private  String filed4;   //备用字段
	@ApiModelProperty("备用字段")
	 private  String filed5;   //备用字段
	@ApiModelProperty("备用字段")
	 private  String filed6;   //备用字段
	
}
