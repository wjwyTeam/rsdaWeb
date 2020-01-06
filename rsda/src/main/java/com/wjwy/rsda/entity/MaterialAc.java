package com.wjwy.rsda.entity;

/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2020-01-06 15:42:02
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-06 17:44:30
 */

import javax.persistence.Column;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("D2-散材料归档列表=>实体类")
@Table(name = "material_ac")
public class MaterialAc {

 @ApiModelProperty("散材料归档列表ID")
 @Column(name = "ac_id")
 private String acId;
}

