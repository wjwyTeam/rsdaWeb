package com.wjwy.rsda.common.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.wjwy.rsda.enums.EnumEntitys;

/**
 * 自定义操作日志记录注解
 * 
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 模块
     */
    public String title() default "";

    /**
     * 功能
     */
    public EnumEntitys businessType() default EnumEntitys.OTHER;

    /**
     * 操作人类别
     */
    public EnumEntitys operatorType() default EnumEntitys.MANAGE;

    /**
     * 是否保存请求的参数
     */
    public boolean isSaveRequestData() default true;
}
