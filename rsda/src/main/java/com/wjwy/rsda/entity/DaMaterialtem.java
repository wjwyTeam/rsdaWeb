/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2020-01-14 08:57:31
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-16 11:13:58
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
 * @Date: 2020-01-14 08:57:31
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-14 08:59:06
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
@ApiModel("M1-材料管理=>实体类")
@Table(name = "da_materialtem")
public class DaMaterialtem {
 @ApiModelProperty("主键ID")
 @Column(name = "id")
 private String id;
 @ApiModelProperty("人员ID")
 @Column(name = "pers_id")
 private String persId;
 @ApiModelProperty("类型")
 @Column(name = "type")
 private String type;
 @ApiModelProperty("排序")
 @Column(name = "sort")
 private Integer sort;
 @ApiModelProperty("编号")
 @Column(name = "code")
 private String code;
 @ApiModelProperty("描述")
 @Column(name = "name")
 private String name;
 @ApiModelProperty("页数")
 @Column(name = "share_number")
 private Integer shareNumber;
 @ApiModelProperty("页码")
 @Column(name = "page_number")
 private Integer pageNumber;
 @ApiModelProperty("备注")
 @Column(name = "remark")
 private String remark;
 @ApiModelProperty("状态")
 @Column(name = "status")
 private String status;
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
 private List<DaMaterialtem> children;

}
