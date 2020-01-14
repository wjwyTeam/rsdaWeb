package com.wjwy.rsda.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2020-01-14 08:53:47
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-14 08:56:05
 */
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("L1-图像管理=>实体类")
@Table(name = "da_electronic")
public class DaElectronic {
 @ApiModelProperty("主键ID")
 @Column(name = "id")
 private String id;
 @ApiModelProperty("关联ID")
 @Column(name = "elec_id")
 private String elecId;
 @ApiModelProperty("人员ID")
 @Column(name = "pers_id")
 private String persId;
 @ApiModelProperty("类型")
 @Column(name = "type_id")
 private String typeId;
 @ApiModelProperty("排序")
 @Column(name = "sort")
 private Integer sort;
 @ApiModelProperty("照片名称")
 @Column(name = "file_name")
 private String fileName;
 @ApiModelProperty("描述名称")
 @Column(name = "name")
 private String name;
 @ApiModelProperty("MD5加密")
 @Column(name = "md5")
 private String md5;
 @ApiModelProperty("删除标记")
 @Column(name = "del_flag")
 private Boolean delFlag;
 @ApiModelProperty("创建时间")
 @Column(name = "create_time")
 private Date createTime;
 @ApiModelProperty("创建人")
 @Column(name = "create_by")
 private String createBy;
}
