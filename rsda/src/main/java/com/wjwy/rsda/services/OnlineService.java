/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-19 16:00:14
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-11 13:15:39
 */
package com.wjwy.rsda.services;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.wjwy.rsda.entity.Online;
import com.wjwy.rsda.common.util.DateUtils;
import com.wjwy.rsda.mapper.UserOnlineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 在线用户 服务层
 */
@Service("onlineService")
@Transactional
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
        Online on = new Online();
        on.setSessionId(sessionId);
        return userOnlineDao.selectOne(on);

    }

    /**
     * 通过会话序号删除信息
     * 
     * @param sessionId 会话ID
     * @return
     * @return 在线用户信息
     */
    public void deleteOnlineById(String sessionId) {
        if (selectOnlineById(sessionId) != null) {
            Example example = new Example(Online.class);
            Criteria criteria = example.createCriteria();
            criteria.andEqualTo("sessionId", sessionId);
            userOnlineDao.deleteByExample(example);
        }

    }

    /**
     * 通过会话序号删除信息
     * 
     * @param sessions 会话ID集合
     * @return 在线用户信息
     */
    public void batchDeleteOnline(List<String> sessions) {
        for (String string : sessions) {
            deleteOnlineById(string);
        }

    }

    /**
     * 保存会话信息
     * 
     * @param online 会话信息
     */
    public void saveOnline(Online online) {
        if (StringUtil.isNotEmpty(online.getSessionId())) {
            Example example = new Example(Online.class);
            Criteria criteria = example.createCriteria();
            criteria.andEqualTo("sessionId", online.getSessionId());
            Online onlineL = userOnlineDao.selectOneByExample(example);
            if (onlineL==null) {
                userOnlineDao.insert(online);
            }
        }
      
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
        deleteOnlineById(sessionId);
    }

    /**
     * 查询会话集合
     * 
     * @param expiredDate 有效期
     * @return 会话集合
     */
    public List<Online> selectOnlineByExpired(Date expiredDate) {
        String lastAccessTime = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, expiredDate);
        return userOnlineDao.selectOnlineByExpired(lastAccessTime);

    }


    /**
     * 
     * @param userName
     */
    public Online getOnline(String userName) {
        Example example = new Example(Online.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("loginName", userName);
        return userOnlineDao.selectOneByExample(example);

    }

    
    /**
     * 清空用户缓存
     */
    public void clear() {
        userOnlineDao.clear();
    }

    /**
     * 
     * @param serializable
     * @return
     */
	public List<Online> getId(Serializable serializable) {
		Example example = new Example(Online.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("sessionId", serializable);
        return userOnlineDao.selectByExample(example);
	}
}
