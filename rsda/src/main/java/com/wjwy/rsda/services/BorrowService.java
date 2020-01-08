package com.wjwy.rsda.services;

import java.util.List;
import java.util.UUID;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.entity.Borrow;
import com.wjwy.rsda.mapper.BorrowMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import tk.mybatis.mapper.util.StringUtil;

@Service("borrowService")
@Transactional
public class BorrowService {
 @Autowired
 private BorrowMapper borrowMapper;

 /**
  * 通过ID获取对象
  * 
  * @param BorrowId
  * @return
  */
 public Borrow getById(String borrowId) {
  Example example = new Example(Borrow.class);
  Criteria criteria = example.createCriteria();
  if (!StringUtil.isEmpty(borrowId)) {
   criteria.andEqualTo("borrowId", borrowId);
  }

  return borrowMapper.selectOneByExample(example);
 }

 /**
  * 查阅管理列表数据查询
  * 
  * @param Borrow
  * @param page
  * @param limit
  * @return
  */
 public PageInfo<Borrow> findList(Borrow borrow, Integer page, Integer limit) {
  PageHelper.startPage(page, limit);
  Example example = new Example(Borrow.class);
  example.setOrderByClause("create_time DESC");
  Criteria criteria = example.createCriteria();
  if (!StringUtil.isEmpty(borrow.getBorrowId())) {
   criteria.andEqualTo("borrowId", borrow.getBorrowId());
  }

  List<Borrow> borrows = borrowMapper.selectByExample(example);
  PageInfo<Borrow> PageInfoDO = new PageInfo<Borrow>(borrows);
  return PageInfoDO;
 }

 /**
  * 查阅管理表单数据新增
  * 
  * @param Borrow
  * @return int
  */
 public int borrowInsert(Borrow borrow) {
  borrow.setBorrowId(UUID.randomUUID().toString().toLowerCase());
  return borrowMapper.insertSelective(borrow);
 }

 /**
  * 查阅管理表单数据更新
  * 
  * @param Borrow
  * @return int
  */
 public int borrowUpdate(Borrow borrow) {
  Example example = new Example(Borrow.class);
  Criteria criteria = example.createCriteria();
  criteria.andEqualTo("borrowId", borrow.getBorrowId());
  return borrowMapper.updateByExampleSelective(borrow, example);
 }

 /**
  * 查阅管理移除数据
  * 
  * @param ids
  * @return int
  */
 public int borrowRemove(String[] ids) {
  int res = 0;
  for (String id : ids) {
   Example example = new Example(Borrow.class);
   Criteria criteria = example.createCriteria();
   criteria.andEqualTo("borrowId", id);
   res = borrowMapper.deleteByExample(example);
  }
  return res;
 }

}