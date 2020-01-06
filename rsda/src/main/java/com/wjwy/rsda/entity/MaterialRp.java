package com.wjwy.rsda.entity;

/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2020-01-06 15:20:49
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-06 15:56:00
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
@ApiModel("散材料上报=>实体")
@Table(name = "material_rp")
public class MaterialRp {
 @ApiModelProperty("散材料上报ID")
 @Column(name = "rp_id")
 private String rpId;

}

