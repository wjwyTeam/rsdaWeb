package com.wjwy.rsda.common.tool.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.wjwy.rsda.entity.Online;
import com.wjwy.rsda.common.util.BeanUtils;
import com.wjwy.rsda.common.util.SpringUtils;
import com.wjwy.rsda.common.util.StringUtils;
import com.wjwy.rsda.common.enums.ShiroConstants;
import com.wjwy.rsda.services.OnlineService;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.shiro.session.ExpiredSessionException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 主要是在此如果会话的属性修改了 就标识下其修改了 然后方便 OnlineSessionDao同步
 */
public class OnlineWebSessionManager extends DefaultWebSessionManager {
    private static final Logger log = LoggerFactory.getLogger(OnlineWebSessionManager.class);

    @Override
    public void setAttribute(SessionKey sessionKey, Object attributeKey, Object value) throws InvalidSessionException {
        super.setAttribute(sessionKey, attributeKey, value);
        if (value != null && needMarkAttributeChanged(attributeKey)) {
            OnlineSession session = getOnlineSession(sessionKey);
            session.markAttributeChanged();
        }
    }

    private boolean needMarkAttributeChanged(Object attributeKey) {
        if (attributeKey == null) {
            return false;
        }
        String attributeKeyStr = attributeKey.toString();
        // 优化 flash属性没必要持久化
        if (attributeKeyStr.startsWith("org.springframework")) {
            return false;
        }
        if (attributeKeyStr.startsWith("javax.servlet")) {
            return false;
        }
        if (attributeKeyStr.equals(ShiroConstants.CURRENT_USERNAME)) {
            return false;
        }
        return true;
    }

    @Override
    public Object removeAttribute(SessionKey sessionKey, Object attributeKey) throws InvalidSessionException {
        Object removed = super.removeAttribute(sessionKey, attributeKey);
        if (removed != null) {
            OnlineSession s = getOnlineSession(sessionKey);
            s.markAttributeChanged();
        }

        return removed;
    }

    public OnlineSession getOnlineSession(SessionKey sessionKey) {
        OnlineSession session = null;
        Object obj = doGetSession(sessionKey);
        if (StringUtils.isNotNull(obj)) {
            session = new OnlineSession();
            BeanUtils.copyBeanProp(session, obj);
        }
        return session;
    }

    /**
     * 验证session是否有效 用于删除过期session
     */
    @Override
    public void validateSessions() {
        if (log.isInfoEnabled()) {
            log.info("invalidation sessions...");
        }

        int invalidCount = 0;

        int timeout = (int) this.getGlobalSessionTimeout();
        Date expiredDate = DateUtils.addMilliseconds(new Date(), 0 - timeout);
        OnlineService onlineService = SpringUtils.getBean(OnlineService.class);
        List<Online> userOnlineList = onlineService.selectOnlineByExpired(expiredDate);
        // 批量过期删除
        List<String> needOfflineIdList = new ArrayList<String>();

        if (StringUtils.isNotNull(userOnlineList)) {
            for (Online userOnline : userOnlineList) {
                try {
                    SessionKey key = new DefaultSessionKey(userOnline.getSessionId());
                    Session session = retrieveSession(key);
                    if (session != null) {
                        throw new InvalidSessionException();
                    }
                } catch (InvalidSessionException e) {
                    if (log.isDebugEnabled()) {
                        boolean expired = (e instanceof ExpiredSessionException);
                        String msg = "Invalidated session with id [" + userOnline.getSessionId() + "]"
                                + (expired ? " (expired)" : " (stopped)");
                        log.debug(msg);
                    }
                    invalidCount++;
                    needOfflineIdList.add(userOnline.getSessionId());
                }

            }
            if (needOfflineIdList.size() > 0) {
                try {
                    onlineService.batchDeleteOnline(needOfflineIdList);
                } catch (Exception e) {
                    log.error("batch delete db session error.", e);
                }
            }

            if (log.isInfoEnabled()) {
                String msg = "Finished invalidation session.";
                if (invalidCount > 0) {
                    msg += " [" + invalidCount + "] sessions were stopped.";
                } else {
                    msg += " No sessions were stopped.";
                }
                log.info(msg);
            }
        }
       

    }

    @Override
    protected Collection<Session> getActiveSessions() {
        throw new UnsupportedOperationException("getActiveSessions method not supported");
    }


    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
       /* 此方法获取客户端cookie的值,如果你的项目将sesssionId放在requestparam中，或者拼接在url中，请查看该方法源码，自行修改*/
        String id = super.getSessionIdCookie().readValue(httpRequest, WebUtils.toHttp(response));
        if(id != null){
           /*此处并非http 的session，是shiro在redis中缓存的session（SimpleSession）*/
           /* 此方法是查询redis中的session，笔者在sessionDao中注入了redisManager如果你重写了RedisSessionDAO，则需要更改获取session的方法 */
            Session session=null;
            if(session == null){
                return null;
            }
        }
        /*   如果redis中session未过期，此处必须调用父类获取方法 */
        return super.getSessionId(request, response);
    }
}
