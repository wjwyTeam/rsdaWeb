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

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.wjwy.rsda.common.tool.server.system.Online;
import com.wjwy.rsda.mapper.UserOnlineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 在线用户 服务层
 */
@Service("onlineService")
public class OnlineService {
    @Autowired
    private UserOnlineMapper userOnlineDao;

    /**
     * 通过会话序号查询信息
     * 
     * @param sessionId 会话ID
     * @return 在线用户信息
     */
    public Online selectOnlineById(String sessionId) {
        return userOnlineDao.selectByPrimaryKey(sessionId);

    }

    /**
     * 通过会话序号删除信息
     * 
     * @param sessionId 会话ID
     * @return 在线用户信息
     */
    public void deleteOnlineById(String sessionId) {

    }

    /**
     * 通过会话序号删除信息
     * 
     * @param sessions 会话ID集合
     * @return 在线用户信息
     */
    public void batchDeleteOnline(List<String> sessions) {

    }

    /**
     * 保存会话信息
     * 
     * @param online 会话信息
     */
    public void saveOnline(Online online) {

    }

    /**
     * 查询会话集合
     * 
     * @param userOnline 分页参数
     * @param limit
     * @param page
     * @return 会话集合
     */
    public PageInfo<Online> selectUserOnlineList(Online userOnline, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        Example example = new Example(Online.class);

        Criteria criteria = example.createCriteria();
        if (StringUtil.isNotEmpty(userOnline.getIpaddr())) {
            criteria.andEqualTo("ipddr", userOnline.getIpaddr());
        }

        if (StringUtil.isNotEmpty(userOnline.getLoginName())) {
            criteria.andEqualTo("loginName", userOnline.getLoginName());
        }
        List<Online> onlines = userOnlineDao.selectByExample(example);
        PageInfo<Online> PageInfoDO = new PageInfo<Online>(onlines);
        return PageInfoDO;
    }

    /**
     * 强退用户
     * 
     * @param sessionId 会话ID
     */
    public void forceLogout(String sessionId) {

    }

    /**
     * 查询会话集合
     * 
     * @param expiredDate 有效期
     * @return 会话集合
     */
    public List<Online> selectOnlineByExpired(Date expiredDate) {
        return null;

    }
}
