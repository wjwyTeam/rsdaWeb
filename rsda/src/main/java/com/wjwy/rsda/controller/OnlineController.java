/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-19 17:50:35
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-13 14:19:43
 */
package com.wjwy.rsda.controller;

import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.common.tool.session.OnlineSession;
import com.wjwy.rsda.common.tool.session.OnlineSessionDAO;
import com.wjwy.rsda.common.util.Log;
import com.wjwy.rsda.common.util.ResponseWrapper;
import com.wjwy.rsda.common.util.ShiroUtils;
import com.wjwy.rsda.entity.Online;
import com.wjwy.rsda.common.enums.EnumEntitys;
import com.wjwy.rsda.services.OnlineService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 在线用户监控
 */
@RestController
@RequestMapping("/online")
@Api(value = "在线用户监控", tags = "L1-在线监控API维护")
public class OnlineController {
  public Logger logger = LoggerFactory.getLogger(OnlineController.class);
  private String prefix = "/webview/system/online";

  @Autowired
  private OnlineService userOnlineService;
  @Autowired
  private OnlineSessionDAO onlineSessionDAO;

  /**
   * 在线用户界面
   * 
   * @param model
   * @return
   */
  @GetMapping("/onlineList")
  @ApiOperation(value = "在线用户界面")
  public ModelAndView dictData(@ApiIgnore ModelAndView model) {
    model.setViewName(prefix + "/onlineUser");
    return model;
  }

  /**
   * 列表数据查询
   * 
   * @param userOnline
   * @return
   */
  @PostMapping("/listTwo")
  @ApiOperation(value = "列表数据查询", notes = "userOnline-object")
  public ResponseWrapper list(Online userOnline,
      @RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
      @RequestParam(value = "limit", required = true, defaultValue = "10") Integer limit) {
    try {
      PageInfo<Online> pageInfos = userOnlineService.selectUserOnlineList(userOnline, page, limit);
      return ResponseWrapper.success(HttpStatus.OK.value(), "获取成功", pageInfos.getList(), null,
          Integer.parseInt(String.valueOf(pageInfos.getTotal())));
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return ResponseWrapper.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "服务错误，请联系管理员");
  }

  @Log(title = "在线用户", businessType = EnumEntitys.FORCE)
  @ApiOperation(value = "在线用户正常退出", notes = "ids")
  @PostMapping("/batchForceLogout")
  @ResponseBody
  public ResponseWrapper batchForceLogout(@RequestParam("ids[]") String[] ids) {

    try {
      for (String sessionId : ids) {
        Online online = userOnlineService.selectOnlineById(sessionId);
        if (online == null) {
          return ResponseWrapper.error(HttpStatus.BAD_REQUEST.value(), "用户已下线", null);
        }
        OnlineSession onlineSession = (OnlineSession) onlineSessionDAO.readSession(online.getSessionId());
        if (onlineSession == null) {
          return ResponseWrapper.error(HttpStatus.BAD_REQUEST.value(), "用户已下线", null);
        }
        if (sessionId.equals(ShiroUtils.getSessionId())) {
          return ResponseWrapper.error(HttpStatus.BAD_REQUEST.value(), "当前登陆用户无法强退", null);
        }
        onlineSession.setStatus((String)EnumEntitys.OFFLINE.getDesc());
        onlineSessionDAO.update(onlineSession);
        online.setStatus((String)EnumEntitys.OFFLINE.getDesc());
        userOnlineService.saveOnline(online);
        return ResponseWrapper.success(HttpStatus.OK.value(), "获取成功", null, null, null);
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return ResponseWrapper.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "服务错误，请联系管理员");

  }

  @Log(title = "在线用户强制退出", businessType = EnumEntitys.FORCE)
  @PostMapping("/forceLogout")
  @ResponseBody
  public ResponseWrapper forceLogout(String sessionId) {
    try {

      Online online = userOnlineService.selectOnlineById(sessionId);
      if (sessionId.equals(ShiroUtils.getSessionId())) {
        return ResponseWrapper.error(HttpStatus.BAD_REQUEST.value(), "当前登陆用户无法强退", null);
      }
      if (online == null) {
        return ResponseWrapper.error(HttpStatus.BAD_REQUEST.value(), "用户已下线", null);
      }
      OnlineSession onlineSession = (OnlineSession) onlineSessionDAO.readSession(online.getSessionId());
      if (onlineSession == null) {
        return ResponseWrapper.error(HttpStatus.BAD_REQUEST.value(), "用户已下线", null);
      }
      onlineSession.setStatus((String) EnumEntitys.OFFLINE.getDesc());
      onlineSessionDAO.update(onlineSession);
      online.setStatus((String)EnumEntitys.OFFLINE.getDesc());
      userOnlineService.saveOnline(online);
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return ResponseWrapper.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "服务错误，请联系管理员");
  }

}
