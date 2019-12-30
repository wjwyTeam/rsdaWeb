package com.wjwy.rsda.controller;

import java.util.ArrayList;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.common.util.ResponseWrapper;
import com.wjwy.rsda.entity.Function;
import com.wjwy.rsda.services.FunctionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
@Api(value = "功能信息", tags = "子功能菜单维护")
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
  @ApiOperation(value = "功能列表页")
  public ModelAndView functionData(ModelAndView model) {
    model.setViewName(prefix+"/functionList");
    return model;
  }


    /**
   * 修改字典类型
   */
  @ApiOperation(value = "修改功能表单", notes = "functionId - 字典编号")
  @GetMapping("/functionEdit")
  public ModelAndView functionedit(String functionId, ModelAndView model) {
    if (StringUtil.isNotEmpty(functionId)) {
      model.addObject("functionOne", functionService.selectFunctionById(functionId));
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
	@ApiOperation(value = "菜单树展示 ")
	@GetMapping(value = "/functionTree")
	public ResponseWrapper loadDepartmentLeftTreeJson(String groupId) {
		List<Function> functionList = new ArrayList<Function>();
        
        try {
            functionList = functionService.list(groupId);
            return ResponseWrapper.success(HttpStatus.OK.value(), "获取成功", functionList,null,null);
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
    @ApiOperation(value = "功能列表", notes = "参数:id-主键, name-功能名称")
    @PostMapping("/functionFindList")
    public ResponseWrapper functionFindList(String functionId, String functionName,Integer page, Integer limit) {
        try {
            PageInfo<Function> pageInfos = functionService.findList(functionId, functionName,page,limit);
            return ResponseWrapper.success(HttpStatus.OK.value(), "获取成功", pageInfos.getList(),null,Integer.parseInt(String.valueOf(pageInfos.getTotal())));
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
    @ApiOperation(value = "功能新增", notes = "参数:Function-JSON对象")
    @PostMapping("/functionInsert")
    public ResponseWrapper functionInsert(@RequestBody Function function) {

        try {
            int resultTotal = functionService.insertFunction(function);
            if (resultTotal == 0) {
                return ResponseWrapper.success(HttpStatus.BAD_REQUEST.value(), "新增失败", null, null,null);
            }
            return ResponseWrapper.success(HttpStatus.OK.value(), "新增成功", null, null,null);
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
    @ApiOperation(value = "功能更新", notes = "参数:Function-JSON对象")
    @PostMapping("/updateFunction")
    public ResponseWrapper updateFunction(@RequestBody Function function) {
        try {
            int resultTotal = functionService.updateFunction(function);
            if (resultTotal == 0) {
                return ResponseWrapper.success(HttpStatus.BAD_REQUEST.value(), "更新失败", null, null,null);
            }
            return ResponseWrapper.success(HttpStatus.OK.value(), "更新成功", null, null,null);
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
    @ApiOperation(value = "根据用户ID批量删除功能", notes = "根据用户ID批量删除功能")
    @PostMapping("/deleteFunctionAlls")
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
    @ApiOperation(value = "根据用户ID删除功能", notes = "参数:id-主键")
    @PostMapping("/deleteFunction")
    public ResponseWrapper deleteFunction(String id) {

        try {
            Function function = new Function();
            function.setId(id);
            int resultTotal = functionService.delete(function);
            if (resultTotal == 0) {
                return ResponseWrapper.success(HttpStatus.OK.value(), "禁止删除[当前功能]", null, null,null);
            } else if (resultTotal == HttpStatus.GONE.value()) {
                return ResponseWrapper.success(HttpStatus.BAD_REQUEST.value(), "数据中不存在当前功能", null, null,null);
            }
            return ResponseWrapper.success(HttpStatus.OK.value(), "删除成功", null, null,null);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return ResponseWrapper.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "服务错误，请联系管理员");
    }


    @RequestMapping(value="/treedata")
	@ResponseBody
	public ResponseWrapper treedata() {
		// Sort sort = Sort.by("forder");
		// List<Function> list = functionService.findAll(sort);
        return ResponseWrapper.success(HttpStatus.OK.value(), "获取成功", null, null,null);
	}
}