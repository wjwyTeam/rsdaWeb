/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-19 18:06:40
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2019-12-26 14:28:55
 */
package com.wjwy.rsda.entity;

import javax.persistence.Table;

import com.wjwy.rsda.common.util.BaseEntity;
import com.wjwy.rsda.common.util.Excel;
import com.wjwy.rsda.common.util.Excel.ColumnType;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 字典类型表 sys_dict_type
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("字典类型=>实体")
@Table(name = "sys_dict_type")
public class DictType extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /** 字典主键 */
    @Excel(name = "字典主键", cellType = ColumnType.NUMERIC)
    private Long dictId;

    /** 字典名称 */
    @Excel(name = "字典名称")
    private String dictName;

    /** 字典类型 */
    @Excel(name = "字典类型")
    private String dictType;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private Boolean status;

}
