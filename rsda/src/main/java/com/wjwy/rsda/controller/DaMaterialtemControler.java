package com.wjwy.rsda.controller;

import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.common.enums.Convert;
import com.wjwy.rsda.common.enums.EnumEntitys;
import com.wjwy.rsda.common.util.Log;
import com.wjwy.rsda.common.util.ResponseWrapper;
import com.wjwy.rsda.entity.DaMaterialtem;
import com.wjwy.rsda.services.DaMaterialtemService;

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
 * @Date: 2020-01-14 10:30:13
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-14 10:32:02
 */
@RequestMapping("/materialtem")
@RestController
@Api(value = "材料管理", tags = "N1-材料管理API维护")
public class DaMaterialtemControler {

 @Autowired
 private DaMaterialtemService daMaterialtemService;

 public Logger logger = LoggerFactory.getLogger(DaMaterialtemControler.class);

 // 跳转界面前缀
 private String prefix = "/webview/person";


 /**
  * 跳转材料管理列表主页
  * 
  * @param model
  * @return ModelAndView
  */
 @GetMapping("/daMaterialtemListPage")
 @ApiOperation(value = "跳转材料管理列表主页")
 public ModelAndView DaMaterialtemListPage(@ApiIgnore ModelAndView model) {
  model.setViewName(prefix + "/daMaterialtemList");
  return model;
 }

 /**
  * 跳转材料管理表单主页
  * 
  * @param DaMaterialtemId
  * @param model
  * @return ModelAndView
  */
 @ApiOperation(value = "跳转材料管理表单主页", notes = "DaMaterialtemId - 材料编号")
 @GetMapping("/daMaterialtemFormPage")
 public ModelAndView DaMaterialtemFormPage(String id, @ApiIgnore ModelAndView model) {
  if (StringUtil.isNotEmpty(id)) {
   model.addObject("DaMaterialtemOne", daMaterialtemService.getById(id));
  }
  model.setViewName(prefix + "/daMaterialtemForm");
  return model;
 }

 /**
  * 列表数据查询
  * 
  * @param DaMaterialtem
  * @param page
  * @param limit
  * @return ResponseWrapper
  */
 @ApiOperation(value = "材料管理列表数据查询", notes = "参数:DaMaterialtem-对象")
 @PostMapping("/daMaterialtemFindList")
 public ResponseWrapper DaMaterialtemFindList(@RequestBody DaMaterialtem daMaterialtem,
   @RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
   @RequestParam(value = "limit", required = true, defaultValue = "10") Integer limit) {
  try {
   PageInfo<DaMaterialtem> pageInfos = daMaterialtemService.findList(daMaterialtem, page, limit);
   return ResponseWrapper.success(HttpStatus.OK.value(), "获取成功", pageInfos.getList(), null,
     Integer.parseInt(String.valueOf(pageInfos.getTotal())));
  } catch (Exception e) {
   logger.error(e.getMessage());
  }
  return ResponseWrapper.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "服务错误，请联系管理员");
 }

 /**
  * 材料管理表单数据新增
  * 
  * @param DaMaterialtem
  * @return ResponseWrapper
  */
 @PostMapping("/daMaterialtemInsert")
 @Log(title = "材料管理", businessType = EnumEntitys.INSERT)
 @ApiOperation(value = "材料管理表单数据新增", notes = "参数:DaMaterialtem-对象")
 public ResponseWrapper DaMaterialtemInsert(@RequestBody DaMaterialtem daMaterialtem) {

  try {
   int resultTotal = daMaterialtemService.daMaterialtemInsert(daMaterialtem);
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
  * 材料管理表单数据更新
  * 
  * @param DaMaterialtem
  * @return ResponseWrapper
  */
 @PostMapping("/daMaterialtemUpdate")
 @Log(title = "材料管理", businessType = EnumEntitys.UPDATE)
 @ApiOperation(value = "材料管理表单数据更新", notes = "参数:DaMaterialtem-对象")
 public ResponseWrapper DaMaterialtemUpdate(@RequestBody DaMaterialtem daMaterialtem) {
  try {
   int resultTotal = daMaterialtemService.daMaterialtemUpdate(daMaterialtem);
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
  * 材料管理移除数据
  * 
  * @param ids
  * @return ResponseWrapper
  */
 @ResponseBody
 @PostMapping("/daMaterialtemRemove")
 @Log(title = "材料管理", businessType = EnumEntitys.DELETE)
 @ApiOperation(value = "材料管理移除数据", notes = "参数：数组-ids")
 public ResponseWrapper remove(String ids) {
  try {
   int resultTotal = daMaterialtemService.daMaterialtemRemove(Convert.toStrArray(ids));
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