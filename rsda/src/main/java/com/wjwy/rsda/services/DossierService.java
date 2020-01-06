/*
 * @Author: ZHANGQI
 * @Date: 2020-01-02 15:41:24 
 * @Last Modified by: ZHANGQI
 * @Last Modified time: 2020-01-02 15:41:44
 */

package com.wjwy.rsda.services;

import java.util.List;
import java.util.UUID;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.entity.Dossier;
import com.wjwy.rsda.mapper.DossierMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import tk.mybatis.mapper.util.StringUtil;

@Service("dossierService")
@Transactional
public class DossierService{
 @Autowired
 private DossierMapper dossierMapper;
 
 /**
  * 通过ID获取对象
  * 
  * @param DossierId
  * @return
  */
 public Dossier getById(String dossierId) {
  Example example = new Example(Dossier.class);
  Criteria criteria = example.createCriteria();
  if (!StringUtil.isEmpty(dossierId)) {
   criteria.andEqualTo("dossierId", dossierId);
  }
  return dossierMapper.selectOneByExample(example);
 }

 /**
  * 案卷管理列表数据查询
  * 
  * @param Dossier
  * @param page
  * @param limit
  * @return
  */
 public PageInfo<Dossier> findList(Dossier dossier, Integer page, Integer limit) {
  PageHelper.startPage(page, limit);
  Example example = new Example(Dossier.class);
  Criteria criteria = example.createCriteria();
  if (!StringUtil.isEmpty(dossier.getDossierId())) {
   criteria.andEqualTo("dossierId", dossier.getDossierId());
  }

  List<Dossier> Dossiers = dossierMapper.selectByExample(example);
  PageInfo<Dossier> PageInfoDO = new PageInfo<Dossier>(Dossiers);
  return PageInfoDO;
 }

 /**
  * 案卷管理表单数据新增
  * 
  * @param Dossier
  * @return int
  */
 public int dossierInsert(Dossier dossier) {
  dossier.setDossierId(UUID.randomUUID().toString().toLowerCase());
  return dossierMapper.insertSelective(dossier);
 }

 /**
  * 案卷管理表单数据更新
  * 
  * @param Dossier
  * @return int
  */
 public int dossierUpdate(Dossier dossier) {
  Example example = new Example(Dossier.class);
  Criteria criteria = example.createCriteria();
  criteria.andEqualTo("dossierId", dossier.getDossierId());
  return dossierMapper.updateByExampleSelective(dossier, example);
 }

 /**
  * 案卷管理移除数据
  * 
  * @param ids
  * @return int
  */
 public int dossierRemove(String[] ids) {
  int res = 0;
  for (String id : ids) {
   Example example = new Example(Dossier.class);
   Criteria criteria = example.createCriteria();
   criteria.andEqualTo("dossierId", id);
   res = dossierMapper.deleteByExample(example);
  }
  return res;
 }
}