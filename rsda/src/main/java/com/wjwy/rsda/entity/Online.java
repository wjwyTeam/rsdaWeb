/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-19 14:35:18
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-08 14:05:39
 */
package com.wjwy.rsda.entity;

import java.util.Date;

import javax.persistence.Table;

import com.wjwy.rsda.common.enums.EnumEntitys;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 当前在线会话 sys_user_online
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sys_user_online")
public class Online {

    /** 用户会话id */
    private String sessionId;

    /** 部门名称 */
    private String deptName;

    /** 登录名称 */
    private String loginName;

    /** 登录IP地址 */
    private String ipaddr;

    /** 登录地址 */
    private String loginLocation;

    /** 浏览器类型 */
    private String browser;

    /** 操作系统 */
    private String os;

    /** session创建时间 */
    private Date startTimestamp;

    /** session最后访问时间 */
    private Date lastAccessTime;

    /** 超时时间，单位为分钟 */
    private Long expireTime;

    /** 在线状态 */
    private EnumEntitys status = EnumEntitys.ONLINE;

}
