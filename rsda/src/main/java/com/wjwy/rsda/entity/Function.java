/*
 * @Descripttion: 系统功能表结构实体类
 * @version: v0.0.1
 * @Author: zgr
 * @Date: 2019-12-03 15:08:01
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-06 17:46:31
 */
package com.wjwy.rsda.entity;

import java.io.Serializable;
import java.util.Date;

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

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("F1-菜单管理=>实体类")
@Table(name = "sys_function")
public class Function implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    @Column(name = "id")
    private String id;

    @ApiModelProperty("groupid")
    @Column(name = "pid")
    private String pid;
    // 名称
    @ApiModelProperty("名称")
    @Column(name = "function_name")
    private String functionName;
    // 图标
    @ApiModelProperty("图标")
    @Column(name = "icon")
    private String icon;

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
    @Column(name = "forder")
    private Integer forder;
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

    /** 打开方式：menuItem页签 menuBlank新窗口 */
    @ApiModelProperty("打开方式：menuItem页签 menuBlank新窗口")
    @Column(name = "target")
    private String target;

    @ApiModelProperty("类型:0目录,1菜单,2按钮")
    @Column(name = "function_type")
    /** 类型:0目录,1菜单,2按钮 */
    private String functionType;
    @ApiModelProperty("菜单状态:0显示,1隐藏")
    @Column(name = "visible")
    /** 菜单状态:0显示,1隐藏 */
    private Boolean visible;
    @ApiModelProperty("权限字符串 ")
    @Column(name = "perms")
    /** 权限字符串 */
    private String perms;

    @Transient
    private Boolean haveChild;

}