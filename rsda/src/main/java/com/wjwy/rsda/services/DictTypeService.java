package com.wjwy.rsda.services;

import java.util.List;

import com.wjwy.rsda.entity.DictType;

import org.springframework.stereotype.Service;

@Service("dictTypeService")
public class DictTypeService {

	public int insertDictType(DictType dict) {
		return 0;
	}

	public DictType selectDictTypeById(String dictId) {
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
 
}