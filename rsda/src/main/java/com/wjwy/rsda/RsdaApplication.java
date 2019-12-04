/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: zgr
 * @Date: 2019-11-29 20:39:17
 * @LastEditors: zgr
 * @LastEditTime: 2019-12-03 17:59:02
 */
package com.wjwy.rsda;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RsdaApplication {
	private static Logger logger = LogManager.getLogger(RsdaApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(RsdaApplication.class, args);
		logger.info("=====-----------------------------启动成功--------------------------====");
	}
}
