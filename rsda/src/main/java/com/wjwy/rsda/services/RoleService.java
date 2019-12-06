/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-06 08:34:07
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2019-12-06 11:24:13
 */
package com.wjwy.rsda.services;

import java.util.List;

import com.wjwy.rsda.common.util.UUIDUtils;
import com.wjwy.rsda.entity.Role;
import com.wjwy.rsda.mapper.RoleMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-06 08:34:07
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2019-12-06 08:46:48
 */
@Service("roleService")
@Transactional
public class RoleService {
    @Autowired 
    private RoleMapper roleMapper;

    /**
     * 列表查询
     * 
     * @param name
     * @param id
     * @return
     */
    public List<Role> findList(String name, String id) {
        Example example = new Example(Role.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", id);
        criteria.andEqualTo("name", name);
        return roleMapper.selectByExample(example);
    }

    /**
     * 新增
     * 
     * @param role
     * @return
     */
    public int insertRole(Role role) {
        role.setId(UUIDUtils.uuid().toLowerCase());
        return roleMapper.insert(role);
    }

    /**
     * 更新
     * 
     * @param role
     * @return
     */
    public int updateRole(Role role) {
        Example example = new Example(Role.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", role.getId());
        return roleMapper.updateByExampleSelective(role, example);
    }

    /**
     * 删除
     * 
     * @param role
     * @return
     */
    public int delete(Role role) {
        /**
         * 不可以删除 IsCandel 不能删除的用户
         */
        Example example = new Example(Role.class);
        Criteria criteria = example.createCriteria();

        criteria.andEqualTo("userId", role.getId());
        criteria.andEqualTo("isCandel", true);

        role.setDelFlag(false);
        int count = roleMapper.updateByExampleSelective(role, example);
        return count;
    }

}