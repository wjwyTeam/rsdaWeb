/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2020-01-03 16:57:08
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-08 14:28:34
 */
package com.wjwy.rsda.common.tool.except;

/**
 * 用户密码不正确或不符合规范异常类
 * 
 */
public class UserPasswordNotMatchException extends UserException {
    private static final long serialVersionUID = 1L;

    public UserPasswordNotMatchException() {
        super("user.password.not.match", null);
    }
}
