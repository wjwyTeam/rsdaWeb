/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2019-12-30 17:14:00
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-07 10:40:43
 */
package com.wjwy.rsda.mapper;

import java.util.List;

import com.wjwy.rsda.common.tool.server.system.Online;

import org.apache.ibatis.annotations.Select;

public interface UserOnlineMapper extends com.wjwy.rsda.common.TkMapper<Online> {

 @Select("select session_id, login_name, dept_name, ipaddr, login_location, browser, os, status, start_timestamp, last_access_time, expire_time"
   + " from sys_user_online WHERE last_access_time <= #{lastAccessTime} ORDER BY last_access_time ASC")
 List<Online> selectOnlineByExpired(String lastAccessTime);

}