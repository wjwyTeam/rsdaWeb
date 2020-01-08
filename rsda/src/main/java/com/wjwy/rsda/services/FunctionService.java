package com.wjwy.rsda.services;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.common.util.StringUtils;
import com.wjwy.rsda.entity.Function;
import com.wjwy.rsda.enums.EnumEntitys;
import com.wjwy.rsda.mapper.FunctionMapper;

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
public class FunctionService {

    @Autowired
    private FunctionMapper functionMapper;

    /**
     * 增加
     * 
     * @param function
     * @return int
     */
    public int insertFunction(Function function) {
        function.setId(UUID.randomUUID().toString().toLowerCase());
        function.setCreateTime(new Date());
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
    public PageInfo<Function> findList(String id, String functionName,Integer page, Integer limit) {
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
		return functionMapper.selectAll();
	}



	public List<Function> list(String groupId) {
		 if(StringUtil.isEmpty(groupId)|| groupId.equalsIgnoreCase("{}")){
            groupId=String.valueOf(EnumEntitys.GJD.getValue());
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
    

}