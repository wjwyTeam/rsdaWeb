/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2020-01-17 15:51:56
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-17 15:52:21
 */
package com.wjwy.rsda.common.tool.except;

/**
 * 文件信息异常类
 *
 */
public class FileException extends BaseException {
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args) {
        super("file", code, args, null);
    }

}
