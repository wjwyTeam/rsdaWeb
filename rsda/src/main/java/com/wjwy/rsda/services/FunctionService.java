package com.wjwy.rsda.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.common.util.StringUtils;
import com.wjwy.rsda.entity.Function;
import com.wjwy.rsda.entity.RoleFunction;
import com.wjwy.rsda.entity.UserRole;
import com.wjwy.rsda.common.enums.EnumEntitys;
import com.wjwy.rsda.mapper.FunctionMapper;
import com.wjwy.rsda.mapper.RoleFunctionMapper;
import com.wjwy.rsda.mapper.UserRoleMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import tk.mybatis.mapper.util.StringUtil;

/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-09 17:08:33
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2019-12-09 17:22:36
 */
@Service("functionService")
@Transactional
public class FunctionService extends BaseService {

    @Autowired
    private FunctionMapper functionMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleFunctionMapper roleFunctionMapper;

    /**
     * 增加
     * 
     * @param function
     * @return int
     */
    public int insertFunction(Function function) {
        function.setId(UUID.randomUUID().toString().toLowerCase());
        function.setCreateTime(new Date());
        if (StringUtil.isEmpty(function.getPid())) {
            function.setPid(String.valueOf(EnumEntitys.GJD.getValue()));
        }
        function.setForder(Integer
                .parseInt(this.getMaxCodeById(functionMapper.getMaxCodeById(""), "", functionMapper.selectMax())));
        return functionMapper.insert(function);
    }

    /**
     * 
     * @param function
     * @return int
     */
    public int delete(Function function) {
        /**
         * 不可以删除 IsCandel 不能删除的用户
         */

        Example example = new Example(Function.class);
        Criteria criteria = example.createCriteria();

        criteria.andEqualTo("id", function.getId());

        List<Function> functionNew = functionMapper.selectByExample(example);

        criteria.andEqualTo("isCandel", false);

        int count = functionMapper.deleteByExample(example);
        if (functionNew.size() == 0) {
            count = HttpStatus.GONE.value();
        }
        return count;
    }

    /**
     * 更新
     * 
     * @param function
     * @return int
     */
    public int updateFunction(Function function) {
        Example example = new Example(Function.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", function.getId());
        return functionMapper.updateByExampleSelective(function, example);
    }

    /**
     * 分页查询
     * 
     * @param id
     * @param name
     * @return List<Function>
     */
    public PageInfo<Function> findList(String id, String functionName, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        Example example = new Example(Function.class);
        example.setOrderByClause("create_time DESC");
        Criteria criteria = example.createCriteria();
        if (!StringUtil.isEmpty(id)) {
            criteria.andEqualTo("id", id);
        }
        if (!StringUtil.isEmpty(functionName)) {
            criteria.andEqualTo("functionName", functionName);
        }

        List<Function> functions = functionMapper.selectByExample(example);
        PageInfo<Function> PageInfoDO = new PageInfo<Function>(functions);
        return PageInfoDO;
    }

    public Set<String> listPerms(String userId) {
        return null;
    }

    /**
     * 通过ID查询数据
     * 
     * @param id
     * @return
     */
    public Function selectFunctionById(String id) {
        Example example = new Example(Function.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", id);
        return functionMapper.selectOneByExample(example);
    }

    /**
     * 
     * @return
     */
    public List<Function> getList() {
        return functionMapper.findList();
    }

    /**
     * 
     * @param groupId
     * @return
     */
    public List<Function> list(String groupId) {
        if (StringUtil.isEmpty(groupId) || groupId.equalsIgnoreCase("{}")) {
            groupId = String.valueOf(EnumEntitys.GJD.getValue());
        }
        List<Function> functionlists = functionMapper.selectTreeList(groupId);
        return functionlists;
    }

    /**
     * 根据用户ID查询权限
     * 
     * @param userId 用户ID
     * @return 权限列表
     */
    public Set<String> selectPermsByUserId(String userId) {
        List<String> perms = functionMapper.selectRolesByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotEmpty(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 角色关联菜单树
     * 
     * @param userId
     * @return
     */
    public List<Function> findUserList(String userId) {
        List<Function> f = new ArrayList<Function>();

        Example example = new Example(UserRole.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        List<UserRole> userRoles = userRoleMapper.selectByExample(example);
        if (userRoles == null) {
            return null;
        }
        for (UserRole userRole : userRoles) {
            Example exampleU = new Example(RoleFunction.class);
            Criteria criteriaU = exampleU.createCriteria();
            criteriaU.andEqualTo("roleId", userRole.getRoleId());
            List<RoleFunction> roleFunctions = roleFunctionMapper.selectByExample(exampleU);
            for (RoleFunction roleFunctionList : roleFunctions) {
                Example exampleF = new Example(Function.class);
                Criteria criteriaF = exampleF.createCriteria();
                // exampleF.setOrderByClause("forder ASC");
                criteriaF.andEqualTo("visible", true);
                criteriaF.andEqualTo("id", roleFunctionList.getFunctionId());
                List<Function> functions = functionMapper.selectByExample(exampleF);
                for (Function fun : functions) {
                    Function ffq = new Function();
                    ffq = fun;
                    f.add(ffq);
                    // Example exampleFs = new Example(Function.class);
                    // Criteria criteriaFs = exampleFs.createCriteria();
                    // criteriaFs.andEqualTo("pid", fun.getId());
                    // List<Function> functionList = functionMapper.selectByExample(exampleFs);
                    // for (Function funT : functionList) {
                    // Function fft = new Function();
                    // fft = funT;
                    // f.add(fft);
                    // }

                }
            }
        }
        LinkedHashSet<Function> hashSet = new LinkedHashSet<Function>(f);

        ArrayList<Function> listW = new ArrayList<Function>(hashSet);

        // 这里就会自动根据规则进行排序
        Collections.sort(listW);

        // 输出查看结果
        // for (int i = 0; i < listW.size(); i++) {
        //     Function stu = listW.get(i);
        //     System.out.println("排序:" + stu.getForder() + "   时间:" + stu.getCreateTime() + "   主键:" + stu.getId());
        // }
        return listW;

    }

    /**
     * 角色查询菜单Id
     * 
     * @param roleid
     * @return
     */
    public List<RoleFunction> findRoleList(String roleid) {
        Example exampleU = new Example(RoleFunction.class);
        Criteria criteriaU = exampleU.createCriteria();
        criteriaU.andEqualTo("roleId", roleid);
        return roleFunctionMapper.selectByExample(exampleU);
    }

}