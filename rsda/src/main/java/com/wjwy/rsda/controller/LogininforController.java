/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2020-01-07 15:16:28
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-08 14:09:15
 */
package com.wjwy.rsda.controller;

import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.common.util.BaseController;
import com.wjwy.rsda.common.util.Log;
import com.wjwy.rsda.common.util.ResponseWrapper;
import com.wjwy.rsda.entity.Logininfor;
import com.wjwy.rsda.common.enums.Convert;
import com.wjwy.rsda.common.enums.EnumEntitys;
import com.wjwy.rsda.services.LogininforService;
import com.wjwy.rsda.services.PasswordService;

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

/**
 * 系统访问记录
 */
@RequestMapping("/logininfo")
@RestController
@Api(value = "登录日志", tags = "K2-登录日志API维护")
public class LogininforController extends BaseController {

    @Autowired
    private LogininforService logininforService;

    @Autowired
    private PasswordService passwordService;

    public Logger logger = LoggerFactory.getLogger(LogininforController.class);

    // 跳转界面前缀
    private String prefix = "/webview/system";

    /**
     * 跳转登录日志列表主页
     * 
     * @param model
     * @return ModelAndView
     */
    @GetMapping("/logininforListPage")
    @ApiOperation(value = "跳转登录日志列表主页")
    public ModelAndView logininforListPage(ModelAndView model) {
        model.setViewName(prefix + "/logininforList");
        return model;
    }

    /**
     * 跳转登录日志表单主页
     * 
     * @param infoId
     * @param model
     * @return ModelAndView
     */
    @ApiOperation(value = "跳转登录日志表单主页", notes = "infoId - 人员编号")
    @GetMapping("/logininforFormPage")
    public ModelAndView logininforFormPage(String infoId, ModelAndView model) {
        if (StringUtil.isNotEmpty(infoId)) {
            model.addObject("logininforOne", logininforService.getById(infoId));
        }
        model.setViewName(prefix + "/logininforForm");
        return model;
    }

    @ApiOperation(value = "登录日志列表数据查询", notes = "参数:logininfor-对象")
    @PostMapping("/logininforFindList")
    public ResponseWrapper logininforFindList(@RequestBody Logininfor logininfor,
            @RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = true, defaultValue = "10") Integer limit) {
        try {
            PageInfo<Logininfor> pageInfos = logininforService.findList(logininfor, page, limit);
            return ResponseWrapper.success(HttpStatus.OK.value(), "获取成功", pageInfos.getList(), null,
                    Integer.parseInt(String.valueOf(pageInfos.getTotal())));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ResponseWrapper.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "服务错误，请联系管理员");
    }

    /**
     * 登录日志表单数据新增
     * 
     * @param logininfor
     * @return ResponseWrapper
     */
    @PostMapping("/logininforInsert")
    @Log(title = "登录日志", businessType = EnumEntitys.INSERT)
    @ApiOperation(value = "登录日志表单数据新增", notes = "参数:logininfor-对象")
    public ResponseWrapper logininforInsert(@RequestBody Logininfor logininfor) {

        try {
            int resultTotal = logininforService.logininforInsert(logininfor);
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
     * 登录日志表单数据更新
     * 
     * @param logininfor
     * @return ResponseWrapper
     */
    @PostMapping("/logininforUpdate")
    @Log(title = "登录日志", businessType = EnumEntitys.UPDATE)
    @ApiOperation(value = "登录日志表单数据更新", notes = "参数:logininfor-对象")
    public ResponseWrapper logininforUpdate(@RequestBody Logininfor logininfor) {
        try {
            int resultTotal = logininforService.logininforUpdate(logininfor);
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
     * 登录日志移除数据
     * 
     * @param ids
     * @return ResponseWrapper
     */
    @ResponseBody
    @PostMapping("/logininforRemove")
    @Log(title = "登录日志", businessType = EnumEntitys.DELETE)
    @ApiOperation(value = "登录日志移除数据", notes = "参数：数组-ids")
    public ResponseWrapper logininforRemove(String ids) {
        try {
            int resultTotal = logininforService.logininforRemove(Convert.toStrArray(ids));
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

    /**
     * 
     * @param loginName
     * @return
     */
    @ResponseBody
    @PostMapping("/unlock")
    @Log(title = "登录日志", businessType = EnumEntitys.OTHER)
    @ApiOperation(value = "登录日志账户解锁", notes = "参数：账号-loginName")
    public ResponseWrapper unlock(String loginName) {
        try {
            passwordService.unlock(loginName);

            return ResponseWrapper.success(HttpStatus.OK.value(), "解锁成功", null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return ResponseWrapper.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "服务错误，请联系管理员");
    }

    /**
     * 
     * @return
     */
    @ResponseBody
    @PostMapping("/clear")
    @Log(title = "登录日志", businessType = EnumEntitys.DELETE)
    @ApiOperation(value = "登录日志账户解锁", notes = "参数：账号-loginName")
    public ResponseWrapper clear() {
        try {
            logininforService.clear();
            return ResponseWrapper.success(HttpStatus.OK.value(), "清空成功", null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return ResponseWrapper.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "服务错误，请联系管理员");
    }
}
