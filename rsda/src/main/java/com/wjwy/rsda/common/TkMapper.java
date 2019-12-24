/*
 * @Descripttion: 集成Mapper
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-04 09:42:19
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2019-12-24 10:03:59
 */
package com.wjwy.rsda.common;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface TkMapper<T> extends Mapper<T>, MySqlMapper<T> {
    // 特别注意：该接口不能被扫描到,否则会出错
}