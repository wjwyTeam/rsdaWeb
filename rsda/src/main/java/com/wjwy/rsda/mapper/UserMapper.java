/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-06 11:22:13
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2019-12-06 11:33:18
 */
package com.wjwy.rsda.mapper;

import com.wjwy.rsda.common.TkMapper;
import com.wjwy.rsda.entity.User;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends TkMapper<User> {

}
