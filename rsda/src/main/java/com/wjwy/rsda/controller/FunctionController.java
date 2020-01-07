package com.wjwy.rsda.controller;

import java.util.ArrayList;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.common.util.Log;
import com.wjwy.rsda.common.util.ResponseWrapper;
import com.wjwy.rsda.entity.Function;
import com.wjwy.rsda.enums.EnumEntitys;
import com.wjwy.rsda.services.FunctionService;

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
 * @Date: 2019-12-09 17:05:18
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2019-12-09 17:05:58
 */
@RequestMapping("/function")
@RestController
@Api(value = "菜单管理数据信息维护", tags = "G1-菜单管理API维护")
public class FunctionController {
    @Autowired
    private FunctionService functionService;
    public Logger logger = LoggerFactory.getLogger(FunctionController.class);
    private String prefix = "/webview/system/function";

    /**
     * 
     * @return String
     */
    @GetMapping("/functionList")
    @ApiOperation(value = "菜单管理列表主页")
    public ModelAndView functionData(ModelAndView model) {
        model.setViewName(prefix + "/functionList");
        return model;
    }

    /**
     * 
     * @return Icon
     */
    @GetMapping("/icon")
    @ApiOperation(value = "菜单管理表单图标")
    public ModelAndView functionIcon(ModelAndView model) {
        model.setViewName(prefix + "/functionIcon");
        return model;
    }

    /**
     * 修改字典类型
     */
    @ApiOperation(value = "菜单管理表单主页", notes = "id - 编号")
    @GetMapping("/functionEdit")
    public ModelAndView functionedit(String id, ModelAndView model) {
        if (StringUtil.isNotEmpty(id)) {
            model.addObject("functionOne", functionService.selectFunctionById(id));
        }
        model.setViewName(prefix + "/functionForm");
        return model;
    }

    /**
     * 加载部门管理左边的部门树的json
     * 
     * @param parentId
     * @return ResponseWrapper
     */
    @ApiOperation(value = "菜单树展示 ", notes = "groupId - 父编号")
    @GetMapping(value = "/functionTree")
    public ResponseWrapper loadDepartmentLeftTreeJson(String groupId) {
        List<Function> functionList = new ArrayList<Function>();

        try {
            functionList = functionService.list(groupId);
            return ResponseWrapper.success(HttpStatus.OK.value(), "获取成功", functionList, null, null);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ResponseWrapper.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "服务错误，请联系管理员");
    }

    /**
     * 列表查询
     * 
     * @param id
     * @param name
     * @return ResponseWrapper
     */
    @PostMapping("/functionFindList")
    @ApiOperation(value = "菜单管理列表数据查询", notes = "参数:id-主键, name-功能名称")
    public ResponseWrapper functionFindList(String id, String functionName,
            @RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = true, defaultValue = "10") Integer limit) {
        try {
            PageInfo<Function> pageInfos = functionService.findList(id, functionName, page, limit);
            return ResponseWrapper.success(HttpStatus.OK.value(), "获取成功", pageInfos.getList(), null,
                    Integer.parseInt(String.valueOf(pageInfos.getTotal())));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ResponseWrapper.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "服务错误，请联系管理员");

    }

    /**
     * 功能新增
     * 
     * @param function
     * @return ResponseWrapper
     */
    @PostMapping("/functionInsert")
    @Log(title = "菜单管理", businessType = EnumEntitys.INSERT)
    @ApiOperation(value = "菜单管理表单数据新增", notes = "参数:function-对象")
    public ResponseWrapper functionInsert(@RequestBody Function function) {

        try {
            int resultTotal = functionService.insertFunction(function);
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
     * 功能更新
     * 
     * @param function
     * @return ResponseWrapper
     */
    @PostMapping("/updateFunction")
    @Log(title = "菜单管理", businessType = EnumEntitys.UPDATE)
    @ApiOperation(value = "菜单管理表单数据更新", notes = "参数:function-对象")
    public ResponseWrapper updateFunction(@RequestBody Function function) {
        try {
            int resultTotal = functionService.updateFunction(function);
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
     * 根据用户ID批量删除功能
     * 
     * @param ids
     * @return ResponseWrapper
     */
    @ResponseBody
    @PostMapping("/deleteFunctionAlls")
    @Log(title = "菜单管理", businessType = EnumEntitys.DELETE)
    @ApiOperation(value = "菜单管理表单数据批量移除", notes = "根据用户ID批量删除功能")
    public ResponseWrapper deleteFunctionAlls(String[] ids) {
        ResponseWrapper rmAll = null;
        for (String id : ids) {
            if (!id.isEmpty()) {
                rmAll = deleteFunction(id);
            }
        }
        return rmAll;
    }

    /**
     * 根据用户ID删除功能
     * 
     * @param id
     * @return ResponseWrapper
     */
    @PostMapping("/deleteFunction")
    @Log(title = "菜单管理", businessType = EnumEntitys.DELETE)
    @ApiOperation(value = "菜单管理表单数据移除", notes = "参数:id-主键")
    public ResponseWrapper deleteFunction(String id) {

        try {
            Function function = new Function();
            function.setId(id);
            int resultTotal = functionService.delete(function);
            if (resultTotal == 0) {
                return ResponseWrapper.success(HttpStatus.OK.value(), "禁止删除[当前功能]", null, null, null);
            }
            return ResponseWrapper.success(HttpStatus.OK.value(), "删除成功", null, null, null);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return ResponseWrapper.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "服务错误，请联系管理员");
    }

}