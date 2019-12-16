/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-04 09:43:55
 * @LastEditors: zgr
 * @LastEditTime: 2019-12-15 19:14:21
 */
package com.wjwy.rsda.mapper;
import java.util.List;
import com.wjwy.rsda.common.TkMapper;
import com.wjwy.rsda.entity.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface DepartmentMapper extends TkMapper<Department> {

    /**
     * 通过ID查找当前组织下的所有单位
     * 
     * @param parentId
     * @return List<Department>
     */
    @Select("SELECT a.*,case (select count(1) from sys_dapartment where parent_id=a.id AND del_flag=false) when 0 then FALSE else TRUE end as is_parent FROM sys_dapartment a where a.parent_id = #{parentId} and a.del_flag = false")
    List<Department> selectTreeList(String parentId);

    /**
     * 查询当前二级数据
     * @param id
     * @return List<Department>
     */
    @Select("select * from sys_dapartment where id=#{id} and del_flag=false UNION ALL select * from sys_dapartment where parent_id=#{id} and del_flag=false ORDER BY d_order ASC")
	List<Department> selectTreeOne(String id);
   
}