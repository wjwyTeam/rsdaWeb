package com.wjwy.rsda.controller;

import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.common.util.Log;
import com.wjwy.rsda.common.util.ResponseWrapper;
import com.wjwy.rsda.entity.Personal;
import com.wjwy.rsda.common.enums.Convert;
import com.wjwy.rsda.common.enums.EnumEntitys;
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
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2020-01-02 10:01:53
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-02 10:03:37
 */

@RequestMapping("/person")
@RestController
@Api(value = "人员管理", tags = "F1-人员管理API维护")
public class PersonalController {
 @Autowired
 private PersonalService personalService;

 public Logger logger = LoggerFactory.getLogger(PersonalController.class);

 // 跳转界面前缀
  private String prefix = "/webview/person";
 
  @Autowired
  private HttpServletRequest request;
 /**
  * 跳转人员管理列表主页
  * 
  * @param model
  * @return ModelAndView
  */
 @GetMapping("/personalListPage")
 @ApiOperation(value = "跳转人员管理列表主页")
  public ModelAndView personalListPage(ModelAndView model) {
    model.addObject("xzStatus",true);
    if (StringUtil.isNotEmpty(request.getParameter("xz"))) {
      //选择控制List - 隐藏按钮
      model.addObject("xzStatus", request.getParameter("xz"));
  }
  model.setViewName(prefix + "/personalList");
  return model;
 }

 /**
  * 跳转人员管理表单主页
  * 
  * @param personalId
  * @param model
  * @return ModelAndView
  */
 @ApiOperation(value = "跳转人员管理表单主页", notes = "personalId - 人员编号")
 @GetMapping("/personalFormPage")
 public ModelAndView personalFormPage(String personalId, ModelAndView model) {
  if (StringUtil.isNotEmpty(personalId)) {
   model.addObject("personalOne", personalService.getById(personalId));
  }
  model.setViewName(prefix + "/personalForm");
  return model;
 }

 /**
  * 列表数据查询
  * 
  * @param personal
  * @param page
  * @param limit
  * @return ResponseWrapper
  */
 @ApiOperation(value = "人员管理列表数据查询", notes = "参数:personal-对象")
 @PostMapping("/personalFindList")
 public ResponseWrapper personalFindList(@RequestBody Personal personal, @RequestParam(value="page",required=true,defaultValue="1") Integer page,@RequestParam(value="limit",required=true,defaultValue="10")  Integer limit) {
  try {
   PageInfo<Personal> pageInfos = personalService.findList(personal, page, limit);
   return ResponseWrapper.success(HttpStatus.OK.value(), "获取成功", pageInfos.getList(), null,
     Integer.parseInt(String.valueOf(pageInfos.getTotal())));
  } catch (Exception e) {
   logger.error(e.getMessage());
  }
  return ResponseWrapper.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "服务错误，请联系管理员");
 }

 /**
  * 人员管理表单数据新增
  * 
  * @param personal
  * @return ResponseWrapper
  */
 @PostMapping("/personalInsert")
 @Log(title = "人员管理", businessType = EnumEntitys.INSERT)
 @ApiOperation(value = "人员管理表单数据新增", notes = "参数:personal-对象")
 public ResponseWrapper personalInsert(@RequestBody Personal personal) {

  try {
   int resultTotal = personalService.personalInsert(personal);
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
  * 人员管理表单数据更新
  * 
  * @param personal
  * @return ResponseWrapper
  */
 @PostMapping("/personalUpdate")
 @Log(title = "人员管理", businessType = EnumEntitys.UPDATE)
 @ApiOperation(value = "人员管理表单数据更新", notes = "参数:personal-对象")
 public ResponseWrapper personalUpdate(@RequestBody Personal personal) {
  try {
   int resultTotal = personalService.personalUpdate(personal);
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
  * 人员管理移除数据
  * @param ids
  * @return ResponseWrapper
  */
 @ResponseBody
 @PostMapping("/personalRemove")
 @Log(title = "人员管理", businessType = EnumEntitys.DELETE)
 @ApiOperation(value = "人员管理移除数据", notes = "参数：数组-ids")
 public ResponseWrapper remove(String ids) {
  try {
   int resultTotal = personalService.personalRemove(Convert.toStrArray(ids));
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