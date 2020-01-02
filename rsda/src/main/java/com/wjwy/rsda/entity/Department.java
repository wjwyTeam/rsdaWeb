/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-04 08:51:31
 * @LastEditors  : zgr
 * @LastEditTime : 2019-12-18 16:52:34
 */
package com.wjwy.rsda.entity;

import java.io.Serializable;
import java.util.Date;

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
public class Department implements Serializable {

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
  @Column(name = "unit_type")
  private Integer unitType;

  /**
   * 单位类别
   */
  @ApiModelProperty("单位类别名称")
  @Column(name = "unit_type_name")
  private String unitTypeName;
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
  private Integer pipeType;

  /**
   * 管档类别名称
   */
  @ApiModelProperty("管档类别名称")
  @Column(name = "pipe_type_name")
  private String pipeTypeName;

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
  private Boolean isParent;

  @Transient
  private String[] ids;

  @Transient
  private Boolean option;

}