/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-04 09:43:55
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2019-12-18 11:08:48
 */
package com.wjwy.rsda.mapper;

import java.util.List;
import com.wjwy.rsda.common.TkMapper;
import com.wjwy.rsda.entity.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface DepartmentMapper extends TkMapper<Department> {

    /**
     * 通过ID查找当前组织下的所有单位
     * 
     * @param parentId
     * @return List<Department>
     */
    @Select("SELECT a.*,case (select count(1) from sys_dapartment where parent_id=a.id AND del_flag=false) when 0 then FALSE else TRUE end as is_parent FROM sys_dapartment a where a.parent_id = #{parentId} and a.del_flag = false order by a.d_order ASC")
    List<Department> selectTreeList(String parentId);

    /**
     * 查询当前二级数据
     * @param id 单位主键ID
     * @param unitType
     * @param name
     * @param limit
     * @param page
     * @return List<Department>
     */
    @Select("  select * from sys_dapartment where  (#{id} <> ''  and id = #{id} )   and  (#{name} = ''  or name like concat('%', #{name} ,'%'))  and (#{unitType} is null or unit_type=#{unitType}) and del_flag=false "
            + " union all   "
            + " (select * from sys_dapartment where  (#{id} = ''  or parent_id= #{id})   and  (#{name} = ''  or name like concat('%', #{name} ,'%')) and (#{unitType} is null or unit_type=#{unitType}) and del_flag=false "
            + " ) ORDER BY  d_order ASC")
    List<Department> selectTreeOne(String id, String name, Integer unitType);


    /**
	 * 获取Order策略方法
	 * @param maxCodeById
	 * @param string
	 * @param i
	 * @return
	 */
    @Select("SELECT  MAX(d_order) FROM  sys_dapartment  where d_order  LIKE CONCAT(#{pstr,jdbcType=VARCHAR}, '%')")
	String getMaxCodeById(@Param("pstr")String pstr);

}