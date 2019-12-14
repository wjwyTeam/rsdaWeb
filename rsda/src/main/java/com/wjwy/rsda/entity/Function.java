/*
 * @Descripttion: 系统功能表结构实体类
 * @version: v0.0.1
 * @Author: zgr
 * @Date: 2019-12-03 15:08:01
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2019-12-11 09:00:14
 */
package com.wjwy.rsda.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户=>实体")
@Table(name = "sys_function")
public class Function implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


    @ApiModelProperty("主键id")
    @Column(name = "function_id")
    private String functionId;
    // 名称
    @ApiModelProperty("名称")
    @Column(name = "function_name")
    private String functionName;
    // 图标
    @ApiModelProperty("图标")
    @Column(name = "icon")
    private String icon;
    // 所属组
    @ApiModelProperty("所属组")
    @Column(name = "group_id")
    private String groupId;
    // 请求地址
    @ApiModelProperty("请求地址")
    @Column(name = "url")
    private String url;
    // 功能备注
    @ApiModelProperty("备注")
    @Column(name = "remark")
    private String remark;
    // 排序字段
    @ApiModelProperty("排序字段")
    @Column(name = "f_order")
    private Integer fOrder;
    // 建立时间

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    @Column(name = "create_time")
    private Date createTime;

    @ApiModelProperty("删除标志")
    @Column(name = "del_flag")
    private Boolean delFlag;

    @ApiModelProperty("是否可删除")
    @Column(name = "is_candel")
    private Boolean isCandel;
}