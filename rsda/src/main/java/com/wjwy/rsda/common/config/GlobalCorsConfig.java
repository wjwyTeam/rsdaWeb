package com.wjwy.rsda.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 
 * 跨域请求
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