/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2020-01-03 16:57:08
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-08 14:28:22
 */
package com.wjwy.rsda.common.tool.except;

/**
 * 用户信息异常类
 */
public class UserException extends BaseException {
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args) {
        super("user", code, args, null);
    }
}
