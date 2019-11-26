package com.unis.personnel.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@ComponentScan("com.unis")
@MapperScan("com.unis.personnel.server.dao")
@org.mybatis.spring.annotation.MapperScan("com.unis.personnel.server.dao")
@EnableAutoConfiguration(exclude = HibernateJpaAutoConfiguration.class)
@EnableScheduling  //从应用的全局定义一个调度器
public class PersonnelApplication {
    public static void main( String[] args ){
        SpringApplication.run(PersonnelApplication.class, args);
        System.out.println("-----------------------------启动成功--------------------------");
    }
    
//    public void onStartup(ServletContext servletContext)throws ServletException {
//        super.onStartup(servletContext);
//        servletContext.setSessionTrackingModes(Collections.singleton(SessionTrackingMode.COOKIE));
//        SessionCookieConfig sessionCookieConfig = servletContext.getSessionCookieConfig();
//        sessionCookieConfig.setHttpOnly(true);
//    }
    
    /** 
     * 跨域过滤器 
     * @return CorsFilter 跨域过滤器对象
     */  
    @Bean  
    public CorsFilter corsFilter() {  
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();  
        source.registerCorsConfiguration("/**", buildConfig()); // 4  
        return new CorsFilter(source);  
    } 
    
     public CorsConfiguration buildConfig() {  
       CorsConfiguration corsConfiguration = new CorsConfiguration();  
       corsConfiguration.addAllowedOrigin("*");  
       corsConfiguration.addAllowedHeader("*");  
       corsConfiguration.addAllowedMethod("*");  
       return corsConfiguration;  
   }  

}