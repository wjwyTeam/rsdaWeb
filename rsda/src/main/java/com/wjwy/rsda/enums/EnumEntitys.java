/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-04 08:27:14
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2019-12-06 17:47:25
 */
package com.wjwy.rsda.enums;
import io.swagger.annotations.ApiModel;

@ApiModel("枚举>大全")
public enum EnumEntitys {

    YES(true, "启动分页"), NO(false, "停用分页"),

    SCUUESS(1, "成功"), FAILED(2, "失败"),

    RETURN_MSG("msg", "查询成功"), RETURN_CODE("code", "编号");

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
