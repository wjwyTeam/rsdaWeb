package com.wjwy.rsda.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.common.util.MD5Util;
import com.wjwy.rsda.common.util.ResponseWrapper;
import com.wjwy.rsda.entity.Role;
import com.wjwy.rsda.entity.User;
import com.wjwy.rsda.enums.EnumEntitys;
import com.wjwy.rsda.services.RoleService;
import com.wjwy.rsda.services.UserService;
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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 张琪
 * @date 2019年11月20日
 * @Description
 */
@RequestMapping("/user")
@RestController
@Api(value = "用户中心", tags = "用户信息库中心")

public class UserController {
	/**
	 * 用户中心业务
	 */
	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	public Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private HttpServletRequest request;
	private String prefix = "webview/system/user/";

	/**
	 * @Description: 网站用户界面
	 * @param
	 * @return
	 * @return void
	 * @throws @author ZHANGQI
	 * @date 2019年11月27日
	 */

	@GetMapping(value = "/userInfo")
	public ModelAndView userInfo(User user, ModelAndView model) {
		model.addObject("type", true);
		if (StringUtil.isNotEmpty(request.getParameter("type"))) {
			model.addObject("type", request.getParameter("type"));
			model.addObject("roleId", request.getParameter("roleId"));
		}
		model.setViewName(prefix + "userList");
		return model;
	}

	/**
	 * 跳转用户授权界面
	 * 
	 * @param user
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/userRole")
	@ApiOperation(value = "跳转用户授权界面")
	public ModelAndView userRole(ModelAndView model) {

		model.addObject("roleList", roleService.getRoleList());
		List<Role> roleList = roleService.getUserRoleList(request.getParameter("userId"));
		model.addObject("roleUserList", roleList);

		model.setViewName(prefix + "userRole");
		return model;
	}

	/**
	 * 
	 * @Title: hello1 @Description: TODO方法描述:(个人信息) @param @return 设定文件 @return
	 *         String 返回类型 @throws
	 */
	@GetMapping("/personInfo")
	public ModelAndView PersonInfo(String userId, ModelAndView model) {
		User user = userService.getPersonInfo(userId);
		model.addObject("user", user);
		model.setViewName(prefix + "userInfo");
		return model;
	}

	/**
	 * @Description: 修改回显页面
	 * @param
	 * @return
	 * @return void
	 * @throws @author ZHANGQI
	 * @date 2019年11月28日
	 */
	@GetMapping(value = "/updateInfo")
	public ModelAndView updateInfo(User user, ModelAndView model) {
		if (StringUtils.isNotEmpty(user.getUserId())) {
			user = userService.getPersonInfo(user.getUserId());
			user.setPassWord(MD5Util.convertMD5(MD5Util.convertMD5(user.getPassWord())));
		}
		model.addObject("userOne", user);
		model.setViewName(prefix + "userForm");
		return model;
	}

	/**
	 * 根据ID获取用户分页列表
	 * 
	 * @param userId
	 * @param userName
	 * @param deptId
	 * @param workDept
	 * @param delFlag
	 * @param isPage
	 * @param pageNum
	 * @param pageSize
	 * @return ResponseWrapper
	 */

	@PostMapping("/userPageInfoList")
	@ApiOperation(value = "根据ID获取用户分页列表", notes = "参数：userId-用户ID,userName-用户名,deptId-部门ID,workDept-部门工号,delFlag-删除标志,isPage-是否开启分页,pageNum-当前页,pageSize-每页条数")
	public ResponseWrapper userPageInfoList(String userId, String userName, String deptId, String workDept,
			Boolean delFlag, Boolean isPage, String pageNum, String pageSize,String roleId) {
		try {
			if (isPage != null && isPage.equals(EnumEntitys.YES.getValue())) {
				PageInfo<User> pageInfos = userService.getPageList(userId, userName, deptId, workDept, delFlag,
						Integer.parseInt(pageNum.trim()), Integer.parseInt(pageSize.trim()),roleId);
				return ResponseWrapper.success(HttpStatus.OK.value(), "获取成功", pageInfos, null,
						Integer.parseInt(String.valueOf(pageInfos.getTotal())));
			} else {
				List<User> userList = userService.getList(userId, userName, deptId, workDept, delFlag,roleId);
				return ResponseWrapper.success(HttpStatus.OK.value(), "获取成功", userList, null, userList.size());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return ResponseWrapper.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "服务错误，请联系管理员");
	}

	/**
	 * 根据用户ID批量删除用户
	 * 
	 * @param ids
	 * @return ResponseWrapper
	 */
	@ResponseBody
	@ApiOperation(value = "根据ID批量删除用户", notes = "参数:ids")
	@PostMapping("/deleteUserAlls")
	public ResponseWrapper deleteUserAlls(String[] ids) {
		ResponseWrapper rmAll = null;
		for (String userId : ids) {
			if (!userId.isEmpty()) {
				rmAll = deleteUser(userId);
			}
		}
		return rmAll;
	}

	/**
	 * 根据ID删除用户
	 * 
	 * @param userId
	 * @return ResponseWrapper
	 */
	@ApiOperation(value = "根据ID删除用户", notes = "参数:userId")
	@PostMapping("/deleteUser")
	public ResponseWrapper deleteUser(String userId) {
		try {
			User user = new User();
			user.setUserId(userId);
			int resultTotal = userService.delete(user);
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
	 * 新增用户
	 * 
	 * @param User
	 * @return ResponseWrapper
	 */
	@ApiOperation(value = "新增用户", notes = "新增用户")
	@PostMapping("/insertUser")
	public ResponseWrapper insertUser(@RequestBody User user) {
		try {
			int resultTotal = userService.insert(user);
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
	 * 更新
	 * 
	 * @param User
	 * @return ResponseWrapper
	 */
	@ApiOperation(value = "更新", notes = "更新")
	@PostMapping("/updateUser")
	public ResponseWrapper toUpdateUser(@RequestBody User User) {
		try {
			int resultTotal = userService.update(User);
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
	 * 根据用户选择角色
	 * 
	 * @param userId
	 * @param ids
	 * @return ResponseWrapper
	 */
	@ApiOperation(value = "根据用户选择角色(授权)", notes = "参数：userId-用户主键,ids-角色数组")
	@GetMapping("/userSelRole")
	public ResponseWrapper userSelRole(String userId, String ids[]) {

		try {
			boolean flag = userService.userSelRole(userId, ids);
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

	
 /**
		* 
		* @param userId
		* @param ids
		* @return
	 */
	@ApiOperation(value = "取消授权", notes = "参数：userId-用户主键,roleId-角色")
	@GetMapping("/userDelRole")
	public ResponseWrapper userDelRole(String userId, String roleId) {

		try {
			boolean flag = userService.userDelRole(userId, roleId);
			if (!flag) {
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
