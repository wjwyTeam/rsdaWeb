
package com.wjwy.rsda.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.wjwy.rsda.entity.User;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import javax.servlet.http.HttpSession;
import org.apache.shiro.subject.Subject;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import javax.servlet.http.HttpServletRequest;
import com.wjwy.rsda.common.util.ResponseWrapper;
import org.springframework.web.servlet.ModelAndView;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/*
 * @Descripttion: 登录配置
 * @version: 1.0
 * @Author: ZHANGQI
 * @Date: 2019-12-16 09:05:48
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-08 09:13:02
 */

@Api(value = "登录配置", tags = "WJ-维佳科技注册中心")
@RestController
public class LoginController {
	public Logger logger = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	private HttpServletRequest request;

	/**
	 * 
	 * @Title: Login
	 * @Description: TODO方法描述:(登录页面)
	 * @return string 返回类型--文件名login页面
	 */
	@GetMapping("/login")
	public ModelAndView Login(ModelAndView model) {
		HttpSession session = request.getSession(false);// 获取库内session
		if (session != null) {// 判断session区分是否登录
			Object userobj = session.getAttribute("userInfo");// 若获取到session内容，说明此端已经登录，跳转到index页面
			if (userobj != null) {
				model.setViewName("redirect:/index");// 设置返回界面为首页
				return model;
			}
		}
		model.setViewName("login");
		return model;
	}

	/**
	 * @author zgr @Title: Logout
	 * @Description: TODO方法描述:(退出登录跳转到登录页面，并清除对应用户session内缓存)
	 * @return string 返回类型--文件名login页面
	 */
	@GetMapping("/logout")
	public ModelAndView Logout(ModelAndView model) {
		HttpSession session = request.getSession(false);// 获取库内session
		if (session != null) {
			session.removeAttribute("userInfo");// 剔除对应缓存

		}
		SecurityUtils.getSubject().logout();
		model.setViewName("login");// 返回到登录界面
		return model;
	}

	/**
	 * @author zgr @Title: Logout
	 * @Description: TODO方法描述:(退出登录跳转到登录页面，并清除对应用户session内缓存)
	 * @return string 返回类型--文件名login页面
	 */
	@GetMapping("/index")
	public ModelAndView Index(ModelAndView model) {

		Subject subject = SecurityUtils.getSubject();
		User userobj = subject.getPrincipals().oneByType(User.class);
			if (userobj != null) {
				model.addObject("user", userobj);// session 内用户信息提供给首页用以用户显示
				model.setViewName("webview/index");// 设置返回界面为首页
				return model;
			}
	
		subject.logout();
		model.setViewName("redirect:/login");// 设置返回界面为首页
		return model;
	}

	/**
	 * 
	 * @param loginName
	 * @param passwordMd5
	 * @return ResponseWrapper
	 */
	@PostMapping("/login")
	public ResponseWrapper UserLogin(String loginName, String passwordMd5) {
		try {
			UsernamePasswordToken token = new UsernamePasswordToken(loginName, passwordMd5);
			Subject subject = SecurityUtils.getSubject();
			subject.login(token);
			return ResponseWrapper.success(HttpStatus.OK.value(), "登陆中,请稍候...", null, "/index", null);
		} catch (AuthenticationException e) {
			return ResponseWrapper.success(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null, "/login", null);
		}
	}

	/**
	 * 
	 * @return
	 */
	@ApiOperation(value = "/unauth")
	@GetMapping("/unauth")
	public String unauth() {
		return "error/unauth";
	}
}
