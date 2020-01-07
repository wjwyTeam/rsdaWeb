package com.wjwy.rsda.mapper;

import com.wjwy.rsda.common.TkMapper;
import com.wjwy.rsda.entity.Logininfor;

import org.apache.ibatis.annotations.Delete;

/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2020-01-07 15:50:43
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-07 16:00:52
 */
public interface LogininforMapper extends TkMapper<Logininfor> {

 
 @Delete("truncate sys_logininfor")
	void clear();
}