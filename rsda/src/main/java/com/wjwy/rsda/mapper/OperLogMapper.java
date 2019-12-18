/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-18 15:52:25
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2019-12-18 15:54:25
 */
package com.wjwy.rsda.mapper;

import com.wjwy.rsda.common.TkMapper;
import com.wjwy.rsda.entity.OperLog;

import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface OperLogMapper extends TkMapper<OperLog> {
  
}