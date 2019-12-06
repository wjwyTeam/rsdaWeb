/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-03 16:08:57
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2019-12-04 17:27:04
 */
package com.wjwy.rsda.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wjwy.rsda.common.TreeNode;
import com.wjwy.rsda.common.util.UUIDUtils;
import com.wjwy.rsda.entity.Department;
import com.wjwy.rsda.mapper.DepartmentMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

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
     * @return
     */
    public List<TreeNode> list(String parentId) {
        // 通过Id获取列
        List<TreeNode> treeNodes = new ArrayList<>();
        List<Department> departmentlists = departmentMapper.selectTreeList(parentId);

        for (Department dept : departmentlists) {
            Boolean spread = dept.getChildNum() == 1 ? true : false;
            treeNodes.add(new TreeNode(dept.getId(), dept.getParentId(), dept.getName(), spread));
        }
        return treeNodes;
    }

    /***
     * 新增
     * 
     * @param department
     * @return
     */
    public int insertUnitTree(Department department) {
        department.setId(UUIDUtils.uuid().toLowerCase());
        department.setCreateTime(new Date());
        return departmentMapper.insertSelective(department);
    }

    /**
     * 更新
     * 
     * @param department
     * @return
     */
    public int updateUnitTree(Department department) {
        Example example = new Example(Department.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", department.getId());
        return departmentMapper.updateByExample(department, example);
    }

    /**
     * 删除
     * 
     * @param department
     * @return
     */
    public int deleteUnitTree(Department department) {
        Example example = new Example(Department.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", department.getId());
        criteria.andEqualTo("isCandel", true);
        department.setDelFlag(false);
        return departmentMapper.updateByExample(department, example);
    }
}