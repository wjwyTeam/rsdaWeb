package com.wjwy.rsda.services;

import com.wjwy.rsda.entity.MaterialAc;
import com.wjwy.rsda.mapper.MaterialAcMapper;

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
 * @Date: 2020-01-06 15:50:26
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-06 15:50:54
 */
@Service("materialAcService")
@Transactional
public class MaterialAcService {
 @Autowired
 private MaterialAcMapper materialAcMapper;

 /**
  * 通过ID获取对象
  * 
  * @param MaterialAcId
  * @return
  */
 public MaterialAc getById(String acId) {
  Example example = new Example(MaterialAc.class);
  Criteria criteria = example.createCriteria();
  if (!StringUtil.isEmpty(acId)) {
   criteria.andEqualTo("acId", acId);
  }

  return materialAcMapper.selectOneByExample(example);
 }


}