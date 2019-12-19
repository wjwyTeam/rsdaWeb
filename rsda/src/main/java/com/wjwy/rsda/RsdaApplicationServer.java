/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: zgr
 * @Date: 2019-11-29 20:39:17
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2019-12-19 09:46:01
 */
package com.wjwy.rsda;

import javax.annotation.PostConstruct;

import com.wjwy.rsda.common.TkMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import tk.mybatis.spring.annotation.MapperScan;

//启动注解事务管理
@SpringBootApplication
@EnableTransactionManagement
@EnableAutoConfiguration
@MapperScan(basePackages = { "com.wjwy.rsda.mapper" }, markerInterface = TkMapper.class)
public class RsdaApplicationServer {// 启动注解事务管理,等同于xml配置方式的<tk:annotation-driven/> 解析--创建并注册事务advisor；
	private static Logger logger = LoggerFactory.getLogger(RsdaApplicationServer.class);

	public static void main(String[] args) {
		SpringApplication.run(RsdaApplicationServer.class, args);
		logger.info("=====-----------------------------启动成功--------------------------====");
	}

	@PostConstruct    //在初始化的时候初始化静态对象和它的静态成员变量bean对象，静态存储下来，防止被释放
	public void init() {
		logger.info("=====-----------------------------初始化加载--------------------------====");
	}

}
