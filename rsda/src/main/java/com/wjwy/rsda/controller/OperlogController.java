/*
 * @Descripttion:
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-17 18:13:24
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2019-12-20 16:26:13
 */
package com.wjwy.rsda.controller;

import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.common.util.ResponseWrapper;
import com.wjwy.rsda.entity.OperLog;
import com.wjwy.rsda.services.OperLogService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/*
 * @Descripttion:
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-17 18:13:24
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2019-12-17 18:15:03
 */
@RequestMapping("/log")
@RestController
@Api(value = "日志管理", tags = "日志管理")
public class OperlogController {
  @Autowired
  private OperLogService operlogService;

  private String prefix = "webview/system/log/";
  public Logger logger = LoggerFactory.getLogger(DepartmentController.class);

  /**
   * 日志展示列数据
   * 
   * @return
   */
  @ApiOperation(value = "日志列表页", notes = "参数：operlog JSON 对象")
  @GetMapping(value = "/operlogList")
  public ModelAndView operlogList(ModelAndView model) {
    model.setViewName(prefix + "operlogList");
    return model;
  }

  /**
   * 日志展示表单数据
   * 
   * @return
   */
  @ApiOperation(value = "日志表单页", notes = "参数：operlog JSON 对象")
  @GetMapping(value = "/operlogForm")
  public ModelAndView operlogForm(String operId, ModelAndView model) {
    OperLog operLogOne = operlogService.getId(Long.parseLong(operId));
    model.addObject("operLog", operLogOne);
    model.setViewName(prefix + "operlogForm");
    return model;
  }

  /**
   *
   * @param pageNum
   * @param pageSize
   * @return
   */
  @PostMapping("/logPageInfoList")
  @ApiOperation(value = "日志分页列表", notes = "参数：pageNum-当前页,pageSize-每页条数")
  public ResponseWrapper logPageInfoList(String operName, Integer page, Integer limit) {
    try {
      PageInfo<OperLog> pageInfos = operlogService.getPageList(operName, page, limit);
      return ResponseWrapper.success(HttpStatus.OK.value(), "获取成功", pageInfos.getList(), null,
          Integer.parseInt(String.valueOf(pageInfos.getTotal())));
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return ResponseWrapper.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "服务错误，请联系管理员");
  }

}