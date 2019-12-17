/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-03 16:08:57
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2019-12-16 15:08:43
 */
package com.wjwy.rsda.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.entity.Department;
import com.wjwy.rsda.enums.EnumEntitys;
import com.wjwy.rsda.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import tk.mybatis.mapper.util.StringUtil;

@Service("departmentService")
@Transactional
public class DepartmentService {

    /**
     * 
     * 结构树展示
     */
    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * 单位数据查询
     * 
     * @param parentId
     * @return List<TreeNode>
     */
    public List<Department> list(String parentId) {
        if(StringUtil.isEmpty(parentId)|| parentId.equalsIgnoreCase("{}")){
            parentId=String.valueOf(EnumEntitys.GJD.getValue());
        }
        List<Department> departmentlists = departmentMapper.selectTreeList(parentId);
        return departmentlists;
    }

    /***
     * 新增
     * 
     * @param department
     * @return int
     */
    public int insertUnitTree(Department department) {
        department.setId(UUID.randomUUID().toString().toLowerCase());
        if (StringUtil.isEmpty(department.getParentId())) {
            department.setParentId(String.valueOf(EnumEntitys.GJD.getValue()));
        } 
        department.setCreateTime(new Date());
        return departmentMapper.insertSelective(department);
    }

    /**
     * 更新
     * 
     * @param department
     * @return int
     */
    public int updateUnitTree(Department department) {
        Example example = new Example(Department.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", department.getId());
        return departmentMapper.updateByExampleSelective(department, example);
    }

    /**
     * 删除
     * 
     * @param department
     * @return int
     */
    public int deleteUnitTree(Department department) {
        Example example = new Example(Department.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", department.getId());

        List<Department> departmentNew = departmentMapper.selectByExample(example);
        criteria.andEqualTo("isCandel", false);

        int count = departmentMapper.deleteByExample(example);
      
        if (departmentNew.size() == 0) {
            count = HttpStatus.GONE.value();
        }
        return count;
    }

    /**
     * 
     * @param department
     * @return
     */
	public List<Department> findList(Department department) {
        List<Department> dList = new ArrayList<Department>();
        Example example = new Example(Department.class);
        Criteria criteria = example.createCriteria();
        if (!StringUtil.isEmpty(department.getId())) {
            criteria.andEqualTo("id", department.getId());
        }
            
        if (!StringUtil.isEmpty(department.getParentId())) {
            criteria.andEqualTo("parentId", department.getParentId());
        }
        
        if (!StringUtil.isEmpty(department.getName())) {
            criteria.andEqualTo("name", department.getName());
        }
            dList = departmentMapper.selectByExample(example);
        return dList;
    }

    /**
     * 通过ID查找数据对象
     * @param id
     * @return
     */
	public Department getDept(String id) {
        Example example = new Example(Department.class);
        Criteria criteria = example.createCriteria();
        if (!StringUtil.isEmpty(id)) {
            criteria.andEqualTo("id", id);
        }
		return departmentMapper.selectOneByExample(example);
	}

    /**
     * 通过ID查找子数据集合
     * @param id
     * @return
     */
	public List<Department> getChildren(String id) {
		Example example = new Example(Department.class);
        Criteria criteria = example.createCriteria();
        if (!StringUtil.isEmpty(id)) {
            criteria.andEqualTo("parentId", id);
        }
        criteria.andEqualTo("delFlag", false);
		return departmentMapper.selectByExample(example); 
    } 
    

    /**
     * 单位分页列表查询
     * @param id
     * @param unitType
     * @param name
     * @param limit
     * @param page
     * @return List<Department>
     */
	public PageInfo<Department> findTreeList(String id, String name, Integer unitType, Integer page, Integer limit) {
        PageHelper.startPage(page , limit);
        
        if(StringUtil.isEmpty(id)){
            id = "";
        }
           
        if(StringUtil.isEmpty(name)){
            name = "";
       }

        List<Department> department = departmentMapper.selectTreeOne(id,name,unitType);
    
		if (department == null) {
			return null;
		}
		PageInfo<Department> PageInfoDO = new PageInfo<Department>(department);
		return PageInfoDO;
    }
}