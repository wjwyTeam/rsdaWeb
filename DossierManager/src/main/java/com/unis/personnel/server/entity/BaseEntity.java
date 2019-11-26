
package com.unis.personnel.server.entity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import lombok.Getter;
import lombok.Setter;


/**
 * 基础实体类
 * 所有继承该实体类的实体在映射成表的时候会自动加入该类中的字段
 * @author 张琪
 * @date 2019年11月20日 下午12:58:50
 * @description 
 * @TODO
 */
@SuppressWarnings("serial")
@Getter
@Setter
public abstract class BaseEntity implements Serializable {
	

	/**
     * 状态默认为1
     */
    @Column(name="status")
    private Integer status = 0;
    /**
     * 状态默认为1
     */
    @Column(name="del_flag")
    private Integer delFlag = 0;
    
    /**
     * 创建人
     */
    @Column(name="creator")
    private String creator;
    
    /**
     * 创建人编号
     */
    @Column(name="creator_code")
    private String creatorCode;

    /**
     * 创建时间
     */
    @Column(name="create_time")
    private Date createTime;

    /**
     * 刷新时间
     */
    @Column(name="refresh_date")
    private Date refreshDate;
    
    /**
     * 刷新人
     */
    @Column(name="refresher")
    private String refresher;    
    /**
     * 刷新人编号
     */
    @Column(name="refresher_code")
    private String refresherCode;

    /**
     * 备注
     */
    @Column(name="remark")
    private String remark;
}