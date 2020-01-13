
package com.wjwy.rsda.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wjwy.rsda.entity.Online;
import com.wjwy.rsda.entity.User;
import io.swagger.annotations.Api;
import springfox.documentation.annotations.ApiIgnore;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpStatus;
import com.wjwy.rsda.services.FunctionService;
import com.wjwy.rsda.services.OnlineService;

import java.util.Collection;

import com.wjwy.rsda.common.tool.session.OnlineSession;
import com.wjwy.rsda.common.tool.session.OnlineSessionDAO;
import com.wjwy.rsda.common.util.ResponseWrapper;
import com.wjwy.rsda.common.util.ShiroUtils;

import org.springframework.web.servlet.ModelAndView;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;

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
public class LoginController{
	public Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private FunctionService functionService;
	@Autowired
	private OnlineService userOnlineService;
	@Autowired
	private OnlineSessionDAO onlineSessionDAO;

	/**
	 * 
	 * @Title: Login
	 * @Description: TODO方法描述:(登录页面)
	 * @return string 返回类型--文件名login页面
	 */

	@GetMapping("/login")
	public ModelAndView Login(@ApiIgnore ModelAndView model) {

		Subject subject = SecurityUtils.getSubject();
		if (subject.getPrincipal() != null) {
			User userobj = subject.getPrincipals().oneByType(User.class);
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
	public ModelAndView Logout(@ApiIgnore ModelAndView model) {
		ShiroUtils.clearCachedAuthorizationInfo();
		model.setViewName("login");// 返回到登录界面
		return model;
	}

	/**
	 * @author zgr @Title: Logout
	 * @Description: TODO方法描述:(退出登录跳转到登录页面，并清除对应用户session内缓存)
	 * @return string 返回类型--文件名login页面
	 */
	@GetMapping("/index")
	public ModelAndView Index(@ApiIgnore ModelAndView model) {
		Subject subject = SecurityUtils.getSubject();
		User userobj = subject.getPrincipals().oneByType(User.class);
		if (userobj != null) {
			model.addObject("user", userobj);// session 内用户信息提供给首页用以用户显示
			// 展示菜单列表(首页)
			model.addObject("manuList", functionService.getList());
			// model.addObject("manuList", functionService.findUserList(userobj.getUserId()));
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
		int n = 0;
		try {
			UsernamePasswordToken token = new UsernamePasswordToken(loginName, passwordMd5);
			Subject subject = SecurityUtils.getSubject();
			subject.login(token);

			Online on = userOnlineService.getOnline(loginName);
			if (on != null) {
				// 以下为只允许同一账户单个登录
				Collection<Session> sessions = onlineSessionDAO.getActiveSessions();
				if (sessions.size() > 0) {
					for (Session session : sessions) {
						OnlineSession onlineSession = (OnlineSession) session;
						// 获得session中已经登录用户的名字
						if (null != onlineSession.getLoginName()) {
							if (loginName.equals(onlineSession.getLoginName())) { // 这里的username也就是当前登录的username
								session.setTimeout(0); // 这里就把session清除，
								userOnlineService.deleteOnlineById(on.getSessionId());
							 n = userOnlineService.getId(onlineSession.getId()).size();
								logger.info(
										"[ IP:" + onlineSession.getHost() + "《=================》用户" + onlineSession.getLoginName() + "] 已下线...");
							}
						}
					}
				}
			}

			return ResponseWrapper.success(HttpStatus.OK.value(), "登陆中,请稍候...", String.valueOf(n), "/index", null);
		} catch (AuthenticationException e) {
			return ResponseWrapper.success(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null, "/login", null);
		}
	}

	@GetMapping("/")
	public ModelAndView getErrorPath(@ApiIgnore ModelAndView model) {
		model.setViewName("redirect:/login");// 设置返回界面为首页loginSession
		return model;
	}
}
