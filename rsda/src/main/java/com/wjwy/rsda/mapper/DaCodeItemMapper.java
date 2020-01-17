package com.wjwy.rsda.mapper;

import java.util.List;

import com.wjwy.rsda.common.TkMapper;
import com.wjwy.rsda.entity.DaCodeItem;

import org.apache.ibatis.annotations.Select;

/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2020-01-14 10:13:12
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-14 10:13:52
 */
public interface DaCodeItemMapper extends TkMapper<DaCodeItem> {

 /**
  * 四类 九类本表特殊处理
  * @param pid
  * @return
  */
 @Select("SELECT a.*,case (select count(1) from da_codeitem where pid=a.id AND del_flag=false) when 0 then FALSE else TRUE end as cid"
   + " FROM da_codeitem a where a.pid = #{pid} and a.del_flag=false and a.cid = true order by a.sort ASC")
	List<DaCodeItem> selectCid(String pid);

  @Select("SELECT * FROM da_codeitem where pid = #{pid} and del_flag=false and cid = false order by sort ASC")
  List<DaCodeItem> selectT(String pid);
}
