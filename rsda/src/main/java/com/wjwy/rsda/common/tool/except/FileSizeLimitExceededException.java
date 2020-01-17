/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2020-01-17 15:53:51
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-17 15:54:03
 */
package com.wjwy.rsda.common.tool.except;

/**
 * 文件名大小限制异常类
 */
public class FileSizeLimitExceededException extends FileException {
    private static final long serialVersionUID = 1L;

    public FileSizeLimitExceededException(long defaultMaxSize) {
        super("upload.exceed.maxSize", new Object[] { defaultMaxSize });
    }
}
