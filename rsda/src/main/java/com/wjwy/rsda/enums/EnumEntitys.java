/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-04 08:27:14
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2019-12-11 09:45:40
 */
package com.wjwy.rsda.enums;

import io.swagger.annotations.ApiModel;

@ApiModel("枚举>大全")
public enum EnumEntitys {

    GJD("0","根节点"),GIDPARENT("85609d4e-5e69-44fd-b356-6e81cae52671","父节点"),

    YES(true, "启动分页"), NO(false, "停用分页"),
    
    BOY(0, "男"), GIRL(1, "女"),

    SCUUESS(1, "成功"), FAILED(2, "失败");

    private Object value;
    private Object desc;

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getDesc() {
        return desc;
    }

    public void setDesc(Object desc) {
        this.desc = desc;
    }

    private EnumEntitys(Object value, Object desc) {
        this.value = value;
        this.desc = desc;
    }
}
