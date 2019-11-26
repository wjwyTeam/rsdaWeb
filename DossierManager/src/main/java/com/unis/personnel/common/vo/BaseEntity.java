package com.unis.personnel.common.vo;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
/**
 * @author 张琪
 * @date 2019年8月27日 下午12:58:50
 * @description 
 * @TODO
 */
@Getter
@Setter
@ApiModel("VO基类->实体")
public class BaseEntity{
	/**
     * 状态默认为1
     */
	@ApiModelProperty("状态")
    private Integer status = 1;
    
    /**
     * 创建人
     */
	@ApiModelProperty("创建人")
    private String creator;
    
    /**
     * 创建人编号
     */
	@ApiModelProperty("创建人编号")
    private String creatorCode;

    /**
     * 创建时间
     */
	@ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 刷新时间
     */
	
	@ApiModelProperty("刷新时间")
    private Date refreshTime;
    
    /**
     * 刷新人
     */
	@ApiModelProperty("刷新人")
    private String refresher;
    
    /**
     * 刷新人编号
     */
	@ApiModelProperty(" 刷新人编号")
    private String refresherCode;

    /**
     * 备注
     */
	@ApiModelProperty(" 备注")
    private String remark;
}
