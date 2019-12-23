/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-20 08:12:34
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2019-12-23 14:48:55
 */
package com.wjwy.rsda.services;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.entity.DictData;
import com.wjwy.rsda.mapper.DictDateMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import tk.mybatis.mapper.util.StringUtil;

@Service("dictDateService")
public class DictDateService {
	
		
	/**
	 * 分页列表
	 */
	@Autowired
	private DictDateMapper dictDateMapper;

	public PageInfo<DictData> findList(DictData dictData, Integer page, Integer limit) {
		PageHelper.startPage(page, limit);
		Example example = new Example(DictData.class);
		Criteria criteria = example.createCriteria();
		if (StringUtil.isNotEmpty( dictData.getDictType())) {
			criteria.andEqualTo("dictType", dictData.getDictType());
		}
		if (StringUtil.isNotEmpty( dictData.getDictLabel())) {
			criteria.andEqualTo("dictLabel", dictData.getDictLabel());
		}
		List<DictData> dictDatas = dictDateMapper.selectByExample(example);
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
		Example example = new Example(DictData.class);
		Criteria criteria = example.createCriteria();

		criteria.andEqualTo("dictCode", dictCode);
		return dictDateMapper.selectOneByExample(example);
	}

	/**
	 * 
	 * @param dictData
	 * @return
	 */
	public int updateDictData(DictData dictData) {
		Example example = new Example(DictData.class);
		Criteria criteria = example.createCriteria();

		criteria.andEqualTo("dictCode", dictData.getDictCode());
		return dictDateMapper.updateByExampleSelective(dictData,example);
	}

	/**
	 * 
	 * @param ids
	 * @return
	 */
	public int deleteDictDataByIds(String[] ids) {
		int resN = 0;
		for (String id : ids) {
			Example example = new Example(DictData.class);
			Criteria criteria = example.createCriteria();
	   	criteria.andEqualTo("dictCode", id);
			resN = dictDateMapper.deleteByExample(example);
		}
		return resN;
	}

}