/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-19 18:01:14
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2019-12-23 16:30:59
 */
package com.wjwy.rsda.controller;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.wjwy.rsda.common.util.BaseController;
import com.wjwy.rsda.common.util.ExcelUtil;
import com.wjwy.rsda.common.util.Log;
import com.wjwy.rsda.common.util.ResponseWrapper;
import com.wjwy.rsda.common.util.ShiroUtils;
import com.wjwy.rsda.entity.DictData;
import com.wjwy.rsda.enums.AjaxResult;
import com.wjwy.rsda.enums.Convert;
import com.wjwy.rsda.enums.EnumEntitys;
import com.wjwy.rsda.services.DictDateService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * 数据字典信息
 */
@Controller
@RequestMapping("/dict")
@Api(value = "字典数据配置", tags = "字典数据配置")
public class DictDataController extends BaseController {

  private String prefix = "/webview/system/dict";
  public Logger logger = LoggerFactory.getLogger(DictDataController.class);
  @Autowired
  private DictDateService dictDataService;

  /**
   * 
   * @return String
   */
  @GetMapping("/dataList")
  @ApiOperation(value = "字典数据列表页")
  public ModelAndView dictData(ModelAndView model) {
    model.setViewName(prefix+"/dictDateList");
    return model;
  }

    /**
   * 修改字典类型
   */
  @ApiOperation(value = "修改字典类型", notes = "dictCode - 字典编号")
  @GetMapping("/dictDataEdit")
  public ModelAndView edit(String dictCode, ModelAndView model) {
    if (StringUtil.isNotEmpty(dictCode)) {
      model.addObject("dictData", dictDataService.selectDictDataById(Long.valueOf(dictCode)));
    }
    model.setViewName(prefix + "/dictDateForm");
    return model;
  }
  
  /**
   * 分页列表
   * 
   * @param dictData
   * @param page
   * @param limit
   * @return
   */
  @ResponseBody
  @PostMapping("/datePageList")
  @ApiOperation(value = "字典数据分页列表数据展示", notes = "dictData - 对象，page，limit ")
  public ResponseWrapper datePageList(@Validated  DictData dictData, Integer page, Integer limit) {
    try {
      PageInfo<DictData> pageInfos = dictDataService.findList(dictData, page, limit);
      return ResponseWrapper.success(HttpStatus.OK.value(), "获取成功", pageInfos.getList(), null,
          Integer.parseInt(String.valueOf(pageInfos.getTotal())));
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return ResponseWrapper.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "服务错误，请联系管理员");
  }

  /**
   * 字典数据分页列表数据导出
   */
  @Log(title = "字典数据", businessType = EnumEntitys.EXPORT)
  @PostMapping("/export")
  @ApiOperation(value = "字典数据分页列表数据导出", notes = "dictData - 对象")
  @ResponseBody
  public AjaxResult export(DictData dictData) {
    List<DictData> list = dictDataService.selectDictDataList(dictData);
    ExcelUtil<DictData> util = new ExcelUtil<DictData>(DictData.class);
    return util.exportExcel(list, "字典数据");
  }

  /**
   * 新增字典类型
   */
  @ApiOperation(value = "字典数据新增字典类型", notes = "dictType - 字典类型")
  @GetMapping("/dictDataForm/{dictType}")
  public String add(@PathVariable("dictType") String dictType, ModelMap map) {
    map.put("dictType", dictType);
    return prefix + "/dictDataForm";
  }

  /**
   * 新增保存字典类型
   */
  @Log(title = "字典数据", businessType = EnumEntitys.INSERT)
  @ApiOperation(value = "新增保存字典类型", notes = "dictData - 对象")
  @PostMapping("/dictDataInsert")
  @ResponseBody
  public ResponseWrapper addSave(@RequestBody DictData dictData) {
    dictData.setCreateBy(ShiroUtils.getLoginName());

    try {
			AjaxResult resultTotal = toAjax(dictDataService.insertDictData(dictData));
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
  @Log(title = "字典数据", businessType = EnumEntitys.UPDATE)
  @PostMapping("/dictDataEdit")
  @ResponseBody
  @ApiOperation(value = "修改保存字典类型", notes = "dictData - 对象")
  public ResponseWrapper editSave(@RequestBody DictData dictData) {
    
    dictData.setUpdateBy(ShiroUtils.getLoginName());

    try {
			int resultTotal =  dictDataService.updateDictData(dictData);
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


  /**
   * 
   * @param ids
   * @return
   */
  @Log(title = "字典数据", businessType = EnumEntitys.DELETE)
  @PostMapping("/dictDataRemove")
  @ApiOperation(value = "移除字典数据", notes = "ids - 数组-ids")
  @ResponseBody
  public ResponseWrapper remove(String ids) {
    try {
			int resultTotal =  dictDataService.deleteDictDataByIds(Convert.toStrArray(ids));
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
}

