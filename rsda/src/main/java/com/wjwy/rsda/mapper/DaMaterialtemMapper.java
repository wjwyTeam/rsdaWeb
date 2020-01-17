package com.wjwy.rsda.mapper;

import java.util.List;

import com.wjwy.rsda.common.TkMapper;
import com.wjwy.rsda.entity.DaMaterialtem;

import org.apache.ibatis.annotations.Select;

/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2020-01-14 10:41:01
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-14 10:41:39
 */
public interface DaMaterialtemMapper extends TkMapper<DaMaterialtem> {
 /**
  * 四类 九类本表特殊处理 =》 联表特殊处理
  * 
  * @param personId 人员ID id 目录ID
  * @return
  */
 @Select("SELECT * FROM da_materialtem WHERE person_id = #{personId} AND item_code = #{id} ORDER BY sort ASC  ")
 List<DaMaterialtem> selectByMater(String personId, String id);
}