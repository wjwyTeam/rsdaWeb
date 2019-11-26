/**  
 * @Title:ShiroConfig.java
 * @Package: com.unis.personnel.common.config
 * @Description: TODO功能描述:(用一句话描述该文件做什么)
 * @Author ZHANGQI
 * @date 2019年11月25日 上午8:07:47
 * @version V1.0  
*/
package com.unis.personnel.server.config;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.cache.RedisCacheManager;

import java.util.LinkedHashMap;
import java.util.Map;
/**
 * @ClassName: ShiroConfig
 * @Description: TODO功能描述:顾名思义就是对shiro的一些配置，相对于之前的xml配置。包括：过滤的文件和权限，密码加密的算法，其用注解等相关功能。
 * @author ZHANGQI
 * @date 2019年11月25日 上午8:07:47
 * @remark 备注:
*/
@Configuration
public class ShiroConfig {
	
/*	@Value("${spring.redis.shiro.host}")
    private String host;
    @Value("${spring.redis.shiro.port}")
    private int port;
    @Value("${spring.redis.shiro.timeout}")
    private int timeout;
    @Value("${spring.redis.shiro.password}")
    private String password;*/
    
	 /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，以为在
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     *
     * Filter Chain定义说明 1、一个URL可以配置多个Filter，使用逗号分隔 2、当设置多个过滤器时，全部验证通过，才视为通过
     * 3、部分过滤器可指定参数，如perms，roles
     *
     */
	    @Bean(name = "shiroFilter")
	    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
	        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
	        
	        // 必须设置 SecurityManager
	        shiroFilterFactoryBean.setSecurityManager(securityManager);
	        
	        // 登录成功后要跳转的链接
	        shiroFilterFactoryBean.setSuccessUrl("/LoginHome");
	        
	        // 未授权界面;
	        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
	        // 拦截器.
	        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
	        // 配置不会被拦截的链接 顺序判断
	        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了，登出后跳转配置的loginUrl
	        filterChainDefinitionMap.put("/logout", "logout");
	        filterChainDefinitionMap.put("/add", "perms[权限添加]");
	        // <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
	        // 配置不会被拦截的链接 顺序判断
	        filterChainDefinitionMap.put("/login", "anon");
	        filterChainDefinitionMap.put("/templates/**", "anon");
	        filterChainDefinitionMap.put("/static/**", "anon");
	        filterChainDefinitionMap.put("/api/**", "anon");
	        
	        filterChainDefinitionMap.put("/admin/**", "authc");
	        filterChainDefinitionMap.put("/user/**", "authc");

	        //主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截 剩余的都需要认证
	        // <!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
	        // <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
	        filterChainDefinitionMap.put("/", "authc");
	        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
	        shiroFilterFactoryBean.setLoginUrl("/admin");
	        
	        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
	        System.out.println("Shiro拦截器工厂类注入成功");
	        return shiroFilterFactoryBean;

	    }

	    @Bean
	    public SecurityManager securityManager() {
	        DefaultWebSecurityManager defaultSecurityManager = new DefaultWebSecurityManager();
	        // 设置realm.
	        defaultSecurityManager.setRealm(customRealm());
//	     // 自定义session管理 使用redis
//	        defaultSecurityManager.setSessionManager(sessionManager());
//	        // 自定义缓存实现 使用redis
//	        defaultSecurityManager.setCacheManager(cacheManager());
	        return defaultSecurityManager;
	    }
//
//	    //自定义sessionManager
//	    @Bean
//	    public SessionManager sessionManager() {
//	        MySessionManager mySessionManager = new MySessionManager();
//	        mySessionManager.setSessionDAO(redisSessionDAO());
//	        return mySessionManager;
//	    }
//	    
//	    /**
//	     * 配置shiro redisManager
//	     * <p>
//	     * 使用的是shiro-redis开源插件
//	     *
//	     * @return
//	     */
//	    public RedisManager redisManager() {
//	        RedisManager redisManager = new RedisManager();
//	        redisManager.setHost(host);
//	        redisManager.setPort(port);
//	        redisManager.setExpire(1800);// 配置缓存过期时间
//	        redisManager.setTimeout(timeout);
//	        redisManager.setPassword(password);
//	        return redisManager;
//	    }
//	    
//	    /**
//	     * cacheManager 缓存 redis实现
//	     * <p>
//	     * 使用的是shiro-redis开源插件
//	     *
//	     * @return
//	     */
//	    @Bean
//	    public RedisCacheManager cacheManager() {
//	        RedisCacheManager redisCacheManager = new RedisCacheManager();
//	        redisCacheManager.setRedisManager(redisManager());
//	        return redisCacheManager;
//	    }
//	 
//	    /**
//	     * RedisSessionDAO shiro sessionDao层的实现 通过redis
//	     * <p>
//	     * 使用的是shiro-redis开源插件
//	     */
//	    @Bean
//	    public RedisSessionDAO redisSessionDAO() {
//	        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
//	        redisSessionDAO.setRedisManager(redisManager());
//	        return redisSessionDAO;
//	    }
	    
	    @Bean
	    public CustomRealm customRealm() {
	        CustomRealm customRealm = new CustomRealm();
	        // 告诉realm,使用credentialsMatcher加密算法类来验证密文
	        customRealm.setCredentialsMatcher(hashedCredentialsMatcher());
	        customRealm.setCachingEnabled(false);
	        return customRealm;
	    }
	    
	    /**
	     * 
	     * @Title: hashedCredentialsMatcher
	     * @Description: TODO方法描述:(这里用一句话描述这个方法的作用)
	     * @param @return    设定文件
	     * @return HashedCredentialsMatcher    返回类型
	     * @throws
	     */
	    @Bean(name = "credentialsMatcher")
	    public HashedCredentialsMatcher hashedCredentialsMatcher() {
	        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
	        // 散列算法:这里使用MD5算法;
	        hashedCredentialsMatcher.setHashAlgorithmName("md5");
	        // 散列的次数，比如散列两次，相当于 md5(md5(""));
	        hashedCredentialsMatcher.setHashIterations(1);
	        // storedCredentialsHexEncoded默认是true，此时用的是密码加密用的是Hex编码；false时用Base64编码
	        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
	        return hashedCredentialsMatcher;
	    }

		@Bean
	    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
	        return new LifecycleBeanPostProcessor();
	    }
	    
	 /**
	     * *
	     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
	     * *
	     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
	     * * @return
	     */
	    @Bean
	    @DependsOn({"lifecycleBeanPostProcessor"})
	    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
	        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
	        advisorAutoProxyCreator.setProxyTargetClass(true);
	        return advisorAutoProxyCreator;
	    }
	    
	    @Bean
	    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
	        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
	        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
	        return authorizationAttributeSourceAdvisor;
	    }
	    
	}
