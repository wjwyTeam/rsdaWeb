package com.wjwy.rsda.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-06 08:06:41
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2019-12-06 08:07:23
 */


@Data
@ToString
@AllArgsConstructor 
@NoArgsConstructor 
@Table(name = "sys_user_role_r")
public class UserRole {

    @ApiModelProperty("主键")
    @Column(name = "r_id")
    private String rId;

    @ApiModelProperty("用户主键")
    @Column(name = "user_id")
    private String userId;

    @ApiModelProperty("角色主键")
    @Column(name = "role_id")
    private String roleId;

}