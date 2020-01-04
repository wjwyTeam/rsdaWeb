/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2020-01-03 16:57:08
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2020-01-03 17:01:13
 */
package com.wjwy.rsda.common.tool.server.except;

/**
 * 用户不存在异常类
 */
public class UserNotExistsException extends UserException {
    private static final long serialVersionUID = 1L;

    public UserNotExistsException() {
        super("user.not.exists", null);
    }
}
