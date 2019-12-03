/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: zgr
 * @Date: 2019-11-30 18:24:05
 * @LastEditors: zgr
 * @LastEditTime: 2019-12-01 20:46:08
 */
package com.wjwy.rsda.controllers;

import com.wjwy.rsda.entity.User;
import com.wjwy.rsda.entity.resultEntity.SimpleResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 张广睿
 * @Description: 登录相关mvc及相关util方法
 * @date 2019年11月30日
 * @Description
 */
@RestController
public class LoginController {
	private SimpleResult _simpleResult;
	LoginController(){
		this._simpleResult = new SimpleResult();
	}
	/**
	 * 
	 * @Title: Login
	 * @Description: TODO方法描述:(登录页面)
	 * @return string 返回类型--文件名login页面
	 */
	@GetMapping("/login")
	public ModelAndView Login(ModelAndView model, HttpServletRequest request) {
		HttpSession session = request.getSession(false);// 获取库内session
		if (session != null) {//判断session区分是否登录
			Object userobj = session.getAttribute("userInfo");// 若获取到session内容，说明此端已经登录，跳转到index页面
			if(userobj != null){
				model.setViewName("redirect:/index");//设置返回界面为首页
				return model;
			}
		}		
		model.setViewName("login");
		return model;
	}

	/**
	 * @author zgr @Title: Logout 
	 * @Description:
	 *         TODO方法描述:(退出登录跳转到登录页面，并清除对应用户session内缓存) 
	 * @return string
	 *         返回类型--文件名login页面
	 */
	@GetMapping("/logout")
	public ModelAndView Logout(ModelAndView model, HttpServletRequest request) {
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
	 * @Description:
	 *         TODO方法描述:(退出登录跳转到登录页面，并清除对应用户session内缓存) 
	 * @return string
	 *         返回类型--文件名login页面
	 */
	@GetMapping("/index")
	public ModelAndView Index(ModelAndView model, HttpServletRequest request) {
		HttpSession session = request.getSession(false);// 获取库内session
		if (session != null) {
			User userobj = (User)session.getAttribute("userInfo");// 若获取到session内容，说明此端已经登录，跳转到index页面
			if(userobj != null){
				model.addObject("user", userobj);//session 内用户信息提供给首页用以用户显示
				model.setViewName("webview/index");//设置返回界面为首页
				return model;
			}
		}
		SecurityUtils.getSubject().logout();
		model.setViewName("redirect:/login");//设置返回界面为首页		
		return model;
	}
	@PostMapping("/login")
	public Object UserLogin(String loginName,String passwordMd5, HttpServletRequest request){
		//jdbc案例
		// JdbcTemplate  _jdbc = new JdbcTemplate();
		// User u =_jdbc.queryForObject("select top 1 * from da_user", new Object[]{}, new BeanPropertyRowMapper<>(User.class));
		// String a = u.getUsername();
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(loginName, passwordMd5);
		try {
			subject.login(token);
			User user =subject.getPrincipals().oneByType(User.class);
			HttpSession session = request.getSession(true);// 获取库内session
			session.setAttribute("userInfo", user);
			_simpleResult.setLoginType(1);
			_simpleResult.setMsg("登录成功，即将跳转页面");
			_simpleResult.setIndexUrl("/index");
		} catch (UnknownAccountException uae) {
			_simpleResult.setLoginType(0);
			_simpleResult.setMsg("登录失败，密码错误或用户名不存在");
			_simpleResult.setIndexUrl("/login");
		} 	
		return _simpleResult;
	}

	// /**
	//  * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址, 参考文章：
	//  * http://developer.51cto.com/art/201111/305181.htm
	//  * 
	//  * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
	//  * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
	//  * 
	//  * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130, 192.168.1.100
	//  * 
	//  * 用户真实IP为： 192.168.1.110
	//  * 
	//  * @param request
	//  * @return   用户真实ip
	//  */
	// public static String getIpAddress(HttpServletRequest request) {
	// 	String ip = request.getHeader("x-forwarded-for");
	// 	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	// 		ip = request.getHeader("Proxy-Client-IP");
	// 	}
	// 	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	// 		ip = request.getHeader("WL-Proxy-Client-IP");
	// 	}
	// 	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	// 		ip = request.getHeader("HTTP_CLIENT_IP");
	// 	}
	// 	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	// 		ip = request.getHeader("HTTP_X_FORWARDED_FOR");
	// 	}
	// 	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	// 		ip = request.getRemoteAddr();
	// 	}
	// 	return ip;
	// }
}
