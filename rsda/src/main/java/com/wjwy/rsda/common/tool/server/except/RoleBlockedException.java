/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2020-01-03 16:57:08
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2020-01-03 17:00:33
 */
package com.wjwy.rsda.common.tool.server.except;

/**
 * 角色锁定异常类
 */
public class RoleBlockedException extends UserException {
    private static final long serialVersionUID = 1L;

    public RoleBlockedException() {
        super("role.blocked", null);
    }
}
