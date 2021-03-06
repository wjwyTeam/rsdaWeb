package com.wjwy.rsda.common.tool.factory;

import java.util.Date;
import java.util.TimerTask;

import com.wjwy.rsda.common.tool.session.OnlineSession;
import com.wjwy.rsda.common.util.AddressUtils;
import com.wjwy.rsda.common.util.LogUtils;
import com.wjwy.rsda.common.util.ServletUtils;
import com.wjwy.rsda.common.util.ShiroUtils;
import com.wjwy.rsda.common.util.SpringUtils;
import com.wjwy.rsda.entity.Logininfor;
import com.wjwy.rsda.entity.Online;
import com.wjwy.rsda.entity.OperLog;
import com.wjwy.rsda.common.enums.MessageConstant;
import com.wjwy.rsda.services.LogininforService;
import com.wjwy.rsda.services.OnlineService;
import com.wjwy.rsda.services.OperLogService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.bitwalker.useragentutils.UserAgent;

/**
 * 异步工厂（产生任务用）
 *
 */
public class AsyncFactory {
    private static final Logger sys_user_logger = LoggerFactory.getLogger("sys-user");

    /**
     * 同步session到数据库
     * 
     * @param session 在线用户会话
     * @return 任务task
     */
    public static TimerTask syncSessionToDb(final OnlineSession session) {
        return new TimerTask() {
            @Override
            public void run() {
                Online online = new Online();
                online.setSessionId(String.valueOf(session.getId()));
                online.setDeptName(session.getDeptName());
                online.setLoginName(session.getLoginName());
                online.setStartTimestamp(session.getStartTimestamp());
                online.setLastAccessTime(session.getLastAccessTime());
                online.setExpireTime(session.getTimeout());
                online.setIpaddr(session.getHost());
                online.setLoginLocation(AddressUtils.getRealAddressByIP(session.getHost()));
                online.setBrowser(session.getBrowser());
                online.setOs(session.getOs());
                online.setStatus((String)session.getStatus());
                SpringUtils.getBean(OnlineService.class).saveOnline(online);

            }
        };
    }

    /**
     * 操作日志记录
     * 
     * @param operLog 操作日志信息
     * @return 任务task
     */
    public static TimerTask recordOper(final OperLog operLog) {
        return new TimerTask() {
            @Override
            public void run() {
                // 远程查询操作地点
                operLog.setOperLocation(AddressUtils.getRealAddressByIP(operLog.getOperIp()));
                SpringUtils.getBean(OperLogService.class).insertOperlog(operLog);
            }
        };
    }

    /**
     * 记录登陆信息
     * 
     * @param username 用户名
     * @param status   状态
     * @param message  消息
     * @param args     列表
     * @return 任务task
     */
    
    public static TimerTask recordLogininfor(final String username, final String status, final String message,
            final Object... args) {
        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        final String ip = ShiroUtils.getIp();
        return new TimerTask() {
            @Override
            public void run() {
                String address = AddressUtils.getRealAddressByIP(ip);
                StringBuilder s = new StringBuilder();
                s.append(LogUtils.getBlock(ip));
                s.append(address);
                s.append(LogUtils.getBlock(username));
                s.append(LogUtils.getBlock(status));
                s.append(LogUtils.getBlock(message));
                // 打印信息到日志
                sys_user_logger.info(s.toString(), args);
                // 获取客户端操作系统
                String os = userAgent.getOperatingSystem().getName();
                // 获取客户端浏览器
                String browser = userAgent.getBrowser().getName();
                // 封装对象
                Logininfor logininfor = new Logininfor();
                logininfor.setLoginName(username);
                logininfor.setIpaddr(ip);
                logininfor.setLoginLocation(address);
                logininfor.setBrowser(browser);
                logininfor.setOs(os);
                logininfor.setMsg(message);
                logininfor.setLoginTime(new Date());
                // 日志状态
                if (MessageConstant.LOGIN_SUCCESS.equals(status) || MessageConstant.LOGOUT.equals(status)) {
                    logininfor.setStatus(MessageConstant.SUCCESS);
                } else if (MessageConstant.LOGIN_FAIL.equals(status)) {
                    logininfor.setStatus(MessageConstant.FAIL);
                }
                // 插入数据
                SpringUtils.getBean(LogininforService.class).logininforInsert(logininfor);
            }
        };
    }
}
