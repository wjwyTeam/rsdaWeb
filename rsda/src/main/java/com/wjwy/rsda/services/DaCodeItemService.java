package com.wjwy.rsda.services;

import java.util.List;
import java.util.UUID;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.entity.DaCodeItem;
import com.wjwy.rsda.mapper.DaCodeItemMapper;

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
 * @Date: 2020-01-14 10:05:37
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-14 10:07:28
 */
@Service("daCodeItemService")
@Transactional
public class DaCodeItemService {
 @Autowired
 private DaCodeItemMapper daCodeItemMapper;

 /**
  * 通过ID获取对象
  * 
  * @param DaCodeItemId
  * @return
  */
 public DaCodeItem getById(String id) {
  Example example = new Example(DaCodeItem.class);
  Criteria criteria = example.createCriteria();
  if (!StringUtil.isEmpty(id)) {
   criteria.andEqualTo("id", id);
  }
  return daCodeItemMapper.selectOneByExample(example);
 }

 /**
  * 目录管理列表数据查询
  * 
  * @param DaCodeItem
  * @param page
  * @param limit
  * @return
  */
 public PageInfo<DaCodeItem> findList(DaCodeItem daCodeItem, Integer page, Integer limit) {
  PageHelper.startPage(page, limit);
  Example example = new Example(DaCodeItem.class);
  example.setOrderByClause("create_time DESC");
  Criteria criteria = example.createCriteria();
  if (!StringUtil.isEmpty(daCodeItem.getId())) {
   criteria.andEqualTo("id", daCodeItem.getId());
  }

  List<DaCodeItem> daCodeItems = daCodeItemMapper.selectByExample(example);
  PageInfo<DaCodeItem> PageInfoDO = new PageInfo<DaCodeItem>(daCodeItems);
  return PageInfoDO;
 }

 /**
  * 目录管理表单数据新增
  * 
  * @param DaCodeItem
  * @return int
  */
 public int daCodeItemInsert(DaCodeItem daCodeItem) {
  daCodeItem.setId(UUID.randomUUID().toString().toLowerCase());
  return daCodeItemMapper.insertSelective(daCodeItem);
 }

 /**
  * 目录管理表单数据更新
  * 
  * @param DaCodeItem
  * @return int
  */
 public int daCodeItemUpdate(DaCodeItem daCodeItem) {
  Example example = new Example(DaCodeItem.class);
  Criteria criteria = example.createCriteria();
  criteria.andEqualTo("id", daCodeItem.getId());
  return daCodeItemMapper.updateByExampleSelective(daCodeItem, example);
 }

 /**
  * 目录管理移除数据
  * 
  * @param ids
  * @return int
  */
 public int daCodeItemRemove(String[] ids) {
  int res = 0;
  for (String id : ids) {
   Example example = new Example(DaCodeItem.class);
   Criteria criteria = example.createCriteria();
   criteria.andEqualTo("id", id);
   res = daCodeItemMapper.deleteByExample(example);
  }
  return res;
 }

 
}