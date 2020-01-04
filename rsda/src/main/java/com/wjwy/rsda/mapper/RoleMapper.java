/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-06 11:18:35
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-03 15:52:33
 */
package com.wjwy.rsda.mapper;

import java.util.List;

import com.wjwy.rsda.entity.Role;

import org.apache.ibatis.annotations.Select;



public interface RoleMapper extends com.wjwy.rsda.common.TkMapper<Role> {

 /**
  * 根据用户ID查询权限
  * 
  * @param userId
  * @return
  */
 @Select("SELECT DISTINCT r.id,r.NAME,r.role_key,r.role_status,r.data_scope,r.create_time,r.del_flag,r.create_time FROM sys_role r"
   + "LEFT JOIN sys_user_role ur ON ur.role_id = r.id LEFT JOIN sys_user u ON u.user_id=ur.user_id LEFT JOIN sys_dapartment d ON u.dept_id=d.id"
   + "WHERE r.del_flag = false AND r.is_candel = false and ur.user_id=#{userId}")
	List<Role> selectRolesByUserId(String userId);

}
