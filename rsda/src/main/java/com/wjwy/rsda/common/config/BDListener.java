
package com.wjwy.rsda.common.config;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import java.util.concurrent.atomic.AtomicInteger;
/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-19 10:56:15
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2020-01-10 16:01:56
 */

/**
 * 用来监听Session，过期或停止后可以进行一些操作。 这里只做参考，具体操作根据业务需求
 */
public class BDListener implements SessionListener {

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