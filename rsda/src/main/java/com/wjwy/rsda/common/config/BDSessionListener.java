/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-19 10:56:15
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2019-12-19 10:56:57
 */
package com.wjwy.rsda.common.config;

import java.util.concurrent.atomic.AtomicInteger;
 
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
 
/**
 * 用来监听Session，过期或停止后可以进行一些操作。
 * 这里只做参考，具体操作根据业务需求
 */
public class BDSessionListener implements SessionListener {
 
	private final AtomicInteger sessionCount = new AtomicInteger(0);
 
	@Override
	public void onStart(Session session) {
		sessionCount.incrementAndGet();
	}
 
	@Override
	public void onStop(Session session) {
		sessionCount.decrementAndGet();
	}
 
	@Override
	public void onExpiration(Session session) {
		sessionCount.decrementAndGet();
 
	}
 
	public int getSessionCount() {
		return sessionCount.get();
	}
}