package com.wjwy.rsda.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.common.resultEntity.ResultJson;
import com.wjwy.rsda.entity.User;

import com.wjwy.rsda.enums.EnumEntitys;
import com.wjwy.rsda.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 张琪
 * @date 2019年11月20日
 * @Description
 */
@RequestMapping("/user")
@RestController
@Api(value = "用户中心", tags = "信息中心维护")


public class UserController {
	/**
	 * 用户中心业务
	 */
	@Autowired
	private UserService userService;
	public Logger logger = LoggerFactory.getLogger(DepartmentController.class);

	/**
	 * @Description: 网站用户界面
	 * @param
	 * @return
	 * @return void
	 * @throws @author ZHANGQI
	 * @date 2019年11月27日
	 */
	@ApiOperation(value = "用户列表 ")
	@GetMapping(value = "/userInfo")
	public ModelAndView userInfo(User user, ModelAndView model) {
		model.setViewName("webview/user/user/list");
		return model;
	}

	/**
	 * @Description: 根据ID查询单条数据
	 * @param
	 * @return
	 * @return void
	 * @throws @author ZHANGQI
	 * @date 2019年11月28日
	 */
	@ApiOperation(value = "根据用户ID查询单条数据", notes = "根据用户ID查询用户")
	@PostMapping("/findoneUser")
	public ResultJson findoneUser(@RequestBody User user, HttpServletRequest request) {
		ResultJson resJsonModel = new ResultJson();
		try {
			boolean flag = false;
			if (user == null) {
				resJsonModel.setMsg(EnumEntitys.FAILED.getDesc());
			} else {
				flag = true;

				// 存放到Session
				HttpSession session = request.getSession();
				session.setAttribute("userOne", user);
				resJsonModel.setData(user);
				resJsonModel.setMsg(EnumEntitys.SCUUESS.getDesc());
			}
			resJsonModel.status = flag;
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return resJsonModel;
	}

	/**
	 * 
	 * @Description: 网站用户
	 * @param @param  userId
	 * @param @param  pageNum
	 * @param @param  pageSize
	 * @param @return
	 * @return JSONObject
	 * @throws JSONException
	 * @throws @author       ZHANGQI
	 * @date 2019年11月27日
	 */

	@PostMapping("/userPageInfoList")
	@ApiOperation(value = "根据用户ID获取用户分页列表", notes = "根据用户ID获取用户分页分页列表")
	public JSONObject userPageInfoList(String userId, String userName, String deptId, String workDept, Boolean delFlag,
			Boolean isPage, String pageNum, String pageSize) {
		int count = 0;
		List<User> userList;
		JSONObject json = new JSONObject();
		try {
			if (isPage != null && isPage.equals(EnumEntitys.YES.getValue())) {
				PageInfo<User> pageInfos = userService.getPageList(userId, userName, deptId, workDept, delFlag,
						Integer.parseInt(pageNum.trim()), Integer.parseInt(pageSize.trim()));
				userList = pageInfos.getList();
			    count = (int) pageInfos.getTotal();
			} else {
				userList = userService.getList(userId, userName, deptId, workDept, delFlag);
				count = userList.size();
			}
			json.put("count", count);
			json.put("data", userList);
			json.put("msg", "success");
			if (!userList.isEmpty()) {
				json.put("code", 200);
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
			json.put(String.valueOf(EnumEntitys.RETURN_MSG.getValue()), "error");
			json.put(String.valueOf(EnumEntitys.RETURN_CODE.getValue()), "400");
		}
		return json;
	}

	/**
	 * @Description: 批量删除
	 * @param
	 * @return
	 * @return void
	 * @throws JSONException
	 * @throws @author       ZHANGQI
	 * @date 2019年11月29日
	 */
	@ResponseBody
	@ApiOperation(value = "根据用户ID批量删除用户", notes = "根据用户ID批量删除用户")
	@PostMapping("/deleteUserAlls")
	public JSONObject deleteUserAlls(String[] ids, HttpServletResponse response) {

		JSONObject resultJSONObject = new JSONObject();
		try {
			int resultTotal = 0;
			User user = new User();
			for (String userId : ids) {
				if (!userId.isEmpty()) {
					user.setUserId(userId);
					resultTotal = userService.delete(user);
				}
			}
			boolean flag = false;
			if (resultTotal > 0) {
				flag = true;
			}
			resultJSONObject.put("success", flag);
			response.setContentType("text/html;charset=utf-8");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}

		return resultJSONObject;
	}

	/**
	 * @Description: 根据用户ID删除用户
	 * @param
	 * @return
	 * @return void
	 * @throws JSONException
	 * @throws @author       ZHANGQI
	 * @date 2019年11月28日
	 */
	@ApiOperation(value = "根据用户ID删除用户", notes = "根据用户ID删除用户")
	@PostMapping("/deleteUser")
	public JSONObject deleteUser(String userId) {
		JSONObject resultJSONObject = new JSONObject();
		try {
			User user = new User();

			user.setUserId(userId);
			int resultTotal = userService.delete(user);

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
	 * @Description: 更新用户
	 * @param
	 * @return
	 * @return void
	 * @throws JSONException
	 * @throws @author       ZHANGQI
	 * @date 2019年11月28日
	 */
	@ApiOperation(value = "新增用户", notes = "新增用户")
	@PostMapping("/insertUser")
	public JSONObject insertUser(@RequestBody User User) {

		JSONObject resultJSONObject = new JSONObject();
		try {
			int resultTotal = 0;// 操作的记录数
			resultTotal = userService.insert(User);
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
	 * @Description: 更新用户
	 * @param
	 * @return
	 * @return void
	 * @throws JSONException
	 * @throws @author       ZHANGQI
	 * @date 2019年11月28日
	 */
	@ApiOperation(value = "更新", notes = "更新")
	@PostMapping("/updateUser")
	public JSONObject toUpdateUser(@RequestBody User User) {

		JSONObject resultJSONObject = new JSONObject();
		try {
			int resultTotal = 0;// 操作的记录数
			resultTotal = userService.update(User);
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
	 * @Description: 修改回显页面
	 * @param
	 * @return
	 * @return void
	 * @throws @author ZHANGQI
	 * @date 2019年11月28日
	 */
	@ApiOperation(value = "跳转修改界面", notes = "参数：VO")
	@GetMapping(value = "/updateInfo")
	public ModelAndView updateInfo(User user, HttpServletRequest request, ModelAndView model, String type) {

		HttpSession session = request.getSession();
		if (!EnumEntitys.SCUUESS.getValue().equals(type)) {
			// 获取登录的session存入的user对象
			user = (User) session.getAttribute("userOne");
		}

		model.addObject("userOne", user);
		model.setViewName("webview/user/user/userform");
		return model;
	}

	/**
	 * 
	 * @Title: hello1 @Description: TODO方法描述:(个人信息) @param @return 设定文件 @return
	 *         String 返回类型 @throws
	 */
	@ApiOperation(value = "基本资料", notes = "参数：用户信息")
	@GetMapping("/personInfo")
	public ModelAndView PersonInfo(String userId, ModelAndView model) {
		User user = userService.getPersonInfo(userId);
		model.addObject("user", user);
		model.setViewName("webview/user/info");
		return model;
	}


	@ApiOperation(value = "根据用户选择角色", notes = "参数：userId-用户主键,ids-角色数组")
	@GetMapping("/userSelRole")
	public  void  userSelRole(String userId,String ids[]){

		try {
			boolean flag = userService.userSelRole(userId, ids);
			if (flag) {
				
			}
		} catch (Exception e) {
			
		}
		

	}


}
