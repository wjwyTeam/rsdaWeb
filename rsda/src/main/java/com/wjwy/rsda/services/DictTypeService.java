/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-20 10:28:39
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2019-12-27 09:24:49
 */
package com.wjwy.rsda.services;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.entity.DictData;
import com.wjwy.rsda.entity.DictType;
import com.wjwy.rsda.mapper.DictDateMapper;
import com.wjwy.rsda.mapper.DictTypeMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import tk.mybatis.mapper.util.StringUtil;

@Service("dictTypeService")
public class DictTypeService {

	@Autowired
	private DictTypeMapper dictTypeMapper;
	@Autowired
	private DictDateMapper dictDataMapper;
/**
	* 
	* @param dict
	* @return
 */
	public int insertDictType(DictType dict) {
		return dictTypeMapper.insertSelective(dict);
	}
/**
	* 
	* @param dictType
	* @return
 */
	public DictType selectDictTypeById(String dictId) {
		Example example = new Example(DictType.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("dictId", dictId);
		return dictTypeMapper.selectOneByExample(example);
	}
	
/**
	* 
	* @param dict
	* @return
 */
	public int updateDictType(DictType dict) {
		Example example = new Example(DictType.class);
		Criteria criteria = example.createCriteria();

		criteria.andEqualTo("dictId", dict.getDictId());
		return dictTypeMapper.updateByExampleSelective(dict,example);
	}

	public int deleteDictTypeByIds(String[] ids) {
		int resN = 0;
		for (String id : ids) {
			Example example = new Example(DictType.class);
			Criteria criteria = example.createCriteria();
					criteria.andEqualTo("dictId", id);
					DictType dictType = 	dictTypeMapper.selectOneByExample(example);
					resN = dictTypeMapper.deleteByExample(example);
					Example exampleData = new Example(DictData.class);
					Criteria criteriaData = exampleData.createCriteria();
					criteriaData.andEqualTo("dictType", dictType.getDictType());
					dictDataMapper.deleteByExample(exampleData);
		}
		return resN;
	}



	public int checkDictTypeUnique(DictType dictType) {
		Example example = new Example(DictType.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("dictType", dictType.getDictType());
		return dictTypeMapper.selectByExample(example).size();
	}

	/**
	 * 
	 * @param dictType
	 * @param page
	 * @param limit
	 * @return
	 */
	public PageInfo<DictType> findList(DictType dictType, Integer page, Integer limit) {
		PageHelper.startPage(page, limit);
		Example example = new Example(DictType.class);
		Criteria criteria = example.createCriteria();
		if (StringUtil.isNotEmpty(dictType.getDictType())) {
			criteria.andEqualTo("dictType", dictType.getDictType());
		}
		if (StringUtil.isNotEmpty(dictType.getDictName())) {
			criteria.andEqualTo("dictName", dictType.getDictName());
		}
		List<DictType> dictTypes = dictTypeMapper.selectByExample(example);
		if (dictTypes == null) {
			return null;
		}
		PageInfo<DictType> PageInfoDO = new PageInfo<DictType>(dictTypes);
		return PageInfoDO;
	}

}