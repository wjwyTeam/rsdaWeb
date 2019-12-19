/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-06 15:02:00
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2019-12-19 09:05:43
 */
package com.wjwy.rsda.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-06 08:13:11
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2019-12-06 08:14:05
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("角色=>实体")
@Table(name = "sys_role")
public class Role implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


    @ApiModelProperty("主键")
    @Column(name = "id")
    private String id;

    @ApiModelProperty("角色名称")
    @Column(name = "name")
    private String name;

    @ApiModelProperty("角色英文名称")
    @Column(name = "en_name")
    private String enName;


    /** 角色权限 */
    @ApiModelProperty("角色权限")
    @Column(name = "role_Key")
    private String roleKey;

     /** 数据范围 */
     @ApiModelProperty("数据范围:1=所有数据权限,2=自定义数据权限,3=本部门数据权限,4=本部门及以下数据权限")
     @Column(name = "data_scope")
    private String dataScope;

    /** 角色状态 0=正常,1=停用 */
    @ApiModelProperty("角色状态 0=正常,1=停用")
    @Column(name = "role_status")
    private String roleStatus;



    @ApiModelProperty("是否可删除")
    @Column(name = "is_candel")
    private Boolean isCandel;

    @ApiModelProperty("创建时间")
    @Column(name = "create_time")
    private Date createTime;

    @ApiModelProperty("删除标志")
    @Column(name = "del_flag")
    private Boolean delFlag;


     /** 部门组（数据权限） */
     @Transient
     @ApiModelProperty("部门组（数据权限）")
     private Long[] deptIds;

      /** 部门组（数据权限） */
      @Transient
      @ApiModelProperty("功能组（数据权限）")
      private Long[] functionsIds;

}