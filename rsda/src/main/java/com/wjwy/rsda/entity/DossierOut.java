/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2020-01-03 13:55:13
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-06 17:43:04
 */
package com.wjwy.rsda.entity;

import javax.persistence.Column;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("C2-转出档案=>实体类")
@Table(name = "dossier_out")
public class DossierOut {
 @ApiModelProperty("档案转入ID")
 @Column(name = "transfer_id")
 private String transferId;
 @ApiModelProperty("转入对象ID")
 @Column(name = "person_id")
 private String personId;
 @ApiModelProperty("转出单位")
 @Column(name = "transferout_dept")
 private String transferoutDept;
 @ApiModelProperty("转入单位")
 @Column(name = "transferin_dept")
 private String transferinDept;
 @ApiModelProperty("转递原因")
 @Column(name = "transfer_reason")
 private String transferReason;
 @ApiModelProperty("转入内容")
 @Column(name = "transfer_content")
 private String transferContent;
 @ApiModelProperty("转出时间")

 @Column(name = "transfer_time")
 private String transferTime;
 @ApiModelProperty("收件人")
 @Column(name = "consignee")
 private String consignee;
 @ApiModelProperty("转出原因")
 @Column(name = "transferout_reason")
 private String transferoutReason;
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