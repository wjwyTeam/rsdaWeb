package com.wjwy.rsda.entity;

/*
 * @Descripttion: 
 * @version: v 1.0
 * @Author: ZHANGQI
 * @String: 2020-01-03 09:31:57
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-06 14:57:58
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
@ApiModel("借阅管理=>实体")
@Table(name = "borrow_apply")
public class Borrow {
 @ApiModelProperty("借阅iD")
 @Column(name = "borrow_id")
 private String borrowId;
 @ApiModelProperty("借阅对象")
 @Column(name = "person_id")
 private String personId;
 @ApiModelProperty("借阅人")
 @Column(name = "borrow_name")
 private String borrowName;
 @ApiModelProperty("工作单位以及职务")
 @Column(name = "dept_duty")
 private String deptDuty;
 @ApiModelProperty("政治面貌")
 @Column(name = "politic_countenance")
 private String politicCountenance;
 @ApiModelProperty("借阅理由")
 @Column(name = "borrow_reason")
 private String borrowReason;
 @ApiModelProperty("借阅开始时间")

 @Column(name = "borrow_start_time")
 private String borrowStartTime;
 @ApiModelProperty("借阅结束时间")

 @Column(name = "borrow_end_time")
 private String borrowEndTime;
 @ApiModelProperty("备注")
 @Column(name = "remarks")
 private String remarks;
 @ApiModelProperty("附件上传")
 @Column(name = "filed_id")
 private String filedId;
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