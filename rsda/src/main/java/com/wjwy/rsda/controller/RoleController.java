package com.wjwy.rsda.controller;

import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.wjwy.rsda.common.util.ResponseWrapper;
import com.wjwy.rsda.entity.Role;
import com.wjwy.rsda.services.RoleService;

import org.apache.commons.lang3.StringUtils;
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

/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-06 08:26:11
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2019-12-06 08:33:26
 */

@RequestMapping("/role")
@RestController
@Api(value = "用户所属角色", tags = "用户所属角色配置")
public class RoleController {
    /**
     * 所属角色业务层注入
     */
    @Autowired
    private RoleService roleService;

    public Logger logger = LoggerFactory.getLogger(RoleController.class);
	@Autowired
	private HttpServletRequest request;
	/**
	 * 角色展示列表页
	 * 
	 * @param department
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/roleInfo")
	public ModelAndView roleInfo(Role role, ModelAndView model) {
		model.setViewName("webview/system/role/roleList");
		return model;
	}

	/**
	 * 机构单位展示添加页
	 * @param department
	 * @param request
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/roleUpdateInfo")
	public ModelAndView roleUpdateInfo(Role role, ModelAndView model) {
		if(StringUtils.isNotEmpty(role.getId())){
			role = roleService.getId(role.getId());
        }
        model.addObject("type",true);
        if (StringUtil.isNotEmpty(request.getParameter("type"))) {
            model.addObject("type",request.getParameter("type"));
        } 
  
		model.addObject("roleOne", role);
		model.setViewName("webview/system/role/roleForm");
		return model;
	}
    /**
     * 列表查询
     * 
     * @param id
     * @param name
     * @return ResponseWrapper
     */
    @ApiOperation(value = "角色列表", notes = "参数:id-主键, name-角色名称")
    @GetMapping("/roleFindList")
    public ResponseWrapper roleFindList(String id, String name,Integer page,Integer limit) {
        try {
            PageInfo<Role> roleList = roleService.findList(id, name,page,limit);
            return ResponseWrapper.success(HttpStatus.OK.value(), "获取成功", roleList.getList(), null, Integer.parseInt(String.valueOf(roleList.getTotal())));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ResponseWrapper.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "服务错误，请联系管理员");

    }

    /**
     * 角色新增
     * 
     * @param role
     * @return ResponseWrapper
     */
    @ApiOperation(value = "角色新增", notes = "参数:Role-JSON对象")
    @PostMapping("/roleInsert")
    public ResponseWrapper insertRole(@RequestBody Role role) {

        try {
            int resultTotal = roleService.insertRole(role);
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
     * 角色更新
     * 
     * @param role
     * @return ResponseWrapper
     */
    @ApiOperation(value = "角色更新", notes = "参数:Role-JSON对象")
    @PostMapping("/updateRole")
    public ResponseWrapper updateRole(@RequestBody Role role) {
        try {
            int resultTotal = roleService.updateRole(role);
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
     * 根据用户ID批量删除角色
     * 
     * @param ids
     * @return ResponseWrapper
     */
    @ResponseBody
    @ApiOperation(value = "根据用户ID批量删除角色", notes = "根据用户ID批量删除角色")
    @PostMapping("/deleteRoleAlls")
    public ResponseWrapper deleteRoleAlls(String[] ids) {
        ResponseWrapper rmAll = null;
        for (String id : ids) {
            if (!id.isEmpty()) {
                rmAll = deleteRole(id);
            }
        }
        return rmAll;
    }

    /**
     * 根据用户ID删除角色
     * 
     * @param id
     * @return ResponseWrapper
     */
    @ApiOperation(value = "根据用户ID删除角色", notes = "参数:id-主键")
    @PostMapping("/deleteRole")
    public ResponseWrapper deleteRole(String id) {

        try {
            Role role = new Role();
            role.setId(id);
            int resultTotal = roleService.delete(role);
            if (resultTotal == 0) {
                return ResponseWrapper.success(HttpStatus.OK.value(), "禁止删除[超级管理员角色]", null, null, null);
            } else if (resultTotal == HttpStatus.GONE.value()) {
                return ResponseWrapper.success(HttpStatus.BAD_REQUEST.value(), "数据中不存在当前角色", null, null, null);
            }
            return ResponseWrapper.success(HttpStatus.OK.value(), "删除成功", null, null, null);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return ResponseWrapper.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "服务错误，请联系管理员");
    }


    /**
	 * 根据角色选择功能
	 * 
	 * @param id
	 * @param ids
	 * @return ResponseWrapper
	 */
	@ApiOperation(value = "根据角色选择功能", notes = "参数：id-角色主键,ids-功能数组")
	@GetMapping("/roleSelFunction")
	public ResponseWrapper roleSelFunction(String id, String ids[]) {

		try {
			boolean flag = roleService.roleSelFunction(id, ids);
			if (!flag) {
				return ResponseWrapper.success(HttpStatus.BAD_REQUEST.value(), "新增失败", null, null, null);
			}
			return ResponseWrapper.success(HttpStatus.OK.value(), "新增成功", null, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return ResponseWrapper.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "服务错误，请联系管理员");
    }
    

    @ApiOperation(value = "根据角色查询功能", notes = "参数：id-角色主键")
	@GetMapping("/getFunction")
	public ResponseWrapper getFunction(String id) {

		try {
			boolean flag = roleService.getFunction(id);
			if (!flag) {
				return ResponseWrapper.success(HttpStatus.BAD_REQUEST.value(), "查询失败", null, null, null);
			}
			return ResponseWrapper.success(HttpStatus.OK.value(), "查询成功", null, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return ResponseWrapper.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "服务错误，请联系管理员");
    }



}
