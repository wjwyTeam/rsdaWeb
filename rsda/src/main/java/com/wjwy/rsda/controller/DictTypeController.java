/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-19 18:01:30
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2019-12-30 08:55:28
 */
package com.wjwy.rsda.controller;
import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.common.util.BaseController;
import com.wjwy.rsda.common.util.Log;
import com.wjwy.rsda.common.util.ResponseWrapper;
import com.wjwy.rsda.common.util.ShiroUtils;
import com.wjwy.rsda.entity.DictType;
import com.wjwy.rsda.enums.AjaxResult;
import com.wjwy.rsda.enums.Convert;
import com.wjwy.rsda.enums.EnumEntitys;
import com.wjwy.rsda.enums.ResponseMessageConstant;
import com.wjwy.rsda.services.DictTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import tk.mybatis.mapper.util.StringUtil;

/**
 * 数据字典信息
 */
@Controller
@RequestMapping("/dictType")
@Api(value = "字典类型数据配置", tags = "字典类型数据配置")
public class DictTypeController extends BaseController {

  private String prefix = "/webview/system/dict";

  @Autowired
  private DictTypeService dictTypeService;

  /**
   * 字典数据类型列表页
   * 
   * @param model
   * @return
   */
  @GetMapping("/dictTypeListPage")
  @ApiOperation(value = "跳转字典数据类型列表页")
  public ModelAndView dictType(ModelAndView model) {
    model.setViewName(prefix + "/dictTypeList");
    return model;
  }

  /**
   * 修改字典类型
   */
  @ApiOperation(value = "跳转表单", notes = "dictId - 字典编号")
  @GetMapping("/dictTypeEdit")
  public ModelAndView edit(String dictId, ModelAndView model) {
    if (StringUtil.isNotEmpty(dictId)) {
      model.addObject("dictType", dictTypeService.selectDictTypeById(dictId));
    }
    model.setViewName(prefix + "/dictTypeForm");
    return model;
  }


/**
   * 分页列表
   * 
   * @param DictType
   * @param page
   * @param limit
   * @return
   */
  @ResponseBody
  @PostMapping("/dateTypeList")
  @ApiOperation(value = "字典类型数据分页列表数据展示", notes = "DictType - 对象，page，limit ")
  public ResponseWrapper datePageList(@Validated  DictType dictType, Integer page, Integer limit) {
    try {
      PageInfo<DictType> pageInfos = dictTypeService.findList(dictType, page, limit);
      return ResponseWrapper.success(HttpStatus.OK.value(), "获取成功", pageInfos.getList(), null,Integer.parseInt(String.valueOf(pageInfos.getTotal())));
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return ResponseWrapper.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "服务错误，请联系管理员");
  }


  /**
   * 新增保存字典类型
   */
  @Log(title = "字典数据", businessType = EnumEntitys.INSERT)
  @ApiOperation(value = "新增保存字典类型", notes = "DictType - 对象")
  @PostMapping("/dictTypeInsert")
  @ResponseBody
  public ResponseWrapper addSave(@RequestBody DictType dictType) {
    dictType.setCreateBy(ShiroUtils.getLoginName());

    try {
	
      if (ResponseMessageConstant.DICT_TYPE_NOT_UNIQUE.equals(String.valueOf(dictTypeService.checkDictTypeUnique(dictType)))) {
        return ResponseWrapper.success(HttpStatus.OK.value(), "新增字典'" + dictType.getDictName() + "'失败，字典类型已存在", null, null, null);
      }
      AjaxResult resultTotal = toAjax(dictTypeService.insertDictType(dictType));
			if (resultTotal.isEmpty()) {
				return ResponseWrapper.success(HttpStatus.BAD_REQUEST.value(), "新增失败", null, null, null);
      }
			return ResponseWrapper.success(HttpStatus.OK.value(), "新增成功", null, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return ResponseWrapper.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "服务错误，请联系管理员");
  }

  /**
   * 修改保存字典类型
   */
  @Log(title = "字典类型", businessType = EnumEntitys.UPDATE)
  @PostMapping("/edit")
  @ResponseBody
  public AjaxResult editSave(@Validated DictType dict) {
    if (ResponseMessageConstant.DICT_TYPE_NOT_UNIQUE.equals(String.valueOf(dictTypeService.checkDictTypeUnique(dict)))) {
      return error("修改字典'" + dict.getDictName() + "'失败，字典类型已存在");
    }
    dict.setUpdateBy(ShiroUtils.getLoginName());
    return toAjax(dictTypeService.updateDictType(dict));
  }



    /**
   * 修改保存字典类型
   */
  @Log(title = "字典数据", businessType = EnumEntitys.UPDATE)
  @PostMapping("/dictTypeEdit")
  @ResponseBody
  @ApiOperation(value = "修改保存字典类型", notes = "dictData - 对象")
  public ResponseWrapper edit(@RequestBody DictType dict) {
    
    dict.setUpdateBy(ShiroUtils.getLoginName());

    try {
      if (ResponseMessageConstant.DICT_TYPE_NOT_UNIQUE.equals(String.valueOf(dictTypeService.checkDictTypeUnique(dict)))) {
        return ResponseWrapper.success(HttpStatus.BAD_REQUEST.value(), "修改字典'" + dict.getDictName() + "'失败，字典类型已存在", null, null, null);
      }
			int resultTotal =  dictTypeService.updateDictType(dict);
			if (resultTotal == 0) {
				return ResponseWrapper.success(HttpStatus.BAD_REQUEST.value(), "更新失败", null, null, null);
      }
   
			return ResponseWrapper.success(HttpStatus.OK.value(), "更新成功", null, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return ResponseWrapper.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "服务错误，请联系管理员");
	}


  @Log(title = "字典数据", businessType = EnumEntitys.DELETE)
  @PostMapping("/dictTypeRemove")
  @ApiOperation(value = "移除字典数据", notes = "ids - 数组-ids")
  @ResponseBody
  public ResponseWrapper remove(String ids) {
    try {
			int resultTotal =  dictTypeService.deleteDictTypeByIds(Convert.toStrArray(ids));
			if (resultTotal == 0) {
				return ResponseWrapper.success(HttpStatus.BAD_REQUEST.value(), "删除失败", null, null, null);
			}
			return ResponseWrapper.success(HttpStatus.OK.value(), "删除成功", null, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return ResponseWrapper.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "服务错误，请联系管理员");
  }


  /**
   * 校验字典类型
   */
  @PostMapping("/checkDictTypeUnique")
  @ResponseBody
  public String checkDictTypeUnique(DictType dictType) {
    return String.valueOf(dictTypeService.checkDictTypeUnique(dictType));
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
