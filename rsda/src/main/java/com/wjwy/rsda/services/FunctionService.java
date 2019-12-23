package com.wjwy.rsda.services;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import com.wjwy.rsda.entity.Function;
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
        function.setFunctionId(UUID.randomUUID().toString().toLowerCase());
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

        criteria.andEqualTo("functionId", function.getFunctionId());

        List<Function> functionNew = functionMapper.selectByExample(example);

        criteria.andEqualTo("isCandel", false);
        function.setDelFlag(true);

        int count = functionMapper.updateByExampleSelective(function, example);
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
        criteria.andEqualTo("functionId", function.getFunctionId());
        return functionMapper.updateByExampleSelective(function, example);
    }

    /**
     * 分页查询
     * 
     * @param id
     * @param name
     * @return List<Function>
     */
    public List<Function> findList(String functionId, String functionName) {
        Example example = new Example(Function.class);
        Criteria criteria = example.createCriteria();
        if (!StringUtil.isEmpty(functionId)) {
            criteria.andEqualTo("functionId", functionId);
        }
        if (!StringUtil.isEmpty(functionName)) {
            criteria.andEqualTo("functionName", functionName);
        }

        return functionMapper.selectByExample(example);
    }

	public Set<String> listPerms(String userId) {
		return null;
    }
    
    /**
     * 通过ID查询数据
     * @param functionId
     * @return
     */
	public Function selectFunctionById(String functionId) {
        Example example = new Example(Function.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("functionId", functionId);
		return functionMapper.selectOneByExample(example);
    }
    

    

}