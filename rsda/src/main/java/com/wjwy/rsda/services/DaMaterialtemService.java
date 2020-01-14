package com.wjwy.rsda.services;

import java.util.List;
import java.util.UUID;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.entity.DaMaterialtem;
import com.wjwy.rsda.mapper.DaMaterialtemMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import tk.mybatis.mapper.util.StringUtil;

/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2020-01-14 10:37:51
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-14 10:38:44
 */
@Service("daMaterialtemService")
@Transactional
public class DaMaterialtemService {
 @Autowired
 private DaMaterialtemMapper daMaterialtemMapper;

 /**
  * 通过ID获取对象
  * 
  * @param DaMaterialtemId
  * @return
  */
 public DaMaterialtem getById(String id) {
  Example example = new Example(DaMaterialtem.class);
  Criteria criteria = example.createCriteria();
  if (!StringUtil.isEmpty(id)) {
   criteria.andEqualTo("id", id);
  }
  return daMaterialtemMapper.selectOneByExample(example);
 }

 /**
  * 目录管理列表数据查询
  * 
  * @param DaMaterialtem
  * @param page
  * @param limit
  * @return
  */
 public PageInfo<DaMaterialtem> findList(DaMaterialtem DaMaterialtem, Integer page, Integer limit) {
  PageHelper.startPage(page, limit);
  Example example = new Example(DaMaterialtem.class);
  example.setOrderByClause("create_time DESC");
  Criteria criteria = example.createCriteria();
  if (!StringUtil.isEmpty(DaMaterialtem.getId())) {
   criteria.andEqualTo("id", DaMaterialtem.getId());
  }

  List<DaMaterialtem> daMaterialtems = daMaterialtemMapper.selectByExample(example);
  PageInfo<DaMaterialtem> PageInfoDO = new PageInfo<DaMaterialtem>(daMaterialtems);
  return PageInfoDO;
 }

 /**
  * 目录管理表单数据新增
  * 
  * @param DaMaterialtem
  * @return int
  */
 public int daMaterialtemInsert(DaMaterialtem daMaterialtem) {
  daMaterialtem.setId(UUID.randomUUID().toString().toLowerCase());
  return daMaterialtemMapper.insertSelective(daMaterialtem);
 }

 /**
  * 目录管理表单数据更新
  * 
  * @param DaMaterialtem
  * @return int
  */
 public int daMaterialtemUpdate(DaMaterialtem daMaterialtem) {
  Example example = new Example(DaMaterialtem.class);
  Criteria criteria = example.createCriteria();
  criteria.andEqualTo("id", daMaterialtem.getId());
  return daMaterialtemMapper.updateByExampleSelective(daMaterialtem, example);
 }

 /**
  * 目录管理移除数据
  * 
  * @param ids
  * @return int
  */
 public int daMaterialtemRemove(String[] ids) {
  int res = 0;
  for (String id : ids) {
   Example example = new Example(DaMaterialtem.class);
   Criteria criteria = example.createCriteria();
   criteria.andEqualTo("id", id);
   res = daMaterialtemMapper.deleteByExample(example);
  }
  return res;
 }

}