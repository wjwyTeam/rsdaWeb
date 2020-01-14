package com.wjwy.rsda.services;

import java.util.List;
import java.util.UUID;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.entity.DaElectronic;
import com.wjwy.rsda.mapper.DaElectronicMapper;

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
 * @Date: 2020-01-14 10:20:43
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-14 10:23:50
 */
@Service("daElectronicService")
@Transactional
public class DaElectronicService {
  @Autowired
 private DaElectronicMapper daElectronicMapper;

 /**
  * 通过ID获取对象
  * 
  * @param DaElectronicId
  * @return
  */
 public DaElectronic getById(String id) {
  Example example = new Example(DaElectronic.class);
  Criteria criteria = example.createCriteria();
  if (!StringUtil.isEmpty(id)) {
   criteria.andEqualTo("id", id);
  }
  return daElectronicMapper.selectOneByExample(example);
 }

 /**
  * 目录管理列表数据查询
  * 
  * @param DaElectronic
  * @param page
  * @param limit
  * @return
  */
 public PageInfo<DaElectronic> findList(DaElectronic daElectronic, Integer page, Integer limit) {
  PageHelper.startPage(page, limit);
  Example example = new Example(DaElectronic.class);
  example.setOrderByClause("create_time DESC");
  Criteria criteria = example.createCriteria();
  if (!StringUtil.isEmpty(daElectronic.getId())) {
   criteria.andEqualTo("id", daElectronic.getId());
  }

  List<DaElectronic> daElectronics = daElectronicMapper.selectByExample(example);
  PageInfo<DaElectronic> PageInfoDO = new PageInfo<DaElectronic>(daElectronics);
  return PageInfoDO;
 }

 /**
  * 目录管理表单数据新增
  * 
  * @param DaElectronic
  * @return int
  */
 public int daElectronicInsert(DaElectronic daElectronic) {
  daElectronic.setId(UUID.randomUUID().toString().toLowerCase());
  return daElectronicMapper.insertSelective(daElectronic);
 }

 /**
  * 目录管理表单数据更新
  * 
  * @param DaElectronic
  * @return int
  */
 public int daElectronicUpdate(DaElectronic daElectronic) {
  Example example = new Example(DaElectronic.class);
  Criteria criteria = example.createCriteria();
  criteria.andEqualTo("id", daElectronic.getId());
  return daElectronicMapper.updateByExampleSelective(daElectronic, example);
 }

 /**
  * 目录管理移除数据
  * 
  * @param ids
  * @return int
  */
 public int daElectronicRemove(String[] ids) {
  int res = 0;
  for (String id : ids) {
   Example example = new Example(DaElectronic.class);
   Criteria criteria = example.createCriteria();
   criteria.andEqualTo("id", id);
   res = daElectronicMapper.deleteByExample(example);
  }
  return res;
 }
 
}
