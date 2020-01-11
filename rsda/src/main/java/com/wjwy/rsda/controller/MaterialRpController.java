/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2020-01-06 15:21:36
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-08 08:53:22
 */
package com.wjwy.rsda.controller;

import com.wjwy.rsda.services.MaterialRpService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;
import tk.mybatis.mapper.util.StringUtil;

@RequestMapping("/materialRp")
@RestController
@Api(value = "散材料上报模块", tags = "D1-材料上报API维护")
public class MaterialRpController {
 public Logger logger = LoggerFactory.getLogger(MaterialRpController.class);
 @Autowired
 private MaterialRpService materialRpService;

 // 跳转界面前缀
 private String prefix = "/webview/person";

 /**
  * 跳转散材料列表主页
  * 
  * @param model
  * @return ModelAndView
  */
 @GetMapping("/materialRpListPage")
 @ApiOperation(value = "跳转散材料列表主页")
 public ModelAndView materialRpListPage(@ApiIgnore ModelAndView model) {
  model.setViewName(prefix + "/materialRpList");
  return model;
 }

 /**
  * 跳转散材料表单主页
  * 
  * @param BorrowId
  * @param model
  * @return ModelAndView
  */
 @ApiOperation(value = "跳转散材料表单主页", notes = "materialRpId - 借阅编号")
 @GetMapping("/materialRpFormPage")
 public ModelAndView materialRpFormPage(String materialRpId, @ApiIgnore ModelAndView model) {
  if (StringUtil.isNotEmpty(materialRpId)) {
   model.addObject("materialRpOne", materialRpService.getById(materialRpId));
  }
  model.setViewName(prefix + "/materialRForm");
  return model;
 }

 /**
  * 跳转散材料上报干部人事档案材料转递通知单主页
  * 
  * @param BorrowId
  * @param model
  * @return ModelAndView
  */
 @ApiOperation(value = "跳转散材料上报干部人事档案材料转递通知单主页", notes = "materialRpId - 借阅编号")
 @GetMapping("/rpNoticePage")
 public ModelAndView materialRpNoticePage(String materialRpId, @ApiIgnore ModelAndView model) {
  if (StringUtil.isNotEmpty(materialRpId)) {
   model.addObject("materialRpOne", materialRpService.getById(materialRpId));
  }
  model.setViewName(prefix + "/mNoticeForm");
  return model;
 }

 /**
  * 跳转散材料上报干部人事档案材料转递通知单主页
  * 
  * @param BorrowId
  * @param model
  * @return ModelAndView
  */
 @ApiOperation(value = "跳转散材料上报干部人事档案材料转递清单主页")
 @GetMapping("/detailPage")
 public ModelAndView detailPage(String materialRpId, @ApiIgnore ModelAndView model) {
  if (StringUtil.isNotEmpty(materialRpId)) {
   model.addObject("materialRpOne", materialRpService.getById(materialRpId));
  }
  model.setViewName(prefix + "/mDetailForm");
  return model;
 }
}
