/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-19 18:01:30
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2019-12-19 18:13:13
 */
package com.wjwy.rsda.controller;

import java.util.List;

import com.wjwy.rsda.common.util.BaseController;
import com.wjwy.rsda.common.util.ExcelUtil;
import com.wjwy.rsda.common.util.Log;
import com.wjwy.rsda.common.util.ShiroUtils;
import com.wjwy.rsda.entity.DictType;
import com.wjwy.rsda.enums.AjaxResult;
import com.wjwy.rsda.enums.EnumEntitys;
import com.wjwy.rsda.enums.ResponseMessageConstant;
import com.wjwy.rsda.services.DictTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 数据字典信息
 */
@Controller
@RequestMapping("/dictType")
@Api(value = "字典类型数据配置", tags = "字典类型数据配置")
public class DictTypeController  extends BaseController{

 private String prefix = "system/dict/";

 @Autowired
 private DictTypeService dictTypeService;

 @GetMapping()
 @ApiOperation(value = "字典数据类型列表页")
 public String dictType() {
  return prefix + "/type";
 }


 @Log(title = "字典类型", businessType = EnumEntitys.EXPORT)
 @PostMapping("/export")
 @ResponseBody
 public AjaxResult export(DictType dictType) {

  List<DictType> list = dictTypeService.selectDictTypeList(dictType);
  ExcelUtil<DictType> util = new ExcelUtil<DictType>(DictType.class);
  return util.exportExcel(list, "字典类型");
 }

 /**
  * 新增字典类型
  */
 @GetMapping("/add")
 public String add() {
  return prefix + "/add";
 }

 /**
  * 新增保存字典类型
  */
 @Log(title = "字典类型", businessType = EnumEntitys.INSERT)
 @PostMapping("/add")
 @ResponseBody
 public AjaxResult addSave(@Validated DictType dict) {
  if (ResponseMessageConstant.DICT_TYPE_NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict))) {
   return error("新增字典'" + dict.getDictName() + "'失败，字典类型已存在");
  }
  dict.setCreateBy(ShiroUtils.getLoginName());
  return toAjax(dictTypeService.insertDictType(dict));
 }

 /**
  * 修改字典类型
  */
 @GetMapping("/edit/{dictId}")
 public String edit(@PathVariable("dictId") String dictId, ModelMap mmap) {
  mmap.put("dict", dictTypeService.selectDictTypeById(dictId));
  return prefix + "/edit";
 }

 /**
  * 修改保存字典类型
  */
 @Log(title = "字典类型", businessType = EnumEntitys.UPDATE)
 @PostMapping("/edit")
 @ResponseBody
 public AjaxResult editSave(@Validated DictType dict) {
  if (ResponseMessageConstant.DICT_TYPE_NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict))) {
   return error("修改字典'" + dict.getDictName() + "'失败，字典类型已存在");
  }
  dict.setUpdateBy(ShiroUtils.getLoginName());
  return toAjax(dictTypeService.updateDictType(dict));
 }

 @Log(title = "字典类型", businessType = EnumEntitys.DELETE)
 @PostMapping("/remove")
 @ResponseBody
 public AjaxResult remove(String ids) {
  try {
   return toAjax(dictTypeService.deleteDictTypeByIds(ids));
  } catch (Exception e) {
   return error(e.getMessage());
  }
 }

 /**
  * 查询字典详细
  */
 @GetMapping("/detail/{dictId}")
 public String detail(@PathVariable("dictId") String dictId, ModelMap mmap) {
  mmap.put("dict", dictTypeService.selectDictTypeById(dictId));
  mmap.put("dictList", dictTypeService.selectDictTypeAll());
  return "system/dict/data/data";
 }

 /**
  * 校验字典类型
  */
 @PostMapping("/checkDictTypeUnique")
 @ResponseBody
 public String checkDictTypeUnique(DictType dictType) {
  return dictTypeService.checkDictTypeUnique(dictType);
 }

 /**
  * 选择字典树
  */
 @GetMapping("/selectDictTree/{columnId}/{dictType}")
 public String selectDeptTree(@PathVariable("columnId") Long columnId, @PathVariable("dictType") String dictType,
   ModelMap map) {
  map.put("columnId", columnId);
  map.put("dict", dictTypeService.selectDictTypeById(dictType));
  return prefix + "/tree";
 }
}
