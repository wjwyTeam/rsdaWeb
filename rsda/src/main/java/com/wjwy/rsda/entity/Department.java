/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-04 08:51:31
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2019-12-14 11:56:40
 */
package com.wjwy.rsda.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@ApiModel("单位=>实体")
@Table(name = "sys_dapartment")
public class Department implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @ApiModelProperty("主键")
  private String id;

  /**
   * 父级编号
   */
  @ApiModelProperty("父级编号")
  @Column(name = "parent_id")
  private String parentId;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  @Column(name = "name")
  private String name;

  /**
   * 排序
   */
  @ApiModelProperty("排序")
  @Column(name = "d_order")
  private Integer dorder;

  /**
   * 单位类别
   */
  @ApiModelProperty("单位类别")
  @Column(name = "type")
  private Integer type;

  /**
   * 是否存档
   */
  @ApiModelProperty("是否存档")
  @Column(name = "is_pipe")
  private Boolean isPipe;

  /**
   * 是否删除
   */
  @ApiModelProperty("是否删除")
  @Column(name = "del_flag")
  private Boolean delFlag;

  /**
   * 能都删除
   */
  @ApiModelProperty("是否能都删除")
  @Column(name = "is_candel")
  private Boolean isCandel;


 /**
   * 管档类别
   */
  @ApiModelProperty("管档类别")
  @Column(name = "pipe_type")
  private String pipeType;

  
   /**
   * 简称
   */
  @ApiModelProperty("简称")
  @Column(name = "short_name")
  private String shortName;
  
  /**
   * 创建时间
   */
  @ApiModelProperty("创建时间")
  @Column(name = "create_time")
  @JSONField(format = "yyyy-MM-dd HH:mm:ss")
  private Date createTime;


  @Transient
  private Integer childNum;

  @Transient
  private Boolean isParent;

  /**
   * 子节点
   */
    @Transient
  private List<Department> children;

  
}