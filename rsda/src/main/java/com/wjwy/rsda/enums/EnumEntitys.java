/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-04 08:27:14
 * @LastEditors  : ZHANGQI
 * @LastEditTime : 2019-12-19 13:55:40
 */
package com.wjwy.rsda.enums;

import io.swagger.annotations.ApiModel;

@ApiModel("枚举>大全")
public enum EnumEntitys {

   /**
     * 其它
     */
    OTHEROP("OTHEROP","其它"),

    /**
     * 后台用户
     */
    MANAGE("MANAGE","后台用户"),

      /**
     * 其它
     */
    OTHER("OTHER","其它"),

    /**
     * 新增
     */
    INSERT("INSERT","新增"),

    /**
     * 修改
     */
    UPDATE("UPDATE","修改"),

    /**
     * 删除
     */
    DELETE("DELETE","删除"),

    /**
     * 授权
     */
    GRANT("GRANT","授权"),

    /**
     * 导出
     */
    EXPORT("EXPORT","导出"),

    /**
     * 导入
     */
    IMPORT("IMPORT","导入"),

    /**
     * 强退
     */
    FORCE("FORCE","强退"),

    /**
     * 生成代码
     */
    GENCODE("GENCODE","生成代码"),
    
    /**
     * 清空
     */
    CLEAN("CLEAN","清空"),
    /**
     * 主库
     */
    MASTER("MASTER", "主库"),

    /**
     * 从库
     */
    SLAVE("SLAVE", "从库"),

    GJD("0", "根节点"), 

    YES(true, "启动分页"), NO(false, "停用分页"),

    BOY(0, "男"), GIRL(1, "女"),

    ONLINE("0", "在线"), OFFLINE("1", "离线"),

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
