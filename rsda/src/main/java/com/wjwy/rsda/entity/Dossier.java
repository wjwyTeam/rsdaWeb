/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @String: 2020-01-02 14:48:17
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-14 16:48:50
 */
package com.wjwy.rsda.entity;

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
@ApiModel("A1-案卷管理=>实体类")
@Table(name = "dossier_manage")
public class Dossier {
 @ApiModelProperty("案卷管理主键")
 @Column(name = "dossier_id")
 private String dossierId;
 @ApiModelProperty("档案号")
 @Column(name = "dossier_no")
 private String dossierNo;
 @ApiModelProperty("档案箱号")
 @Column(name = "dossier_box_no")
 private String dossierBoxNo;
 @ApiModelProperty("档案正副本")
 @Column(name = "dossier_charter")
 private String dossierCharter;
 @ApiModelProperty("来档单位BH")
 @Column(name = "department_id")
 private String departmentId;
 @ApiModelProperty("来档单位MC")
 @Column(name = "department_name")
 private String departmentName;
 @ApiModelProperty("姓名")
 @Column(name = "person_name")
 private String personName;
 @ApiModelProperty("转入时间")
 @Column(name = "transfer_time")

 private String transferTime;
 @ApiModelProperty("档案状态")
 @Column(name = "dossier_status")
 private String dossierStatus;
 @ApiModelProperty("工作单位及职务")
 @Column(name = "person_duty")
 private String personDuty;

 @ApiModelProperty("创建时间")
 @Column(name = "create_time")
 private String createTime;
 @ApiModelProperty("是否删除")
 @Column(name = "del_flag")
 private Boolean delFlag;
 @ApiModelProperty("是否能删除")
 @Column(name = "is_candel")
 private Boolean isCandel;

 @ApiModelProperty("性别")
 @Column(name = "person_sex")
 private String personSex;
 @ApiModelProperty("籍贯")
 @Column(name = "native_place")
 private String nativePlace;
 @ApiModelProperty("政治面貌")
 @Column(name = "political_outlook")
 private String politicalOutlook;
 @ApiModelProperty("身份证号")
 @Column(name = "person_no")
 private String personNo;
 @ApiModelProperty("出生年月")
 @Column(name = "birth_date")
 private String birthDate;
 @ApiModelProperty("人员类别")
 @Column(name = "personal_type")
 private String personalType;
 @ApiModelProperty("管理类别")
 @Column(name = "management_category")
 private String managementCategory;
 @ApiModelProperty("档案份数")
 @Column(name = "dossier_fs")
 private Integer dossierFs;
 @ApiModelProperty("档案卷数")
 @Column(name = "dossier_js")
 private Integer dossierJs;
 @ApiModelProperty("转出单位BH")
 @Column(name = "deptout_id")
 private String deptoutId;
 @ApiModelProperty("转出单位MC")
 @Column(name = "deptout_name")
 private String deptoutName;
 @ApiModelProperty("转出时间")
 @Column(name = "transferout_time")
 private String transferoutTime;



 
}