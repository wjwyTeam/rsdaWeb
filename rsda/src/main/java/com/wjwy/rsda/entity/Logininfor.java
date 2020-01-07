/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-19 17:04:51
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-07 16:00:36
 */
package com.wjwy.rsda.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Table;

import com.wjwy.rsda.common.util.Excel;
import com.wjwy.rsda.common.util.Excel.ColumnType;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 系统访问记录表 sys_logininfor
 * 
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("J1-登录日志=>实体类")
@Table(name = "sys_logininfor")
public class Logininfor implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @Excel(name = "序号", cellType = ColumnType.NUMERIC)
    private String infoId;

    /** 用户账号 */
    @Excel(name = "用户账号")
    private String loginName;

    /** 登录状态 0成功 1失败 */
    @Excel(name = "登录状态", readConverterExp = "0=成功,1=失败")
    private String status;

    /** 登录IP地址 */
    @Excel(name = "登录地址")
    private String ipaddr;

    /** 登录地点 */
    @Excel(name = "登录地点")
    private String loginLocation;

    /** 浏览器类型 */
    @Excel(name = "浏览器")
    private String browser;

    /** 操作系统 */
    @Excel(name = "操作系统")
    private String os;

    /** 提示消息 */
    @Excel(name = "提示消息")
    private String msg;

    /** 访问时间 */
    @Excel(name = "访问时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;

}