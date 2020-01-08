/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2020-01-03 11:24:10
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-08 09:27:49
 */
package com.wjwy.rsda.controller;

import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.common.util.Log;
import com.wjwy.rsda.common.util.ResponseWrapper;
import com.wjwy.rsda.entity.DossierInto;
import com.wjwy.rsda.common.enums.Convert;
import com.wjwy.rsda.common.enums.EnumEntitys;
import com.wjwy.rsda.services.DossierIntoService;
import com.wjwy.rsda.services.PersonalService;

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
import tk.mybatis.mapper.util.StringUtil;

@RequestMapping("/dossierInto")
@RestController
@Api(value = "档案转入", tags = "C1-档案转入API维护")
public class DossierIntoController {
 @Autowired
 private DossierIntoService dossierIntoService;
  @Autowired
  private PersonalService personalService;
 public Logger logger = LoggerFactory.getLogger(DossierIntoController.class);

 // 跳转界面前缀
 private String prefix = "/webview/person";

 /**
  * 跳转档案转入管理列表主页
  * 
  * @param model
  * @return ModelAndView
  */
 @GetMapping("/dossierIntoListPage")
 @ApiOperation(value = "跳转档案转入管理列表主页")
 public ModelAndView dossierIntoListPage(ModelAndView model) {
  model.setViewName(prefix + "/dossierIntoList");
  return model;
 }

 /**
  * 跳转档案转入管理表单主页
  * 
  * @param DossierIntoId
  * @param model
  * @return ModelAndView
  */
 @ApiOperation(value = "跳转档案转入管理表单主页", notes = "DossierIntoId - 档案转入编号")
 @GetMapping("/dossierIntoFormPage")
  public ModelAndView DossierIntoFormPage(String dossierIntoId, ModelAndView model) {
    model.addObject("personalList", personalService.getList());
  if (StringUtil.isNotEmpty(dossierIntoId)) {
   model.addObject("DossierIntoOne", dossierIntoService.getById(dossierIntoId));
  }
  model.setViewName(prefix + "/dossierIntoForm");
  return model;
 }

 /**
  * 列表数据查询
  * 
  * @param DossierInto
  * @param page
  * @param limit
  * @return ResponseWrapper
  */
 @ApiOperation(value = "档案转入管理列表数据查询", notes = "参数:DossierInto-对象")
 @PostMapping("/dossierIntoFindList")
 public ResponseWrapper dossierIntoFindList(@RequestBody DossierInto DossierInto,
   @RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
   @RequestParam(value = "limit", required = true, defaultValue = "10") Integer limit) {
  try {
   PageInfo<DossierInto> pageInfos = dossierIntoService.findList(DossierInto, page, limit);
   return ResponseWrapper.success(HttpStatus.OK.value(), "获取成功", pageInfos.getList(), null,
     Integer.parseInt(String.valueOf(pageInfos.getTotal())));
  } catch (Exception e) {
   logger.error(e.getMessage());
  }
  return ResponseWrapper.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "服务错误，请联系管理员");
 }

 /**
  * 档案转入管理表单数据新增
  * 
  * @param DossierInto
  * @return ResponseWrapper
  */
 @PostMapping("/dossierIntoInsert")
 @Log(title = "档案转入", businessType = EnumEntitys.INSERT)
 @ApiOperation(value = "档案转入管理表单数据新增", notes = "参数:DossierInto-对象")
 public ResponseWrapper dossierIntoInsert(@RequestBody DossierInto dossierInto) {

  try {
   int resultTotal = dossierIntoService.dossierIntoInsert(dossierInto);
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
  * 档案转入管理表单数据更新
  * 
  * @param DossierInto
  * @return ResponseWrapper
  */
 @PostMapping("/dossierIntoUpdate")
 @Log(title = "档案转入", businessType = EnumEntitys.UPDATE)
 @ApiOperation(value = "档案转入管理表单数据更新", notes = "参数:DossierInto-对象")
 public ResponseWrapper dossierIntoUpdate(@RequestBody DossierInto dossierInto) {
  try {
   int resultTotal = dossierIntoService.dossierIntoUpdate(dossierInto);
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
  * 档案转入管理移除数据
  * 
  * @param ids
  * @return ResponseWrapper
  */
 @ResponseBody
 @PostMapping("/dossierIntoRemove")
 @Log(title = "档案转入", businessType = EnumEntitys.DELETE)
 @ApiOperation(value = "档案转入管理移除数据", notes = "参数：数组-ids")
 public ResponseWrapper remove(String ids) {
  try {
   int resultTotal = dossierIntoService.dossierIntoRemove(Convert.toStrArray(ids));
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