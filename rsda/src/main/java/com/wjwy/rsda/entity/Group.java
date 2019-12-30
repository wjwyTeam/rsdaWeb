/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-09 17:47:32
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2019-12-30 08:48:25
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

/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-09 17:47:32
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2019-12-09 17:48:53
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("功能组=>实体")
@Table(name = "sys_group")
public class Group implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


    @ApiModelProperty("功能主键ID")
    @Column(name = "group_id")
    private String groupId; // 功能组id
    @ApiModelProperty("功能组名称")
    @Column(name = "group_name")
    private String groupName; // 功能组名称
    @ApiModelProperty("图标")
    @Column(name = "icon")
    private String icon; // icon
    @ApiModelProperty("备注")
    @Column(name = "remark")
    private String remark; // 功能组备注
    @ApiModelProperty("功能组排序")
    @Column(name = "f_order")
    private Integer fOrder; // 功能组排序
    @ApiModelProperty("创建时间")
    @Column(name = "create_time")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime; // 创建时间
    @ApiModelProperty("是否可删除项")
    @Column(name = " is_candel")
    private Boolean isCandel; // 是否可删除项
    @ApiModelProperty("是否删除")
    @Column(name = "del_flag")
    private Boolean delFlag; // 是否删除

}