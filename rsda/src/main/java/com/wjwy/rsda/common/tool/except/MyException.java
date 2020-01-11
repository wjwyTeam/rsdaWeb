/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2020-01-11 09:15:02
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-11 09:29:37
 */
package com.wjwy.rsda.common.tool.except;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyException implements ErrorController {

 /**
  * 异常的分别处理--------------------------------》》》》》》》》》》》》
  *
  * @param request
  * @return
  */
 @RequestMapping("/error")
 public String handleError(HttpServletRequest request) {
  // 获取statusCode:401,404,500
  Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
  switch (statusCode) {
  case 400:
   return "/400";
  case 500:
   return "/500";
  case 404:
   return "/404";
  default:
   return "/500";
  }

 }

 /**
     * 该方法和以上方法不能同时共存，因为@RequestMapping("/error")相同
     * 异常的统一处理----------------------------》》》》》》》》》》》》》》》》》》
     *是有的错误都到这个页面
     * @return
     */
 @Override
 @GetMapping("/error")
 public String getErrorPath() {
  return "redirect:/login";
 }
}