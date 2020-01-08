package com.wjwy.rsda.services;

import java.util.List;
import java.util.UUID;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.entity.DossierOut;
import com.wjwy.rsda.mapper.DossierOutMapper;

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
 * @Date: 2020-01-03 14:03:20
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-03 14:04:04
 */
@Service("dossierOutService")
@Transactional
public class DossierOutService {
 @Autowired
 private DossierOutMapper dossierOutMapper;

 /**
  * 通过ID获取对象
  * 
  * @param DossierOutId
  * @return
  */
 public DossierOut getById(String dossierOutId) {
  Example example = new Example(DossierOut.class);
  Criteria criteria = example.createCriteria();
  if (!StringUtil.isEmpty(dossierOutId)) {
   criteria.andEqualTo("transferId", dossierOutId);
  }
  return dossierOutMapper.selectOneByExample(example);
 }

 /**
  * 档案转入管理列表数据查询
  * 
  * @param DossierOut
  * @param page
  * @param limit
  * @return
  */
 public PageInfo<DossierOut> findList(DossierOut dossierOut, Integer page, Integer limit) {
  PageHelper.startPage(page, limit);
  Example example = new Example(DossierOut.class);
  example.setOrderByClause("create_time DESC");
  Criteria criteria = example.createCriteria();
  if (!StringUtil.isEmpty(dossierOut.getTransferId())) {
   criteria.andEqualTo("transferId", dossierOut.getTransferId());
  }

  List<DossierOut> dossierOuts = dossierOutMapper.selectByExample(example);
  PageInfo<DossierOut> PageInfoDO = new PageInfo<DossierOut>(dossierOuts);
  return PageInfoDO;
 }

 /**
  * 档案转入管理表单数据新增
  * 
  * @param DossierOut
  * @return int
  */
 public int dossierOutInsert(DossierOut dossierOut) {
  dossierOut.setTransferId(UUID.randomUUID().toString().toLowerCase());
  return dossierOutMapper.insertSelective(dossierOut);
 }

 /**
  * 档案转入管理表单数据更新
  * 
  * @param DossierOut
  * @return int
  */
 public int dossierOutUpdate(DossierOut dossierOut) {
  Example example = new Example(DossierOut.class);
  Criteria criteria = example.createCriteria();
  criteria.andEqualTo("transferId", dossierOut.getTransferId());
  return dossierOutMapper.updateByExampleSelective(dossierOut, example);
 }

 /**
  * 档案转入管理移除数据
  * 
  * @param ids
  * @return int
  */
 public int dossierOutRemove(String[] ids) {
  int res = 0;
  for (String id : ids) {
   Example example = new Example(DossierOut.class);
   Criteria criteria = example.createCriteria();
   criteria.andEqualTo("transferId", id);
   res = dossierOutMapper.deleteByExample(example);
  }
  return res;
 }



}