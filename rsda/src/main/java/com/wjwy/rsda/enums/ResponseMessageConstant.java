/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-06 18:03:09
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2019-12-19 17:18:04
 */
package com.wjwy.rsda.enums;

public class ResponseMessageConstant {
	// Response返回错误信息
	public static final String MSG_SUCCESS = "操作成功";
	public static final String MSG_NOT_FOUND = "找不到记录,可能已被删除";
	public static final String MSG_INVALID_DATA = "数据不完整或格式错误";
	public static final String MSG_FAIL = "操作不成功,可能是网络故障,请稍后重试";
	public static final String MSG_ERROR = "网络故障,请稍后重试";
	public static final String MSG_DENY_MODIFY = "当前数据不可操作";
	public static final String MSG_DENY_DUPLICATE = "已经存在唯一数据";
	/**
	 * UTF-8 字符集
	 */
	public static final String UTF8 = "UTF-8";

	/**
	 * 通用成功标识
	 */
	public static final String SUCCESS = "0";

	/**
	 * 通用失败标识
	 */
	public static final String FAIL = "1";

	/**
	 * 登录成功
	 */
	public static final String LOGIN_SUCCESS = "Success";

	/**
	 * 注销
	 */
	public static final String LOGOUT = "Logout";

	/**
	 * 登录失败
	 */
	public static final String LOGIN_FAIL = "Error";

	/**
	 * 当前记录起始索引
	 */
	public static final String PAGE_NUM = "pageNum";

	/**
	 * 每页显示记录数
	 */
	public static final String PAGE_SIZE = "pageSize";

	/**
	 * 排序列
	 */
	public static final String ORDER_BY_COLUMN = "orderByColumn";

	/**
	 * 排序的方向 "desc" 或者 "asc".
	 */
	public static final String IS_ASC = "isAsc";

	/**
	 * 资源映射路径 前缀
	 */
	public static final String RESOURCE_PREFIX = "/profile";
}
