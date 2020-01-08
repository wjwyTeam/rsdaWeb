package com.wjwy.rsda.controller;

import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.common.util.Log;
import com.wjwy.rsda.common.util.ResponseWrapper;
import com.wjwy.rsda.entity.DossierOut;
import com.wjwy.rsda.enums.Convert;
import com.wjwy.rsda.enums.EnumEntitys;
import com.wjwy.rsda.services.DossierOutService;
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

/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2020-01-03 13:59:18
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-03 14:00:02
 */
@RequestMapping("/dossierOut")
@RestController
@Api(value = "档案转出", tags = "C2-档案转出API维护")
public class DossierOutController {
 @Autowired
 private DossierOutService dossierOutService;
  @Autowired
  private PersonalService personalService;
 public Logger logger = LoggerFactory.getLogger(DossierOutController.class);

 // 跳转界面前缀
 private String prefix = "/webview/person";

 /**
  * 跳转档案转出管理列表主页
  * 
  * @param model
  * @return ModelAndView
  */
 @GetMapping("/dossierOutListPage")
 @ApiOperation(value = "跳转档案转出管理列表主页")
 public ModelAndView dossierOutListPage(ModelAndView model) {
  model.setViewName(prefix + "/dossierOutList");
  return model;
 }

 /**
  * 跳转档案转出管理表单主页
  * 
  * @param DossierOutId
  * @param model
  * @return ModelAndView
  */
 @ApiOperation(value = "跳转档案转出管理表单主页", notes = "DossierOutId - 档案转出编号")
 @GetMapping("/dossierOutFormPage")
  public ModelAndView DossierOutFormPage(String dossierOutId, ModelAndView model) {
    model.addObject("personalList", personalService.getList());
  if (StringUtil.isNotEmpty(dossierOutId)) {
   model.addObject("dossierOutOne", dossierOutService.getById(dossierOutId));
  }
  model.setViewName(prefix + "/dossierOutForm");
  return model;
 }

 /**
  * 列表数据查询
  * 
  * @param DossierOut
  * @param page
  * @param limit
  * @return ResponseWrapper
  */
 @ApiOperation(value = "档案转出管理列表数据查询", notes = "参数:DossierOut-对象")
 @PostMapping("/dossierOutFindList")
 public ResponseWrapper dossierOutFindList(@RequestBody DossierOut DossierOut,
   @RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
   @RequestParam(value = "limit", required = true, defaultValue = "10") Integer limit) {
  try {
   PageInfo<DossierOut> pageInfos = dossierOutService.findList(DossierOut, page, limit);
   return ResponseWrapper.success(HttpStatus.OK.value(), "获取成功", pageInfos.getList(), null,
     Integer.parseInt(String.valueOf(pageInfos.getTotal())));
  } catch (Exception e) {
   logger.error(e.getMessage());
  }
  return ResponseWrapper.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "服务错误，请联系管理员");
 }

 /**
  * 档案转出管理表单数据新增
  * 
  * @param DossierOut
  * @return ResponseWrapper
  */
 @PostMapping("/dossierOutInsert")
 @Log(title = "档案转出", businessType = EnumEntitys.INSERT)
 @ApiOperation(value = "档案转出管理表单数据新增", notes = "参数:DossierOut-对象")
 public ResponseWrapper dossierOutInsert(@RequestBody DossierOut dossierOut) {

  try {
   int resultTotal = dossierOutService.dossierOutInsert(dossierOut);
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
  * 档案转出管理表单数据更新
  * 
  * @param DossierOut
  * @return ResponseWrapper
  */
 @PostMapping("/dossierOutUpdate")
 @Log(title = "档案转出", businessType = EnumEntitys.UPDATE)
 @ApiOperation(value = "档案转出管理表单数据更新", notes = "参数:DossierOut-对象")
 public ResponseWrapper dossierOutUpdate(@RequestBody DossierOut dossierOut) {
  try {
   int resultTotal = dossierOutService.dossierOutUpdate(dossierOut);
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
  * 档案转出管理移除数据
  * 
  * @param ids
  * @return ResponseWrapper
  */
 @ResponseBody
 @PostMapping("/dossierOutRemove")
 @Log(title = "档案转出", businessType = EnumEntitys.DELETE)
 @ApiOperation(value = "档案转出管理移除数据", notes = "参数：数组-ids")
 public ResponseWrapper remove(String ids) {
  try {
   int resultTotal = dossierOutService.dossierOutRemove(Convert.toStrArray(ids));
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

