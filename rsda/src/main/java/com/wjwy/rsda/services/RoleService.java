/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-06 08:34:07
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-13 14:05:59
 */
package com.wjwy.rsda.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.wjwy.rsda.common.enums.EnumEntitys;
import com.wjwy.rsda.common.util.StringUtils;
import com.wjwy.rsda.entity.Role;
import com.wjwy.rsda.entity.RoleFunction;
import com.wjwy.rsda.entity.User;
import com.wjwy.rsda.entity.UserRole;
import com.wjwy.rsda.mapper.RoleFunctionMapper;
import com.wjwy.rsda.mapper.RoleMapper;
import com.wjwy.rsda.mapper.UserRoleMapper;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
    private UserRoleMapper userRoleDao;

    @Autowired
    private RoleMapper roleDao;
    @Autowired
    private RoleFunctionMapper roleFunctionMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    public Logger logger = LoggerFactory.getLogger(RoleService.class);

    /**
     * 列表查询
     * 
     * @param name
     * @param id
     * @param limit
     * @param page
     * @return List<Role>
     */
    public PageInfo<Role> findList(String id, String name, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        Example example = new Example(Role.class);
        example.setOrderByClause("create_time DESC");
        Criteria criteria = example.createCriteria();
        if (!StringUtil.isEmpty(id)) {
            criteria.andEqualTo("id", id);
        }
        if (!StringUtil.isEmpty(name)) {
            criteria.andEqualTo("name", name);
        }
        criteria.andEqualTo("delFlag", false);

        Subject subject = SecurityUtils.getSubject();
        if (subject != null && subject.getPrincipals() != null) {
            User userobj = subject.getPrincipals().oneByType(User.class);
            if (userobj != null) {
                Example exampleR = new Example(UserRole.class);
                Criteria criteriaR = exampleR.createCriteria();
                criteriaR.andEqualTo("userId", userobj.getUserId());
                List<UserRole> ur = userRoleDao.selectByExample(exampleR);
                for (UserRole userRole : ur) {
                    Example exampleT = new Example(Role.class);
                    Criteria criteriaT = exampleT.createCriteria();
                    criteriaT.andEqualTo("id", userRole.getRoleId());
                    List<Role> r = roleDao.selectByExample(exampleT);
                    for (Role role : r) {
                        if (!role.getEnName().equals(EnumEntitys.SUPER.getValue())) {
                            criteria.andNotEqualTo("enName", EnumEntitys.SUPER.getValue());
                        }
                    }
                }
            }
        }



        List<Role> roles = roleMapper.selectByExample(example);

        if (roles == null) {
            return null;
        }
        PageInfo<Role> PageInfoDO = new PageInfo<Role>(roles);
        return PageInfoDO;
    }

    /**
     * 新增
     * 
     * @param role
     * @return int
     */
    public int insertRole(Role role) {
        // 调用功能更新接口
        role.setDelFlag(false);
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
     * 
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
     * 
     * @return
     */
    public List<RoleFunction> getFunction(String id) {
        List<RoleFunction> fids = new ArrayList<RoleFunction>();
        Example exampleRoleFunction = new Example(RoleFunction.class);
        Criteria criteriaRoleFunction = exampleRoleFunction.createCriteria();
        criteriaRoleFunction.andEqualTo("roleId", id);

        List<RoleFunction> functions = roleFunctionMapper.selectByExample(exampleRoleFunction);

        for (RoleFunction roleFunction : functions) {
            RoleFunction r = new RoleFunction();
            r.setFunctionId(roleFunction.getFunctionId());
            r.setLAY_CHECKED(true);
            fids.add(r);
        }
        return fids;
    }

    /**
     * 
     * @param id
     * @return
     */
    public Role getId(String id) {

        Example example = new Example(Role.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", id);

        Role roleNew = roleMapper.selectOneByExample(example);
        if (roleNew == null) {
            return null;
        }
        return roleNew;
    }

	public List<Role> getRoleList() {
        Example example = new Example(Role.class);
        example.setOrderByClause("create_time DESC");
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("delFlag", false);
        criteria.andEqualTo("roleStatus", true);
        Subject subject = SecurityUtils.getSubject();
        if (subject != null && subject.getPrincipals() != null) {
            User userobj = subject.getPrincipals().oneByType(User.class);
            if (userobj != null) {
                Example exampleR = new Example(UserRole.class);
                Criteria criteriaR = exampleR.createCriteria();
                criteriaR.andEqualTo("userId", userobj.getUserId());
                List<UserRole> ur = userRoleDao.selectByExample(exampleR);
                for (UserRole userRole : ur) {
                    Example exampleT = new Example(Role.class);
                    Criteria criteriaT = exampleT.createCriteria();
                    criteriaT.andEqualTo("id", userRole.getRoleId());
                    List<Role> r = roleDao.selectByExample(exampleT);
                    for (Role role : r) {
                        if (!role.getEnName().equals(EnumEntitys.SUPER.getValue())) {
                            criteria.andNotEqualTo("enName", EnumEntitys.SUPER.getValue());
                        }
                    }
                }
            }
        }
		return roleMapper.selectByExample(example);
	}

    /**
     * 查询数据
     * @param parameter
     * @return
     */
    public List<Role> getUserRoleList(String userId) {
        List<Role> roles = new ArrayList<Role>();
        Example example = new Example(UserRole.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        List<UserRole> userRole = userRoleMapper.selectByExample(example);
        for (UserRole userRoleList : userRole) {
            Role role = new Role();
            Example exampleR = new Example(Role.class);
            Criteria criteriaR = exampleR.createCriteria();
            criteriaR.andEqualTo("delFlag", false);
            criteriaR.andEqualTo("id", userRoleList.getRoleId());
            criteriaR.andEqualTo("roleStatus", true);
            role = roleMapper.selectOneByExample(exampleR);
            roles.add(role);
        }
        return roles;
    }

    
    /**
     * 根据用户ID查询权限
     * 
     * @param userId 用户ID
     * @return 权限列表
     */
    public Set<String> selectRoleKeys(String userId) {
    
        List<Role> perms = roleMapper.selectRolesByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (Role perm : perms) {
            if (StringUtils.isNotNull(perm)) {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }

	public void getFunctionIds(String parameter) {
	}
}