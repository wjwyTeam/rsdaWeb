package com.wjwy.rsda.controller;

import com.wjwy.rsda.services.MaterialAcService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import tk.mybatis.mapper.util.StringUtil;

/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2020-01-06 15:44:22
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-06 15:45:39
 */
@RequestMapping("/materialAc")
@RestController
@Api(value = "散材料归档列表模块", tags = "散材料归档列表模块API维护")
public class MaterialAController {


 public Logger logger = LoggerFactory.getLogger(MaterialAController.class);
 @Autowired
 private MaterialAcService materialAcService;

 // 跳转界面前缀
 private String prefix = "/webview/person";

 /**
  * 跳转散材料归档列表主页
  * 
  * @param model
  * @return ModelAndView
  */
 @GetMapping("/materialAcListPage")
 @ApiOperation(value = "跳转散材料归档列表主页")
 public ModelAndView materialAcListPage(ModelAndView model) {
  model.setViewName(prefix + "/materialAcList");
  return model;
 }

 /**
  * 跳转散材料散材料归档表单主页
  * 
  * @param BorrowId
  * @param model
  * @return ModelAndView
  */
 @ApiOperation(value = "跳转散材料散材料归档表单主页", notes = "materialAcId - 散材料归档编号")
 @GetMapping("/materialAcFormPage")
 public ModelAndView materialAcFormPage(String materialAcId, ModelAndView model) {
  if (StringUtil.isNotEmpty(materialAcId)) {
   model.addObject("materialAcOne", materialAcService.getById(materialAcId));
  }
  model.setViewName(prefix + "/materialAcForm");
  return model;
 }
}

