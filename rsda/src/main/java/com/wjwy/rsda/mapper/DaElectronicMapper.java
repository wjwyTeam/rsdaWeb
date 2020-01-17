package com.wjwy.rsda.mapper;

import java.util.List;

import com.wjwy.rsda.common.TkMapper;
import com.wjwy.rsda.entity.DaElectronic;

import org.apache.ibatis.annotations.Select;

/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2020-01-14 10:26:38
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-14 10:27:26
 */
public interface DaElectronicMapper extends TkMapper<DaElectronic> {

 /**
  * 
  * @param personId
  * @param itemCode
  * @param id
  * @return
  */
 @Select("select * from da_electronic where pers_id =#{personId} and item_code=#{itemCode} and material_id =#{id} order by sort ASC")
	List<DaElectronic> selectMater(String personId, String itemCode, String id);

 
}