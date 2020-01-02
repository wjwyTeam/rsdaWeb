package com.wjwy.rsda.entity.per;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;

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
@ApiModel("案卷管理=>实体")
@Table(name = "personal_infomation")
public class Dossier {
 @ApiModelProperty("案卷管理主键")
 @Column(name = "dossier_id")
 private String dossierId;
 @ApiModelProperty("档案号")
 @Column(name = "dossier_no")
 private String dossierNo;
 @ApiModelProperty("档案箱号")
 @Column(name = "dossier_box_no")
 private String dossierBox;
 @ApiModelProperty("档案正副本")    
 @Column(name = "dossier_ charter" )  
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
 @JSONField(format = "yyyy-MM-dd HH:mm:ss")
 private Date transferTime;
 @ApiModelProperty("档案状态")
 @Column(name = "dossier_status")
 private String dossierStatus;
 @ApiModelProperty("工作单位及职务")
 @Column(name = "person_duty")
 private String personDuty;
 @JSONField(format = "yyyy-MM-dd HH:mm:ss")
 @ApiModelProperty("创建时间")
 @Column(name = "create_time")
 private Date createTime;
 @ApiModelProperty("是否删除")
 @Column(name = "del_flag")
 private Boolean delFlag;
 @ApiModelProperty("是否能删除")
 @Column(name = "is_candel")
 private Boolean isCandel;
}