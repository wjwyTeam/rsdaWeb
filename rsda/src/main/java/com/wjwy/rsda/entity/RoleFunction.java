/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-09 17:37:40
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-11 08:29:43
 */
package com.wjwy.rsda.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sys_role_function")
public class RoleFunction implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @Column(name = "r_id")
    private String rId;

    @ApiModelProperty("功能主键")
    @Column(name = "function_id")
    private String functionId;

    @ApiModelProperty("角色主键")
    @Column(name = "role_id")
    private String roleId;


    @Transient
    private Boolean checked;
}