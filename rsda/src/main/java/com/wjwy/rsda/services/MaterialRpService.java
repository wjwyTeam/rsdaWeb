package com.wjwy.rsda.services;

import com.wjwy.rsda.entity.MaterialRp;
import com.wjwy.rsda.mapper.MaterialRpMapper;

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
 * @Date: 2020-01-06 15:29:48
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-06 15:30:30
 */
@Service("materialRpService")
@Transactional
public class MaterialRpService {
 @Autowired
 private MaterialRpMapper materialRpMapper;

 /**
  * 通过ID获取对象
  * 
  * @param MaterialRpId
  * @return
  */
 public MaterialRp getById(String rpId) {
  Example example = new Example(MaterialRp.class);
  Criteria criteria = example.createCriteria();
  if (!StringUtil.isEmpty(rpId)) {
   criteria.andEqualTo("rpId", rpId);
  }

  return materialRpMapper.selectOneByExample(example);
 }


}