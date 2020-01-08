package com.wjwy.rsda.common.config;

import java.util.Set;
import org.slf4j.Logger;
import java.util.HashSet;
import org.slf4j.LoggerFactory;
import com.wjwy.rsda.entity.User;
import org.apache.shiro.SecurityUtils;
import com.wjwy.rsda.services.RoleService;
import com.wjwy.rsda.services.UserService;
import com.wjwy.rsda.common.util.ShiroUtils;
import com.wjwy.rsda.services.FunctionService;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import com.wjwy.rsda.common.tool.except.CaptchaException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.springframework.context.annotation.Configuration;
import com.wjwy.rsda.common.tool.except.RoleBlockedException;
import com.wjwy.rsda.common.tool.except.UserBlockedException;
import org.springframework.beans.factory.annotation.Autowired;
import com.wjwy.rsda.common.tool.except.UserNotExistsException;
import com.wjwy.rsda.common.tool.except.UserPasswordNotMatchException;
import com.wjwy.rsda.common.tool.except.UserPasswordRetryLimitExceedException;
/*
 * @Descripttion: 认证领域
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2019-12-16 09:05:48
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-08 14:34:50
 */

@Configuration
public class AuthRealm extends AuthorizingRealm {
    private static final Logger log = LoggerFactory.getLogger(AuthRealm.class);
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private FunctionService functionService;

    /**
     * 认证回调函数,登录时调用
     * 首先根据传入的用户名获取User信息；然后如果user为空，那么抛出没找到帐号异常UnknownAccountException；
     * 如果user找到但锁定了抛出锁定异常LockedAccountException；最后生成AuthenticationInfo信息，
     * 交给间接父类AuthenticatingRealm使用CredentialsMatcher进行判断密码是否匹配，
     * 如果不匹配将抛出密码错误异常IncorrectCredentialsException；
     * 另外如果密码重试此处太多将抛出超出重试次数异常ExcessiveAttemptsException；
     * 在组装SimpleAuthenticationInfo信息时， 需要传入：身份信息（用户名）、凭据（密文密码）、盐（username+salt），
     * CredentialsMatcher使用盐加密传入的明文密码和此处的密文密码进行匹配。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // StringBuffer passWord = new StringBuffer();
        // for (char p : (char[]) token.getCredentials()) {
        // passWord.append(p);
        // }
        // User user = Optional
        // .ofNullable(userService.getUserByLogin(token.getPrincipal().toString(),
        // passWord.toString()))
        // .orElseThrow(UnknownAccountException::new);
        // 锁定账户先空着， 暂时用不上
        // if (!user.isLocked()) {
        // throw new LockedAccountException();
        // }
        // 从数据库查询出来的账号名和密码,与用户输入的账号和密码对比
        // 当用户执行登录时,在方法处理上要实现 user.login(token)
        // 然后会自动进入这个类进行认证
        // 交给 AuthenticatingRealm 使用 CredentialsMatcher 进行密码匹配，如果觉得人家的不好可以自定义实现
        //
        // 存入用户信息
        // List<Object> principals = new ArrayList<Object>();
        // principals.add(user.getUserName());
        // principals.add(user);
        // SimpleAuthenticationInfo authenticationInfo = new
        // SimpleAuthenticationInfo(principals, user.getPassWord(),
        // getName());
        // Session session = SecurityUtils.getSubject().getSession();
        // session.setAttribute("USER_SESSION", user);

        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String userName = upToken.getUsername();
        String passWord = "";
        if (upToken.getPassword() != null) {
            passWord = new String(upToken.getPassword());
        }

        User user = null;
        try {
            user = userService.login(userName, passWord);
        } catch (CaptchaException e) {
            throw new AuthenticationException(e.getMessage(), e);
        } catch (UserNotExistsException e) {
            throw new UnknownAccountException(e.getMessage(), e);
        } catch (UserPasswordNotMatchException e) {
            throw new IncorrectCredentialsException(e.getMessage(), e);
        } catch (UserPasswordRetryLimitExceedException e) {
            throw new ExcessiveAttemptsException(e.getMessage(), e);
        } catch (UserBlockedException e) {
            throw new LockedAccountException(e.getMessage(), e);
        } catch (RoleBlockedException e) {
            throw new LockedAccountException(e.getMessage(), e);
        } catch (Exception e) {
            log.info("对用户[" + userName + "]进行登录验证..验证未通过{}", e.getMessage());
            throw new AuthenticationException(e.getMessage(), e);
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, passWord, getName());
        return info;
    }

    /**
     * 只有需要验证权限时才会调用, 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.在配有缓存的情况下，只加载一次.
     * 如果需要动态权限,但是又不想每次去数据库校验,可以存在ehcache中.自行完善
     */

    /**
     * 只有需要验证权限时才会调用, 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.在配有缓存的情况下，只加载一次.
     * 如果需要动态权限,但是又不想每次去数据库校验,可以存在ehcache中.自行完善
     */

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        User user = ShiroUtils.getUser();

        // 角色列表
        Set<String> roles = new HashSet<String>();

        // 功能列表
        Set<String> functions = new HashSet<String>();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 管理员拥有所有权限
        if (user.isAdmin()) {
            authorizationInfo.addRole("admin");
            authorizationInfo.addStringPermission("*:*:*");
        } else {
            roles = roleService.selectRoleKeys(user.getUserId());
            functions = functionService.selectPermsByUserId(user.getUserId());
            // 角色加入AuthorizationInfo认证对象
            authorizationInfo.setRoles(roles);
            // 权限加入AuthorizationInfo认证对象
            authorizationInfo.setStringPermissions(functions);
        }
        return authorizationInfo;
    }

    /**
     * 清理缓存权限
     */
    public void clearCachedAuthorizationInfo() {
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }
}