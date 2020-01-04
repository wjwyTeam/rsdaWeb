/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2020-01-03 16:57:08
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2020-01-03 17:01:01
 */
package com.wjwy.rsda.common.tool.server.except;

/**
 * 用户账号已被删除
 */
public class UserDeleteException extends UserException {
    private static final long serialVersionUID = 1L;

    public UserDeleteException() {
        super("user.password.delete", null);
    }
}
