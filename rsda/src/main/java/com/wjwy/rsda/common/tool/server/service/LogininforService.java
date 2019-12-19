/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-19 17:30:10
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2019-12-19 17:31:31
 */
package com.wjwy.rsda.common.tool.server.service;

import java.util.List;

import org.springframework.stereotype.Service;


/**
 * 系统访问日志情况信息 服务层
 * @param <Logininfor>
 */
@Service("logininforService")
public class LogininforService<Logininfor>
{
    /**
     * 新增系统登录日志
     * 
     * @param logininfor 访问日志对象
     */
    public void insertLogininfor(Logininfor logininfor){

    }

    /**
     * 查询系统登录日志集合
     * 
     * @param logininfor 访问日志对象
     * @return 登录记录集合
     */
    public List<Logininfor> selectLogininforList(Logininfor logininfor){
            return null;
    }

    /**
     * 批量删除系统登录日志
     * 
     * @param ids 需要删除的数据
     * @return
     */
    public int deleteLogininforByIds(String ids){
        return 0;
    }

    /**
     * 清空系统登录日志
     */
    public void cleanLogininfor(){
        
    }
}
