/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2020-01-03 16:57:08
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2020-01-03 17:01:37
 */
package com.wjwy.rsda.common.tool.except;

/**
 * 用户错误记数异常类
 */
public class UserPasswordRetryLimitCountException extends UserException {
    private static final long serialVersionUID = 1L;

    public UserPasswordRetryLimitCountException(int retryLimitCount) {
        super("user.password.retry.limit.count", new Object[] { retryLimitCount });
    }
}
