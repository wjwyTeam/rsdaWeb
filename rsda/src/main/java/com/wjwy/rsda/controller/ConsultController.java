package com.wjwy.rsda.controller;

import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.common.util.Log;
import com.wjwy.rsda.common.util.ResponseWrapper;
import com.wjwy.rsda.entity.Consult;
import com.wjwy.rsda.enums.Convert;
import com.wjwy.rsda.enums.EnumEntitys;
import com.wjwy.rsda.services.ConsultService;

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

@RequestMapping("/consult")
@RestController
@Api(value = "查阅管理", tags = "查阅管理API维护")
public class ConsultController {
  @Autowired
  private ConsultService consultService;

  public Logger logger = LoggerFactory.getLogger(ConsultController.class);

  // 跳转界面前缀
  private String prefix = "/webview/person";

  /**
   * 跳转查阅管理列表主页
   * 
   * @param model
   * @return ModelAndView
   */
  @GetMapping("/consultListPage")
  @ApiOperation(value = "跳转查阅管理列表主页")
  public ModelAndView ConsultListPage(ModelAndView model) {
    model.setViewName(prefix + "/consultList");
    return model;
  }

  /**
   * 跳转查阅管理表单主页
   * 
   * @param ConsultId
   * @param model
   * @return ModelAndView
   */
  @ApiOperation(value = "跳转查阅管理表单主页", notes = "ConsultId - 查阅编号")
  @GetMapping("/consultFormPage")
  public ModelAndView ConsultFormPage(String consultId, ModelAndView model) {
    if (StringUtil.isNotEmpty(consultId)) {
      model.addObject("consultOne", consultService.getById(consultId));
    }
    model.setViewName(prefix + "/consultForm");
    return model;
  }

  /**
   * 列表数据查询
   * 
   * @param Consult
   * @param page
   * @param limit
   * @return ResponseWrapper
   */
  @ApiOperation(value = "查阅管理列表数据查询", notes = "参数:Consult-对象")
  @PostMapping("/consultFindList")
  public ResponseWrapper ConsultFindList(@RequestBody Consult consult,
      @RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
      @RequestParam(value = "limit", required = true, defaultValue = "10") Integer limit) {
    try {
      PageInfo<Consult> pageInfos = consultService.findList(consult, page, limit);
      return ResponseWrapper.success(HttpStatus.OK.value(), "获取成功", pageInfos.getList(), null,
          Integer.parseInt(String.valueOf(pageInfos.getTotal())));
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return ResponseWrapper.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "服务错误，请联系管理员");
  }

  /**
   * 查阅管理表单数据新增
   * 
   * @param Consult
   * @return ResponseWrapper
   */
  @PostMapping("/consultInsert")
  @Log(title = "查阅管理表单数据新增", businessType = EnumEntitys.INSERT)
  @ApiOperation(value = "查阅管理表单数据新增", notes = "参数:Consult-对象")
  public ResponseWrapper ConsultInsert(@RequestBody Consult Consult) {

    try {
      int resultTotal = consultService.consultInsert(Consult);
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
   * 查阅管理表单数据更新
   * 
   * @param Consult
   * @return ResponseWrapper
   */
  @PostMapping("/consultUpdate")
  @Log(title = "查阅管理表单数据更新", businessType = EnumEntitys.UPDATE)
  @ApiOperation(value = "查阅管理表单数据更新", notes = "参数:Consult-对象")
  public ResponseWrapper ConsultUpdate(@RequestBody Consult consult) {
    try {
      int resultTotal = consultService.consultUpdate(consult);
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
   * 查阅管理移除数据
   * 
   * @param ids
   * @return ResponseWrapper
   */
  @ResponseBody
  @PostMapping("/consultRemove")
  @Log(title = "查阅管理移除数据", businessType = EnumEntitys.DELETE)
  @ApiOperation(value = "查阅管理移除数据", notes = "参数：数组-ids")
  public ResponseWrapper remove(String ids) {
    try {
      int resultTotal = consultService.consultRemove(Convert.toStrArray(ids));
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