/*
 * @Descripttion: 通用返回类
 * @version: v0.0.1
 * @Author: zgr
 * @Date: 2019-11-30 19:59:02
 * @LastEditors: zgr
 * @LastEditTime: 2019-11-30 22:48:51
 */
package com.wjwy.rsda.entity.resultEntity;

import java.io.Serializable;
import lombok.Data;

@Data
public class SimpleResult implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int loginType;// 0失败，1正常
    private String msg;//返回前端信息
    private String indexUrl;//相应用户返回显示页
}