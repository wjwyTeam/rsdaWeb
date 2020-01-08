package com.wjwy.rsda.common.config;

/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-19 11:11:03
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-08 14:56:53
 */
import com.alibaba.druid.util.StringUtils;

import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

public class MySessionManage extends DefaultWebSessionManager {
    private static final String AUTHORIZATION = "token";

    public MySessionManage() {
        super();
    }

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String sessionId = WebUtils.toHttp(request).getHeader(AUTHORIZATION);
        if (StringUtils.isEmpty(sessionId)) {
            sessionId = super.getSessionId(request, response) + "";
        }
        return sessionId;
    }
}