/**  
 * @Title:SysPermission.java
 * @Package: com.unis.personnel.server.entity
 * @Description: TODO功能描述:(用一句话描述该文件做什么)
 * @Author ZHANGQI
 * @date 2019年11月25日 上午11:31:23
 * @version V1.0  
*/
package com.unis.personnel.server.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
/**
 * @ClassName: SysPermission
 * @Description: TODO功能描述:权限信息
 * @author ZHANGQI
 * @date 2019年11月25日 上午11:31:23
 * @remark 备注:
*/
@SuppressWarnings("serial")
@Entity
	public class SysPermission implements Serializable {
	    @Id@GeneratedValue
	    private Integer id;//主键.
	    private String name;//名称.
	    @Column(columnDefinition="enum('menu','button')")
	    private String resourceType;//资源类型，[menu|button]
	    private String url;//资源路径.
	    private String permission; //权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
	    private Long parentId; //父编号
	    private String parentIds; //父编号列表
	    private Boolean available = Boolean.FALSE;
	    @ManyToMany
	    @JoinTable(name="SysRolePermission",joinColumns={@JoinColumn(name="permissionId")},inverseJoinColumns={@JoinColumn(name="roleId")})
	    private List<SysRole> roles;
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
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}
		/**
		 * @return the resourceType
		 */
		public String getResourceType() {
			return resourceType;
		}
		/**
		 * @param resourceType the resourceType to set
		 */
		public void setResourceType(String resourceType) {
			this.resourceType = resourceType;
		}
		/**
		 * @return the url
		 */
		public String getUrl() {
			return url;
		}
		/**
		 * @param url the url to set
		 */
		public void setUrl(String url) {
			this.url = url;
		}
		/**
		 * @return the permission
		 */
		public String getPermission() {
			return permission;
		}
		/**
		 * @param permission the permission to set
		 */
		public void setPermission(String permission) {
			this.permission = permission;
		}
		/**
		 * @return the parentId
		 */
		public Long getParentId() {
			return parentId;
		}
		/**
		 * @param parentId the parentId to set
		 */
		public void setParentId(Long parentId) {
			this.parentId = parentId;
		}
		/**
		 * @return the parentIds
		 */
		public String getParentIds() {
			return parentIds;
		}
		/**
		 * @param parentIds the parentIds to set
		 */
		public void setParentIds(String parentIds) {
			this.parentIds = parentIds;
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
		 * @return the roles
		 */
		public List<SysRole> getRoles() {
			return roles;
		}
		/**
		 * @param roles the roles to set
		 */
		public void setRoles(List<SysRole> roles) {
			this.roles = roles;
		}

	    // 省略 get set 方法
	 }
