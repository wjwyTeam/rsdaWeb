package com.unis.personnel.server.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * ClassName: RoleDO
 * @Description: 角色管理
 * @author ZHANGQI
 * @date 2019年11月27日
 */

@ToString
@SuppressWarnings("serial")
@Getter
@Setter
@Table(name = "role")
public class RoleDO extends BaseEntity{
	@Column(name = "role_id")  
	private  String roleId; //角色主键id
	
	@Column(name = "role_name")  
	private  String roleName; //角色名称
	
	@Column(name = "rorder")  
	private  int rorder; //角色排序
	
	@Column(name = "filed1")  
	private  String filed1; //备用字段1
	
	@Column(name = "filed2")  
	private  String filed2; //备用字段2
	
	@Column(name = "filed3")  
	private  String filed3; //备用字段3
	
	@Column(name = "filed4")  
	private  String filed4; //备用字段4
	
	@Column(name = "filed5")  
	private  String filed5; //备用字段5
	
	@Column(name = "filed6")  
	private  String filed6; //备用字段6
	
	/**
     * 角色对应权限集合
     */
    private Set<Permissions> permissions;
}
