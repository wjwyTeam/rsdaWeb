/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-04 16:43:15
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2019-12-04 16:44:03
 */
package com.wjwy.rsda.common.util;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: Swagger工具类
 * @author: 杨祖榕
 * @Date: 2019-01-05 11:13
 * @Version v1.0
 **/
public class SwaggerUtil {

    public static Docket getDocket(String applicationName) {

        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        ticketPar.name("token").description("user token")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build(); //header中的ticket参数非必填，传空也可以
        pars.add(ticketPar.build());    //根据每个方法名也知道当前方法在设置什么参数

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo(applicationName))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);

    }


    private static ApiInfo apiInfo(String applicationName) {
        return new ApiInfoBuilder()
                .title("springboot利用swagger构建api文档,用于对"+ applicationName +"进行管理的接口API")
                .description("简单优雅的restfun风格")
                .termsOfServiceUrl("")
                .version("1.0")
                .build();
    }
	
	public static Docket getDocket(String applicationName,String packageName) {

    ParameterBuilder ticketPar = new ParameterBuilder();
    List<Parameter> pars = new ArrayList<Parameter>();
    ticketPar.name("token").description("user token")
            .modelRef(new ModelRef("string")).parameterType("header")
            .required(false).build(); //header中的ticket参数非必填，传空也可以
    pars.add(ticketPar.build());    //根据每个方法名也知道当前方法在设置什么参数

    return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo(applicationName))
            .select()
            .apis(RequestHandlerSelectors.basePackage(packageName))
            .paths(PathSelectors.any())
            .build()
            .globalOperationParameters(pars);

	}

}
