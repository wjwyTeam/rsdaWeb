package com.wjwy.rsda.services.per;

import java.util.List;
import java.util.UUID;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.entity.per.Personal;
import com.wjwy.rsda.mapper.per.PersonalMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import tk.mybatis.mapper.util.StringUtil;

/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2020-01-02 10:05:06
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-02 10:19:02
 */
@Service("personalService")
public class PersonalService {
 @Autowired
 private PersonalMapper personalMapper;

 /**
  * 通过ID获取对象
  * 
  * @param personalId
  * @return
  */
 public Personal getById(String personalId) {
  return personalMapper.selectByPrimaryKey(personalId);
 }

 /**
  * 人员管理列表数据查询
  * 
  * @param personal
  * @param page
  * @param limit
  * @return
  */
 public PageInfo<Personal> findList(Personal personal, Integer page, Integer limit) {
  PageHelper.startPage(page, limit);
  Example example = new Example(Personal.class);
  Criteria criteria = example.createCriteria();
  if (!StringUtil.isEmpty(personal.getPersonalId())) {
   criteria.andEqualTo("personalId", personal.getPersonalId());
  }
  if (!StringUtil.isEmpty(personal.getPersonalName())) {
   criteria.andEqualTo("personalName", personal.getPersonalName());
  }

  List<Personal> personals = personalMapper.selectByExample(example);
  PageInfo<Personal> PageInfoDO = new PageInfo<Personal>(personals);
  return PageInfoDO;
 }

 /**
  * 人员管理表单数据新增
  * 
  * @param personal
  * @return int
  */
 public int personalInsert(Personal personal) {
  personal.setPersonalId(UUID.randomUUID().toString().toLowerCase());
  return personalMapper.insertSelective(personal);
 }

 /**
  * 人员管理表单数据更新
  * 
  * @param personal
  * @return int
  */
 public int personalUpdate(Personal personal) {
  Example example = new Example(Personal.class);
  Criteria criteria = example.createCriteria();
  criteria.andEqualTo("personalId", personal.getPersonalId());
  return personalMapper.updateByExampleSelective(personal, example);
 }

 /**
  * 人员管理移除数据
  * 
  * @param ids
  * @return int
  */
 public int personalRemove(String[] ids) {
  int res = 0;
  for (String id : ids) {
   Example example = new Example(Personal.class);
   Criteria criteria = example.createCriteria();
   criteria.andEqualTo("personalId", id);
   res = personalMapper.deleteByExample(example);
  }
  return res;
 }
}