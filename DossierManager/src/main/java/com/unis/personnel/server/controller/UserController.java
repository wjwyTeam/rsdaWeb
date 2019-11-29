package com.unis.personnel.server.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.unis.common.utils.RSAUtils;
import com.unis.personnel.common.enums.EnumEntitys;
import com.unis.personnel.common.vo.UserQueryVO;
import com.unis.personnel.common.vo.UserVO;
import com.unis.personnel.server.entity.ResultJson;
import com.unis.personnel.server.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author 张琪
 * @date 2019年11月20日
 * @Description
 */
@RestController
@Slf4j
@Api(value = "用户中心", tags = "用户中心维护")
@EnableSwagger2
public class UserController {
	/**
	 * 用户中心业务
	 */
	@Autowired
	private UserService userService;

	/**
	 * 
	 * @Title: Login @Description: TODO方法描述:(登录首页) @param @param
	 * model @param @return 设定文件 @return ModelAndView 返回类型 @throws
	 */

	@ApiOperation(value = "登录首页")
	@GetMapping("/admin")
	public ModelAndView Login(ModelAndView model) {
		model.setViewName(EnumEntitys.LOGIN.getValue());
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
	public ResultJson findoneUser(@RequestBody UserVO userVO, HttpServletRequest request,ResultJson resJsonModel) {
		
		try {
			boolean flag = false;
			if(userVO == null) {
				resJsonModel.msg = "查询失败";
			}else {
				flag = true;
				resJsonModel.msg = "查询成功";
				 //存放到Session
			    HttpSession session= request.getSession();
			    session.setAttribute("userOne", userVO);
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
	 * @param @param userId
	 * @param @param pageNum
	 * @param @param pageSize
	 * @param @return
	 * @return JSONObject
	 * @throws @author ZHANGQI
	 * @date 2019年11月27日
	 */
	@PostMapping("/userPageInfoList")
	@ApiOperation(value = "根据用户ID获取用户分页列表",  notes = "根据用户ID获取用户分页分页列表")
	public JSONObject userPageInfoList(UserQueryVO userQueryVO, @RequestParam(value = "page") String pageNum,
			@RequestParam(value = "limit") String pageSize) {
		JSONObject json = new JSONObject();
		try {
			PageInfo<UserVO> pageInfos = userService.getPageList(userQueryVO, Integer.parseInt(pageNum.trim()),
					Integer.parseInt(pageSize.trim()));
			List<UserVO> userList = pageInfos.getList();
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
	 * @throws
	 * @author ZHANGQI
	 * @date 2019年11月29日
	 */
	@ResponseBody
	@ApiOperation(value = "根据用户ID批量删除用户", notes = "根据用户ID批量删除用户")
	@PostMapping("/deleteUserAlls")
	public JSONObject deleteUserAlls(String[] ids,HttpServletResponse response) {
		int resultTotal = 0;// 操作的记录数
		resultTotal = userService.deleteAll(ids);
		JSONObject resultJSONObject = new JSONObject();
		try {
			if (resultTotal > 0) {
				resultJSONObject.put("success", true);
			} else {
				resultJSONObject.put("success", false);
			}
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
	 * @throws @author ZHANGQI
	 * @date 2019年11月28日
	 */
	@ApiOperation(value = "根据用户ID删除用户", notes = "根据用户ID删除用户")
	@PostMapping("/deleteUser")
	public JSONObject deleteUser(UserVO userVO, HttpServletResponse response) {
		int resultTotal = 0;// 操作的记录数
		resultTotal = userService.delete(userVO);
		JSONObject resultJSONObject = new JSONObject();
		try {
			if (resultTotal > 0) {
				resultJSONObject.put("success", true);
			} else {
				resultJSONObject.put("success", false);
			}
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
	 * @throws @author ZHANGQI
	 * @date 2019年11月28日
	 */
	@ApiOperation(value = "更新用户", notes = "更新用户")
	@PostMapping("/toUpdateUser")
	public JSONObject toUpdateUser(UserVO userVO, HttpServletResponse response) {
		int resultTotal = 0;// 操作的记录数
		if(StringUtils.isEmpty(userVO.getUserId()) ) {
			resultTotal = userService.insert(userVO);
		}else {
			resultTotal = userService.update(userVO);
		}
		JSONObject resultJSONObject = new JSONObject();
		try {
			if (resultTotal > 0) {
				resultJSONObject.put("success", true);
			} else {
				resultJSONObject.put("success", false);
			}
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
	public ModelAndView updateInfo(UserVO userVO,HttpServletRequest request, ModelAndView model,String type) {
		if(!EnumEntitys.SCUUESS.getValue().equals(type)) {
 			HttpSession session = request.getSession();
			// 获取登录的session存入的user对象
			userVO = (UserVO) session.getAttribute("userOne");
		}
		model.addObject("userOne", userVO);
		model.setViewName("webview/user/user/userform");
		return model;
	}
	
	
	
	
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
	public ModelAndView userInfo(UserQueryVO userQueryVO,ModelAndView model) {
		model.setViewName("webview/user/user/list");
		return model;
	}

	/**
	 * 
	 * @Title: loginInfo @Description: TODO方法描述:(登录验证) @param @param
	 * userQueryVO @param @return 设定文件 @return ResponseWrapper 返回类型 @throws
	 */
	@ResponseBody
	@ApiOperation(value = "登录验证", notes = "参数：账号 , 密码")
	@PostMapping(value = "/Login")
	public ResultJson loginInfo(@RequestBody UserVO userVO) {

		ResultJson resJsonModel = new ResultJson();
		boolean flag = false;
		if (userVO != null) {
			// 从SecurityUtils里边创建一个 subject
			Subject subject = SecurityUtils.getSubject();
			// 在认证提交前准备 token（令牌）
			userVO.setPassWord(RSAUtils.decryptDataOnJava(userVO.getPassWord(), EnumEntitys.RSADECRYPTION.getValue())
					.toLowerCase());
			UsernamePasswordToken token = new UsernamePasswordToken(userVO.getUserName(), userVO.getPassWord());
			// 执行认证登录
			try {
				subject.login(token);
			} catch (UnknownAccountException uae) {
				resJsonModel.msg = "未知账户";
			} catch (IncorrectCredentialsException ice) {
				resJsonModel.msg = "密码不正确";
			} catch (LockedAccountException lae) {
				resJsonModel.msg = "账户已锁定";
			} catch (ExcessiveAttemptsException eae) {
				resJsonModel.msg = "用户名或密码错误次数过多";
			} catch (AuthenticationException ae) {
				resJsonModel.msg = "用户名或密码不正确！";
			}
			if (subject.isAuthenticated()) {
				flag = true;
				resJsonModel.msg = "登陆成功，即将跳转页面";
			} else {
				token.clear();
			}

		} else {
			resJsonModel.msg = "登陆信息不能为空!";
		}
		resJsonModel.status = flag;
		return resJsonModel;
	}

	/**
	 * 
	 * @Title: LoginHome @Description: TODO方法描述:(登陆成功) @param @return 设定文件 @return
	 * String 返回类型 @throws
	 */
	@ApiOperation(value = "登录成功", notes = "参数：用户信息")
	@GetMapping("/LoginHome")
	public ModelAndView LoginHome(HttpServletRequest request, ModelAndView model) {
		UserVO userVO = new UserVO();
		try {
			HttpSession session = request.getSession();
			// 获取登录的session存入的user对象
			userVO = (UserVO) session.getAttribute("user");
			if (userVO == null) {
				model.setViewName(EnumEntitys.LOGIN.getValue());
			} else {
				model.addObject("user", userVO);
				model.setViewName("webview/index");
			}
		} catch (Exception e) {
			Login(model);
		}
		return model;
	}

	/**
	 * 
	 * @Title: hello1 @Description: TODO方法描述:(个人信息) @param @return 设定文件 @return
	 * String 返回类型 @throws
	 */
	@ApiOperation(value = "基本资料", notes = "参数：用户信息")
	@GetMapping("/personInfo")
	public ModelAndView PersonInfo(UserVO userVO, ModelAndView model) {
		userVO = userService.getPersonInfo(userVO.getUserId());
		model.addObject("user", userVO);
		model.setViewName("webview/user/info");
		return model;
	}

	/**
	 * @Title: logout @Description: TODO方法描述:(这里用一句话描述这个方法的作用) @param 设定文件 @return
	 * void 返回类型 @throws
	 */
	/*
	 * private ModelAndView logout(HttpServletRequest request,HttpServletResponse
	 * response,Map<String,Object> map) { //1.从cookie 里查询 Cookie[] cookies =
	 * request.getCookies(); if (cookies != null && cookies.length > 0) {
	 * //2.清除redis for (Cookie cookie : cookies) { if
	 * (cookie.getName().contains("JSESSION")) { System.out.println(cookie.getName()
	 * + "=" + cookie.getValue()); } } } return null;
	 * 
	 * }
	 */

	/**
	 * 
	 * @Title: test SpringBoot利用Redis管理分布式Session
	 * https://www.jianshu.com/p/fe9a6c3bda4e @Description:
	 * TODO方法描述:(set用于向session添加属性，get用于从session获取属性) @param @param request
	 * 设定文件 @return void 返回类型 @throws
	 */
	@GetMapping("/set")
	public void test(HttpServletRequest request) {
		request.getSession().setAttribute("message", request.getQueryString());
	}

	@GetMapping("/get")
	public Map<String, Object> two(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		map.put("sessionId", request.getSession().getId());
		map.put("message", request.getSession().getAttribute("message"));
		return map;
	}

	// 从request中读出cookies集合，然后封装成map，为的是能够直接通过name得到相应的cookie即get方法
	public static Cookie get(HttpServletRequest request, String name) {
		Map<String, Cookie> cookieMap = readCookieMap(request);
		if (cookieMap.containsKey(name)) {
			return cookieMap.get(name);
		} else {
			return null;
		}
	}

	/**
	 * 将cookie封装成Map
	 * 
	 * @param request
	 * @return
	 */
	private static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<>();
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}
}
