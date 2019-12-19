/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-18 15:52:25
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2019-12-19 17:25:39
 */
package com.wjwy.rsda.mapper;

import com.wjwy.rsda.common.TkMapper;
import com.wjwy.rsda.entity.OperLog;

import org.apache.ibatis.annotations.Insert;
public interface OperLogMapper extends TkMapper<OperLog> {


  @Insert("insert into sys_oper_log(title, business_type, method, request_method, operator_type, oper_name, dept_name, oper_url, oper_ip, oper_location, oper_param, json_result, status, error_msg, oper_time) values (#{title}, #{businessType}, #{method}, #{requestMethod}, #{operatorType}, #{operName}, #{deptName}, #{operUrl}, #{operIp}, #{operLocation}, #{operParam}, #{jsonResult}, #{status}, #{errorMsg}, now())")
	void insertOperlog(OperLog operLog);
  
}