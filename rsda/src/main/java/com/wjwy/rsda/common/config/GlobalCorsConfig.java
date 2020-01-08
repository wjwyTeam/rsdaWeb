
package com.wjwy.rsda.common.config;

import org.springframework.http.HttpMethod;
import org.springframework.web.filter.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/*
 * @Descripttion: 跨域请求
 * @version: 
 * @Author: ZHANGQI
 * @Date: 2019-12-20 13:47:21
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2020-01-08 14:56:31
 */
@Configuration
public class GlobalCorsConfig {

 private CorsConfiguration buildConfig() {
  CorsConfiguration config = new CorsConfiguration();
  config.addAllowedOrigin("*");
  config.addAllowedHeader("*");
  config.addAllowedMethod(HttpMethod.GET);
  config.addAllowedMethod(HttpMethod.POST);
  config.addAllowedMethod(HttpMethod.PUT);
  config.addAllowedMethod(HttpMethod.DELETE);
  config.addAllowedMethod(HttpMethod.OPTIONS);
  return config;
 }

 @Bean
 public CorsFilter corsFilter() {
  UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
  configSource.registerCorsConfiguration("/**", buildConfig());
  return new CorsFilter(configSource);
 }
}