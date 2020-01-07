/*
 * @Descripttion: 
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2020-01-03 17:44:27
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-07 15:42:30
 */
package com.wjwy.rsda.common.tool.server.service;

import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.PostConstruct;

import com.wjwy.rsda.common.tool.factory.AsyncFactory;
import com.wjwy.rsda.common.tool.factory.AsyncManager;
import com.wjwy.rsda.common.tool.server.except.UserPasswordNotMatchException;
import com.wjwy.rsda.common.tool.server.except.UserPasswordRetryLimitExceedException;
import com.wjwy.rsda.common.util.MessageUtils;
import com.wjwy.rsda.entity.User;
import com.wjwy.rsda.enums.MessageConstant;
import com.wjwy.rsda.enums.ShiroConstants;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 登录密码方法
 */
@Service("passwordService")
@Transactional
public class PasswordService {
    @Autowired
    private CacheManager cacheManager;

    private Cache<String, AtomicInteger> loginRecordCache;

    private Integer maxRetryCount = 5;

    @PostConstruct
    public void init() {
        loginRecordCache = cacheManager.getCache(ShiroConstants.LOGINRECORDCACHE);
    }

    public void validate(User user, String password) {
        String loginName = user.getUserName();

        AtomicInteger retryCount = loginRecordCache.get(loginName);

        if (retryCount == null) {
            retryCount = new AtomicInteger(0);
            loginRecordCache.put(loginName, retryCount);
        }
        if (retryCount.incrementAndGet() > maxRetryCount) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, MessageConstant.LOGIN_FAIL,
                    MessageUtils.message("user.password.retry.limit.exceed", maxRetryCount)));
            throw new UserPasswordRetryLimitExceedException(Integer.valueOf(maxRetryCount).intValue());
        }

        if (!matches(user, password)) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, MessageConstant.LOGIN_FAIL,
                    MessageUtils.message("user.password.retry.limit.count", retryCount)));
            loginRecordCache.put(loginName, retryCount);
            throw new UserPasswordNotMatchException();
        } else {
            clearLoginRecordCache(loginName);
        }
    }

    public boolean matches(User user, String newPassword) {
        return user.getPassWord().equals(encryptPassword(user.getUserName(), newPassword, user.getSalt()));
    }

    public void clearLoginRecordCache(String userName) {
        loginRecordCache.remove(userName);
    }

    public String encryptPassword(String userName, String passWord, String salt) {
        return new Md5Hash(userName + passWord + salt).toHex().toString();
    }

    public void unlock(String loginName) {
        loginRecordCache.remove(loginName);
    }

}
