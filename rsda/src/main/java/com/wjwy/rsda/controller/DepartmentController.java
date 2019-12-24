/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-04 08:50:53
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2019-12-24 08:16:15
 */
package com.wjwy.rsda.controller;

import java.util.ArrayList;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.common.util.ResponseWrapper;
import com.wjwy.rsda.entity.Department;
import com.wjwy.rsda.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/dept")
@RestController
@Api(value = "单位组织结构", tags = "单位机构树维护")
public class DepartmentController {

	@Autowired
	private DepartmentService deptService;
	@Autowired
	private HttpServletRequest request;
	public Logger logger = LoggerFactory.getLogger(DepartmentController.class);

	/**
	 * 机构数展示列表页
	 * 
	 * @param department
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/departmentInfo")
	public ModelAndView departmentInfo(Department department, ModelAndView model) {
		boolean flag = true;
		if(StringUtils.isNotEmpty(request.getParameter("hideshowType"))){
			flag = false;
		}
		model.addObject("hiddenType", flag);
		model.setViewName("webview/system/dept/deptList");
		return model;
	}

	/**
	 * 机构单位展示添加页
	 * @param department
	 * @param request
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/deptInfo")
	public ModelAndView updateInfo(	Department department, ModelAndView model) {
		if(StringUtils.isNotEmpty(department.getId())){
			department = deptService.getDept(department.getId());
		}
		model.addObject("deptOne", department);
		model.setViewName("webview/system/dept/deptForm");
		return model;
	}

	/**
	 * 加载部门管理左边的部门树的json
	 * 
	 * @param parentId
	 * @return ResponseWrapper
	 */
	@ApiOperation(value = "机构树展示 ")
	@PostMapping(value = "/unitTree")
	public List<Department> loadDepartmentLeftTreeJson(@RequestBody Department department) {
		List<Department> departmentList = new ArrayList<Department>();
		try {
			departmentList = deptService.list(department.getId());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return departmentList;
	}

	/**
	 * 
	 * @param department
	 * @return
	 */
	@ApiOperation(value = "机构列表", notes = "参数:department")
	@PostMapping(value = "/departmentPageInfoList")
	public ResponseWrapper departmentPageInfoList(Department department) {
		try {
 
			List<Department> treeNodes = deptService.findList(department);
			return ResponseWrapper.success(HttpStatus.OK.value(), "获取成功", treeNodes, null, treeNodes.size());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return ResponseWrapper.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "服务错误，请联系管理员");
	}


	/**
	 * 界面列表展示主页
	 * @param department
	 * @return
	 */
	@ApiOperation(value = "机构单点查询列表", notes = "参数:id-机构ID,name-机构名称,unitType-机构类型,page-当前页,limit-条数")
	@PostMapping(value = "/findUnitList")
	public ResponseWrapper findUnitList(String id,String name,Integer unitType,Integer page,Integer limit) {
		try {

			PageInfo<Department> treeNodesPageInfos = deptService.findTreeList(id,name,unitType,page,limit);
			return ResponseWrapper.success(HttpStatus.OK.value(), "获取成功", treeNodesPageInfos.getList(), null,Integer.parseInt(String.valueOf(treeNodesPageInfos.getTotal())));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return ResponseWrapper.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "服务错误，请联系管理员");
	}

	/**
	 * 新增机构
	 * 
	 * @param department
	 * @return ResponseWrapper
	 */
	@ApiOperation(value = "新增机构")
	@PostMapping(value = "/insertUnit")
	public ResponseWrapper insertUnitTree(@RequestBody Department department) {
		try {
			int resultTotal = deptService.insertUnitTree(department);
			if (resultTotal == 0) {
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

	/**
	 * 更新机构
	 * 
	 * @param department
	 * @return ResponseWrapper
	 */
	@ApiOperation(value = "更新机构")
	@PostMapping(value = "/updateUnitTree")
	@ResponseBody
	public ResponseWrapper updateUnitTree(@RequestBody Department department) {
		try {
			int resultTotal = deptService.updateUnitTree(department);
			if (resultTotal == 0) {
				return ResponseWrapper.success(HttpStatus.BAD_REQUEST.value(), "更新失败", null, null, null);
			}
			return ResponseWrapper.success(HttpStatus.OK.value(), "更新成功", null, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return ResponseWrapper.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "服务错误，请联系管理员");
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "删除机构")
	@PostMapping(value = "/deleteUnit")
	public ResponseWrapper deleteUnit(String id) {
		try {
			Department department = new Department();
			department.setId(id);
			int resultTotal = deptService.deleteUnitTree(department);
			if (resultTotal == 0) {
				return ResponseWrapper.success(HttpStatus.OK.value(), "禁止删除[当前用户]", null, null, null);
			} else if (resultTotal == HttpStatus.GONE.value()) {
				return ResponseWrapper.success(HttpStatus.BAD_REQUEST.value(), "数据中不存在当前用户", null, null, null);
			}
			return ResponseWrapper.success(HttpStatus.OK.value(), "删除成功", null, null, null);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return ResponseWrapper.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "服务错误，请联系管理员");
	}
	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	@ApiOperation(value = "批量删除机构")
	@PostMapping(value = "/deleteUnitAll")
	public ResponseWrapper deleteUnitAll(String[] ids) {
		ResponseWrapper rmAll = null;
		for (String deptId : ids) {
			if (!deptId.isEmpty()) {
				rmAll = deleteUnit(deptId);
			}
		}
		return rmAll;
	}

	/**
	 * 
	 * @param ids
	 * @param id
	 * @return 
	 */
	@ApiOperation(value = "机构上移/下移" ,notes = "参数:+"
	+"ids:机构选中移动的数组ID,"
	+"id-移动参考数据行ID,"
	+"option-true 上移/false 下移"
	+"注意:上/下移数组中按照列表顺序传递组织ID拼接 以移动参考行为标准 就近数组重组"+"/")
	@PostMapping(value = "/moveUpOrDown")
	public ResponseWrapper moveUpOrDown(@RequestBody Department department){
		try {
			String msg = "";
			int resultTotal = deptService.moveUpOrDown(department.getIds(),department.getId(),department.getOption());
			if (department.getOption()) {
				msg = "上移";
			} else {
				msg = "下移";
			}
			if (resultTotal == 0) {
				return ResponseWrapper.success(HttpStatus.BAD_REQUEST.value(), msg+"失败", null, null, null);
			}
			return ResponseWrapper.success(HttpStatus.OK.value(), msg+"成功", null, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return ResponseWrapper.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "服务错误，请联系管理员");
 	 }
	}