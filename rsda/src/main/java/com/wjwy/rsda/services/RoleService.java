/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-06 08:34:07
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2019-12-17 14:27:54
 */
package com.wjwy.rsda.services;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.github.pagehelper.util.StringUtil;
import com.wjwy.rsda.entity.Role;
import com.wjwy.rsda.entity.RoleFunction;
import com.wjwy.rsda.mapper.RoleFunctionMapper;
import com.wjwy.rsda.mapper.RoleMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private RoleFunctionMapper roleFunctionMapper;


	public Logger logger = LoggerFactory.getLogger(RoleService.class);
    /**
     * 列表查询
     * 
     * @param name
     * @param id
     * @return List<Role>
     */
    public List<Role> findList(String id, String name) {
        Example example = new Example(Role.class);
        Criteria criteria = example.createCriteria();
        if (!StringUtil.isEmpty(id)) {
            criteria.andEqualTo("id", id);
        }
        if (!StringUtil.isEmpty(name)) {
            criteria.andEqualTo("name", name);
        }

       

        return roleMapper.selectByExample(example);
    }

    /**
     * 新增
     * 
     * @param role
     * @return int
     */
    public int insertRole(Role role) {
		// 调用功能更新接口
        role.setId(UUID.randomUUID().toString().toLowerCase());
        role.setCreateTime(new Date());
        return roleMapper.insert(role);
    }

    /**
     * 更新
     * 
     * @param role
     * @return int
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
     * @return int
     */
    public int delete(Role role) {
        /**
         * 不可以删除 IsCandel 不能删除的用户
         */

        Example example = new Example(Role.class);
        Criteria criteria = example.createCriteria();

        criteria.andEqualTo("id", role.getId());

        List<Role> roleNew = roleMapper.selectByExample(example);

        criteria.andEqualTo("isCandel", false);
        role.setDelFlag(true);

        int count = roleMapper.updateByExampleSelective(role, example);
        if (roleNew.size() == 0) {
            count = HttpStatus.GONE.value();
        }
        return count;
    }


    /**
     * 根据角色选择功能
     * @param id
     * @param ids
     * @return boolean
     */
	public boolean roleSelFunction(String id, String[] ids) {
		if (id.isEmpty() || id == null) {
			return false;
		}
		RoleFunction roleFunction = new RoleFunction();
		roleFunction.setRoleId(id);
		Example example = new Example(RoleFunction.class);
		Criteria criteria = example.createCriteria();
		// 将当前用户所选角色全部清除，新增
		criteria.andEqualTo("roleId", id);
		logger.info(id, roleFunctionMapper.deleteByExample(example));
		for (String functionId : ids) {
			criteria.andEqualTo("functionId", functionId);
			roleFunction.setRId(UUID.randomUUID().toString().toLowerCase());
			roleFunction.setFunctionId(functionId);
			logger.info(functionId, roleFunctionMapper.insertSelective(roleFunction));
		}

		return true;
	}

    /**
     * 根据角色查询功能
     */
	public boolean getFunction(String id) {
   
    //      List<Role> roles = roleMapper.selectByExample(example);
    //     if (roles == null) {
    //        return false;
    //    }


    //     List<Role> roleNew = new ArrayList<Role>();
    //     for (Role role : roles) {

    //         Example exampleRoleFunction = new Example(RoleFunction.class);
    //         Criteria criteriaRoleFunction = exampleRoleFunction.createCriteria();
    //        criteriaRoleFunction.andEqualTo("roleId", role.getId());
    //          List<RoleFunction> roleFunctionNew = roleFunctionMapper.selectByExample(exampleRoleFunction);

    //         List<Function> functionNewList = new ArrayList<Function>();
    //         if (!roleFunctionNew.isEmpty()) {
    //             for (RoleFunction roleFunction : roleFunctionNew) {
    //                 Function function = new Function();
    //                 Example exampleFunction = new Example(Function.class);
    //                 Criteria criteriaFunction = exampleFunction.createCriteria();
    //                criteriaFunction.andEqualTo("functionId", roleFunction.getFunctionId());
    //                 function = functionMapper.selectOneByExample(exampleFunction);
    //                 functionNewList.add(function);
    //            }
    //            role.setFunctions(functionNewList);
    //             roleNew.add(role);
    //  }

        // }
		return true;
	}

}