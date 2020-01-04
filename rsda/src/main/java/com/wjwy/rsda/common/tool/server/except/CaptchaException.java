/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2020-01-03 16:57:08
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-03 16:57:42
 */
package com.wjwy.rsda.common.tool.server.except;

/**
 * 验证码错误异常类
 */
public class CaptchaException extends UserException {
    private static final long serialVersionUID = 1L;

    public CaptchaException() {
        super("user.jcaptcha.error", null);
    }
}
