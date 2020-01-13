/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-06 15:02:00
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-13 08:24:32
 */
package com.wjwy.rsda.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-06 08:06:41
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2019-12-06 08:07:23
 */

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sys_user_role")
public class UserRole implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


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