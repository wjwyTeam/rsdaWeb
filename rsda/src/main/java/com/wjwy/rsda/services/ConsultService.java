package com.wjwy.rsda.services;

import java.util.List;
import java.util.UUID;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.entity.Consult;
import com.wjwy.rsda.mapper.ConsultMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import tk.mybatis.mapper.util.StringUtil;

@Service("consultService")
@Transactional
public class ConsultService {
 @Autowired
 private ConsultMapper consultMapper;
 
 /**
  * 通过ID获取对象
  * 
  * @param ConsultId
  * @return
  */
 public Consult getById(String consultId) {
  return consultMapper.selectByPrimaryKey(consultId);
 }

 /**
  * 查阅管理列表数据查询
  * 
  * @param Consult
  * @param page
  * @param limit
  * @return
  */
 public PageInfo<Consult> findList(Consult consult, Integer page, Integer limit) {
  PageHelper.startPage(page, limit);
  Example example = new Example(Consult.class);
  Criteria criteria = example.createCriteria();
  if (!StringUtil.isEmpty(consult.getConsultId())) {
   criteria.andEqualTo("consultId", consult.getConsultId());
  }

  List<Consult> consults = consultMapper.selectByExample(example);
  PageInfo<Consult> PageInfoDO = new PageInfo<Consult>(consults);
  return PageInfoDO;
 }

 /**
  * 查阅管理表单数据新增
  * 
  * @param Consult
  * @return int
  */
 public int consultInsert(Consult consult) {
  consult.setConsultId(UUID.randomUUID().toString().toLowerCase());
  return consultMapper.insertSelective(consult);
 }

 /**
  * 查阅管理表单数据更新
  * 
  * @param Consult
  * @return int
  */
 public int consultUpdate(Consult consult) {
  Example example = new Example(Consult.class);
  Criteria criteria = example.createCriteria();
  criteria.andEqualTo("consultId", consult.getConsultId());
  return consultMapper.updateByExampleSelective(consult, example);
 }

 /**
  * 查阅管理移除数据
  * 
  * @param ids
  * @return int
  */
 public int consultRemove(String[] ids) {
  int res = 0;
  for (String id : ids) {
   Example example = new Example(Consult.class);
   Criteria criteria = example.createCriteria();
   criteria.andEqualTo("consultId", id);
   res = consultMapper.deleteByExample(example);
  }
  return res;
 }
}
