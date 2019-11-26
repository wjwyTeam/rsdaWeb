package com.unis.personnel.server.controller;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.unis.common.utils.CookieUtil;
import com.unis.common.utils.MD5Util;
import com.unis.common.utils.RSAUtils;
import com.unis.common.utils.ResponseWrapper;
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
	private  UserService userService;
	
	/**
	 * 
	 * @Title: Login
	 * @Description: TODO方法描述:(登录首页)
	 * @param @param model
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
//	@RequiresPermissions("user:login")
	@ApiOperation(value = "登录首页")
	@GetMapping("/admin")
    public ModelAndView Login(ModelAndView model) {
		model.setViewName(EnumEntitys.LOGIN.getValue());
        return model;
    }
	/**
	  * 根据条件分页查询列表
	 * @param userQueryVO
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@ApiOperation(value = "用户列表 根据条件【分页查询】",notes = "参数：姓名")
	@PostMapping(value = "/userPageList/{pageNum}/{pageSize}")
	public ResponseWrapper getUserPageList(UserQueryVO userQueryVO,
			   @PathVariable(value = "pageNum") Integer pageNum,
			   @PathVariable(value = "pageSize") Integer pageSize){
		try {
			PageInfo<UserVO> pageInfos = userService.getPageList(userQueryVO,pageNum,pageSize);
			return ResponseWrapper.success(HttpStatus.OK.value(), "获取信息列表成功", pageInfos);
		} catch (Exception e) {
			ResponseWrapper log;
//			log.error(e.getMessage());
			return ResponseWrapper.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),"获取信息列表失败",null);
		}
	}
	
	/**
	 * 
	 * @Title: loginInfo
	 * @Description: TODO方法描述:(登录验证)
	 * @param @param userQueryVO
	 * @param @return    设定文件
	 * @return ResponseWrapper    返回类型
	 * @throws
	 */
	@ResponseBody
	@ApiOperation(value = "登录验证",notes = "参数：账号 , 密码")
	@PostMapping(value = "/Login")
	public ResultJson loginInfo(@RequestBody UserVO userVO, HttpServletRequest request){
		
		
		
			ResultJson resJsonModel = new ResultJson();
			boolean flag = false;
	    	if(userVO != null ){
	    		  // 从SecurityUtils里边创建一个 subject
	            Subject subject = SecurityUtils.getSubject();
	            // 在认证提交前准备 token（令牌）
	    		userVO.setPassWord(RSAUtils.decryptDataOnJava(userVO.getPassWord(), EnumEntitys.RSADECRYPTION.getValue()).toLowerCase());
	            UsernamePasswordToken token = new UsernamePasswordToken(userVO.getUserName(), userVO.getPassWord());
	            //执行认证登录
	            try {
	                subject.login(token);
	            } catch (UnknownAccountException uae) {
	            	resJsonModel.msg= "未知账户";
	            } catch (IncorrectCredentialsException ice) {
	            	resJsonModel.msg= "密码不正确";
	            } catch (LockedAccountException lae) {
	            	resJsonModel.msg= "账户已锁定";
	            } catch (ExcessiveAttemptsException eae) {
	            	resJsonModel.msg= "用户名或密码错误次数过多";
	            } catch (AuthenticationException ae) {
	            	resJsonModel.msg= "用户名或密码不正确！";
	            }
	            
	            if (subject.isAuthenticated()) {
	            	flag = true;
	            	resJsonModel.msg= "登陆成功，即将跳转页面";
	            } else {
	                token.clear();
	                resJsonModel.msg= "登录失败";
	            }
	            
	    	}else {
	    		resJsonModel.msg="登陆信息不能为空!";
	    	}
	    	
	    	resJsonModel.status=flag;
	    	
	    	return resJsonModel;
	}
	
	/**
	 * 
	 * @Title: LoginHome
	 * @Description: TODO方法描述:(登陆成功)
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	 */
	@ApiOperation(value = "登录成功",notes = "参数：用户信息")
	@GetMapping("/LoginHome")
    public ModelAndView LoginHome(HttpServletRequest request,ModelAndView model) {
		 UserVO userVO =new UserVO();
		try {
		   
			HttpSession session = request.getSession();
			//获取登录的session存入的user对象
			userVO = (UserVO)session.getAttribute("user");
			if(userVO == null) {
				model.setViewName(EnumEntitys.LOGIN.getValue());
			}else {
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
      * @Title: hello1
      * @Description: TODO方法描述:(个人信息)
      * @param @return    设定文件
      * @return String    返回类型
      * @throws
      */
	 @ApiOperation(value = "基本资料",notes = "参数：用户信息")
	 @GetMapping("/personInfo")
	  public ModelAndView PersonInfo(UserVO userVO,ModelAndView model) {
		 	userVO = userService.getPersonInfo(userVO.getUserId());
			model.addObject("user", userVO);
			model.setViewName("webview/user/info");
	    return model;
	  }

	 /**
	 * @Title: logout
	 * @Description: TODO方法描述:(这里用一句话描述这个方法的作用)
	 * @param     设定文件
	 * @return void    返回类型
	 * @throws
	*/
	private ModelAndView logout(HttpServletRequest request,HttpServletResponse response,Map<String,Object> map) {
		//1.从cookie 里查询
	    Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
        	//2.清除redis
    /*        for (Cookie cookie : cookies) {
                if (cookie.getName().contains("JSESSION")) {
	                    System.out.println(cookie.getName() + "=" + cookie.getValue());
                }
            }*/
        }
		return null;

	}
	 
	 
	 /**
	  * 
	  * @Title: test SpringBoot利用Redis管理分布式Session https://www.jianshu.com/p/fe9a6c3bda4e
	  * @Description: TODO方法描述:(set用于向session添加属性，get用于从session获取属性)
	  * @param @param request    设定文件
	  * @return void    返回类型
	  * @throws
	  */
	 	@GetMapping("/set")
	    public void test(HttpServletRequest request){
	        request.getSession().setAttribute("message",request.getQueryString());
	    }
	 
	    @GetMapping("/get")
	    public Map<String,Object> two(HttpServletRequest request){
	        Map<String,Object> map = new HashMap<>();
	        map.put("sessionId",request.getSession().getId());
	        map.put("message",request.getSession().getAttribute("message"));
	        return map;
	    }
	 
	    //从request中读出cookies集合，然后封装成map，为的是能够直接通过name得到相应的cookie即get方法
	    public static Cookie get(HttpServletRequest request, String name) {
			Map<String, Cookie> cookieMap = readCookieMap(request);
				if (cookieMap.containsKey(name)) {
				return cookieMap.get(name);
			}else {
				return null;
			}
		}

	    /**
	     * 将cookie封装成Map
	     * @param request
	     * @return
	     */
	    private static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
	        Map<String, Cookie> cookieMap = new HashMap<>();
	        Cookie[] cookies = request.getCookies();
	        if (cookies != null) {
	            for (Cookie cookie: cookies) {
	                cookieMap.put(cookie.getName(), cookie);
	            }
	        }
	        return cookieMap;
	    }
}
