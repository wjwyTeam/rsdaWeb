
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
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
	private FunctionService functionService;
	@Autowired
	private OnlineService userOnlineService;
	@Autowired
	private OnlineSessionDAO onlineSessionDAO;
	@Autowired
	private SocketServer socketServer;

	// 跳转界面前缀
	private String prefix = "/webview/system/server";
	
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
			model.addObject("userName", userobj.getUserName());// session 内用户信息提供给首页用以用户显示
			// 展示菜单列表(首页)
			// model.addObject("manuList", functionService.getList());
			model.addObject("manuList", functionService.findUserList(userobj.getUserId()));
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
								sendmsg("[ IP:" + onlineSession.getHost() + ",因异地登录，您已强制下线!]", onlineSession.getLoginName());
								logger.info(
										"[ IP:" + onlineSession.getHost() + "《=================》用户" + onlineSession.getLoginName() + "] 已下线...");
							}
						}
					}
				}
			}

			return ResponseWrapper.success(HttpStatus.OK.value(), "登陆中,请稍候...", null, "/index", null);
		} catch (AuthenticationException e) {
			return ResponseWrapper.success(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null, "/login", null);
		}
	}

	@GetMapping("/")
	public ModelAndView getErrorPath(@ApiIgnore ModelAndView model) {
		model.setViewName("redirect:/login");// 设置返回界面为首页loginSession
		return model;
	}

	/**
	 *
	 * 客户端页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/client")
	public ModelAndView idnex(@ApiIgnore ModelAndView model) {
		model.setViewName(prefix + "/client");// 设置返回界面为首页
			Subject subject = SecurityUtils.getSubject();
		User userobj = subject.getPrincipals().oneByType(User.class);
		if (userobj != null) {
			model.addObject("userName", userobj.getUserName());// session 内用户信息提供给首页用以用户显示
		}
		return model;
	}

	/**
	 *
	 * 服务端页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/server")
	public ModelAndView admin(@ApiIgnore ModelAndView model) {
		int num = socketServer.getOnlineNum();
		List<String> list = socketServer.getOnlineUsers();

		model.addObject("num", num);
		model.addObject("users", list);
		model.setViewName(prefix +"/server");// 设置返回界面为首页
		return model;
	}

	/**
	 * 个人信息推送
	 * 
	 * @return
	 */
	@RequestMapping("sendmsg")
	@ResponseBody
	public String sendmsg(String msg, String username) {
		// 第一个参数 :msg 发送的信息内容
		// 第二个参数为用户长连接传的用户人数
		String[] persons = username.split(",");
		SocketServer.SendMany(msg, persons);
		return "success";
	}

	/**
	 * 推送给所有在线用户
	 * 
	 * @return
	 */
	@RequestMapping("sendAll")
	@ResponseBody
	public String sendAll(String msg) {
		SocketServer.sendAll(msg);
		return "success";
	}
}
