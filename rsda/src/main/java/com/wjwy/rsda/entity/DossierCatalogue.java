package com.wjwy.rsda.entity;



/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2020-01-16 14:26:26
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-17 09:11:51
 */
import javax.persistence.Transient;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("目录JSON=>实体类")
public class DossierCatalogue {
 @Transient
 @ApiModelProperty("当前用户ID")
 private String currentPid;
 @Transient
 @ApiModelProperty("当前用户名称")
 private String currentName;
 @Transient
 @ApiModelProperty("当前用户IP")
 private String currentIp;
 @Transient
 @ApiModelProperty("人员档案")
 private Person person;

 public void getField() {
  Subject subject = SecurityUtils.getSubject();
  if (subject.getPrincipal() != null) {
   User user = subject.getPrincipals().oneByType(User.class);
   if (user != null) {
    this.currentPid = user.getUserId();
    this.currentName = user.getUserName();
    this.currentIp = user.getUserIp();
   }
  }
 }
}