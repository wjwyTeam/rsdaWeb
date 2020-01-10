/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-31 09:37:57
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-10 12:51:35
 */
package com.wjwy.rsda.common.tool.session;

import java.io.IOException;
import com.wjwy.rsda.entity.User;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.util.WebUtils;
import com.github.pagehelper.util.StringUtil;
import com.wjwy.rsda.common.enums.EnumEntitys;
import com.wjwy.rsda.common.enums.ShiroConstants;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义访问控制
 * 
 */
public class OnlineFilter extends AccessControlFilter {
    /**
     * 强制退出后重定向的地址
     */

    @Autowired
    private OnlineSessionDAO onlineSessionDAO;

    public OnlineFilter(OnlineSessionDAO onlineSessionDAO) {
        this.onlineSessionDAO = onlineSessionDAO;
    }

    public OnlineFilter() {
     
    }
    /**
     * 表示是否允许访问；mappedValue就是[urls]配置中拦截器参数部分，如果允许访问返回true，否则false；
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        Subject subject = getSubject(request, response);
        if (subject == null || subject.getSession() == null) {
            return true;
        }
             Session session = onlineSessionDAO.readSession(subject.getSession().getId());
            if (session != null && session instanceof OnlineSession) {
                OnlineSession onlineSession = (OnlineSession) session;
                request.setAttribute(ShiroConstants.ONLINE_SESSION, onlineSession);
                // 把user对象设置进去
                boolean isGuest = onlineSession.getUserId() == null || StringUtil.isEmpty(onlineSession.getUserId());
                if (isGuest == true) {
                    if (subject.getPrincipal() != null) {
                        User user = subject.getPrincipals().oneByType(User.class);
                        if (user != null) {
                            onlineSession.setUserId(user.getUserId());
                            onlineSession.setLoginName(user.getUserName());
                            onlineSession.setDeptName(user.getDeptId());
                            onlineSession.setAvatar(user.getBase64Photo());
                            onlineSession.setStatus(EnumEntitys.ONLINE.getDesc());
                            onlineSession.markAttributeChanged();
                        }
                    }
                }

                if (onlineSession.getStatus() == EnumEntitys.OFFLINE.getDesc()) {
                    return false;
                }
            }
        return true;
    }

    /**
     * 表示当访问拒绝时是否已经处理了；如果返回true表示需要继续处理；如果返回false表示该拦截器实例已经处理了，将直接返回即可。
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        if (subject != null) {
            subject.logout();
        }
        saveRequestAndRedirectToLogin(request, response);
        return false;
    }

    // 跳转到登录页
    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        WebUtils.issueRedirect(request, response, "/login");
    }
}
