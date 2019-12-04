package com.wjwy.rsda.controllers;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.wjwy.rsda.entity.User;
import com.wjwy.rsda.entity.resultEntity.ResultJson;
import com.wjwy.rsda.enums.EnumEntitys;
import com.wjwy.rsda.services.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author 张琪
 * @date 2019年11月20日
 * @Description
 */
@RestController
@Api(value = "用户中心", tags = "用户中心维护")
@EnableSwagger2
public class UserController {
	/**
	 * 用户中心业务
	 */
	@Autowired
	private UserService userService;
	
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
	public ModelAndView userInfo(User user,ModelAndView model) {
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
	public ResultJson findoneUser(@RequestBody User User, HttpServletRequest request, ResultJson resJsonModel) {

		try {
			boolean flag = false;
			if (User == null) {
				resJsonModel.msg = "加载失败";
			} else {
				flag = true;
				resJsonModel.msg = "加载成功";
				// 存放到Session
				HttpSession session = request.getSession();
				session.setAttribute("userOne", User);
			}
			resJsonModel.status = flag;
		} catch (Exception e) {
			resJsonModel.msg = "异常";
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
	public JSONObject userPageInfoList( User user, @RequestParam(value = "page") String pageNum,
			@RequestParam(value = "limit") String pageSize) {
		JSONObject json = new JSONObject();
		try {
			PageInfo<User> pageInfos = userService.getPageList(user, Integer.parseInt(pageNum.trim()),
					Integer.parseInt(pageSize.trim()));
			List<User> userList = pageInfos.getList();
			int count = (int) pageInfos.getTotal();
			json.put("data", userList);
			json.put("count", count);
			json.put("msg", "success");
			if (!userList.isEmpty()) {
				json.put("code", 200);
			}
		} catch (Exception e) {
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
	public JSONObject deleteUserAlls(String[] ids, HttpServletResponse response){
		int resultTotal = 0;
		JSONObject resultJSONObject = new JSONObject();
		try {
			User user = new User();
			for (String userId : ids) {
				user.setUserId(userId);
			    resultTotal = userService.delete(user);
			}
	
			boolean flag = false;
			if (resultTotal > 0) {
				flag = true;
			} 
			resultJSONObject.put("success", flag);
			response.setContentType("text/html;charset=utf-8");
		} catch (Exception e) {
			resultJSONObject.put("success", false);
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
	public JSONObject deleteUser(User User, HttpServletResponse response) {

		JSONObject resultJSONObject = new JSONObject();
		try {
			int resultTotal = userService.delete(User);
			boolean flag = false;
			if (resultTotal > 0) {
				flag = true;
			} 
			resultJSONObject.put("success", flag);
			response.setContentType("text/html;charset=utf-8");
		} catch (Exception e) {
			resultJSONObject.put("success", false);
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
	@ApiOperation(value = "更新用户", notes = "更新用户")
	@PostMapping("/toUpdateUser")
	public JSONObject toUpdateUser(User User, HttpServletResponse response) throws JSONException {
		
		JSONObject resultJSONObject = new JSONObject();
		try {
			int resultTotal = 0;// 操作的记录数
			if(StringUtils.isEmpty(User.getUserId()) ) {
				resultTotal = userService.insert(User);
			}else {
				resultTotal = userService.update(User);
			}
			boolean flag = false;
			if (resultTotal > 0) {
				flag = true;
			} 
			resultJSONObject.put("success", flag);
			response.setContentType("text/html;charset=utf-8");
		} catch (Exception e) {
			resultJSONObject.put("success", false);
		}
		return resultJSONObject;
	}
	


	/**
	 * @Description: 修改回显页面
	 * @param    
	 * @return 
	 * @return void  
	 * @throws
	 * @author ZHANGQI
	 * @date 2019年11月28日
	 */
	@ApiOperation(value = "跳转修改界面", notes = "参数：VO")
	@GetMapping(value = "/updateInfo")
	public ModelAndView updateInfo(User User,HttpServletRequest request, ModelAndView model,String type) {
		if(!EnumEntitys.SCUUESS.getValue().equals(type)) {
 			HttpSession session = request.getSession();
			// 获取登录的session存入的user对象
			User = (User) session.getAttribute("userOne");
		}
		model.addObject("userOne", User);
		model.setViewName("webview/user/user/userform");
		return model;
	}
	


	/**
	 * 
	 * @Title: hello1 @Description: TODO方法描述:(个人信息) @param @return 设定文件 @return
	 * String 返回类型 @throws
	 */
	@ApiOperation(value = "基本资料", notes = "参数：用户信息")
	@GetMapping("/personInfo")
	public ModelAndView PersonInfo(User User, ModelAndView model) {
		User = userService.getPersonInfo(User.getUserId());
		model.addObject("user", User);
		model.setViewName("webview/user/info");
		return model;
	}
}
