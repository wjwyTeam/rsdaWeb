/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2020-01-03 11:45:57
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-06 17:43:13
 */
package com.wjwy.rsda.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("C1-转入档案=>实体类")
@Table(name = "dossier_into")
public class DossierInto {
 @ApiModelProperty("档案转入ID")
 @Column(name = "transfer_id")
 private String transferId;
 @ApiModelProperty("转入对象ID")
 @Column(name = "person_id")
 private String personId;
 @ApiModelProperty("转入中心")
 @Column(name = "transfer_center")
 private String transferCenter;
 @ApiModelProperty("申请单位")
 @Column(name = "applicant_unit")
 private String applicantUnit;
 @ApiModelProperty("转入原因")
 @Column(name = "transfer_reason")
 private String transferReason;
 @ApiModelProperty("转入内容")
 @Column(name = "transfer_content")
 private String transferContent;
 @ApiModelProperty("转出时间")

 @Column(name = "transfer_time")
 private String transferTime;
 @ApiModelProperty("发件人")
 @Column(name = "poster")
 private String poster;
 @ApiModelProperty("转字号(呼)")
 @Column(name = "transfer_short_name")
 private String transferShortName;
 @ApiModelProperty("转字号(干档字)")
 @Column(name = "transfer_short_no")
 private String transferShortNo;
 @ApiModelProperty("备注")
 @Column(name = "remarks")
 private String remarks;
 @ApiModelProperty("文件上传")
 @Column(name = "file_id")
 private String fileId;
 @ApiModelProperty("是否可删除")
 @Column(name = "is_candel")
 private Boolean isCandel;

 @ApiModelProperty("创建时间")
 @Column(name = "create_time")
 private String createTime;
 @ApiModelProperty("删除标记")
 @Column(name = "del_flag")
 private Boolean delFlag;

}