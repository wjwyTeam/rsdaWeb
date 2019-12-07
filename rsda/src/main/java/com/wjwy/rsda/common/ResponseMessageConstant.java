/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-06 18:03:09
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2019-12-06 18:04:40
 */
package com.wjwy.rsda.common;

public class ResponseMessageConstant {
	//Response返回错误信息
	public static final String MSG_SUCCESS = "操作成功";
	public static final String MSG_NOT_FOUND = "找不到记录,可能已被删除";
	public static final String MSG_INVALID_DATA="数据不完整或格式错误";
	public static final String MSG_FAIL="操作不成功,可能是网络故障,请稍后重试";
	public static final String MSG_ERROR="网络故障,请稍后重试";
	public static final String MSG_DENY_MODIFY="当前数据不可操作";
	public static final String MSG_DENY_DUPLICATE="已经存在唯一数据";
}
