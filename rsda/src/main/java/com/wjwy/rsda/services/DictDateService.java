/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-20 08:12:34
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2019-12-20 08:42:41
 */
package com.wjwy.rsda.services;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.entity.DictData;
import com.wjwy.rsda.mapper.DictDateMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("dictDateService")
public class DictDateService {

  /**
   * 分页列表
   */
  @Autowired
  private DictDateMapper dictDateMapper;

  public PageInfo<DictData> findList(DictData dictData, Integer page, Integer limit) {
    PageHelper.startPage(page, limit);
    List<DictData> dictDatas = dictDateMapper.select(dictData);
    if (dictDatas == null) {
      return null;
    }
    PageInfo<DictData> PageInfoDO = new PageInfo<DictData>(dictDatas);
    return PageInfoDO;
  }

  /**
   * 
   * @param dictData
   * @return
   */
  public List<DictData> selectDictDataList(DictData dictData) {
    return dictDateMapper.select(dictData);
  }

  /**
   * 新增保存字典数据信息
   * 
   * @param dict
   * @return
   */
  public int insertDictData(DictData dictData) {
    return dictDateMapper.insertSelective(dictData);
  }

  /**
   * 根据编号查询回显数据
   * 
   * @param dictCode
   * @return
   */
  public DictData selectDictDataById(Long dictCode) {
    return dictDateMapper.selectByPrimaryKey(dictCode);
  }

  /**
   * 
   * @param dictData
   * @return
   */
  public int updateDictData(DictData dictData) {
    return dictDateMapper.updateByPrimaryKeySelective(dictData);
  }

  /**
   * 
   * @param ids
   * @return
   */
  public int deleteDictDataByIds(String[] ids) {
    int resN = 0;
    for (String id : ids) {
      resN = dictDateMapper.deleteByPrimaryKey(id);
    }
    return resN;
  }

}