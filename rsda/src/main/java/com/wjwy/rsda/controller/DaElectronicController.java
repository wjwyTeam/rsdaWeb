package com.wjwy.rsda.controller;
import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.common.enums.Convert;
import com.wjwy.rsda.common.enums.EnumEntitys;
import com.wjwy.rsda.common.util.Log;
import com.wjwy.rsda.common.util.ResponseWrapper;
import com.wjwy.rsda.entity.DaElectronic;
import com.wjwy.rsda.services.DaElectronicService;
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
 * @Date: 2020-01-14 10:15:13
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-14 10:16:09
 */
@RequestMapping("/electronic")
@RestController
@Api(value = "图像管理", tags = "M1-图像管理API维护")
public class DaElectronicController {
 @Autowired
 private DaElectronicService daElectronicService;

 public Logger logger = LoggerFactory.getLogger(DaElectronicController.class);

 // 跳转界面前缀
 private String prefix = "/webview/person";

 /**
  * 跳转图像管理列表主页
  * 
  * @param model
  * @return ModelAndView
  */
 @GetMapping("/daElectronicListPage")
 @ApiOperation(value = "跳转图像管理列表主页")
 public ModelAndView daElectronicListPage(@ApiIgnore ModelAndView model) {
  model.setViewName(prefix + "/daElectronicList");
  return model;
 }

 /**
  * 跳转图像管理表单主页
  * 
  * @param DaElectronicId
  * @param model
  * @return ModelAndView
  */
 @ApiOperation(value = "跳转图像管理表单主页", notes = "DaElectronicId - 图像编号")
 @GetMapping("/daElectronicFormPage")
 public ModelAndView daElectronicFormPage(String id, @ApiIgnore ModelAndView model) {
  if (StringUtil.isNotEmpty(id)) {
   model.addObject("daElectronicOne", daElectronicService.getById(id));
  }
  model.setViewName(prefix + "/daElectronicForm");
  return model;
 }

 /**
  * 列表数据查询
  * 
  * @param DaElectronic
  * @param page
  * @param limit
  * @return ResponseWrapper
  */
 @ApiOperation(value = "图像管理列表数据查询", notes = "参数:DaElectronic-对象")
 @PostMapping("/daElectronicFindList")
 public ResponseWrapper daElectronicFindList(@RequestBody DaElectronic daElectronic,
   @RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
   @RequestParam(value = "limit", required = true, defaultValue = "10") Integer limit) {
  try {
   PageInfo<DaElectronic> pageInfos = daElectronicService.findList(daElectronic, page, limit);
   return ResponseWrapper.success(HttpStatus.OK.value(), "获取成功", pageInfos.getList(), null,
     Integer.parseInt(String.valueOf(pageInfos.getTotal())));
  } catch (Exception e) {
   logger.error(e.getMessage());
  }
  return ResponseWrapper.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "服务错误，请联系管理员");
 }

 /**
  * 图像管理表单数据新增
  * 
  * @param DaElectronic
  * @return ResponseWrapper
  */
 @PostMapping("/daElectronicInsert")
 @Log(title = "图像管理", businessType = EnumEntitys.INSERT)
 @ApiOperation(value = "图像管理表单数据新增", notes = "参数:DaElectronic-对象")
 public ResponseWrapper daElectronicInsert(@RequestBody DaElectronic daElectronic) {

  try {
   int resultTotal = daElectronicService.daElectronicInsert(daElectronic);
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
  * 图像管理表单数据更新
  * 
  * @param DaElectronic
  * @return ResponseWrapper
  */
 @PostMapping("/daElectronicUpdate")
 @Log(title = "图像管理", businessType = EnumEntitys.UPDATE)
 @ApiOperation(value = "图像管理表单数据更新", notes = "参数:DaElectronic-对象")
 public ResponseWrapper daElectronicUpdate(@RequestBody DaElectronic daElectronic) {
  try {
   int resultTotal = daElectronicService.daElectronicUpdate(daElectronic);
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
  * 图像管理移除数据
  * 
  * @param ids
  * @return ResponseWrapper
  */
 @ResponseBody
 @PostMapping("/daElectronicRemove")
 @Log(title = "图像管理", businessType = EnumEntitys.DELETE)
 @ApiOperation(value = "图像管理移除数据", notes = "参数：数组-ids")
 public ResponseWrapper remove(String ids) {
  try {
   int resultTotal = daElectronicService.daElectronicRemove(Convert.toStrArray(ids));
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