/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-19 16:00:14
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2019-12-19 16:00:41
 */
package com.wjwy.rsda.common.tool.server.service;

import java.util.Date;
import java.util.List;

import com.wjwy.rsda.common.tool.server.Online;

import org.springframework.stereotype.Service;

/**
 * 在线用户 服务层
 */
@Service("onlineService")
public class OnlineService {
    /**
     * 通过会话序号查询信息
     * 
     * @param sessionId 会话ID
     * @return 在线用户信息
     */
    public Online selectOnlineById(String sessionId){
        return null;
        
    }

    /**
     * 通过会话序号删除信息
     * 
     * @param sessionId 会话ID
     * @return 在线用户信息
     */
    public void deleteOnlineById(String sessionId){

    }

    /**
     * 通过会话序号删除信息
     * 
     * @param sessions 会话ID集合
     * @return 在线用户信息
     */
    public void batchDeleteOnline(List<String> sessions){

    }

    /**
     * 保存会话信息
     * 
     * @param online 会话信息
     */
    public void saveOnline(Online online){

    }

    /**
     * 查询会话集合
     * 
     * @param userOnline 分页参数
     * @return 会话集合
     */
    public List<Online> selectUserOnlineList(Online userOnline){
        return null;

        
    }

    /**
     * 强退用户
     * 
     * @param sessionId 会话ID
     */
    public void forceLogout(String sessionId){

    }

    /**
     * 查询会话集合
     * 
     * @param expiredDate 有效期
     * @return 会话集合
     */
    public List<Online> selectOnlineByExpired(Date expiredDate){
        return null;

    }
}
