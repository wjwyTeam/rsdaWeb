/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2020-01-03 16:57:08
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-03 19:22:01
 */
package com.wjwy.rsda.common.tool.server.except;

/**
 * 用户错误最大次数异常类
 * 
 */
public class UserPasswordRetryLimitExceedException extends UserException {
    private static final long serialVersionUID = 1L;

    public UserPasswordRetryLimitExceedException(int retryLimitCount) {
        super("user.password.retry.limit.exceed", new Object[] { retryLimitCount });
    }
}
