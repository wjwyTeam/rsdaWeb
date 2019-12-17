package com.wjwy.rsda.controller;

import com.wjwy.rsda.entity.OperLog;
import com.wjwy.rsda.services.OperLogService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.annotations.Api;


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


	public Logger logger = LoggerFactory.getLogger(DepartmentController.class);
  /**
   * 日志展示列数据
   * @return
   */
  @GetMapping(value = "/operlogList")
  public ModelAndView operlog(OperLog operlog, ModelAndView model) {
    model.setViewName("webview/system/log/operlogList");
    return model;
  }

 
}