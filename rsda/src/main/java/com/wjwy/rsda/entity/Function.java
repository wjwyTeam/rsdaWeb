/*
 * @Descripttion: 系统功能表结构实体类
 * @version: v0.0.1
 * @Author: zgr
 * @Date: 2019-12-03 15:08:01
 * @LastEditors: zgr
 * @LastEditTime: 2019-12-03 15:26:20
 */
package com.wjwy.rsda.entity;

import lombok.Data;
import lombok.ToString;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression.DateTime;
@ToString
@Data
public class Function{
    //主键id
    private String functionid;
    //名称
    private String functionname;
    //图标
    private String icon;
    //所属组
    private String groupid;
    //请求地址
    private String url;
    //功能备注
    private String remark;
    //排序字段
    private int forder;
    //建立时间
    private DateTime createtime;
}