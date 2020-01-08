/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-19 17:30:10
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-08 09:46:34
 */
package com.wjwy.rsda.services;

import java.util.List;
import java.util.UUID;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.entity.Logininfor;
import com.wjwy.rsda.mapper.LogininforMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import tk.mybatis.mapper.util.StringUtil;

/**
 * 系统访问日志情况信息 服务层
 * 
 * @param <Logininfor>
 */
@Service("logininforService")
@Transactional
public class LogininforService {

    @Autowired
    private LogininforMapper logininforMapper;

    /**
     * 
     * @param logininfor
     * @return
     */
    public int logininforUpdate(Logininfor logininfor) {
        Example example = new Example(Logininfor.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("infoId", logininfor.getInfoId());
        return logininforMapper.updateByExampleSelective(logininfor, example);
    }

    /**
     * 
     * @param infoId
     * @return
     */
    public Logininfor getById(String infoId) {
        Example example = new Example(Logininfor.class);
        Criteria criteria = example.createCriteria();
        if (!StringUtil.isEmpty(infoId)) {
            criteria.andEqualTo("infoId", infoId);
        }
        return logininforMapper.selectOneByExample(example);
    }

    /**
     * 新增系统登录日志
     * 
     * @param logininfor 访问日志对象
     * @return
     */
    public int logininforInsert(Logininfor logininfor) {
        logininfor.setInfoId(UUID.randomUUID().toString().toLowerCase());
        return logininforMapper.insertSelective(logininfor);

    }

    /**
     * 查询系统登录日志集合
     * 
     * @param logininfor 访问日志对象
     * @return 登录记录集合
     */
    public PageInfo<Logininfor> findList(Logininfor logininfor, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        Example example = new Example(Logininfor.class);
        example.setOrderByClause("create_time DESC");
        Criteria criteria = example.createCriteria();
        if (!StringUtil.isEmpty(logininfor.getInfoId())) {
            criteria.andEqualTo("infoId", logininfor.getInfoId());
        }
        if (!StringUtil.isEmpty(logininfor.getLoginName())) {
            criteria.andEqualTo("loginName", logininfor.getLoginName());
        }

        List<Logininfor> logininfors = logininforMapper.selectByExample(example);
        PageInfo<Logininfor> PageInfoDO = new PageInfo<Logininfor>(logininfors);
        return PageInfoDO;
    }

    /**
     * 批量删除系统登录日志
     * 
     * @param ids 需要删除的数据
     * @return
     */
    public int logininforRemove(String[] ids) {
        int res = 0;
        for (String id : ids) {
            Example example = new Example(Logininfor.class);
            Criteria criteria = example.createCriteria();
            criteria.andEqualTo("infoId", id);
            res = logininforMapper.deleteByExample(example);
        }
        return res;
    }

    /**
     * 清空系统登录日志
     */
    public void clear() {
        logininforMapper.clear();
    }

}
