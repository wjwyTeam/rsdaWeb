/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-19 14:47:31
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2019-12-31 09:47:13
 */
package com.wjwy.rsda.common.tool.server.session;

import com.wjwy.rsda.enums.EnumEntitys;

import org.apache.shiro.session.mgt.SimpleSession;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * 在线用户会话属性
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("在线用户=>")
public class OnlineSession extends SimpleSession
{
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    private Long userId;

    /** 用户名称 */
    private String loginName;

    /** 部门名称 */
    private String deptName;
	
	/** 用户头像 */
	private String avatar;

    /** 登录IP地址 */
    private String host;

    /** 浏览器类型 */
    private String browser;

    /** 操作系统 */
    private String os;

    /** 在线状态 */
    private EnumEntitys status = EnumEntitys.ONLINE;

    /** 属性是否改变 优化session数据同步 */
    private transient boolean attributeChanged = false;

    @Override
    public String getHost()
    {
        return host;
    }

    @Override
    public void setHost(String host)
    {
        this.host = host;
    }

    @Override
    public void setAttribute(Object key, Object value)
    {
        super.setAttribute(key, value);
    }

    @Override
    public Object removeAttribute(Object key)
    {
        return super.removeAttribute(key);
    }


    public void markAttributeChanged()
    {
        this.attributeChanged = true;
    }

    public void resetAttributeChanged()
    {
        this.attributeChanged = false;
    }

    public boolean isAttributeChanged()
    {
        return attributeChanged;
    }

}
