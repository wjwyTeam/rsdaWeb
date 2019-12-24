/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-20 10:28:39
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2019-12-24 15:15:00
 */
package com.wjwy.rsda.services;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.entity.DictType;
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

	public int insertDictType(DictType dict) {
		return 0;
	}

	public DictType selectDictTypeById(String dictType) {
		return null;
	}

	public int updateDictType(DictType dict) {
		return 0;
	}

	public int deleteDictTypeByIds(String ids) {
		return 0;
	}

	public Object selectDictTypeAll() {
		return null;
	}

	public String checkDictTypeUnique(DictType dictType) {
		return null;
	}

	public List<DictType> selectDictTypeList(DictType dictType) {
		return null;
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