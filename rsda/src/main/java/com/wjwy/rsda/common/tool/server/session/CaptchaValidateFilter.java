/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2020-01-04 10:34:33
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-04 10:37:57
 */
package com.wjwy.rsda.common.tool.server.session;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.wjwy.rsda.common.util.ShiroUtils;
import com.wjwy.rsda.common.util.StringUtils;
import com.wjwy.rsda.enums.MessageConstant;
import com.wjwy.rsda.enums.ShiroConstants;

import org.apache.shiro.web.filter.AccessControlFilter;


/**
 * 验证码过滤器
 * 
 */
public class CaptchaValidateFilter extends AccessControlFilter
{
    /**
     * 是否开启验证码
     */
    private boolean captchaEnabled = true;

    /**
     * 验证码类型
     */
    private String captchaType = "math";

    public void setCaptchaEnabled(boolean captchaEnabled)
    {
        this.captchaEnabled = captchaEnabled;
    }

    public void setCaptchaType(String captchaType)
    {
        this.captchaType = captchaType;
    }

    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception
    {
        request.setAttribute(ShiroConstants.CURRENT_ENABLED, captchaEnabled);
        request.setAttribute(ShiroConstants.CURRENT_TYPE, captchaType);
        return super.onPreHandle(request, response, mappedValue);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception
    {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        // 验证码禁用 或不是表单提交 允许访问
        if (captchaEnabled == false || !"post".equals(httpServletRequest.getMethod().toLowerCase()))
        {
            return true;
        }
        return validateResponse(httpServletRequest, httpServletRequest.getParameter(ShiroConstants.CURRENT_VALIDATECODE));
    }

    public boolean validateResponse(HttpServletRequest request, String validateCode)
    {
        Object obj = ShiroUtils.getSession().getAttribute(MessageConstant.KAPTCHA_SESSION_KEY);
        String code = String.valueOf(obj != null ? obj : "");
        if (StringUtils.isEmpty(validateCode) || !validateCode.equalsIgnoreCase(code))
        {
            return false;
        }
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception
    {
        request.setAttribute(ShiroConstants.CURRENT_CAPTCHA, ShiroConstants.CAPTCHA_ERROR);
        return true;
    }
}
