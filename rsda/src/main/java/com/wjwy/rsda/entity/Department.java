/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-04 08:51:31
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2019-12-06 11:26:07
 */
package com.wjwy.rsda.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.alibaba.fastjson.annotation.JSONField;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@ToString
@AllArgsConstructor 
@NoArgsConstructor 
@ApiModel("单位=>实体")
@Table(name = "sys_dapartment")
public class Department{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

     /**
     * 父级编号
     */
    @Column(name = "parent_id")
    private String parentId;

    
     /**
     * 名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 排序
     */
    @Column(name = "d_order")
    private BigDecimal dOrder;

    /**
     * 单位类别
     */
    @Column(name = "type")
    private Integer type;

     /**
     * 是否存档
     */
    @Column(name = "is_pipe")
    private Boolean isPipe;

    /**
     * 是否删除
     */
    @Column(name = "del_flag")
    private Boolean delFlag;

      /**
     * 能都删除
     */
    @Column(name = "is_candel")
    private Boolean isCandel;
    
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 是否有节点
     */
    @Transient
    private Integer childNum;
}