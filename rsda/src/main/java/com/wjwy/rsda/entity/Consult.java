/*
 * @Descripttion: 
 * @version: v 1.0
 * @Author: ZHANGQI
 * @Date: 2020-01-02 18:12:32
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-03 09:12:45
 */
package com.wjwy.rsda.entity;

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
@ApiModel("查阅管理=>实体")
@Table(name = "consult_apply")
public class Consult {
 @ApiModelProperty("查阅申请ID")
 @Column(name = "consult_id")
 private String consultId;
 @ApiModelProperty("查阅对象")
 @Column(name = "person_id")
 private String personId;
 @ApiModelProperty("查档人员1")
 @Column(name = "consult_user_one")
 private String consultUserOne;
 @ApiModelProperty("查档人员2")
 @Column(name = "consult_user_two")
 private String consultUserTwo;
 @ApiModelProperty("查档理由")
 @Column(name = "consult_reason")
 private String consultReason;
 @ApiModelProperty("查阅类别（0，纸质 1，电子）")
 @Column(name = "consult_type")
 private String consultType;

 @ApiModelProperty("查阅动作(0,查询 1，摘录 ， 2，复制)")
 @Column(name = "consult_active")

 private String consultActive;
 @ApiModelProperty("查档内容")
 @Column(name = "consult_content")
 private String consultContent;
 @ApiModelProperty("备注")
 @Column(name = "remarks")
 private String remarks;
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
 @ApiModelProperty("上传附件ID")
 @Column(name = "filed_id")
 private String filedId;

}