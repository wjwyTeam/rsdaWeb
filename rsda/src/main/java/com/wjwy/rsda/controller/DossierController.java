/*
 * @Author: ZHANGQI 
 * @Date: 2020-01-02 14:59:58 
 * @Last Modified by: ZHANGQI
 * @Last Modified time: 2020-01-02 15:40:33
 */

package com.wjwy.rsda.controller;

import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.common.util.Log;
import com.wjwy.rsda.common.util.ResponseWrapper;
import com.wjwy.rsda.entity.Dossier;
import com.wjwy.rsda.enums.Convert;
import com.wjwy.rsda.enums.EnumEntitys;
import com.wjwy.rsda.services.DossierService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import tk.mybatis.mapper.util.StringUtil;

@RequestMapping("/dossier")
@RestController
@Api(value = "案卷管理", tags = "案卷管理API维护")
public class DossierController{
 @Autowired
 private DossierService dossierService;

 public Logger logger = LoggerFactory.getLogger(DossierController.class);

 // 跳转界面前缀
 private String prefix = "/webview/person";

 /**
  * 跳转案卷管理列表主页
  * 
  * @param model
  * @return ModelAndView
  */
 @GetMapping("/dossierListPage")
 @ApiOperation(value = "跳转案卷管理列表主页")
 public ModelAndView dossierListPage(ModelAndView model) {
  model.setViewName(prefix + "/dossierList");
  return model;
 }

 /**
  * 跳转案卷管理表单主页
  * 
  * @param dossierId
  * @param model
  * @return ModelAndView
  */
 @ApiOperation(value = "跳转案卷管理表单主页", notes = "dossierId - 案卷编号")
 @GetMapping("/dossierFormPage")
 public ModelAndView dossierFormPage(String dossierId, ModelAndView model) {
  if (StringUtil.isNotEmpty(dossierId)) {
   model.addObject("dossierOne", dossierService.getById(dossierId));
  }
  model.setViewName(prefix + "/dossierForm");
  return model;
 }

 /**
  * 列表数据查询
  * 
  * @param dossier
  * @param page
  * @param limit
  * @return ResponseWrapper
  */
 @ApiOperation(value = "案卷管理列表数据查询", notes = "参数:dossier-对象")
 @PostMapping("/dossierFindList")
 public ResponseWrapper dossierFindList(@RequestBody Dossier dossier, Integer page, Integer limit) {
  try {
   PageInfo<Dossier> pageInfos = dossierService.findList(dossier, page, limit);
   return ResponseWrapper.success(HttpStatus.OK.value(), "获取成功", pageInfos.getList(), null,
     Integer.parseInt(String.valueOf(pageInfos.getTotal())));
  } catch (Exception e) {
   logger.error(e.getMessage());
  }
  return ResponseWrapper.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "服务错误，请联系管理员");
 }

 /**
  * 案卷管理表单数据新增
  * 
  * @param dossier
  * @return ResponseWrapper
  */
 @PostMapping("/dossierInsert")
 @Log(title = "案卷管理表单数据新增", businessType = EnumEntitys.INSERT)
 @ApiOperation(value = "案卷管理表单数据新增", notes = "参数:dossier-对象")
 public ResponseWrapper dossierInsert(@RequestBody Dossier dossier) {

  try {
   int resultTotal = dossierService.dossierInsert(dossier);
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
  * 案卷管理表单数据更新
  * 
  * @param dossier
  * @return ResponseWrapper
  */
 @PostMapping("/dossierUpdate")
 @Log(title = "案卷管理表单数据更新", businessType = EnumEntitys.UPDATE)
 @ApiOperation(value = "案卷管理表单数据更新", notes = "参数:dossier-对象")
 public ResponseWrapper dossierUpdate(@RequestBody Dossier dossier) {
  try {
   int resultTotal = dossierService.dossierUpdate(dossier);
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
  * 案卷管理移除数据
  * 
  * @param ids
  * @return ResponseWrapper
  */
 @ResponseBody
 @PostMapping("/dossierRemove")
 @Log(title = "案卷管理移除数据", businessType = EnumEntitys.DELETE)
 @ApiOperation(value = "案卷管理移除数据", notes = "参数：数组-ids")
 public ResponseWrapper remove(String ids) {
  try {
   int resultTotal = dossierService.dossierRemove(Convert.toStrArray(ids));
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