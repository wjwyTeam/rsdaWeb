package com.wjwy.rsda.mapper;

import java.util.List;

import com.wjwy.rsda.common.TkMapper;
import com.wjwy.rsda.entity.Function;

import org.apache.ibatis.annotations.Select;

/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-09 17:21:56
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2019-12-24 10:05:30
 */
public  interface FunctionMapper extends TkMapper<Function> {


 @Select("SELECT a.*,case (select count(1) from sys_function where pid=a.id AND del_flag=false) when 0 then FALSE else TRUE end as haveChild FROM sys_function a where a.pid = #{groupId} and a.del_flag = false order by a.forder ASC")
	List<Function> selectTreeList(String groupId);

/**
	* 权限功能
	* @param userId
	* @return
 */
	@Select("select distinct m.perms from sys_function m "
			+ " left join sys_role_function rm on m.id = rm.function_id"
			+ " left join sys_user_role ur on rm.role_id = ur.role_id "
			+ "where ur.user_id = #{userId}" )
	List<String> selectRolesByUserId(String userId);
    
}