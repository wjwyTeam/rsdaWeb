package com.wjwy.rsda.controller;

import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.common.enums.Convert;
import com.wjwy.rsda.common.enums.EnumEntitys;
import com.wjwy.rsda.common.util.Log;
import com.wjwy.rsda.common.util.ResponseWrapper;
import com.wjwy.rsda.entity.DaCodeItem;
import com.wjwy.rsda.services.DaCodeItemService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;
import tk.mybatis.mapper.util.StringUtil;

/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2020-01-14 09:00:45
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-14 09:01:06
 */
@RequestMapping("/codeItem")
@RestController
@Api(value = "目录管理", tags = "L1-目录管理API维护")
public class DaCodeItemController {
 @Autowired
 private DaCodeItemService daCodeItemService;

 public Logger logger = LoggerFactory.getLogger(DaCodeItemController.class);

 // 跳转界面前缀
 private String prefix = "/webview/person";

 /**
  * 跳转目录管理列表主页
  * 
  * @param model
  * @return ModelAndView
  */
 @GetMapping("/daCodeItemListPage")
 @ApiOperation(value = "跳转目录管理列表主页")
 public ModelAndView daCodeItemListPage(@ApiIgnore ModelAndView model) {
  model.setViewName(prefix + "/daCodeItemList");
  return model;
 }

 /**
  * 跳转目录管理表单主页
  * 
  * @param DaCodeItemId
  * @param model
  * @return ModelAndView
  */
 @ApiOperation(value = "跳转目录管理表单主页", notes = "DaCodeItemId - 目录编号")
 @GetMapping("/DaCodeItemFormPage")
 public ModelAndView DaCodeItemFormPage(String id, @ApiIgnore ModelAndView model) {
  if (StringUtil.isNotEmpty(id)) {
   model.addObject("daCodeItemOne", daCodeItemService.getById(id));
  }
  model.setViewName(prefix + "/daCodeItemForm");
  return model;
 }

 /**
  * 列表数据查询
  * 
  * @param DaCodeItem
  * @param page
  * @param limit
  * @return ResponseWrapper
  */
 @ApiOperation(value = "目录管理列表数据查询", notes = "参数:DaCodeItem-对象")
 @PostMapping("/daCodeItemFindList")
 public ResponseWrapper DaCodeItemFindList(@RequestBody DaCodeItem daCodeItem,
   @RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
   @RequestParam(value = "limit", required = true, defaultValue = "10") Integer limit) {
  try {
   PageInfo<DaCodeItem> pageInfos = daCodeItemService.findList(daCodeItem, page, limit);
   return ResponseWrapper.success(HttpStatus.OK.value(), "获取成功", pageInfos.getList(), null,
     Integer.parseInt(String.valueOf(pageInfos.getTotal())));
  } catch (Exception e) {
   logger.error(e.getMessage());
  }
  return ResponseWrapper.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "服务错误，请联系管理员");
 }

 /**
  * 目录管理表单数据新增
  * 
  * @param DaCodeItem
  * @return ResponseWrapper
  */
 @PostMapping("/daCodeItemInsert")
 @Log(title = "目录管理", businessType = EnumEntitys.INSERT)
 @ApiOperation(value = "目录管理表单数据新增", notes = "参数:DaCodeItem-对象")
 public ResponseWrapper DaCodeItemInsert(@RequestBody DaCodeItem daCodeItem) {

  try {
   int resultTotal = daCodeItemService.daCodeItemInsert(daCodeItem);
   if (resultTotal == 0) {
    return ResponseWrapper.success(HttpStatus.BAD_REQUEST.value(), "新增失败", null, null, null);
   }
   return ResponseWrapper.success(HttpStatus.OK.value(), "新增成功", null, null, null);
  } catch (Exception e) {
   logger.error(e.getMessage());
  }
  return ResponseWrapper.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "服务错误，请联系管理员");
 }

 /**
  * 目录管理表单数据更新
  * 
  * @param DaCodeItem
  * @return ResponseWrapper
  */
 @PostMapping("/daCodeItemUpdate")
 @Log(title = "目录管理", businessType = EnumEntitys.UPDATE)
 @ApiOperation(value = "目录管理表单数据更新", notes = "参数:DaCodeItem-对象")
 public ResponseWrapper DaCodeItemUpdate(@RequestBody DaCodeItem daCodeItem) {
  try {
   int resultTotal = daCodeItemService.daCodeItemUpdate(daCodeItem);
   if (resultTotal == 0) {
    return ResponseWrapper.success(HttpStatus.BAD_REQUEST.value(), "更新失败", null, null, null);
   }
   return ResponseWrapper.success(HttpStatus.OK.value(), "更新成功", null, null, null);
  } catch (Exception e) {
   logger.error(e.getMessage());
  }
  return ResponseWrapper.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "服务错误，请联系管理员");
 }

 /**
  * 目录管理移除数据
  * 
  * @param ids
  * @return ResponseWrapper
  */
 @ResponseBody
 @PostMapping("/daCodeItemRemove")
 @Log(title = "目录管理", businessType = EnumEntitys.DELETE)
 @ApiOperation(value = "目录管理移除数据", notes = "参数：数组-ids")
 public ResponseWrapper remove(String ids) {
  try {
   int resultTotal = daCodeItemService.daCodeItemRemove(Convert.toStrArray(ids));
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
