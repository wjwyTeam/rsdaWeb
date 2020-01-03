package com.wjwy.rsda.services;

import java.util.List;
import java.util.UUID;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.entity.DossierInto;
import com.wjwy.rsda.mapper.DossierIntoMapper;

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
 * @Date: 2020-01-03 11:33:48
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-03 11:34:58
 */

@Service("dossierIntoService")
@Transactional
public class DossierIntoService {
  @Autowired
 private DossierIntoMapper dossierIntoMapper;

 /**
  * 通过ID获取对象
  * 
  * @param DossierIntoId
  * @return
  */
 public DossierInto getById(String dossierIntoId) {
  return dossierIntoMapper.selectByPrimaryKey(dossierIntoId);
 }

 /**
  * 档案转入管理列表数据查询
  * 
  * @param DossierInto
  * @param page
  * @param limit
  * @return
  */
 public PageInfo<DossierInto> findList(DossierInto dossierInto, Integer page, Integer limit) {
  PageHelper.startPage(page, limit);
  Example example = new Example(DossierInto.class);
  Criteria criteria = example.createCriteria();
  if (!StringUtil.isEmpty(dossierInto.getTransferId())) {
   criteria.andEqualTo("transferId", dossierInto.getTransferId());
  }

  List<DossierInto> dossierIntos = dossierIntoMapper.selectByExample(example);
  PageInfo<DossierInto> PageInfoDO = new PageInfo<DossierInto>(dossierIntos);
  return PageInfoDO;
 }

 /**
  * 档案转入管理表单数据新增
  * 
  * @param DossierInto
  * @return int
  */
 public int dossierIntoInsert(DossierInto dossierInto) {
  dossierInto.setTransferId(UUID.randomUUID().toString().toLowerCase());
  return dossierIntoMapper.insertSelective(dossierInto);
 }

 /**
  * 档案转入管理表单数据更新
  * 
  * @param DossierInto
  * @return int
  */
 public int dossierIntoUpdate(DossierInto dossierInto) {
  Example example = new Example(DossierInto.class);
  Criteria criteria = example.createCriteria();
  criteria.andEqualTo("transferId", dossierInto.getTransferId());
  return dossierIntoMapper.updateByExampleSelective(dossierInto, example);
 }

 /**
  * 档案转入管理移除数据
  * 
  * @param ids
  * @return int
  */
 public int dossierIntoRemove(String[] ids) {
  int res = 0;
  for (String id : ids) {
   Example example = new Example(DossierInto.class);
   Criteria criteria = example.createCriteria();
   criteria.andEqualTo("transferId", id);
   res = dossierIntoMapper.deleteByExample(example);
  }
  return res;
 }
}
