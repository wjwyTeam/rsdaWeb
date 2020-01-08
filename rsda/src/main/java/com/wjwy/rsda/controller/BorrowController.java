/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2020-01-03 09:35:54
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-08 08:43:40
 */

package com.wjwy.rsda.controller;
import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.common.util.Log;
import com.wjwy.rsda.common.util.ResponseWrapper;
import com.wjwy.rsda.entity.Borrow;
import com.wjwy.rsda.enums.Convert;
import com.wjwy.rsda.enums.EnumEntitys;
import com.wjwy.rsda.services.BorrowService;
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

@RequestMapping("/borrow")
@RestController
@Api(value = "借阅管理", tags = "B2-借阅管理API维护")
public class BorrowController {
 @Autowired
 private BorrowService borrowService;

 public Logger logger = LoggerFactory.getLogger(BorrowController.class);

 // 跳转界面前缀
 private String prefix = "/webview/person";

 /**
  * 跳转借阅管理列表主页
  * 
  * @param model
  * @return ModelAndView
  */
 @GetMapping("/borrowListPage")
 @ApiOperation(value = "跳转借阅管理列表主页")
 public ModelAndView BorrowListPage(ModelAndView model) {
  model.setViewName(prefix + "/borrowList");
  return model;
 }

 /**
  * 跳转借阅管理表单主页
  * 
  * @param BorrowId
  * @param model
  * @return ModelAndView
  */
 @ApiOperation(value = "跳转借阅管理表单主页", notes = "borrowId - 借阅编号")
 @GetMapping("/borrowFormPage")
  public ModelAndView BorrowFormPage(String borrowId, ModelAndView model) {
    if (StringUtil.isNotEmpty(borrowId)) {
      model.addObject("BorrowOne", borrowService.getById(borrowId));
    }
    model.setViewName(prefix + "/borrowForm");
    return model;
  }

 
  /**
   * 跳转借阅审批表单主页
   * 
   * @param ConsultId
   * @param model
   * @return ModelAndView
   */
  @ApiOperation(value = "跳转借阅审批表单主页", notes = "borrowId - 借阅编号")
  @GetMapping("/applyFormPage")
  public ModelAndView applyFormPage(String borrowId, ModelAndView model) {
    if (StringUtil.isNotEmpty(borrowId)) {
      model.addObject("consultOne", borrowService.getById(borrowId));
    }
    model.setViewName(prefix + "/bApplyForm");
    return model;
  }

 /**
  * 列表数据查询
  * 
  * @param Borrow
  * @param page
  * @param limit
  * @return ResponseWrapper
  */
 @ApiOperation(value = "借阅管理列表数据查询", notes = "参数:Borrow-对象")
 @PostMapping("/borrowFindList")
 public ResponseWrapper BorrowFindList(@RequestBody Borrow borrow,
   @RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
   @RequestParam(value = "limit", required = true, defaultValue = "10") Integer limit) {
  try {
   PageInfo<Borrow> pageInfos = borrowService.findList(borrow, page, limit);
   return ResponseWrapper.success(HttpStatus.OK.value(), "获取成功", pageInfos.getList(), null,
     Integer.parseInt(String.valueOf(pageInfos.getTotal())));
  } catch (Exception e) {
   logger.error(e.getMessage());
  }
  return ResponseWrapper.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "服务错误，请联系管理员");
 }

 /**
  * 借阅管理表单数据新增
  * 
  * @param Borrow
  * @return ResponseWrapper
  */
 @PostMapping("/borrowInsert")
 @Log(title = "借阅管理", businessType = EnumEntitys.INSERT)
 @ApiOperation(value = "借阅管理表单数据新增", notes = "参数:Borrow-对象")
 public ResponseWrapper BorrowInsert(@RequestBody Borrow borrow) {

  try {
   int resultTotal = borrowService.borrowInsert(borrow);
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
  * 借阅管理表单数据更新
  * 
  * @param Borrow
  * @return ResponseWrapper
  */
 @PostMapping("/borrowUpdate")
 @Log(title = "借阅管理", businessType = EnumEntitys.UPDATE)
 @ApiOperation(value = "借阅管理表单数据更新", notes = "参数:Borrow-对象")
 public ResponseWrapper BorrowUpdate(@RequestBody Borrow borrow) {
  try {
   int resultTotal = borrowService.borrowUpdate(borrow);
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
  * 借阅管理移除数据
  * 
  * @param ids
  * @return ResponseWrapper
  */
 @ResponseBody
 @PostMapping("/borrowRemove")
 @Log(title = "借阅管理", businessType = EnumEntitys.DELETE)
 @ApiOperation(value = "借阅管理移除数据", notes = "参数：数组-ids")
 public ResponseWrapper remove(String ids) {
  try {
   int resultTotal = borrowService.borrowRemove(Convert.toStrArray(ids));
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