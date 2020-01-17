/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2020-01-14 08:48:44
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-16 17:01:19
 */
package com.wjwy.rsda.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import io.swagger.annotations.ApiModelProperty;

/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2020-01-14 08:48:44
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-14 08:49:05
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
@ApiModel("K1-目录管理=>实体类")
@Table(name = "da_codeitem")
public class DaCodeItem {


 @ApiModelProperty("主键ID")
 @Column(name = "id")
 private String id;

 @ApiModelProperty("名称")
 @Column(name = "name")
 private String name;
 @ApiModelProperty("父级id")
 @Column(name = "pid")
 private String pid;
 @ApiModelProperty("子级")
 @Column(name = "cid")
 private Boolean cid;
 @ApiModelProperty("拼写")
 @Column(name = "spell")
 private String spell;
 @ApiModelProperty("拼写web")
 @Column(name = "spell_wb")
 private String spellWb;
 @ApiModelProperty("排序")
 @Column(name = "sort")
 private Integer sort;
 @ApiModelProperty("删除标记")
 @Column(name = "del_flag")
 private Boolean delFlag;
 @ApiModelProperty("创建时间")
 @Column(name = "create_time")
 private Date createTime;
 @ApiModelProperty("创建人")
 @Column(name = "create_by")
 private String createBy;
 @Transient
 @ApiModelProperty("阅档权限子类")
 private List<DaCodeItem> children;
 
}