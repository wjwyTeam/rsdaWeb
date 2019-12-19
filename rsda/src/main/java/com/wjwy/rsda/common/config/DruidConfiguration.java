/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-19 13:31:00
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2019-12-19 13:36:32
 */
package com.wjwy.rsda.common.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.druid.spring.boot.autoconfigure.properties.DruidStatProperties;
import com.alibaba.druid.util.Utils;
import com.wjwy.rsda.enums.EnumEntitys;

/**
 * druid 监控
 */
@Configuration
public class DruidConfiguration {
  @Bean
  @ConfigurationProperties("spring.datasource.druid")
  public DataSource masterDataSource(DruidProperties druidProperties)
  {
      DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
      return druidProperties.dataSource(dataSource);
  }

  @Bean
  @ConfigurationProperties("spring.datasource.druid.slave")
  @ConditionalOnProperty(prefix = "spring.datasource.druid.slave", name = "enabled", havingValue = "true")
  public DataSource slaveDataSource(DruidProperties druidProperties)
  {
      DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
      return druidProperties.dataSource(dataSource);
  }

  @Bean(name = "dynamicDataSource")
  @Primary
  public DynamicDataSource dataSource(DataSource masterDataSource, DataSource slaveDataSource)
  {
      Map<Object, Object> targetDataSources = new HashMap<>();
      targetDataSources.put(EnumEntitys.MASTER.getValue(), masterDataSource);
      targetDataSources.put(EnumEntitys.MASTER.getDesc(), slaveDataSource);
      return new DynamicDataSource(masterDataSource, targetDataSources);
  }

  /**
   * 去除监控页面底部的广告
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Bean
  @ConditionalOnProperty(name = "spring.datasource.druid.statViewServlet.enabled", havingValue = "true")
  public FilterRegistrationBean removeDruidFilterRegistrationBean(DruidStatProperties properties)
  {
      // 获取web监控页面的参数
      DruidStatProperties.StatViewServlet config = properties.getStatViewServlet();
      // 提取common.js的配置路径
      String pattern = config.getUrlPattern() != null ? config.getUrlPattern() : "/druid/*";
      String commonJsPattern = pattern.replaceAll("\\*", "js/common.js");
      final String filePath = "support/http/resources/js/common.js";
      // 创建filter进行过滤
      Filter filter = new Filter()
      {
          @Override
          public void init(javax.servlet.FilterConfig filterConfig) throws ServletException
          {
          }
          @Override
          public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                  throws IOException, ServletException
          {
              chain.doFilter(request, response);
              // 重置缓冲区，响应头不会被重置
              response.resetBuffer();
              // 获取common.js
              String text = Utils.readFromResource(filePath);
              // 正则替换banner, 除去底部的广告信息
              text = text.replaceAll("<a.*?banner\"></a><br/>", "");
              text = text.replaceAll("powered.*?shrek.wang</a>", "");
              response.getWriter().write(text);
          }
          @Override
          public void destroy()
          {
          }
      };
      FilterRegistrationBean registrationBean = new FilterRegistrationBean();
      registrationBean.setFilter(filter);
      registrationBean.addUrlPatterns(commonJsPattern);
      return registrationBean;
  }
}