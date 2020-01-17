package com.wjwy.rsda.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Table;
import io.swagger.annotations.ApiModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2020-01-02 09:43:23
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-02 09:58:46
 */

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("E2-人员管理=>实体类")
@Table(name = "person_dossier")
public class Personal {

 @ApiModelProperty("档案主键")
 @Column(name = "personal_id")
 private String personalId;
 @ApiModelProperty("人员名称")
 @Column(name = "personal_name")
 private String personalName;
 @ApiModelProperty("出生年月")
 @Column(name = "birth_date")
 private String birthDate;
 @ApiModelProperty("性别")
 @Column(name = "personal_sex")
 private String personalSex;

 @ApiModelProperty("档案照片")
 @Column(name = "personal_photo")
 private String personalPhoto;
 @ApiModelProperty("拼音码")
 @Column(name = "spell_name")
 private String spellName;
 @ApiModelProperty("年龄")
 @Column(name = "personal_age")
 private Integer personalAge;

 @ApiModelProperty("参加工作时间")
 @Column(name = "work_time")
 private String workTime;
 @ApiModelProperty("入党时间")
 @Column(name = "party_time")
 private String partyTime;
 @ApiModelProperty("身份证号码")
 @Column(name = "personal_no")
 private String personalNo;
 @ApiModelProperty("出生地址")
 @Column(name = "birth_address")
 private String birthAddress;

 @ApiModelProperty("籍贯")
 @Column(name = "native_place")
 private String nativePlace;
 @ApiModelProperty("政治面貌")
 @Column(name = "political_outlook")
 private String politicalOutlook;

 @ApiModelProperty("工龄")
 @Column(name = "work_age")
 private Integer workAge;

 @ApiModelProperty("工作范围以及职务")
 @Column(name = "work_duty")
 private String workDuty;

 @ApiModelProperty("任职时间")
 @Column(name = "appointment_time")
 private String appointmentTime;

 @ApiModelProperty("最高学历")
 @Column(name = "highest_degreel")
 private String highestDegreel;

 @ApiModelProperty("毕业院校")
 @Column(name = "graduate_school")
 private String graduateSchool;

 @ApiModelProperty("专业")
 @Column(name = "major")
 private String major;

 @ApiModelProperty("全日制最高学历，需要关联查询")
 @Column(name = "highest_education")
 private String highestEducation;

 @ApiModelProperty("最高学位")
 @Column(name = "highest_degreew")
 private String highestDegreew;
 @ApiModelProperty("人员类别")
 @Column(name = "personal_type")
 private String personalType;
 @ApiModelProperty("备案人员")
 @Column(name = "record_personne")
 private Boolean recordPersonne;
 @ApiModelProperty("管理类别")
 @Column(name = "management_category")
 private String managementCategory;

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

 @ApiModelProperty("转入时间")
 @Column(name = "transfer_time")

 private String transferTime;
 @ApiModelProperty("档案状态")
 @Column(name = "dossier_status")
 private String dossierStatus;

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

 @ApiModelProperty("创建时间")
 @Column(name = "create_time")
 private Date createTime;
 @ApiModelProperty("是否删除")
 @Column(name = "del_flag")
 private Boolean delFlag;
 @ApiModelProperty("是否能删除")
 @Column(name = "is_candel")
 private Boolean isCandel;

 @ApiModelProperty("创建人")
 @Column(name = "create_by")
 private String createBy;

 @ApiModelProperty("所属机构")
 @Column(name = "dept_id")
 private String deptId;


}