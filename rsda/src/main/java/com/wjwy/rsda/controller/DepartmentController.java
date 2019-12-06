/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-04 08:50:53
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2019-12-04 17:06:13
 */
package com.wjwy.rsda.controller;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.wjwy.rsda.common.TreeNode;
import com.wjwy.rsda.common.resultEntity.ResultJson;
import com.wjwy.rsda.entity.Department;
import com.wjwy.rsda.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
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
	public Logger logger = LoggerFactory.getLogger(DepartmentController.class);

	/**
	 * 加载部门管理左边的部门树的json
	 * 
	 * @param department
	 * @return
	 */
	@ApiOperation(value = "机构树展示 ")
	@GetMapping(value = "/unitTree")
	public ResultJson loadDepartmentLeftTreeJson(String parentId) {

		ResultJson resultJson = new ResultJson();
		List<TreeNode> treeNodes;
		try {
			treeNodes = deptService.list(parentId);
			resultJson.status = true;
			resultJson.msg = "获取成功";
			resultJson.setData(treeNodes);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}

		return resultJson;
	}

	/**
	 * 新增机构
	 * 
	 * @param department
	 * @return
	 */
	@ApiOperation(value = "新增机构")
	@PostMapping(value = "/insertUnit")
	public JSONObject insertUnitTree(@RequestBody Department department) {
		JSONObject resultJSONObject = new JSONObject();
		try {
			int resultTotal = 0;// 操作的记录数
			resultTotal = deptService.insertUnitTree(department);
			boolean flag = false;
			if (resultTotal > 0) {
				flag = true;
			}
			resultJSONObject.put("success", flag);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return resultJSONObject;
	}

	/**
	 * 更新机构
	 * 
	 * @param department
	 * @return
	 */
	@ApiOperation(value = "更新机构")
	@PostMapping(value = "/updateUnitTree")
	public JSONObject updateUnitTree(@RequestBody Department department) {
		JSONObject resultJSONObject = new JSONObject();
		try {
			int resultTotal = 0;// 操作的记录数
			resultTotal = deptService.updateUnitTree(department);
			boolean flag = false;
			if (resultTotal > 0) {
				flag = true;
			}
			resultJSONObject.put("success", flag);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return resultJSONObject;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "删除机构")
	@PostMapping(value = "/deleteUnitTree")
	public JSONObject deleteUser(String id) {
		JSONObject resultJSONObject = new JSONObject();
		try {
			Department department = new Department();

			department.setId(id);
			int resultTotal = deptService.deleteUnitTree(department);

			boolean flag = false;
			if (resultTotal > 0) {
				flag = true;
			}
			resultJSONObject.put("success", flag);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return resultJSONObject;
	}

}