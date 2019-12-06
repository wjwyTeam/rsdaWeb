package com.wjwy.rsda.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

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
public class Role {

    @ApiModelProperty("主键")
    @Column(name = "id")
    private String id;

    @ApiModelProperty("角色名称")
    @Column(name = "name")
    private String  name;

    
    @ApiModelProperty("角色英文名称")
    @Column(name = "en_name")
    private String  enName;

    @ApiModelProperty("是否可删除")
    @Column(name = "is_candel")
    private Boolean isCandel;

    @ApiModelProperty("创建时间")
    @Column(name = "create_time")
    private Date    createTime;


    @ApiModelProperty("删除标志")
    @Column(name = "del_flag")
    private Boolean delFlag;
    
}