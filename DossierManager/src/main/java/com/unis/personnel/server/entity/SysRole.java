/**  
 * @Title:SysRole.java
 * @Package: com.unis.personnel.server.entity
 * @Description: TODO功能描述:(用一句话描述该文件做什么)
 * @Author ZHANGQI
 * @date 2019年11月25日 上午11:26:53
 * @version V1.0  
*/
package com.unis.personnel.server.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import javax.persistence.JoinColumn;
/**
 * @ClassName: SysRole
 * @Description: TODO功能描述:角色信息
 * @author ZHANGQI
 * @date 2019年11月25日 上午11:26:53
 * @remark 备注:
*/
@Entity
public class SysRole {
	@Id
	@GeneratedValue
    private Integer id; // 编号
    private String role; // 角色标识程序中判断使用,如"admin",这个是唯一的:
    private String description; // 角色描述,UI界面显示使用
    private Boolean available = Boolean.FALSE; // 是否可用,如果不可用将不会添加给用户

    //角色 -- 权限关系：多对多关系;
    @ManyToMany(fetch= FetchType.EAGER)
    @JoinTable(name="SysRolePermission",joinColumns={@JoinColumn(name="roleId")},inverseJoinColumns={@JoinColumn(name="permissionId")})
    private List<SysPermission> permissions;

    // 用户 - 角色关系定义;
    @ManyToMany
    @JoinTable(name="SysUserRole",joinColumns={@JoinColumn(name="roleId")},inverseJoinColumns={@JoinColumn(name="uid")})
    private List<UserInfo> userInfos;// 一个角色对应多个用户

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the available
	 */
	public Boolean getAvailable() {
		return available;
	}

	/**
	 * @param available the available to set
	 */
	public void setAvailable(Boolean available) {
		this.available = available;
	}

	/**
	 * @return the permissions
	 */
	public List<SysPermission> getPermissions() {
		return permissions;
	}

	/**
	 * @param permissions the permissions to set
	 */
	public void setPermissions(List<SysPermission> permissions) {
		this.permissions = permissions;
	}

	/**
	 * @return the userInfos
	 */
	public List<UserInfo> getUserInfos() {
		return userInfos;
	}

	/**
	 * @param userInfos the userInfos to set
	 */
	public void setUserInfos(List<UserInfo> userInfos) {
		this.userInfos = userInfos;
	}

    // 省略 get set 方法
}
