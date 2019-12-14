/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-04 09:43:55
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2019-12-11 19:06:45
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
    @Select("SELECT a.*,(select count(1) from sys_dapartment where parent_id=a.id) as child_num FROM sys_dapartment a where a.parent_id = #{parentId} and a.del_flag = false")
    List<Department> selectTreeList(String parentId);

    /**
     * 查询当前二级数据
     * @param id
     * @return List<Department>
     */
    @Select("select * from sys_dapartment where id=#{id} and del_flag=false UNION select * from sys_dapartment where parent_id=#{id} and del_flag=false ORDER BY d_order ASC")
	List<Department> selectTreeOne(String id);
   
}