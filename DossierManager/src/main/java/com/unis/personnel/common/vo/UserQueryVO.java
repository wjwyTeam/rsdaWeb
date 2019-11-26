package com.unis.personnel.common.vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 张琪
 * @date 2019年11月20日
 * @Description 
 */

@ApiModel(value = "人才库条件查询->实体")
@Getter
@Setter
public class UserQueryVO {
	
	/**
	 *用户姓名
	 */
	@ApiModelProperty("用户姓名")
	private String userName;
}
