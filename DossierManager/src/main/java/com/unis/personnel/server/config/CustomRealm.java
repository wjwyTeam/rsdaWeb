package com.unis.personnel.server.config;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.unis.common.utils.MD5Util;
import com.unis.personnel.common.vo.UserVO;
import com.unis.personnel.server.service.UserService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @ClassName: CustomRealm
 * @Description: TODO功能描述:将 customRealm的实例化交给spring去管理，当然这里也可以利用注解的方式去注入
 *               身份认证realm; (这个需要自己写，账号密码校验；权限等)
 * @author ZHANGQI
 * @date 2019年11月25日 上午8:14:29
 * @remark 备注:
 */
@Slf4j
@Api(value = "CustomRealm", tags = "CustomRealm  身份认证")
public class CustomRealm extends AuthorizingRealm {
	/**
	 * 用户中心业务
	 */
	@Autowired
	private UserService userService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		System.out.println(username);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		Set<String> stringSet = new HashSet<>();
		stringSet.add("user:show");
		stringSet.add("user:admin");
		info.setStringPermissions(stringSet);
		return info;
	}

	/**
	 * 这里可以注入userService,为了方便演示 private UserService userService;
	 * <p>
	 * 获取即将需要认证的信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		UserVO userVO = new UserVO();
		// 根据用户名从数据库获取密码
		userVO.setPassWord(MD5Util.md5ToHex(new String((char[]) authenticationToken.getCredentials())));
		userVO.setUserName((String) authenticationToken.getPrincipal());
		UserVO user = userService.checkName(userVO);

		String passWord = "";
		String userName = "";

		if (user != null) {
			passWord = user.getPassWord();
			userName = user.getUserName();
		}
		try {
			if (userVO.getUserName() == null) {
				throw new AccountException("用户名不正确");
			} else if (user == null || !userVO.getPassWord().equals(user.getPassWord())) {
				throw new AccountException("密码不正确");
			} else {
				// 获取HttpServletRequest
				HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
						.getRequest();
				// 存放到Session
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				// 以秒为单位，即在没有活动30分钟后，session将失效
//		    	    session.setMaxInactiveInterval(30*60);
			}
		} catch (Exception e) {
			System.out.println("-------捕获异常--------");
		}

		return new SimpleAuthenticationInfo(userName, passWord, getName());
	}

	/**
	 * 授权
	 */
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(
//            PrincipalCollection principals) {
//        System.out.println("权限认证方法：MyShiroRealm.doGetAuthenticationInfo()");
//        SysUser token = (SysUser)SecurityUtils.getSubject().getPrincipal();
//        String userId = token.getId();
//        SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
//        //根据用户ID查询角色（role），放入到Authorization里。
//        /*Map<String, Object> map = new HashMap<String, Object>();
//        map.put("user_id", userId);
//        List<SysRole> roleList = sysRoleService.selectByMap(map);
//        Set<String> roleSet = new HashSet<String>();
//        for(SysRole role : roleList){
//            roleSet.add(role.getType());
//        }*/
//        //实际开发，当前登录用户的角色和权限信息是从数据库来获取的，我这里写死是为了方便测试
//        Set<String> roleSet = new HashSet<String>();
//        roleSet.add("100002");
//        info.setRoles(roleSet);
//        //根据用户ID查询权限（permission），放入到Authorization里。
//        /*List<SysPermission> permissionList = sysPermissionService.selectByMap(map);
//        Set<String> permissionSet = new HashSet<String>();
//        for(SysPermission Permission : permissionList){
//            permissionSet.add(Permission.getName());
//        }*/
//        Set<String> permissionSet = new HashSet<String>();
//        permissionSet.add("权限添加");
//        info.setStringPermissions(permissionSet);
//           return info;
//    }
}
/*
 * package com.unis.personnel.server.config; import
 * org.apache.shiro.SecurityUtils; import org.apache.shiro.authc.*; import
 * org.apache.shiro.authz.AuthorizationInfo; import
 * org.apache.shiro.authz.SimpleAuthorizationInfo; import
 * org.apache.shiro.realm.AuthorizingRealm; import
 * org.apache.shiro.subject.PrincipalCollection; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.web.context.request.RequestContextHolder; import
 * org.springframework.web.context.request.ServletRequestAttributes;
 * 
 * import com.unis.common.utils.MD5Util; import
 * com.unis.personnel.common.vo.UserVO; import
 * com.unis.personnel.server.entity.Permissions; import
 * com.unis.personnel.server.entity.RoleDO; import
 * com.unis.personnel.server.service.UserService;
 * 
 * import io.swagger.annotations.Api; import lombok.extern.slf4j.Slf4j;
 * 
 * import java.util.HashSet; import java.util.Set;
 * 
 * import javax.servlet.http.HttpServletRequest; import
 * javax.servlet.http.HttpSession;
 * 
 *//**
	 * @ClassName: CustomRealm
	 * @Description: TODO功能描述:将 customRealm的实例化交给spring去管理，当然这里也可以利用注解的方式去注入
	 *               身份认证realm; (这个需要自己写，账号密码校验；权限等)
	 * @author ZHANGQI
	 * @date 2019年11月25日 上午8:14:29
	 * @remark 备注:
	 */
/*
 * @Slf4j
 * 
 * @Api(value = "CustomRealm", tags = "CustomRealm  身份认证") public class
 * CustomRealm extends AuthorizingRealm {
 *//**
	 * 用户中心业务
	 */
/*
 * @Autowired private UserService userService;
 * 
 * @Override protected AuthorizationInfo
 * doGetAuthorizationInfo(PrincipalCollection principalCollection) { //获取登录用户名
 * String userName = (String) principalCollection.getPrimaryPrincipal();
 * //根据用户名去数据库查询用户信息 UserVO user = userService.getUserByName(userName);
 * //添加角色和权限 SimpleAuthorizationInfo simpleAuthorizationInfo = new
 * SimpleAuthorizationInfo(); for (RoleDO role :user.getRoles()) { //添加角色
 * simpleAuthorizationInfo.addRole(role.getRoleName()); //添加权限 for (Permissions
 * permissions : role.getPermissions()) {
 * simpleAuthorizationInfo.addStringPermission(permissions.getPermissionsName())
 * ; } } return simpleAuthorizationInfo; }
 * 
 *//**
	 * 获取即将需要认证的信息
	 *//*
		 * @Override protected AuthenticationInfo
		 * doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws
		 * AuthenticationException { //加这一步的目的是在Post请求的时候会先进认证，然后在到请求 if
		 * (authenticationToken.getPrincipal() == null) { return null; }
		 * 
		 * //根据用户名从数据库获取密码 UserVO user =
		 * userService.getUserByName(authenticationToken.getPrincipal().toString()); if
		 * (user == null) { //这里返回后会报出对应异常 return null; } else { HttpServletRequest
		 * request = ((ServletRequestAttributes)
		 * RequestContextHolder.getRequestAttributes()).getRequest(); // 存放到Session
		 * HttpSession session= request.getSession(); session.setAttribute("user",
		 * user); //这里验证authenticationToken和simpleAuthenticationInfo的信息 return new
		 * SimpleAuthenticationInfo(authenticationToken.getPrincipal().toString(),
		 * user.getPassWord().toString(), getName()); } } }
		 */
